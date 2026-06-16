import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CommControlPanel extends JPanel {
    private DefaultListModel<DataPacket> thisSolModel = new DefaultListModel<>();
    private DefaultListModel<DataPacket> storedModel = new DefaultListModel<>();
    private JList<DataPacket> thisSolList;
    private JList<DataPacket> storedList;
    private JLabel storageLabel;
    private JCheckBox autoTransmitBox;

    public CommControlPanel() {
        setPreferredSize(new Dimension(500, 360));
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        autoTransmitBox = new JCheckBox("Auto-transmit", true);
        autoTransmitBox.addActionListener(this::onAutoToggle);
        top.add(autoTransmitBox, BorderLayout.WEST);

        storageLabel = new JLabel("Storage: 0KB / 0KB");
        top.add(storageLabel, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(2, 1));

        // This sol queue panel
        JPanel solPanel = new JPanel(new BorderLayout());
        solPanel.setBorder(BorderFactory.createTitledBorder("This Sol Queue"));
        thisSolList = new JList<>(thisSolModel);
        solPanel.add(new JScrollPane(thisSolList), BorderLayout.CENTER);

        JPanel solButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton transmitSel = new JButton("Transmit Selected");
        JButton storeSel = new JButton("Store Selected");
        JButton deleteSel = new JButton("Delete Selected");
        JButton transmitAll = new JButton("Transmit All");
        solButtons.add(transmitAll);
        solButtons.add(transmitSel);
        solButtons.add(storeSel);
        solButtons.add(deleteSel);
        solPanel.add(solButtons, BorderLayout.SOUTH);

        center.add(solPanel);

        // Stored onboard panel
        JPanel storedPanel = new JPanel(new BorderLayout());
        storedPanel.setBorder(BorderFactory.createTitledBorder("Stored Onboard"));
        storedList = new JList<>(storedModel);
        storedPanel.add(new JScrollPane(storedList), BorderLayout.CENTER);

        JPanel storedButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton transmitStored = new JButton("Transmit Selected");
        JButton deleteStored = new JButton("Delete Selected");
        JButton transmitAllStored = new JButton("Transmit All");
        storedButtons.add(transmitAllStored);
        storedButtons.add(transmitStored);
        storedButtons.add(deleteStored);
        storedPanel.add(storedButtons, BorderLayout.SOUTH);

        center.add(storedPanel);

        add(center, BorderLayout.CENTER);

        // wire actions
        transmitSel.addActionListener(e -> transmitSelectedFromList(thisSolList, true));
        storeSel.addActionListener(e -> storeSelectedFromSol());
        deleteSel.addActionListener(e -> deleteSelectedFromSol());
        transmitAll.addActionListener(e -> transmitAllThisSol());

        transmitStored.addActionListener(e -> transmitSelectedFromList(storedList, false));
        deleteStored.addActionListener(e -> deleteSelectedFromStored());
        transmitAllStored.addActionListener(e -> transmitAllStored());

        // refresh timer to keep lists in sync
        Timer timer = new Timer(700, e -> refreshModels());
        timer.start();

        // initialize UI state
        refreshModels();
    }

    private void onAutoToggle(ActionEvent e) {
        boolean auto = autoTransmitBox.isSelected();
        if (GameState.communicationsManager != null) GameState.communicationsManager.setAutoTransmit(auto);
    }

    private void refreshModels() {
        // ensure operations happen on EDT
        SwingUtilities.invokeLater(() -> {
            // Auto-tick checkbox reflects manager
            if (GameState.communicationsManager != null) {
                autoTransmitBox.setSelected(GameState.communicationsManager.isAutoTransmit());
                storageLabel.setText("Storage: " + GameState.communicationsManager.getUsedStorageKB() + "KB / " + GameState.communicationsManager.getOnboardCapacityKB() + "KB");
            } else {
                storageLabel.setText("Storage: 0KB / 0KB");
            }

            // preserve selection
            java.util.Set<DataPacket> selThis = new java.util.HashSet<>(thisSolList.getSelectedValuesList());
            java.util.Set<DataPacket> selStored = new java.util.HashSet<>(storedList.getSelectedValuesList());

            // refresh thisSolModel
            thisSolModel.clear();
            if (GameState.toTransmitThisSol != null) {
                for (DataPacket p : GameState.toTransmitThisSol) {
                    thisSolModel.addElement(p);
                }
            }

            // refresh storedModel
            storedModel.clear();
            if (GameState.storedData != null) {
                for (DataPacket p : GameState.storedData) {
                    storedModel.addElement(p);
                }
            }

            // restore selection where possible (by identity)
            java.util.List<Integer> restoreThis = new java.util.ArrayList<>();
            for (int i = 0; i < thisSolModel.size(); i++) {
                if (selThis.contains(thisSolModel.get(i))) restoreThis.add(i);
            }
            if (!restoreThis.isEmpty()) {
                int[] idx = new int[restoreThis.size()];
                for (int i = 0; i < restoreThis.size(); i++) idx[i] = restoreThis.get(i);
                thisSolList.setSelectedIndices(idx);
            }

            java.util.List<Integer> restoreStored = new java.util.ArrayList<>();
            for (int i = 0; i < storedModel.size(); i++) {
                if (selStored.contains(storedModel.get(i))) restoreStored.add(i);
            }
            if (!restoreStored.isEmpty()) {
                int[] idx2 = new int[restoreStored.size()];
                for (int i = 0; i < restoreStored.size(); i++) idx2[i] = restoreStored.get(i);
                storedList.setSelectedIndices(idx2);
            }
        });
    }

    private void transmitSelectedFromList(JList<DataPacket> list, boolean fromThisSol) {
        List<DataPacket> sel = list.getSelectedValuesList();
        if (sel.isEmpty()) return;
        for (DataPacket p : sel) {
            boolean ok = false;
            if (GameState.communicationsManager != null) ok = GameState.communicationsManager.transmit(p);
            if (ok) {
                // remove from both queues if present
                if (GameState.toTransmitThisSol != null) GameState.toTransmitThisSol.remove(p);
                if (GameState.storedData != null) GameState.storedData.remove(p);
            }
        }
        refreshModels();
    }

    private void storeSelectedFromSol() {
        List<DataPacket> sel = thisSolList.getSelectedValuesList();
        if (sel.isEmpty()) return;
        for (DataPacket p : sel) {
            if (GameState.communicationsManager != null) {
                boolean ok = GameState.communicationsManager.storeOnboard(p);
                if (ok) {
                    if (GameState.toTransmitThisSol != null) GameState.toTransmitThisSol.remove(p);
                }
            }
        }
        refreshModels();
    }

    private void deleteSelectedFromSol() {
        List<DataPacket> sel = thisSolList.getSelectedValuesList();
        if (sel.isEmpty()) return;
        for (DataPacket p : sel) {
            if (GameState.toTransmitThisSol != null) GameState.toTransmitThisSol.remove(p);
        }
        refreshModels();
    }

    private void deleteSelectedFromStored() {
        List<DataPacket> sel = storedList.getSelectedValuesList();
        if (sel.isEmpty()) return;
        for (DataPacket p : sel) {
            if (GameState.storedData != null) GameState.storedData.remove(p);
        }
        refreshModels();
    }

    private void transmitAllThisSol() {
        if (GameState.toTransmitThisSol == null || GameState.toTransmitThisSol.isEmpty()) return;
        // iterate over a copy since transmit may remove elements
        for (DataPacket p : new java.util.ArrayList<>(GameState.toTransmitThisSol)) {
            boolean ok = false;
            if (GameState.communicationsManager != null) ok = GameState.communicationsManager.transmit(p);
            if (ok) GameState.toTransmitThisSol.remove(p);
        }
        refreshModels();
    }

    private void transmitAllStored() {
        if (GameState.storedData == null || GameState.storedData.isEmpty()) return;
        for (DataPacket p : new java.util.ArrayList<>(GameState.storedData)) {
            boolean ok = false;
            if (GameState.communicationsManager != null) ok = GameState.communicationsManager.transmit(p);
            if (ok) GameState.storedData.remove(p);
        }
        refreshModels();
    }
}

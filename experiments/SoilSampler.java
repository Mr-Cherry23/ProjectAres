import javax.swing.*;
import java.awt.*;

public class SoilSampler extends Experiment {
    RenderEngine engine;
    private double[] composition = null;
    private double[] demoComposition = null;
    private JPanel plotPanel;

    public SoilSampler(long ID, RenderEngine engine) {
        super("SoilSampler", ID, new boolean[] {true,true,true,true,true});
        this.engine = engine;
        this.runPowerCost = 15.0;
        this.dataSizeKB = 450;

        setLayout(new BorderLayout());

        plotPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                double[] draw = (composition != null) ? composition : demoComposition;
                if (draw == null) return;
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();
                int margin = 20;
                int barW = Math.max(10, (w - margin * 2) / draw.length - 10);
                int baseY = h - margin - 20;

                String[] labels = new String[] {"Silicates", "Iron", "Sulfates", "Water", "Organics"};
                for (int i = 0; i < draw.length; i++) {
                    int x = margin + i * (barW + 10);
                    int barH = (int) (draw[i] * (h - margin * 3));
                    g2.setColor(new Color(100 + i * 30, 120, 140));
                    g2.fillRect(x, baseY - barH, barW, barH);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(x, baseY - barH, barW, barH);
                    g2.drawString(labels[i], x, baseY + 15);
                    g2.drawString(String.format("%.1f%%", draw[i] * 100), x, baseY - barH - 5);
                }
            }
        };
        plotPanel.setPreferredSize(new Dimension(400, 160));
        add(plotPanel, BorderLayout.CENTER);


        JPanel control = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton collect = new JButton("Collect");
        collect.addActionListener(e -> {
            ConsolePanel.log("SoilSampler: collection requested");
            DataPacket pkt = runExperiment();
            if (pkt != null) {
                if (GameState.communicationsManager != null) {
                    GameState.communicationsManager.enqueueOrTransmit(pkt);
                } else {
                    GameState.storedData.add(pkt);
                    ConsolePanel.log("SoilSampler: stored onboard (no comms manager)");
                }
                plotPanel.repaint();
            }
        });
        control.add(collect);
        add(control, BorderLayout.SOUTH);
    }

    @Override
    public DataPacket runExperiment() {
        DataPacket pkt = super.runExperiment();
        if (pkt != null) {
            generateComposition();
            if (plotPanel != null) plotPanel.repaint();
        }
        return pkt;
    }

    private void generateComposition() {

        composition = new double[5];
        double total = 0;
        for (int i = 0; i < composition.length; i++) {
            composition[i] = 0.1 + Math.random() * 0.9;
            total += composition[i];
        }
        for (int i = 0; i < composition.length; i++) composition[i] /= total;
    }
    
    public void updateReadings() {
        if (plotPanel != null) plotPanel.repaint();
    }

}

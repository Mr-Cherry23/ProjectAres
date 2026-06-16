import javax.swing.*;
import java.awt.*;

public class Spectrometer extends Experiment {
    RenderEngine engine;
    private double[] spectrum = null;
    private double[] demoSpectrum = null;
    private JPanel plotPanel;

    public Spectrometer(long ID, RenderEngine engine) {
        super("Spectrometer", ID, new boolean[] {true,true,true,true,true});
        this.engine = engine;
        this.runPowerCost = 12.0;
        this.dataSizeKB = 1200;

        setLayout(new BorderLayout());

        plotPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                double[] draw = (spectrum != null) ? spectrum : demoSpectrum;
                if (draw == null) return;
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();
                int margin = 8;
                int plotW = Math.max(10, w - margin * 2);
                int plotH = Math.max(10, h - margin * 2 - 20);

                g2.setColor(new Color(20, 20, 30));
                g2.fillRect(margin, margin, plotW, plotH);

                double max = 0;
                for (double v : draw) max = Math.max(max, v);
                if (max <= 0) max = 1;

                g2.setColor(Color.GREEN);
                int len = draw.length;
                int prevX = margin;
                int prevY = margin + plotH - (int) (draw[0] / max * plotH);
                for (int i = 1; i < len; i++) {
                    int x = margin + (int) ((i / (double) (len - 1)) * (plotW - 1));
                    int y = margin + plotH - (int) (draw[i] / max * plotH);
                    g2.drawLine(prevX, prevY, x, y);
                    prevX = x;
                    prevY = y;
                }
            }
        };
        plotPanel.setPreferredSize(new Dimension(400, 140));
        add(plotPanel, BorderLayout.CENTER);

        generateDemoSpectrum();

        JPanel control = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel name = new JLabel("Atmospheric Atomic Spectrum");
        JButton measure = new JButton("Analyze");
        measure.addActionListener(e -> {
            ConsolePanel.log("Spectrometer: analysis requested");
            DataPacket pkt = runExperiment();
            if (pkt != null) {
                if (GameState.communicationsManager != null) {
                    GameState.communicationsManager.enqueueOrTransmit(pkt);
                } else {
                    GameState.storedData.add(pkt);
                    ConsolePanel.log("Spectrometer: stored onboard (no comms manager)");
                }
                plotPanel.repaint();
            }
        });
        control.add(name);
        control.add(measure);
        add(control, BorderLayout.SOUTH);
    }

    @Override
    public DataPacket runExperiment() {
        DataPacket pkt = super.runExperiment();
        if (pkt != null) {
            generateSpectrum();
            if (plotPanel != null) plotPanel.repaint();
        }
        return pkt;
    }

    private void generateSpectrum() {
        int len = 160;
        spectrum = new double[len];
        for (int i = 0; i < len; i++) {
            double x = i / (double) len;
            double value = Math.exp(-Math.pow((x - 0.4) * 12, 2)) * 0.8 + Math.exp(-Math.pow((x - 0.75) * 20, 2)) * 0.6;
            value += (Math.random() - 0.5) * 0.08;
            spectrum[i] = Math.max(0, value);
        }
    }

    private void generateDemoSpectrum() {
        int len = 160;
        demoSpectrum = new double[len];
        for (int i = 0; i < len; i++) {
            double x = i / (double) len;
            double value = Math.exp(-Math.pow((x - 0.45) * 12, 2)) * 0.7 + Math.exp(-Math.pow((x - 0.75) * 18, 2)) * 0.5;
            demoSpectrum[i] = Math.max(0, value);
        }
    }
}

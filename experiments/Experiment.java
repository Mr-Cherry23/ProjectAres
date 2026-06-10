import java.awt.*;

import javax.swing.JPanel;

public abstract class Experiment extends JPanel{
    String name;
    long experimentId;
    double condition;
    boolean isPresent;
    boolean[] sitesApplicable = new boolean[5];
    // gameplay fields
    protected double runPowerCost = 0.0; // power cost to run this experiment once
    protected int dataSizeKB = 0; // size of produced data in KB

    public Experiment(String name, long ID, boolean[] sitesApplicable) {
        this.condition = 100;
        this.isPresent = true;
        this.name = name;
        this.experimentId = ID;
        this.sitesApplicable = sitesApplicable;

    }

    public double getCondition() {
        return this.condition;
    }

    public String getName() {
        return this.name;
    }

    public long getExperimentId() {
        return this.experimentId;
    }

    public boolean getIsPresent() {
        return this.isPresent;
    }

    public boolean getIsBroken() {
        return this.condition == 0;
    }

    public boolean getIsApplicable() {
        return this.isPresent;
    }

    public boolean[] getSitesApplicable() {
        return this.sitesApplicable;
    }

    /**
     * Attempt to run the experiment, consuming power and returning a DataPacket
     * or null if there was not enough power.
     */
    public DataPacket runExperiment() {
        if (runPowerCost <= 0) {
            // no power required, produce data if any
            if (dataSizeKB > 0) {
                ConsolePanel.log("[Experiment] " + name + " produced " + dataSizeKB + "KB (no power cost)");
                return new DataPacket(name, dataSizeKB);
            }
            return null;
        }

        if (GameState.powerManager == null) {
            ConsolePanel.log("[Experiment] No PowerManager registered; cannot run " + name);
            return null;
        }

        boolean ok = GameState.powerManager.consume(runPowerCost);
        if (!ok) {
            ConsolePanel.log("[Experiment] Not enough power to run " + name + " (needed=" + runPowerCost + ")");
            return null;
        }

        ConsolePanel.log("[Experiment] Ran " + name + ", produced " + dataSizeKB + "KB");
        if (dataSizeKB > 0) {
            return new DataPacket(name, dataSizeKB);
        }
        return null;
    }

    /**
     * Run the experiment and attempt to auto-transmit the result.
     */
    public void runAndTransmit() {
        DataPacket pkt = runExperiment();
        if (pkt != null && GameState.communicationsManager != null) {
            GameState.communicationsManager.transmit(pkt);
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
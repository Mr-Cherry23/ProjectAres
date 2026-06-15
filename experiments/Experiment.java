import java.awt.*;

import javax.swing.JPanel;

public abstract class Experiment extends JPanel{
    String name;
    long experimentId;
    double condition;
    boolean isPresent;
    boolean[] sitesApplicable = new boolean[5];
    protected double runPowerCost = 0.0;
    protected int dataSizeKB = 0;

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

        public void updateReadings() {
        repaint();
    }


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
            DataPacket pkt = new DataPacket(name, dataSizeKB);
            if (GameState.missionManager != null) {
                GameState.missionManager.reportExperimentRun(this);
            }
            return pkt;
        }
        return null;
    }

    public void runAndTransmit() {
        DataPacket pkt = runExperiment();
        if (pkt != null) {
            if (GameState.communicationsManager != null) {
                GameState.communicationsManager.enqueueOrTransmit(pkt);
            } else {
                GameState.storedData.add(pkt);
                ConsolePanel.log("[Experiment] Stored '" + pkt.getNAME() + "' onboard (no communications manager)");
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
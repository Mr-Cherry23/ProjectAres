public abstract class Experiment{
    String name;
    long experimentId;
    double condition;
    boolean isPresent;
    boolean[] sitesApplicable = new boolean[5];

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
}
public class Mission {
    public final int id;
    public final double targetX;
    public final double targetZ;
    public final String requiredExperiment; // lower-case name
    public final int startSol;
    public final int deadlineSol; // must be completed by this sol (inclusive)
    public boolean completed = false;
    public boolean failed = false;

    public Mission(int id, double targetX, double targetZ, String requiredExperiment, int startSol, int deadlineSol) {
        this.id = id;
        this.targetX = targetX;
        this.targetZ = targetZ;
        this.requiredExperiment = requiredExperiment;
        this.startSol = startSol;
        this.deadlineSol = deadlineSol;
    }

    public int solsRemaining(int currentSol) {
        return Math.max(0, deadlineSol - currentSol);
    }

    @Override
    public String toString() {
        return "Mission#" + id + " -> (" + (int)targetX + "," + (int)targetZ + ") do '" + requiredExperiment + "' by sol " + deadlineSol + (completed?" [COMPLETED]":"") + (failed?" [FAILED]":"");
    }
}

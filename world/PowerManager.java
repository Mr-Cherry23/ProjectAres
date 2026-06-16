public class PowerManager {
    private double baseDailyBudget;
    private double remaining;
    private int sol;
    private double rtgHealth = 1.0;
    private double minDecay = 0.005;
    private double maxDecay = 0.02;

    public PowerManager(double baseDailyBudget) {
        this.baseDailyBudget = baseDailyBudget;
        this.remaining = baseDailyBudget;
        this.sol = 0;
        this.rtgHealth = 1.0;
    }

    public void startNewSol() {
        sol++;

        // RTG decays overnight
        if (rtgHealth > 0) {
            double decay = minDecay + Math.random() * (maxDecay - minDecay);
            rtgHealth = Math.max(0.0, rtgHealth - decay);
        }

        double currentBudget = getDailyBudget();
        remaining = currentBudget;
        ConsolePanel.log("[PowerManager] Starting sol " + sol + ", budget=" + remaining + ", RTG health=" + String.format("%.3f", rtgHealth));

        // clear per-sol transmit queue at start of sol
        if (GameState.communicationsManager != null) {
            GameState.communicationsManager.clearThisSolQueue();
        } else {
            // fallback if there is no communications manager
            if (GameState.toTransmitThisSol != null) GameState.toTransmitThisSol.clear();
        }

        if (GameState.commandScheduler != null) {
            GameState.commandScheduler.runScheduledForNewSol();
        }

        if (GameState.missionManager != null) {
            GameState.missionManager.onNewSol();
        }
    }

    public boolean consume(double amount) {
        if (amount <= 0) return true;
        if (remaining >= amount) {
            remaining -= amount;
            ConsolePanel.log("[PowerManager] Consumed " + amount + ", remaining=" + remaining);
            return true;
        }
        ConsolePanel.log("[PowerManager] Not enough power (needed=" + amount + ", remaining=" + remaining + ")");
        return false;
    }

    public double getRemaining() {
        return remaining;
    }

    public double getDailyBudget() {
        return baseDailyBudget * rtgHealth;
    }

    public double getRTGHealth() {
        return rtgHealth;
    }

    public int getSol() {
        return sol;
    }
}

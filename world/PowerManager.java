public class PowerManager {
    private double dailyBudget;
    private double remaining;
    private int sol;

    public PowerManager(double dailyBudget) {
        this.dailyBudget = dailyBudget;
        this.remaining = dailyBudget;
        this.sol = 0;
    }

    public void startNewSol() {
        sol++;
        remaining = dailyBudget;
        ConsolePanel.log("[PowerManager] Starting sol " + sol + ", budget=" + remaining);
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
        return dailyBudget;
    }

    public int getSol() {
        return sol;
    }
}

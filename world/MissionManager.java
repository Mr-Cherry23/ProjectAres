import java.util.*;

public class MissionManager {
    private Mission current;
    private int nextId = 1;
    private Random rng = new Random();

    public MissionManager() {
    }

    public Mission getCurrent() { return current; }

    public void startInitialMission() {
        if (current == null) generateAndStart();
    }

    private void generateAndStart() {
        if (GameState.engine == null || GameState.powerManager == null) return;
        int mapW = GameState.engine.getMapPixelWidth();
        int mapH = GameState.engine.getMapPixelHeight();
        if (mapW <= 0 || mapH <= 0) {
            mapW = 1000; mapH = 1000;
        }
        double tx = rng.nextInt(Math.max(1, mapW));
        double tz = rng.nextInt(Math.max(1, mapH));

        // pick an available experiment if possible
        String chosen = "thermometer";
        if (GameState.experiments != null && !GameState.experiments.isEmpty()) {
            Object[] keys = GameState.experiments.keySet().toArray();
            chosen = (String) keys[rng.nextInt(keys.length)];
        }

        int startSol = (GameState.powerManager != null) ? GameState.powerManager.getSol() : 0;
        int deadline = startSol + (2 + rng.nextInt(5)); // 2-6 sols

        current = new Mission(nextId++, tx, tz, chosen, startSol, deadline);
        ConsolePanel.log("[MissionManager] New mission: " + current.toString());
    }

    public void onNewSol() {
        // if no current mission, generate one
        if (current == null) {
            generateAndStart();
            return;
        }

        int sol = GameState.powerManager != null ? GameState.powerManager.getSol() : 0;
        if (!current.completed && sol > current.deadlineSol) {
            current.failed = true;
            ConsolePanel.log("[MissionManager] Mission failed: " + current.toString());
            // spawn next mission
            current = null;
            generateAndStart();
        }
    }

    public void reportExperimentRun(Experiment exp) {
        if (current == null || current.completed || current.failed) return;
        if (exp == null) return;
        String en = exp.getName().toLowerCase();
        if (!en.contains(current.requiredExperiment) && !current.requiredExperiment.contains(en)) return;

        // check rover position
        if (GameState.engine == null) return;
        double rx = GameState.engine.getPlayerPosX();
        double rz = GameState.engine.getPlayerPosZ();
        double dx = rx - current.targetX;
        double dz = rz - current.targetZ;
        double dist = Math.sqrt(dx*dx + dz*dz);
        double allowedRadius = 50.0; // proximity radius
        if (dist <= allowedRadius) {
            current.completed = true;
            ConsolePanel.log("[MissionManager] Mission completed: " + current.toString());
            // start next mission after slight delay: immediate
            current = null;
            generateAndStart();
        } else {
            ConsolePanel.log("[MissionManager] Experiment run did not meet mission location. distance=" + (int)dist);
        }
    }
}

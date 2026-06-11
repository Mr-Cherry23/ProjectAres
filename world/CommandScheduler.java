import java.util.*;

public class CommandScheduler {
    private final List<String> scheduled = new ArrayList<>();

    public CommandScheduler() {
    }

    public void scheduleFromText(String text) {
        List<String> cmds = splitCommands(text);
        if (cmds.isEmpty()) {
            ConsolePanel.log("[CommandScheduler] No commands found to schedule.");
            return;
        }
        scheduled.addAll(cmds);
        ConsolePanel.log("[CommandScheduler] Scheduled " + cmds.size() + " command(s) for next sol.");
    }

    public void executeFromText(String text) {
        List<String> cmds = splitCommands(text);
        if (cmds.isEmpty()) {
            ConsolePanel.log("[CommandScheduler] No commands to execute.");
            return;
        }
        for (String c : cmds) {
            executeSingle(c);
        }
    }

    public void runScheduledForNewSol() {
        if (scheduled.isEmpty()) {
            ConsolePanel.log("[CommandScheduler] No scheduled commands for this sol.");
            return;
        }
        ConsolePanel.log("[CommandScheduler] Running " + scheduled.size() + " scheduled command(s) for new sol.");
        for (String c : new ArrayList<>(scheduled)) {
            executeSingle(c);
        }
        scheduled.clear();
    }

    public void listScheduled() {
        ConsolePanel.log("[CommandScheduler] Scheduled commands: " + scheduled.toString());
    }

    private List<String> splitCommands(String text) {
        List<String> out = new ArrayList<>();
        if (text == null) return out;
        String[] parts = text.split("-");
        for (String p : parts) {
            String t = p.trim();
            if (!t.isEmpty()) out.add(t);
        }
        return out;
    }

    private void executeSingle(String rawCmd) {
        String cmd = rawCmd.trim();
        if (cmd.isEmpty()) return;
        String c = cmd.toLowerCase();

        ConsolePanel.log("[CommandScheduler] Executing: " + cmd);

        // Movement / camera commands
        if (c.contains("forward") || c.equals("move forward") || c.equals("forward")) {
            if (GameState.engine != null) GameState.engine.moveForward(); else ConsolePanel.log("[CommandScheduler] No engine available.");
            return;
        }
        if (c.contains("back") || c.contains("backward") || c.equals("move back")) {
            if (GameState.engine != null) GameState.engine.moveBackward(); else ConsolePanel.log("[CommandScheduler] No engine available.");
            return;
        }
        if (c.contains("turn left") || (c.contains("turn") && c.contains("left"))) {
            if (GameState.engine != null) GameState.engine.turnLeft(); else ConsolePanel.log("[CommandScheduler] No engine available.");
            return;
        }
        if (c.contains("turn right") || (c.contains("turn") && c.contains("right"))) {
            if (GameState.engine != null) GameState.engine.turnRight(); else ConsolePanel.log("[CommandScheduler] No engine available.");
            return;
        }
        if (c.contains("rotate left")) {
            if (GameState.engine != null) GameState.engine.rotateLeft(); else ConsolePanel.log("[CommandScheduler] No engine available.");
            return;
        }
        if (c.contains("rotate right")) {
            if (GameState.engine != null) GameState.engine.rotateRight(); else ConsolePanel.log("[CommandScheduler] No engine available.");
            return;
        }
        if (c.contains("look up") || c.equals("lookup")) {
            if (GameState.engine != null) GameState.engine.lookUp(); else ConsolePanel.log("[CommandScheduler] No engine available.");
            return;
        }
        if (c.contains("look down") || c.equals("lookdown")) {
            if (GameState.engine != null) GameState.engine.lookDown(); else ConsolePanel.log("[CommandScheduler] No engine available.");
            return;
        }
        if (c.equals("reset") || c.contains("reset position") ) {
            if (GameState.engine != null) GameState.engine.resetPosition(); else ConsolePanel.log("[CommandScheduler] No engine available.");
            return;
        }

        // Transmit commands
        if (c.contains("transmit all")) {
            if (GameState.storedData == null || GameState.storedData.isEmpty()) {
                ConsolePanel.log("[CommandScheduler] No stored data to transmit.");
                return;
            }
            if (GameState.communicationsManager == null) {
                ConsolePanel.log("[CommandScheduler] No communications manager available.");
                return;
            }
            for (DataPacket pkt : GameState.storedData) {
                if (!pkt.isTransmitted()) {
                    GameState.communicationsManager.transmit(pkt);
                }
            }
            return;
        }

        if (c.startsWith("transmit ")) {
            String target = c.substring("transmit ".length()).trim();
            if (target.isEmpty()) {
                ConsolePanel.log("[CommandScheduler] Transmit target empty.");
                return;
            }
            boolean foundAny = false;
            for (DataPacket pkt : GameState.storedData) {
                if (!pkt.isTransmitted() && pkt.getNAME().toLowerCase().contains(target)) {
                    GameState.communicationsManager.transmit(pkt);
                    foundAny = true;
                }
            }
            if (!foundAny) ConsolePanel.log("[CommandScheduler] No matching stored data to transmit for '" + target + "'.");
            return;
        }

        // List stored data
        if (c.contains("list data") || c.contains("list stored") || c.equals("list")) {
            ConsolePanel.log("[CommandScheduler] Stored data: " + GameState.storedData.toString());
            return;
        }

        // Experiment execution: try to match known experiments
        if (GameState.experiments != null && !GameState.experiments.isEmpty()) {
            for (Experiment exp : GameState.experiments.values()) {
                String en = exp.getName().toLowerCase();
                if (en.contains(c) || c.contains(en) || en.contains(c.replaceAll(" ",""))) {
                    DataPacket pkt = exp.runExperiment();
                    if (pkt != null) {
                        GameState.storedData.add(pkt);
                        ConsolePanel.log("[CommandScheduler] Experiment produced data: " + pkt.getNAME());
                    }
                    return;
                }
            }
        }

        ConsolePanel.log("[CommandScheduler] Unknown command: " + cmd);
    }
}

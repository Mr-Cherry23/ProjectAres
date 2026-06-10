public class CommunicationsManager {
    private double powerPerKB = 0.02; // power cost per KB transmitted

    public CommunicationsManager() {
    }

    public boolean transmit(DataPacket packet) {
        if (packet == null) return false;
        double cost = packet.getSIZE() * powerPerKB;
        if (GameState.powerManager == null) {
            ConsolePanel.log("[CommunicationsManager] No PowerManager available");
            return false;
        }
        if (GameState.powerManager.consume(cost)) {
            packet.setTransmitted(true);
            ConsolePanel.log("[CommunicationsManager] Transmitted '" + packet.getNAME() + "' (" + packet.getSIZE() + "KB) cost=" + cost);
            return true;
        } else {
            ConsolePanel.log("[CommunicationsManager] Failed to transmit '" + packet.getNAME() + "' - insufficient power (cost=" + cost + ")");
            return false;
        }
    }
}

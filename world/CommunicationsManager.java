public class CommunicationsManager {
    private double powerPerKB = 0.02; // power cost per KB transmitted
    // behavior controls and storage
    private boolean autoTransmit = true;
    private int onboardCapacityKB = 5000; // total onboard storage capacity in KB

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

    /**
     * Called when an experiment produced a packet. Depending on `autoTransmit` this
     * will either attempt to transmit immediately or queue for the current sol.
     */
    public boolean enqueueOrTransmit(DataPacket packet) {
        if (packet == null) return false;
        if (autoTransmit) {
            boolean ok = transmit(packet);
            if (!ok) {
                GameState.toTransmitThisSol.add(packet);
                ConsolePanel.log("[CommunicationsManager] Queued '" + packet.getNAME() + "' for this sol (transmit failed)");
            }
            return ok;
        } else {
            GameState.toTransmitThisSol.add(packet);
            ConsolePanel.log("[CommunicationsManager] Queued '" + packet.getNAME() + "' for this sol (auto-transmit disabled)");
            return true;
        }
    }

    public boolean storeOnboard(DataPacket packet) {
        if (packet == null) return false;
        int used = getUsedStorageKB();
        if (used + packet.getSIZE() > onboardCapacityKB) {
            ConsolePanel.log("[CommunicationsManager] Cannot store '" + packet.getNAME() + "' - storage full (used=" + used + "KB)");
            return false;
        }
        GameState.storedData.add(packet);
        ConsolePanel.log("[CommunicationsManager] Stored '" + packet.getNAME() + "' onboard (" + packet.getSIZE() + "KB)");
        return true;
    }

    public int getUsedStorageKB() {
        int sum = 0;
        if (GameState.storedData != null) {
            for (DataPacket p : GameState.storedData) sum += p.getSIZE();
        }
        return sum;
    }

    public int getFreeStorageKB() {
        return Math.max(0, onboardCapacityKB - getUsedStorageKB());
    }

    public int getOnboardCapacityKB() {
        return onboardCapacityKB;
    }

    public void setOnboardCapacityKB(int kb) {
        this.onboardCapacityKB = kb;
    }

    public boolean isAutoTransmit() {
        return autoTransmit;
    }

    public void setAutoTransmit(boolean auto) {
        this.autoTransmit = auto;
    }

    public java.util.List<DataPacket> getThisSolQueue() {
        return GameState.toTransmitThisSol;
    }

    public java.util.List<DataPacket> getStoredData() {
        return GameState.storedData;
    }

    public void clearThisSolQueue() {
        GameState.toTransmitThisSol.clear();
    }
}

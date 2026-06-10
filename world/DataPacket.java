public class DataPacket {
    private final String NAME;
    private final int SIZE;
    private boolean transmitted;

    public DataPacket(String name, int sizeKB) {
        this.NAME = name;
        this.SIZE = sizeKB;
        this.transmitted = false;
    }

    public String getNAME() {
        return NAME;
    }

    public int getSIZE() {
        return SIZE;
    }

    public boolean isTransmitted() {
        return transmitted;
    }

    public void setTransmitted(boolean transmitted) {
        this.transmitted = transmitted;
    }
}

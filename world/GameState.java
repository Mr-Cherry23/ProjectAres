import java.util.*;

public class GameState {
    public static PowerManager powerManager;
    public static CommunicationsManager communicationsManager;

    // Optional mission scheduling and global references
    public static CommandScheduler commandScheduler;
    public static RenderEngine engine;
    public static MissionManager missionManager;
    public static Map<String, Experiment> experiments = new HashMap<>();
    public static java.util.List<DataPacket> storedData = new ArrayList<>();
    public static java.util.List<DataPacket> toTransmitThisSol = new ArrayList<>();
    public static PowerPanel powerPanel;
}

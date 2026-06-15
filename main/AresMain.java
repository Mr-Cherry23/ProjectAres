import javax.swing.*;
import java.awt.*;

public class AresMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AresMain().init());
    }

    public void init() {

        JFrame frame = new JFrame("Project Ares");
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel mainPane = new JPanel();
        JPanel sciencePane = new JPanel();
        JPanel communicationsPane = new JPanel();
    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setLayout(null);
        mainPane.setLayout(null);
        sciencePane.setLayout(null);
        communicationsPane.setLayout(null);
        tabbedPane.setBounds(0, 0, 1920, 1080);

        RenderEngine engine = new RenderEngine();
        SensorPanel sensors = new SensorPanel(engine);

        PowerManager powerManager = new PowerManager(100.0); // default daily budget
        CommunicationsManager commsManager = new CommunicationsManager();
        GameState.powerManager = powerManager;
        GameState.communicationsManager = commsManager;
        GameState.engine = engine;
        GameState.experiments = new java.util.HashMap<>();
        GameState.storedData = new java.util.ArrayList<>();
        GameState.commandScheduler = new CommandScheduler();
        GameState.missionManager = new MissionManager();

        powerManager.startNewSol();


        DraggablePanel viewPanel = new DraggablePanel(new ViewPanel(engine));
        DraggablePanel mahliPanel = new DraggablePanel(new SciencePanel(engine, 2));
        DraggablePanel thermoPanel = new DraggablePanel(new SciencePanel(engine, 1));
        DraggablePanel spectPanel = new DraggablePanel(new SciencePanel(engine, 3));
        DraggablePanel soilPanel = new DraggablePanel(new SciencePanel(engine, 4));
        DraggablePanel movementPanel = new DraggablePanel(new MovementPanel(engine));
        DraggablePanel powerPanel = new DraggablePanel(new PowerPanel(engine));
        DraggablePanel commsPanel = new DraggablePanel(new CommunicationsPanel(engine));
        DraggablePanel commControlDraggable = new DraggablePanel(new CommControlPanel());
        DraggablePanel missionDraggable = new DraggablePanel(new MissionPanel());
        DraggablePanel consoleDraggable = new DraggablePanel(new ConsolePanel());
        DraggablePanel sensorPanel = new DraggablePanel(sensors);
        DraggablePanel enginePanel = new DraggablePanel(engine);

        engine.setSensors(sensors);
        
        
        enginePanel.setLocation(0, 0);
        consoleDraggable.setLocation(400, 720);
        viewPanel.setLocation(0, 720);
        missionDraggable.setLocation(600, 720);
        movementPanel.setLocation(200, 720);
        sensorPanel.setLocation(1280, 0);
        powerPanel.setLocation(0, 500);
        commsPanel.setLocation(0, 520);
        commControlDraggable.setLocation(340, 500);
        

        mainPane.add(viewPanel);
        mainPane.add(consoleDraggable);
        mainPane.add(missionDraggable);
        sciencePane.add(mahliPanel);
        sciencePane.add(thermoPanel);
        sciencePane.add(spectPanel);
        sciencePane.add(soilPanel);
        mainPane.add(movementPanel);
        mainPane.add(sensorPanel);
        communicationsPane.add(powerPanel);
        communicationsPane.add(commsPanel);
        communicationsPane.add(commControlDraggable);
        mainPane.add(enginePanel);

        tabbedPane.addTab("main", mainPane );
        tabbedPane.addTab("science", sciencePane );
        tabbedPane.addTab("communications & power", communicationsPane);

        frame.add(tabbedPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // start the first mission after UI and experiments have been registered
        if (GameState.missionManager != null) GameState.missionManager.startInitialMission();
    }
}
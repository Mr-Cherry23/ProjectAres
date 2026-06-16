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
        DraggablePanel commControlPanel = new DraggablePanel(new CommControlPanel());
        DraggablePanel missionPanel = new DraggablePanel(new MissionPanel(engine));
        DraggablePanel consolePanel = new DraggablePanel(new ConsolePanel());
        DraggablePanel solControlPanel = new DraggablePanel(new SolControlPanel());
        DraggablePanel rtgPanel = new DraggablePanel(new RTGPanel(engine));
        DraggablePanel sensorPanel = new DraggablePanel(sensors);
        DraggablePanel enginePanel = new DraggablePanel(engine);

        engine.setSensors(sensors);
        
        
        enginePanel.setLocation(0, 0);
        consolePanel.setLocation(1280, 500);
        viewPanel.setLocation(1680, 250);
        missionPanel.setLocation(0, 720);
        solControlPanel.setLocation(340, 720);
        movementPanel.setLocation(1680, 0);
        sensorPanel.setLocation(1280, 0);
        powerPanel.setLocation(0, 0);
        commsPanel.setLocation(500, 0);
        rtgPanel.setLocation(0, 220);
        commControlPanel.setLocation(500, 360);
        mahliPanel.setLocation(0, 0);
        thermoPanel.setLocation(1500, 0);
        spectPanel.setLocation(660, 0);
        soilPanel.setLocation(660, 500);
        solControlPanel.setLocation( 400, 720);
        
        mainPane.add(enginePanel);
        mainPane.add(viewPanel);
        mainPane.add(consolePanel);
        mainPane.add(solControlPanel);
        mainPane.add(missionPanel);
        mainPane.add(movementPanel);
        mainPane.add(sensorPanel);
        sciencePane.add(mahliPanel);
        sciencePane.add(thermoPanel);
        sciencePane.add(spectPanel);
        sciencePane.add(soilPanel);
        communicationsPane.add(powerPanel);
        communicationsPane.add(commsPanel);
        communicationsPane.add(commControlPanel);
        communicationsPane.add(rtgPanel);
        
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
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
        // Console UI
        JTextArea consoleArea = new JTextArea();
        consoleArea.setEditable(false);
        JScrollPane consoleScroll = new JScrollPane(consoleArea);
        JPanel consolePanel = new JPanel(new BorderLayout());
        consolePanel.setPreferredSize(new Dimension(400, 200));
        consolePanel.add(consoleScroll, BorderLayout.CENTER);

        RenderEngine engine = new RenderEngine();
        // set up gameplay managers
        PowerManager pm = new PowerManager(100.0); // default daily budget
        CommunicationsManager cm = new CommunicationsManager();
        GameState.powerManager = pm;
        GameState.communicationsManager = cm;
        pm.startNewSol();

        ViewPanel view = new ViewPanel(engine);
        //SciencePanel thermometer = new SciencePanel(engine, 1);
        SciencePanel mahli = new SciencePanel(engine, 2);
        SciencePanel thermo = new SciencePanel(engine, 1);
        SciencePanel spect = new SciencePanel(engine, 3);
        SciencePanel soil = new SciencePanel(engine, 4);
        MovementPanel movement = new MovementPanel(engine);
        SensorPanel sensors = new SensorPanel(engine);
        PowerPanel power = new PowerPanel(engine);
        CommunicationsPanel comms = new CommunicationsPanel(engine);

        DraggablePanel viewPanel = new DraggablePanel(view);
        //DraggablePanel thermometerPanel = new DraggablePanel(thermometer);
        DraggablePanel mahliPanel = new DraggablePanel(mahli);
        DraggablePanel thermoPanel = new DraggablePanel(thermo);
        DraggablePanel spectPanel = new DraggablePanel(spect);
        DraggablePanel soilPanel = new DraggablePanel(soil);
        DraggablePanel movementPanel = new DraggablePanel(movement);
        DraggablePanel sensorPanel = new DraggablePanel(sensors);
        DraggablePanel powerPanel = new DraggablePanel(power);
        DraggablePanel commsPanel = new DraggablePanel(comms);
        DraggablePanel enginePanel = new DraggablePanel(engine);


        DraggablePanel consoleDraggable = new DraggablePanel(consolePanel);
        ConsolePanel.setTextArea(consoleArea);

        engine.setInterfaces(mahli, sensors);
        
        enginePanel.setLocation(0, 0);
        consoleDraggable.setLocation(400, 720);
        viewPanel.setLocation(0, 720);
        movementPanel.setLocation(200, 720);
        sensorPanel.setLocation(1280, 0);
        
        
        powerPanel.setLocation(0, 500);
        commsPanel.setLocation(0, 520);
        

        mainPane.add(viewPanel);
        mainPane.add(consoleDraggable);
        //frame.add(thermometerPanel);
        sciencePane.add(mahliPanel);
        sciencePane.add(thermoPanel);
        sciencePane.add(spectPanel);
        sciencePane.add(soilPanel);
        mainPane.add(movementPanel);
        mainPane.add(sensorPanel);
        communicationsPane.add(powerPanel);
        communicationsPane.add(commsPanel);
        mainPane.add(enginePanel);

        tabbedPane.addTab("main", mainPane );
        tabbedPane.addTab("science", sciencePane );
        tabbedPane.addTab("communications & power", communicationsPane);

        frame.add(tabbedPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
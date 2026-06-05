import javax.swing.*;

public class AresMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AresMain().init());
    }

    public void init() {

        JFrame frame = new JFrame("Project Ares");
    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setLayout(null);

        RenderEngine engine = new RenderEngine();
    
        ViewInterface view = new ViewInterface(engine);
        //ScienceInterface thermometer = new ScienceInterface(engine, 1);
        ScienceInterface mahli = new ScienceInterface(engine, 2);
        MovementInterface movement = new MovementInterface(engine);
        SensorInterface sensors = new SensorInterface(engine);
        PowerInterface power = new PowerInterface(engine);
        CommunicationsInterface comms = new CommunicationsInterface(engine);

        DraggablePanel viewPanel = new DraggablePanel(view);
        //DraggablePanel thermometerPanel = new DraggablePanel(thermometer);
        DraggablePanel mahliPanel = new DraggablePanel(mahli);
        DraggablePanel movementPanel = new DraggablePanel(movement);
        DraggablePanel sensorPanel = new DraggablePanel(sensors);
        DraggablePanel powerPanel = new DraggablePanel(power);
        DraggablePanel commsPanel = new DraggablePanel(comms);
        DraggablePanel enginePanel = new DraggablePanel(engine);
    
        engine.setInterfaces(mahli, sensors);
        
        enginePanel.setLocation(640, 0);
        viewPanel.setLocation(640, 720);
        movementPanel.setLocation(840, 720);
        sensorPanel.setLocation(240, 0);
        powerPanel.setLocation(0, 500);
        commsPanel.setLocation(0, 520);

        frame.add(viewPanel);
        //frame.add(thermometerPanel);
        frame.add(mahliPanel);
        frame.add(movementPanel);
        frame.add(sensorPanel);
        //frame.add(powerPanel);
        frame.add(commsPanel);
        frame.add(enginePanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
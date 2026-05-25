import javax.swing.*;

import java.awt.*;

public class AresMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AresMain().init());
    }

    public void init() {

        JFrame frame = new JFrame("Project Ares");
    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
    
        // MAIN LAYOUT
        frame.setLayout(null);

    
        RenderEngine engine = new RenderEngine();
    
        ViewInterface view = new ViewInterface(engine);
        ScienceInterface science = new ScienceInterface(engine);
        MovementInterface movement = new MovementInterface(engine);
        SensorInterface sensors = new SensorInterface(engine);

        DraggablePanel viewPanel = new DraggablePanel(view);
        DraggablePanel sciencePanel = new DraggablePanel(science);
        DraggablePanel movementPanel = new DraggablePanel(movement);
        DraggablePanel sensorPanel = new DraggablePanel(sensors);
        DraggablePanel enginePanel = new DraggablePanel(engine);
    
        engine.setInterfaces(science, sensors);
    
        // CENTER STACK

    
        frame.add(viewPanel);
        frame.add(sciencePanel);
        frame.add(movementPanel);
        frame.add(sensorPanel);
        frame.add(enginePanel);
    
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
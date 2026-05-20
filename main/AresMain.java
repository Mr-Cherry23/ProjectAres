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
        frame.setLayout(new BorderLayout());
    
        RenderEngine engine = new RenderEngine();
    
        ViewInterface view = new ViewInterface(engine);
        ScienceInterface science = new ScienceInterface(engine);
        MovementInterface movement = new MovementInterface(engine);
        SensorInterface sensors = new SensorInterface(engine);
    
        engine.setInterfaces(science, sensors);
    
        // CENTER STACK
        JPanel uiStack = new JPanel();
    
        uiStack.setLayout(
            new BoxLayout(uiStack, BoxLayout.Y_AXIS)
        );
    
        uiStack.add(view);
        uiStack.add(movement);
        uiStack.add(sensors);
    
        frame.add(science, BorderLayout.WEST);
        frame.add(engine, BorderLayout.CENTER);
        frame.add(uiStack, BorderLayout.EAST);
    
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
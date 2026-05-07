import javax.swing.*;
import java.awt.*;

public class AresMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AresMain().init());
    }

    public void init() {

        JFrame frame = new JFrame("Project Ares");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 920);
        frame.setLayout(new BorderLayout());

        RenderEngine engine = new RenderEngine();
        ViewInterface view = new ViewInterface(engine);
        ScienceInterface science = new ScienceInterface(engine);
        MovementInterface movement = new MovementInterface(engine);


        frame.add(engine, BorderLayout.PAGE_START); 
        frame.add(view, BorderLayout.LINE_START);
        frame.add(science, BorderLayout.CENTER);
        frame.add(movement, BorderLayout.LINE_END);   

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
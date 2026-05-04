import javax.swing.*;
import java.awt.*;

public class AresMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AresMain().init());
    }

    public void init() {

        JFrame frame = new JFrame("Project Ares");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 800);
        frame.setLayout(new BorderLayout());

        RenderEngine engine = new RenderEngine();
        Interface ui = new Interface(engine);

        frame.add(engine, BorderLayout.CENTER); // top
        frame.add(ui, BorderLayout.SOUTH);      // bottom

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
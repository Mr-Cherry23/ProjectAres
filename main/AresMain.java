import javax.swing.*;

public class AresMain {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Mars Terrain Viewer");

            RenderEngine engine = new RenderEngine();

            frame.add(engine);
            frame.setSize(1280, 720);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            engine.requestFocusInWindow();
        });
    }
}
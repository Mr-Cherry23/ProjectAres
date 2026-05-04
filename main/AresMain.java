import javax.swing.*;

public class AresMain {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AresMain().init();
            }
        });
    }

    public void init() {

        JFrame frame = new JFrame("Mars Terrain Viewer");

        RenderEngine engine = new RenderEngine();
        Interface ui = new Interface(engine); // pass engine reference

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        engine.setBounds(0, 0, 1280, 720);
        ui.setBounds(0, 0, 1280, 720);

        layeredPane.add(engine, Integer.valueOf(0));
        layeredPane.add(ui, Integer.valueOf(1));

        frame.add(layeredPane);
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        engine.requestFocusInWindow();
    }
}
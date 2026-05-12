import javax.swing.*;
import java.awt.*;

public class SensorInterface extends JPanel {
    final static boolean shouldFill = true;
    RenderEngine engine;
    Compass inclinometer;

    public SensorInterface(RenderEngine engine) {
        this.engine = engine;
        setPreferredSize(new Dimension(426, 500));
        setBackground(Color.DARK_GRAY);
        setLayout(new GridBagLayout());
        initUI();
    }

    void initUI() {
        GridBagConstraints i = new GridBagConstraints();
        i.fill = GridBagConstraints.BOTH;

        inclinometer = new Compass();
        inclinometer.setPreferredSize(new Dimension(300,300));
        i.weightx = 1;
        i.weighty = 1;
        i.gridx = 0;
        i.gridy = 0;
        add(inclinometer, i);

    }
    public void updateReadings() {
        inclinometer.setHeading(engine.roverAttitude[0]);
        inclinometer.setCameraAngle(engine.cameraAngle);
        inclinometer.updateReadings();
    }
}
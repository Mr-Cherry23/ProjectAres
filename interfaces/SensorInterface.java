
import javax.swing.*;

import java.awt.*;

public class SensorInterface extends JPanel {
    final static boolean shouldFill = true;
    RenderEngine engine;
    Compass compass;
    Inclinometer inclinometer;

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

        compass = new Compass();
        compass.setPreferredSize(new Dimension(300,300));
        i.weightx = 1;
        i.weighty = 1;
        i.gridx = 0;
        i.gridy = 0;
        add(compass, i);

        inclinometer = new Inclinometer();
        inclinometer.setPreferredSize(new Dimension(300,300));
        i.weightx = 1;
        i.weighty = 1;
        i.gridx = 0;
        i.gridy = 1;
        add(inclinometer, i);
    }

    public void updateReadings() {
        compass.setHeading(engine.roverAttitude[0]);
        compass.setCameraAngle(engine.cameraAngle);
        compass.updateReadings();
        inclinometer.setAttitude(engine.roverAttitude[1], engine.roverAttitude[2]);
        inclinometer.updateReadings();
    }
}
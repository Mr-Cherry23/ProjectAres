import javax.swing.*;
import java.awt.*;

public class ScienceInterface extends JPanel {

    RenderEngine engine;
    Thermometer thermometer;
    HandLenseImager mahli;
    Experiment experiment;

    public ScienceInterface(RenderEngine engine, int experimentId) {
        this.engine = engine;

        setLayout(new GridLayout());
        setPreferredSize(new Dimension(426, 200));
        setBackground(Color.WHITE);

        initUI(experimentId);
    }

    private void initUI(int experimentId) {
        if (experimentId == 1) {
            thermometer = new Thermometer(experimentId);
            thermometer.setPreferredSize(new Dimension(400, 200));
            add(thermometer);
        } else if (experimentId == 2) {
            mahli = new HandLenseImager(experimentId);
            mahli.setPreferredSize(new Dimension(400, 200));
            add(mahli.getPanel());
        }
    }

    public void updateReadings() {
        thermometer.updateReadings();
    }


}
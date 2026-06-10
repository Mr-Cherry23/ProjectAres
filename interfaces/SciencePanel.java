import javax.swing.*;
import java.awt.*;

public class SciencePanel extends JPanel {

    RenderEngine engine;
    Thermometer thermometer;
    HandLenseImager mahli;
    Experiment experiment;

    public SciencePanel(RenderEngine engine, int experimentId) {
        this.engine = engine;

        setLayout(new GridLayout());
        setBackground(Color.WHITE);

        initUI(experimentId);
    }

    private void initUI(int experimentId) {
        if (experimentId == 1) {
            thermometer = new Thermometer(experimentId, engine);
            thermometer.setPreferredSize(new Dimension(400, 200));
            add(thermometer);
        } else if (experimentId == 2) {
            mahli = new HandLenseImager(experimentId, engine);
            mahli.setPreferredSize(new Dimension(400, 350));
            add(mahli);
        } else if (experimentId == 3) {
            Spectrometer spec = new Spectrometer(experimentId, engine);
            spec.setPreferredSize(new Dimension(400, 250));
            add(spec);
        } else if (experimentId == 4) {
            SoilSampler soil = new SoilSampler(experimentId, engine);
            soil.setPreferredSize(new Dimension(400, 250));
            add(soil);
        }
    }

    public void updateReadings() {
        if (thermometer != null) thermometer.updateReadings();
    }


}

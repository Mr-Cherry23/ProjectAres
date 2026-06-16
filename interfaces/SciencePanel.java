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
            thermometer.setPreferredSize(new Dimension(400, 1000));
            add(thermometer);
            this.experiment = thermometer;
            GameState.experiments.put(thermometer.getName().toLowerCase(), thermometer);
        } else if (experimentId == 2) {
            mahli = new HandLenseImager(experimentId, engine);
            mahli.setPreferredSize(new Dimension(660, 550));
            add(mahli);
            this.experiment = mahli;
            GameState.experiments.put(mahli.getName().toLowerCase(), mahli);
        } else if (experimentId == 3) {
            Spectrometer spec = new Spectrometer(experimentId, engine);
            spec.setPreferredSize(new Dimension(840, 500));
            add(spec);
            this.experiment = spec;
            GameState.experiments.put(spec.getName().toLowerCase(), spec);
        } else if (experimentId == 4) {
            SoilSampler soil = new SoilSampler(experimentId, engine);
            soil.setPreferredSize(new Dimension(840, 500));
            add(soil);
            this.experiment = soil;
            GameState.experiments.put(soil.getName().toLowerCase(), soil);
        }
    }

    public void updateReadings() {
        if (experiment != null) experiment.updateReadings();
    }


}

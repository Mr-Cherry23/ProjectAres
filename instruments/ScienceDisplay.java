import javax.swing.*;
import java.awt.*;

public class ScienceDisplay extends JPanel {
    RenderEngine engine;
    Experiment experiment;

    public ScienceDisplay(RenderEngine engine, Experiment experiment) {
        this.engine = engine;
        this.experiment = experiment;

        setLayout(new GridLayout(1,1));
        setPreferredSize(new Dimension(426, 200));
        setBackground(Color.WHITE);

        initUI();
    }

    void initUI() {
        add(experiment);
    }

    void updateReadings() {
        experiment.repaint();
    }


}
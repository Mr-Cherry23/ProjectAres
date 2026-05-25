import javax.swing.*;
import java.awt.*;

public class ScienceInterface extends JPanel {

    RenderEngine engine;
    ScienceDisplay scienceDisplay;
    Thermometer thermometer;
    HandLenseImager mahli;

    public ScienceInterface(RenderEngine engine) {
        this.engine = engine;

        setLayout(new GridLayout());
        setPreferredSize(new Dimension(426, 200));
        setBackground(Color.WHITE);

        initUI();
    }

    private void initUI() {
        thermometer = new Thermometer(1);
        mahli = new HandLenseImager(2);
        scienceDisplay = new ScienceDisplay(engine, thermometer);
        scienceDisplay = new ScienceDisplay(engine, mahli);
        scienceDisplay.setPreferredSize(new Dimension(400, 200));
        scienceDisplay.updateReadings();
        add(scienceDisplay);
    }

    public void updateReadings() {
        scienceDisplay.updateReadings();
    }


}
import javax.swing.*;
import java.awt.*;

public class Spectrometer extends Experiment {
    RenderEngine engine;

    public Spectrometer(long ID, RenderEngine engine) {
        super("Spectrometer", ID, new boolean[] {true,true,true,true,true});
        this.engine = engine;
        this.runPowerCost = 12.0;
        this.dataSizeKB = 1200;

        setLayout(new FlowLayout());
        JButton measure = new JButton("Analyze");
        measure.addActionListener(e -> {
            ConsolePanel.log("Spectrometer: analysis requested");
            runAndTransmit();
        });
        add(measure);
    }
}

import javax.swing.*;
import java.awt.*;

public class SoilSampler extends Experiment {
    RenderEngine engine;

    public SoilSampler(long ID, RenderEngine engine) {
        super("SoilSampler", ID, new boolean[] {true,true,true,true,true});
        this.engine = engine;
        this.runPowerCost = 15.0;
        this.dataSizeKB = 450;

        setLayout(new FlowLayout());
        JButton collect = new JButton("Collect");
        collect.addActionListener(e -> {
            ConsolePanel.log("SoilSampler: collection requested");
            runAndTransmit();
        });
        add(collect);
    }
}

import javax.swing.*;
import java.awt.*;

public class ScienceDisplay extends JPanel {
    RenderEngine engine;

    public ScienceDisplay(RenderEngine engine) {
        this.engine = engine;

        setLayout(new GridLayout(1,1));
        setPreferredSize(new Dimension(426, 200));
        setBackground(Color.WHITE);

        initUI();
    }

    void initUI() {
        GridBagConstraints i = new GridBagConstraints();
        i.fill = GridBagConstraints.BOTH;


        i.weightx = 1;
        i.weighty = 1;
        i.gridx = 0;
        i.gridy = 0;


        i.weightx = 1;
        i.weighty = 1;
        i.gridx = 0;
        i.gridy = 1;

    }

    void updateReadings() {

        
    }


}
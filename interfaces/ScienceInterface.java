import javax.swing.*;
import java.awt.*;

public class ScienceInterface extends JPanel {

    RenderEngine engine;
    JLabel positionXLabel;
    JLabel positionZLabel;

    public ScienceInterface(RenderEngine engine) {
        this.engine = engine;

        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(426, 200));
        setBackground(Color.WHITE);

        initUI();
    }

    private void initUI() {
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

    public void updateReadings() {
        
        positionXLabel.setText(String.format("%.2f", engine.playerPosX));
        positionZLabel.setText(String.format("%.2f", engine.playerPosZ));
        
    }


}
import javax.swing.*;
import java.awt.*;

public class ScienceInterface extends JPanel {

    RenderEngine engine;
    JLabel positionXLabel;
    JLabel positionZLabel;

    public ScienceInterface(RenderEngine engine) {
        this.engine = engine;

        setLayout(new GridLayout(3,3));
        setPreferredSize(new Dimension(426, 200));
        setBackground(Color.WHITE);

        initUI();
    }

    void initUI() {
        
        positionXLabel = new JLabel(String.format("%.2f", engine.playerPosX));
        add(positionXLabel);
        positionZLabel = new JLabel(String.format("%.2f", engine.playerPosZ));
        add(positionZLabel);
    }

    void updateReadings() {
        
        positionXLabel.setText(String.format("%.2f", engine.playerPosX));
        positionZLabel.setText(String.format("%.2f", engine.playerPosZ));
        
    }


}
import javax.swing.*;
import java.awt.*;

public class MovementInterface extends JPanel {

    RenderEngine engine;

    public MovementInterface(RenderEngine engine) {
        this.engine = engine;

        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(426, 200));
        setBackground(Color.DARK_GRAY);

        initUI();
    }

    void initUI() {
        GridBagConstraints i = new GridBagConstraints();
        i.fill = GridBagConstraints.BOTH;
        JButton left = new JButton("<=");
        left.setPreferredSize(new Dimension(50, 50));
        left.addActionListener(event -> engine.turnLeft());
        i.gridx = 0;
        i.gridy = 1;
        add(left, i);
        
        JButton upView = new JButton("^");
        upView.setPreferredSize(new Dimension(50, 50));
        upView.addActionListener(event -> engine.moveForward());
        i.gridx = 1;
        i.gridy = 0;
        add(upView, i);    

        JButton downView = new JButton("!^");
        downView.setPreferredSize(new Dimension(50, 50));
        downView.addActionListener(event -> engine.moveBackward());
        i.gridx = 1;
        i.gridy = 2;
        add(downView, i);

        JButton right = new JButton("=>");
        right.setPreferredSize(new Dimension(50, 50));
        right.addActionListener(event -> engine.turnRight());
        i.gridwidth = GridBagConstraints.REMAINDER;
        i.gridx = 2;
        i.gridy = 1;
        add(right, i);

    }
}
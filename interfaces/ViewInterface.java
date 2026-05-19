import javax.swing.*;
import java.awt.*;

public class ViewInterface extends JPanel {
    final static boolean shouldFill = true;
    RenderEngine engine;

    public ViewInterface(RenderEngine engine) {
        this.engine = engine;
        setPreferredSize(new Dimension(426, 500));
        setBackground(Color.DARK_GRAY);
        setLayout(new GridBagLayout());
        initUI();
    }

    private void initUI() {
        GridBagConstraints i = new GridBagConstraints();
        i.fill = GridBagConstraints.BOTH;
        JButton left = new JButton("<=");
        left.setPreferredSize(new Dimension(50, 50));
        left.addActionListener(event -> engine.rotateLeft());
        i.gridx = 0;
        i.gridy = 1;
        add(left, i);
        
        JButton upView = new JButton("^");
        upView.setPreferredSize(new Dimension(50, 50));
        upView.addActionListener(event -> engine.lookUp());
        i.gridx = 1;
        i.gridy = 0;
        add(upView, i);    

        JButton downView = new JButton("!^");
        downView.setPreferredSize(new Dimension(50, 50));
        downView.addActionListener(event -> engine.lookDown());
        i.gridx = 1;
        i.gridy = 2;
        add(downView, i);

        JButton right = new JButton("=>");
        right.setPreferredSize(new Dimension(50, 50));
        right.addActionListener(event -> engine.rotateRight());
        i.gridwidth = GridBagConstraints.REMAINDER;
        i.gridx = 2;
        i.gridy = 1;
        add(right, i);

    }
}
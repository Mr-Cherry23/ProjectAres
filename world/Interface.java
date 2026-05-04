import javax.swing.*;
import java.awt.*;

public class Interface extends JPanel {

    RenderEngine engine;

    public Interface(RenderEngine engine) {
        this.engine = engine;

        setLayout(new GridLayout(4,2));
        setPreferredSize(new Dimension(1280, 200));
        setBackground(Color.DARK_GRAY);

        initUI();
    }

    void initUI() {

        JButton left = new JButton("Left");
        JButton right = new JButton("Right");
        JButton forward = new JButton("Forward");
        JButton back = new JButton("Back");
        JButton reset = new JButton("Reset");
        JButton upView = new JButton("Look Up");
        JButton downView = new JButton("Look Down");
        
        upView.addActionListener(event -> engine.lookUp());
        downView.addActionListener(event -> engine.lookDown());
        left.addActionListener(event -> engine.rotateLeft());
        right.addActionListener(event -> engine.rotateRight());
        forward.addActionListener(event -> engine.moveForward());
        back.addActionListener(event -> engine.moveBackward());
        reset.addActionListener(event -> engine.resetPosition());

        add(left);
        add(right);
        add(back); 
        add(forward);
        add(upView);
        add(downView);
        add(reset);
    }
}
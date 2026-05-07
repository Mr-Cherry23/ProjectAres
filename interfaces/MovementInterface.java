import javax.swing.*;
import java.awt.*;

public class MovementInterface extends JPanel {

    RenderEngine engine;

    public MovementInterface(RenderEngine engine) {
        this.engine = engine;

        setLayout(new GridLayout(3,1));
        setPreferredSize(new Dimension(426, 200));
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

        add(forward);
        add(reset); 
        add(back);
    }
}
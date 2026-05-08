import javax.swing.*;
import java.awt.*;

public class ScienceInterface extends JPanel {

    RenderEngine engine;

    public ScienceInterface(RenderEngine engine) {
        this.engine = engine;

        setLayout(new GridLayout(3,3));
        setPreferredSize(new Dimension(426, 200));
        setBackground(Color.WHITE);

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

        add(new JLabel(String.format("%.2f", engine.playerPosX)));
        add(upView);
        add(new JLabel(String.format("%.2f", engine.playerPosZ)));
        add(left);
        add(new JLabel(""));
        add(right);
        add(new JLabel(""));
        add(downView);
        add(new JLabel(""));
        //add(reset); 
        //add(back); 
        //add(forward);
    }
}
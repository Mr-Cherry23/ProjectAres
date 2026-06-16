import javax.swing.*;
import java.awt.*;

public class PowerPanel extends JPanel{
    RenderEngine engine;

    public PowerPanel(RenderEngine engine) {
        this.engine = engine;
        setPreferredSize(new Dimension(500, 220));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        double remaining = 0;
        double budget = 1;
        if (GameState.powerManager != null) {
            remaining = GameState.powerManager.getRemaining();
            budget = GameState.powerManager.getDailyBudget();
        }

        double frac = (budget > 0) ? Math.max(0, Math.min(1, remaining / budget)) : 0;

        int x = 40;
        int y = 60;
        int w = Math.max(100, getWidth() - 160);
        int h = 80;

        // battery outline
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x - 2, y - 2, w + 4, h + 4);
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, w, h);
        // tip
        int tipW = 8;
        int tipH = h / 2;
        g2.fillRect(x + w, y + (h - tipH)/2, tipW, tipH);

        // fill
        int fillW = (int)((w - 4) * frac);
        if (frac > 0.6) g2.setColor(new Color(50, 200, 50));
        else if (frac > 0.25) g2.setColor(new Color(230, 180, 20));
        else g2.setColor(new Color(200, 50, 50));

        g2.fillRect(x + 2, y + 2, fillW, h - 4);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.drawString(String.format("Power: %.1f / %.1f", remaining, budget), x, y + h + 20);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.drawString("Rover Power Managment", 60, 40);
    }

    public void updateReadings() {
        repaint();
    }
}

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;

public class Display extends JPanel
        implements MouseMotionListener,
        MouseListener {
    private BallSystem ballSystem;
    private int[] tempBall;

    Display(BallSystem s) {
        ballSystem = s;

        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.WHITE);

        addMouseMotionListener(this);
        addMouseListener(this);

        tempBall = new int[3];

        new Thread(new RunSim()).start();
    }

    public class RunSim implements Runnable {
        private double t0;

        public void run() {
            while (true) {
                if (!ballSystem.isPaused()) {
                    ballSystem.evolve(System.nanoTime() / 1e9 - t0);
                    repaint();
                }

                t0 = System.nanoTime() / 1e9;

                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHints(new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON));
        ballSystem.paintBalls(g2d);
        g.setColor(Color.LIGHT_GRAY);
        g2d.draw(new Ellipse2D.Double(
                tempBall[0] - getLocationOnScreen().x - tempBall[2],
                tempBall[1] - getLocationOnScreen().y - tempBall[2],
                tempBall[2] * 2,
                tempBall[2] * 2));
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        if (ballSystem.getToolState() == 0) {
            tempBall[0] = e.getXOnScreen();
            tempBall[1] = e.getYOnScreen();
        }
        if (ballSystem.getToolState() == 1)
            ballSystem.removeBall(e.getXOnScreen(), e.getYOnScreen());
    }

    public void mousePressed(MouseEvent e) {
        if (ballSystem.getToolState() == 0) {
            tempBall[0] = e.getXOnScreen();
            tempBall[1] = e.getYOnScreen();
        }
        if (ballSystem.getToolState() == 2)
            ballSystem.pickBall(e.getXOnScreen(), e.getYOnScreen());
    }

    public void mouseReleased(MouseEvent e) {
        if (ballSystem.getToolState() == 0) {
            double[] nxtPos = {tempBall[0], tempBall[1]};

            if (tempBall[2] > 5)
                ballSystem.addBall(nxtPos, tempBall[2]);
            else
                ballSystem.addBall(nxtPos, 5);

            tempBall[0] = 0;
            tempBall[1] = 0;
            tempBall[2] = 0;
        }

        if (ballSystem.getToolState() == 2)
            ballSystem.unPick();
    }

    public void mouseDragged(MouseEvent e) {
        if (ballSystem.getToolState() == 0) {
            int dx = e.getXOnScreen() - tempBall[0];
            int dy = e.getYOnScreen() - tempBall[1];
            int r = (int)Math.sqrt(dx * dx + dy * dy);
            tempBall[2] = r;
        }

        if (ballSystem.getToolState() == 2)
            ballSystem.setMosXY(e.getLocationOnScreen());
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }
}

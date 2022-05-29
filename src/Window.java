import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;

public class Window extends JFrame implements ComponentListener {
    private BallSystem ballSystem;
    private Display display;

    public Window() {
        ballSystem = new BallSystem();
        display = new Display(ballSystem);

        setTitle("Physics Simulation");
        addComponentListener(this);

        add(display);
        add(new Input(ballSystem), "West");
        pack();
        setMinimumSize(new Dimension(600, 300));
        setDefaultCloseOperation(3);
        setVisible(true);
    }

    public void componentShown(ComponentEvent e) {
        try {
            ballSystem.setBounds(
                    display.getLocationOnScreen().x,
                    display.getLocationOnScreen().y,
                    display.getWidth(),
                    display.getHeight());
        } catch (IllegalComponentStateException error) {
            error.printStackTrace();
        }
    }

    public void componentMoved(ComponentEvent e) {
        try {
            ballSystem.setBounds(
                    display.getLocationOnScreen().x,
                    display.getLocationOnScreen().y,
                    display.getWidth(),
                    display.getHeight());
        } catch (IllegalComponentStateException error) {
            error.printStackTrace();
        }
    }

    public void componentResized(ComponentEvent e) {
        try {
            ballSystem.setBounds(
                    display.getLocationOnScreen().x,
                    display.getLocationOnScreen().y,
                    display.getWidth(),
                    display.getHeight());
        } catch (IllegalComponentStateException error) {
            error.printStackTrace();
        }
    }

    public void componentHidden(ComponentEvent e) {
    }
}

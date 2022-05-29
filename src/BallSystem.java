import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;

public class BallSystem {
    private int pickedIndex, toolState, nxtColor, nxtQ;
    private double xB1, xB2, yB1, yB2, gravity, drag;
    private double[] mosXY;
    private boolean nxtMoves, paused;
    private ArrayList<Ball> balls;
    private ArrayList<Color> colors;

    public BallSystem() {
        balls = new ArrayList<>();

        pickedIndex = -1;
        mosXY = new double[2];
        nxtMoves = true;

        String[] colorCodes = {"#f37474", "#f3bd75", "#f4e97b", "#a8f47b",
                "#b0f0dd", "#7cc7f4", "#dda8f7"};

        colors = new ArrayList<>();
        for (String s : colorCodes)
            colors.add(Color.decode(s));
    }

    public void evolve(double dt) {
        for (Ball a : balls) {
            if (a.moves) {
                if (a.picked) {
                    double xi = a.p[0];
                    double yi = a.p[1];

                    a.v[0] = (mosXY[0] - xi) / dt / 8;
                    a.v[1] = (mosXY[1] - yi) / dt / 8;
                }

                if (a.p[0] - a.r <= xB1)
                    a.v[0] += 1000 * (xB1 + a.r - a.p[0]) * dt;
                if (a.p[0] + a.r >= xB2)
                    a.v[0] -= 1000 * (a.p[0] + a.r - xB2) * dt;
                if (a.p[1] - a.r <= yB1)
                    a.v[1] += 1000 * (yB1 + a.r - a.p[1]) * dt;
                if (a.p[1] + a.r >= yB2)
                    a.v[1] -= 1000 * (a.p[1] + a.r - yB2) * dt;

                a.v[1] += gravity * 50 * dt;

                double[] Ftot = new double[2];

                for (Ball b : balls)
                    if (!a.equals(b)) {
                        if (Math.sqrt(a.distanceSqr(b.p)) <= a.r + b.r)
                            a.collidesWith(b);

                        Ftot[0] += a.forceFrom(b)[0];
                        Ftot[1] += a.forceFrom(b)[1];
                    }

                Ftot[0] -= 6 * 3.14 * a.r * drag * a.v[0];
                Ftot[1] -= 6 * 3.14 * a.r * drag * a.v[1];

                a.v[0] += Ftot[0] / a.m * dt;
                a.v[1] += Ftot[1] / a.m * dt;
            }
        }

        for (Ball a : balls) {
            a.p[0] = a.p[0] + a.v[0] * dt;
            a.p[1] = a.p[1] + a.v[1] * dt;
        }
    }

    public void reset() {
        pickedIndex = -1;
        balls = new ArrayList<>();
    }

    public void setBounds(int x, int y, int w, int h) {
        xB1 = x;
        yB1 = y;
        xB2 = x + w;
        yB2 = y + h;

        for (Ball a : balls) {
            if (a.p[0] + a.r >= xB2)
                a.p[0] = xB2 - a.r;
            if (a.p[0] - a.r <= xB1)
                a.p[0] = xB1 + a.r;
            if (a.p[1] + a.r >= yB2)
                a.p[1] = yB2 - a.r;
            if (a.p[1] - a.r <= yB1)
                a.p[1] = yB1 + a.r;
        }
    }

    public void setMosXY(Point p) {
        mosXY[0] = p.x;
        mosXY[1] = p.y;
    }

    public void setGravity(int g) {
        gravity = g;
    }

    public void setDrag(int d) {
        drag = d;
    }

    public void setToolState(int s) {
        toolState = s;
    }

    public int getToolState() {
        return toolState;
    }

    public void setNxtQ(int q) {
        nxtQ = q;
    }

    public void setNxtColor(int c) {
        nxtColor = c;
    }

    public void setNxtMoves(boolean m) {
        nxtMoves = m;
    }

    public void setPaused(boolean p) {
        paused = p;
    }

    public boolean isPaused() {
        return paused;
    }

    public void unPick() {
        if (pickedIndex > -1)
            balls.get(pickedIndex).picked = false;
    }

    public void addBall(double[] p, int r) {
        if (balls.size() <= 32)
            balls.add(new Ball(p, new double[]{0, 0},
                    nxtQ, r, nxtColor, nxtMoves));
    }

    public void pickBall(int x, int y) {
        mosXY[0] = x;
        mosXY[1] = y;

        for (Ball a : balls) {
            if (a.distanceSqr(mosXY) <= a.r * a.r) {
                a.picked = true;
                pickedIndex = balls.indexOf(a);
            }
        }
    }

    public void removeBall(int x, int y) {
        if (balls.size() > 0) {
            mosXY[0] = x;
            mosXY[1] = y;
            Iterator<Ball> iter = balls.iterator();
            while (iter.hasNext()) {
                Ball a = iter.next();

                if (a.distanceSqr(mosXY) <= a.r * a.r)
                    iter.remove();
            }
        }
    }

    public void paintBalls(Graphics2D g2d) {
        for (Ball a : balls) {
            int d = (int) (a.r * 2);
            int x = (int) (a.p[0] - xB1 - d / 2);
            int y = (int) (a.p[1] - yB1 - d / 2);
            g2d.setColor(colors.get(a.color));
            g2d.fill(new Ellipse2D.Double(x, y, d, d));
        }
    }
}

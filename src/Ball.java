
public class Ball {
    public int color;
    public double[] p, v;
    public double q, r, m;
    public boolean picked, moves;

    Ball(double[] p, double[] v, double q, double r, int c, boolean mv) {
        this.p = new double[]{p[0], p[1]};
        this.v = new double[]{v[0], v[1]};
        this.q = q;
        this.r = r;
        this.color = c;
        this.moves = mv;
        this.m = 3.1415 * r * r;
    }

    public double distanceSqr(double[] p2) {
        double dx = (p[0] - p2[0]);
        double dy = (p[1] - p2[1]);
        return dx * dx + dy * dy;
    }

    public void collidesWith(Ball b) {
        double amk = 2 * b.m / (m + b.m);
        double bmk = 2 * m / (m + b.m);
        double dpx = p[0] - b.p[0];
        double dpy = p[1] - b.p[1];
        double distance = dpx * dpx + dpy * dpy;
        double k = (dpx) * (v[0] - b.v[0]) + (dpy) * (v[1] - b.v[1]);

        p[0] = (b.r + r + 0.1) * dpx / Math.sqrt(distance) + b.p[0];
        p[1] = (b.r + r + 0.1) * dpy / Math.sqrt(distance) + b.p[1];

        double dvx = k / distance * dpx * 0.9;
        double dvy = k / distance * dpy * 0.9;

        v[0] -= amk * dvx;
        v[1] -= amk * dvy;

        if (b.moves) {
            b.v[0] += bmk * dvx;
            b.v[1] += bmk * dvy;
        }
    }

    public double[] forceFrom(Ball b) {
        double coulombC = 500000000;

        double dp = distanceSqr(b.p);
        double[] output = new double[2];
        output[0] = coulombC * (q * b.q / dp) * ((p[0] - b.p[0]) / Math.sqrt(dp));
        output[1] = coulombC * (q * b.q / dp) * ((p[1] - b.p[1]) / Math.sqrt(dp));

        return output;
    }
}

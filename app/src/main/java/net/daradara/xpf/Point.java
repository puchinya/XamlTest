package net.daradara.xpf;

import android.support.annotation.NonNull;

/**
 * Created by masatakanabeshima on 2016/02/12.
 */
public final class Point {
    public Point(double x, double y)
    {
        m_x = x;
        m_y = y;
    }

    public Point(@NonNull Point pt)
    {
        m_x = pt.m_x;
        m_y = pt.m_y;
    }

    public double getX() { return m_x; }
    public double getY() { return m_y; }

    private double m_x;
    private double m_y;
}

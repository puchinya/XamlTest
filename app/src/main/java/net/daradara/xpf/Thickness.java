package net.daradara.xpf;

/**
 * Created by masatakanabeshima on 2016/01/23.
 */
public final class Thickness {

    public Thickness(double uniformLength)
    {
        m_left = m_right = m_top = m_bottom = uniformLength;
    }

    public double getLeft()
    {
        return m_left;
    }

    public double getRight()
    {
        return m_right;
    }

    public double getTop()
    {
        return m_top;
    }

    public double getBottom()
    {
        return m_bottom;
    }

    private double m_left;
    private double m_right;
    private double m_top;
    private double m_bottom;
}

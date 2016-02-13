package net.daradara.xpf;

/**
 * Created by masatakanabeshima on 2016/01/23.
 */
public class Size {

    public Size()
    {
        m_width = 0.0;
        m_height = 0.0;
    }

    public Size(double width, double height)
    {
        m_width = width;
        m_height = height;

    }

    public static final Size EMPTY = new Size(Double.MIN_VALUE, Double.MIN_VALUE);

    public boolean isEmpty()
    {
        return m_width == Double.MIN_VALUE;
    }

    public double getWidth()
    {
        return m_width;
    }

    public double getHeight()
    {
        return m_height;
    }

    private double m_width;
    private double m_height;
}

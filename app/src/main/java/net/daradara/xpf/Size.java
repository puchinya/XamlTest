package net.daradara.xpf;

import android.support.annotation.NonNull;

/**
 * Created by masatakanabeshima on 2016/01/23.
 */
public final class Size {

    public Size(double width, double height)
    {
        m_width = width;
        m_height = height;
    }

    public Size(@NonNull Size size)
    {
        m_width = size.m_width;
        m_height = size.m_height;

    }

    public static final Size ZERO = new Size(0.0, 0.0);
    public static final Size INFINITY = new Size(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
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

    private final double m_width;
    private final double m_height;
}

package net.daradara.xpf;

import android.support.annotation.NonNull;

/**
 * Created by masatakanabeshima on 2016/02/13.
 */
public class Rect {
    public Rect(double x, double y, double width, double height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double x, y, width, height;

    public double getLeft()
    {
        return x;
    }

    public double getTop()
    {
        return y;
    }

    public double getRight()
    {
        return x + width;
    }

    public double getBottom()
    {
        return y + height;
    }

    public boolean getIsEmpty()
    {
        return x == Double.POSITIVE_INFINITY &&
                y == Double.POSITIVE_INFINITY &&
                width == Double.NEGATIVE_INFINITY &&
                height == Double.NEGATIVE_INFINITY;
    }

    public boolean equals(@NonNull Rect rect)
    {
        return x == rect.x &&
                y == rect.y &&
                width == rect.width &&
                height == rect.height;
    }

    public Rect clone()
    {
        return new Rect(x, y, width, height);
    }

    public static final Rect empty = new Rect(Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY,
            Double.NEGATIVE_INFINITY);
}

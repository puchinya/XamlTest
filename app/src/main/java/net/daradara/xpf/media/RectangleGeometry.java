package net.daradara.xpf.media;

import android.support.annotation.NonNull;

import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.PropertyMetadata;
import net.daradara.xpf.Rect;

/**
 * Created by masatakanabeshima on 2016/02/16.
 */
public final class RectangleGeometry extends Geometry {
    public RectangleGeometry()
    {

    }

    public RectangleGeometry(Rect rect)
    {
        setRect(rect);
    }

    public RectangleGeometry(Rect rect, double radiusX, double radiusY)
    {
        setRect(rect);
        setRadiusX(radiusX);
        setRadiusY(radiusY);
    }

    @Override
    public @NonNull Rect getBounds()
    {
        return getRect();
    }

    public @NonNull Rect getRect()
    {
        return (Rect)getValue(rectProperty);
    }

    public void setRect(@NonNull Rect value)
    {
        setValue(rectProperty, value);
    }

    public double getRadiusX()
    {
        return ((Double)getValue(radiusXProperty)).doubleValue();
    }

    public void setRadiusX(double value)
    {
        setValue(radiusXProperty, Double.valueOf(value));
    }

    public double getRadiusY()
    {
        return ((Double)getValue(radiusYProperty)).doubleValue();
    }

    public void setRadiusY(double value)
    {
        setValue(radiusYProperty, Double.valueOf(value));
    }

    public static final DependencyProperty rectProperty = DependencyProperty.register("rect", Rect.class,
            RectangleGeometry.class, new PropertyMetadata(Rect.empty));
    public static final DependencyProperty radiusXProperty = DependencyProperty.register("radiusX", Double.class,
            RectangleGeometry.class, new PropertyMetadata(Double.valueOf(0.0)));
    public static final DependencyProperty radiusYProperty = DependencyProperty.register("radiusY", Double.class,
            RectangleGeometry.class, new PropertyMetadata(Double.valueOf(0.0)));
}

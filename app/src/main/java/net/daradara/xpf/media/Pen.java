package net.daradara.xpf.media;

import android.support.annotation.Nullable;

import net.daradara.xpf.DependencyObject;
import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.PropertyMetadata;

/**
 * Created by masatakanabeshima on 2016/02/12.
 */
public class Pen extends DependencyObject {

    public Pen()
    {

    }

    public Pen(Brush brush, double thickness)
    {
        setBrush(brush);
        setThickness(thickness);
    }

    public @Nullable Brush getBrush()
    {
        return (Brush)getValue(brushProperty);
    }

    public void setBrush(@Nullable Brush value)
    {
        setValue(brushProperty, value);
    }

    public double getThickness()
    {
        return ((Double)getValue(thicknessProperty)).doubleValue();
    }

    public void setThickness(double value)
    {
        setValue(thicknessProperty, new Double(value));
    }

    public static final DependencyProperty brushProperty = DependencyProperty.register("brush",
            Brush.class, Pen.class, new PropertyMetadata(null));
    public static final DependencyProperty thicknessProperty = DependencyProperty.register("thickness",
            Double.class, Pen.class, new PropertyMetadata(new Double(1.0)));
}

package net.daradara.xpf.shapes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.FrameworkElement;
import net.daradara.xpf.PropertyMetadata;
import net.daradara.xpf.Size;
import net.daradara.xpf.Thickness;
import net.daradara.xpf.media.Brush;

/**
 * Created by masatakanabeshima on 2016/02/14.
 */
public abstract class Shape extends FrameworkElement {
    protected Shape()
    {

    }

    public final void setFill(@Nullable Brush value)
    {
        setValue(fillProperty, value);
    }

    public final @Nullable Brush getFill()
    {
        return (Brush)getValue(fillProperty);
    }

    public final void setStroke(@Nullable Brush value)
    {
        setValue(strokeProperty, value);
    }

    public final @Nullable Brush getStroke()
    {
        return (Brush)getValue(strokeProperty);
    }

    public final void setStrokeThickness(double value)
    {
        setValue(strokeThicknessProperty, Double.valueOf(value));
    }

    public final double getStrokeThickness()
    {
        return ((Double)getValue(strokeThicknessProperty)).doubleValue();
    }

    public static final DependencyProperty fillProperty = DependencyProperty.register("fill", Brush.class,
            Shape.class, new PropertyMetadata(null));

    public static final DependencyProperty strokeProperty = DependencyProperty.register("stroke", Brush.class,
            Shape.class, new PropertyMetadata(null));

    public static final DependencyProperty strokeThicknessProperty = DependencyProperty.register("strokeThickness", Double.class,
            Shape.class, new PropertyMetadata(Double.valueOf(1.0)));
}

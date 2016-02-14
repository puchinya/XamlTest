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

    @Override
    protected @NonNull Size measureOverride(@NonNull Size availableSize)
    {
        return availableSize;
    }

    public void setFill(@Nullable Brush value)
    {
        setValue(fillProperty, value);
    }

    public @Nullable Brush getFill()
    {
        return (Brush)getValue(fillProperty);
    }

    public void setStroke(@Nullable Brush value)
    {
        setValue(strokeProperty, value);
    }

    public @Nullable Brush getStroke()
    {
        return (Brush)getValue(strokeProperty);
    }

    public void setStrokeThickness(double value)
    {
        setValue(strokeThicknessProperty, Double.valueOf(value));
    }

    public double getStrokeThickness()
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

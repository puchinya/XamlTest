package net.daradara.xpf.media;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.PropertyMetadata;
import net.daradara.xpf.Rect;

/**
 * Created by masatakanabeshima on 2016/02/16.
 */
public final class GeometryDrawing extends Drawing {
    public GeometryDrawing()
    {

    }

    public GeometryDrawing(@Nullable Brush brush,
                           @Nullable Pen pen,
                           @Nullable Geometry geometry)
    {
        setBrush(brush);
        setPen(pen);
        setGeometry(geometry);
    }

    public @Nullable Brush getBrush()
    {
        return (Brush)getValue(brushProperty);
    }

    public void setBrush(@Nullable Brush value)
    {
        setValue(brushProperty, value);
    }

    public @Nullable Pen getPen()
    {
        return (Pen)getValue(penProperty);
    }

    public void setPen(@Nullable Pen value)
    {
        setValue(penProperty, value);
    }

    public @Nullable Geometry getGeometry()
    {
        return (Geometry)getValue(geometryProperty);
    }

    public void setGeometry(@Nullable Geometry value)
    {
        setValue(geometryProperty, value);
    }

    public static final DependencyProperty brushProperty = DependencyProperty.register("brush", Brush.class,
            GeometryDrawing.class, new PropertyMetadata(null));
    public static final DependencyProperty penProperty = DependencyProperty.register("pen", Pen.class,
            GeometryDrawing.class, new PropertyMetadata(null));
    public static final DependencyProperty geometryProperty = DependencyProperty.register("geometry", Geometry.class,
            GeometryDrawing.class, new PropertyMetadata(null));
}

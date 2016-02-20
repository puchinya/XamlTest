package net.daradara.xpf.controls;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.FrameworkElement;
import net.daradara.xpf.PropertyMetadata;
import net.daradara.xpf.Rect;
import net.daradara.xpf.Size;
import net.daradara.xpf.Thickness;
import net.daradara.xpf.UIElement;
import net.daradara.xpf.media.Brush;
import net.daradara.xpf.media.DrawingContext;
import net.daradara.xpf.media.Pen;
import net.daradara.xpf.Visual;

/**
 * Created by masatakanabeshima on 2016/02/14.
 */
public class Border extends FrameworkElement {
    public Border()
    {

    }

    @Override
    public void onRender(DrawingContext drawingContext)
    {
        Pen pen = new Pen(getBorderBrush(), getBorderThickness());
        Size size = getDesiredSize();

        drawingContext.drawRectangle(getBackground(), pen,
                new Rect(0.0, 0.0, size.getWidth(), size.getHeight()));
    }

    @Override
    public int getVisualChildrenCount()
    {
        return m_child != null ? 1 : 0;
    }

    @Override
    public @NonNull Visual getVisualChild(int index)
    {
        if(m_child == null) {
            throw new IndexOutOfBoundsException();
        }

        return m_child;
    }

    public @Nullable UIElement getChild()
    {
        return m_child;
    }

    public void setChild(@Nullable UIElement value)
    {
        if(m_child == value)
            return;

        if(m_child != null) {
            removeLogicalChild(m_child);
            removeVisualChild(m_child);
        }

        m_child = value;

        if(m_child != null) {
            addLogicalChild(m_child);
            addVisualChild(m_child);
        }

        invalidateMeasure();
    }

    public @Nullable Brush getBackground()
    {
        return (Brush)getValue(backgroundProperty);
    }

    public void setBackground(@Nullable Brush value)
    {
        setValue(backgroundProperty, value);
    }

    public @Nullable Brush getBorderBrush()
    {
        return (Brush)getValue(borderBrushProperty);
    }

    public void setBorderBrush(@Nullable Brush value)
    {
        setValue(borderBrushProperty, value);
    }

    public double getBorderThickness()
    {
        return ((Double)getValue(borderThicknessProperty)).doubleValue();
    }

    public void setBorderThickness(double value)
    {
        setValue(borderThicknessProperty, Double.valueOf(value));
    }

    private UIElement m_child;

    public static final DependencyProperty backgroundProperty = DependencyProperty.register("background",
            Brush.class, Control.class, new PropertyMetadata(null));

    public static final DependencyProperty borderBrushProperty = DependencyProperty.register("borderBrush",
            Brush.class, Control.class, new PropertyMetadata(null));

    public static final DependencyProperty borderThicknessProperty = DependencyProperty.register("borderThickness",
            Thickness.class, Control.class, new PropertyMetadata(new Thickness(0.0)));
}

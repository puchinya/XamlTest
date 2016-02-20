package net.daradara.xpf.controls;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.FrameworkElement;
import net.daradara.xpf.HorizontalAlignment;
import net.daradara.xpf.PropertyMetadata;
import net.daradara.xpf.Thickness;
import net.daradara.xpf.VerticalAlignment;
import net.daradara.xpf.media.Brush;

/**
 * Created by masatakanabeshima on 2016/02/13.
 */
public class Control extends FrameworkElement {
    public Control()
    {

    }

    public @Nullable Brush getBackground()
    {
        return (Brush)getValue(backgroundProperty);
    }

    public void setBackground(@Nullable Brush value)
    {
        setValue(backgroundProperty, value);
    }

    public @NonNull Thickness getPadding()
    {
        return (Thickness)getValue(paddingProperty);
    }

    public void setPadding(@NonNull Thickness value)
    {
        setValue(paddingProperty, value);
    }

    public HorizontalAlignment getHorizontalContentAlignment()
    {
        return (HorizontalAlignment)getValue(horizontalContentAlignmentProperty);
    }

    public void setHorizontalContentAlignment(HorizontalAlignment value) {
        setValue(horizontalContentAlignmentProperty, value);
    }

    public VerticalAlignment getVerticalContentAlignment()
    {
        return (VerticalAlignment)getValue(verticalContentAlignmentProperty);
    }

    public void setVerticalContentAlignment(VerticalAlignment value) {
        setValue(verticalContentAlignmentProperty, value);
    }

    public static final DependencyProperty backgroundProperty = DependencyProperty.register("background",
            Brush.class, Control.class, new PropertyMetadata(null));

    public static final DependencyProperty paddingProperty = DependencyProperty.register("padding",
            Thickness.class, Control.class, new PropertyMetadata(new Thickness(0.0)));

    public static final DependencyProperty horizontalContentAlignmentProperty = DependencyProperty.register("horizontalContentAlignment",
            HorizontalAlignment.class, Control.class, new PropertyMetadata(HorizontalAlignment.LEFT));

    public static final DependencyProperty verticalContentAlignmentProperty = DependencyProperty.register("verticalContentAlignment",
            VerticalAlignment.class, Control.class, new PropertyMetadata(VerticalAlignment.TOP));
}

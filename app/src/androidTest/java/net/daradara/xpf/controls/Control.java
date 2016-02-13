package net.daradara.xpf.controls;

import android.support.annotation.Nullable;

import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.FrameworkElement;
import net.daradara.xpf.PropertyMetadata;
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

    public void setBackground(Brush value)
    {
        setValue(backgroundProperty, value);
    }

    public static final DependencyProperty backgroundProperty = DependencyProperty.register("background",
            Brush.class, Control.class, new PropertyMetadata(null));

}

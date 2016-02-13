package net.daradara.xpf.media;

import android.support.annotation.Nullable;

import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.PropertyMetadata;

/**
 * Created by masatakanabeshima on 2016/02/12.
 */
public class SolidColorBrush extends Brush {

    public SolidColorBrush(@Nullable Color color)
    {
        setColor(color);
    }

    public SolidColorBrush()
    {

    }

    public @Nullable Color getColor()
    {
        return (Color)getValue(colorProperty);
    }

    public void setColor(@Nullable Color value)
    {
        setValue(colorProperty, value);
    }

    public static final DependencyProperty colorProperty = DependencyProperty.register("color",
            Color.class, SolidColorBrush.class, new PropertyMetadata(null));
}

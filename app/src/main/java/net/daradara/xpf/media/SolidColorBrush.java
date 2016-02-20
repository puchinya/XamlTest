package net.daradara.xpf.media;

import android.support.annotation.Nullable;

import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.PropertyMetadata;

/**
 * Created by masatakanabeshima on 2016/02/12.
 */
public final class SolidColorBrush extends Brush implements Cloneable {

    public SolidColorBrush(@Nullable Color color)
    {
        setColor(color);
    }

    public SolidColorBrush()
    {

    }

    @Override
    public SolidColorBrush clone()
    {
        SolidColorBrush r = null;

        try {
            r = (SolidColorBrush)super.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
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

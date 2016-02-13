package net.daradara.xpf.media;

import net.daradara.xpf.DependencyObject;
import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.PropertyMetadata;

/**
 * Created by masatakanabeshima on 2016/02/12.
 */
public abstract class Brush extends DependencyObject {

    protected Brush()
    {

    }

    public double getOpacity()
    {
        return ((Double)getValue(opacityProperty)).doubleValue();
    }

    public void setOpacity(double value)
    {
        setValue(opacityProperty, new Double(value));
    }

    public static final DependencyProperty opacityProperty = DependencyProperty.register("opacity",
            Double.TYPE, Brush.class, new PropertyMetadata(new Double(100.0)));

}

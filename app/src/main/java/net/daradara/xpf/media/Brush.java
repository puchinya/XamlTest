package net.daradara.xpf.media;

import net.daradara.xpf.DependencyObject;
import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.PropertyMetadata;

/**
 * Created by masatakanabeshima on 2016/02/12.
 */
public abstract class Brush extends DependencyObject implements Cloneable {

    protected Brush()
    {

    }

    @Override
    public Brush clone()
    {
        Brush r = null;

        try {
            r = (Brush)super.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    public final double getOpacity()
    {
        return ((Double)getValue(opacityProperty)).doubleValue();
    }

    public final void setOpacity(double value)
    {
        setValue(opacityProperty, new Double(value));
    }

    public static final DependencyProperty opacityProperty = DependencyProperty.register("opacity",
            Double.class, Brush.class, new PropertyMetadata(new Double(100.0)));

}

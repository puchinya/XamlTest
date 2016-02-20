package net.daradara.xpf.media;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.Point;
import net.daradara.xpf.PropertyMetadata;
import net.daradara.xpf.Rect;

/**
 * Created by masatakanabeshima on 2016/02/16.
 */
public class DrawingGroup extends Drawing {

    public DrawingGroup()
    {

    }

    public final @NonNull DrawingCollection getChildren()
    {
        return (DrawingCollection)getValue(childrenProperty);
    }

    public final void setChildren(@NonNull DrawingCollection value)
    {
        setValue(childrenProperty, value);
    }

    public final @NonNull DrawingContext append()
    {
        return new DrawingGroupDrawingContext(this);
    }

    public static final DependencyProperty childrenProperty = DependencyProperty.register("children",
            DrawingCollection.class,
            DrawingGroup.class, new PropertyMetadata(new DrawingCollection()));
}

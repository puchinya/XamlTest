package net.daradara.xpf.media;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.daradara.xpf.Point;
import net.daradara.xpf.Rect;
import net.daradara.xpf.DispatcherObject;

/**
 * Created by masatakanabeshima on 2016/01/23.
 */
public abstract class DrawingContext extends DispatcherObject {
    protected DrawingContext()
    {

    }

    public abstract void close();
    public abstract void drawLine(@Nullable Pen pen, @NonNull Point point0, @NonNull Point point1);
    public abstract void drawRectangle(@Nullable Brush brush, @Nullable Pen pen,
                                       @NonNull Rect rect);
}

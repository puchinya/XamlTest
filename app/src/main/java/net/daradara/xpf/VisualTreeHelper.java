package net.daradara.xpf;

import android.support.annotation.NonNull;

import net.daradara.xpf.UIElement;
import net.daradara.xpf.Visual;
import net.daradara.xpf.media.DrawingGroup;
import net.daradara.xpf.media.DrawingVisual;

/**
 * Created by masatakanabeshima on 2016/02/17.
 */
public final class VisualTreeHelper {

    public static @NonNull
    Visual getChild(@NonNull Visual visual, int index)
    {
        return visual.getVisualChild(index);
    }

    public static int getChildrenCount(@NonNull Visual visual)
    {
        return visual.getVisualChildrenCount();
    }

    public static @NonNull
    DrawingGroup getDrawing(@NonNull Visual visual)
    {
        DrawingGroup drawingGroup = new DrawingGroup();

        if(visual instanceof DrawingVisual) {
            return ((DrawingVisual)visual).getDrawing();
        } else if(visual instanceof UIElement) {
            return ((UIElement)visual).getDrawing();
        }

        return null;
    }
}

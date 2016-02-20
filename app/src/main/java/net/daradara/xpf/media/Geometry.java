package net.daradara.xpf.media;

import android.support.annotation.NonNull;

import net.daradara.xpf.DependencyObject;
import net.daradara.xpf.Rect;

/**
 * Created by masatakanabeshima on 2016/02/16.
 */
public abstract class Geometry extends DependencyObject {

    protected Geometry()
    {

    }

    public abstract @NonNull Rect getBounds();

}

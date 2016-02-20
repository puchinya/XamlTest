package net.daradara.xpf;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by masatakanabeshima on 2016/02/11.
 */
public abstract class Visual extends DependencyObject {

    protected Visual()
    {

    }

    public @NonNull Vector getVisualOffset()
    {
        return m_visualOffset;
    }

    public void setVisualOffset(@NonNull Vector value)
    {
        m_visualOffset = value;
    }

    public @Nullable Visual getVisualParent()
    {
        return m_visualParent;
    }

    public int getVisualChildrenCount()
    {
        return 0;
    }

    public @NonNull Visual getVisualChild(int index)
    {
        throw new IndexOutOfBoundsException();
    }

    protected void addVisualChild(@NonNull Visual visual)
    {
        if(visual == null) {
            throw new IllegalArgumentException();
        }

        Visual oldVisualParent = visual.m_visualParent;
        if(oldVisualParent != null) {
            oldVisualParent.removeVisualChild(visual);
        }
        visual.m_visualParent = this;
        onVisualParentChanged(oldVisualParent);
        if(visual != null) {
            onVisualChildrenChanged(visual, null);
        }
    }

    protected void removeVisualChild(@NonNull Visual visual)
    {
        if(visual == null) {
            throw new IllegalArgumentException();
        }
        Visual oldVisualParent = visual.m_visualParent;
        visual.m_visualParent = null;
        onVisualParentChanged(oldVisualParent);
        onVisualChildrenChanged(null, visual);
    }

    protected void onVisualParentChanged(@Nullable DependencyObject oldParent)
    {

    }

    protected void onVisualChildrenChanged(@Nullable DependencyObject visualAdded,
                                           @Nullable DependencyObject visualRemoved)
    {

    }

    protected @Nullable Visual hitTestCore(@NonNull Point point)
    {
        return null;
    }

    private Visual m_visualParent = null;
    private Vector m_visualOffset = new Vector(0.0, 0.0);
}

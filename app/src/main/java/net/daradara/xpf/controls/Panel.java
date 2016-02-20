package net.daradara.xpf.controls;

import android.support.annotation.NonNull;

import net.daradara.xpf.FrameworkElement;
import net.daradara.xpf.UIElementCollection;
import net.daradara.xpf.Visual;

import java.util.Iterator;

/**
 * Created by masatakanabeshima on 2016/02/20.
 */
public abstract class Panel extends FrameworkElement {

    protected Panel()
    {

    }

    public int getVisualChildrenCount()
    {
        return m_children.size();
    }

    public @NonNull Visual getVisualChild(int index)
    {
        return m_children.get(index);
    }

    public @NonNull UIElementCollection getChildren()
    {
        return m_children;
    }

    protected final @NonNull Iterator getLogicalChildren() {
        return m_children.iterator();
    }

    private UIElementCollection m_children = new UIElementCollection(this, this);
}

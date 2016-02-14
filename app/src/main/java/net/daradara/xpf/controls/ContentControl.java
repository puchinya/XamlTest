package net.daradara.xpf.controls;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.PropertyMetadata;
import net.daradara.xpf.Size;
import net.daradara.xpf.UIElement;
import net.daradara.xpf.media.Visual;

/**
 * Created by masatakanabeshima on 2016/02/13.
 */
public class ContentControl extends Control {
    public ContentControl()
    {

    }

    @Override
    public int getVisualChildrenCount()
    {
        return getHasContent() ? 1 : 0;
    }

    @Override
    public @NonNull Visual getVisualChild(int index)
    {
        if(!getHasContent()) {
            throw new IndexOutOfBoundsException();
        }

        return m_child;
    }

    @Override
    protected @NonNull Size measureOverride(@NonNull Size availableSize)
    {
        Size desiredSize = null;
        if(m_child != null) {
            m_child.measure(availableSize);
            desiredSize = m_child.getDesiredSize();
        } else {
            desiredSize = new Size();
        }
        return desiredSize;
    }

    @Override
    protected void onPropertyChanged(@NonNull DependencyProperty dp,
                                     @Nullable Object oldValue, @Nullable Object newValue)
    {
        super.onPropertyChanged(dp, oldValue, newValue);

        if(dp == contentProperty && oldValue != newValue) {
            if(oldValue != null) {
                removeLogicalChild(oldValue);
            }

            if(newValue != null) {
                addLogicalChild(newValue);
            }

            // Update hasContent dependency property
            setValue(hasContentProperty, Boolean.valueOf(newValue != null));

            if(newValue == null) {
                m_child = null;
            } else if(newValue instanceof UIElement) {
                m_child = (UIElement)newValue;
            } else {
                // pending
                throw new IllegalArgumentException();
            }

            invalidateMeasure();
        }
    }

    public @Nullable Object getContent()
    {
        return getValue(contentProperty);
    }

    public void setContent(@Nullable Object value)
    {
        setValue(contentProperty, value);
    }

    public boolean getHasContent()
    {
        return ((Boolean)getValue(hasContentProperty)).booleanValue();
    }

    private UIElement m_child = null;

    public static final DependencyProperty contentProperty = DependencyProperty.register("content",
            Object.class, ContentControl.class, new PropertyMetadata(null));

    public static final DependencyProperty contentStringFormatProperty = DependencyProperty.register("contentStringFormat",
            String.class, ContentControl.class, new PropertyMetadata(null));

    public static final DependencyProperty hasContentProperty = DependencyProperty.register("hasContent",
            Boolean.class, ContentControl.class, new PropertyMetadata(Boolean.FALSE));
}

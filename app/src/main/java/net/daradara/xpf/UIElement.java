package net.daradara.xpf;

import android.support.annotation.NonNull;

import net.daradara.xpf.delegate.Delegate;
import net.daradara.xpf.media.Visual;
import net.daradara.xpf.media.DrawingContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by masatakanabeshima on 2016/01/23.
 */
public class UIElement extends Visual {

    public void onRender(DrawingContext drawingContext)
    {

    }

    public boolean getIsEnabled()
    {
        return ((Boolean)getValue(isEnabledProperty)).booleanValue();
    }

    public void setIsEnabled(boolean value)
    {
        setValue(isEnabledProperty, Boolean.valueOf(value));
    }

    public boolean getIsHitTestVisible()
    {
        return ((Boolean)getValue(isHitTestVisibleProperty)).booleanValue();
    }

    public void setIsHitTestVisible(boolean value)
    {
        setValue(isHitTestVisibleProperty, Boolean.valueOf(value));
    }

    public Visibility getVisibility()
    {
        return (Visibility)getValue(visibilityProperty);
    }

    public void setVisibility(Visibility value)
    {
        setValue(visibilityProperty, value);
    }

    public Size getDesiredSize()
    {
        return m_desiredSize;
    }

    public void AddHandler(RoutedEvent routedEvent, Delegate handler)
    {
        m_handlers.put(routedEvent, handler);
    }

    public void measure(@NonNull Size availableSize)
    {
        if(m_isMeasureValid) {
            return;
        }

        if(getVisibility() == Visibility.COLLAPSED) {
            m_desiredSize = new Size();
        } else {
            m_desiredSize = measureCore(availableSize);
        }
        m_isMeasureValid = true;
    }

    protected @NonNull Size measureCore(@NonNull Size availableSize)
    {
        return new Size();
    }

    public void invalidateArrange()
    {
        m_isArrangeValid = false;
        for(int i = 0; i < getVisualChildrenCount(); i++) {
            Visual child = getVisualChild(i);
            if(child instanceof UIElement) {
                ((UIElement)child).invalidateArrange();
            }
        }

    }

    public void invalidateMeasure()
    {
        m_isMeasureValid = false;
        for(int i = 0; i < getVisualChildrenCount(); i++) {
            Visual child = getVisualChild(i);
            if(child instanceof UIElement) {
                ((UIElement)child).invalidateMeasure();
            }
        }
        invalidateArrange();
    }

    public void invalidateVisual()
    {
        invalidateArrange();
    }

    protected DependencyObject getUIParentCore()
    {
        return null;
    }

    private void processEvent(RoutedEventArgs e)
    {
        RoutedEvent routedEvent = e.getRoutedEvent();
        if(m_handlers.containsKey(routedEvent)) {
            Delegate d = m_handlers.get(routedEvent);
            d.invoke(e);
        }

        if(routedEvent.getRoutingStrategy() == RoutingStrategy.BUBBLE) {
            DependencyObject parent = getUIParentCore();
            if (parent != null) {
                if (parent instanceof UIElement) {
                    UIElement parentElement = (UIElement)parent;
                    parentElement.processEvent(e);
                }
            }
        }
    }

    public void raiseEvent(RoutedEventArgs e)
    {
        processEvent(e);
    }

    public static final DependencyProperty isEnabledProperty = DependencyProperty.register("isEnabled", Boolean.TYPE,
            UIElement.class, new PropertyMetadata(Boolean.TRUE));

    public static final DependencyProperty isHitTestVisibleProperty = DependencyProperty.register("isHitTestVisible", Boolean.TYPE,
            UIElement.class, new PropertyMetadata(Boolean.TRUE));

    public static final DependencyProperty visibilityProperty = DependencyProperty.register("visibility", Visibility.class,
            UIElement.class, new PropertyMetadata(Visibility.VISIBLE));

    private Map<RoutedEvent, Delegate> m_handlers = new HashMap<>();

    private Size m_desiredSize;
    private boolean m_isArrangeValid = false;
    private boolean m_isMeasureValid = false;
    private boolean m_isVisualValid = false;
}

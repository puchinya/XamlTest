package net.daradara.xpf;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.daradara.xpf.android.ElementHost;
import net.daradara.xpf.delegate.Delegate;
import net.daradara.xpf.delegate.Func;
import net.daradara.xpf.media.DrawingGroup;
import net.daradara.xpf.media.DrawingContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by masatakanabeshima on 2016/01/23.
 */
public class UIElement extends Visual {

    protected void onRender(DrawingContext drawingContext)
    {

    }

    public final boolean getIsEnabled()
    {
        return ((Boolean)getValue(isEnabledProperty)).booleanValue();
    }

    public final void setIsEnabled(boolean value)
    {
        setValue(isEnabledProperty, Boolean.valueOf(value));
    }

    public final boolean getIsHitTestVisible()
    {
        return ((Boolean)getValue(isHitTestVisibleProperty)).booleanValue();
    }

    public final void setIsHitTestVisible(boolean value)
    {
        setValue(isHitTestVisibleProperty, Boolean.valueOf(value));
    }

    public final Visibility getVisibility()
    {
        return (Visibility)getValue(visibilityProperty);
    }

    public final void setVisibility(Visibility value)
    {
        setValue(visibilityProperty, value);
    }

    public final Size getDesiredSize()
    {
        return m_desiredSize;
    }

    public final void AddHandler(RoutedEvent routedEvent, Delegate handler)
    {
        m_handlers.put(routedEvent, handler);
    }

    public final void measure(@NonNull Size availableSize)
    {
        Size desiredSize;

        if(getVisibility() == Visibility.COLLAPSED) {
            desiredSize = Size.ZERO;
        } else {
            desiredSize = measureCore(availableSize);
        }

        m_desiredSize = desiredSize;

        m_isMeasureValid = true;
    }

    protected @NonNull Size measureCore(@NonNull Size availableSize)
    {
        return Size.ZERO;
    }

    public final void arrange(@NonNull Rect finalRect)
    {
        if(getVisibility() != Visibility.COLLAPSED) {
            arrangeCore(finalRect);
        }

        m_isArrangeValid = true;

        render();
    }

    protected void arrangeCore(@NonNull Rect finalRect)
    {

    }

    public final void invalidateArrange()
    {
        m_isArrangeValid = false;
        getDispatcher().beginInvoke(new Func<Void, UIElement>() {
            @Override
            public Void invokeOverride(UIElement element) {
                ElementHost.ElementHostRoot root = ElementHost.ElementHostRoot.getElementHostRoot(element);
                root.doArrange();
                return null;
            }
        }, new Object[]{this});
    }

    public final void invalidateMeasure()
    {
        m_isMeasureValid = false;
        invalidateArrange();
    }

    public final void invalidateVisual()
    {
        m_isVisualValid = false;
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

    private void render()
    {
        DrawingGroup drawing = new DrawingGroup();
        if(getVisibility() == Visibility.VISIBLE) {
            onRender(drawing.append());
        }

        m_drawing = drawing;
        m_isVisualValid = true;
    }

    /**
     * Cannot use
     * @return
     */
    public final DrawingGroup getDrawing() {
        return m_drawing;
    }

    public static final DependencyProperty isEnabledProperty = DependencyProperty.register("isEnabled", Boolean.class,
            UIElement.class, new PropertyMetadata(Boolean.TRUE));

    public static final DependencyProperty isHitTestVisibleProperty = DependencyProperty.register("isHitTestVisible", Boolean.class,
            UIElement.class, new PropertyMetadata(Boolean.TRUE));

    public static final DependencyProperty visibilityProperty = DependencyProperty.register("visibility", Visibility.class,
            UIElement.class, new PropertyMetadata(Visibility.VISIBLE));

    private Map<RoutedEvent, Delegate> m_handlers = new HashMap<>();

    private Size m_desiredSize;
    private boolean m_isArrangeValid = false;
    private boolean m_isMeasureValid = false;
    private boolean m_isVisualValid = false;
    private DrawingGroup m_drawing = null;
}

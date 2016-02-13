package net.daradara.xpf;

import android.support.annotation.NonNull;

import net.daradara.xpf.media.Visual;
import net.daradara.xpf.media.DrawingContext;

/**
 * Created by masatakanabeshima on 2016/01/23.
 */
public class UIElement extends Visual {

    public void onRender(DrawingContext drawingContext)
    {

    }

    public void measure(@NonNull Size availableSize)
    {
        m_desiredSize = measureCore(availableSize);
    }

    protected Size measureCore(@NonNull Size availableSize)
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

    private Size m_desiredSize;
    private boolean m_isArrangeValid = false;
    private boolean m_isMeasureValid = false;
    private boolean m_isVisualValid = false;
}

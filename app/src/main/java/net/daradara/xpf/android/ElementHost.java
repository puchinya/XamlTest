package net.daradara.xpf.android;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import net.daradara.xpf.Rect;
import net.daradara.xpf.UIElement;
import net.daradara.xpf.controls.ContentControl;
import net.daradara.xpf.media.DrawingGroup;
import net.daradara.xpf.Visual;
import net.daradara.xpf.VisualTreeHelper;

/**
 * Created by masatakanabeshima on 2016/02/12.
 */
public class ElementHost extends View
{
    public ElementHost(@NonNull Context context)
    {
        super(context);
    }

    public ElementHost(@NonNull Context context,
                       @NonNull AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public ElementHost(Context context,
                       AttributeSet attrs,
                       int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        if(m_host != null) {
            m_host.onSizeChanged(w, h);
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas)
    {
        super.onDraw(canvas);
        if(m_content != null) {
            AndroidDrawingContext drawingContext = new AndroidDrawingContext(canvas);
            drawVisual(drawingContext, m_content);
        }
    }

    private void drawVisualChildren(@NonNull AndroidDrawingContext drawingContext, @NonNull Visual parent)
    {
        for(int i = 0; i < VisualTreeHelper.getChildrenCount(parent); i++) {
            Visual child = VisualTreeHelper.getChild(parent, i);
            drawVisual(drawingContext, child);
        }
    }

    private void drawVisual(@NonNull AndroidDrawingContext drawingContext, @NonNull Visual visual)
    {
        drawVisualCore(drawingContext, visual);
        drawVisualChildren(drawingContext, visual);
    }

    private void drawVisualCore(@NonNull AndroidDrawingContext drawingContext, @NonNull Visual visual)
    {
        DrawingGroup drawing = VisualTreeHelper.getDrawing(visual);
        drawingContext.pushOffset(visual.getVisualOffset());
        drawingContext.drawDrawing(drawing);
        drawingContext.popOffset();
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();


        return true;
    }

    public void setContent(UIElement value)
    {
        m_content = value;
        m_host = new ElementHostRoot(this, value);
        invalidate();
    }

    public static class ElementHostRoot extends ContentControl
    {
        public ElementHostRoot(View view, UIElement content)
        {
            m_view = view;
            setContent(content);
        }

        protected void onSizeChanged(double newWidth, double newHeight)
        {
            setWidth(newWidth);
            setHeight(newHeight);
        }

        protected final void onContentRendered()
        {
            m_view.invalidate();
        }

        public final void doArrange()
        {
            onContentRendered();
            m_view.invalidate();
        }

        public static ElementHostRoot getElementHostRoot(Visual visual)
        {
            if(visual instanceof ElementHostRoot)
                return (ElementHostRoot)visual;

            return getElementHostRoot(visual.getVisualParent());
        }

        private View m_view;
    }

    public UIElement getContent()
    {
        return m_content;
    }

    private UIElement m_content = null;
    private ElementHostRoot m_host = null;
}

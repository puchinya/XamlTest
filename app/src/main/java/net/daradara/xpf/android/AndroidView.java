package net.daradara.xpf.android;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import net.daradara.xpf.Size;
import net.daradara.xpf.UIElement;
import net.daradara.xpf.media.Visual;

/**
 * Created by masatakanabeshima on 2016/02/12.
 */
public class AndroidView extends View
{
    public AndroidView(Context context)
    {
        super(context);
    }

    public AndroidView(Context context,
                       AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public AndroidView(Context context,
                       AttributeSet attrs,
                       int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        if(m_content != null) {
            m_content.measure(new Size(w, h));
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(m_content != null) {
            drawVisual(m_content, canvas);
        }
    }

    private void drawVisual(@NonNull Visual visual, @NonNull Canvas canvas)
    {
        if(visual instanceof UIElement) {
            UIElement element = (UIElement)visual;
            element.onRender(new AndroidDrawingContext(canvas, element));
        }

        for(int i = 0; i < visual.getVisualChildrenCount(); i++)
        {
            Visual child = visual.getVisualChild(i);
            drawVisual(child, canvas);
        }
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
        invalidate();
    }

    public UIElement getContent()
    {
        return m_content;
    }

    private UIElement m_content = null;
}

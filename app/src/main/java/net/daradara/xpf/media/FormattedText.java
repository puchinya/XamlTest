package net.daradara.xpf.media;

import android.graphics.Paint;
import android.support.annotation.NonNull;

/**
 * Created by masatakanabeshima on 2016/02/13.
 */
public class FormattedText {

    public FormattedText(String text, Typeface typeface,
                         double emSize, Brush foreground)
    {
        m_text = text;
        m_emSize = emSize;
        m_foreground = foreground;
    }

    public double getWidth()
    {
        measure();
        return m_width;
    }

    public double getHeight()
    {
        measure();
        return m_height;
    }

    private void measure()
    {
        if(m_isMeasureValid)
            return;

        Paint paint = new Paint();
        paint.setTextSize((float)m_emSize);
        float textWidth = paint.measureText(getText());
        Paint.FontMetrics fm = paint.getFontMetrics();
        float textHeight = Math.abs(fm.top) + fm.bottom;

        m_width = textWidth;
        m_height = textHeight;
        m_isMeasureValid = true;
    }

    private @NonNull String getText() {
        return m_text;
    }

    public String m_text;
    public Brush m_foreground;
    private double m_width;
    private double m_height;
    private boolean m_isMeasureValid = false;
    public double m_emSize;
}


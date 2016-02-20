package net.daradara.xpf.media;

import android.support.annotation.NonNull;

import net.daradara.xpf.Visual;

/**
 * Created by masatakanabeshima on 2016/02/16.
 */
public class DrawingVisual extends Visual {
    public DrawingVisual()
    {

    }

    public @NonNull DrawingGroup getDrawing()
    {
        return m_drawing;
    }

    public DrawingContext renderOpen()
    {
        return new VisualDrawingContext(this);
    }

    private static class VisualDrawingContext extends DrawingGroupDrawingContext
    {
        public VisualDrawingContext(@NonNull DrawingVisual visual)
        {
            super(new DrawingGroup());
            m_visual = visual;
        }

        @Override
        public void close()
        {
            m_visual.m_drawing = getDrawingGroup();
        }

        private DrawingVisual m_visual;
    }

    private DrawingGroup m_drawing = new DrawingGroup();
}

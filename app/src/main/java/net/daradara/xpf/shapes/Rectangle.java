package net.daradara.xpf.shapes;

import net.daradara.xpf.Rect;
import net.daradara.xpf.Size;
import net.daradara.xpf.media.Brush;
import net.daradara.xpf.media.DrawingContext;
import net.daradara.xpf.media.Pen;

/**
 * Created by masatakanabeshima on 2016/02/14.
 */
public class Rectangle extends Shape {
    public Rectangle()
    {

    }

    @Override
    public void onRender(DrawingContext drawingContext)
    {
        super.onRender(drawingContext);
        Pen pen = null;
        Brush strokeBrush = getStroke();
        if(strokeBrush != null) {
            pen = new Pen(strokeBrush, getStrokeThickness());
        }
        Size size = getDesiredSize();
        drawingContext.drawRectangle(getFill(), pen,
                new Rect(0.0, 0.0, size.getWidth(), size.getHeight()));
    }
}

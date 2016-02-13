package net.daradara.xpf.android;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.daradara.xpf.Point;
import net.daradara.xpf.Rect;
import net.daradara.xpf.Vector;
import net.daradara.xpf.media.Brush;
import net.daradara.xpf.media.Color;
import net.daradara.xpf.media.DrawingContext;
import net.daradara.xpf.media.FormattedText;
import net.daradara.xpf.media.Pen;
import net.daradara.xpf.media.SolidColorBrush;
import net.daradara.xpf.media.Visual;

/**
 * Created by masatakanabeshima on 2016/02/14.
 */
public class AndroidDrawingContext extends DrawingContext
{
    public AndroidDrawingContext(@NonNull Canvas canvas,
                                 @NonNull Visual visual)
    {
        m_canvas = canvas;
        m_visual = visual;
        m_matrix = createMatrix(visual);
    }

    private Canvas m_canvas;
    private Visual m_visual;
    private Matrix m_matrix;

    @Override
    public void close()
    {

    }

    @Override
    public void drawEllipse(@Nullable Brush brush,
                            @Nullable Pen pen,
                            @NonNull Point center,
                            double radiusX,
                            double radiusY)
    {
        if(center == null) {
            throw new IllegalArgumentException();
        }

        m_canvas.setMatrix(m_matrix);

        float left = (float)(center.x - radiusX);
        float top = (float)(center.y - radiusY);
        float right = (float)(center.x + radiusX);
        float bottom = (float)(center.y + radiusY);

        Paint paint = createPaint(brush);
        if(paint != null) {
            m_canvas.drawOval(left, top, right, bottom, paint);
        }

        paint = createPaint(pen);
        if(paint != null) {
            m_canvas.drawOval(left, top, right, bottom, paint);
        }
    }

    @Override
    public void drawLine(@Nullable Pen pen, @NonNull Point point0, @NonNull Point point1)
    {
        if(point0 == null || point1 == null) {
            throw new IllegalArgumentException();
        }

        Paint paint = createPaint(pen);
        if(paint != null) {
            m_canvas.setMatrix(m_matrix);
            m_canvas.drawLine((float) point0.x, (float) point0.y,
                    (float) point1.x, (float) point1.y, paint);
        }
    }

    @Override
    public void drawRectangle(@Nullable Brush brush, @Nullable Pen pen,
                              @NonNull Rect rect)
    {
        if(rect == null) {
            throw new IllegalArgumentException();
        }

        m_canvas.setMatrix(m_matrix);

        Paint paint = createPaint(brush);
        if(paint != null) {
            m_canvas.drawRect((float)rect.x, (float)rect.y,
                    (float)rect.getRight(), (float)rect.getBottom(), paint);
        }

        paint = createPaint(pen);
        if(paint != null) {
            m_canvas.drawRect((float) rect.x, (float) rect.y,
                    (float) rect.getRight(), (float) rect.getBottom(), paint);
        }
    }

    @Override
    public void drawRoundedRectangle(@Nullable Brush brush,
                                     @Nullable Pen pen,
                                     @NonNull Rect rect,
                                     double radiusX,
                                     double radiusY)
    {
        if(rect == null) {
            throw new IllegalArgumentException();
        }

        m_canvas.setMatrix(m_matrix);

        Paint paint = createPaint(brush);
        if(paint != null) {
            m_canvas.drawRoundRect((float) rect.x, (float) rect.y,
                    (float) rect.getRight(), (float) rect.getBottom(),
                    (float) radiusX, (float) radiusY, paint);
        }

        paint = createPaint(pen);
        if(paint != null) {
            m_canvas.drawRoundRect((float) rect.x, (float) rect.y,
                    (float) rect.getRight(), (float) rect.getBottom(),
                    (float) radiusX, (float) radiusY, paint);
        }
    }

    @Override
    public void drawText(@NonNull FormattedText formattedText, @NonNull Point origin)
    {
        m_canvas.setMatrix(m_matrix);
        //m_canvas.drawText();
    }

    private Matrix createMatrix(Visual visual)
    {
        Matrix matrix = new Matrix();

        Vector visualOffset = visual.getVisualOffset();
        matrix.postTranslate((float)visualOffset.x, (float)visualOffset.y);

        return matrix;
    }

    private @Nullable Paint createPaint(@Nullable Pen pen)
    {
        if(pen == null) {
            return null;
        }

        Brush brush = pen.getBrush();
        if(brush == null) {
            return null;
        }

        double thickness = pen.getThickness();
        if(thickness <= 0.0) {
            return null;
        }

        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getColorCode(((SolidColorBrush) brush).getColor()));
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth((float)thickness);

        return paint;
    }

    private @Nullable Paint createPaint(@Nullable Brush brush)
    {
        if(brush == null) {
            return null;
        }

        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getColorCode(((SolidColorBrush) brush).getColor()));

        return paint;
    }

    private int getColorCode(@Nullable Color c)
    {
        if(c == null) return 0;
        return android.graphics.Color.argb(c.a, c.r, c.g, c.b);
    }
}


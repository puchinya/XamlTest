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
import net.daradara.xpf.media.Drawing;
import net.daradara.xpf.media.DrawingContext;
import net.daradara.xpf.media.DrawingGroup;
import net.daradara.xpf.media.FormattedText;
import net.daradara.xpf.media.Geometry;
import net.daradara.xpf.media.GeometryDrawing;
import net.daradara.xpf.media.Pen;
import net.daradara.xpf.media.RectangleGeometry;
import net.daradara.xpf.media.SolidColorBrush;
import net.daradara.xpf.media.TextDrawing;

import java.util.Stack;

/**
 * Created by masatakanabeshima on 2016/02/14.
 */
public class AndroidDrawingContext extends DrawingContext
{
    public AndroidDrawingContext(@NonNull Canvas canvas)
    {
        m_canvas = canvas;
    }

    private Canvas m_canvas;

    @Override
    public void close()
    {

    }

    private Stack<Matrix> m_offsetStack = new Stack<>();
    private static final Matrix m_defaultOffset = new Matrix();

    private Matrix getCurrentOffset()
    {
        if(m_offsetStack.empty()) {
            return m_defaultOffset;
        }
        return m_offsetStack.peek();
    }

    public void pushOffset(Vector offset)
    {
        Matrix m = new Matrix(getCurrentOffset());
        m.postTranslate((float)offset.x, (float)offset.y);
        m_offsetStack.push(m);
    }

    public void popOffset()
    {
        m_offsetStack.pop();
    }

    @Override
    public void drawDrawing(@NonNull Drawing drawing) {
        if(drawing instanceof DrawingGroup) {
            DrawingGroup drawingGroup = (DrawingGroup)drawing;
            for(Drawing child : drawingGroup.getChildren()) {
                drawDrawing(child);
            }
        } else if(drawing instanceof GeometryDrawing) {
            GeometryDrawing geometryDrawing = (GeometryDrawing)drawing;
            drawGeometry(geometryDrawing.getBrush(), geometryDrawing.getPen(), geometryDrawing.getGeometry());
        }  else if(drawing instanceof TextDrawing) {
            TextDrawing textDrawing = (TextDrawing)drawing;
            drawText(textDrawing.getText(), textDrawing.getOffset());
        } else {

        }
    }

    @Override
    public void drawGeometry(@Nullable Brush brush, @Nullable Pen pen, @NonNull Geometry geometry) {

        if(geometry instanceof RectangleGeometry) {
            RectangleGeometry rectangleGeometry = (RectangleGeometry)geometry;
            drawRoundedRectangle(brush, pen, rectangleGeometry.getRect(),
                    rectangleGeometry.getRadiusX(), rectangleGeometry.getRadiusY());
        }
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

        float left = (float)(center.getX() - radiusX);
        float top = (float)(center.getY() - radiusY);
        float right = (float)(center.getX() + radiusX);
        float bottom = (float)(center.getY() + radiusY);

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
            m_canvas.drawLine((float) point0.getX(), (float) point0.getY(),
                    (float) point1.getX(), (float) point1.getY(), paint);
        }
    }

    @Override
    public void drawRectangle(@Nullable Brush brush, @Nullable Pen pen,
                              @NonNull Rect rect)
    {
        if(rect == null) {
            throw new IllegalArgumentException();
        }

        m_canvas.setMatrix(getCurrentOffset());

        Paint paint = createPaint(brush);
        if(paint != null) {
            m_canvas.drawRect((float) rect.x, (float) rect.y,
                    (float) rect.getRight(), (float) rect.getBottom(), paint);
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

        m_canvas.save();
        m_canvas.concat(getCurrentOffset());

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

        m_canvas.restore();
    }

    @Override
    public void drawText(@NonNull FormattedText formattedText, @NonNull Point origin)
    {
        m_canvas.save();
        m_canvas.concat(getCurrentOffset());

        Paint paint = createPaint(formattedText.m_foreground);
        if(paint != null) {
            paint.setTextSize((float)formattedText.m_emSize);
            m_canvas.drawText(formattedText.m_text,
                    (float)origin.getX(), (float)origin.getY(),paint);
        }

        m_canvas.restore();
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


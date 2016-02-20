package net.daradara.xpf.media;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.daradara.xpf.Point;
import net.daradara.xpf.Rect;

/**
 * Created by masatakanabeshima on 2016/02/17.
 */
class DrawingGroupDrawingContext extends DrawingContext
{
    public DrawingGroupDrawingContext(@NonNull DrawingGroup drawingGroup)
    {
        m_drawingGroup = drawingGroup;
    }

    @Override
    public void close()
    {

    }

    @Override
    public void drawDrawing(@NonNull Drawing drawing)
    {
        if(drawing instanceof DrawingGroup) {
            DrawingGroup drawingGroup = (DrawingGroup)drawing;
            for(Drawing child : drawingGroup.getChildren()) {
                drawDrawing(child);
            }
        } else if(drawing instanceof GeometryDrawing) {
            GeometryDrawing geometryDrawing = (GeometryDrawing)drawing;
            drawGeometry(geometryDrawing.getBrush(), geometryDrawing.getPen(), geometryDrawing.getGeometry());
        } else {

        }
    }

    @Override
    public void drawGeometry(@Nullable Brush brush, @Nullable Pen pen, @NonNull Geometry geometry)
    {
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

    }

    @Override
    public void drawLine(@Nullable Pen pen, @NonNull Point point0, @NonNull Point point1)
    {


    }

    public void drawRectangle(@Nullable Brush brush, @Nullable Pen pen,
                              @NonNull Rect rect)
    {
        GeometryDrawing geometryDrawing = new GeometryDrawing(brush.clone(),
                pen.clone(), new RectangleGeometry(rect));
        m_drawingGroup.getChildren().add(geometryDrawing);
    }

    public void drawRoundedRectangle(@Nullable Brush brush,
                                     @Nullable Pen pen,
                                     @NonNull Rect rect,
                                     double radiusX,
                                     double radiusY)
    {
        GeometryDrawing geometryDrawing = new GeometryDrawing(brush.clone(),
                pen.clone(), new RectangleGeometry(rect, radiusX, radiusY));
        m_drawingGroup.getChildren().add(geometryDrawing);
    }

    public void drawText(@NonNull FormattedText formattedText, @NonNull Point origin)
    {
        TextDrawing textDrawing = new TextDrawing(formattedText, origin);
        m_drawingGroup.getChildren().add(textDrawing);
    }

    public @NonNull DrawingGroup getDrawingGroup()
    {
        return m_drawingGroup;
    }

    private DrawingGroup m_drawingGroup;
}


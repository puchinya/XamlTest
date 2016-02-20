package net.daradara.xpf.controls;

import android.support.annotation.NonNull;

import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.PropertyMetadata;
import net.daradara.xpf.Rect;
import net.daradara.xpf.Size;
import net.daradara.xpf.UIElement;

/**
 * Created by masatakanabeshima on 2016/02/20.
 */
public class StackPanel extends Panel{
    public StackPanel()
    {

    }

    @Override
    protected @NonNull Size measureOverride(@NonNull Size availableSize) {
        Orientation orientation = getOrientation();
        double width = 0.0;
        double height = 0.0;

        if (orientation == Orientation.HORIZONTAL) {
            double remainWidth = availableSize.getWidth();
            double maxHeight = availableSize.getHeight();
            for (UIElement element : getChildren()) {
                element.measure(new Size(remainWidth, maxHeight));
                Size desiredSize = element.getDesiredSize();
                width += desiredSize.getWidth();
                if (height < desiredSize.getHeight()) {
                    height = desiredSize.getHeight();
                }
                remainWidth -= width;
            }
        } else {
            double maxWidth = availableSize.getWidth();
            double remainHeight = availableSize.getHeight();
            for (UIElement element : getChildren()) {
                element.measure(new Size(maxWidth, remainHeight));
                Size desiredSize = element.getDesiredSize();
                height += desiredSize.getHeight();
                if (width < desiredSize.getWidth()) {
                    width = desiredSize.getWidth();
                }
                remainHeight -= height;
            }
        }

        return new Size(width, height);
    }

    @Override
    protected @NonNull Size arrangeOverride(@NonNull Size finalSize)
    {
        Orientation orientation = getOrientation();
        if (orientation == Orientation.HORIZONTAL) {
            double offsetX = 0.0;
            for (UIElement element : getChildren()) {
                Size desiredSize = element.getDesiredSize();
                element.arrange(new Rect(offsetX, 0.0,
                        desiredSize.getWidth(), desiredSize.getHeight()));
                offsetX += desiredSize.getWidth();
            }
        } else {
            double offsetY = 0.0;
            for (UIElement element : getChildren()) {
                Size desiredSize = element.getDesiredSize();
                element.arrange(new Rect(0.0, offsetY,
                        desiredSize.getWidth(), desiredSize.getHeight()));
                offsetY += desiredSize.getHeight();
            }
        }

        return finalSize;
    }

    public final Orientation getOrientation() { return (Orientation)getValue(orientationProperty);}
    public void setOrientation(Orientation value) {
        setValue(orientationProperty, value);
    }

    public static final DependencyProperty orientationProperty = DependencyProperty.register("orientation",
            Orientation.class, StackPanel.class, new PropertyMetadata(Orientation.HORIZONTAL));
}

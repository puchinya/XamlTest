package net.daradara.xpf.controls;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.FrameworkElement;
import net.daradara.xpf.Point;
import net.daradara.xpf.PropertyMetadata;
import net.daradara.xpf.Size;
import net.daradara.xpf.media.Brush;
import net.daradara.xpf.media.DrawingContext;
import net.daradara.xpf.media.FormattedText;

/**
 * Created by masatakanabeshima on 2016/02/20.
 */
public class TextBlock extends FrameworkElement {

    public TextBlock()
    {

    }

    @Override
    protected void onRender(@NonNull DrawingContext drawingContext)
    {
        drawingContext.drawText(makeFormattedText(), new Point(0.0, 0.0));
    }

    @Override
    protected Size measureOverride(Size availableSize)
    {
        FormattedText formattedText = makeFormattedText();

        return new Size(formattedText.getWidth(), formattedText.getHeight());
    }

    private FormattedText makeFormattedText()
    {
        FormattedText formattedText = new FormattedText(getText(),
                null, getFontSize(), getForeground());
        return formattedText;
    }

    public final @NonNull String getText()
    {
        return (String)getValue(textProperty);
    }

    public final void setText(@NonNull String value)
    {
        setValue(textProperty, value);
    }

    public final double getFontSize()
    {
        return ((Double)getValue(fontSizeProperty)).doubleValue();
    }

    public final @Nullable Brush getForeground()
    {
        return ((Brush)getValue(foregroundProperty));
    }

    public final void setForeground(@Nullable Brush value)
    {
        setValue(foregroundProperty, value);
    }

    public static final DependencyProperty textProperty = DependencyProperty.register("text",
            String.class, TextBlock.class, new PropertyMetadata(""));
    public static final DependencyProperty fontSizeProperty = DependencyProperty.register("fontSize",
            Double.class, TextBlock.class, new PropertyMetadata(Double.valueOf(11.0)));
    public static final DependencyProperty foregroundProperty = DependencyProperty.register("foreground",
            Brush.class, TextBlock.class, new PropertyMetadata(null));
}

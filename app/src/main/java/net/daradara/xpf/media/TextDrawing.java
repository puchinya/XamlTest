package net.daradara.xpf.media;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.Point;
import net.daradara.xpf.PropertyMetadata;

/**
 * Created by masatakanabeshima on 2016/02/20.
 */
public class TextDrawing extends Drawing {
    public TextDrawing()
    {

    }

    public TextDrawing(@NonNull FormattedText text,
                       @NonNull Point offset)
    {
        setText(text);
        setOffset(offset);
    }

    public @Nullable FormattedText getText()
    {
        return (FormattedText)getValue(textProperty);
    }

    public void setText(@Nullable FormattedText value)
    {
        setValue(textProperty, value);
    }

    public @NonNull Point getOffset()
    {
        return (Point)getValue(offsetProperty);
    }

    public void setOffset(@NonNull Point value)
    {
        setValue(offsetProperty, value);
    }

    public static final DependencyProperty textProperty = DependencyProperty.register("text", FormattedText.class,
            TextDrawing.class, new PropertyMetadata(null));
    public static final DependencyProperty offsetProperty = DependencyProperty.register("offset", Point.class,
            TextDrawing.class, new PropertyMetadata(new Point(0.0, 0.0)));
}

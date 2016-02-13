package net.daradara.xpf.controls;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.daradara.xpf.DependencyProperty;
import net.daradara.xpf.PropertyMetadata;

/**
 * Created by masatakanabeshima on 2016/02/13.
 */
public class ContentControl extends Control {
    public ContentControl()
    {

    }

    @Override
    protected void onPropertyChanged(@NonNull DependencyProperty dp,
                                     @Nullable Object oldValue, @Nullable Object newValue)
    {
        super.onPropertyChanged(dp, oldValue, newValue);

        if(dp == contentProperty && oldValue != newValue) {
            if(oldValue != null) {
                removeLogicalChild(oldValue);
            }

            if(newValue != null) {
                addLogicalChild(newValue);
            }

            // Update hasContent dependency property
            setValue(hasContentProperty, Boolean.valueOf(newValue != null));

            invalidateMeasure();
        }
    }

    public boolean getHasContent()
    {
        return ((Boolean)getValue(hasContentProperty)).booleanValue();
    }

    public static final DependencyProperty contentProperty = DependencyProperty.register("content",
            Object.class, ContentControl.class, new PropertyMetadata(null));

    public static final DependencyProperty contentStringFormatProperty = DependencyProperty.register("contentStringFormat",
            String.class, ContentControl.class, new PropertyMetadata(null));

    public static final DependencyProperty hasContentProperty = DependencyProperty.register("hasContent",
            Boolean.TYPE, ContentControl.class, new PropertyMetadata(Boolean.FALSE));
}

package net.daradara.xpf;

import android.support.annotation.Nullable;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public final class PropertyMetadata {

    public PropertyMetadata()
    {
        this(DependencyProperty.getUnsetValue());
    }

    public PropertyMetadata(@Nullable Object defaultValue)
    {
        m_defaultValue = defaultValue;
    }

    public @Nullable Object getDefaultValue()
    {
        return m_defaultValue;
    }

    private Object m_defaultValue;
}

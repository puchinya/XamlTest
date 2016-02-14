package net.daradara.xpf;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.daradara.xpf.delegate.Func;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public class DependencyProperty {

    private DependencyProperty()
    {

    }

    public @NonNull String getName()
    {
        return m_name;
    }

    public boolean getReadOnly()
    {
        return m_readOnly;
    }

    public @NonNull Class getOwnerType()
    {
        return m_ownerType;
    }

    public @NonNull Class getPropertyType()
    {
        return m_propertyType;
    }

    public @Nullable PropertyMetadata getDefaultMetadata()
    {
        return m_defaultMetadata;
    }

    public @Nullable Func<Boolean, Object> getValidateValueCallback()
    {
        return m_validateValueCallback;
    }

    public static @NonNull Object getUnsetValue()
    {
        return m_unsetValue;
    }

    public boolean isValidType(@Nullable Object value)
    {
        if(value == null) return true;
        return m_propertyType.isAssignableFrom(value.getClass());
    }

    public boolean isValidValue(@Nullable Object value)
    {
        if(!isValidType(value)) return false;
        if(m_validateValueCallback == null)
            return true;
        return m_validateValueCallback.invoke(value);
    }

    public static DependencyProperty register(@NonNull String name, @NonNull Class propertyType, @NonNull Class ownerType)
    {
        return register(name, propertyType, ownerType, null, null);
    }

    public static DependencyProperty register(@NonNull String name, @NonNull Class propertyType, @NonNull Class ownerType,
                                              @Nullable PropertyMetadata metadata)
    {
        return register(name, propertyType, ownerType, metadata, null);
    }

    public static DependencyProperty register(@NonNull String name, @NonNull Class propertyType, @NonNull Class ownerType,
                                              @Nullable PropertyMetadata metadata,
                                              @Nullable Func<Boolean, Object> validateValueCallback)
    {
        DependencyProperty dp = new DependencyProperty();
        dp.m_name = name;
        dp.m_propertyType = propertyType;
        dp.m_ownerType = ownerType;
        dp.m_defaultMetadata = metadata;
        dp.m_validateValueCallback = validateValueCallback;
        dp.m_readOnly = false;

        return dp;
    }

    public static DependencyProperty registerReadOnly(@NonNull String name, @NonNull Class propertyType, @NonNull Class ownerType,
                                              @Nullable PropertyMetadata metadata)
    {
        return register(name, propertyType, ownerType, metadata, null);
    }

    public static DependencyProperty registerReadOnly(@NonNull String name, @NonNull Class propertyType, @NonNull Class ownerType,
                                              @Nullable PropertyMetadata metadata,
                                              @Nullable Func<Boolean, Object> validateValueCallback)
    {
        DependencyProperty dp = new DependencyProperty();
        dp.m_name = name;
        dp.m_propertyType = propertyType;
        dp.m_ownerType = ownerType;
        dp.m_defaultMetadata = metadata;
        dp.m_validateValueCallback = validateValueCallback;
        dp.m_readOnly = true;

        return dp;
    }

    private String m_name;
    private boolean m_readOnly;
    private Class m_ownerType;
    private Class m_propertyType;
    private PropertyMetadata m_defaultMetadata;
    private Func<Boolean, Object> m_validateValueCallback;
    private static final Object m_unsetValue = new Object();
}

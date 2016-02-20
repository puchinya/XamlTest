package net.daradara.xpf;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public class DependencyObject extends DispatcherObject implements Cloneable {

    public DependencyObject()
    {

    }

    @Override
    public DependencyObject clone()
    {
        DependencyObject r = null;

        try {
            r = (DependencyObject)super.clone();
            r.m_currentValues = new HashMap<>(m_currentValues);
            r.m_localValues = new HashMap<>(m_localValues);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    public final @Nullable Object getValue(@NonNull DependencyProperty dp)
    {
        if(dp == null || !dp.getOwnerType().isInstance(this)) {
            throw new IllegalArgumentException("dp is invalid");
        }

        synchronized (m_localValues) {
            if (m_localValues.containsKey(dp)) {
                return m_localValues.get(dp);
            }
        }

        synchronized (m_currentValues) {
            if(m_currentValues.containsKey(dp)) {
                return m_currentValues.get(dp);
            } else {
                return dp.getDefaultMetadata().getDefaultValue();
            }
        }
    }

    public final @Nullable Object readLocalValue(@NonNull DependencyProperty dp) {
        if (dp == null || !dp.getOwnerType().isInstance(this)) {
            throw new IllegalArgumentException("dp is invalid.");
        }

        synchronized (m_localValues) {
            if(!m_localValues.containsKey(dp))
                return DependencyProperty.getUnsetValue();
            return m_localValues.get(dp);
        }
    }

    public final void setValue(@NonNull DependencyProperty dp, @Nullable Object value)
    {
        if(dp == null || !dp.getOwnerType().isInstance(this)) {
            throw new IllegalArgumentException("dp is invalid.");
        }

        if(!dp.isValidValue(value)) {
            throw new IllegalArgumentException("value is invalid.");
        }

        Object oldValue = getValue(dp);

        m_localValues.put(dp, value);
        onPropertyChanged(dp, oldValue, value);
    }

    public final void setCurrentValue(@NonNull DependencyProperty dp, @Nullable Object value)
    {
        if(dp == null || !dp.getOwnerType().isInstance(this)) {
            throw new IllegalArgumentException("dp is invalid.");
        }

        if(!dp.isValidValue(value)) {
            throw new IllegalArgumentException("value is invalid.");
        }

        Object oldValue = getValue(dp);

        m_currentValues.put(dp, value);

        if(!m_localValues.containsKey(dp)) {
            onPropertyChanged(dp, oldValue, value);
        }
    }

    protected void onPropertyChanged(@NonNull DependencyProperty dp,
                                     @Nullable Object oldValue, @Nullable Object newValue)
    {

    }

    private Map<DependencyProperty, Object> m_currentValues = new HashMap<>();
    private Map<DependencyProperty, Object> m_localValues = new HashMap<>();
}

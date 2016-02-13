package net.daradara.xpf;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public class DependencyObject extends DispatcherObject {

    public DependencyObject()
    {

    }

    public Object getValue(@NonNull DependencyProperty dp)
    {
        if(dp == null || !dp.getOwnerType().isInstance(this)) {
            throw new IllegalArgumentException("dp is invalid");
        }

        synchronized (m_currentValues) {
            if(m_currentValues.containsKey(dp)) {
                return m_currentValues.get(dp);
            }
        }

        synchronized (m_values) {
            if (!m_values.containsKey(dp)) {
                return dp.getDefaultMetadata().getDefaultValue();
            }
            return m_values.get(dp);
        }
    }

    public Object readLocalValue(@NonNull DependencyProperty dp) {
        if (dp == null || !dp.getOwnerType().isInstance(this)) {
            throw new IllegalArgumentException("dp is invalid.");
        }

        synchronized (m_values) {
            if(!m_values.containsKey(dp))
                return DependencyProperty.getUnsetValue();
            return m_values.get(dp);
        }
    }

    public void setValue(@NonNull DependencyProperty dp, @Nullable Object value)
    {
        if(dp == null || !dp.getOwnerType().isInstance(this)) {
            throw new IllegalArgumentException("dp is invalid.");
        }

        if(!dp.isValidValue(value)) {
            throw new IllegalArgumentException("value is invalid.");
        }

        m_values.put(dp, value);
    }

    protected void onPropertyChanged(@NonNull DependencyProperty dp,
                                     @Nullable Object oldValue, @Nullable Object newValue)
    {

    }

    private Map<DependencyProperty, Object> m_currentValues = new HashMap<>();
    private Map<DependencyProperty, Object> m_values = new HashMap<>();
}

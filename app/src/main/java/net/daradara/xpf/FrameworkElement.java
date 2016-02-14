package net.daradara.xpf;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.daradara.xpf.data.Binding;
import net.daradara.xpf.data.BindingMode;
import net.daradara.xpf.delegate.Func;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public class FrameworkElement extends UIElement {

    protected FrameworkElement()
    {
        setDataContext(this);
    }

    public @Nullable Object getDataContext()
    {
        return m_dataContext;
    }

    public void setDataContext(@Nullable Object value)
    {
        unBindFromDataContext();
        m_dataContext = value;
        bindToDataContext();
    }

    private void unBindFromDataContext()
    {
        Object dataContext = m_dataContext;
        if(dataContext != null) {
            if (dataContext instanceof NotifyPropertyChanged) {
                NotifyPropertyChanged n = (NotifyPropertyChanged)dataContext;

                for (Map.Entry<DependencyProperty, BindingSet> pair : m_bindings.entrySet()) {
                    BindingSet bindingSet = pair.getValue();
                    if(bindingSet.binding.getSource() == null) {
                        n.getPropertyChanged().remove(bindingSet.propertyChangedEventHandler);
                    }
                }
            }
        }
    }

    private void bindToDataContext()
    {
        Object dataContext = m_dataContext;
        if(dataContext != null) {
            if (dataContext instanceof NotifyPropertyChanged) {
                NotifyPropertyChanged n = (NotifyPropertyChanged)dataContext;

                for (Map.Entry<DependencyProperty, BindingSet> pair : m_bindings.entrySet()) {
                    BindingSet bindingSet = pair.getValue();
                    if(bindingSet.binding.getSource() == null) {
                        n.getPropertyChanged().add(bindingSet.propertyChangedEventHandler);
                        applyToTarget(bindingSet);
                    }
                }
            }
        }
    }

    private void bind(@NonNull BindingSet bindingSet)
    {
        switch (getBindingMode(bindingSet.dp, bindingSet.binding)) {
            case ONE_WAY:
            case TWO_WAY:
                Object source = getBindingSource(bindingSet.binding);
                if(source instanceof NotifyPropertyChanged) {
                    NotifyPropertyChanged n = (NotifyPropertyChanged) source;
                    n.getPropertyChanged().add(bindingSet.propertyChangedEventHandler);
                }
                break;
            default:
                break;
        }

        applyToTarget(bindingSet);
    }

    public void setBinding(@NonNull final DependencyProperty dp,
                           @NonNull final Binding binding)
    {
        if(dp == null || binding == null) {
            throw new IllegalArgumentException();
        }

        final BindingSet bindingSet = new BindingSet();

        bindingSet.dp = dp;
        bindingSet.binding = binding;
        bindingSet.propertyChangedEventHandler = new Func<Void, String>() {
            @Override
            public Void invoke(String s) {

                Object source = getBindingSource(binding);

                if(source != null) {
                    Object propertyValue = getPropertyValue(source, bindingSet.binding.getPath());
                    if (!bindingSet.settingToSource) {
                        setValue(dp, propertyValue);
                    }
                }
                return null;
            }
        };

        m_bindings.put(dp, bindingSet);

        bind(bindingSet);
    }

    private static Object getPropertyValue(@NonNull Object source, @NonNull String propertyName)
    {
        Method m = getGetter(source.getClass(), propertyName);
        if(m != null) {
            return null;
        }
        try {
            return m.invoke(source);
        } catch (Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }

    private void applyToTarget(@NonNull BindingSet bindingSet)
    {
        Binding binding = bindingSet.binding;
        Object source = getBindingSource(binding);
        switch (getBindingMode(bindingSet.dp, binding)) {
            case ONE_WAY:
            case ONE_TIME:
            case TWO_WAY:
                Object propertyValue = getPropertyValue(source, binding.getPath());
                setValue(bindingSet.dp, propertyValue);
                break;
            default:
                break;
        }
    }

    public void setBinding(@NonNull DependencyProperty dp,
                           @NonNull String path)
    {
        Binding binding = new Binding(path);
        setBinding(dp, binding);
    }

    public BindingMode getBindingMode(@NonNull DependencyProperty dp, @NonNull Binding binding)
    {
        BindingMode mode = binding.getMode();

        if(mode == BindingMode.DEFAULT) {
            mode = BindingMode.ONE_WAY;
        }

        return mode;
    }

    public @Nullable Object getBindingSource(@NonNull Binding binding)
    {
        Object source = binding.getSource();
        if(source == null) {
            source = m_dataContext;
        }
        return source;
    }

    @Override
    protected void onPropertyChanged(@NonNull DependencyProperty dp,
                                     @Nullable Object oldValue, @Nullable Object newValue)
    {
        BindingSet bindingSet = m_bindings.get(dp);

        if(bindingSet != null && !bindingSet.settingToSource) {
            Binding binding = bindingSet.binding;

            switch (binding.getMode()) {
                case ONE_WAY_TO_SOURCE:
                case TWO_WAY:
                {
                    Object source = getBindingSource(binding);
                    if(source != null) {
                        Class type = source.getClass();
                        String path = binding.getPath();
                        Field field = null;
                        try {
                            field = type.getField(path);
                        } catch (NoSuchFieldException ex)
                        {

                        }
                        if(field != null) {
                            try {
                                field.set(source, newValue);
                            } catch (IllegalAccessException ex)
                            {
                                ex.printStackTrace();
                            }
                        } else {
                            Method setter = getSetter(type, path);
                            if(setter != null) {
                                try {
                                    setter.invoke(source, newValue);
                                } catch (Exception ex)
                                {
                                    ex.printStackTrace();
                                }
                            }
                        }

                    }
                }
                    break;
                default:
                    break;
            }
        }
    }

    private static @Nullable Method getSetter(@NonNull Class type, @NonNull String propertyName)
    {
        Method[] methods = type.getMethods();
        String setterName = "set" + Character.toUpperCase(propertyName.charAt(0)) +
                propertyName.substring(1);
        for (int i = 0; i < methods.length; i++) {
            Method m = methods[i];
            if(m.getName().equals(setterName)) {
                return m;
            }
        }
        return null;
    }

    private static @Nullable Method getGetter(@NonNull Class type, @NonNull String propertyName)
    {
        Method[] methods = type.getMethods();
        String getterName = "get" + Character.toUpperCase(propertyName.charAt(0)) +
                propertyName.substring(1);
        for (int i = 0; i < methods.length; i++) {
            Method m = methods[i];
            if(m.getName().equals(getterName)) {
                return m;
            }
        }
        return null;
    }

    protected final @NonNull Size measureCore(@NonNull Size availableSize)
    {
        double width = getWidth();
        double height = getHeight();
        double minWidth = getMinWidth();
        double minHeight = getMinHeight();
        double maxWidth = getMaxWidth();
        double maxHeight = getMaxHeight();

        Thickness margin = getMargin();

        double realAvailableWidth = availableSize.getWidth();
        double realAvailableHeight = availableSize.getHeight();

        if(width != Double.NaN) {
            realAvailableWidth = width;
        }
        if(minWidth != Double.NaN && realAvailableWidth < minWidth) {
            realAvailableWidth = minWidth;
        }
        if(maxWidth != Double.NaN && realAvailableWidth > maxWidth) {
            realAvailableWidth = maxWidth;
        }

        if(height != Double.NaN) {
            realAvailableHeight = height;
        }
        if(minHeight != Double.NaN && realAvailableHeight < minHeight) {
            realAvailableHeight = minHeight;
        }
        if(maxHeight != Double.NaN && realAvailableHeight > maxHeight) {
            realAvailableHeight = maxHeight;
        }

        Size childrenAvailableSize = new Size(realAvailableWidth, realAvailableHeight);

        Size childrenDesiredSize = measureOverride(childrenAvailableSize);

        double desiredWidth = childrenDesiredSize.getWidth() + margin.getLeft() + margin.getRight();
        double desiredHeight = childrenDesiredSize.getHeight() + margin.getTop() + margin.getBottom();

        return new Size(desiredWidth, desiredHeight);
    }

    protected Size measureOverride(@NonNull Size availableSize)
    {
        Size desiredSize = new Size();
        return desiredSize;
    }

    protected void addLogicalChild(Object child)
    {
        m_logicalChildren.add(child);
    }

    protected void removeLogicalChild(Object child)
    {
        m_logicalChildren.remove(child);
    }

    public void setWidth(double value)
    {
        setValue(widthProperty, Double.valueOf(value));
    }

    public double getWidth() {
        return ((Double)getValue(widthProperty)).doubleValue();
    }

    public void setHeight(double value)
    {
        setValue(heightProperty, Double.valueOf(value));
    }

    public double getHeight() {
        return ((Double)getValue(heightProperty)).doubleValue();
    }

    public void setMinWidth(double value) {
        setValue(minWidthProperty, Double.valueOf(value));
    }

    public double getMinWidth() {
        return ((Double)getValue(minWidthProperty)).doubleValue();
    }

    public void setMinHeight(double value) {
        setValue(minHeightProperty, Double.valueOf(value));
    }

    public double getMinHeight() {
        return ((Double)getValue(minHeightProperty)).doubleValue();
    }

    public void setMaxWidth(double value) {
        setValue(maxWidthProperty, Double.valueOf(value));
    }

    public double getMaxWidth() {
        return ((Double)getValue(maxWidthProperty)).doubleValue();
    }

    public void setMaxHeight(double value) {
        setValue(maxHeightProperty, Double.valueOf(value));
    }

    public double getMaxHeight() {
        return ((Double)getValue(maxHeightProperty)).doubleValue();
    }

    public @NonNull Thickness getMargin() {
        return (Thickness)getValue(marginProperty);
    }

    public void setMargin(@NonNull Thickness value) {
        setValue(marginProperty, value);
    }

    public static final DependencyProperty actualWidthProperty = DependencyProperty.registerReadOnly("actualWidth", Double.class,
            FrameworkElement.class, new PropertyMetadata());

    public static final DependencyProperty actualHeightProperty = DependencyProperty.registerReadOnly("actualHeight", Double.class,
            FrameworkElement.class, new PropertyMetadata());

    public static final DependencyProperty widthProperty = DependencyProperty.register("width", Double.class,
            FrameworkElement.class, new PropertyMetadata(Double.NaN));

    public static final DependencyProperty heightProperty = DependencyProperty.register("height", Double.class,
            FrameworkElement.class, new PropertyMetadata(Double.NaN));

    public static final DependencyProperty minWidthProperty = DependencyProperty.register("minWidth", Double.class,
            FrameworkElement.class, new PropertyMetadata(Double.NaN));

    public static final DependencyProperty minHeightProperty = DependencyProperty.register("minHeight", Double.class,
            FrameworkElement.class, new PropertyMetadata(Double.NaN));

    public static final DependencyProperty maxWidthProperty = DependencyProperty.register("maxWidth", Double.class,
            FrameworkElement.class, new PropertyMetadata(Double.NaN));

    public static final DependencyProperty maxHeightProperty = DependencyProperty.register("maxHeight", Double.class,
            FrameworkElement.class, new PropertyMetadata(Double.NaN));

    public static final DependencyProperty marginProperty = DependencyProperty.register("margin", Thickness.class,
            FrameworkElement.class, new PropertyMetadata(new Thickness(0.0)));

    private static class BindingSet {
        public boolean settingToSource;
        public DependencyProperty dp;
        public Binding binding;
        public Func<Void, String> propertyChangedEventHandler;
    }

    private Map<DependencyProperty, BindingSet> m_bindings = new HashMap<>();
    private Object m_dataContext;
    private ArrayList<Object> m_logicalChildren = new ArrayList<>();
}

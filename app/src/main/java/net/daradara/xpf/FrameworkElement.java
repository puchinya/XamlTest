package net.daradara.xpf;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.daradara.xpf.data.Binding;
import net.daradara.xpf.data.BindingMode;
import net.daradara.xpf.delegate.Func;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
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
            public Void invokeOverride(String s) {

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
        super.onPropertyChanged(dp, oldValue, newValue);

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

        invalidateMeasure();
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

    @Override
    protected final @NonNull Size measureCore(@NonNull Size availableSize)
    {
        double width = getWidth();
        double height = getHeight();
        double minWidth = getMinWidth();
        double minHeight = getMinHeight();
        double maxWidth = getMaxWidth();
        double maxHeight = getMaxHeight();

        HorizontalAlignment horizontalAlignment = getHorizontalAlignment();
        VerticalAlignment verticalAlignment = getVerticalAlignment();

        Thickness margin = getMargin();

        double realAvailableWidth = availableSize.getWidth() - (margin.getLeft() + margin.getRight());
        double realAvailableHeight = availableSize.getHeight() - (margin.getTop() + margin.getBottom());

        double constraintWidth = Double.POSITIVE_INFINITY;
        double constraintHeight = Double.POSITIVE_INFINITY;

        if(horizontalAlignment == HorizontalAlignment.STRETCH) {
            constraintWidth = realAvailableWidth;
        }
        if(verticalAlignment == VerticalAlignment.STRETCH) {
            constraintHeight = realAvailableHeight;
        }

        if(!Double.isNaN(minWidth)) {
            if(constraintWidth < minWidth) {
                constraintWidth = minWidth;
            }
            if(!Double.isNaN(width) && width < minWidth) {
                width = minWidth;
            }
        }
        if(!Double.isNaN(maxWidth)) {
            if(constraintWidth > maxWidth) {
                constraintWidth = maxWidth;
            }
            if(!Double.isNaN(width) && width > maxWidth) {
                width = maxWidth;
            }
        }

        if(!Double.isNaN(width)) {
            constraintWidth = width;
        }

        if(!Double.isNaN(minHeight)) {
            if(constraintHeight < minHeight) {
                constraintHeight = minHeight;
            }
            if(!Double.isNaN(height) && height < minHeight) {
                height = minHeight;
            }
        }
        if(!Double.isNaN(maxHeight)) {
            if(constraintHeight > maxHeight) {
                constraintHeight = maxHeight;
            }
            if(!Double.isNaN(height) && height > maxHeight) {
                height = maxHeight;
            }
        }

        if(!Double.isNaN(height)) {
            constraintHeight = height;
        }

        Size childSize = measureOverride(new Size(constraintWidth, constraintHeight));
        double actualWidth = Double.isInfinite(constraintWidth) ? childSize.getWidth() : constraintWidth;
        double actualHeight = Double.isInfinite(constraintHeight) ? childSize.getHeight() : constraintHeight;

        double desiredWidth = actualWidth + margin.getLeft() + margin.getRight();
        double desiredHeight = actualHeight + margin.getTop() + margin.getBottom();

        return new Size(desiredWidth, desiredHeight);
    }

    protected @NonNull Size measureOverride(@NonNull Size availableSize)
    {
        return Size.ZERO;
    }

    @Override
    protected final void arrangeCore(@NonNull Rect finalRect)
    {
        double finalWidth = finalRect.width;
        double finalHeight = finalRect.height;
        Thickness margin = getMargin();

        finalWidth -= margin.getLeft() + margin.getRight();
        finalHeight -= margin.getTop() + margin.getBottom();

        if(finalWidth < 0.0) finalWidth = 0.0;
        if(finalHeight < 0.0) finalHeight = 0.0;

        Size usedSize = arrangeOverride(new Size(finalWidth, finalHeight));

        double offsetX = finalRect.x + margin.getLeft();
        double offsetY = finalRect.y + margin.getTop();

        setVisualOffset(new Vector(offsetX, offsetY));
        setActualWidth(usedSize.getWidth());
        setActualHeight(usedSize.getHeight());
    }

    protected @NonNull Size arrangeOverride(@NonNull Size finalSize)
    {
        return finalSize;
    }

    protected final void addLogicalChild(Object child)
    {
        if(child instanceof FrameworkElement) {
            ((FrameworkElement)child).m_parent = this;
        }
        m_logicalChildren.add(child);
    }

    protected final void removeLogicalChild(Object child)
    {
        m_logicalChildren.remove(child);
        if(child instanceof FrameworkElement) {
            ((FrameworkElement)child).m_parent = null;
        }
    }

    public final void setActualWidth(double value)
    {
        setValue(actualWidthProperty, Double.valueOf(value));
    }

    public final double getActualWidth() {
        return ((Double)getValue(actualWidthProperty)).doubleValue();
    }

    public final void setActualHeight(double value)
    {
        setValue(actualHeightProperty, Double.valueOf(value));
    }

    public final double getActualHeight() {
        return ((Double)getValue(actualHeightProperty)).doubleValue();
    }

    public final void setWidth(double value)
    {
        setValue(widthProperty, Double.valueOf(value));
    }

    public final double getWidth() {
        return ((Double)getValue(widthProperty)).doubleValue();
    }

    public final void setHeight(double value)
    {
        setValue(heightProperty, Double.valueOf(value));
    }

    public final double getHeight() {
        return ((Double)getValue(heightProperty)).doubleValue();
    }

    public final void setMinWidth(double value) {
        setValue(minWidthProperty, Double.valueOf(value));
    }

    public final double getMinWidth() { return ((Double)getValue(minWidthProperty)).doubleValue(); }

    public final void setMinHeight(double value) { setValue(minHeightProperty, Double.valueOf(value)); }

    public final double getMinHeight() { return ((Double)getValue(minHeightProperty)).doubleValue(); }

    public final void setMaxWidth(double value) { setValue(maxWidthProperty, Double.valueOf(value)); }

    public final double getMaxWidth() { return ((Double)getValue(maxWidthProperty)).doubleValue(); }

    public final void setMaxHeight(double value) { setValue(maxHeightProperty, Double.valueOf(value)); }

    public final double getMaxHeight() { return ((Double)getValue(maxHeightProperty)).doubleValue(); }

    public final @NonNull Thickness getMargin() { return (Thickness)getValue(marginProperty); }

    public final void setMargin(@NonNull Thickness value) { setValue(marginProperty, value); }

    public final void setHorizontalAlignment(HorizontalAlignment value) {
        setValue(horizontalAlignmentProperty, value);
    }

    public final HorizontalAlignment getHorizontalAlignment() {
        return (HorizontalAlignment)getValue(horizontalAlignmentProperty);
    }

    public final void setVerticalAlignment(VerticalAlignment value) { setValue(verticalAlignmentProperty, value); }

    public final VerticalAlignment getVerticalAlignment() {
        return (VerticalAlignment)getValue(verticalAlignmentProperty);
    }

    public final @Nullable DependencyObject getParent() {
        return m_parent;
    }

    protected @NonNull Iterator getLogicalChildren() { return m_logicalChildren.iterator(); }

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

    public static final DependencyProperty horizontalAlignmentProperty = DependencyProperty.register("horizontalAlignment",
            HorizontalAlignment.class, FrameworkElement.class, new PropertyMetadata(HorizontalAlignment.LEFT));

    public static final DependencyProperty verticalAlignmentProperty = DependencyProperty.register("verticalAlignment",
            VerticalAlignment.class, FrameworkElement.class, new PropertyMetadata(VerticalAlignment.TOP));

    private static class BindingSet {
        public boolean settingToSource;
        public DependencyProperty dp;
        public Binding binding;
        public Func<Void, String> propertyChangedEventHandler;
    }

    private Map<DependencyProperty, BindingSet> m_bindings = new HashMap<>();
    private Object m_dataContext;
    private FrameworkElement m_parent = null;
    private ArrayList<Object> m_logicalChildren = new ArrayList<>();
}

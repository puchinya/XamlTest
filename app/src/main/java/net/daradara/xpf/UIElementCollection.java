package net.daradara.xpf;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by masatakanabeshima on 2016/02/20.
 */
public final class UIElementCollection implements List<UIElement> {
    public UIElementCollection(@NonNull UIElement visualParent,
                               @NonNull FrameworkElement logicalParent)
    {
        m_visualParent = visualParent;
        m_logicalParent = logicalParent;
    }

    private UIElement m_visualParent;
    private FrameworkElement m_logicalParent;
    private ArrayList<UIElement> m_list = new ArrayList<>();

    private void onCollectionChanged(@Nullable UIElement added, @Nullable UIElement removed)
    {
        if(removed != null) {
            m_visualParent.removeVisualChild(removed);
            m_logicalParent.removeLogicalChild(removed);
        }
        if (added != null) {
            m_logicalParent.addLogicalChild(added);
            m_visualParent.addVisualChild(added);
        }
    }

    @Override
    public void add(int location, UIElement element) {
        m_list.add(location, element);
        onCollectionChanged(element, null);
    }

    @Override
    public boolean add(UIElement element) {
        if(m_list.add(element)) {
            onCollectionChanged(element, null);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addAll(int location, Collection<? extends UIElement> collection) {
        if(m_list.addAll(location, collection)) {
            for (UIElement element:
                 m_list) {
                onCollectionChanged(element, null);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addAll(Collection<? extends UIElement> collection) {
        if(m_list.addAll(collection)) {
            for (UIElement element:
                    m_list) {
                onCollectionChanged(element, null);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void clear() {
        for (UIElement element:
                m_list) {
            onCollectionChanged(null, element);
        }
        m_list.clear();
    }

    @Override
    public boolean contains(Object object) {
        return m_list.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return m_list.containsAll(collection);
    }

    @Override
    public UIElement get(int location) {
        return m_list.get(location);
    }

    @Override
    public int indexOf(Object object) {
        return m_list.indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return m_list.isEmpty();
    }

    @NonNull
    @Override
    public Iterator<UIElement> iterator() {
        return m_list.iterator();
    }

    @Override
    public int lastIndexOf(Object object) {
        return m_list.lastIndexOf(object);
    }

    @Override
    public ListIterator<UIElement> listIterator() {
        return m_list.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<UIElement> listIterator(int location) {
        return m_list.listIterator(location);
    }

    @Override
    public @NonNull UIElement remove(int location) {
        UIElement removed = m_list.remove(location);
        onCollectionChanged(null, removed);
        return removed;
    }

    @Override
    public boolean remove(Object object) {
        if(m_list.remove(object)) {
            onCollectionChanged(null, (UIElement)object);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if(m_list.removeAll(collection)) {
            for (UIElement element:
                    m_list) {
                onCollectionChanged(null, element);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return m_list.retainAll(collection);
    }

    @Override
    public UIElement set(int location, @NonNull UIElement element) {
        if(location < 0 || location >= m_list.size())
            throw new IndexOutOfBoundsException();
        UIElement oldElement = m_list.get(location);
        onCollectionChanged(element, oldElement);
        return m_list.set(location, element);
    }

    @Override
    public int size() {
        return m_list.size();
    }

    @NonNull
    @Override
    public List<UIElement> subList(int start, int end) {
        return m_list.subList(start, end);
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return m_list.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] array) {
        return m_list.toArray(array);
    }
}

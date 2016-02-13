package net.daradara.xpf;

import net.daradara.xpf.delegate.Delegate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public class ContentElement extends UIElement {

    public void AddHandler(RoutedEvent routedEvent, Delegate handler)
    {
        m_handlers.put(routedEvent, handler);
    }

    protected DependencyObject getUIParentCore()
    {
        return null;
    }

    protected void processEvent(RoutedEventArgs e)
    {
        RoutedEvent routedEvent = e.getRoutedEvent();
        if(m_handlers.containsKey(routedEvent)) {
            Delegate d = m_handlers.get(routedEvent);
            d.invoke(e);
        }
        if(routedEvent.getRoutingStrategy() == RoutingStrategy.BUBBLE) {
            DependencyObject parent = getUIParentCore();
            if (parent != null) {
                if (parent instanceof ContentElement) {
                    ContentElement parentElement = (ContentElement) parent;
                    parentElement.processEvent(e);
                }
            }
        }
    }

    public void raiseEvent(RoutedEventArgs e)
    {
        processEvent(e);
    }

    private Map<RoutedEvent, Delegate> m_handlers = new HashMap<>();
}

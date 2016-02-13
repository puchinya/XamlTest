package net.daradara.xpf;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public class RoutedEventArgs {

    public boolean getHandled()
    {
        return m_handled;
    }

    public void setHandled(boolean value)
    {
        m_handled = value;
    }

    public RoutedEvent getRoutedEvent()
    {
        return m_routedEvent;
    }

    private boolean m_handled;
    private Object m_originalSource;
    private RoutedEvent m_routedEvent;
    private Object m_source;
}

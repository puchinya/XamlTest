package net.daradara.xpf;

import android.support.annotation.NonNull;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public class RoutedEvent {

    public @NonNull String getName()
    {
        return m_name;
    }

    public @NonNull Class getHandlerType()
    {
        return m_handlerType;
    }

    public @NonNull Class getOwnerType()
    {
        return m_ownerType;
    }

    public RoutingStrategy getRoutingStrategy()
    {
        return m_routingStrategy;
    }

    private Class m_handlerType;
    private String m_name;
    private Class m_ownerType;
    private RoutingStrategy m_routingStrategy = RoutingStrategy.BUBBLE;
}

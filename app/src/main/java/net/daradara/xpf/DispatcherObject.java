package net.daradara.xpf;

import android.support.annotation.NonNull;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public class DispatcherObject {

    public DispatcherObject()
    {
        m_dispatcher = Dispatcher.getCurrentDispatcher();
    }

    public @NonNull Dispatcher getDispatcher()
    {
        return m_dispatcher;
    }

    private Dispatcher m_dispatcher;
}

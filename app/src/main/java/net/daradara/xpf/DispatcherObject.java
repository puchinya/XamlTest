package net.daradara.xpf;

import android.support.annotation.NonNull;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public class DispatcherObject implements Cloneable {

    public DispatcherObject()
    {
        m_dispatcher = Dispatcher.getCurrentDispatcher();
    }

    @Override
    public DispatcherObject clone()
    {
        DispatcherObject r = null;

        try {
            r = (DispatcherObject)super.clone();
            r.m_dispatcher = m_dispatcher;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    public final @NonNull Dispatcher getDispatcher()
    {
        return m_dispatcher;
    }

    private Dispatcher m_dispatcher;
}

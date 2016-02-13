package net.daradara.xpf.delegate;

import java.util.LinkedList;

/**
 * Created by masatakanabeshima on 2016/01/21.
 */
public class event<TDelegate extends Delegate> {
    public event()
    {

    }

    public void add(TDelegate handler)
    {
        m_handlers.add(handler);
    }

    public void remove(TDelegate handler)
    {
        m_handlers.remove(handler);
    }

    public void invoke(Object... args)
    {
        for (TDelegate handler: m_handlers) {
            handler.invoke(args);
        }
    }

    private LinkedList<TDelegate> m_handlers = new LinkedList<>();
}

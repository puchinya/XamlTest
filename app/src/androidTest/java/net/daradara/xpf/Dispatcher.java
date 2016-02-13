package net.daradara.xpf;

import android.support.annotation.NonNull;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public class Dispatcher {

    @NonNull
    public static Dispatcher getCurrentDispatcher()
    {
        return m_currentDispatcher;
    }

    private static Dispatcher m_currentDispatcher = new Dispatcher();
}

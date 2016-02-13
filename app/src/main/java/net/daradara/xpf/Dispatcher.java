package net.daradara.xpf;

import android.support.annotation.NonNull;

import net.daradara.xpf.delegate.Delegate;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public class Dispatcher {

    private Dispatcher()
    {

    }

    private static final ThreadLocal<Dispatcher> currentDispatcher = new ThreadLocal<Dispatcher>()
    {
        @Override protected Dispatcher initialValue() {
            return new Dispatcher();
        }
    };

    public Object invoke(Delegate delegate, Object[] args,
                         DispatcherPriority priority)
    {
        return null;
    }

    @NonNull
    public static Dispatcher getCurrentDispatcher()
    {
        return currentDispatcher.get();
    }
}

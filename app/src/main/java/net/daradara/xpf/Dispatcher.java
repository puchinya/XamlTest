package net.daradara.xpf;

import android.support.annotation.NonNull;

import net.daradara.xpf.delegate.Delegate;

import android.os.Handler;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public final class Dispatcher {

    protected Dispatcher(@NonNull Thread thread)
    {
        m_thread = thread;
        m_handler = new Handler();
    }

    private static final ThreadLocal<Dispatcher> currentDispatcher = new ThreadLocal<Dispatcher>()
    {
        @Override protected Dispatcher initialValue() {
            return new Dispatcher(Thread.currentThread());
        }
    };

    public Object invoke(final @NonNull Delegate delegate, final Object[] args)
    {
        Thread currentThread = Thread.currentThread();
        if(currentThread == m_thread) {
            return delegate.invoke(args);
        } else {
            final Var result = new Var();

            synchronized (result) {
                m_handler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (result) {
                            result.value = delegate.invoke(args);
                            result.notify();
                        }
                    }
                });

                try {
                    result.wait();
                } catch (InterruptedException ex) {

                }
            }

            return result.value;
        }
    }

    public void beginInvoke(final @NonNull Delegate delegate, final Object[] args)
    {
        Thread currentThread = Thread.currentThread();
        if(currentThread == m_thread) {
            delegate.invoke(args);
        } else {
            m_handler.post(new Runnable() {
                @Override
                public void run() {
                    delegate.invoke(args);
                }
            });
        }
    }

    static class Var
    {
        public Object value;
    }

    public @NonNull Thread getThread()
    {
        return m_thread;
    }

    public static @NonNull Dispatcher getCurrentDispatcher()
    {
        return currentDispatcher.get();
    }

    @NonNull
    private Thread m_thread;

    @NonNull
    private Handler m_handler;
}

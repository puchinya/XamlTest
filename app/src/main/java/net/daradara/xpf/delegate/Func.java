package net.daradara.xpf.delegate;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public abstract class Func<TResult, TArg> implements Delegate {
    public abstract TResult invokeOverride(TArg arg);

    public final Object invoke(Object... args)
    {
        return invokeOverride((TArg)args[0]);
    }

}

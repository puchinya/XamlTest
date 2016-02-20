package net.daradara.xpf.delegate;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public abstract class Func2<TResult, TArg1, TArg2> implements Delegate {
    public abstract TResult invokeOverride(TArg1 arg1, TArg2 arg2);

    public final Object invoke(Object... args) {
        return invokeOverride((TArg1)args[0], (TArg2)args[1]);
    }
}

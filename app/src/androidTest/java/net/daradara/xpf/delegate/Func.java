package net.daradara.xpf.delegate;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public abstract class Func<TResult, TArg> implements Delegate {
    public abstract TResult invoke(TArg arg);

    public Object invoke(Object... args)
    {
        return invoke(args[0]);
    }

}

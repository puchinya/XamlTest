package net.daradara.xpf.delegate;

/**
 * Created by masatakanabeshima on 2016/01/10.
 */
public abstract class Func2<TResult, TArg1, TArg2> implements Delegate {
    public abstract TResult invoke(TArg1 arg1, TArg2 arg2);

    public Object invoke(Object... args) {
        return invoke(args[0], args[1]);
    }
}

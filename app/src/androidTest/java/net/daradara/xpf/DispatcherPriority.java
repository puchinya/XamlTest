package net.daradara.xpf;

/**
 * Created by masatakanabeshima on 2016/02/13.
 */
public enum DispatcherPriority {
    BACKGROUND(4),
    DATA_BIND(8),
    INPUT(5),
    RENDER(7),
    ;

    private final int id;

    DispatcherPriority(int id)
    {
        this.id = id;
    }
}

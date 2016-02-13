package net.daradara.xpf;

import net.daradara.xpf.delegate.Func;
import net.daradara.xpf.delegate.event;

/**
 * Created by masatakanabeshima on 2016/01/21.
 */
public interface NotifyPropertyChanged {
    event<Func<Void, String>> getPropertyChanged();
}

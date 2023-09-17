package com.jh.sgs.base.interfaces;

import com.jh.sgs.base.interactive.Interactive;

public interface InteractiveEvent {

    void cancel();

    void complete();
    int player();
    String message();
    Interactive interactive();
}

package com.jh.sgs.core.interactive;

import com.jh.sgs.core.InteractiveEvent;

public interface Interactiveable extends Interactive{


    /**
     * 流程的默认实现，在处理方不处理时调用此方法
     */
    void cancel();

    /**
     * 流程的完成检查，在处理方处理完成时调用此方法
     * @return 流程是否完成
     */
    InteractiveEvent.CompleteEnum complete();

//    int getPlayer();
}

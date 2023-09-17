package com.jh.sgs.base.interactive;


import java.io.Serializable;

public interface Interactiveable extends Interactive, Serializable {



    /**
     * 流程的默认实现，在处理方不处理时调用此方法
     */
    void cancel();

    /**
     * 流程的完成检查，在处理方处理完成时调用此方法
     * @return 流程是否完成
     */
    CompleteEnum complete();

//    int getPlayer();


    public enum CompleteEnum{
        COMPLETE,
        PROGRESS,
        NOEXECUTE
    }
}

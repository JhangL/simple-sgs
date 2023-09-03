package com.jh.sgs.core.pojo;

import lombok.Getter;

@Getter
public class EventLock {
    private boolean lock=false;

    private String event;

    public void setTrue(String event){
        lock=true;
        this.event=event;
    }
    public void setFalse(String event){
        lock=false;
        this.event=event;
    }
}

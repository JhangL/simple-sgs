package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.exception.DesktopErrorException;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.exception.DesktopRelationalException;

public class WuXieKeJi extends SilkbagCard {
    @Override
    public void begin() {


    }

    @Override
    public void effect() throws DesktopException {
        if (ContextManage.desktopStack().size() == 1)throw new DesktopErrorException("不可直接使用无懈可击");
        ContextManage.roundManage().wxkjCheck();
        throw new DesktopRelationalException("无懈可击生效");
    }

    @Override
    public void end() {

    }

    @Override
    String getName() {
        return "无懈可击";
    }
}

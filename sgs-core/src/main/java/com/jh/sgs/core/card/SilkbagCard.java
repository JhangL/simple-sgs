package com.jh.sgs.core.card;

import com.jh.sgs.core.desktop.CardDesktop;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.interfaces.MessageReceipt;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class SilkbagCard extends BaseCard implements Executable{
    @Override
    public void execute() throws DesktopException {
        log.debug("执行{}-->",getName());
        MessageReceipt.globalInContext(CardDesktop.playerInContext() +"使用"+ CardDesktop.cardInContext());
        begin();
        effect();
        end();
        MessageReceipt.globalInContext(CardDesktop.playerInContext() +"完成使用"+CardDesktop.cardInContext());
    }

    abstract public void begin();
    abstract public void effect() throws DesktopException;
    abstract public void end();
}

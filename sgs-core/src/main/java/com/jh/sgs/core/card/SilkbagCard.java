package com.jh.sgs.core.card;

import com.jh.sgs.core.desktop.CardDesktop;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.pojo.MessageReceipter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class SilkbagCard extends BaseCard implements Executable{
    @Override
    public void execute() throws DesktopException {
        log.debug("执行{}-->",getName());
        MessageReceipter.globalInContext(CardDesktop.playerInContext() +"使用"+ CardDesktop.cardInContext());
        begin();
        effect();
        end();
        MessageReceipter.globalInContext(CardDesktop.playerInContext() +"完成使用"+CardDesktop.cardInContext());
    }

    abstract public void begin();
    abstract public void effect() throws DesktopException;
    abstract public void end();
}

package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.exception.DesktopException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class SilkbagCard extends BaseCard implements Executable{
    @Override
    public void execute() throws DesktopException {
        log.debug("执行{}-->",getName());
        ContextManage.messageReceipt().global(ContextManage.executeCardDesktop().getPlayer() +"使用"+ContextManage.executeCardDesktop().getCard());
        begin();
        effect();
        end();
        ContextManage.messageReceipt().global(ContextManage.executeCardDesktop().getPlayer() +"完成使用"+ContextManage.executeCardDesktop().getCard());
    }

    abstract public void begin();
    abstract public void effect() throws DesktopException;
    abstract public void end();
}

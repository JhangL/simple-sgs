package com.jh.sgs.core.desktop;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.exception.DesktopErrorException;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.exception.DesktopRefuseException;
import com.jh.sgs.core.exception.DesktopRelationalException;
import com.jh.sgs.core.pojo.MessageReceipter;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class Desktop {
    @Getter
    private final int player;

    public Desktop(int player) {
        this.player = player;
    }


    /**
     * 执行操作
     */
    private void start() throws DesktopRefuseException {
        try {
            execute();
        } catch (DesktopException e) {
            if (e instanceof DesktopRelationalException) {
                log.debug("{} 执行发出关联阻挡", player);
                MessageReceipter.globalInContext(player + "完成阻挡" );
                end();
                throw new DesktopRefuseException(e.getMessage());
            } else if (e instanceof DesktopRefuseException) {
                log.debug("{} 执行阻挡", player);
                MessageReceipter.globalInContext(player + "被阻挡" );
            } else if (e instanceof DesktopErrorException) {
                MessageReceipter.globalInContext(player + "执行错误" );
                error();
                return;
            }
        }
        end();
    }

    protected abstract void initCheck();
    protected abstract void execute() throws DesktopException;

    protected abstract void end();

    protected abstract void error();


    public static class Stack extends java.util.Stack<Desktop> {
        public void create(Desktop desktop) {
            desktop.initCheck();
            push(desktop);
        }

        public void start() throws DesktopRefuseException {
            peek().start();
        }

        public void remove() throws DesktopRefuseException {
            if (empty()) {
                log.debug("不存在待处理桌面desktop");
                return;
            }
            try {
                peek().start();
            } finally {
                pop();
            }
        }
    }
    public static int playerInContext(){
        return ContextManage.desktopStack().peek().getPlayer();
    }

}

package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.desktop.CardDesktop;
import com.jh.sgs.core.exception.DesktopErrorException;
import com.jh.sgs.core.exception.DesktopException;
import com.jh.sgs.core.pojo.CompletePlayer;

public class Tao extends BaseCard implements Executable {
    @Override
    String getName() {
        return "桃";
    }

    @Override
    public void execute() throws DesktopException {
        int player = CardDesktop.playerInContext();
        CompletePlayer player1 = Util.getPlayer(player);
        if (player1.getBlood() == player1.getMaxBlood()) {
            throw new DesktopErrorException("血量已满");
        }
        player1.setBlood(player1.getBlood() + 1);
        ContextManage.roundManage().addBlood(player, player, CardDesktop.cardInContext());
    }
}

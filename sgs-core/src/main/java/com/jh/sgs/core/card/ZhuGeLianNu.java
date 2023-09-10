package com.jh.sgs.core.card;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.enums.CardEnum;
import com.jh.sgs.core.exception.DesktopException;
import lombok.extern.log4j.Log4j2;

@Log4j2

public class ZhuGeLianNu extends WeaponCard implements Loseable{

    @Override
    public void sha() throws DesktopException {
        //调用杀方法
        Shaable shaable = (Shaable) CardEnum.SHA.getBaseCard();
        shaable.sha();
    }

    @Override
    public void execute() {
        super.execute();
        log.debug("{} 特殊流程",getName());
        ContextManage.roundProcess(ContextManage.executeCardDesktop().getPlayer()).setLimitSha(100);
    }

    @Override
    String getName() {
        return "诸葛连弩";
    }

    @Override
    public void lose(int player) {
        ContextManage.roundProcess(player).setLimitSha(1);
    }




    @Override
    public int shaTarget() {
        return 0;
    }

    @Override
    public void shaExecute(int player) {

    }

    @Override
    public int shaDistance() {
        return 0;
    }
}

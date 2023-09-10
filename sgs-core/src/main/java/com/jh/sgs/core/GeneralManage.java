package com.jh.sgs.core;

import com.jh.sgs.core.enums.GeneralEnum;
import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.general.BaseGeneral;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.General;

import java.lang.reflect.Constructor;
import java.util.List;

public class GeneralManage {
    List<General> allGeneral;

    GeneralManage(List<General> allGeneral) {
        this.allGeneral = allGeneral;
    }

    public List<General> getAll() {
        return allGeneral;
    }

    public void setGeneral(CompletePlayer completePlayer) {
        GeneralEnum byId = GeneralEnum.getById(completePlayer.getCompleteGeneral().getGeneral().getId());
        try {
            Class[] parameterTypes = {CompletePlayer.class};
            //根据参数类型获取相应的构造函数
            Constructor<? extends BaseGeneral> constructor = byId.aClass.getConstructor(parameterTypes);
            BaseGeneral baseGeneral = constructor.newInstance(completePlayer);
            completePlayer.getCompleteGeneral().setBaseGeneral(baseGeneral);
        } catch (Exception e) {
            throw new SgsRuntimeException(e.getMessage());
        }
    }

}

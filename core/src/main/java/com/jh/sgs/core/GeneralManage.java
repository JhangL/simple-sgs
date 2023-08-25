package com.jh.sgs.core;

import com.jh.sgs.core.exception.SgsException;
import com.jh.sgs.core.general.BaseGeneral;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.General;
import com.jh.sgs.core.pojo.GeneralEnum;

import java.util.List;

public class GeneralManage {
    List<General> allGeneral;

    GeneralManage(List<General> allGeneral) {
        this.allGeneral = allGeneral;
    }

    public List<General> getAll() {
        return allGeneral;
    }

    public void setGeneral(CompletePlayer completePlayer)  {
        GeneralEnum byId = GeneralEnum.getById(completePlayer.getCompleteGeneral().getGeneral().getId());
        try {
            BaseGeneral baseGeneral = byId.aClass.newInstance();
            completePlayer.getCompleteGeneral().setBaseGeneral(baseGeneral);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new SgsException(e.getMessage());
        }
    }

}

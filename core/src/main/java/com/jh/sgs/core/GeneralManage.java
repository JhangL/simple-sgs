package com.jh.sgs.core;

import com.jh.sgs.exception.SgsException;
import com.jh.sgs.general.BaseGeneral;
import com.jh.sgs.pojo.CompletePlayer;
import com.jh.sgs.pojo.General;
import com.jh.sgs.pojo.GeneralEnum;

import java.util.List;

public class GeneralManage {
    List<General> allGeneral;

    public GeneralManage(List<General> allGeneral) {
        this.allGeneral = allGeneral;
    }

    public List<General> getAll() {
        return allGeneral;
    }

    public void setGeneral(CompletePlayer completePlayer,General general)  {
        GeneralEnum byId = GeneralEnum.getById(general.getId());
        try {
            BaseGeneral baseGeneral = byId.aClass.newInstance();
            general.setBaseGeneral(baseGeneral);

        } catch (InstantiationException | IllegalAccessException e) {
            throw new SgsException(e.getMessage());
        }
        completePlayer.setGeneral(general);
    }

}

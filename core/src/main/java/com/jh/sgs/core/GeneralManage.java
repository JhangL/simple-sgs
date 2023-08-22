package com.jh.sgs.core;

import com.jh.sgs.pojo.General;

import java.util.List;

public class GeneralManage {
    List<General> allGeneral;

    public GeneralManage(List<General> allGeneral) {
        this.allGeneral = allGeneral;
    }

    public List<General> getAll() {
        return allGeneral;
    }
}

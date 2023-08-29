package com.jh.sgs.core.interactive;

import com.jh.sgs.core.pojo.General;

import java.util.List;

public interface XZYX {

    /**
     * 选择武将<br/>
     * 阶段：选择武将
     */
    void setGeneral(int id);

    /**
     * 查看可选择武将<br/>
     * 阶段：选择武将
     */
   List<General> selectableGeneral();
}

package com.jh.sgs.core.interactive;

import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.pojo.General;

import java.util.List;

public interface XZYX {

    /**
     * 选择武将<br/>
     * 阶段：选择武将
     */
    default void setGeneral(int id) {
        throw SgsApiException.FFWSX;
    }

    /**
     * 查看可选择武将<br/>
     * 阶段：选择武将
     */
    default List<General> selectableGeneral() {
        throw SgsApiException.FFWSX;
    }
}

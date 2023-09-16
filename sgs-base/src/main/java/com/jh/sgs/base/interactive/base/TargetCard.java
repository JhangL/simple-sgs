package com.jh.sgs.base.interactive.base;


import com.jh.sgs.base.exception.SgsApiException;
import com.jh.sgs.base.pojo.Card;

import java.util.List;

public interface TargetCard {
    /**
     * 查看目标牌<br/>
     * 阶段：五谷丰登执行
     */
    default List<Card> targetCard() {
        throw SgsApiException.FFWSX;
    }
}

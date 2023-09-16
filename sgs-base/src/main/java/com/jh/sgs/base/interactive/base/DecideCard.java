package com.jh.sgs.base.interactive.base;


import com.jh.sgs.base.exception.SgsApiException;
import com.jh.sgs.base.pojo.Card;

import java.util.List;

public interface DecideCard {
    /**
     * 查看判定牌<br/>
     * 阶段：顺手牵羊执行
     */
    default List<Card> decideCard() {
        throw SgsApiException.FFWSX;
    }
}

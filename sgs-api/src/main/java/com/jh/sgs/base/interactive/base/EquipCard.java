package com.jh.sgs.base.interactive.base;

import com.jh.sgs.base.exception.SgsApiException;
import com.jh.sgs.base.pojo.Card;

import java.util.List;

public interface EquipCard {
    /**
     * 查看装备牌<br/>
     * 阶段：顺手牵羊执行
     */
    default List<Card> equipCard() {
        throw SgsApiException.FFWSX;
    }
}

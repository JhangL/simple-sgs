package com.jh.sgs.core.interactive.base;

import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.pojo.Skill;

import java.util.List;

public interface SetSkill {
    default void setSkill(int id){
        throw SgsApiException.FFWSX;
    }

    default List<Skill> showSkill(){
        throw SgsApiException.FFWSX;
    }
}

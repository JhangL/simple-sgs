package com.jh.sgs.core;

import com.jh.sgs.base.pojo.General;
import com.jh.sgs.base.pojo.Skill;
import com.jh.sgs.core.enums.GeneralEnum;
import com.jh.sgs.core.exception.SgsRuntimeException;
import com.jh.sgs.core.general.BaseGeneral;
import com.jh.sgs.core.pojo.CompletePlayer;
import lombok.Getter;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralManage {
    List<General> allGeneral;
    @Getter
    Map<GeneralEnum, BaseGeneral> baseGeneralMap = new HashMap<>();

    Map<Integer,Skill> skillMap;

    GeneralManage(List<General> allGeneral, Map<Integer, Skill> skill) {
        this.allGeneral = allGeneral;
        this.skillMap=skill;
        for (General general : allGeneral) {
            int[] skillIds = general.getSkillIds();
            Skill[] skills = new Skill[skillIds.length];
            for (int i = 0; i < skillIds.length; i++) {
                int skillId = skillIds[i];
                Skill skill1 = skillMap.get(skillId);
                skills[i]=skill1;

            }
            general.setSkills(skills);
        }
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
            baseGeneralMap.put(byId, baseGeneral);
        } catch (Exception e) {
            throw new SgsRuntimeException(e.getMessage());
        }
    }

}

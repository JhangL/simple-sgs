package com.jh.sgs.core;

import com.alibaba.fastjson2.JSON;
import com.jh.sgs.core.interfaces.ShowStatus;
import com.jh.sgs.core.pojo.IdentityEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class IdentityManage implements ShowStatus {
    Map<String, Integer> usingIdentity;

    IdentityManage(Map<String, Integer> map) {
        usingIdentity = map;
    }

    List<IdentityEnum> distribute() {
        ArrayList<IdentityEnum> identities = new ArrayList<>();
        for (String s : usingIdentity.keySet()) {
            Integer i = usingIdentity.get(s);
            for (int j = 0; j < i; j++) {
                identities.add(IdentityEnum.getByName(s));
            }
        }
        Collections.shuffle(identities);
        return identities;
    }


    @Override
    public String getStatus() {
        return "{" +
                "\"usingIdentity\":" + JSON.toJSONString(usingIdentity) +
                '}';
    }
}

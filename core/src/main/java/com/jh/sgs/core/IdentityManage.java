package com.jh.sgs.core;

import com.jh.sgs.pojo.Identity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class IdentityManage {
    Map<String, Integer> usingIdentity;

    public IdentityManage(Map<String, Integer> map) {
        usingIdentity = map;
    }

    List<Identity> distribute() {
        ArrayList<Identity> identities = new ArrayList<>();
        for (String s : usingIdentity.keySet()) {
            Integer i = usingIdentity.get(s);
            for (int j = 0; j < i; j++) {
                identities.add(Identity.getByName(s));
            }
        }
        Collections.shuffle(identities);
        return identities;
    }
}

package com.jh.sgs.text;

import com.jh.sgs.core.data.DataBaseBasicData;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.General;

import java.util.List;
import java.util.Map;

public class DataBaseBasicDataCache extends DataBaseBasicData {
    List<Card> a;
    Map<Integer, Map<String, String>> b;
    Map<String, Integer> c;
    List<General> d;

    public DataBaseBasicDataCache(String propFilePath) {
        super(propFilePath);
    }

    @Override
    public synchronized List<Card> getCards() {
        if (a==null){
           a=super.getCards();
        }
        return a;
    }

    @Override
    public synchronized Map<Integer, Map<String, String>> getCardParameter() {
        if (b==null){
            b=super.getCardParameter();
        }
        return b;
    }

    @Override
    public synchronized Map<String, Integer> getIdentity(int num) {
        if (c==null){
            c=super.getIdentity(num);
        }
        return c;
    }

    @Override
    public synchronized List<General> getGenerals() {
        if (d==null){
            d=super.getGenerals();
        }
        return d;
    }
}

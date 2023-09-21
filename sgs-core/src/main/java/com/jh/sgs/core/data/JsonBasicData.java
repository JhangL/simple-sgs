package com.jh.sgs.core.data;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pojo.General;
import com.jh.sgs.base.pojo.Skill;
import com.jh.sgs.core.interfaces.BasicData;

import java.util.*;

public class JsonBasicData implements BasicData {

    private JSONObject jsonObject;

    public JsonBasicData(String json) {
        this.jsonObject = JSON.parseObject(json);

    }

    @Override
    public List<Card> getCards() {
        JSONArray jsonArray = jsonObject.getJSONArray("card");
        return jsonArray.toList(Card.class);
    }

    @Override
    public Map<Integer, Map<String, String>> getCardParameter() {
        HashMap<Integer, Map<String, String>> integerMapHashMap = new HashMap<>();
        JSONArray jsonArray = jsonObject.getJSONArray("card_parameter");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            HashMap<String, String> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("name", jsonObject1.getString("name"));
            stringStringHashMap.put("remark", jsonObject1.getString("remark"));
            integerMapHashMap.put(jsonObject1.getInteger("id"), stringStringHashMap);
        }
        return integerMapHashMap;
    }

    @Override
    public Map<String, Integer> getIdentity(int num) {
        JSONArray jsonArray = jsonObject.getJSONArray("identity");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            int num1 = jsonObject1.getIntValue("num");
            if (num1 == num) {
                HashMap<String, Integer> identity = new HashMap<>();
                identity.put("zhu", jsonObject1.getInteger("zhu"));
                identity.put("zhong", jsonObject1.getInteger("zhong"));
                identity.put("fan", jsonObject1.getInteger("fan"));
                identity.put("nei", jsonObject1.getInteger("nei"));
                return identity;
            }
        }
        return null;
    }

    @Override
    public List<General> getGenerals() {
        JSONArray jsonArray = jsonObject.getJSONArray("general");
        ArrayList<General> generals = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            General general = new General();
            general.setId(jsonObject1.getIntValue("id"));
            general.setName(jsonObject1.getString("name"));
            general.setCountry(jsonObject1.getString("country"));
            general.setBlood(jsonObject1.getIntValue("blood"));
            String[] skillIds = jsonObject1.getString("skill_ids").split(",");
            general.setSkillIds(Arrays.stream(skillIds).mapToInt(Integer::parseInt).toArray());
            generals.add(general);
        }
        return generals;
    }

    @Override
    public Map<Integer, Skill> getSkill() {
        JSONArray jsonArray = jsonObject.getJSONArray("skill");
        HashMap<Integer, Skill> integerSkillHashMap = new HashMap<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            Skill skill = new Skill();
            skill.setId(jsonObject1.getIntValue("id"));
            skill.setName(jsonObject1.getString("name"));
            skill.setRemake(jsonObject1.getString("remake"));
            integerSkillHashMap.put(skill.getId(),skill);
        }
        return integerSkillHashMap;
    }
}

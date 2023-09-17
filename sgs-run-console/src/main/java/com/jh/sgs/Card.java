package com.jh.sgs;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Card {

    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "nameId")
    private Integer nameId;
    @JSONField(name = "num")
    private String num;
    @JSONField(name = "status")
    private String status;
    @JSONField(name = "suit")
    private Integer suit;

    @Override
    public String toString() {
        return "{"+status+"}";
    }
}

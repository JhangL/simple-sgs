package com.jh.sgs.pojo;

public enum IdentityEnum {
    ZG("zhu"), ZC("zhong"), FZ("fan"), NJ("nei");
    private final String name;

    IdentityEnum(String name) {
        this.name = name;
    }

    public static IdentityEnum getByName(String name) {
        for (IdentityEnum value : IdentityEnum.values()) {
            if (value.name.equals(name)) return value;
        }
        return null;
    }
}

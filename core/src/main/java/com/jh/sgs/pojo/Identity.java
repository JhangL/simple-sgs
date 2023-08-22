package com.jh.sgs.pojo;

public enum Identity {
    ZG("zhu"), ZC("zhong"), FZ("fan"), NJ("nei");
    private final String name;

    Identity(String name) {
        this.name = name;
    }

    public static Identity getByName(String name) {
        for (Identity value : Identity.values()) {
            if (value.name.equals(name)) return value;
        }
        return null;
    }
}

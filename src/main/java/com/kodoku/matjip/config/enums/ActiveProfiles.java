package com.kodoku.matjip.config.enums;

import lombok.Getter;

public enum ActiveProfiles {
    LOCAL("local"),
    STAGE("stage"),
    EXAMPLE("example"),
    PRODUCT("product");

    @Getter
    private String lowerCaseName;

    ActiveProfiles(String profile) {
        this.lowerCaseName = profile;
    }
}

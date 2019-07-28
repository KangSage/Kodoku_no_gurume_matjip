package com.kodoku.matjip.config.enums;

import lombok.Getter;

public enum ActiveProfiles {
    LOCAL("local"),
    STAGE("stage"),
    EXAMPLE("example");

    @Getter
    private String nameLowerCase;

    ActiveProfiles(String profile) {
        this.nameLowerCase = profile;
    }
}

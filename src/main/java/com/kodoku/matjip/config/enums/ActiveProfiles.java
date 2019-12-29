package com.kodoku.matjip.config.enums;

import lombok.Getter;

public enum ActiveProfiles {
  LOCAL("local", 1),
  STAGE("stage", 2),
  EXAMPLE("example", 3),
  PRODUCT("product", 4);

  @Getter private String lowerCaseName;

  @Getter private int no;

  ActiveProfiles(String profile, int no) {
    this.lowerCaseName = profile;
    this.no = no;
  }
}

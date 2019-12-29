package com.kodoku.matjip.config.enums;

import lombok.Getter;

public enum ResponseBodyResult {
  SUCCESS("success"),
  FAILURE("failure");

  @Getter private String result;

  ResponseBodyResult(String result) {
    this.result = result;
  }
}

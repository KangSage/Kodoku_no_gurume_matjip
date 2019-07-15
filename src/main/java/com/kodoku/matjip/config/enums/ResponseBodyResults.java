package com.kodoku.matjip.config.enums;

import lombok.Getter;

import java.net.MalformedURLException;
import java.net.URL;

public enum ResponseBodyResults {

    SUCCESS("success"),
    FAIL("fail");

    @Getter
    private String result;

    ResponseBodyResults(String result) {
        this.result = result;
    }

}

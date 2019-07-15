package com.kodoku.matjip.entity.enums;

public enum SocialType {

    FACEBOOK("facebook"),
    GOOGLE("google"),
    TWITTER("twitter"),
    KAKAO("kakao"),
    NAVER("naver"),
    LINE("line"),
    GENERAL("general");

    private final String ROLE_PRIFIX = "ROLE_";
    private String name;
    StringBuilder strBuilder = new StringBuilder();

    SocialType(String name) {
        this.name = name;
    }

    public String getRoleType() {
        strBuilder.append(ROLE_PRIFIX).append(name.toUpperCase());
        return strBuilder.toString();
    }

    public String getValue() {
        return name;
    }

    public boolean isEquals(String authority) {
        return this.getRoleType().equals(authority);
    }


}

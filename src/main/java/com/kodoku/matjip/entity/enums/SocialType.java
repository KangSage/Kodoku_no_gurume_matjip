package com.kodoku.matjip.entity.enums;

public enum SocialType {

    FACEBOOK("facebook"),
    GOOGLE("google"),
    TWITTER("twitter"),
    KAKAO("kakao"),
    NAVER("naver"),
    LINE("line"),
    GENERAL("general");

    private String socialName;

    SocialType(String socialName) {
        this.socialName = socialName;
    }

    public String getSocialName() {
        return socialName;
    }

}

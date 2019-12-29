package com.kodoku.matjip.entity.enums;

public enum RoleType {
  ROLE_USER {
    @Override
    public String getForwardUrl() {
      return "/html/user/list.html";
    }
  },
  ROLE_ADMIN {
    @Override
    public String getForwardUrl() {
      return "/html/admin/list.html";
    }
  },
  ROLE_TEMP {
    @Override
    public String getForwardUrl() {
      return "/html/login.html";
    }
  };
  //    private String roleType;
  //    RoleType(String roleType) {
  //        this.roleType = roleType;
  //    }

  public boolean isEquals(String authority) {
    return name().equals(authority);
  }

  public abstract String getForwardUrl();
}

package com.kodoku.matjip.entity;

import javax.persistence.Transient;

public class PagingEntity {
    @Transient
    Long startPage;
    @Transient
    Long finishPage;
}

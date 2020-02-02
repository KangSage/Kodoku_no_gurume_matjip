package com.kodoku.matjip.entity;

import java.io.Serializable;
import javax.persistence.Transient;

public interface PagingEntity extends Serializable {
  @Transient Long startPage = 1L;
  @Transient Long finishPage = 10L;
}

package com.wdeanmedical.portal.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "health_trend_report")
public class HealthTrendReport extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -6472438611028481446L;
  
  public static final Integer VITAL_SIGNS = 1;
  public static final Integer DM_DATA = 2;
  public static final Integer LIPIDS = 3;
  public static final Integer BP = 4;
  public static final Integer T_CELL = 5;
  
  private String name;

  public HealthTrendReport() {
  }

  @Column(name = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

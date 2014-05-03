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
@Table(name = "patient_health_trend_report")
public class PatientHealthTrendReport extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 6364564784378683322L;
  private Patient patient;
  private HealthTrendReport healthTrendReport;

  public PatientHealthTrendReport() {
  }



  @JoinColumn(name = "patient", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public Patient getPatient() {
    return patient;
  }
  public void setPatient(Patient patient) {
    this.patient = patient;
  }
  
  @JoinColumn(name = "health_trend_report", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public  HealthTrendReport getHealthTrendReport() {
    return healthTrendReport;
  }
  public void setHealthTrendReport(HealthTrendReport healthTrendReport) {
    this.healthTrendReport = healthTrendReport;
  }

}

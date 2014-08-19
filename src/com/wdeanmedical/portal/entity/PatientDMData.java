package com.wdeanmedical.portal.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "patient_dm_data")
public class PatientDMData extends BaseEntity implements Serializable {
  
  private static final long serialVersionUID = -2879757912027185161L;
  private Date date;
  private Integer patientId;
  private Integer clinicianId;
  private float hgba1c; 
  private float meanGlucose; 
  private float creatine; 
  private String egfr; 
  private int tg;
  private int ldl;
  private int weight;
  private float bmi;
  private int sys;
  private int dia;
  
  public PatientDMData() {
  }
  
  @Column(name = "date")
  public Date getDate() { return date; }
  public void setDate(Date date) { this.date = date; }
  
  @Column(name = "patient_id")
  public Integer getPatientId() { return patientId; }
  public void setPatientId(Integer patientId) { this.patientId = patientId; }

  @Column(name = "clinician_id")
  public Integer getClinicianId() { return clinicianId; }
  public void setClinicianId(Integer clinicianId) { this.clinicianId = clinicianId; }

  @Column(name = "hgbaic")
  public float getHgba1c() { return hgba1c; }
  public void setHgba1c(float hgba1c) { this.hgba1c = hgba1c; }

  @Column(name = "mean_glucose")
  public float getMeanGlucose() { return meanGlucose; }
  public void setMeanGlucose(float meanGlucose) { this.meanGlucose = meanGlucose; }

  @Column(name = "creatine")
  public float getCreatine() { return creatine; }
  public void setCreatine(float creatine) { this.creatine = creatine; }

  @Column(name = "egfr")
  public String getEgfr() { return egfr; }
  public void setEgfr(String egfr) { this.egfr = egfr; }

  @Column(name = "tg")
  public int getTg() { return tg; }
  public void setTg(int tg) { this.tg = tg; }

  @Column(name = "ldl")
  public int getLdl() { return ldl; }
  public void setLdl(int ldl) { this.ldl = ldl; }

  @Column(name = "sys")
  public int getSys() { return sys; }
  public void setSys(int sys) { this.sys = sys; }

  @Column(name = "dia")
  public int getDia() { return dia; }
  public void setDia(int dia) { this.dia = dia; }
  
  @Column(name = "weight")
  public int getWeight() { return weight; }
  public void setWeight(int weight) { this.weight = weight; }

  @Column(name = "bmi")
  public float getBmi() { return bmi; }
  public void setBmi(float bmi) { this.bmi = bmi; }

}

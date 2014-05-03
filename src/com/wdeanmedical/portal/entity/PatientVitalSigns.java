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
@Table(name = "patient_vital_signs")
public class PatientVitalSigns extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1342327774068897447L;
  
  private Date date;
  private Patient patient;
  private Clinician clinician;
  private int height;
  private int weight;
  private float bmi;
  private float ofc;
  private float temperature;
  private int pulse;
  private int respiration;
  private int systolic;
  private int diastolic;
  private float oximetry;

  public PatientVitalSigns() {
  }
  
  @Column(name = "date")
  public Date getDate() { return date; }
  public void setDate(Date date) { this.date = date; }
  
  @JoinColumn(name = "patient", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public Patient getPatient() { return patient; }
  public void setPatient(Patient patient) { this.patient = patient; }

  @JoinColumn(name = "clinician", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public Clinician getClinician() { return clinician; }
  public void setClinician(Clinician clinician) { this.clinician = clinician; }

  @Column(name = "height")
  public int getHeight() { return height; }
  public void setHeight(int height) { this.height = height; }

  @Column(name = "weight")
  public int getWeight() { return weight; }
  public void setWeight(int weight) { this.weight = weight; }

  @Column(name = "bmi")
  public float getBmi() { return bmi; }
  public void setBmi(float bmi) { this.bmi = bmi; }

  @Column(name = "ofc")
  public float getOfc() { return ofc; }
  public void setOfc(float ofc) { this.ofc = ofc; }

  @Column(name = "temperature")
  public float getTemperature() { return temperature; }
  public void setTemperature(float temperature) { this.temperature = temperature; }

  @Column(name = "pulse")
  public int getPulse() { return pulse; }
  public void setPulse(int pulse) { this.pulse = pulse; }

  @Column(name = "respiration")
  public int getRespiration() { return respiration; }
  public void setRespiration(int respiration) { this.respiration = respiration; }

  @Column(name = "systolic")
  public int getSystolic() { return systolic; }
  public void setSystolic(int systolic) { this.systolic = systolic; }

  @Column(name = "diastolic")
  public int getDiastolic() { return diastolic; }
  public void setDiastolic(int diastolic) { this.diastolic = diastolic; }

  @Column(name = "oximetry")
  public float getOximetry() { return oximetry; }
  public void setOximetry(float oximetry) { this.oximetry = oximetry; }

}

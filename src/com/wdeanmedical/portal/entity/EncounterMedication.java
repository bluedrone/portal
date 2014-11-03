package com.wdeanmedical.portal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "encounter_medication")
public class EncounterMedication extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 548926604494760704L;
  
  private String medication;
  private String dose;
  private String frequency;
  private int patientId;
  private Integer medicalHistoryId;
  private MedicalHistory medicalHistory;
  private Integer patientMedicalHistoryId;
  private PatientMedicalHistory patientMedicalHistory;

  public EncounterMedication() {
  }

  @Column(name = "medication")
  public String getMedication() { return medication; }
  public void setMedication(String medication) { this.medication = medication; }

  @Column(name = "dose")
  public String getDose() { return dose; }
  public void setDose(String dose) { this.dose = dose; }

  @Column(name = "frequency")
  public String getFrequency() { return frequency; }
  public void setFrequency(String frequency) { this.frequency = frequency; }
  
  @Column(name = "patient_id")
  public int getPatientId() { return patientId; }
  public void setPatientId(int patientId) { this.patientId = patientId; }
  
  @Column(name = "medical_history_id")
  public Integer getMedicalHistoryId() {
	return medicalHistoryId;
  }

  public void setMedicalHistoryId(Integer medicalHistoryId) {
	this.medicalHistoryId = medicalHistoryId;
  }

  @JoinColumn(name = "medical_history_id", referencedColumnName = "id", insertable = false, updatable = false)
  public MedicalHistory getMedicalHistory() {
	return medicalHistory;
  }
	
  public void setMedicalHistory(MedicalHistory medicalHistory) {
	this.medicalHistory = medicalHistory;
  }    
  
  @Column(name = "patient_medical_history_id")
  public Integer getPatientMedicalHistoryId() {
	return patientMedicalHistoryId;
  }

  public void setPatientMedicalHistoryId(Integer patientMedicalHistoryId) {
	this.patientMedicalHistoryId = patientMedicalHistoryId;
  }

  @JoinColumn(name = "patient_medical_history_id", referencedColumnName = "id", insertable = false, updatable = false)
  public PatientMedicalHistory getPatientMedicalHistory() {
	return patientMedicalHistory;
  }

  public void setPatientMedicalHistory(PatientMedicalHistory patientMedicalHistory) {
	this.patientMedicalHistory = patientMedicalHistory;
  }
  
}

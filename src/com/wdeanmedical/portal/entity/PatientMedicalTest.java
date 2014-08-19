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
@Table(name = "patient_medical_test")
public class PatientMedicalTest extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -1122130358806883720L;
  private Integer patientId;
  private Integer clinicianId;
  private Clinician clinician;
  private MedicalTest medicalTest;
  private MedicalTestStatus status;
  private Date date;

  public PatientMedicalTest() {
  }

  @Column(name = "patient_id")
  public Integer getPatientId() { return patientId; }
  public void setPatientId(Integer patientId) { this.patientId = patientId; }

  @Column(name = "clinician_id")
  public Integer getClinicianId() { return clinicianId; }
  public void setClinicianId(Integer clinicianId) { this.clinicianId = clinicianId; }
  
  @JoinColumn(name = "clinician", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public Clinician getClinician() { return clinician; }
  public void setClinician(Clinician clinician) { this.clinician = clinician; }
  
  @JoinColumn(name = "medical_test", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public  MedicalTest getMedicalTest() { return medicalTest; }
  public void setMedicalTest(MedicalTest medicalTest) { this.medicalTest = medicalTest; }

  @Column(name = "date")
  public Date getDate() { return date; }
  public void setDate(Date date) { this.date = date; }

  @JoinColumn(name = "status", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public MedicalTestStatus getStatus() { return status; }
  public void setStatus(MedicalTestStatus status) { this.status = status; }

}

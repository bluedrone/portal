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
@Table(name = "patient_lipids")
public class PatientLipids extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -2002635222227901060L;
  
  private Date date;
  private Integer patientId;
  private Integer clinicianId;
  private int chol;
  private int hdl;
  private int ldl;
  private int trig;
  private int alt;

  public PatientLipids() {
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

  @Column(name = "chol")
  public int getChol() { return chol; }
  public void setChol(int chol) { this.chol = chol; }

  @Column(name = "hdl")
  public int getHdl() { return hdl; }
  public void setHdl(int hdl) { this.hdl = hdl; }

  @Column(name = "ldl")
  public int getLdl() { return ldl; }
  public void setLdl(int ldl) { this.ldl = ldl; }

  @Column(name = "trig")
  public int getTrig() { return trig; }
  public void setTrig(int trig) { this.trig = trig; }

  @Column(name = "alt")
  public int getAlt() { return alt; }
  public void setAlt(int alt) { this.alt = alt; }

}

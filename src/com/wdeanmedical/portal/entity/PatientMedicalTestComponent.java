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
@Table(name = "patient_medical_test_component")
public class PatientMedicalTestComponent extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 448635476533025146L;
  private PatientMedicalTest patientMedicalTest;
  private String name;
  private String testValue;
  private String testRange;
  private String units;
  private String flag;

  public PatientMedicalTestComponent() {
  }

  @JoinColumn(name = "patient_medical_test", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public PatientMedicalTest getPatientMedicalTest() { return patientMedicalTest; }
  public void setPatientMedicalTest(PatientMedicalTest patientMedicalTest) { this.patientMedicalTest = patientMedicalTest; }
  
  @Column(name = "name")
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  @Column(name = "test_value")
  public String getTestValue() { return testValue; }
  public void setTestValue(String testValue) { this.testValue = testValue; }

  @Column(name = "test_range")
  public String getTestRange() { return testRange; }
  public void setTestRange(String testRange) { this.testRange = testRange; }

  @Column(name = "units")
  public String getUnits() { return units; }
  public void setUnits(String units) { this.units = units; }

  @Column(name = "flag")
  public String getFlag() { return flag; }
  public void setFlag(String flag) { this.flag = flag; }
 
}

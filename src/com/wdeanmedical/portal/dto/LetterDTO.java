package com.wdeanmedical.portal.dto;

import java.util.Date;


public class LetterDTO extends AuthorizedDTO {
  private int id;
  private int patientId;
  private Date date;
  private String status;
  private String reason;
  private String content;
  private int clinicianId;
  

  public LetterDTO() {
  }

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }

  public int getPatientId() {
    return patientId;
  }
  public void setPatientId(int patientId) {
    this.patientId = patientId;
  }

  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }

  public Date getDate() {
    return date;
  }
  public void setDate(Date date) {
    this.date = date;
  }

  public int getClinicianId() {
    return clinicianId;
  }
  public void setClinicianId(int clinicianId) {
    this.clinicianId = clinicianId;
  }

  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }

  public String getReason() {
    return reason;
  }
  public void setReason(String reason) {
    this.reason = reason;
  }

}

package com.wdeanmedical.portal.dto;


public class ContactMessageDTO  {
  private int patientId;
  private String name;
  private String email;
  private String phone;
  private String comments;
  

  public ContactMessageDTO() {
  }


  public int getPatientId() {
    return patientId;
  }
  public void setPatientId(int patientId) {
    this.patientId = patientId;
  }


  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getComments() {
    return comments;
  }
  public void setComments(String comments) {
    this.comments = comments;
  }

}

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
@Table(name = "patient_session")
public class PatientSession extends BaseEntity implements Serializable {
  private static final long serialVersionUID = -4156072292651636347L;
  private Patient patient;
  private String ipAddress;
  private String sessionId;
  private Date  lastAccessTime;

  public PatientSession() {
  }

  @Column(name = "ip_address")
  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  @JoinColumn(name = "patient", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  @Column(name = "session_id")
  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
  
  @Column(name = "last_access_time")
  @Temporal(TemporalType.TIMESTAMP)
  @Basic(optional = false)
  public Date getLastAccessTime() {
    return lastAccessTime;
  }
  public void setLastAccessTime(Date lastAccessTime) {
    this.lastAccessTime = lastAccessTime;
  }
  
  @Override
  public String toString() {
    return "PatientSession[" + getSessionId() + ", " + getPatient().getCred().getUsername() + ", " + getIpAddress() + ", " + getLastAccessTime() + "]";
  }
    
  @Override
  public boolean equals(Object object) {
    if (!(object instanceof PatientSession)) {
      return false;
    }
    PatientSession other = (PatientSession) object;
    if ((getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
      return false;
    }
    return true;
  }

}

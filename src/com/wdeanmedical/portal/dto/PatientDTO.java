/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */
 
package com.wdeanmedical.portal.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.wdeanmedical.portal.entity.Appointment;
import com.wdeanmedical.portal.entity.Patient;
import com.wdeanmedical.portal.entity.PatientAllergen;
import com.wdeanmedical.portal.entity.PatientClinician;
import com.wdeanmedical.portal.entity.PatientDMData;
import com.wdeanmedical.portal.entity.PatientHealthIssue;
import com.wdeanmedical.portal.entity.PatientHealthTrendReport;
import com.wdeanmedical.portal.entity.PatientImmunization;
import com.wdeanmedical.portal.entity.PatientLetter;
import com.wdeanmedical.portal.entity.PatientLipids;
import com.wdeanmedical.portal.entity.PatientMedicalProcedure;
import com.wdeanmedical.portal.entity.PatientMedicalTest;
import com.wdeanmedical.portal.entity.PatientMedicalTestComponent;
import com.wdeanmedical.portal.entity.PatientMedication;
import com.wdeanmedical.portal.entity.PatientMessage;
import com.wdeanmedical.portal.entity.VitalSigns;

public class PatientDTO extends AuthorizedDTO {
  private Patient patient;
  private int id;
  private String username;
  private String password;
  private String firstName;
  private String middleName;
  private String lastName;
  private String ssn;
  private String dob;
  private String gender;
  private Integer maritalStatus;
  private Integer race;
  private Integer ethnicity;
  private String streetAddress1;
  private String streetAddress2;
  private String city;
  private Integer usState;
  private String postalCode;
  private String employed;
  private String employer;
  private String school;
  private String schoolName;
  private boolean motherAlive; 
  private String motherDeathReason; 
  private boolean fatherAlive; 
  private String fatherDeathReason; 
  private String pastSM; 
  private String famHist; 
  private String famHistOther; 
  private String famHistNotes; 
  private String allergFood; 
  private String allergDrug; 
  private String allergEnv; 
  private String subst; 
  private String smokePksDay; 
  private String yearsSmoked; 
  private String smokeYearsQuit; 
  private String primaryPhone;
  private String secondaryPhone;
  private String email;
  private boolean active;
  private boolean purged;
  private String salt;
  private int authStatus;
  private String sessionId;
  private Date lastLoginTime;
  private int patientMedicalTestId;
  private PatientMedicalTest patientMedicalTest;
  private String previousLoginTime;
  private List<PatientAllergen> patientAllergens;
  private List<PatientMedication> patientMedications;
  private List<PatientImmunization> patientImmunizations;
  private List<PatientHealthIssue> patientHealthIssues;
  private List<PatientMedicalTest> patientMedicalTests;
  private List<PatientMedicalProcedure> patientMedicalProcedures;
  private List<PatientHealthTrendReport> patientHealthTrendReports;
  private List<PatientLetter> patientLetters;
  private List<PatientMessage> patientMessages;
  private List<VitalSigns> vitalSigns;
  private List<PatientDMData> patientDMData;
  private List<PatientLipids> patientLipids;
  private List<PatientClinician> patientClinicians;
  private List<PatientMedicalTestComponent> patientMedicalTestComponents;
  private List<Appointment> appointments;
  private String occupation;
  private String employmentStatus;
  private String schoolStatus;
  private String profileImagePath;
  private boolean updateEmail;
  private boolean updatePassword;

  public PatientDTO() {
  }


  public Patient getPatient() { return patient; }
  public void setPatient(Patient patient) { this.patient = patient; }

  public String getPassword() { return this.password; }
  public void setPassword(String password) { this.password = password; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }
  
  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }
  
  public String getMiddleName() { return middleName; }
  public void setMiddleName(String middleName) { this.middleName = middleName; }
  
  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  
  public String getPrimaryPhone() { return primaryPhone; }
  public void setPrimaryPhone(String primaryPhone) { this.primaryPhone = primaryPhone; }
  
  public String getSecondaryPhone() { return secondaryPhone; }
  public void setSecondaryPhone(String secondaryPhone) { this.secondaryPhone = secondaryPhone; }

  public boolean isActive() { return active; }
  public void setActive(boolean active) { this.active = active; }

  public String getSalt() { return salt; }
  public void setSalt(String salt) { this.salt = salt; }
  
  public int getAuthStatus() { return authStatus; }
  public void setAuthStatus(int authStatus) { this.authStatus = authStatus; }

  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }

  public boolean isPurged() { return purged; }
  public void setPurged(boolean purged) { this.purged = purged; }

  public String getSessionId() { return sessionId; }
  public void setSessionId(String sessionId) { this.sessionId = sessionId; }

  public Date getLastLoginTime() { return lastLoginTime; }
  public void setLastLoginTime(Date lastLoginTime) { this.lastLoginTime = lastLoginTime; }

  public String getPreviousLoginTime() { return previousLoginTime; }
  public void setPreviousLoginTime(String previousLoginTime) { this.previousLoginTime = previousLoginTime; }
  
  public String getSsn() { return ssn; }
  public void setSsn(String ssn) { this.ssn = ssn; }

  public String getDob() { return dob; }
  public void setDob(String dob) { this.dob = dob; }

  public String getGender() { return gender; }
  public void setGender(String gender) { this.gender = gender; }

  public Integer getMaritalStatus() { return maritalStatus; }
  public void setMaritalStatus(Integer maritalStatus) { this.maritalStatus = maritalStatus; }

  public Integer getRace() { return race; }
  public void setRace(Integer race) { this.race = race; }

  public Integer getEthnicity() { return ethnicity; }
  public void setEthnicity(Integer ethnicity) { this.ethnicity = ethnicity; }

  public String getCity() { return city; }
  public void setCity(String city) { this.city = city; }

  public Integer getUsState() { return usState; }
  public void setUsState(Integer usState) { this.usState = usState; }

  public String getPostalCode() { return postalCode; }
  public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

  public String getEmployed() { return employed; }
  public void setEmployed(String employed) { this.employed = employed; }

  public String getEmployer() { return employer; }
  public void setEmployer(String employer) { this.employer = employer; }

  public String getSchool() { return school; }
  public void setSchool(String school) { this.school = school; }

  public String getSchoolName() { return schoolName; }
  public void setSchoolName(String schoolName) { this.schoolName = schoolName; }

  public boolean isMotherAlive() { return motherAlive; }
  public void setMotherAlive(boolean motherAlive) { this.motherAlive = motherAlive; }

  public String getMotherDeathReason() { return motherDeathReason; }
  public void setMotherDeathReason(String motherDeathReason) { this.motherDeathReason = motherDeathReason; }

  public boolean isFatherAlive() { return fatherAlive; }
  public void setFatherAlive(boolean fatherAlive) { this.fatherAlive = fatherAlive; }

  public String getFatherDeathReason() { return fatherDeathReason; }
  public void setFatherDeathReason(String fatherDeathReason) { this.fatherDeathReason = fatherDeathReason; }

  public String getPastSM() { return pastSM; }
  public void setPastSM(String pastSM) { this.pastSM = pastSM; }

  public String getFamHist() { return famHist; }
  public void setFamHist(String famHist) { this.famHist = famHist; }

  public String getFamHistOther() { return famHistOther; }
  public void setFamHistOther(String famHistOther) { this.famHistOther = famHistOther; }

  public String getFamHistNotes() { return famHistNotes; }
  public void setFamHistNotes(String famHistNotes) { this.famHistNotes = famHistNotes; }

  public String getAllergFood() { return allergFood; }
  public void setAllergFood(String allergFood) { this.allergFood = allergFood; }

  public String getAllergDrug() { return allergDrug; }
  public void setAllergDrug(String allergDrug) { this.allergDrug = allergDrug; }

  public String getAllergEnv() { return allergEnv; }
  public void setAllergEnv(String allergEnv) { this.allergEnv = allergEnv; }

  public String getSubst() { return subst; }
  public void setSubst(String subst) { this.subst = subst; }

  public String getSmokePksDay() { return smokePksDay; }
  public void setSmokePksDay(String smokePksDay) { this.smokePksDay = smokePksDay; }

  public String getYearsSmoked() { return yearsSmoked; }
  public void setYearsSmoked(String yearsSmoked) { this.yearsSmoked = yearsSmoked; }

  public String getSmokeYearsQuit() { return smokeYearsQuit; }
  public void setSmokeYearsQuit(String smokeYearsQuit) { this.smokeYearsQuit = smokeYearsQuit; }

  public List<PatientAllergen> getPatientAllergens() { return patientAllergens; }
  public void setPatientAllergens(List<PatientAllergen> patientAllergens) { this.patientAllergens = patientAllergens; }

  public List<PatientMedication> getPatientMedications() { return patientMedications; }
  public void setPatientMedications(List<PatientMedication> patientMedications) { this.patientMedications = patientMedications; }

  public List<PatientImmunization> getPatientImmunizations() { return patientImmunizations; }
  public void setPatientImmunizations(List<PatientImmunization> patientImmunizations) { this.patientImmunizations = patientImmunizations; }

  public List<PatientHealthIssue> getPatientHealthIssues() { return patientHealthIssues; }
  public void setPatientHealthIssues(List<PatientHealthIssue> patientHealthIssues) { this.patientHealthIssues = patientHealthIssues; }

  public List<PatientMedicalTest> getPatientMedicalTests() { return patientMedicalTests; }
  public void setPatientMedicalTests(List<PatientMedicalTest> patientMedicalTests) { this.patientMedicalTests = patientMedicalTests; }
  
  public List<PatientMedicalProcedure> getPatientMedicalProcedures() { return patientMedicalProcedures; }
  public void setPatientMedicalProcedures(List<PatientMedicalProcedure> patientProcedures) { this.patientMedicalProcedures = patientProcedures; }
  
  public List<PatientHealthTrendReport> getPatientHealthTrendReports() { return patientHealthTrendReports; }
  public void setPatientHealthTrendReports(List<PatientHealthTrendReport> patientHealthTrendReports) { this.patientHealthTrendReports = patientHealthTrendReports; }

  public List<PatientLetter> getPatientLetters() { return patientLetters; }
  public void setPatientLetters(List<PatientLetter> patientLetters) { this.patientLetters = patientLetters; }

  public List<PatientMessage> getPatientMessages() { return patientMessages; }
  public void setPatientMessages(List<PatientMessage> patientMessages) { this.patientMessages = patientMessages; }
  
  public List<Appointment> getAppointments() { return appointments; }
  public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }

  public List<PatientClinician> getPatientClinicians() { return patientClinicians; }
  public void setPatientClinicians(List<PatientClinician> patientClinicians) { this.patientClinicians = patientClinicians; }

  public int getPatientMedicalTestId() { return patientMedicalTestId; }
  public void setPatientMedicalTestId(int patientMedicalTestId) { this.patientMedicalTestId = patientMedicalTestId; }

  public PatientMedicalTest getPatientMedicalTest() { return patientMedicalTest; }
  public void setPatientMedicalTest(PatientMedicalTest patientMedicalTest) { this.patientMedicalTest = patientMedicalTest; }

  public List<PatientMedicalTestComponent> getPatientMedicalTestComponents() { return patientMedicalTestComponents; }
  public void setPatientMedicalTestComponents( List<PatientMedicalTestComponent> patientMedicalTestComponents) {
 this.patientMedicalTestComponents = patientMedicalTestComponents; }

  public List<VitalSigns> getVitalSigns() { return vitalSigns; }
  public void setVitalSigns(List<VitalSigns> vitalSigns) { this.vitalSigns = vitalSigns; }

  public List<PatientDMData> getPatientDMData() { return patientDMData; }
  public void setPatientDMData(List<PatientDMData> patientDMData) { this.patientDMData = patientDMData; }

  public List<PatientLipids> getPatientLipids() { return patientLipids; }
  public void setPatientLipids(List<PatientLipids> patientLipids) { this.patientLipids = patientLipids; }
  
  public String getStreetAddress1() { return streetAddress1; }
  public void setStreetAddress1(String streetAddress1) { this.streetAddress1 = streetAddress1; }

  public String getStreetAddress2() { return streetAddress2; }
  public void setStreetAddress2(String streetAddress2) { this.streetAddress2 = streetAddress2; }

  public String getOccupation() { return occupation; }
  public void setOccupation(String occupation) { this.occupation = occupation; }
  
  public String getEmploymentStatus() { return employmentStatus; }
  public void setEmploymentStatus(String employmentStatus) { this.employmentStatus = employmentStatus; }
  
  public String getSchoolStatus() { return schoolStatus; }
  public void setSchoolStatus(String schoolStatus) { this.schoolStatus = schoolStatus; }
  
  public String getProfileImagePath() { return profileImagePath; }
  public void setProfileImagePath(String profileImagePath) { this.profileImagePath = profileImagePath; }

  public boolean isUpdateEmail() { return updateEmail; }
  public void setUpdateEmail(boolean updateEmail) { this.updateEmail = updateEmail; }

  public boolean isUpdatePassword() { return updatePassword; }
  public void setUpdatePassword(boolean updatePassword) { this.updatePassword = updatePassword; }
  
}

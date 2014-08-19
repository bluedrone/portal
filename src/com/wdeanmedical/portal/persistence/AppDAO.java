/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */
 
package com.wdeanmedical.portal.persistence;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.wdeanmedical.portal.entity.Ethnicity;
import com.wdeanmedical.portal.entity.Race;
import com.wdeanmedical.portal.entity.MaritalStatus;
import com.wdeanmedical.portal.entity.Gender;
import com.wdeanmedical.portal.entity.USState;
import com.wdeanmedical.portal.core.Core;
import com.wdeanmedical.portal.entity.Appointment;
import com.wdeanmedical.portal.entity.BaseEntity;
import com.wdeanmedical.portal.entity.Clinician;
import com.wdeanmedical.portal.entity.Credentials;
import com.wdeanmedical.portal.entity.Demographics;
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
import com.wdeanmedical.portal.entity.PatientMessageType;
import com.wdeanmedical.portal.entity.PatientSession;
import com.wdeanmedical.portal.entity.PatientStatus;
import com.wdeanmedical.portal.entity.VitalSigns;
import com.wdeanmedical.portal.util.OneWayPasswordEncoder;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AppDAO extends SiteDAO {

  private static final Logger log = Logger.getLogger(AppDAO.class);

  private SessionFactory sessionFactory;

  public AppDAO() {
    super();
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  protected Session getSession() {
    return this.sessionFactory.getCurrentSession();
  }
  
  public void create(BaseEntity item) throws Exception {
    this.createEntity(item);
  }
  
  public void update(BaseEntity item) throws Exception {
    this.updateEntity(item);
  }
  
  public void delete(BaseEntity item) throws Exception {
    this.deleteEntity(item);
  }
  
  
  public List<PatientAllergen> getPatientAllergens(Patient patient) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientAllergen.class);
    crit.add(Restrictions.eq("patient", patient));
    List<PatientAllergen> list =  crit.list();
    return list;
  }
  
  public List<PatientMedication> getPatientMedications(Integer patientId) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientMedication.class);
    crit.add(Restrictions.eq("patientId", patientId));
    List<PatientMedication> list =  crit.list();
    return list;
  }
  
  public List<PatientImmunization> getPatientImmunizations(Integer patientId) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientImmunization.class);
    crit.add(Restrictions.eq("patientId", patientId));
    List<PatientImmunization> list =  crit.list();
    return list;
  }
  
  public List<PatientHealthIssue> getPatientHealthIssues(Integer patientId) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientHealthIssue.class);
    crit.add(Restrictions.eq("patientId", patientId));
    List<PatientHealthIssue> list =  crit.list();
    return list;
  }
  
  public List<PatientMedicalTest> getPatientMedicalTests(Integer patientId) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientMedicalTest.class);
    crit.add(Restrictions.eq("patientId", patientId));
    List<PatientMedicalTest> list =  crit.list();
    return list;
  }
  
  public List<PatientMedicalProcedure> getPatientMedicalProcedures(Integer patientId) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientMedicalProcedure.class);
    crit.add(Restrictions.eq("patientId", patientId));
    List<PatientMedicalProcedure> list =  crit.list();
    return list;
  }
  
  public List<PatientHealthTrendReport> getPatientHealthTrendReports(Integer patientId) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientHealthTrendReport.class);
    crit.add(Restrictions.eq("patientId", patientId));
    List<PatientHealthTrendReport> list =  crit.list();
    return list;
  }
  
  public List<PatientLetter> getPatientLetters(Patient patient) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientLetter.class);
    crit.add(Restrictions.eq("patient", patient));
    List<PatientLetter> list =  crit.list();
    return list;
  }
  
  public List<PatientMessage> getPatientMessages(Patient patient, Boolean fromClinician) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientMessage.class);
    crit.add(Restrictions.eq("patient", patient));
    crit.add(Restrictions.eq("fromClinician", fromClinician));
    List<PatientMessage> list =  crit.list();
    return list;
  }
  
  public List<Appointment> getAppointments(Patient patient, boolean isPast) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(Appointment.class);
    crit.add(Restrictions.eq("patient", patient));
    if (isPast) {
      crit.add(Restrictions.lt("startTime", new Date()));
    }
    else {
      crit.add(Restrictions.ge("startTime", new Date()));
    }
    List<Appointment> list =  crit.list();
    return list;
  }
  
  
  public List <VitalSigns> getPatientVitalSigns(Integer patientId) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(VitalSigns.class);
    crit.add(Restrictions.eq("patientId", patientId));
    crit.addOrder(Order.desc("date"));
    List<VitalSigns> list =  crit.list();
    return list;
  }
  
  public List <PatientDMData> getPatientDMData(Integer patientId) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientDMData.class);
    crit.add(Restrictions.eq("patientId", patientId));
    crit.addOrder(Order.desc("date"));
    List<PatientDMData> list =  crit.list();
    return list;
  }
  
  public List <PatientLipids> getPatientLipids(Integer patientId) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientLipids.class);
    crit.add(Restrictions.eq("patientId", patientId));
    crit.addOrder(Order.desc("date"));
    List<PatientLipids> list =  crit.list();
    return list;
  }
  
  public Patient findPatientById(int id) throws Exception {
    return (Patient) this.findById(Patient.class, id);
  }

  public Patient findPatientByEmail(String email) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(Credentials.class);
    crit.add(Restrictions.eq("email", email));
    Credentials cred = (Credentials) crit.uniqueResult();
    crit = session.createCriteria(Patient.class);
    crit.add(Restrictions.eq("cred", cred));
    Patient patient = (Patient) crit.uniqueResult();
    return patient;
  }
 
  
  public Boolean checkEmail(String email) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(Credentials.class);
    crit.add(Restrictions.eq("email", email));
    Credentials cred = (Credentials) crit.uniqueResult();
    return (cred == null);
  }
  
  public PatientMessageType findPatientMessageTypeById(int id) throws Exception {
    return (PatientMessageType) this.findById(PatientMessageType.class, id);
  }
  
  public PatientMedicalTest findPatientMessageTestById(int id) throws Exception {
    return (PatientMedicalTest) this.findById(PatientMedicalTest.class, id);
  }
  
  public List <PatientMedicalTestComponent> findPatientMedicalTestComponentByTestId(int id) throws Exception {
    PatientMedicalTest patientMedicalTest = findPatientMessageTestById(id);
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientMedicalTestComponent.class);
    crit.add(Expression.eq("patientMedicalTest", patientMedicalTest));
    List<PatientMedicalTestComponent> list = crit.list();
    return list;
  }
  
  public PatientMessage findPatientMessageById(int id) throws Exception {
    PatientMessage patientMessage = (PatientMessage) this.findById(PatientMessage.class, id);
    if (patientMessage.getFromClinician() == true) {
      patientMessage.setReadByRecipient(true);
      update(patientMessage);
    }
    return patientMessage;
  }
  
  public PatientLetter findPatientLetterById(int id, int patientId) throws Exception {
    PatientLetter patientLetter = (PatientLetter) this.findById(PatientLetter.class, id);
    patientLetter.setReadByRecipient(true);
    update(patientLetter);
    return patientLetter;
  }
  
  public Clinician findClinicianById(int id) throws Exception {
    return (Clinician) this.findById(Clinician.class, id);
  }
  
  
  public Patient findPatientBySessionId(String sessionId ) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientSession.class);
    crit.add(Restrictions.eq("sessionId", sessionId));
    PatientSession patientSession = (PatientSession) crit.uniqueResult();
    return (Patient) this.findById(Patient.class, patientSession.getPatient().getId());
  }
  
  public void deletePatientSession(String sessionId) {
    Session session = this.getSession();
    String hql = "delete from PatientSession ps where ps.sessionId = :sessionId";
    Query query = session.createQuery(hql);
    query.setParameter("sessionId", sessionId);
    query.executeUpdate();
  }
  
  
  public void deleteExpiredPatientSessions() throws Exception {
    Session session = getSession(); 
    Calendar timeoutThreshold = Calendar.getInstance();
    timeoutThreshold.add(Calendar.MINUTE, 0 - Core.sessionTimeout);
    Date  expireTime = timeoutThreshold.getTime();
    String hql = "delete from PatientSession ps where ps.lastAccessTime < :expireTime";
    Query query = session.createQuery(hql);
    query.setParameter("expireTime", expireTime);
    query.executeUpdate();
  }
  
  
   public Patient authenticatePatient(String username, String password) throws Exception {
    log.info("testing username: " + username);
    Patient patient = findPatientByUsername(username);
    if (patient == null) {
      patient = new Patient();
      Credentials cred = new Credentials();
      cred.setAuthStatus(Patient.STATUS_NOT_FOUND);
      patient.setCred(cred);
      return patient;
    }
    String encodedPassword = OneWayPasswordEncoder.getInstance().encode(password, patient.getCred().getSalt());

    Session session = this.getSession();
    Criteria crit = session.createCriteria(Credentials.class);
    crit.add(Expression.eq("username", username));
    crit.add(Expression.eq("password", encodedPassword));
    Credentials cred = (Credentials) crit.uniqueResult();
    crit = session.createCriteria(Patient.class);
    crit.add(Expression.eq("cred", cred));
    patient = (Patient) crit.uniqueResult();
    if (patient != null) {
      patient.getCred().setAuthStatus(Patient.STATUS_AUTHORIZED);
      if (patient.getCred().getStatus().getId() == PatientStatus.INACTIVE) {
        patient.getCred().setAuthStatus(Patient.STATUS_INACTIVE);
      } 
      else {
        DateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
        cred = patient.getCred();
        cred.setPreviousLoginTime(patient.getCred().getLastLoginTime() != null ? df.format(patient.getCred().getLastLoginTime().getTime()) : "");
        cred.setLastLoginTime(new Date());
        cred.setSessionId(UUID.randomUUID().toString());
        update(cred);
      }
    } 
    else {
      patient = new Patient();
      cred = new Credentials();
      cred.setAuthStatus(Patient.STATUS_INVALID_PASSWORD);
      patient.setCred(cred);
    }
    return patient;
  }
  
  
  public Patient authenticatePatientViaActivationCode(String activationCode) throws Exception {
    Patient patient = findPatientByActivationCode(activationCode);
    if (patient == null) {
      patient = new Patient();
      Credentials cred = new Credentials();
      cred.setAuthStatus(Patient.STATUS_NOT_FOUND);
      patient.setCred(cred);
      return patient;
    }
    else {
      patient.getCred().setAuthStatus(Patient.STATUS_AUTHORIZED);
      if (patient.getCred().getStatus().getId() == PatientStatus.INACTIVE) {
        patient.getCred().setAuthStatus(Patient.STATUS_INACTIVE);
      } 
      else {
        DateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
        Credentials cred = patient.getCred();
        cred.setPreviousLoginTime(patient.getCred().getLastLoginTime() != null ? df.format(patient.getCred().getLastLoginTime().getTime()) : "");
        cred.setLastLoginTime(new Date());
        cred.setSessionId(UUID.randomUUID().toString());
        update(cred);
      }
    }
    return patient;
  }
  
  
  public Patient findPatientByUsername(String username) throws Exception {
    Session session = this.getSession();
    Patient patient = null;
    Criteria crit = session.createCriteria(Credentials.class);
    crit.add(Expression.eq("username", username));
    Credentials cred = (Credentials) crit.uniqueResult();
    crit = session.createCriteria(Patient.class);
    crit.add(Expression.eq("cred", cred));
    patient = (Patient) crit.uniqueResult();
    return patient;
  }
  
  public Patient findPatientByActivationCode(String code) throws Exception {
    Session session = this.getSession();
    Patient patient = null;
    Criteria crit = session.createCriteria(Credentials.class);
    crit.add(Expression.eq("activationCode", code));
    Credentials cred = (Credentials) crit.uniqueResult();
    crit = session.createCriteria(Patient.class);
    crit.add(Expression.eq("cred", cred));
    patient = (Patient) crit.uniqueResult();
    return patient;
  }
  
  public PatientSession findPatientSessionBySessionId(String sessionId ) throws Exception {
    Session session = this.getSession();
    PatientSession item = null;
    Criteria crit = session.createCriteria(PatientSession.class);
    crit.add(Expression.eq("sessionId", sessionId));
    item = (PatientSession) crit.uniqueResult();
    return item;
  }
  
  public List<PatientClinician> getPatientClinicians(Patient patient) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientClinician.class);
    crit.add(Restrictions.eq("patient", patient));
    List<PatientClinician> list =  crit.list();
    return list;
  }
  
  public List<USState> getUSStates() throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(USState.class);
    crit.addOrder(Order.asc("name"));
    List<USState> list =  crit.list();
    return list;
  }
  
   public Gender findGenderByCode(String code) throws Exception {
    Session session = getSession();
    Criteria crit = session.createCriteria(Gender.class);
    crit.add(Restrictions.eq("code", code));
    return (Gender)crit.uniqueResult();
  }
  
  public Race findRaceById(Integer id) throws Exception {
    return (Race) this.findById(Race.class, id);
  }
  
  public Credentials findCredentialsById(Integer id) throws Exception {
    return (Credentials) this.findById(Credentials.class, id);
  }
  
  public MaritalStatus findMaritalStatusById(Integer id) throws Exception {
    return (MaritalStatus) this.findById(MaritalStatus.class, id);
  }
  
  public Ethnicity findEthnicityById(Integer id) throws Exception {
    return (Ethnicity) this.findById(Ethnicity.class, id);
  }
  
  public USState findUSStateById(int id) throws Exception {
    return (USState) this.findById(USState.class, id);
  }
  
  public void updateCredentials(Credentials credValues, boolean updatePassword, boolean updateEmail) throws Exception {
    Credentials cred = findCredentialsById(credValues.getId());
    if (updateEmail) {
      cred.setEmail(credValues.getEmail());
      cred.setUsername(credValues.getEmail());
    }
    if (updatePassword) {
      cred.setPassword(credValues.getPassword());
      cred.setSalt(credValues.getSalt());
    }
    cred.setFirstName(credValues.getFirstName());
    cred.setMiddleName(credValues.getMiddleName());
    cred.setLastName(credValues.getLastName());
    cred.setActivationCode("");
    
    Session session = this.getSession();
    session.update(cred);
  }
  
  
  public void updatePatientProfileImage(Patient patient, String path) throws Exception {
    Session session = this.getSession();
    Demographics demo = patient.getDemo();
    demo.setProfileImagePath(path);
    session.update(demo);
  }
  
  
  
  public List<Appointment> getAllAppointments() throws Exception {
    return this.findAll(Appointment.class);
  }
  
  
  
  public List<Appointment> getAllAppointmentsByPatient(Patient patient) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(Appointment.class);
    crit.add(Restrictions.eq("patient", patient));
    List<Appointment> list =  crit.list();
    return list;
  }
  
  
  
  public Appointment findAppointmentById(int id) throws Exception {
    return (Appointment) this.findById(Appointment.class, id);
  }

}

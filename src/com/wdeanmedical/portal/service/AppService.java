/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */
 
package com.wdeanmedical.portal.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wdeanmedical.portal.util.DataEncryptor;
import com.wdeanmedical.portal.dto.AppointmentDTO;
import com.wdeanmedical.portal.service.ActivityLogService;
import com.wdeanmedical.portal.core.Core;
import com.wdeanmedical.portal.core.Permissions;
import com.wdeanmedical.portal.dto.AuthorizedDTO;
import com.wdeanmedical.portal.dto.BooleanResultDTO;
import com.wdeanmedical.portal.dto.ContactMessageDTO;
import com.wdeanmedical.portal.dto.LetterDTO;
import com.wdeanmedical.portal.dto.LoginDTO;
import com.wdeanmedical.portal.dto.MessageDTO;
import com.wdeanmedical.portal.dto.PasswordResetDTO;
import com.wdeanmedical.portal.dto.PatientDTO;
import com.wdeanmedical.portal.entity.Appointment;
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
import com.wdeanmedical.portal.entity.VitalSigns;
import com.wdeanmedical.portal.persistence.AppDAO;
import com.wdeanmedical.portal.util.MailHandler;
import com.wdeanmedical.portal.util.OneWayPasswordEncoder;
import com.wdeanmedical.portal.util.PatientSessionData;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.Ostermiller.util.RandPass;
import com.google.gson.Gson;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AppService {

  private static Log log = LogFactory.getLog(AppService.class);
  
  public static int RETURN_CODE_DUP_EMAIL = -1;
  public static int RETURN_CODE_INVALID_PASSWORD = -2;
  
  private ServletContext context;
  private WebApplicationContext wac;
  private AppDAO appDAO;
  private ActivityLogService activityLogService;


  public AppService() throws MalformedURLException {
    context = Core.servletContext;
    wac = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
    appDAO = (AppDAO) wac.getBean("appDAO");
    activityLogService = new ActivityLogService();
  }
  
  public  List<PatientAllergen> getPatientAllergens(PatientDTO dto) throws Exception {
    Patient patient = appDAO.findPatientById(dto.getId());
    activityLogService.logViewPatient(patient.getId(), null, patient.getId(), "GetPatientAllergens");
    return appDAO.getPatientAllergens(patient);
  }
  
  public List<VitalSigns> getPatientVitalSigns(PatientDTO dto) throws Exception {
    Integer patientId = dto.getId();
    activityLogService.logViewPatient(patientId, null, patientId, "GetPatientVitalSigns");
    return appDAO.getPatientVitalSigns(patientId);
  }
  
  public List<PatientDMData> getPatientDMData(PatientDTO dto) throws Exception {
    Integer patientId = dto.getId();
    activityLogService.logViewPatient(patientId, null, patientId, "GetPatientDMData");
    return appDAO.getPatientDMData(patientId);
  }
  
  public List<PatientLipids> getPatientLipids(PatientDTO dto) throws Exception {
    Integer patientId = dto.getId();
    activityLogService.logViewPatient(patientId, null, patientId, "GetPatientLipids");
    return appDAO.getPatientLipids(patientId);
  }
  
  public List<PatientMedication> getPatientMedications(PatientDTO dto) throws Exception {
    Integer patientId = dto.getId();
    activityLogService.logViewPatient(patientId, null, patientId, "GetPatientMedications");
    return appDAO.getPatientMedications(patientId);
  }
  
  public List<PatientImmunization> getPatientImmunizations(PatientDTO dto) throws Exception {
    Integer patientId = dto.getId();
    activityLogService.logViewPatient(patientId, null, patientId, "GetPatientImmunizations");
    return appDAO.getPatientImmunizations(patientId);
  }
  
  public List<PatientHealthIssue> getPatientHealthIssues(PatientDTO dto) throws Exception {
    Integer patientId = dto.getId();
    activityLogService.logViewPatient(patientId, null, patientId, "GetPatientHealthIssues");
    return appDAO.getPatientHealthIssues(patientId);
  }
  
  public List<PatientMedicalTest> getPatientMedicalTests(PatientDTO dto) throws Exception {
    Integer patientId = dto.getId();
    activityLogService.logViewPatient(patientId, null, patientId, "GetPatientMedicalTests");
    return appDAO.getPatientMedicalTests(patientId);
  }
  
  public List<PatientMedicalProcedure> getPatientMedicalProcedures(PatientDTO dto) throws Exception {
    Integer patientId = dto.getId();
    activityLogService.logViewPatient(patientId, null, patientId, "GetPatientMedicalProcedures");
    return appDAO.getPatientMedicalProcedures(patientId);
  }
  
  public List<PatientHealthTrendReport> getPatientHealthTrendReports(PatientDTO dto) throws Exception {
    Integer patientId = dto.getId();
    activityLogService.logViewPatient(patientId, null, patientId, "GetPatientHealthTrendReports");
    return appDAO.getPatientHealthTrendReports(patientId);
  }
  
  public List<PatientLetter> getPatientLetters(PatientDTO dto) throws Exception {
    Patient patient = appDAO.findPatientById(dto.getId());
    activityLogService.logViewPatient(patient.getId(), null, patient.getId(), "GetPatientLetters");
    List<PatientLetter> letters = appDAO.getPatientLetters(patient);
    for (PatientLetter pl : letters) { decrypt(pl.getPatient()); }
    return letters;
  }
  
  public  List<PatientMessage> getPatientMessages(PatientDTO dto, Boolean fromClinician) throws Exception {
    Patient patient = appDAO.findPatientById(dto.getId());
    activityLogService.logViewPatient(patient.getId(), null, patient.getId(), "GetPatientMessages");
    List<PatientMessage> messages = appDAO.getPatientMessages(patient, fromClinician);
    for (PatientMessage pm : messages) { decrypt(pm.getPatient()); }
    return messages;
  }
  
  public List<Appointment> getAppointments(PatientDTO dto, boolean isPast) throws Exception {
    Patient patient = appDAO.findPatientById(dto.getId());
    activityLogService.logViewPatient(patient.getId(), null, patient.getId(), "GetAppointments");
    List<Appointment> appointments = appDAO.getAppointments(patient, isPast);
    for (Appointment a : appointments) { decrypt(a.getPatient()); }
    return appointments;
  }
  
  public String buildAppointmentMessage(MessageDTO dto) throws Exception {
    Patient patient = appDAO.findPatientById(dto.getPatientId());
    decrypt(patient);
    String message = "Appointment Request from: ";
    message += patient.getCred().getFirstName() + " " + patient.getCred().getMiddleName() + " " + patient.getCred().getLastName() + "\n";
    message += "Visit Reason: " + dto.getVisitReason() + "\n"; 
    Clinician clinician = appDAO.findClinicianById(dto.getClinicianId());
    String clinicianFullName = clinician.getFirstName() + " " + clinician.getMiddleName() + " " + clinician.getLastName() + "\n";
    message += "Wants to See: " + clinicianFullName + "\n"; 
    message += "Would See: " + dto.getWouldSee() + "\n"; 
    message += "Preferred Dates: " + dto.getApptFrom() + " - " + dto.getApptTo() + "\n"; 
    message += "Preferred Times: " + dto.getPreferredTimes() + "\n"; 
    activityLogService.logViewPatient(patient.getId(), null, patient.getId(), "BuildAppointmentMessage");
    return message;
  } 
  
  public boolean processMessage(MessageDTO dto, int messageTypeId) throws Exception {
    PatientMessage message = new PatientMessage();
    String content = dto.getContent();
    if (messageTypeId == PatientMessageType.APPT_REQUEST) {  
      content = buildAppointmentMessage(dto);
      content 
      += "======================================\n" +
      "Patient Message: \n" +
      dto.getContent();
      
    }
    message.setContent(content);
    message.setDate(new Date());
    message.setSubject(dto.getSubject());
    message.setClinician(appDAO.findClinicianById(dto.getClinicianId()));
    message.setPatient(appDAO.findPatientById(dto.getPatientId()));
    message.setFromClinician(false);
    message.setPatientMessageType(appDAO.findPatientMessageTypeById(messageTypeId));
    appDAO.create(message);
    activityLogService.logViewPatient(dto.getPatientId(), null, dto.getPatientId(), "ProcessMessage");
    return true;
  }
  
  public boolean getPatientMedicalTestComponents(PatientDTO dto) throws Exception {
    List <PatientMedicalTestComponent> patientMedicalTestComponents = appDAO.findPatientMedicalTestComponentByTestId(dto.getPatientMedicalTestId());
    dto.setPatientMedicalTestComponents(patientMedicalTestComponents);
    activityLogService.logViewPatient(dto.getId(), null, dto.getId(), "GetPatientMedicalTestComponents");    
    return true;
  }
  
  public boolean getPatientMessage(MessageDTO dto) throws Exception {
    Patient patient = appDAO.findPatientBySessionId(dto.getSessionId());
    PatientMessage patientMessage = appDAO.findPatientMessageById(dto.getId());
    dto.setContent(patientMessage.getContent());
    dto.setFromClinician(patientMessage.getFromClinician());
    activityLogService.logViewPatient(patient.getId(), null, patient.getId(), "GetPatientMedicalTestComponents");
    return true;
  }
  
  public boolean getPatientLetter(LetterDTO dto) throws Exception {
    Patient patient = appDAO.findPatientBySessionId(dto.getSessionId());
    PatientLetter patientLetter = appDAO.findPatientLetterById(dto.getId(), patient.getId());
    dto.setContent(patientLetter.getContent());
    activityLogService.logViewPatient(patient.getId(), null, patient.getId(), "GetPatientLetter");
    return true;
  }
  
  public  boolean sendContactMessage(HttpServletRequest request, ContactMessageDTO dto) throws Exception {
    String to = dto.getEmail();
    String autoto = "nick@wdeanmedical.com";
    String from = Core.mailFrom;
    String name = dto.getName();
    String subject = "Contact Message From: " + name;
    String comment = dto.getComments();
  
  
    String osinfo = System.getProperty("os.name");
    String browserinfo = request.getHeader("User-Agent");
    String title = subject;
  
    String templatePath = context.getRealPath("/WEB-INF/email_templates");
    StringTemplateGroup group = new StringTemplateGroup("underwebinf", templatePath, DefaultTemplateLexer.class);

    StringTemplate feedbackConfirmation = group.getInstanceOf("feedback_confirmation");
    StringTemplate feedbackDetail = group.getInstanceOf("feedback_detail");
    
    feedbackConfirmation.setAttribute("to", "nick@wdeanmedical.com");
    feedbackConfirmation.setAttribute("from", "Pleasantville Medical.");
 
    feedbackDetail.setAttribute("name", name);
    feedbackDetail.setAttribute("email", to);
    feedbackDetail.setAttribute("subject", subject);
    feedbackDetail.setAttribute("comment", comment);
    feedbackDetail.setAttribute("osinfo", osinfo);
    feedbackDetail.setAttribute("browserinfo", browserinfo);
    
    MailHandler handler = new MailHandler();

    handler.sendMimeMessage(autoto, from, feedbackDetail.toString(), title, true);
    handler.sendMimeMessage(to, from, feedbackConfirmation.toString(), title, true);
    
    return true;
  }
  
  
  public void saveNewPatient(PatientDTO dto, HttpServletRequest request) throws Exception{
    Patient patient = dto.getPatient();
    
    if (dto.isUpdatePassword()) {
      if (testPassword(patient.getCred().getPassword()) == false) {
        dto.setResult(false);
        dto.setErrorMsg("Insufficient Password");
        dto.setReturnCode(RETURN_CODE_INVALID_PASSWORD);
        return;
      }
      String salt = UUID.randomUUID().toString();
      patient.getCred().setSalt(salt);
      String encodedPassword = OneWayPasswordEncoder.getInstance().encode(patient.getCred().getPassword(), salt);
      patient.getCred().setPassword(encodedPassword);
    }
    
    if(dto.isUpdateEmail()) {
      if(appDAO.checkEmail(DataEncryptor.encrypt(patient.getCred().getEmail())) == false) {
        dto.setResult(false);
        dto.setErrorMsg("Email already in system");
        dto.setReturnCode(RETURN_CODE_DUP_EMAIL);
        return;
      }
    }
     
    appDAO.updateCredentials(patient.getCred(), dto.isUpdatePassword(), dto.isUpdateEmail());
    appDAO.update(patient.getPfsh());
    appDAO.update(patient.getHist());
    Demographics demo = patient.getDemo();
    demo.setEthnicity(appDAO.findEthnicityById(demo.getEthnicity().getId()));
    demo.setMaritalStatus(appDAO.findMaritalStatusById(demo.getMaritalStatus().getId()));
    if (demo.getUsState() != null) {
      demo.setUsState(appDAO.findUSStateById(demo.getUsState().getId()));
    }
    demo.setRace(appDAO.findRaceById(demo.getRace().getId()));
    appDAO.update(patient.getDemo());
    appDAO.update(patient);
    decrypt(patient);
    String patientFullName = patient.getCred().getFirstName() + " " + patient.getCred().getLastName();
    String title = patientFullName + ", welcome to the Pleasantville Medical Patient Portal";
    String templatePath = context.getRealPath("/WEB-INF/email_templates");
    StringTemplateGroup group = new StringTemplateGroup("underwebinf", templatePath, DefaultTemplateLexer.class);
    StringTemplate st = group.getInstanceOf("portal_signup_confirmation");
    String from = Core.mailFrom;
    st.setAttribute("patient", patientFullName);
    st.setAttribute("email", patient.getCred().getEmail());
    st.setAttribute("phone", patient.getDemo().getPrimaryPhone());
    
    MailHandler handler = new MailHandler();
    boolean isHtml = true;
    String stString = st.toString();
    activityLogService.logViewPatient(patient.getId(), null, patient.getId(), "SaveNewPatient");
    handler.sendMimeMessage(patient.getCred().getEmail(), from, stString, title, isHtml);
  }
  
  
  public  void logoutPatient(AuthorizedDTO dto) throws Exception {
    Patient patient = appDAO.findPatientBySessionId(dto.getSessionId());
    activityLogService.logLogout(patient.getId());
    appDAO.deletePatientSession(dto.getSessionId());
  }
  
  public  List<PatientClinician> getPatientClinicians(PatientDTO dto) throws Exception {
    Patient patient = appDAO.findPatientById(dto.getId());
    activityLogService.logViewPatient(patient.getId(), null, patient.getId(), "GetPatientClinicians");
    return appDAO.getPatientClinicians(patient);
  }
  
  
  public  Patient doLogin(LoginDTO loginDTO, String ipAddress) throws Exception {
    Patient patient = appDAO.authenticatePatient(DataEncryptor.encrypt(loginDTO.getUsername()), loginDTO.getPassword());
    if (patient.getCred().getAuthStatus() == Patient.STATUS_AUTHORIZED) {
      startPatientSession(patient, ipAddress, appDAO);
    }
    activityLogService.logLogin(patient.getId());
    decrypt(patient);
    return patient;
  }
  
  public Patient validateFromOffice(AuthorizedDTO authDTO, String ipAddress) throws Exception {
    Patient patient = null;
    PatientSession patientSession = appDAO.findPatientSessionBySessionId(authDTO.getSessionId());
    if (patientSession != null) {
      patient = appDAO.findPatientBySessionId(authDTO.getSessionId());
      String newSessionId = UUID.randomUUID().toString();
      patient.getCred().setSessionId(newSessionId);
      appDAO.update(patient.getCred());
      patientSession.setSessionId(newSessionId);
      appDAO.update(patientSession);
      activityLogService.logViewPatient(patient.getId(), null, patient.getId(), "ValidateFromOffice");
    }
    decrypt(patient);
    return patient;
  }
  
  
  
  public  Patient validateViaActivation(AuthorizedDTO authDTO, String ipAddress) throws Exception {
    Patient patient = appDAO.authenticatePatientViaActivationCode(authDTO.getActivationCode());
    if (patient.getCred().getAuthStatus() == Patient.STATUS_AUTHORIZED) {
      startPatientSession(patient, ipAddress, appDAO);
    }
    activityLogService.logViewPatient(patient.getId(), null, patient.getId(), "ValidateViaActivation");
    decrypt(patient);
    return patient;
  }
  
  
  
  public void startPatientSession(Patient patient, String ipAddress, AppDAO appDAO) throws Exception {
     PatientSession patientSession = new PatientSession();
     patientSession.setPatient(patient);
     patientSession.setSessionId(patient.getCred().getSessionId());
     patientSession.setIpAddress(ipAddress);
     patientSession.setLastAccessTime(new Date());
     appDAO.create(patientSession);
     PatientSessionData patientSessionData = new PatientSessionData();
     patientSessionData.setPatientSession(patientSession);
     log.info("======= Added " + patientSession.toString()); 
     activityLogService.logViewPatient(patient.getId(), null, patient.getId(), "StartPatientSession");
  }  
  
  
   public  boolean isValidSession(AuthorizedDTO dto, String ipAddress, String path) throws Exception {
    String username = "";
    
    appDAO.deleteExpiredPatientSessions();
    
    if (dto == null || dto.getSessionId() == null) {
      log.info("======= isValidSession() no session id submitted by user at ip address of " + ipAddress); 
      return false;
    }
    
    PatientSession patientSession = appDAO.findPatientSessionBySessionId(dto.getSessionId());
    
    if (patientSession == null) {
      log.info("======= isValidSession() no session found for : " + dto.getSessionId()); 
      return false;
    }
    
    
    if (patientSession.getIpAddress().equals(ipAddress) == false) {
      log.info("======= isValidSession() submitted IP address is of " + ipAddress + " does not match the one found in current session"); 
      return false;
    }
    
     // check for proper access level
    int accessLevel = patientSession.getPatient().getCred().getAccessLevel();
    log.info("======= isValidSession() checking " + path); 
    if (Permissions.patientPermissionsMap.get(path) != null) {
      username = patientSession.getPatient().getCred().getUsername(); 
      log.info("======= isValidSession() checking " + path + " for user " + username + " with a permissions level of " + accessLevel); 
      if (Permissions.patientPermissionsMap.get(path)[accessLevel] == false) {
        log.info("======= isValidSession() user " + username + " lacks permission level to execute " + path); 
        return false;
      }
    }
    
    // update session timestamp to current time 
    patientSession.setLastAccessTime(new Date());
    appDAO.update(patientSession);
    log.info("======= isValidSession() user " + username + "'s timestamp updated to " + patientSession.getLastAccessTime()); 
    
    return true;
  }
  
   public String getStaticLists() throws Exception{
    Map<String,List> map = new HashMap<String,List>();
    Gson gson = new Gson();
    map.put("usStates", appDAO.getUSStates());
    return gson.toJson(map);
  }
  
  
  public String uploadProfileImage(HttpServletRequest request, HttpServletResponse response) throws Exception{
    String patientId = request.getParameter("patientId");
    InputStream is = null;
    FileOutputStream fos = null;
    String returnString = "";
    
    is = request.getInputStream();
    String filename = request.getHeader("X-File-Name");
    String filesHomePatientDirPath =  Core.filesHome  + Core.patientDirPath + "/" + patientId + "/";
    fos = new FileOutputStream(new File(filesHomePatientDirPath + filename));
    IOUtils.copy(is, fos);
    response.setStatus(HttpServletResponse.SC_OK);
    fos.close();
    is.close();
   
    String[] imageMagickArgs = {
      Core.imageMagickHome + "convert", 
     filesHomePatientDirPath + filename, 
      "-resize", 
      "160x160", 
      filesHomePatientDirPath + filename
    };
    Runtime runtime = Runtime.getRuntime();
    Process process = runtime.exec(imageMagickArgs);
    
    InputStream pis = process.getInputStream();
    InputStreamReader isr = new InputStreamReader(pis);
    BufferedReader br = new BufferedReader(isr);
    String line;
    log.info("Output of running "+ Arrays.toString(imageMagickArgs) + "is: ");

    while ((line = br.readLine()) != null) {
      log.info(line);
    }
    log.info("\n" + filename + " uploaded");
    
    Patient patient = appDAO.findPatientById(new Integer(patientId));    
    appDAO.updatePatientProfileImage(patient, filename); 
   
    returnString = "{\"filename\":\""+filename+"\"}";
    activityLogService.logViewPatient(patient.getId(), null, patient.getId(), "UploadProfileImage");
    return returnString;
  }
  
  
  
  public String generatePassword() {
    String alphabet = "$%!@#^&?";
    int n = alphabet.length();
    Random r = new Random();
    String newPassword =  new RandPass().getPass(8) + alphabet.charAt(r.nextInt(n));
    return newPassword;
  }
  
  
  public  boolean doPasswordReset(PasswordResetDTO passwordResetDTO) throws Exception {
    Patient patient = appDAO.findPatientByEmail(passwordResetDTO.getEmail());
    if(patient != null) {
      
      String newPassword = generatePassword();
      String newSalt = UUID.randomUUID().toString();
      String encodedPassword = OneWayPasswordEncoder.getInstance().encode(newPassword, newSalt);
      patient.getCred().setPassword(encodedPassword);
      patient.getCred().setSalt(newSalt);
      appDAO.update(patient.getCred());
      new MailHandler().sendPasswordReset(patient, newPassword);
    }
    activityLogService.logViewPatient(patient.getId(), null, patient.getId(), "PasswordReset");
    return patient != null;
  }
  
  
  public boolean testPassword(String password) {
  
   if (password.length() < 6) {
    log.info("Submitted passwords is not at least six characters long");
    return false;
    }
    Pattern lowerCasePattern = Pattern.compile("[a-z]+");
    Matcher lowerCaseMatcher = lowerCasePattern.matcher(password);
        
    Pattern upperCasePattern = Pattern.compile("[A-Z]+");
    Matcher upperCaseMatcher = upperCasePattern.matcher(password);
        
    if (lowerCaseMatcher.find() == false || upperCaseMatcher.find() == false) {
      log.info("Sumitted passwords does not include at least one uppercase and one lowercase letter");
      return false;
    }
          
    Pattern numericPattern = Pattern.compile("\\d+");
    Matcher numericMatcher = numericPattern.matcher(password);
        
    Pattern punctuationPattern = Pattern.compile("\\p{Punct}+");
    Matcher punctuationMatcher = punctuationPattern.matcher(password);
         
    if (numericMatcher.find() == false || punctuationMatcher.find() == false) {
      log.info("Submitted passwords does not include at least one numeric character and one punctuation character");
      return false;
    }
    return true;
  }
  
  public BooleanResultDTO checkEmail(LoginDTO loginDTO) throws Exception {
    BooleanResultDTO booleanResultDTO = new BooleanResultDTO();
    booleanResultDTO.setResult(appDAO.checkEmail(loginDTO.getEmail()));
    return booleanResultDTO;
  }
  
  
  public void getFile(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, 
                      String filePath, String fileName) throws Exception {
    String mime = servletContext.getMimeType(fileName);
    if (mime == null) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }
    response.setContentType(mime);  
    File file = new File(filePath + fileName);
    response.setContentLength((int) file.length());
    FileInputStream in = new FileInputStream(file);
    OutputStream out = response.getOutputStream();
    byte[] buf = new byte[1024];
    int count = 0;
    while ((count = in.read(buf)) >= 0) {
      out.write(buf, 0, count);
    }
    out.close();
    in.close();
  }
  
  
  public List<Appointment> getAllAppointments() throws Exception {
    List<Appointment> appointments = appDAO.getAllAppointments();
    for (Appointment a : appointments) { decrypt(a.getPatient()); }
    return appointments;
  }
  
  
  
  public List<Appointment> getAllAppointmentsByPatient(Patient patient) throws Exception {
    List<Appointment> appointments = appDAO.getAllAppointmentsByPatient(patient);
    for (Appointment a : appointments) { decrypt(a.getPatient()); }
    return appointments;
  }
  
  
  
  public boolean getAppointment(AppointmentDTO dto) throws Exception {
    Appointment appointment = appDAO.findAppointmentById(dto.getId());
    decrypt(appointment.getPatient());
    dto.setAppointment(appointment);
    return true;
  }
  
  
  public Patient getPatientBySessionId(String sessionId) throws Exception {
    return appDAO.findPatientBySessionId(sessionId);
  }
  
  
  
   public void encrypt(Patient patient) throws Exception { 
     log.info("encrypt()");
     if (patient == null || patient.isEncrypted()) {
       return;
     }
     Credentials cred = patient.getCred();
     Demographics demo = patient.getDemo();
     if (cred.getUsername() != null) { cred.setUsername(DataEncryptor.encrypt(cred.getUsername()));}
     if (cred.getMrn() != null) { cred.setMrn(DataEncryptor.encrypt(cred.getMrn()));}
     if (cred.getFirstName() != null) { cred.setFirstName(DataEncryptor.encrypt(cred.getFirstName()));}
     if (cred.getMiddleName() != null) { cred.setMiddleName(DataEncryptor.encrypt(cred.getMiddleName()));}
     if (cred.getLastName() != null) { cred.setLastName(DataEncryptor.encrypt(cred.getLastName()));}
     if (cred.getAdditionalName() != null) { cred.setAdditionalName(DataEncryptor.encrypt(cred.getAdditionalName()));}
     if (cred.getEmail() != null) { cred.setEmail(DataEncryptor.encrypt(cred.getEmail()));}
     if (cred.getGovtId() != null) { cred.setGovtId(DataEncryptor.encrypt(cred.getGovtId()));}
     if (demo.getPrimaryPhone() != null) { demo.setPrimaryPhone(DataEncryptor.encrypt(demo.getPrimaryPhone()));}
     if (demo.getSecondaryPhone() != null) { demo.setSecondaryPhone(DataEncryptor.encrypt(demo.getSecondaryPhone()));}
     if (demo.getStreetAddress1() != null) { demo.setStreetAddress1(DataEncryptor.encrypt(demo.getStreetAddress1()));}
     if (demo.getStreetAddress2() != null) { demo.setStreetAddress2(DataEncryptor.encrypt(demo.getStreetAddress2()));}
     if (demo.getCity() != null) { demo.setCity(DataEncryptor.encrypt(demo.getCity()));}
     if (demo.getPostalCode() != null) { demo.setPostalCode(DataEncryptor.encrypt(demo.getPostalCode()));}
     if (demo.getEmployer() != null) { demo.setEmployer(DataEncryptor.encrypt(demo.getEmployer()));}
     if (demo.getSchoolName() != null) { demo.setSchoolName(DataEncryptor.encrypt(demo.getSchoolName()));}
     patient.setCred(cred);
     patient.setDemo(demo);
     patient.setEncrypted(true);
   }
   
   
   public void decrypt(Patient patient) throws Exception { 
     log.info("decrypt()");
     if (patient == null || patient.isEncrypted() == false) {
       return;
     }
     Credentials cred = patient.getCred();
     Demographics demo = patient.getDemo();
     if (cred.getUsername() != null) { cred.setUsername(DataEncryptor.decrypt(cred.getUsername()));}
     if (cred.getMrn() != null) { cred.setMrn(DataEncryptor.decrypt(cred.getMrn()));}
     if (cred.getFirstName() != null) { cred.setFirstName(DataEncryptor.decrypt(cred.getFirstName()));}
     if (cred.getMiddleName() != null) { cred.setMiddleName(DataEncryptor.decrypt(cred.getMiddleName()));}
     if (cred.getLastName() != null) { cred.setLastName(DataEncryptor.decrypt(cred.getLastName()));}
     if (cred.getAdditionalName() != null) { cred.setAdditionalName(DataEncryptor.decrypt(cred.getAdditionalName()));}
     if (cred.getEmail() != null) { cred.setEmail(DataEncryptor.decrypt(cred.getEmail()));}
     if (cred.getGovtId() != null) { cred.setGovtId(DataEncryptor.decrypt(cred.getGovtId()));}
     if (demo.getPrimaryPhone() != null) { demo.setPrimaryPhone(DataEncryptor.decrypt(demo.getPrimaryPhone()));}
     if (demo.getSecondaryPhone() != null) { demo.setSecondaryPhone(DataEncryptor.decrypt(demo.getSecondaryPhone()));}
     if (demo.getStreetAddress1() != null) { demo.setStreetAddress1(DataEncryptor.decrypt(demo.getStreetAddress1()));}
     if (demo.getStreetAddress2() != null) { demo.setStreetAddress2(DataEncryptor.decrypt(demo.getStreetAddress2()));}
     if (demo.getCity() != null) { demo.setCity(DataEncryptor.decrypt(demo.getCity()));}
     if (demo.getPostalCode() != null) { demo.setPostalCode(DataEncryptor.decrypt(demo.getPostalCode()));}
     if (demo.getEmployer() != null) { demo.setEmployer(DataEncryptor.decrypt(demo.getEmployer()));}
     if (demo.getSchoolName() != null) { demo.setSchoolName(DataEncryptor.decrypt(demo.getSchoolName()));}
     patient.setCred(cred);
     patient.setDemo(demo);
     patient.setEncrypted(false);
   }

}

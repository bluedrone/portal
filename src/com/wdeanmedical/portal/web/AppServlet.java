package com.wdeanmedical.portal.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wdeanmedical.portal.dto.AppointmentDTO;
import com.wdeanmedical.portal.core.Core;
import com.wdeanmedical.portal.dto.AuthorizedDTO;
import com.wdeanmedical.portal.dto.ContactMessageDTO;
import com.wdeanmedical.portal.dto.LetterDTO;
import com.wdeanmedical.portal.dto.LoginDTO;
import com.wdeanmedical.portal.dto.MessageDTO;
import com.wdeanmedical.portal.dto.PatientDTO;
import com.wdeanmedical.portal.dto.SiteDTO;
import com.wdeanmedical.portal.entity.Appointment;
import com.wdeanmedical.portal.entity.Clinician;
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
import com.wdeanmedical.portal.entity.PatientMedication;
import com.wdeanmedical.portal.entity.PatientMessage;
import com.wdeanmedical.portal.entity.PatientMessageType;
import com.wdeanmedical.portal.entity.VitalSigns;
import com.wdeanmedical.portal.service.AppService;
import com.wdeanmedical.portal.util.PatientSessionData;
import com.google.gson.Gson;

import org.apache.log4j.Logger;


public class AppServlet extends HttpServlet  {
  
  private static final long serialVersionUID = 5141268230082988870L;
  private static final Logger log = Logger.getLogger(AppServlet.class);
  
  private AppService appService;

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ServletContext context = getServletContext();
    Core.servletContext = context;
    Core.timeZone = context.getInitParameter("timeZone");
    Core.sendMail = new Boolean(context.getInitParameter("mail.send"));
    Core.mailFrom = context.getInitParameter("mail.from");
    Core.smtphost = context.getInitParameter("mail.smtp.host");
    Core.smtpport = context.getInitParameter("mail.smtp.port");
    Core.imageMagickHome = context.getInitParameter("IMAGE_MAGICK_HOME");
    Core.appBaseDir = context.getRealPath("/");
    Core.sessionTimeout = Integer.parseInt(context.getInitParameter("appSessionTimeout"));
    Core.mailAuthenticationUser = context.getInitParameter("mailAuthenticationUser");
    Core.mailAuthenticationPassword = context.getInitParameter("mailAuthenticationPassword");
    Core.filesHome = context.getInitParameter("filesHome");
    Core.patientDirPath = context.getInitParameter("patientDirPath");
    Core.imagesDir = context.getInitParameter("imagesDir");
    Core.buildPatientPermissionsMap();
    try{
      appService = new AppService();
    }
    catch(MalformedURLException e){
      e.printStackTrace();
    }
  }
    
  public void doPost( HttpServletRequest request, HttpServletResponse response) {
    String returnString = "";
    String pathInfo = request.getPathInfo();
    String servletPath = request.getServletPath();
     
    try { 
      if (pathInfo.equals("/login")) {
        returnString = doLogin(request, response);  
      }
      else if (pathInfo.equals("/validateFromOffice")) {
        returnString = validateFromOffice(request, response);  
      }
      else if (pathInfo.equals("/validateViaActivation")) {
        returnString = validateViaActivation(request, response);  
      }
      else if (pathInfo.equals("/getStaticLists")) {
        returnString = getStaticLists(request, response);  
      }
      else { 
        if (isValidSession(request, response) == false) {
          returnString = doLogout(request, response);  
        }
        else {
          if (pathInfo.equals("/getMedicalAdvice")) {
            returnString = getMedicalAdvice(request, response);  
          }
          else if (pathInfo.equals("/getPastAppointments")) {
            returnString = getPastAppointments(request, response);  
          }
          else if (pathInfo.equals("/getPatientAllergens")) {
            returnString = getPatientAllergens(request, response);  
          }
          else if (pathInfo.equals("/getAppointment")) {
            returnString = getAppointment(request, response);  
          }
          else if (pathInfo.equals("/getAppointments")) {
            returnString = getAppointments(request, response);  
          }
          else if (pathInfo.equals("/getAppointmentsByPatient")) {
            returnString = getAppointmentsByPatient(request, response);  
          }
          else if (pathInfo.equals("/getPatientClinicians")) {
            returnString = getPatientClinicians(request, response);  
          }
          else if (pathInfo.equals("/getPatientDMData")) {
            returnString = getPatientDMData(request, response);  
          } 
          else if (pathInfo.equals("/getPatientHealthIssues")) {
            returnString = getPatientHealthIssues(request, response);  
          }
          else if (pathInfo.equals("/getPatientHealthTrendReports")) {
            returnString = getPatientHealthTrendReports(request, response);  
          }
          else if (pathInfo.equals("/getPatientImmunizations")) {
            returnString = getPatientImmunizations(request, response);  
          }
          else if (pathInfo.equals("/getPatientLetter")) {
            returnString = getPatientLetter(request, response);  
          }
          else if (pathInfo.equals("/getPatientLetters")) {
            returnString = getPatientLetters(request, response);  
          }
          else if (pathInfo.equals("/getPatientLipids")) {
            returnString = getPatientLipids(request, response);  
          } 
          else if (pathInfo.equals("/getPatientMedicalTestComponents")) {
            returnString = getPatientMedicalTestComponents(request, response);  
          }
          else if (pathInfo.equals("/getPatientMedicalTests")) {
            returnString = getPatientMedicalTests(request, response);  
          }
          else if (pathInfo.equals("/getPatientMedications")) {
            returnString = getPatientMedications(request, response);  
          }
          else if (pathInfo.equals("/getPatientProcedures")) {
            returnString = getPatientProcedures(request, response);  
          }
          else if (pathInfo.equals("/getPatientProfileImage")) {
            returnString = getPatientProfileImage(request, response);  
          }
          else if (pathInfo.equals("/getPatientMessage")) {
            returnString = getPatientMessage(request, response);  
          }
          else if (pathInfo.equals("/getPatientMessages")) {
            returnString = getPatientMessages(request, response);  
          }
          else if (pathInfo.equals("/getPatientSentMessages")) {
            returnString = getPatientSentMessages(request, response);  
          }
          else if (pathInfo.equals("/getPatientVitalSigns")) {
            returnString = getPatientVitalSigns(request, response);  
          } 
          else if (pathInfo.equals("/getUpcomingAppointments")) {
            returnString = getUpcomingAppointments(request, response);  
          }
          else if (pathInfo.equals("/logout")) {
            returnString = doLogout(request, response);  
          }
          else if (pathInfo.equals("/requestAppointment")) {
            returnString = requestAppointment(request, response);  
          }
          else if (pathInfo.equals("/requestRxRenewal")) {
            returnString = requestRxRenewal(request, response);  
          }
          else if (pathInfo.equals("/saveNewPatient")) {
            returnString = saveNewPatient(request, response);  
          }
          else if (pathInfo.equals("/sendContactMessage")) {
            returnString = sendContactMessage(request, response);  
          }
          else if (pathInfo.equals("/uploadProfileImage")) {
            returnString = uploadProfileImage(request, response);  
          }
        }
      }
      
      PrintWriter out = null;
      response.setContentType("text/plain");
      out = response.getWriter();
      out.println(returnString);
      log.debug(returnString);
      out.close();
    }  
    catch( IOException ioe ) {
      ioe.printStackTrace();
    } 
    catch( Exception e ) {
      e.printStackTrace();
    }
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    doPost(request, response);  
  }
  
  
 protected  boolean isValidSession(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String ipAddress = request.getRemoteHost();
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AuthorizedDTO dto = gson.fromJson(data, AuthorizedDTO.class);  
    if (dto == null){
      dto = new AuthorizedDTO();
      dto.setSessionId(request.getParameter("sessionId"));
    }
    String path = request.getPathInfo();
    if(path.substring(1).split("/").length > 1) {
      path = path.substring(1).split("/")[1];
    } 
    path = request.getServletPath() + path;
    return appService.isValidSession(dto, ipAddress, path);
  }
  
  
  public String getPatientAllergens(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientAllergen> patientAllergens = appService.getPatientAllergens(dto); 
    dto.setPatientAllergens(patientAllergens);
    String json = gson.toJson(dto);
    return json;
  }
  
  
  public String getPatientVitalSigns(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<VitalSigns> patientVitalSigns = appService.getPatientVitalSigns(dto); 
    dto.setVitalSigns(patientVitalSigns);
    String json = gson.toJson(dto);
    return json;
  }
  
  public String getPatientDMData(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientDMData> patientDMData = appService.getPatientDMData(dto); 
    dto.setPatientDMData(patientDMData);
    String json = gson.toJson(dto);
    return json;
  }
  
  public String getPatientLipids(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientLipids> patientLipids = appService.getPatientLipids(dto); 
    dto.setPatientLipids(patientLipids);
    String json = gson.toJson(dto);
    return json;
  }
  
  
  public String getPatientMedications(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientMedication> patientMedications = appService.getPatientMedications(dto); 
    dto.setPatientMedications(patientMedications);
    String json = gson.toJson(dto);
    return json;
  }
  
  
  public String getPatientImmunizations(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientImmunization> patientImmunizations = appService.getPatientImmunizations(dto); 
    dto.setPatientImmunizations(patientImmunizations);
    String json = gson.toJson(dto);
    return json;
  }
  
 
  public String getPatientHealthIssues(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientHealthIssue> patientHealthIssues = appService.getPatientHealthIssues(dto); 
    dto.setPatientHealthIssues(patientHealthIssues);
    String json = gson.toJson(dto);
    return json;
  }
  
  
  public String getPatientMedicalTests(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientMedicalTest> patientMedicalTests = appService.getPatientMedicalTests(dto); 
    dto.setPatientMedicalTests(patientMedicalTests);
    String json = gson.toJson(dto);
    return json;
  }
  
  public String getPatientMedicalTestComponents(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    boolean result = appService.getPatientMedicalTestComponents(dto); 
    String json = gson.toJson(dto);
    return (json);
  }  
  
   public String getPatientProcedures(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientMedicalProcedure> patientProcedures = appService.getPatientMedicalProcedures(dto); 
    dto.setPatientMedicalProcedures(patientProcedures);
    String json = gson.toJson(dto);
    return json;
  }
  
  public String getPatientHealthTrendReports(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientHealthTrendReport> patientHealthTrendReports = appService.getPatientHealthTrendReports(dto); 
    dto.setPatientHealthTrendReports(patientHealthTrendReports);
    String json = gson.toJson(dto);
    return json;
  }
  
  
  public String getPatientLetters(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientLetter> patientLetters = appService.getPatientLetters(dto); 
    dto.setPatientLetters(patientLetters);
    String json = gson.toJson(dto);
    return json;
  }
  
  
  public String getPatientMessages(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientMessage> patientMessages = appService.getPatientMessages(dto, true); 
    dto.setPatientMessages(patientMessages);
    String json = gson.toJson(dto);
    return json;
  }
  
  public String getPatientClinicians(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientClinician> patientClinicians = appService.getPatientClinicians(dto); 
    dto.setPatientClinicians(patientClinicians);
    String json = gson.toJson(dto);
    return json;
  }
  
  
  public String getPatientSentMessages(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientMessage> patientMessages = appService.getPatientMessages(dto, false); 
    dto.setPatientMessages(patientMessages);
    String json = gson.toJson(dto);
    return json;
  }
  
  
  public String getPastAppointments(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<Appointment> appointments = appService.getAppointments(dto, true); 
    dto.setAppointments(appointments);
    String json = gson.toJson(dto);
    return json;
  }
  
  
  public String getUpcomingAppointments(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<Appointment> appointments = appService.getAppointments(dto, false); 
    dto.setAppointments(appointments);
    String json = gson.toJson(dto);
    return json;
  }
  
  public String getMedicalAdvice(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    MessageDTO dto = gson.fromJson(data, MessageDTO.class); 
    boolean result = appService.processMessage(dto, PatientMessageType.MEDICAL_ADVICE); 
    String json = gson.toJson(dto);
    return (json);
  }
  
  public String getPatientLetter(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    LetterDTO dto = gson.fromJson(data, LetterDTO.class); 
    boolean result = appService.getPatientLetter(dto); 
    String json = gson.toJson(dto);
    return (json);
  }
  
  public String getPatientMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    MessageDTO dto = gson.fromJson(data, MessageDTO.class); 
    boolean result = appService.getPatientMessage(dto); 
    String json = gson.toJson(dto);
    return (json);
  }
  
  public String requestRxRenewal(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    MessageDTO dto = gson.fromJson(data, MessageDTO.class); 
    boolean result = appService.processMessage(dto, PatientMessageType.RX_RENEWAL); 
    String json = gson.toJson(dto);
    return (json);
  }
  
  public String requestAppointment(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    MessageDTO dto = gson.fromJson(data, MessageDTO.class); 
    dto.setPatientMessageTypeId(PatientMessageType.APPT_REQUEST);
    boolean result = appService.processMessage(dto, PatientMessageType.APPT_REQUEST); 
    String json = gson.toJson(dto);
    return (json);
  }
  
  public String sendContactMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    ContactMessageDTO dto = gson.fromJson(data, ContactMessageDTO.class); 
    boolean result = appService.sendContactMessage(request, dto); 
    String json = gson.toJson(result);
    return (json);
  }
  
  public String saveNewPatient(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    appService.saveNewPatient(dto, request); 
    String json = gson.toJson(dto);
    return (json);
  }
  
  
  public String doLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    LoginDTO loginDTO = gson.fromJson(data, LoginDTO.class);  
    String ipAddress = request.getRemoteHost();
    Patient patient = appService.doLogin(loginDTO, ipAddress); 
    String json = gson.toJson(patient);
    return (json);
  }
  
  
  public String validateFromOffice(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AuthorizedDTO authDTO = gson.fromJson(data, AuthorizedDTO.class);  
    String ipAddress = request.getRemoteHost();
    Patient patient = appService.validateFromOffice(authDTO, ipAddress); 
    String json = gson.toJson(patient);
    return (json);
  }
  
  
  public String validateViaActivation(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AuthorizedDTO authDTO = gson.fromJson(data, AuthorizedDTO.class);  
    String ipAddress = request.getRemoteHost();
    Patient patient = appService.validateViaActivation(authDTO, ipAddress); 
    String json = gson.toJson(patient);
    return (json);
  }
  
  
  public String doLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AuthorizedDTO dto = gson.fromJson(data, AuthorizedDTO.class);  
    appService.logoutPatient(dto);
    dto.setAuthenticated(false);
    String json = gson.toJson(dto);
    return json;
  }
  
  
  public String checkSession(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AuthorizedDTO dto = gson.fromJson(data, AuthorizedDTO.class);  
    if (dto == null) {
      dto = new AuthorizedDTO();
    }
    dto.setAuthenticated(isValidSession(request, response));
    String json = gson.toJson(dto);
    return json;
  }
  
  
  public String logoutUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AuthorizedDTO dto = gson.fromJson(data, AuthorizedDTO.class);  
    if (dto == null) {
      dto = new AuthorizedDTO();
    }
    //dto.setAuthenticated(false);
    String json = gson.toJson(dto);
    return json;
  }
  
  public String uploadProfileImage(HttpServletRequest request, HttpServletResponse response) throws Exception{
    return appService.uploadProfileImage(request, response);
  }
  
   public String getStaticLists(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String json = appService.getStaticLists(); 
    return json;
  }
  
    
  public String getPatientProfileImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    String profileImagePath = request.getParameter("profileImagePath"); 
    Gson gson = new Gson();
    String patientId = request.getParameter("patientId");
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    String filesHomePatientDirPath =  Core.filesHome  + Core.patientDirPath + "/" + patientId + "/";
	appService.getFile(request, response, getServletContext(), filesHomePatientDirPath, profileImagePath);  
    String json = gson.toJson(dto);
    return json;
  }
  
  
  
  public String getAppointmentsByPatient(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Gson gson = new Gson();
    List<Appointment> bookedAppts = null;
    Patient patient = appService.getPatientBySessionId(request.getParameter("sessionId"));
    bookedAppts = appService.getAllAppointmentsByPatient(patient);
        
    ArrayList<Map<String, Object>> visitsList = new ArrayList<Map<String, Object>>();
    Map<String, Object> visitInstance = null;
    if(bookedAppts != null) {
      for(Appointment event : bookedAppts) {
        visitInstance = new HashMap<String, Object>();
        visitInstance.put("id", event.getId());
        Clinician clinician = event.getClinician();
        visitInstance.put("title", clinician.getFirstName() + " " + clinician.getLastName());
        visitInstance.put("start", formatDate(event.getStartTime()));
        visitInstance.put("end", formatDate(event.getEndTime()));
        visitInstance.put("desc", event.getDesc());
        visitInstance.put("allDay", Boolean.FALSE);
        visitsList.add(visitInstance);
      }
    }
    return gson.toJson(visitsList);
  }
  
  
  
  public String getAppointments(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Gson gson = new Gson();
    List<Appointment> bookedAppts = null;
    bookedAppts = appService.getAllAppointments();
        
    ArrayList<Map<String, Object>> visitsList = new ArrayList<Map<String, Object>>();
    Map<String, Object> visitInstance = null;
    if(bookedAppts != null) {
      for(Appointment event : bookedAppts) {
        visitInstance = new HashMap<String, Object>();
        visitInstance.put("id", event.getId());
        visitInstance.put("title", event.getTitle());
        visitInstance.put("start", formatDate(event.getStartTime()));
        visitInstance.put("end", formatDate(event.getEndTime()));
        visitInstance.put("desc", event.getDesc());
        visitInstance.put("allDay", Boolean.FALSE);
        visitsList.add(visitInstance);
      }
    }
    return gson.toJson(visitsList);
  }
  
  
  
  public static String formatDate(Date date){
    String value = null;
    if (date != null){
      SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      value = dateformat.format(date);
    }
    return value;
  }
  
  
  public String getAppointment(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AppointmentDTO dto = gson.fromJson(data, AppointmentDTO.class); 
    boolean result = appService.getAppointment(dto);
    String json = gson.toJson(dto);
    return json;
  }
  
}
 
 

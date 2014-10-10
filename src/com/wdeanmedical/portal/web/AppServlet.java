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
import com.wdeanmedical.portal.core.Permissions;
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
import com.wdeanmedical.portal.util.DataEncryptor;
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
    try { DataEncryptor.setEncryptionKey(context.getInitParameter("encryptionKey")); } catch (Exception e1) { e1.printStackTrace();}
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
    Permissions.buildPatientPermissionsMap();
    try{ appService = new AppService(); } catch(MalformedURLException e){ e.printStackTrace(); }
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
            returnString = getMessageServiceData(request, "/getMedicalAdvice");  
          }
          else if (pathInfo.equals("/getPastAppointments")) {
            returnString = getPatientServiceData(request, "/getPastAppointments");  
          }
          else if (pathInfo.equals("/getPatientAllergens")) {
            returnString = getPatientServiceData(request, "/getPatientAllergens");  
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
            returnString = getPatientServiceData(request, "/getPatientClinicians");  
          }
          else if (pathInfo.equals("/getPatientDMData")) {
            returnString = getPatientServiceData(request, "/getPatientDMData");  
          } 
          else if (pathInfo.equals("/getPatientHealthIssues")) {
            returnString = getPatientServiceData(request, "/getPatientHealthIssues");  
          }
          else if (pathInfo.equals("/getPatientHealthTrendReports")) {
            returnString = getPatientServiceData(request, "/getPatientHealthTrendReports");  
          }
          else if (pathInfo.equals("/getPatientImmunizations")) {
            returnString = getPatientServiceData(request, "/getPatientImmunizations");  
          }
          else if (pathInfo.equals("/getPatientLetter")) {
            returnString = getPatientLetter(request, response);  
          }
          else if (pathInfo.equals("/getPatientLetters")) {
            returnString = getPatientServiceData(request, "/getPatientLetters");  
          }
          else if (pathInfo.equals("/getPatientLipids")) {
            returnString = getPatientServiceData(request, "/getPatientLipids");  
          } 
          else if (pathInfo.equals("/getPatientMedicalTestComponents")) {
            returnString = getPatientServiceData(request, "/getPatientMedicalTestComponents");  
          }
          else if (pathInfo.equals("/getPatientMedicalTests")) {
            returnString = getPatientServiceData(request, "/getPatientMedicalTests");  
          }
          else if (pathInfo.equals("/getPatientMedications")) {
            returnString = getPatientServiceData(request, "/getPatientMedications");  
          }
          else if (pathInfo.equals("/getPatientProcedures")) {
            returnString = getPatientServiceData(request, "/getPatientProcedures");  
          }
          else if (pathInfo.equals("/getPatientProfileImage")) {
            returnString = getPatientProfileImage(request, response);  
          }
          else if (pathInfo.equals("/getPatientMessage")) {
            returnString = getMessageServiceData(request, "/getPatientMessage");  
          }
          else if (pathInfo.equals("/getPatientMessages")) {
            returnString = getPatientServiceData(request, "/getPatientMessages");  
          }
          else if (pathInfo.equals("/getPatientSentMessages")) {
            returnString = getPatientServiceData(request, "/getPatientSentMessages");  
          }
          else if (pathInfo.equals("/getPatientVitalSigns")) {
            returnString = getPatientServiceData(request, "/getPatientVitalSigns");  
          } 
          else if (pathInfo.equals("/getUpcomingAppointments")) {
            returnString = getPatientServiceData(request, "/getUpcomingAppointments");  
          }
          else if (pathInfo.equals("/logout")) {
            returnString = doLogout(request, response);  
          }
          else if (pathInfo.equals("/requestAppointment")) {
            returnString = getMessageServiceData(request, "/requestAppointment");  
          }
          else if (pathInfo.equals("/requestRxRenewal")) {
            returnString = getMessageServiceData(request, "/requestRxRenewal");  
          }
          else if (pathInfo.equals("/saveNewPatient")) {
            returnString = getPatientServiceData(request, "/saveNewPatient");  
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
  
 public String getPatientServiceData(HttpServletRequest request, String pathAction) throws Exception {
   String data = request.getParameter("data");
   Gson gson = new Gson();
   PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
   if(pathAction.equals("/getPatientAllergens")) {
     List<PatientAllergen> patientAllergens = appService.getPatientAllergens(dto); 
     dto.setPatientAllergens(patientAllergens);
   }
   else if(pathAction.equals("/getPatientVitalSigns")) {
     List<VitalSigns> patientVitalSigns = appService.getPatientVitalSigns(dto); 
     dto.setVitalSigns(patientVitalSigns);
   }
   else if(pathAction.equals("/getPatientLetters")) {
     List<PatientLetter> patientLetters = appService.getPatientLetters(dto); 
     dto.setPatientLetters(patientLetters);
   }
   else if(pathAction.equals("/getPatientMessages")) {
     List<PatientMessage> patientMessages = appService.getPatientMessages(dto, true); 
     dto.setPatientMessages(patientMessages);
   }
   else if(pathAction.equals("/getPatientClinicians")) {
     List<PatientClinician> patientClinicians = appService.getPatientClinicians(dto); 
     dto.setPatientClinicians(patientClinicians);
   }
   else if(pathAction.equals("/getPatientDMData")) {
     List<PatientDMData> patientDMData = appService.getPatientDMData(dto); 
     dto.setPatientDMData(patientDMData);
   }
   else if(pathAction.equals("/getUpcomingAppointments")) {
     List<Appointment> appointments = appService.getAppointments(dto, false); 
     dto.setAppointments(appointments);
   }
   else if(pathAction.equals("/getPatientLipids")) {
     List<PatientLipids> patientLipids = appService.getPatientLipids(dto); 
     dto.setPatientLipids(patientLipids);
   }
   else if(pathAction.equals("/saveNewPatient")) {
     appService.saveNewPatient(dto, request);
   }
   else if(pathAction.equals("/getPatientMedications")) {
     List<PatientMedication> patientMedications = appService.getPatientMedications(dto); 
     dto.setPatientMedications(patientMedications);
   }
   else if(pathAction.equals("/getPatientImmunizations")) {
     List<PatientImmunization> patientImmunizations = appService.getPatientImmunizations(dto); 
     dto.setPatientImmunizations(patientImmunizations);
   }
   else if(pathAction.equals("/getPatientHealthIssues")) {
     List<PatientHealthIssue> patientHealthIssues = appService.getPatientHealthIssues(dto); 
     dto.setPatientHealthIssues(patientHealthIssues);
   }
   else if(pathAction.equals("/getPatientMedicalTests")) {
     List<PatientMedicalTest> patientMedicalTests = appService.getPatientMedicalTests(dto); 
     dto.setPatientMedicalTests(patientMedicalTests);
   }
   else if(pathAction.equals("/getPatientMedicalTestComponents")) {
     boolean result = appService.getPatientMedicalTestComponents(dto);
   }
   else if(pathAction.equals("/getPatientProcedures")) {
     List<PatientMedicalProcedure> patientProcedures = appService.getPatientMedicalProcedures(dto); 
     dto.setPatientMedicalProcedures(patientProcedures);
   }
   else if(pathAction.equals("/getPatientHealthTrendReports")) {
     List<PatientHealthTrendReport> patientHealthTrendReports = appService.getPatientHealthTrendReports(dto); 
     dto.setPatientHealthTrendReports(patientHealthTrendReports);
   }
   else if(pathAction.equals("/getPatientSentMessages")) {
     List<PatientMessage> patientMessages = appService.getPatientMessages(dto, false); 
     dto.setPatientMessages(patientMessages);
   }
   else if(pathAction.equals("/getPastAppointments")) {
     List<Appointment> appointments = appService.getAppointments(dto, true); 
     dto.setAppointments(appointments);
   }
   String json = gson.toJson(dto);
   return json;
 } 
 
 public String getMessageServiceData(HttpServletRequest request, String pathAction) throws Exception {
   String data = request.getParameter("data");
   Gson gson = new Gson();
   MessageDTO dto = gson.fromJson(data, MessageDTO.class); 
   if(pathAction.equals("/getMedicalAdvice")) {
     boolean result = appService.processMessage(dto, PatientMessageType.MEDICAL_ADVICE);
   }
   else if(pathAction.equals("/getPatientMessage")) {
     boolean result = appService.getPatientMessage(dto); 
   }
   else if(pathAction.equals("/requestRxRenewal")) {
     boolean result = appService.processMessage(dto, PatientMessageType.RX_RENEWAL); 
   }
   else if(pathAction.equals("/requestAppointment")) {
     dto.setPatientMessageTypeId(PatientMessageType.APPT_REQUEST);
     boolean result = appService.processMessage(dto, PatientMessageType.APPT_REQUEST); 
   }
   String json = gson.toJson(dto);
   return json;
 }
 
  public String getPatientLetter(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    LetterDTO dto = gson.fromJson(data, LetterDTO.class); 
    boolean result = appService.getPatientLetter(dto); 
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
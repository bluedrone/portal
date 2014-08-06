package com.wdeanmedical.portal.core;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;

import com.wdeanmedical.portal.util.PatientSessionData;

public class Core {

  public static ServletContext servletContext;
  public static String timeZone;
  public static Log logger;
  public static Boolean sendMail;
  public static String mailUserName;
  public static String mailPassword;
  public static String mailAuthenticationUser;
  public static String mailAuthenticationPassword;
  public static String mailFrom;
  public static String smtphost;
  public static String smtpport;
  public static String debug;
  public static String smtpauth;
  public static String factport;
  public static String factclass;
  public static String fallback;
  public static String starttls;
  public static int sessionTimeout;
  public static String imagesDir;
  public static String imageMagickHome;
  public static String filesHome;  
  public static String appBaseDir;
  public static String patientDirPath;
  public static Map<String, PatientSessionData> patientSessionMap = Collections.synchronizedMap(new TreeMap<String, PatientSessionData>());
  public static Map<String, boolean[]> patientPermissionsMap = new TreeMap<String, boolean[]>();
  
  public static void buildPatientPermissionsMap() {
    patientPermissionsMap.put("/app/getAppointment",                   new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getAppointments",                  new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getAppointmentsByPatient",         new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getPastAppointments",              new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getPatientAllergens",              new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getPatientImmunizations",          new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getPatientDMData",                 new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getPatientHealthIssues",           new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getPatientHealthTrendReports",     new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getPatientLetters",                new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getPatientLipids",                 new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getPatientMedications",            new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getPatientMedicalTestComponents",  new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getPatientMedicalTests",           new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getPatientMessages",               new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getPatientProcedures",             new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getPatientProfileImage",           new boolean[] {true,true});
    patientPermissionsMap.put("/app/getPatientVitalSigns",             new boolean[] {true ,true});
    patientPermissionsMap.put("/app/getUpcomingAppointments",          new boolean[] {true ,true});
    patientPermissionsMap.put("/app/logout",                           new boolean[] {true ,true});
    patientPermissionsMap.put("/app/requestAppointment",               new boolean[] {true ,true});
    patientPermissionsMap.put("/app/requestRxRenewal",                 new boolean[] {true ,true});
    patientPermissionsMap.put("/app/saveNewPatient",                   new boolean[] {true ,true});
    patientPermissionsMap.put("/app/uploadProfileImage",               new boolean[] {true ,true});
 }
  

  
  
}

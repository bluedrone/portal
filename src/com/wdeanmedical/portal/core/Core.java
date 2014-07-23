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
    patientPermissionsMap.put("/logout",                           new boolean[] {true ,true});
    patientPermissionsMap.put("/getAppointment",                   new boolean[] {true ,true});
    patientPermissionsMap.put("/getAppointments",                  new boolean[] {true ,true});
    patientPermissionsMap.put("/getPatientAllergens",              new boolean[] {true ,true});
    patientPermissionsMap.put("/getPatientMedications",            new boolean[] {true ,true});
    patientPermissionsMap.put("/getPatientImmunizations",          new boolean[] {true ,true});
    patientPermissionsMap.put("/getPatientHealthIssues",           new boolean[] {true ,true});
    patientPermissionsMap.put("/getPatientMedicalTestComponents",  new boolean[] {true ,true});
    patientPermissionsMap.put("/getPatientMedicalTests",           new boolean[] {true ,true});
    patientPermissionsMap.put("/getPatientVitalSigns",             new boolean[] {true ,true});
    patientPermissionsMap.put("/getPatientDMData",                 new boolean[] {true ,true});
    patientPermissionsMap.put("getPatientProfileImage",            new boolean[] {true,true});
    patientPermissionsMap.put("/getPatientLipids",                 new boolean[] {true ,true});
    patientPermissionsMap.put("/getPatientVitalSigns",             new boolean[] {true ,true});
    patientPermissionsMap.put("/getPatientProcedures",             new boolean[] {true ,true});
    patientPermissionsMap.put("/getPatientHealthTrendReports",     new boolean[] {true ,true});
    patientPermissionsMap.put("/getPatientLetters",                new boolean[] {true ,true});
    patientPermissionsMap.put("/getPatientMessages",               new boolean[] {true ,true});
    patientPermissionsMap.put("/getPastAppointments",              new boolean[] {true ,true});
    patientPermissionsMap.put("/getUpcomingAppointments",          new boolean[] {true ,true});
    patientPermissionsMap.put("/requestRxRenewal",                 new boolean[] {true ,true});
    patientPermissionsMap.put("/requestAppointment",               new boolean[] {true ,true});
    patientPermissionsMap.put("/saveNewPatient",                   new boolean[] {true ,true});
    patientPermissionsMap.put("/uploadProfileImage",               new boolean[] {true ,true});
 }
  

  
  
}

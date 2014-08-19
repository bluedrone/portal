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
  
}

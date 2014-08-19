/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */
 
package com.wdeanmedical.portal.core;

import java.util.Map;
import java.util.TreeMap;


public class Permissions {
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

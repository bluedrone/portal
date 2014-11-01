/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */

var DO_SCROLL = true;
var DONT_SCROLL = false;
var MANUAL_LOGOUT = false;
var AUTO_LOGOUT = true;
var DEMO_MODE_ON = true;
var DEMO_MODE_OFF = false;
var INITIALIZED = false;
var DEMO_USERNAME = 'patient01@pleasantvillemedical.com';
var DEMO_PASSWORD = 'Njs2101$';
var PASSWORD_PLACEHOLDER = 'not a password';
var app_currentUsername = 'not a username';
var app_templates = {};
var PATIENT_STATUS_AUTHORIZED = 1;
var PATIENT_STATUS_NOT_FOUND = 0;
var PATIENT_STATUS_INVALID_PASSWORD = -1;
var PATIENT_STATUS_INACTIVE = -2;

var RETURN_CODE_DUP_EMAIL = -1;
var RETURN_CODE_INVALID_PASSWORD = -2;

var HEALTH_TREND_VITAL_SIGNS = 1;
var HEALTH_TREND_DM_DATA = 2;
var HEALTH_TREND_LIPIDS = 3;

var patientFullName = "";
var app_currentMedicalTestName = "";
var app_currentHealthIssueName = "";
var app_chartMap = {};

var app_currentScreen = '';
var app_previousScreen = '';
var app_usStates;
var patient = null;
var patientAllergens;
var patientMedications;
var patientImmunizations;
var patientHealthIssues;
var patientVitalSigns;
var patientDMData;
var patientLipids;
var patientMedicalTests;
var patientMedicalTestComponents;
var patientProcedures;
var patientHealthTrendReports;
var patientLetters;
var patientMessages;
var pastAppointments;
var upcomingAppointments;
var patientClinicians;
var app_currentCalendarView = 'month';
var patient;
var app_idleInterval;
var app_idleTime = 0;
var app_autoLogoutWarningDisplayed;
var ONE_SECOND =  1000;
var ONE_MINUTE = 60000;


/***********      @JQUERY INIT    *******************/
(function() {
jqueryInit = function()	{
  if (INITIALIZED == false) {
    INITIALIZED = true;
    getStaticLists();
    app_viewStack('signin-screen', DO_SCROLL);
    $('#section-notification').css("visibility", "hidden");
    $('#section-notification-text').html("");
    $('#appt-request-from').datepicker();
    $('#appt-request-to').datepicker();
    
    var fromOffice = $.QueryString["fromOffice"];
    if (fromOffice == "true") {
      var tempSessionId = $.QueryString["tempSessionId"];
      validateFromOffice(tempSessionId);
    }
    
    var activateUser = $.QueryString["activateUser"];
    if (activateUser == "true") {
      var activationCode = $.QueryString["activationCode"];
      validateViaActivation(activationCode);
    }
    
    !function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';
    if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";
    fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");
    
    $(document).mousemove( function(){ app_timerReset(); });
    window.onbeforeunload = confirmBeforeUnload;
  }
}
$(document).ready(function() {
	standalone = modulejs.require("app/standalone")
    standalone.ready(jqueryInit)
})
})()
/***********      @JQUERY INIT    *******************/

function app_runIdleTimer() {
  app_idleTime = 0;
  if (app_idleInterval) {clearInterval(app_idleInterval)};
  app_idleInterval = setInterval(app_timerIncrement, ONE_MINUTE);
}


function app_timerReset() {
  if (app_autoLogoutWarningDisplayed) { 
    $('#wdm-notification-text').html('');
    app_autoLogoutWarningDisplayed = false;
  }
  app_idleTime = 0;
}



function app_timerIncrement() {
  app_idleTime++;
  if (app_idleTime == 25) {
    displayNotification('You will soon be automatically logged out if still idle', true);
    app_autoLogoutWarningDisplayed = true;
  }
  else if (app_idleTime == 30) {
    logout(AUTO_LOGOUT);
  }
}


function confirmBeforeUnload() {
  if (patient && patient != null) {
    return "Please log out first in order to save your data."; 
  }
}



$('#app-signin-submit').click(function(){ login(DEMO_MODE_OFF); });
$('#app-your-records-panel-btn').click(function(){yourRecordsScreen()});
$('#app-family-records-panel-btn').click(function(){familyRecordsScreen()});
$('#app-messages-panel-btn').click(function(){messagesScreen()});
$('#app-appointments-panel-btn').click(function(){appointmentsScreen()});
$('#app-send-message-panel-btn').click(function(){sendMessageScreen()});
$('#app-settings-panel-btn').click(function(){settingsScreen()});
$('#app-logout-submit').click(function(e){e.preventDefault();logout(MANUAL_LOGOUT);});


function newUserScreen() {
  app_viewStack('new-patient-screen', DO_SCROLL);
  
  setupPictureUpload();
  app_currentUsername = patient.cred.email;
  $("#new-patient-photo").attr("src", 'app/getPatientProfileImage?sessionId=' + patient.sessionId + "&patientId=" + patient.id  + "&profileImagePath=" + patient.demo.profileImagePath);
  $('#new-patient-last-name').val(patient.cred.firstName);
  $('#new-patient-first-name').val(patient.cred.lastName);
  $('#new-patient-middle-initial').val(patient.cred.middleName);
  $('#new-patient-ssn').val(patient.cred.govtId);
  $('#new-patient-address1').val(patient.demo.streetAddress1);
  $('#new-patient-address2').val(patient.demo.streetAddress2);
  $('#new-patient-city').val(patient.demo.city);
  $('#new-patient-postal-code').val(patient.demo.postalCode);
  $('#new-patient-primary-phone').val(patient.demo.primaryPhone);
  $('#new-patient-secondary-phone').val(patient.demo.secondaryPhone);
  $('#new-patient-email').val(patient.cred.email);
  $('#new-patient-email-confirm').val(patient.cred.email);
  $('#new-patient-password').val(PASSWORD_PLACEHOLDER);
  $('#new-patient-password-confirm').val(PASSWORD_PLACEHOLDER);
  $('#new-patient-mrn').val(patient.cred.mrn);
  $('#new-patient-dob').val(dateFormat(patient.demo.dob, 'mm/dd/yyyy'));
  $('#new-patient-gender').val(patient.demo.gender.code);
  $('#new-patient-race').val(patient.demo.race ? patient.demo.race.id : '');
  $('#new-patient-ethnicity').val(patient.demo.ethnicity ? patient.demo.ethnicity.id : '');
  $('#new-patient-occupation').val(patient.demo.occupation);
  $('#new-patient-employmentStatus').val(patient.demo.employmentStatus);
  $('#new-patient-employer').val(patient.demo.employer);
  $('#new-patient-school').val(patient.demo.schoolStatus);
  $('#new-patient-school-name').val(patient.demo.schoolName);
  $('#new-patient-marital-status').val(patient.demo.maritalStatus ? patient.demo.maritalStatus.id : '');
  $('input[name=new-patient-mother-alive][value='+patient.pfsh.motherAlive+']').attr("checked", true);
  $('#new-patient-mother-death-reason').val(patient.pfsh.motherDeathReason);
  $('input[name=new-patient-father-alive][value='+patient.pfsh.fatherAlive+']').attr("checked", true);
  $('#new-patient-father-death-reason').val(patient.pfsh.fatherDeathReason);
  $('#new-patient-past-s-m').val(patient.hist.pastSM);
  $('#new-patient-fam-hist-other').val(patient.hist.famHistOther);
  $('#new-patient-fam-hist-notes').val(patient.hist.famHistNotes);
  $('#new-patient-allerg-food').val(patient.hist.allergFood);
  $('#new-patient-allerg-drug').val(patient.hist.allergDrug);
  $('#new-patient-allerg-env').val(patient.hist.allergEnv);
  $('#new-patient-smoke-pks-day').val(patient.hist.smokePksDay);
  $('#new-patient-years-smoked').val(patient.hist.yearsSmoked);
  $('#new-patient-smoke-years-quit').val(patient.hist.smokeYearsQuit);
  util_selectCheckboxesFromList(patient.hist.subst, 'new-patient-subst');
  util_selectCheckboxesFromList(patient.hist.famHist, 'new-patient-fam-hist');
  $('#new-patient-ssn').mask("999-99-9999");
  $('#new-patient-dob').mask("99/99/9999");
  $('#new-patient-postal-code').mask("99999");
  RenderUtil.render('component/basic_select_options', {options:app_usStates}, function(s) {
    $("#new-patient-us-state").html(s);
    $('#new-patient-us-state').val(patient.demo.usState.id);
  });
  $('#new-patient-save').click(function() { saveNewPatient() });
  
  
  $('#new-patient-cancel').click(function() { 
    var args = {
      modalTitle:"Cancel Password Setup Confirmation", 
      modalH3:"Close Form Before Setting Password?", 
      modalH4:"If you close the form before setting your password you will need to get a new activation email sent to you.",
      cancelButton: 'Cancel',
      okButton: 'Confirm'
    };
    RenderUtil.render('dialog/confirm', args, function(s) { 
      $('#modals-placement').html(s);
      $('#modal-confirm').modal('show'); 
      $('#app-modal-confirmation-btn').click(function(){  
        $('#modal-confirm').modal('hide'); 
        logout(MANUAL_LOGOUT);
      });
    });
  });
  
}


function login(demoMode) {
  doLogin(demoMode);
}

function yourRecordsScreen() {
  app_viewStack('your-records-screen', DO_SCROLL);
}

function familyRecordsScreen() {
  app_viewStack('family-records-screen', DO_SCROLL);
}

function messagesScreen() {
  app_viewStack('messages-screen', DO_SCROLL);
}

function appointmentsScreen() {
  app_viewStack('appointments-screen', DO_SCROLL);
  app_loadCalendar();
}

function sendMessageScreen() {
  app_viewStack('send-message-screen', DO_SCROLL);
  sendMessage_clearForm();
}

function settingsScreen() {
  app_viewStack('settings-screen', DO_SCROLL);
}


function validateViaActivation(activationCode) {
  app_viewStack('signin-screen', DO_SCROLL);
  var jsonData = JSON.stringify({activationCode: activationCode });
    $.post("app/validateViaActivation", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    if (parsedData.id) {
      patient = new Patient();
      patient = parsedData;
      patientFullName = util_buildFullName(patient.cred.firstName, patient.cred.middleName, patient.cred.lastName);
      $('.app-patient-appt-name').text(patientFullName + " [" + patient.cred.mrn + "]");
      $('.home-today').html(getTodaysDateFormatted());
      $('.app-patient-full-name').html(patientFullName);
      var notificationText = patientFullName + ' ready for activation.';
      buildFormControls();
      newUserScreen();  
      app_runIdleTimer(); 
    }
    else {
      var notificationText = 'Activation code failed.';
    }
    displayNotification(notificationText);
  });
}

function validateFromOffice(sessionId) {
  app_viewStack('signin-screen', DO_SCROLL);
  var jsonData = JSON.stringify({sessionId: sessionId });
  $.post("app/validateFromOffice", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    if (parsedData.id) {
      patient = new Patient();
      patient = parsedData;
      patientFullName = util_buildFullName(patient.cred.firstName, patient.cred.middleName, patient.cred.lastName);
      $('.app-patient-appt-name').text(patientFullName + " [" + patient.cred.mrn + "]");
      $('.home-today').html(getTodaysDateFormatted());
      $('.app-patient-full-name').html(patientFullName);
      var notificationText = patientFullName + ' logged in.';
      buildFormControls();
      app_viewStack('dashboard-screen', DO_SCROLL);
      displayNotification(notificationText);
        app_runIdleTimer(); 
    }
    else {
      var notificationText = 'Login from practice website failed.';
    }
  });
}

function doLogin(demoMode) {
  var username;
  var password;
  
  if (demoMode == false) {
    if($.trim($("#app-signin-username").val()).length < 1 || $.trim($("#app-signin-password").val()).length < 1) { 
      return;
    }
    username = $.trim($("#app-signin-username").val());
    password = $.trim($("#app-signin-password").val());
  }
  else {
    username = DEMO_USERNAME;
    password = DEMO_PASSWORD;
  }
  
  var jsonData = JSON.stringify({ username: username, password: password});
  $.post("app/login", {data:jsonData},
    function(data) {
      patient = new Patient();
      patient = $.parseJSON(data);
      var notificationText = "";
        
      if (patient.cred.authStatus == PATIENT_STATUS_AUTHORIZED) {
        app_viewStack('dashboard-screen', DO_SCROLL);
        patientFullName = util_buildFullName(patient.cred.firstName, patient.cred.middleName, patient.cred.lastName);
        $('.app-patient-appt-name').text(patientFullName + " [" + patient.cred.mrn + "]");
        $('.home-today').html(getTodaysDateFormatted());
        $('.app-patient-full-name').html(patientFullName);
        //$('#home_previousLoginTime').css({visibility: "visible"});
        notificationText = patientFullName + ' logged in.';
        buildFormControls();
        app_runIdleTimer(); 
      }  
      else  {
        if (patient.cred.authStatus == PATIENT_STATUS_NOT_FOUND) {
          notificationText = 'User not found in system';
        }
        else if (patient.cred.authStatus == PATIENT_STATUS_INVALID_PASSWORD) {
          notificationText = 'Invalid password';
        }
        else if (patient.cred.authStatus == PATIENT_STATUS_INACTIVE) {
          notificationText = 'User is inactive';
        }
      }
      displayNotification(notificationText);
    }
  );  
}


function buildFormControls() {
   getPatientClinicians();
}


function getPatientClinicians() {
  var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.cred.sessionId });
  $.post("app/getPatientClinicians", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    patientClinicians = parsedData.patientClinicians;
    RenderUtil.render('patient_clinician_select_options', {options:patientClinicians}, function(s) {
      $(".app-patient-clinicians-select").html(s);
    });
  });
}


function logout(isAutoLogout) {
  app_viewStack('signin-screen', DO_SCROLL);
  var jsonData = JSON.stringify({ sessionId: patient.cred.sessionId });
  $.post("app/logout", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    patient = null;
    var notificationText = patientFullName + ' logged out.';
    if (isAutoLogout) {
      notificationText = 'You have been automatically logged out due to inactivity';
    }
    if (app_idleInterval) {clearInterval(app_idleInterval)};
    var sticky = isAutoLogout;
    displayNotification(notificationText, sticky);
  });
}


function displayNotification(text, sticky) {
  $('#wdm-notification-text').html(text);
  if (sticky) {
    $('#wdm-notification').fadeIn(400);
  }
  else {
    $('#wdm-notification').fadeIn(400).delay(3000).fadeOut(400); 
  }
}


function getTodaysDateFormatted() {
  return dateFormat("fullDate");
}

function getDataTableName(item) {
  var value = '';
  if(item.medicalTest) {
    value = item.medicalTest.name;
  }
  else if(item.healthTrendReport) {
    value = item.healthTrendReport.name;
  }
  return value;
}
  

function getColumnValue(column, item) {
  var value = '';
  
  if (column.type == 'simple') {
    value = item[column.field];
  }
  else if (column.type == 'date') {
    value = dateFormat(item[column.field], 'mm/dd/yyyy')
  }
  else if (column.type == 'date-time') {
    value = dateFormat(item[column.field], 'mm/dd/yyyy hh:mm')
  }
  else {
    var columnFields = column.field.split('.'); 
    var columnField = columnFields[0];
    var columnFieldName = columnFields[1];

    if (column.type == 'name') {
      value = item[columnField][columnFieldName];
    }
    else if (column.type == 'clinician') {
      value = util_buildFullName(item[columnField]['firstName'], item[columnField]['middleName'], item[columnField]['lastName'])
    }
    else if (column.type == 'description') {
      value = item['appointmentType']['name'] + " with " + 
      util_buildFullName(item[columnField]['firstName'], item[columnField]['middleName'], item[columnField]['lastName'])
    }
    else if (column.type == 'prescriber') {
      var prescriber = util_buildFullName(item[columnField]['firstName'], item[columnField]['middleName'], item[columnField]['lastName']);
      value = prescriber + ", " + item[columnField]['credential']['name'];
    }
  }
  return value !== undefined ? value : '';
}

function saveNewPatient() {
  var phoneRegexObj = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
  var emailRegexObj = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i;
  var isValid = true;
  var updateEmail = false;
  var updatePassword = false;
  saveNewPatient_clearErrors();
  
  if($("#new-patient-last-name").val().length < 1) { util_showError('#new-patient-last-name-validation'); isValid = false; }
  if($("#new-patient-first-name").val().length < 1) { util_showError('#new-patient-first-name-validation'); isValid = false; }
  if($("#new-patient-dob").val().length < 1) { util_showError('#new-patient-dob-validation'); isValid = false; }
  if($("#new-patient-gender").val().length < 1) { util_showError('#new-patient-gender-validation'); isValid = false; }
  if($("#new-patient-address1").val().length < 1) { util_showError('#new-patient-address-validation'); isValid = false; }
  if($("#new-patient-city").val().length < 1) { util_showError('#new-patient-city-validation'); isValid = false; }
  if($("#new-patient-us-state").val().length < 1) { util_showError('#new-patient-us-state-validation'); isValid = false; }
  if($("#new-patient-postal-code").val().length < 1) { util_showError('#new-patient-postal-code-validation'); isValid = false; }
  if($("#new-patient-primary-phone").val().length < 1) { util_showError('#new-patient-primary-phone-validation'); isValid = false; }
  
  if ($.trim($("#new-patient-password").val()) != PASSWORD_PLACEHOLDER) {  
  updatePassword = true;
    if($.trim($("#new-patient-password").val()).length < 1) { util_showError('#new-patient-password-validation'); isValid = false; }
    if ($.trim($("#new-patient-password").val()).length > 0) {
      if($.trim($("#new-patient-password").val()).length < 6 || $.trim($("#new-patient-password-confirm").val()).length < 6) { 
        util_showError('#new-patient-password-validation', 'must be at least 6 chararcters long'); 
        isValid = false; 
      }
      if(util_checkPassword($.trim($("#new-patient-password").val())) == false) { 
        util_showError('#new-patient-password-validation', 'must contain a lowercase, uppercase, digit, and special character'); 
        isValid = false; 
      }
      if($.trim($("#new-patient-password").val()) != $.trim($("#new-patient-password-confirm").val())) { 
        util_showError('#new-patient-password-validation', 'passwords must match'); 
        isValid = false; 
      }
    }
  }
  
  if (updatePassword == false) {
    var args = {
      modalTitle:"Password Setup Required", 
      modalH3:"Please Set Password", 
      modalH4:"Your password needs to be created at this time.",
      cancelButton: null,
      okButton: 'OK'
    };
    RenderUtil.render('dialog/confirm', args, function(s) { 
      $('#modals-placement').html(s);
      $('#modal-confirm').modal('show'); 
      $('#app-modal-confirmation-btn').click(function(){  $('#modal-confirm').modal('hide');});
    });
    return;
  }

  
 /* 
  if (phoneRegexObj.test($('#new-patient-primary-phone').attr("value"))) {
    $("#new-patient-primary-phone").val($('#new-patient-primary-phone').attr("value").replace(phoneRegexObj, "($1) $2-$3"));
  } 
  else {
    $('#new-patient-primary-phone-validation').text('not a valid phone number format');
    util_showError('#new-patient-primary-phone-validation');
    isValid = false;
  }
  if($.trim($("#new-patient-secondary-phone").val()).length > 0) {
    if(regexObj.test($('#new-patient-secondary-phone').attr("value"))) {
      $("#new-patient-secondary-phone").val($('#new-patient-secondary-phone').attr("value").replace(phoneRegexObj, "($1) $2-$3"));
    } 
    else {
      $('#new-patient-secondary-phone-validation').text('secondary phone not a valid phone number format');
      util_showError('#new-patient-secondary-phone-validation');
      isValid = false;
    }
  }
  */
  
  if ($.trim($("#new-patient-email").val()) != app_currentUsername) {  
  updateEmail = true;
    if($.trim($("#new-patient-email").val()).length < 1) { util_showError('#new-patient-email-validation'); isValid = false; }
    var emailValid = util_checkRegexp($.trim($("#new-patient-email").val()), emailRegexObj);
    if(emailValid == false) { util_showError('#new-patient-email-validation', 'invalid email address'); isValid = false; }
    if($.trim($("#new-patient-email").val()) != $.trim($("#new-patient-email-confirm").val())) { 
      util_showError('#new-patient-email-validation', 'email addresses must match'); 
      isValid = false; 
    }
  }

  if (isValid == false) {
    return;
  }
  
    patient.cred.email =  $('#new-patient-email').val(); 
    patient.cred.password =  $('#new-patient-password').val(); 
    patient.cred.lastName =  $('#new-patient-last-name').val(); 
    patient.cred.firstName =  $('#new-patient-first-name').val(); 
    patient.cred.middleName =  $('#new-patient-middle-initial').val(); 
    patient.cred.govtId =  $('#new-patient-ssn').val(); 
    patient.demo.dob = util_formatDate('#new-patient-dob');
    patient.demo.gender =  createGender($('#new-patient-gender').val()); 
    var ms = new MaritalStatus(); ms.id = $('#new-patient-marital-status').val(); patient.demo.maritalStatus = ms;
    var race = new Race(); race.id = $('#new-patient-race').val(); patient.demo.race = race;
    var ethnicity = new Ethnicity(); ethnicity.id = $('#new-patient-ethnicity').val(); patient.demo.ethnicity = ethnicity;
    patient.demo.address1 =  $('#new-patient-address1').val(); 
    patient.demo.address2 =  $('#new-patient-address2').val(); 
    patient.demo.city =  $('#new-patient-city').val(); 
    patient.demo.usState =  new USState({id: $('#new-patient-us-state').val()}); 
    var usState = new USState(); usState.id = $('#new-patient-us-state').val(); patient.demo.usState = usState;
    patient.demo.postalCode =  $('#new-patient-postal-code').val(); 
    patient.demo.occupation =  $('#new-patient-occupation').val(); 
    patient.demo.employmentStatus =  $('#new-patient-employed').val(); 
    patient.demo.employer =  $('#new-patient-employer').val(); 
    patient.demo.schoolStatus =  $('#new-patient-school').val(); 
    patient.demo.schoolName =  $('#new-patient-schoolName').val(); 
    patient.demo.primaryPhone =  $('#new-patient-primary-phone').val(); 
    patient.demo.secondaryPhone =  $('#new-patient-secondary-phone').val(); 
    patient.demo.email =  $('#new-patient-email').val();
    patient.demo.occupation =  $('#new-patient-occupation').val();
    patient.demo.employmentStatus =  $('#new-patient-employed').val();
    patient.demo.employer =  $('#new-patient-employer').val();
    patient.demo.schoolStatus =  $('#new-patient-school').val();
    patient.demo.schoolName =  $('#new-patient-school-name').val();
    patient.demo.primaryPhone =  $('#new-patient-primary-phone').val();
    patient.demo.secondaryPhone =  $('#new-patient-secondary-phone').val();
    patient.pfsh.motherAlive =  $('input[name=new-patient-mother-alive]:checked').val() == 'true',
    patient.pfsh.motherDeathReason =  $.trim($("#new-patient-mother-death-reason").val());
    patient.pfsh.fatherAlive =  $('input[name=new-patient-father-alive]:checked').val() == 'true',
    patient.pfsh.fatherDeathReason =  $.trim($("#new-patient-father-death-reason").val());
    patient.hist.pastSM =  $.trim($("#new-patient-past-s-m").val());
    patient.hist.famHist =  $('input[name="new-patient-fam-hist"]:checked').map(function() {return this.value;}).get().join(',');
    patient.hist.famHistOther =  $.trim($("#new-patient-fam-hist-other").val());
    patient.hist.famHistNotes =  $.trim($("#new-patient-fam-hist-notes").val());
    patient.hist.allergFood =  $.trim($("#new-patient-allerg-food").val());
    patient.hist.allergDrug =  $.trim($("#new-patient-allerg-drug").val());
    patient.hist.allergEnv =  $.trim($("#new-patient-allerg-env").val());
    patient.hist.subst =  $('input[name="new-patient-subst"]:checked').map(function() {return this.value;}).get().join(',');
    patient.hist.smokePksDay = util_processNumber("#new-patient-smoke-pks-day", patient.hist.smokePksDay); 
    patient.hist.yearsSmoked = util_processNumber("#new-patient-years-smoked", patient.hist.yearsSmoked); 
    patient.hist.smokeYearsQuit  = util_processNumber("#new-patient-smoke-years-quit", patient.hist.smokeYearsQuit); 
    sessionId =  patient.cred.sessionId;
    
  var jsonData = JSON.stringify({
    patient: patient, 
    updateEmail: updateEmail,
    updatePassword: updatePassword,
    sessionId: patient.cred.sessionId 
  });
  
  $.post("app/saveNewPatient", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    if (parsedData.returnCode == RETURN_CODE_DUP_EMAIL) {
      var args = {
        modalTitle:"Email Address Already In Use", 
        modalH3:"Email Address Already In Use", 
        modalH4:"Please try again with a different email address.",
        cancelButton: null,
        okButton: 'OK'
      };
      RenderUtil.render('dialog/confirm', args, function(s) { 
        $('#modals-placement').html(s);
        $('#modal-confirm').modal('show'); 
        $('#app-modal-confirmation-btn').click(function(){  $('#modal-confirm').modal('hide');});
      });
      return;
    }
    
    saveNewPatient_clearForm();
    app_viewStack('dashboard-screen', DO_SCROLL);
    displayNotification('Info for '+ parsedData.patient.cred.firstName + ' ' + parsedData.patient.cred.lastName + ' updated.');
  });
}

function saveNewPatient_clearForm() {
  $('#new-patient-mrn').val('');
  $('#new-patient-last-name').val('');
  $('#new-patient-first-name').val('');
  $('#new-patient-middle-initial').val('');
  $('#new-patient-ssn').val('');
  $('#new-patient-dob').val('');
  $('#new-patient-gender').val('');
  $('#new-patient-marital-status').val('');
  $('#new-patient-race').val('7');
  $('#new-patient-ethnicity').val('3');
  $('#new-patient-address1').val('');
  $('#new-patient-address2').val('');
  $('#new-patient-city').val('');
  $('#new-patient-us-state').val('');
  $('#new-patient-postal-code').val('');
  $('#new-patient-status').val('');
  $('#new-patient-occupation').val('');
  $('#new-patient-employed').val('');
  $('#new-patient-employer').val('');
  $('#new-patient-school').val('');
  $('#new-patient-school-name').val('');
  $('#new-patient-primary-phone').val('');
  $('#new-patient-secondary-phone').val('');
  $('#new-patient-email').val('');
  saveNewPatient_clearErrors();
}

function saveNewPatient_clearErrors() {
  $('#new-patient-last-name-validation').css({visibility: "hidden"});
  $('#new-patient-first-name-validation').css({visibility: "hidden"});
  $('#new-patient-ssn-validation').css({visibility: "hidden"});
  $('#new-patient-dob-validation').css({visibility: "hidden"});
  $('#new-patient-gender-validation').css({visibility: "hidden"});
  $('#new-patient-address-validation').css({visibility: "hidden"});
  $('#new-patient-city-validation').css({visibility: "hidden"});
  $('#new-patient-us-state-validation').css({visibility: "hidden"});
  $('#new-patient-postal-code-validation').css({visibility: "hidden"});
  $('#new-patient-primary-phone-validation').css({visibility: "hidden"});
  $('#new-patient-email-validation').css({visibility: "hidden"});
}


function getStaticLists() {
  $.post("app/getStaticLists", {}, function(data) {
    parsedData = $.parseJSON(data);
    app_usStates = parsedData.usStates;
 });
}


function setupPictureUpload() {
  $('#new-patient-photo-upload').click(function(){ 
    $('#new-patient-photo-upload-progress .progress-bar').css('width', '0');
  });
  uploader = new qq.FileUploader({
   element: document.getElementById('new-patient-photo-upload'),
   action: 'app/uploadProfileImage?patientId='+patient.id+'&sessionId=' + patient.cred.sessionId,
   debug: true,
   allowedExtensions: ['jpg', 'jpeg', 'png', 'gif'],
   sizeLimit: 1048576,   
   onProgress: function(id, fileName, loaded, total) {
      var progress = parseInt(loaded / total * 100, 10);
      $('#new-patient-photo-upload-progress .progress-bar').css('width', progress + '%');
   },
   onComplete: function(id, fileName, responseJSON){
     $('#new-patient-photo-upload-progress .progress-bar').css('width', '100%');
   var response = parsedData = $.parseJSON(responseJSON);
   app_profileImageTempPath = response.filename;
     $("#new-patient-photo").attr("src","images/"+app_profileImageTempPath);
   }
  }); 
}
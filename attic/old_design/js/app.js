var DO_SCROLL = true;
var DONT_SCROLL = false;
var DO_AUTO_LOGOUT = true;
var DO_AUTO_SERVER_LOGOUT = true;
var INITIALIZED = false;
var PATIENT_STATUS_AUTHORIZED = 1;
var PATIENT_STATUS_NOT_FOUND = 0;
var PATIENT_STATUS_INVALID_PASSWORD = -1;
var PATIENT_STATUS_INACTIVE = -2;
var DEFAULT_TABLE_WIDTH = 772;

var HEALTH_TREND_VITAL_SIGNS = 1;
var HEALTH_TREND_DM_DATA = 2;
var HEALTH_TREND_LIPIDS = 3;

var currentScreen = "home_screen";
var previousScreen = "home_screen";
var currentDetailScreen = "mr1_content";
var previousDetailScreen = "mr1_content";
var patientFullName = "";
var app_currentMedicalTestName = "";
var app_currentHealthIssueName = "";
var app_chartMap = {};

var contentLabels = 
['#mr1_content','#mr1_content_details','#mr2_content','#mr3_content','#mr4_content','#mr5_content','#mr6_content','#mr7_content', '#mr7_content_details',
 '#mr8_content','#mr9_content', '#ms1_content','#ms2_content','#ms3_content','#ms4_content','#ms5_content','#ms6_content',
 '#a1_content','#a2_content','#a3_content', '#faq_content','#contact_content','#family_content','#admin_content'];

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
var appointments;
var patientClinicians;

var patient = null;
var app_templateNames = ['simple_data_table', 'medications_list', 'patient_clinician_select_options'];

debug("console debugging enabled.");


$('#home_link').on('click', function(e){ e.preventDefault(); app_viewStack('home_screen', DO_SCROLL); });
$('#med_records_link').on('click', function(e) { e.preventDefault(); app_viewStack('med_records_screen', DO_SCROLL); });
$('#med_records_link_back').on('click', function(e){ e.preventDefault(); app_viewStack('home_screen', DO_SCROLL); });
$('#message_center_link').on('click', function(e){ e.preventDefault(); app_viewStack('message_center_screen', DO_SCROLL); });
$('#message_center_link_back').on('click', function(e){ e.preventDefault(); app_viewStack('home_screen', DO_SCROLL); });
$('#appt_link').on('click', function(e){ e.preventDefault(); app_viewStack('appt_screen', DO_SCROLL); });
$('#appt_link_back').on('click', function(e){ e.preventDefault(); app_viewStack('home_screen', DO_SCROLL); });

$('#detail_screen_back').on('click', function(e){ 
  e.preventDefault(); 
  if (currentDetailScreen == "mr1_content_details") {
    detail_viewStack(previousDetailScreen, DO_SCROLL); 
  }
  else if (currentDetailScreen == "mr7_content_details") {
    detail_viewStack(previousDetailScreen, DO_SCROLL); 
  }
  else {
    app_viewStack('med_records_screen', DO_SCROLL); 
  }
});
  
$('#mr1').on('click', function(e){ e.preventDefault(); detail_viewStack('mr1_content', DO_SCROLL); });
$('#mr2').on('click', function(e){ e.preventDefault(); detail_viewStack('mr2_content', DO_SCROLL); });
$('#mr3').on('click', function(e){ e.preventDefault(); detail_viewStack('mr3_content', DO_SCROLL); });
$('#mr4').on('click', function(e){ e.preventDefault(); detail_viewStack('mr4_content', DO_SCROLL); });
$('#mr5').on('click', function(e){ e.preventDefault(); detail_viewStack('mr5_content', DO_SCROLL); });
$('#mr6').on('click', function(e){ e.preventDefault(); detail_viewStack('mr6_content', DO_SCROLL); });
$('#mr7').on('click', function(e){ e.preventDefault(); detail_viewStack('mr7_content', DO_SCROLL); });
$('#mr8').on('click', function(e){ e.preventDefault(); detail_viewStack('mr8_content', DO_SCROLL); });
$('#mr9').on('click', function(e){ e.preventDefault(); detail_viewStack('mr9_content', DO_SCROLL); });
$('#ms1').on('click', function(e){ e.preventDefault(); detail_viewStack('ms1_content', DO_SCROLL); });
$('#ms2').on('click', function(e){ e.preventDefault(); detail_viewStack('ms2_content', DO_SCROLL); });
$('#ms3').on('click', function(e){ e.preventDefault(); detail_viewStack('ms3_content', DO_SCROLL); });
$('#ms4').on('click', function(e){ e.preventDefault(); detail_viewStack('ms4_content', DO_SCROLL); });
$('#ms5').on('click', function(e){ e.preventDefault(); detail_viewStack('ms5_content', DO_SCROLL); });
$('#ms6').on('click', function(e){ e.preventDefault(); detail_viewStack('ms6_content', DO_SCROLL); });
$('#a1').on('click', function(e){ e.preventDefault(); detail_viewStack('a1_content', DO_SCROLL); });
$('#a2').on('click', function(e){ e.preventDefault(); detail_viewStack('a2_content', DO_SCROLL); });
$('#a3').on('click', function(e){ e.preventDefault(); detail_viewStack('a3_content', DO_SCROLL); });
$('#faq_link').on('click', function(e){ e.preventDefault(); detail_viewStack('faq_content', DO_SCROLL); });
$('#reg_link').on('click', function(e){ e.preventDefault(); detail_viewStack('contact_content', DO_SCROLL); });
$('#family_link').on('click', function(e){ e.preventDefault(); detail_viewStack('family_content', DO_SCROLL); });
$('#admin_link').on('click', function(e){ e.preventDefault(); detail_viewStack('admin_content', DO_SCROLL); });
$('#message_link').on('click', function(e){ e.preventDefault(); detail_viewStack('ms5_content', DO_SCROLL); });

$('#login_submit').on('click', function(){ login(); });
$('#logout_submit').on('click', function(){ logout(); });
$('#ms3_submit').on('click', function(){ getMedicalAdvice(); });
$('#ms4_submit').on('click', function(){ requestRxRenewal(); });
$('#ms5_submit').on('click', function(){ sendMessageToProvider(); });
$('#ms6_submit').on('click', function(){ sendContactMessage(); });
$('#a3-submit').on('click', function(){ requestAppointment(); });



/***********      @JQUERY INIT    *******************/
$(document).ready(function() {
  if (INITIALIZED == false) {
    INITIALIZED = true;
    app_viewStack('login_screen', DO_SCROLL);
    $('#loginForm').css({display: "block"});
    $('#logoutForm').css({display: "none"});
    $('#a3-appt-from').datepicker();
    $('#a3-appt-to').datepicker();
    RenderUtil.loadTemplates(app_templateNames, function() {
      Backbone.history.start();    
    });
  }
});

function displayNotification(text) {
  $('#wdm-notification-text').html(text);
  $('#wdm-notification').fadeIn(400).delay(3000).fadeOut(400); 
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
        value = item['appointmentType']['name'] + " with " + util_buildFullName(item[columnField]['firstName'], item[columnField]['middleName'], item[columnField]['lastName'])
      }
    }
    return value !== undefined ? value : '';
  }
  
  
  function getPatientMedications() {
    var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.sessionId });
    $.post("../app/getPatientMedications", {data:jsonData}, function(data) {
      var parsedData = $.parseJSON(data);
      patientMedications = parsedData.patientMedications;
      var s = RenderUtil.render(RenderUtil.get('medications_list'), {items:patientMedications});
      $('#patient_medications_table').html(s);
    });
  }
  
  
  function getPatientAllergens() {
    var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.sessionId });
    $.post("../app/getPatientAllergens", {data:jsonData}, function(data) {
      var parsedData = $.parseJSON(data);
      patientAllergens = parsedData.patientAllergens;
      var s = RenderUtil.render(RenderUtil.get('simple_data_table'), 
              {items:patientAllergens, 
              title:'Allergies', 
              clickable:false, 
              columns:[{title:'Allergen', field:'allergen.name', type:'name'}, 
                       {title:'Reaction', field:'comment', type:'simple'}]});
      $('#patient_allergies_table').html(s);
    });
  }
  
  
  function getPatientImmunizations() {
    var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.sessionId });
    $.post("../app/getPatientImmunizations", {data:jsonData}, function(data) {
      var parsedData = $.parseJSON(data);
      patientImmunizations = parsedData.patientImmunizations;
      var s = RenderUtil.render(RenderUtil.get('simple_data_table'), 
      {items:patientImmunizations, 
      title:'Immunizations', 
      clickable:false, 
      columns:[{title:'Immunization', field:'immunization.name', type:'name'}, 
               {title:'Date', field:'date', type:'date'}]});
      $('#patient_immunizations_table').html(s);
    });
  }
  
  
  function getPatientHealthIssues() {
    var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.sessionId });
    $.post("../app/getPatientHealthIssues", {data:jsonData}, function(data) {
      var parsedData = $.parseJSON(data);
      patientHealthIssues = parsedData.patientHealthIssues;
      var s = RenderUtil.render(RenderUtil.get('simple_data_table'), 
      {items:patientHealthIssues, 
      title:'Health Issues', 
      clickable:false, 
      columns:[{title:'Health Issue', field:'healthIssue.name', type:'name'}, 
               {title:'Date', field:'date', type:'date'}]});
      $('#patient_health_issues_table').html(s);
    });
  }

  
  function getPatientMedicalTests() {
    var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.sessionId });
    $.post("../app/getPatientMedicalTests", {data:jsonData}, function(data) {
      var parsedData = $.parseJSON(data);
      patientMedicalTests = parsedData.patientMedicalTests;
      var s = RenderUtil.render(RenderUtil.get('simple_data_table'), 
      {items:patientMedicalTests, 
      title:'Test Results', 
      clickable:true, 
      columns:[{title:'Date', field:'date', type:'date'}, 
               {title:'Test', field:'medicalTest.name', type:'name'}, 
               {title:'Ordered By', field:'clinician.firstName', type:'clinician'}, 
               {title:'Status', field:'status.name', type:'name'}]});
      $('#patient_medical_tests_table').html(s);
      $('.clickable').click( function(e){ 
        handleClickablePatientMedicalTestRow(e); 
      });
    });
  }
  
  function handleClickablePatientMedicalTestRow(e) {
    var id = undefined;
    var tableName = undefined;
    var attributes = e.currentTarget.attributes;
    for (i=0;i<attributes.length;i++) {
      if (attributes[i].name == 'name') {
        id = attributes[i].nodeValue; 
      }
      if (attributes[i].name == 'data-table-name') {
        app_currentMedicalTestName = attributes[i].nodeValue; 
      }
    }
    if (id !== undefined) {
      getPatientMedicalTestComponents(id);
    }
    detail_viewStack('mr1_content_details', DO_SCROLL);
  }
  
  function getPatientMedicalTestComponents(id) {
    var jsonData = JSON.stringify({ patientMedicalTestId: id, sessionId: patient.sessionId });
    $.post("../app/getPatientMedicalTestComponents", {data:jsonData}, function(data) {
      var parsedData = $.parseJSON(data);
     
      patientMedicalTestComponents = parsedData.patientMedicalTestComponents;
      var s = RenderUtil.render(RenderUtil.get('simple_data_table'), 
      {items:patientMedicalTestComponents, 
      title:'Component Results', 
      clickable:false, 
      columns:[{title:'Component', field:'name', type:'simple'}, 
               {title:'Your Value', field:'testValue', type:'simple'}, 
               {title:'Standard Range', field:'testRange', type:'simple'}, 
               {title:'Units', field:'units', type:'simple'}, 
               {title:'Flag', field:'flag', type:'simple'}]}); 
      $('#patient_medical_test_components_table').html(s);
     
    });
  }
  
  
  function getPatientProcedures() {
    var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.sessionId });
    $.post("../app/getPatientProcedures", {data:jsonData}, function(data) {
      var parsedData = $.parseJSON(data);
      patientMedicalProcedures = parsedData.patientMedicalProcedures;
      var s = RenderUtil.render(RenderUtil.get('simple_data_table'), 
      {items:patientMedicalProcedures, 
      title:'Preventative Care', 
      clickable:false, 
      columns:[{title:'Name', field:'medicalProcedure.name', type:'name'}, 
               {title:'Due Date', field:'dueDate', type:'date'}, 
               {title:'Status', field:'status.name', type:'name'}, 
               {title:'Last Done', field:'lastDone', type:'date'}]});
      $('#patient_medical_procedures_table').html(s);
    });
  }
  
  
  function getPatientHealthTrendReports() {
    var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.sessionId });
    $.post("../app/getPatientHealthTrendReports", {data:jsonData}, function(data) {
      var parsedData = $.parseJSON(data);
      patientHealthTrendReports = parsedData.patientHealthTrendReports;
      var s = RenderUtil.render(RenderUtil.get('simple_data_table'), 
      {items:patientHealthTrendReports, 
      title:'Available Reports', 
      clickable:true, 
      columns:[{title:'Available Reports', field:'healthTrendReport.name', type:'name'}]});
      $('#patient_health_trend_reports_table').html(s);
      $('.clickable').click( function(e){ 
        handleClickableHealthTrendReportsRow(e); 
      });
    });
  }
  
  function handleClickableHealthTrendReportsRow(e) {
    var id = undefined;
    var attributes = e.currentTarget.attributes;
    for (i=0;i<attributes.length;i++) {
      if (attributes[i].name == 'name') {
        id = attributes[i].nodeValue; 
      }
      if (attributes[i].name == 'data-table-name') {
        app_currentHealthIssueName = attributes[i].nodeValue; 
      }
    }
    if (id !== undefined) {
      if (id == HEALTH_TREND_VITAL_SIGNS) {  
        getPatientVitalSigns(id);
      }
      else if (id == HEALTH_TREND_DM_DATA) {  
        getPatientDMData(id);
      }
      else if (id == HEALTH_TREND_LIPIDS) {  
        getPatientLipids(id);
      }
    }
    detail_viewStack('mr7_content_details', DO_SCROLL);
  }
    
  function getPatientLetters() {
    var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.sessionId });
    $.post("../app/getPatientLetters", {data:jsonData}, function(data) {
      var parsedData = $.parseJSON(data);
      patientLetters = parsedData.patientLetters;
      var s = RenderUtil.render(RenderUtil.get('simple_data_table'), 
      {items:patientLetters, 
      title:'Letters', 
      tableId:'patient_letters', 
      clickable:true, 
      columns:[{title:'Date', field:'date', type:'date'}, 
               {title:'From', field:'clinician.firstName', type:'clinician'}, 
               {title:'Reason', field:'reason.name', type:'name'}, 
               {title:'Status', field:'status.name', type:'name'}]});
      $('#patient_letters_table').html(s);
      $('.clickable').click( function(e){ 
        handleClickablePatientLetterRow(e); 
      });
    });
  }
  
  function handleClickablePatientLetterRow(e) {
    var id = undefined;
    var tableId = undefined;
    var attributes = e.currentTarget.attributes;
    for (i=0;i<attributes.length;i++) {
      if (attributes[i].name == 'name') {
        id = attributes[i].nodeValue; 
      }
      if (attributes[i].name == 'id') {
        tableId = attributes[i].nodeValue; 
      }
    }
    
    if ($('#'+tableId+'-content td').html() != '') {
      $('#'+tableId+'-content td').html('');
      $('#'+tableId+'-content td').css({"padding-bottom":"0px"});
      return;
    }
    
    if (id !== undefined) {
      getPatientLetter(id, tableId);
    }
  }
  
  
  function getPatientLetter(id, tableId) {
    var jsonData = JSON.stringify({ id: id, sessionId: patient.sessionId });
    $.post("../app/getPatientLetter", {data:jsonData}, function(data) {
      var parsedData = $.parseJSON(data);
      var content = parsedData.content;
      var fromClinician = parsedData.fromClinician;
      $('.row-content td').html('');
      $('.row-content td').css({"padding-bottom":"0px"});
      $('#'+tableId+'-content td').html('<pre>'+content+'</pre>');
      $('#'+tableId+'-content td').css({"padding-bottom":"20px"});
      $('#'+tableId+' td').css({"font-weight":"normal"});
    });
  }
  
  function getPatientMessages() {
    var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.sessionId });
    $.post("../app/getPatientMessages", {data:jsonData}, function(data) {
      var parsedData = $.parseJSON(data);
      patientMessages = parsedData.patientMessages;
      var s = RenderUtil.render(RenderUtil.get('simple_data_table'), 
      {items:patientMessages, 
      title:'Messages', 
      tableId:'patient_messages', 
      clickable:true, 
      columns:[{title:'Date', field:'date', type:'date'}, 
               {title:'From', field:'clinician.firstName', type:'clinician'}, 
               {title:'Subject', field:'subject', type:'simple'}]});
      $('#patient_messages_table').html(s);
      $('.clickable').click( function(e){ 
        handleClickablePatientMessageRow(e); 
      });
    });
  }
  
  
  function getPatientSentMessages() {
    var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.sessionId });
    $.post("../app/getPatientSentMessages", {data:jsonData}, function(data) {
      var parsedData = $.parseJSON(data);
      patientMessages = parsedData.patientMessages;
      var s = RenderUtil.render(RenderUtil.get('simple_data_table'), 
      {items:patientMessages, 
      title:'Messages', 
      tableId:'patient_sent_messages', 
      clickable:true, 
      columns:[{title:'Date', field:'date', type:'date'}, 
               {title:'To', field:'clinician.firstName', type:'clinician'}, 
               {title:'Subject', field:'subject', type:'simple'}]});
      $('#patient_sent_messages_table').html(s);
      $('.clickable').click( function(e){ 
        handleClickablePatientMessageRow(e); 
      });
    });
  }
  
  function handleClickablePatientMessageRow(e) {
    var id = undefined;
    var tableId = undefined;
    var attributes = e.currentTarget.attributes;
    for (i=0;i<attributes.length;i++) {
      if (attributes[i].name == 'name') {
        id = attributes[i].nodeValue; 
      }
      if (attributes[i].name == 'id') {
        tableId = attributes[i].nodeValue; 
      }
    }
    
    if ($('#'+tableId+'-content td').html() != '') {
      $('#'+tableId+'-content td').html('');
      $('#'+tableId+'-content td').css({"padding-bottom":"0px"});
      return;
    }
    
    if (id !== undefined) {
      getPatientMessage(id, tableId);
    }
  }
  
  
  function getPatientMessage(id, tableId) {
    var jsonData = JSON.stringify({ id: id, sessionId: patient.sessionId });
    $.post("../app/getPatientMessage", {data:jsonData}, function(data) {
      var parsedData = $.parseJSON(data);
      var content = parsedData.content;
      var fromClinician = parsedData.fromClinician;
      $('.row-content td').html('');
      $('.row-content td').css({"padding-bottom":"0px"});
      $('#'+tableId+'-content td').html(content);
      $('#'+tableId+'-content td').css({"padding-bottom":"20px"});
      if (fromClinician == true) {
        $('#'+tableId+' td').css({"font-weight":"normal"});
      }
    });
  }
  

  function getUpcomingAppointments() {
    var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.sessionId });
    $.post("../app/getUpcomingAppointments", {data:jsonData}, function(data) {
      var parsedData = $.parseJSON(data);
      appointments = parsedData.appointments;
      var s = RenderUtil.render(RenderUtil.get('simple_data_table'), 
      {items:appointments, 
      title:'Appointments', 
      clickable:false, 
      columns:[{title:'Date/Time', field:'startTime', type:'date-time'}, 
               {title:'Description', field:'clinician.firstName', type:'description'}, 
               {title:'Department', field:'department.name', type:'name'}]});
      $('#upcoming_appointments_table').html(s);
    });
  }
  

  function getPastAppointments() {
    var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.sessionId });
    $.post("../app/getPastAppointments", {data:jsonData}, function(data) {
      var parsedData = $.parseJSON(data);
      appointments = parsedData.appointments;
      var s = RenderUtil.render(RenderUtil.get('simple_data_table'), 
      {items:appointments, 
      title:'Appointments', 
      clickable:false, 
      columns:[{title:'Date/Time', field:'startTime', type:'date-time'}, 
               {title:'Description', field:'clinician.firstName', type:'description'}, 
               {title:'Department', field:'department.name', type:'name'}]});
      $('#past_appointments_table').html(s);
    });
  }

function login() {
   if($.trim($("#login_username").val()).length < 1 || $.trim($("#login_password").val()).length < 1) { 
      return;
    }
    $('#loginRunning').css({display: "block"});
    var jsonData = JSON.stringify({ username: $('#login_username').val(), password: $('#login_password').val()});
    $.post("../app/login", {data:jsonData},
      function(data) {
        $('#loginError').css({display:'none'});
        var parsedData = $.parseJSON(data);
        patient = new Patient();
        patient.id = parsedData.id;
        patient.firstName = parsedData.firstName;
        patient.middleName = parsedData.middleName;
        patient.lastName = parsedData.lastName;
        patient.streetAddress1 = parsedData.streetAddress1;
        patient.streetAddress2 = parsedData.streetAddress2;
        patient.city = parsedData.city;
        patient.state = parsedData.state;
        patient.zip = parsedData.zip;
        patient.primaryPhone = parsedData.primaryPhone;
        patient.secondaryPhone = parsedData.secondaryPhone;
        patient.email = parsedData.email;
        patient.authStatus = parsedData.authStatus;
        patient.patientId = parsedData.patientId;
        patient.previousLoginTime = parsedData.previousLoginTime;
        patient.sessionId = parsedData.sessionId;
        patient.mrn = parsedData.mrn;
        
        $('#loginRunning').css({display: "none"});
        
        if (patient.authStatus == PATIENT_STATUS_AUTHORIZED) {
          app_viewStack('home_screen', DO_SCROLL);
          patientFullName = util_buildFullName(patient.firstName, patient.middleName, patient.lastName);
          $('#logout_patientFullName').text(patientFullName);
          $('#home_full_name').text(patientFullName);
          $('.patient_appt_name').text(patientFullName + " [" + patient.mrn + "]");
          
          $('#home_today').text(getTodaysDateFormatted());
          $('#home_previousLoginTime').css({visibility: "visible"});
          $('#loginForm').css({display: "none"});
          $('#logoutForm').css({display: "block"});
          $('#app_title').css({display: "none"});
          $('#welcome_message').css({display: "block"});
          
          buildFormControls(); 
        }  
        else  {
          if (patient.authStatus == PATIENT_STATUS_NOT_FOUND) {
            $('#loginError').text('User not found in system');
          }
          else if (patient.authStatus == PATIENT_STATUS_INVALID_PASSWORD) {
            $('#loginError').text('Invalid password');
          }
          else if (patient.authStatus == PATIENT_STATUS_INACTIVE) {
            $('#loginError').text('User is inactive');
          }
          $('#loginError').css({display:'block'});
        }
      }
    );  
}


function buildFormControls() {
   getPatientClinicians();
}


function getPatientClinicians() {
  var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.sessionId });
  $.post("../app/getPatientClinicians", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    patientClinicians = parsedData.patientClinicians;
    var s = RenderUtil.render(RenderUtil.get('patient_clinician_select_options'), {options:patientClinicians});
    $(".patient_clinicians_select").html(s);
  });
}


function logout() {
  app_viewStack('login_screen', DO_SCROLL);
  $('#loginForm').css({display: "block"});
  $('#logoutForm').css({display: "none"});
  $('#logout_patientFullName').text('');
  $('#home_today').text('');
  $('#home_full_name').text('');
  $('#app_title').css({display: "block"});
  $('#welcome_message').css({display: "none"});
  var jsonData = JSON.stringify({ sessionId: patient.sessionId });
  $.post("../app/logout", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    patient = null;
  });
}

function ms3_clearForm() {
  $('#ms3_clinicians').val('');
  $('#ms3_subject').val('');
  $('#ms3_message').val('');
  ms3_clearErrors();
}
function ms3_clearErrors() {
  $('#ms3_cliniciansValidation').css({visibility: "hidden"});
  $('#ms3_messageValidation').css({visibility: "hidden"});
}

function ms4_clearForm() {
  $('#ms4_clinicians').val('');
  $('#ms4_message').val('');
  ms4_clearErrors();
}
function ms4_clearErrors() {
  $('#ms4_cliniciansValidation').css({visibility: "hidden"});
  $('#ms4_messageValidation').css({visibility: "hidden"});
}

function ms5_clearForm() {
  $('#ms5_clinicians').val('');
  $('#ms5_subject').val('');
  $('#ms5_message').val('');
  ms5_clearErrors();
}
function ms5_clearErrors() {
  $('#ms5_cliniciansValidation').css({visibility: "hidden"});
  $('#ms5_messageValidation').css({visibility: "hidden"});
}

function ms6_clearForm() {
  $('#ms6_name').val('');
  $('#ms6_email').val('');
  $('#ms6_phone').val('');
  $('#ms6_comments').val('');
  ms6_clearErrors();
}
function ms6_clearErrors() {
  $('#ms6_nameValidation').css({visibility: "hidden"});
  $('#ms6_emailValidation').css({visibility: "hidden"});
  $('#ms6_phoneValidation').css({visibility: "hidden"});
  $('#ms6_commentsValidation').css({visibility: "hidden"});
}


function getMedicalAdvice() {
  var isValid = true;

  ms3_clearErrors();
  
  if($("#ms3_clinicians").val().length < 1) { 
    showError('#ms3_cliniciansValidation');
    isValid = false;
  }
  if($("#ms3_message").val().length < 1) { 
    showError('#ms3_messageValidation');
    isValid = false;
  }
  
  if (isValid == false) {
    return;
  }
  
  var jsonData = JSON.stringify({ 
    patientId: patient.id,
    sessionId: patient.sessionId, 
    subject: $('#ms3_subject').val(), 
    clinicianId: $('#ms3_clinicians').val(), 
    content: $('#ms3_message').val() 
  });
  $('#getMedicalAdviceRunning').css({display: "block"});
  $.post("../app/getMedicalAdvice", {data:jsonData}, function(data) {
    $('#getMedicalAdviceRunning').css({display: "none"});
    ms3_clearForm();
    var parsedData = $.parseJSON(data);
    displayNotification('Inquiry sent to provider.');
  });
}

function sendMessageToProvider() {
  var isValid = true;

  ms5_clearErrors();
  
  if($("#ms5_clinicians").val().length < 1) { 
    showError('#ms5_cliniciansValidation');
    isValid = false;
  }
  if($("#ms5_message").val().length < 1) { 
    showError('#ms5_messageValidation');
    isValid = false;
  }
  
  if (isValid == false) {
    return;
  }
  
  var jsonData = JSON.stringify({ 
    patientId: patient.id,
    sessionId: patient.sessionId, 
    subject: $('#ms5_subject').val(), 
    clinicianId: $('#ms5_clinicians').val(), 
    content: $('#ms5_message').val() 
  });
  $('#getMedicalAdviceRunning').css({display: "block"});
  $.post("../app/getMedicalAdvice", {data:jsonData}, function(data) {
    $('#getMedicalAdviceRunning').css({display: "none"});
    ms5_clearForm();
    var parsedData = $.parseJSON(data);
    displayNotification('Message sent to provider.');
  });
}


function sendContactMessage() {
  var isValid = true;

  ms6_clearErrors();
  
  if($("#ms6_name").val().length < 1) { 
    showError('#ms6_nameValidation');
    isValid = false;
  }
  if($("#ms6_comments").val().length < 1) { 
    showError('#ms6_commentsValidation');
    isValid = false;
  }
  
  if (isValid == false) {
    return;
  }
  
  var jsonData = JSON.stringify({ 
    patientId: patient.id,
    sessionId: patient.sessionId, 
    name: $('#ms6_name').val(), 
    email: $('#ms6_email').val(), 
    phone: $('#ms6_phone').val(), 
    comments: $('#ms6_comments').val() 
  });
  $('#sendContactMessageRunning').css({display: "block"});
  $.post("../app/sendContactMessage", {data:jsonData}, function(data) {
    $('#sendContactMessageRunning').css({display: "none"});
    ms6_clearForm();
    var parsedData = $.parseJSON(data);
    displayNotification('Contact Message Sent.');
  });
}


function requestRxRenewal() {
  var isValid = true;

  ms4_clearErrors();
  
  if($("#ms4_clinicians").val().length < 1) { 
    showError('#ms4_cliniciansValidation');
    isValid = false;
  }
  if($("#ms4_message").val().length < 1) { 
    showError('#ms4_messageValidation');
    isValid = false;
  }
  
  if (isValid == false) {
    return;
  }
  
  var jsonData = JSON.stringify({ 
    patientId: patient.id,
    sessionId: patient.sessionId, 
    subject: "Rx Renewal Request", 
    clinicianId: $('#ms4_clinicians').val(), 
    content: $('#ms4_message').val() 
  });
  $('#requestRxRenewalRunning').css({display: "block"});
  $.post("../app/requestRxRenewal", {data:jsonData}, function(data) {
    $('#requestRxRenewalRunning').css({display: "none"});
    ms4_clearForm();
    var parsedData = $.parseJSON(data);
    displayNotification('Prescription renewal request sent.');
  });
}

function requestAppointment() {
  var isValid = true;

  a3_clearErrors();
  
  if($("#a3-clinicians").val().length < 1) { 
    showError('#a3-clinicians-validation');
    isValid = false;
  }
  if($("#a3-would-see").val().length < 1) { 
    showError('#a3-would-see-validation');
    isValid = false;
  }
  if($("#a3-visit-reason").val().length < 1) { 
    showError('#a3-visit-reason-validation');
    isValid = false;
  }
  
  if (isValid == false) {
    return;
  }
  
  var jsonData = JSON.stringify({ 
    patientId: patient.id,
    sessionId: patient.sessionId, 
    clinicianId: $('#a3-clinicians').val(), 
    wouldSee: $('#a3-would-see').val(), 
    visitReason: $('#a3-visit-reason').val(), 
    apptFrom: $('#a3-appt-from').val(), 
    apptTo: $('#a3-appt-to').val(), 
    preferredTimes: getAppointmentRequestPreferredTimes(),
    content: $('#a3-message').val(), 
    subject: "Appointment   Request" 
  });
  $('#requestAppointmentRunning').css({display: "block"});
  $.post("../app/requestAppointment", {data:jsonData}, function(data) {
    $('#requestAppointmentRunning').css({display: "none"});
    a3_clearForm();
    var parsedData = $.parseJSON(data);
    displayNotification('Appointment request sent.');
  });
}

function getAppointmentRequestPreferredTimes() {
  var preferredTimesArray = 
    [$('#a3-mon-morn'),$('#a3-mon-aft'),$('#a3-tues-morn'),$('#a3-tues-aft'),$('#a3-wed-morn'),  
     $('#a3-wed-aft'),$('#a3-thurs-morn'),$('#a3-thurs-aft'),$('#a3-fri-morn'),$('#a3-fri-aft')]; 
  var preferredTimes = ""; 
  for (var i=0;i<preferredTimesArray.length;i++) {
    if (preferredTimesArray[i].prop('checked') == true) {
      preferredTimes += preferredTimesArray[i].val() + ", ";
    }
  }  
  return preferredTimes.substring(0, preferredTimes.length - 2);
}

function appointmentRequest_clearForm() {
  $('#a3-clinicians').val('');
  $("#a3-would-see").prop("selectedIndex", 0);
  $("#a3-visit-reason").prop("selectedIndex", 0);
  $('#a3-appt-from').val('');
  $('#a3-appt-to').val('');
  $('#a3-message').val('');
  $('.a3-checkbox').prop('checked', false);
  a3_clearErrors();
}
function appointmentRequest_clearErrors() {
  $('#a3-clinicians-validation').css({visibility: "hidden"});
  $('#a3-would-see-validation').css({visibility: "hidden"});
  $('#a3-visit-reason-validation').css({visibility: "hidden"});
}


function getTodaysDateFormatted() {
  return dateFormat("fullDate");
}

function showError(item, message) {
  //$(item).css({display:'inline'});
  if (message == null) {
    message = 'required field';
  }
  $(item).text(message);
  $(item).css({opacity: 0.0, visibility: "visible"}).animate({opacity: 1.0}); 
}


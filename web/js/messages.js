$('#app-inbox-btn').click(function(e){getPatientMessages();});
$('#app-sent-messages-btn').click(function(e){getPatientSentMessages();});
$('#send-message-submit').click(function(e){sendMessageToProvider();});
$('#app-rx-request-btn').click(function(e){rxRequest_clearForm();});
$('#rx-request-submit').click(function(e){rxRequest();});


function getPatientMessages() {
  var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.cred.sessionId });
  $.post("app/getPatientMessages", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    patientMessages = parsedData.patientMessages;
    RenderUtil.render('simple_data_table', 
    {items:patientMessages, 
    title:'Messages', 
    tableId:'patient_messages', 
    columns:[{title:'Date', field:'date', type:'date'}, 
             {title:'From', field:'clinician.firstName', type:'clinician'}, 
             {title:'Subject', field:'subject', type:'simple'}]},
    function(s) { 
      $('#patient_messages_table').html(s);
      $('.clickable-table-row').click( function(e) { 
        $(this).addClass('table-row-highlight').siblings().removeClass('table-row-highlight');
        handleClickablePatientMessageRow(e); 
      });
    });
  });
}


function getPatientSentMessages() {
  var jsonData = JSON.stringify({ id: patient.id, sessionId: patient.cred.sessionId });
  $.post("app/getPatientSentMessages", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    patientMessages = parsedData.patientMessages;
    RenderUtil.render('simple_data_table', 
    {items:patientMessages, 
    title:'Messages', 
    tableId:'patient_sent_messages', 
    columns:[{title:'Date', field:'date', type:'date'}, 
             {title:'To', field:'clinician.firstName', type:'clinician'}, 
             {title:'Subject', field:'subject', type:'simple'}]},
    function(s) {
      $('#patient_sent_messages_table').html(s);
      $('.clickable-table-row').click( function(e) { 
        $(this).addClass('table-row-highlight').siblings().removeClass('table-row-highlight');
        handleClickablePatientMessageRow(e); 
      });
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
    $('tr.row-content').css({"display":"none"});
    return;
  }
  if (id !== undefined) {
    getPatientMessage(id, tableId);
  }
}
  
  
function getPatientMessage(id, tableId) {
  var jsonData = JSON.stringify({ id: id, sessionId: patient.cred.sessionId });
  $.post("app/getPatientMessage", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    var content = parsedData.content;
    var fromClinician = parsedData.fromClinician;
    $('tr.row-content').css({"display":"table-row"});
    $('.row-content td').html('');
    $('.row-content td').css({"padding-bottom":"0px"});
    $('#'+tableId+'-content td').html(content);
    $('#'+tableId+'-content td').css({"padding-bottom":"20px"});
    if (fromClinician == true) {
      $('#'+tableId+' td').css({"font-weight":"normal"});
    }
  });
}

function sendMessageToProvider() {
  var isValid = true;

  sendMessage_clearErrors();
  
  if($("#send-message-clinician").val().length < 1) { 
    util_showError('#send-message-clinician-validation');
    isValid = false;
  }
  if($("#send-message-message").val().length < 1) { 
    util_showError('#send-message-message-validation');
    isValid = false;
  }
  
  if (isValid == false) {
    return;
  }
  
  var jsonData = JSON.stringify({ 
    patientId: patient.id,
    sessionId: patient.cred.sessionId, 
    subject: $('#send-message-subject').val(), 
    clinicianId: $('#send-message-clinician').val(), 
    content: $('#send-message-message').val() 
  });
  $('#getMedicalAdviceRunning').css({display: "block"});
  $.post("app/getMedicalAdvice", {data:jsonData}, function(data) {
    $('#getMedicalAdviceRunning').css({display: "none"});
    sendMessage_clearForm();
    var parsedData = $.parseJSON(data);
    displayNotification('Message sent to provider.');
  });
}

function sendMessage_clearForm() {
  $('#send-message-clinician').val('');
  $('#send-message-subject').val('');
  $('#send-message-message').val('');
  sendMessage_clearErrors();
}
function sendMessage_clearErrors() {
  $('#send-message-clinician-validation').css({visibility: "hidden"});
  $('#send-message-message-validation').css({visibility: "hidden"});
}


function rxRequest() {
  var isValid = true;

  rxRequest_clearErrors();
  
  if($("#rx-request-clinician").val().length < 1) { 
    util_showError('#rx-request-clinician-validation');
    isValid = false;
  }
  if($("#rx-request-message").val().length < 1) { 
    util_showError('#rx-request-message-validation');
    isValid = false;
  }
  
  if (isValid == false) {
    return;
  }
  
  var jsonData = JSON.stringify({ 
    patientId: patient.id,
    sessionId: patient.cred.sessionId, 
    subject: "Rx Renewal Request", 
    clinicianId: $('#rx-request-clinician').val(), 
    content: $('#rx-request-message').val() 
  });
  $('#requestRxRenewalRunning').css({display: "block"});
  $.post("app/requestRxRenewal", {data:jsonData}, function(data) {
    $('#requestRxRenewalRunning').css({display: "none"});
    rxRequest_clearForm();
    var parsedData = $.parseJSON(data);
    displayNotification('Prescription renewal request sent.');
  });
}

function rxRequest_clearForm() {
  $('#rx-request-clinician').val('');
  $('#rx-request-message').val('');
  rxRequest_clearErrors();
}
function rxRequest_clearErrors() {
  $('#rx-request-clinician-validation').css({visibility: "hidden"});
  $('#rx-request-message-validation').css({visibility: "hidden"});
}
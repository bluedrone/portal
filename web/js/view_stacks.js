var hide_element_list_cache = $("#signin-screen, #new-patient-screen, #dashboard-screen, #your-records-screen, #family-records-screen, #messages-screen, #appointments-screen, #send-message-screen, #settings-screen");
var signin_cache = $("#signin-screen");
var new_patient_cache = $("#new-patient-screen");
var dashboard_cache = $("#dashboard-screen, #top-nav-panel");
var your_records_cache = $("#your-records-screen, #top-nav-panel");
var family_records_cache = $("#family-records-screen, #top-nav-panel");
var messages_cache = $("#messages-screen, #top-nav-panel");
var appointments_cache = $("#appointments-screen, #top-nav-panel");
var send_message_cache = $("#send-message-screen, #top-nav-panel");
var settings_cache = $("#settings-screen, #top-nav-panel");

function showScreen(screen) {
  hide_element_list_cache.css({display: "none"});
  screen.css({display: "block"});
}

function app_viewStack(screen, doScroll) {
  $('body').removeClass("signin");
  $('#top-nav-panel').css({display: "none"});
  $('#app-logout-submit').css({display: "inline-block"});
  switch (screen) {
    case 'signin-screen':
      $('body').addClass("signin");
      showScreen(signin_cache);
      $('#app-logout-submit').css({display: "none"});
    break;
    case 'new-patient-screen':
      showScreen(new_patient_cache);
      $('#app-logout-submit').css({display: "none"});
    break;
    case 'dashboard-screen':
      showScreen(dashboard_cache);
    break;
    case 'your-records-screen':
      showScreen(your_records_cache);
    break;        
    case 'family-records-screen':
      showScreen(family_records_cache);
    break; 
    case 'messages-screen':
      showScreen(messages_cache);
    break;
    case 'appointments-screen':
      showScreen(appointments_cache);
    break;
    case 'send-message-screen':
      showScreen(send_message_cache);
    break;
    case 'settings-screen':
      showScreen(settings_cache);
    break;
  }
  if (doScroll) {scroll(0,0);}
}
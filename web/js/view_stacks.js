var hideElementListCache = 
$('#signin-screen, #new-patient-screen, #dashboard-screen, #your-records-screen, #family-records-screen, #messages-screen, ' + 
'#appointments-screen, #send-message-screen, #settings-screen');
var signinCache = $('#signin-screen');
var newPatientCache = $('#new-patient-screen');
var dashboardCache = $('#dashboard-screen, #top-nav-panel');
var yourRecordsCache = $('#your-records-screen, #top-nav-panel');
var familyRecordsCache = $('#family-records-screen, #top-nav-panel');
var messagesCache = $('#messages-screen, #top-nav-panel');
var appointmentsCache = $('#appointments-screen, #top-nav-panel');
var sendMessageCache = $('#send-message-screen, #top-nav-panel');
var settingsCache = $('#settings-screen, #top-nav-panel');

function showScreen(screen) {
  hideElementListCache.css({display: "none"});
  screen.css({display: "block"});
}

function app_viewStack(screen, doScroll) {
  $('body').removeClass("signin");
  $('#top-nav-panel').css({display: "none"});
  $('#app-logout-submit').css({display: "inline-block"});
  switch (screen) {
    case 'signin-screen':
      $('body').addClass("signin");
      showScreen(signinCache);
      $('#app-logout-submit').css({display: "none"});
    break;
    case 'new-patient-screen':
      showScreen(newPatientCache);
      $('#app-logout-submit').css({display: "none"});
    break;
    case 'dashboard-screen':
      showScreen(dashboardCache);
    break;
    case 'your-records-screen':
      showScreen(yourRecordsCache);
    break;        
    case 'family-records-screen':
      showScreen(familyRecordsCache);
    break; 
    case 'messages-screen':
      showScreen(messagesCache);
    break;
    case 'appointments-screen':
      showScreen(appointmentsCache);
    break;
    case 'send-message-screen':
      showScreen(sendMessageCache);
    break;
    case 'settings-screen':
      showScreen(settingsCache);
    break;
  }
  if (doScroll) {scroll(0,0);}
}
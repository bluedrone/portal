function app_viewStack(screen, doScroll) {
  $('body').removeClass("signin");
  $('#top-nav-panel').css({display: "none"});
  $('#app-logout-submit').css({display: "inline-block"});
  switch (screen) {
    case 'signin-screen':
      $('body').addClass("signin");
      $('#signin-screen').css({display: "block"});
      $('#new-patient-screen').css({display: "none"});
      $('#dashboard-screen').css({display: "none"});
      $('#your-records-screen').css({display: "none"});
      $('#family-records-screen').css({display: "none"});
      $('#messages-screen').css({display: "none"});
      $('#appointments-screen').css({display: "none"});
      $('#send-message-screen').css({display: "none"});
      $('#settings-screen').css({display: "none"});
      $('#app-logout-submit').css({display: "none"});
    break;
    case 'new-patient-screen':
      $('#signin-screen').css({display: "none"});
      $('#new-patient-screen').css({display: "block"});
      $('#dashboard-screen').css({display: "none"});
      $('#your-records-screen').css({display: "none"});
      $('#family-records-screen').css({display: "none"});
      $('#messages-screen').css({display: "none"});
      $('#appointments-screen').css({display: "none"});
      $('#send-message-screen').css({display: "none"});
      $('#settings-screen').css({display: "none"});
      $('#top-nav-panel').css({display: "none"});
      $('#app-logout-submit').css({display: "none"});
    break;
    case 'dashboard-screen':
      $('#signin-screen').css({display: "none"});
      $('#new-patient-screen').css({display: "none"});
      $('#dashboard-screen').css({display: "block"});
      $('#your-records-screen').css({display: "none"});
      $('#family-records-screen').css({display: "none"});
      $('#messages-screen').css({display: "none"});
      $('#appointments-screen').css({display: "none"});
      $('#send-message-screen').css({display: "none"});
      $('#settings-screen').css({display: "none"});
      $('#top-nav-panel').css({display: "block"});
    break;
    case 'your-records-screen':
      $('#signin-screen').css({display: "none"});
      $('#new-patient-screen').css({display: "none"});
      $('#dashboard-screen').css({display: "none"});
      $('#your-records-screen').css({display: "block"});
      $('#family-records-screen').css({display: "none"});
      $('#messages-screen').css({display: "none"});
      $('#appointments-screen').css({display: "none"});
      $('#send-message-screen').css({display: "none"});
      $('#settings-screen').css({display: "none"});
      $('#top-nav-panel').css({display: "block"});
    break;        
    case 'family-records-screen':
      $('#signin-screen').css({display: "none"});
      $('#new-patient-screen').css({display: "none"});
      $('#dashboard-screen').css({display: "none"});
      $('#your-records-screen').css({display: "none"});
      $('#family-records-screen').css({display: "block"});
      $('#messages-screen').css({display: "none"});
      $('#appointments-screen').css({display: "none"});
      $('#send-message-screen').css({display: "none"});
      $('#settings-screen').css({display: "none"});
      $('#top-nav-panel').css({display: "block"});
    break; 
    case 'messages-screen':
      $('#signin-screen').css({display: "none"});
      $('#new-patient-screen').css({display: "none"});
      $('#dashboard-screen').css({display: "none"});
      $('#your-records-screen').css({display: "none"});
      $('#family-records-screen').css({display: "none"});
      $('#messages-screen').css({display: "block"});
      $('#appointments-screen').css({display: "none"});
      $('#send-message-screen').css({display: "none"});
      $('#settings-screen').css({display: "none"});
      $('#top-nav-panel').css({display: "block"});
    break;
    case 'appointments-screen':
      $('#signin-screen').css({display: "none"});
      $('#new-patient-screen').css({display: "none"});
      $('#dashboard-screen').css({display: "none"});
      $('#your-records-screen').css({display: "none"});
      $('#messages-screen').css({display: "none"});
      $('#your-records-screen').css({display: "none"});
      $('#family-records-screen').css({display: "none"});
      $('#appointments-screen').css({display: "block"});
      $('#send-message-screen').css({display: "none"});
      $('#settings-screen').css({display: "none"});
      $('#top-nav-panel').css({display: "block"});
    break;
    case 'send-message-screen':
      $('#signin-screen').css({display: "none"});
      $('#new-patient-screen').css({display: "none"});
      $('#dashboard-screen').css({display: "none"});
      $('#your-records-screen').css({display: "none"});
      $('#family-records-screen').css({display: "none"});
      $('#messages-screen').css({display: "none"});
      $('#appointments-screen').css({display: "none"});
      $('#send-message-screen').css({display: "block"});
      $('#settings-screen').css({display: "none"});
      $('#top-nav-panel').css({display: "block"});
    break;
    case 'settings-screen':
      $('#signin-screen').css({display: "none"});
      $('#new-patient-screen').css({display: "none"});
      $('#dashboard-screen').css({display: "none"});
      $('#your-records-screen').css({display: "none"});
      $('#family-records-screen').css({display: "none"});
      $('#messages-screen').css({display: "none"});
      $('#appointments-screen').css({display: "none"});
      $('#send-message-screen').css({display: "none"});
      $('#settings-screen').css({display: "block"});
      $('#top-nav-panel').css({display: "block"});
    break;
  }
  if (doScroll) {scroll(0,0);}
}
function detail_viewStack(screen, doScroll) {
  app_viewStack('detail_screen', DO_SCROLL); 

  for (var i=0;i<contentLabels.length;i++) {
    var contentLabel = contentLabels[i];
    if (contentLabel == '#' + screen) {
      $(contentLabel).css({display: "block"});
    }
    else {
      $(contentLabel).css({display: "none"});
    }
  }

  previousDetailScreen = currentDetailScreen;
  switch (screen) {
    case 'mr1_content':
      getPatientMedicalTests();
      $('#detail_heading').html("Test Results");
      $('#detail_icon').attr("src", "images/mr1_icon.png");
    break;
    case 'mr1_content_details':
      $('#detail_heading').html(app_currentMedicalTestName);
      $('#detail_icon').attr("src", "images/mr2_icon.png");
    break;
    case 'mr2_content':
      getPatientMedications();
      $('#detail_heading').html("Medications");
      $('#detail_icon').attr("src", "images/mr2_icon.png");
    break;
    case 'mr3_content':
      getPatientAllergens();
      $('#detail_heading').html("Allergies");
      $('#detail_icon').attr("src", "images/mr3_icon.png");
    break;
    case 'mr4_content':
      getPatientImmunizations();
      $('#detail_heading').html("Immunizations");
      $('#detail_icon').attr("src", "images/mr4_icon.png");
    break;
    case 'mr5_content':
      getPatientProcedures();
      $('#detail_heading').html("Preventive Care");
      $('#detail_icon').attr("src", "images/mr5_icon.png");
    break;
    case 'mr6_content':
      $('#detail_heading').html("Medical History");
      $('#detail_icon').attr("src", "images/mr6_icon.png");
    break;
    case 'mr7_content':
      getPatientHealthIssues();
      $('#detail_heading').html("Current Health Issues");
      $('#detail_icon').attr("src", "images/mr7_icon.png");
    break;
    case 'mr7_content_details':
      $('#detail_heading').html(app_currentHealthIssueName);
      $('#detail_icon').attr("src", "images/mr7_icon.png");
    break;
    case 'mr8_content':
      getPatientHealthTrendReports();
      $('#detail_heading').html("Health Trends");
      $('#detail_icon').attr("src", "images/mr8_icon.png");
    break;
    case 'mr9_content':
      getPatientLetters();
      $('#detail_heading').html("Letters");
      $('#detail_icon').attr("src", "images/mr9_icon.png");
    break;
    case 'ms1_content':
      getPatientMessages();
      $('#detail_heading').html("Inbox");
      $('#detail_icon').attr("src", "images/ms1_icon.png");
    break;
    case 'ms2_content':
      getPatientSentMessages();
      $('#detail_heading').html("Sent Messages");
      $('#detail_icon').attr("src", "images/ms2_icon.png");
    break;
    case 'ms3_content':
      $('#detail_heading').html("Get Medical Advice");
      $('#detail_icon').attr("src", "images/ms3_icon.png");
      ms3_clearForm();
    break;
    case 'ms4_content':
      $('#detail_heading').html("Request Rx Renewal");
      $('#detail_icon').attr("src", "images/ms4_icon.png");
    break;
    case 'ms5_content':
      $('#detail_heading').html("Send A Message To Your Doctor");
      $('#detail_icon').attr("src", "images/ms5_icon.png");
    break;
    case 'ms6_content':
      $('#detail_heading').html("Contact YourHealth");
      $('#detail_icon').attr("src", "images/ms6_icon.png");
    break;
    case 'a1_content':
      getUpcomingAppointments();
      $('#detail_heading').html("Upcoming Appointments");
      $('#detail_icon').attr("src", "images/a1_icon.png");
    break;
    case 'a2_content':
      getPastAppointments();
      $('#detail_heading').html("Past Appointments");
      $('#detail_icon').attr("src", "images/a2_icon.png");
    break;
    case 'a3_content':
      $('#detail_heading').html("Request Appointment");
      $('#detail_icon').attr("src", "images/a3_icon.png");
      a3_clearForm();
    break;
    case 'faq_content':
      $('#detail_heading').html("FAQs");
      $('#detail_icon').attr("src", "images/faq_icon.png");
    break;
    case 'contact_content':
      $('#detail_heading').html("New User Signup");
      $('#detail_icon').attr("src", "images/contact_icon.png");
    break;
    case 'family_content':
      $('#detail_heading').html("Family Access Settings");
      $('#detail_icon').attr("src", "images/family_icon.png");
    break;
    case 'admin_content':
      $('#detail_heading').html("Terms and Conditions");
      $('#detail_icon').attr("src", "images/admin_icon.png");
    break;
  }
  if (doScroll) {scroll(0,0);}
  currentDetailScreen = screen;
}


function app_viewStack(screen, doScroll) {

  if (screen != 'login_screen' && screen != 'home_screen' && screen != 'detail_screen' && patient == null) {
    return;
  }
 
  previousScreen = currentScreen;
  switch (screen) {
    case 'login_screen':
      $('#section_title').html("");
      $('#main_screen').css({display: "block"});
      $('#detail_screen').css({display: "none"});
      $('.your_prefs').css({display: "none"});
      if (patient != null) {
        $('#app_title').css({display: "none"});
        $('#welcome_message').css({display: "block"});
      }
      else {
        $('#app_title').css({display: "block"});
        $('#welcome_message').css({display: "none"});
      }
      $('#app_title').html("Patient Portal");
      $('#home_screen').css({display: "block"});
      $('#why_screen').css({display: "none"});
      $('#demo_screen').css({display: "none"});
      $('#demo_live_screen').css({display: "none"});
      $('#contact_screen').css({display: "none"});
      $('#appt_link_disabled').css({display: "block"});
      $('#appt_link').css({display: "none"});
      $('#appt_link_back').css({display: "none"});
      $('#features_screen').css({display: "none"});
      $('#arra_screen').css({display: "none"});
      $('#med_records_link_disabled').css({display: "block"});
      $('#med_records_link').css({display: "none"});
      $('#med_records_link_back').css({display: "none"});
      $('#message_center_link_disabled').css({display: "block"});
      $('#message_center_link').css({display: "none"});
      $('#message_center_link_back').css({display: "none"});
      $('#family_link_disabled').css({display: "block"});
      $('#family_link').css({display: "none"});
      $('#message_link_disabled').css({display: "block"});
      $('#message_link').css({display: "none"});
      $('#reg_link').css({display: "block"});
      $('#admin_link_disabled').css({display: "block"});
      $('#admin_link').css({display: "none"});
      $('#r3c1_why_tile').css({display: "none"});
      $('#r4c5_why_tile').css({display: "none"});
      $('#serpent').css({display: "none"});
      $('#gear3').css({display: "none"});
      $('#gear4').css({display: "none"});
      $('#tile1').css({display: "block"});
      $('#demo_live_screen_link_back').css({display: "none"});
      $('#med_records_img1').css({display: "none"});
      $('#med_records_img2').css({display: "none"});
      $('#med_records_screen').css({display: "none"});
      $('#message_center_screen').css({display: "none"});
      $('#appt_screen').css({display: "none"});
      $('#r5c2').css({display: "block"});
      $('#r5c3').css({display: "block"});
      $('#r5c4').css({display: "block"});
      $('#message_center_img1').css({display: "none"});
      $('#appt_img1').css({display: "none"});
      
      if($(window).width() < 500){console.log("home screen < 500")
        $('#r3c1, #r3c5, #r4c1, #r4c5, #r5c1, #r5c5').css({display: "block"});
        $('#r4c1').css({top: "187px"});
        $('#r5c1').css({top: "260px"});
        $('#r6c1, #r6c3').css({top: "771px"});
        $('#r7c4').css({top: "809px"});
        $('#r8c4').css({top: "847px"});
      }
      else if($(window).width() > 500 && $(window).width() < 1000){console.log("home screen between 500 and 1000")
        $('#r3c1, #r3c5, #r4c1, #r4c5, #r5c1, #r5c5').css({display: "block"});
      }
      else if($(window).width() > 1000){console.log("home screen > 1000")
        $('#r3c1, #r3c5, #r4c1, #r4c5, #r5c1, #r5c5').css({display: "block"});
      }
    break;
    case 'home_screen':
      $('#section_title').html("");
      $('#main_screen').css({display: "block"});
      $('#detail_screen').css({display: "none"});
      $('.your_prefs').css({display: "none"});
      if (patient != null) {
        $('#app_title').css({display: "none"});
        $('#welcome_message').css({display: "block"});
      }
      else {
        $('#app_title').css({display: "block"});
        $('#welcome_message').css({display: "none"});
      }
      $('#app_title').html("Patient Portal");
      $('#home_screen').css({display: "block"});
      $('#why_screen').css({display: "none"});
      $('#demo_screen').css({display: "none"});
      $('#demo_live_screen').css({display: "none"});
      $('#contact_screen').css({display: "none"});
      $('#appt_link_disabled').css({display: "none"});
      $('#appt_link').css({display: "block"});
      $('#appt_link_back').css({display: "none"});
      $('#features_screen').css({display: "none"});
      $('#arra_screen').css({display: "none"});
      $('#med_records_link_disabled').css({display: "none"});
      $('#med_records_link').css({display: "block"});
      $('#med_records_link_back').css({display: "none"});
      $('#message_center_link').css({display: "block"});
      $('#message_center_link_back').css({display: "none"});
      $('#family_link_disabled').css({display: "none"});
      $('#family_link').css({display: "block"});
      $('#message_link_disabled').css({display: "none"});
      $('#message_link').css({display: "block"});
      $('#reg_link').css({display: "block"});
      $('#admin_link_disabled').css({display: "none"});
      $('#admin_link').css({display: "block"});
      $('#r3c1_why_tile').css({display: "none"});
      $('#r4c5_why_tile').css({display: "none"});
      $('#serpent').css({display: "none"});
      $('#gear3').css({display: "none"});
      $('#gear4').css({display: "none"});
      $('#tile1').css({display: "block"});
      $('#demo_live_screen_link_back').css({display: "none"});
      $('#med_records_img1').css({display: "none"});
      $('#med_records_img2').css({display: "none"});
      $('#med_records_screen').css({display: "none"});
      $('#message_center_screen').css({display: "none"});
      $('#appt_screen').css({display: "none"});
      $('#r5c2').css({display: "block"});
      $('#r5c3').css({display: "block"});
      $('#r5c4').css({display: "block"});
      $('#message_center_img1').css({display: "none"});
      $('#appt_img1').css({display: "none"});
      
      if($(window).width() < 500){console.log("home screen < 500")
        $('#r3c1, #r3c5, #r4c1, #r4c5, #r5c1, #r5c5').css({display: "block"});
        $('#r4c1').css({top: "187px"});
        $('#r5c1').css({top: "260px"});
        $('#r6c1, #r6c3').css({top: "771px"});
        $('#r7c4').css({top: "809px"});
        $('#r8c4').css({top: "847px"});
      }
      else if($(window).width() > 500 && $(window).width() < 1000){console.log("home screen between 500 and 1000")
        $('#r3c1, #r3c5, #r4c1, #r4c5, #r5c1, #r5c5').css({display: "block"});
      }
      else if($(window).width() > 1000){console.log("home screen > 1000")
        $('#r3c1, #r3c5, #r4c1, #r4c5, #r5c1, #r5c5').css({display: "block"});
      }
    break;
    case 'med_records_screen':
      $('#section_title').html("Your Medical Health Records");
      $('#main_screen').css({display: "block"});
      $('#detail_screen').css({display: "none"});
      $('#app_title').css({display: "none"});
      $('#welcome_message').css({display: "block"});
      $('#med_records_link_disabled').css({display: "none"});
      $('#med_records_link').css({display: "none"});
      $('#med_records_link_back').css({display: "block"});
      $('.your_prefs').css({display: "block"});
      $('#med_records_img1').css({display: "block"});
      $('#med_records_img2').css({display: "block"});
      $('#home_screen').css({display: "none"});
      $('#med_records_screen').css({display: "block"});
      $('#message_center_screen').css({display: "none"});
      $('#appt_screen').css({display: "none"});
      $('#r5c2').css({display: "none"});
      $('#r5c3').css({display: "none"});
      $('#r5c4').css({display: "none"});
      $('#message_center_img1').css({display: "none"});
      $('#appt_link').css({display: "block"});
      $('#appt_link_back').css({display: "none"});
      $('#appt_img1').css({display: "none"});
      $('#message_center_link').css({display: "block"});
      $('#message_center_link_back').css({display: "none"});
      if($(window).width() < 500){
        $('#r3c5, #r4c1, #r4c5, #r5c1, #r5c5').css({display: "none"});
        $('#r3c1').css({display: "block"});
      }else if($(window).width() > 500 && $(window).width() < 1000){
        $('#r3c5, #r4c1, #r4c5, #r5c1, #r5c5').css({display: "none"});
        $('#r3c1').css({display: "block"});
      }else if($(window).width() > 1000){
        $('#r3c5, #r4c1, #r4c5, #r5c1, #r5c5').css({display: "none"});
        $('#r3c1').css({display: "block"});
      }
    break;
    case 'message_center_screen':
      $('#section_title').html("Message Center");
      $('#main_screen').css({display: "block"});
      $('#detail_screen').css({display: "none"});
      $('#app_title').css({display: "none"});
      $('#welcome_message').css({display: "block"});
      $('#med_records_link_disabled').css({display: "none"});
      $('#med_records_link').css({display: "block"});
      $('#med_records_link_back').css({display: "none"});
      $('.your_prefs').css({display: "block"});
      $('#med_records_img1').css({display: "none"});
      $('#med_records_img2').css({display: "none"});
      $('#home_screen').css({display: "none"});
      $('#med_records_screen').css({display: "none"});
      $('#message_center_screen').css({display: "block"});
      $('#appt_screen').css({display: "none"});
      $('#r5c2').css({display: "none"});
      $('#r5c3').css({display: "none"});
      $('#r5c4').css({display: "none"});
      $('#message_center_img1').css({display: "block"});
      $('#appt_link').css({display: "block"});
      $('#appt_link_back').css({display: "none"});
      $('#appt_img1').css({display: "none"});
      $('#message_center_link').css({display: "none"});
      $('#message_center_link_back').css({display: "block"});
      if($(window).width() < 500){console.log("message screen < 500")
        $('#r3c5, #r4c5, #r5c1, #r5c5').css({display: "none"});
        $('#r4c1').css({top: "114px"});
        $('#r6c1, #r6c3').css({top: "628px"});
        $('#r7c4').css({top: "667px"});
        $('#r8c4').css({top: "704px"});
        $('#message_center_img1').css({width: "294px", height: "67px", top: "555px", left: "0px"});
      }
      if($(window).width() > 500 && $(window).width() < 1000){console.log("message screen between 500 and 1000")
        $('#r3c1, #r3c5, #r4c1, #r4c5, #r5c1, #r5c5', "#med_records_link").css({display: "block"});
        $('#message_center_img1').css({width: "411px", height: "103px", top: "380px", left: "139px"});
        $('#r4c1').css({top: "271px"});
        $('#r5c1').css({top: "380px"});
      }
      if($(window).width() > 1000){console.log("message screen > 1000")
        $('#r3c1, #r3c5, #r4c1, #r4c5, #r5c1, #r5c5', "#med_records_link").css({display: "block"});
        $('#message_center_img1').css({width: "612px", height: "141px", top: "516px", left: "206px"});
        $('#r4c1').css({top: "369px"});
        $('#r5c1').css({top: "516px"});
      }
    break;
    case 'appt_screen':
      $('#section_title').html("Appointments");
      $('#main_screen').css({display: "block"});
      $('#detail_screen').css({display: "none"});
      $('#app_title').css({display: "none"});
      $('#welcome_message').css({display: "block"});
      $('#med_records_link_disabled').css({display: "none"});
      $('#med_records_link').css({display: "block"});
      $('#med_records_link_back').css({display: "none"});
      $('.your_prefs').css({display: "block"});
      $('#med_records_img1').css({display: "none"});
      $('#med_records_img2').css({display: "none"});
      $('#home_screen').css({display: "none"});
      $('#med_records_screen').css({display: "none"});
      $('#message_center_screen').css({display: "none"});
      $('#appt_screen').css({display: "block"});
      $('#r5c2').css({display: "none"});
      $('#r5c3').css({display: "none"});
      $('#r5c4').css({display: "none"});
      $('#message_center_img1').css({display: "none"});
      $('#appt_link').css({display: "none"});
      $('#appt_link_back').css({display: "block"});
      $('#appt_img1').css({display: "block"});
      $('#message_center_link').css({display: "block"});
      $('#message_center_link_back').css({display: "none"});
      if($(window).width() < 500){
        $('#r3c1, #med_records_link, #r3c5, #r4c1, #r4c5, #r5c5').css({display: "none"});
        $('#r5c1').css({top: "114px"});
        $('#appt_img1').css({width: "294px", height: "67px", top: "406px", left: "0px"});
        $('#r6c1, #r6c3').css({top: "479px"});
        $('#r7c4').css({top: "517px"});
        $('#r8c4').css({top: "555px"});
        $('#appt_img').css({display: "block", width: "194px", height: "67px", left: "-40px", top: "-47px", position: "absolute"});
      }else if($(window).width() > 500 && $(window).width() < 1000){
        $('#appt_img1').css({width: "411px", height: "212px", top: "271px", left: "139px"});
        $('#appt_img').css({display: "none"});
      }else if($(window).width() > 1000){
        $('#appt_img1').css({width: "612px", height: "288px", top: "369px", left: "206px"});
        $('#appt_img').css({display: "none"});
      }
    break;
    case 'detail_screen':
      $('#main_screen').css({display: "none"});
      $('#detail_screen').css({display: "block"});
    break;
  }
  
  if (doScroll) {scroll(0,0);}
  currentScreen = screen;
}
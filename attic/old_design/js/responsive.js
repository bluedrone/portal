$(window).bind('resize', function () {
  if($(window).width() < 500 && currentScreen == 'home_screen') {
    $('#r5c1').css({top: "260px"});
    $('#r4c1').css({top: "187px"});
    
    $('#r6c1, #r6c3').css({top: "771px"});
    $('#r7c4').css({top: "809px"});
    $('#r8c4').css({top: "847px"});
  }
  if($(window).width() > 500 && $(window).width() < 1000 && currentScreen == 'home_screen') {
    $('#r5c1').css({top: "380px"});
    $('#r4c1').css({top: "271px"});
    $('#r3c1, #r3c5, #r4c1, #r4c5, #r5c1, #r5c5').css({display: "block"});
    
    $('#r6c1, #r6c3').css({top: "489px"});
    $('#r7c4').css({top: "543px"});
    $('#r8c4').css({top: "597px"});
  }
  if($(window).width() > 1000 && currentScreen == 'home_screen'){
    $('#r3c1, #r3c5, #r4c1, #r4c5, #r5c1, #r5c5').css({display: "block"});
    $('#r5c1').css({top: "516px"});
    $('#r4c1').css({top: "369px"});
    
    $('#r6c1, #r6c3').css({top: "663px"});
    $('#r7c4').css({top: "737px"});
    $('#r8c4').css({top: "805px"});
  }
  
  if($(window).width() < 500 && currentScreen == 'med_records_screen') {
    $('#r3c5, #r4c1, #r4c5, #r5c1, #r5c5').css({display: "none"});
    $('#r3c1').css({display: "block"});
    
    $('#r6c1, #r6c3').css({top: "771px"});
    $('#r7c4').css({top: "809px"});
    $('#r8c4').css({top: "847px"});
  }
  if($(window).width() > 500 && $(window).width() < 1000 && currentScreen == 'med_records_screen') {
    $('#r3c5, #r4c1, #r4c5, #r5c1, #r5c5').css({display: "none"});
    $('#r3c1').css({display: "block"});
    
    $('#r6c1, #r6c3').css({top: "489px"});
    $('#r7c4').css({top: "543px"});
    $('#r8c4').css({top: "597px"});
  }
  if($(window).width() > 1000 && currentScreen == 'med_records_screen'){
    $('#r3c5, #r4c1, #r4c5, #r5c1, #r5c5').css({display: "none"});
    $('#r3c1').css({display: "block"});
    $('#r4c1').css({top: "369px"});
    $('#r5c1').css({top: "516px"});
    
    $('#r6c1, #r6c3').css({top: "663px"});
    $('#r7c4').css({top: "737px"});
    $('#r8c4').css({top: "805px"});
  }
  
  if($(window).width() < 500 && currentScreen == 'message_center_screen') {
    console.log("moving message screen < 500")
    $('#r3c1, #r3c5, #r4c1, #r4c5, #r5c1, #r5c5').css({display: "none"});
    //$('#message_center_img1').css({width: "294px", height: "67px", top: "260px", left: "0px"});
    $('#message_center_img1').css({width: "294px", height: "67px", top: "555px", left: "0px"});
    $('#r4c1').css({top: "114px", display: "block"});
    
    $('#r6c1, #r6c3').css({top: "628px"});
    $('#r7c4').css({top: "667px"});
    $('#r8c4').css({top: "704px"});
  }
  if($(window).width() > 500 && $(window).width() < 1000 && currentScreen == 'message_center_screen') {
    console.log("moving message screen between 500 and 1000")
    $('#r3c1, #r3c5, #r4c1, #r4c5, #r5c1, #r5c5, #med_records_link').css({display: "block"});
    $('#message_center_img1').css({width: "411px", height: "103px", top: "380px", left: "139px"});
    $('#r4c1').css({top: "271px"});
    $('#r5c1').css({top: "380px"});
    
    $('#r6c1, #r6c3').css({top: "489px"});
    $('#r7c4').css({top: "543px"});
    $('#r8c4').css({top: "597px"});
  }
  if($(window).width() > 1000 && currentScreen == 'message_center_screen') {
    console.log("moving message screen > 1000")
    $('#r3c1, #r3c5, #r4c1, #r4c5, #r5c1, #r5c5, #med_records_link').css({display: "block"});
    $('#message_center_img1').css({width: "612px", height: "141px", top: "516px", left: "206px"});
    $('#r4c1').css({top: "369px"});
    $('#r5c1').css({top: "516px"});
    
    $('#r6c1, #r6c3').css({top: "663px"});
    $('#r7c4').css({top: "737px"});
    $('#r8c4').css({top: "805px"});
  }
  
  if($(window).width() < 500 && currentScreen == 'appt_screen') {
    $('#r3c1, #med_records_link, #r3c5, #r4c1, #r4c5, #r5c5').css({display: "none"});
    $('#r5c1').css({top: "114px"});
    //$('#appt_img1').css({width: "294px", height: "67px", top: "260px", left: "0px"});
    $('#appt_img1').css({width: "294px", height: "67px", top: "406px", left: "0px"});
    $('#r6c1, #r6c3').css({top: "479px"});
    $('#r7c4').css({top: "517px"});
    $('#r8c4').css({top: "555px"});
    $('#appt_img').css({display: "block", width: "194px", height: "67px", left: "-40px", top: "-47px", position: "absolute"});
  }
  if($(window).width() > 500 && $(window).width() < 1000 && currentScreen == 'appt_screen') {
    $('#r3c1, #med_records_link, #r3c5, #r4c1, #r4c5, #r5c5').css({display: "block"});
    $('#appt_img').css({display: "none"});
    $('#r5c1').css({top: "380px"});
    $('#r4c1').css({top: "271px"});
    $('#appt_img1').css({top: "271px", left: "139px", width: "411px", height: "212px"});
    
    $('#r6c1, #r6c3').css({top: "489px"});
    $('#r7c4').css({top: "543px"});
    $('#r8c4').css({top: "597px"});
  }
  if($(window).width() > 1000 && currentScreen == 'appt_screen') {
    $('#r3c1, #med_records_link, #r3c5, #r4c1, #r4c5, #r5c5').css({display: "block"});
    $('#appt_img').css({display: "none"});
    $('#r5c1').css({top: "516px"});
    $('#r4c1').css({top: "369px"});
    $('#appt_img1').css({top: "369px", left: "206px", width: "612px", height: "288px"});
    
    $('#r6c1, #r6c3').css({top: "663px"});
    $('#r7c4').css({top: "737px"});
    $('#r8c4').css({top: "805px"});
  }
});

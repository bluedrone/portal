/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */

var debug = function (log_txt) {
  if (window.console != undefined) {
    console.log(log_txt);
  }
}

function util_processNumber(selector, property) {
  var value = $.trim($(selector).val());
  if (value.length > 0) {property = value;}
  return property;
}

function util_formatDate(selector) {
  //"Mar 17, 2014 10:01:12 PM";
  var date = Date.parse($.trim($(selector).val()));
  if (date.length > 0) {
    return dateFormat(date, "EEE MMM dd kk:mm:ss z yyyy"); 
  }
}

function util_processDate(selector, property) {
  //"Mar 17, 2014 10:01:12 PM";
  var date = $.trim($(selector).val());
  if (date.length > 0) {
    property = dateFormat(date, "EEE MMM dd kk:mm:ss z yyyy"); 
  }
  return property;
}

function util_checkRegexp(s, regexp) {
  return regexp.test(s);
}

function util_checkPassword(s) {
  if (util_checkRegexp(s,/[a-z]/) && util_checkRegexp(s,/[A-Z]/) && util_checkRegexp(s,/\d/) && util_checkRegexp(s,/\W/)) {
    return true;
  }
  return false;
}


function util_nullCheck(val) {
  if (typeof val !== 'undefined') {
    return val;
  }
  return "";
}


function util_buildFullName(first, middle, last) {
  var middleToken = "";
  if (typeof first !== 'undefined' && first.length > 0) {
  if (typeof middle !== 'undefined' && middle.length > 0) {
    middleToken = middle + " ";
  }
  return first + " " + middleToken + last;
  }
  else {
      return "";
  }
}

function util_checkSessionResponse(obj) {
  idleTime = 0;
  if (obj != undefined){
  if(obj.authenticated == false){
    //util_logout(DO_AUTO_LOGOUT);
    return false;
  }
  return true;
  }
  else {
   DO_AUTO_LOGOUT = false;
   util_logout(DO_AUTO_LOGOUT, DO_AUTO_SERVER_LOGOUT);
   return false;   
  }
}


function util_checkSession() {
  var jsonData = JSON.stringify({sessionId: user.sessionId});
  $.post("auth/checkSession",{data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    if(parsedData.authenticated == false){util_logout(DO_AUTO_LOGOUT);return false;}
  }); 
}


function util_selectCheckboxesFromList(list, name) {
  if (list !== undefined) { 
    var a = list.split(","); 
    for (i=0;i<a.length;i++) {
      var s = a[i];
        $("input[name="+name+"][value="+s+"]").attr("checked", true);
    }
  }
}


function util_showError(item, message) {
  if (message == null) {
    message = 'required field';
  }
  $(item).text(message);
  $(item).css({opacity: 0.0, visibility: "visible"}).animate({opacity: 1.0}); 
}
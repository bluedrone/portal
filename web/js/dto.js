/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */


function Credentials() { }
function Demographics() { }
function PFSH() { }
function MedicalHistory() { }
function USState() { }
function Ethnicity() { }
function Race() { }
function Gender() { }
function MaritalStatus() { }

function Patient() {
  this.id = 0;
  this.previousLoginTime = null;
  this.sessionId = 'NO_SESSION';
  this.cred = new Credentials();
  this.demo = new Demographics();
  this.pfsh = new PFSH();
  this.hist = new MedicalHistory();
}

function createGender(code) {
  var gender = new Gender();
  gender.code = code; 
  if (!code) {
    gender.id = 5;
    gender.name = 'Unreported';
    gender.code = 'U'; 
  }
  else if (code == 'M') {
    gender.id = 1;
    gender.name = 'Male';
  }
  else if (code == 'F') {
    gender.id = 2;
    gender.name = 'Female';
  }
  return gender;
}


/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */
 
package com.wdeanmedical.portal.dto;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;


import static javax.persistence.GenerationType.IDENTITY;

public class PasswordResetDTO extends DTO {
  private String email;


  public PasswordResetDTO() {
  }


  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

}

/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */
 
package com.wdeanmedical.portal.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "marital_status")
public class MaritalStatus extends BaseEntity implements Serializable {
  private static final long serialVersionUID = -8288916455837731262L;
  
  private String name;
  private String code;
    

   public MaritalStatus() {
   }
    
  @Column(name = "name")
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  @Column(name = "code")
  public String getCode() { return code; }
  public void setCode(String code) { this.code = code; }
    
}

package com.robert.java.unioviscope.model.dto;

import java.io.Serializable;

public class AttendanceCertificate implements Serializable {

	private static final long serialVersionUID = 5569643668529397265L;
	
	private String userName;
    private String token;
    private Long scanned;

    AttendanceCertificate() {
		
	}
    
    public AttendanceCertificate(String userName, String token, Long scanned) {
        this.userName = userName;
        this.token = token;
        this.scanned = scanned;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getScanned() {
        return scanned;
    }

    public void setScanned(Long scanned) {
        this.scanned = scanned;
    }
}

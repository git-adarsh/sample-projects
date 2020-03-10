package com.sample.orm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AUTH")
public class Auth implements Serializable {

	private static final long serialVersionUID = -7950821950425940299L;

	@Id
	@Column(name = "AUTH_ID", unique = true, length = 6, nullable = false)
	private String authId;

	// save hash instead of plain pwd
	@Column(name = "PWD")
	private String pwd;

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwd() {
		return pwd;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getAuthId() {
		return authId;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Auth Id: ");
		sb.append(authId);
		sb.append("\n");

		sb.append("pwd: ");
		sb.append(pwd);
		sb.append("\n");

		return sb.toString();
	}
}
package com.sample.orm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AUTH")
public class Auth implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7950821950425940299L;

	@Id
	@Column(name = "u_id", unique = true, length = 6, nullable = false)
	private String username;

	// save hash instead of plain pwd
	@Column(name = "pwd")
	private String pwd;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}

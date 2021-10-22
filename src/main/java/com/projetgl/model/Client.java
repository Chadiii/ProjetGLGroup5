package com.projetgl.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="clients")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Basic
	private String name;
	
	@Basic
	private String mail;
	
	@Basic
	private String ccName;
	
	@Basic
	private String ccNumber;
	
	@Basic
	private String ccExpiration;
	
	@Basic
	private String ccCCV;
	
	@OneToOne(mappedBy = "client", fetch = FetchType.EAGER)
	private Order order;
	
	public Client() {
		
	}

	public Client(String name, String mail, String ccName, String ccNumber, String ccExpiration, String ccCCV) {
	
		this.name = name;
		this.mail = mail;
		this.ccName = ccName;
		this.ccNumber = ccNumber;
		this.ccExpiration = ccExpiration;
		this.ccCCV = ccCCV;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCcName() {
		return ccName;
	}

	public void setCcName(String ccName) {
		this.ccName = ccName;
	}

	public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public String getCcExpiration() {
		return ccExpiration;
	}

	public void setCcExpiration(String ccExpiration) {
		this.ccExpiration = ccExpiration;
	}

	public String getCcCCV() {
		return ccCCV;
	}

	public void setCcCCV(String ccCCV) {
		this.ccCCV = ccCCV;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
}

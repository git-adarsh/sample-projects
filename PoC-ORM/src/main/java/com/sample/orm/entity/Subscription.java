package com.sample.orm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SUBSCRIPTION")
public class Subscription {
	@Id
	@GeneratedValue(generator = "subscription-id-generator")
	@GenericGenerator(name = "subscription-id-generator", strategy = "com.sample.orm.entity.idgenerator.SubscriptionIdGenerator")
	private String id;

	@Column(name = "summary")
	private String summary;

	@Column(name = "details")
	private String details;

	@Column(name = "price", nullable = false)
	private int packValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getPackValue() {
		return packValue;
	}

	public void setPackValue(int packValue) {
		this.packValue = packValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n Subscription [id=").append(id).append(", \n summary=").append(summary)
				.append(", \n details=").append(details).append(", \n packValue=").append(packValue).append("]");
		return builder.toString();
	}
}
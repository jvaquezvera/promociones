package com.posadas.nochespremio.dto;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="roomRate")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class RoomRateDTO implements Comparable<RoomRateDTO>{
	
	private Date startDate;
	private String start;
	private String end;
	private String roomType;
	private float amount;
	private String currency;
	private PaymentDTO payment;
	
	public Date getStartDate(){
		return this.startDate;
	}
	public String getStart() {
		return start;
	}
	
	@XmlAttribute(name="start")
	public void setStart(String start) throws ParseException {
		this.start = start;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		this.startDate = sdf.parse(start);
	}
	public String getEnd() {
		return end;
	}
	
	@XmlAttribute(name="end")
	public void setEnd(String end) {
		this.end = end;
	}
	public String getRoomType() {
		return roomType;
	}
	
	@XmlAttribute(name="roomType")
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public float getAmount() {
		return amount;
	}
	
	@XmlAttribute(name="amount")
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	
	@XmlAttribute(name="currency")
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public PaymentDTO getPayment() {
		return payment;
	}
	
	@XmlElement(name="payment")
	public void setPayment(PaymentDTO payment) {
		this.payment = payment;
	}
	
	@Override
	public String toString() {
		return "RoomRateDTO [start=" + start + ", end=" + end + ", roomType=" + roomType + ", amount=" + amount
				+ ", currency=" + currency + ", payment=" + payment + "]";
	}

	@Override
	public int compareTo(RoomRateDTO o) {
		return this.getStartDate().compareTo(o.getStartDate());
	}
	
}

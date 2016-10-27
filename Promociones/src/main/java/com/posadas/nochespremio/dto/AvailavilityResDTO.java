package com.posadas.nochespremio.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="availabilityRes")
@XmlAccessorType(XmlAccessType.FIELD)
public class AvailavilityResDTO {
	
	@XmlElement(name="requestId")
	private String requestId;
	
	@XmlElement(name="status")
	private String status;
	
	@XmlElement(name="programName")
	private String programName;
	
	@XmlElement(name="factor")
	private float factor;
	
	@XmlElement(name="rateExchange")
	private float rateExchange;

	@XmlElement(name="roomRate")
	@XmlElementWrapper(name="roomRates")
	private List<RoomRateDTO> roomRates;
	
	@XmlElement(name="payment")
	@XmlElementWrapper(name="totalPayment")
	private List<PaymentDTO> payments;
	
	public List<RoomRateDTO> getRoomRates() {
		return roomRates;
	}

	public void setRoomRates(List<RoomRateDTO> roomRates) {
		this.roomRates = roomRates;
	}

	public List<PaymentDTO> getPayments() {
		return payments;
	}

	public void setPayments(List<PaymentDTO> payments) {
		this.payments = payments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}
	
	public float getFactor() {
		return factor;
	}

	public void setFactor(float factor) {
		this.factor = factor;
	}

	public float getRateExchange() {
		return rateExchange;
	}

	public void setRateExchange(float rateExchange) {
		this.rateExchange = rateExchange;
	}

	@Override
	public String toString() {
		return "AvailavilityResDTO [roomRates=" + roomRates + ", payments=" + payments + ", status=" + status + "]";
	}

}
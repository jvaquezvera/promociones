package com.posadas.nochespremio.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "availabilityRes")
@XmlAccessorType(XmlAccessType.FIELD)
public class AvailavilityResDTO
{

   @XmlElement(name = "requestId")
   private String requestId;

   @XmlElement(name = "status")
   private String status;

   @XmlElement(name = "programName")
   private String programName;

   @XmlElement(name = "factor")
   private float factor;

   @XmlElement(name = "rateExchange")
   private float rateExchange;

   private String currency;

   private String claveHotel;

   //campos para descuentos
   private String dateInicialPromo;
   private String dateFinalPromo;
   private float porcentaje;

   @XmlElement(name = "roomRate")
   @XmlElementWrapper(name = "roomRates")
   private List<RoomRateDTO> roomRates;

   @XmlElement(name = "payment")
   @XmlElementWrapper(name = "totalPayment")
   private List<PaymentDTO> totalPayment;

   private float iva;

   public List<RoomRateDTO> getRoomRates()
   {
      return roomRates;
   }

   public void setRoomRates(List<RoomRateDTO> roomRates)
   {
      this.roomRates = roomRates;
   }

   public List<PaymentDTO> getTotalPayment()
   {
      return totalPayment;
   }

   public void setTotalPayment(List<PaymentDTO> totalPayment)
   {
      this.totalPayment = totalPayment;
   }

   public String getStatus()
   {
      return status;
   }

   public void setStatus(String status)
   {
      this.status = status;
   }

   public String getRequestId()
   {
      return requestId;
   }

   public void setRequestId(String requestId)
   {
      this.requestId = requestId;
   }

   public String getProgramName()
   {
      return programName;
   }

   public void setProgramName(String programName)
   {
      this.programName = programName;
   }

   public float getFactor()
   {
      return factor;
   }

   public void setFactor(float factor)
   {
      this.factor = factor;
   }

   public float getCurrency()
   {
      return factor;
   }

   public void setCurrency(float factor)
   {
      this.factor = factor;
   }

   public float getRateExchange()
   {
      return rateExchange;
   }

   public void setRateExchange(float rateExchange)
   {
      this.rateExchange = rateExchange;
   }

   public void setClaveHotel(String claveHotel)
   {
      this.claveHotel = claveHotel;
   }

   public String getClaveHotel()
   {
      return this.claveHotel;
   }

   public String getDateInicialPromo()
   {
      return dateInicialPromo;
   }

   public void setDateInicialPromo(String dateInicialPromo)
   {
      this.dateInicialPromo = dateInicialPromo;
   }

   public String getDateFinalPromo()
   {
      return dateFinalPromo;
   }

   public void setDateFinalPromo(String dateFinalPromo)
   {
      this.dateFinalPromo = dateFinalPromo;
   }

   public float getPorcentaje()
   {
      return porcentaje;
   }

   public void setPorcentaje(float porcentaje)
   {
      this.porcentaje = porcentaje;
   }

   @Override
   public String toString()
   {
      return "AvailavilityResDTO [roomRates=" + roomRates + ", payments=" + totalPayment + ", status=" + status + "]";
   }

   public float getIva()
   {
      return this.iva;
   }

   public void setIva(float iva)
   {
      this.iva = iva;
   }

   public AvailavilityResDTO()
   {
   }

   public AvailavilityResDTO(
         java.lang.String requestId,
         java.lang.String status,
         java.lang.String programName,
         float factor,
         float rateExchange,
         java.lang.String currency,
         java.lang.String claveHotel,
         java.lang.String dateInicialPromo,
         java.lang.String dateFinalPromo,
         float porcentaje,
         java.util.List<com.posadas.nochespremio.dto.RoomRateDTO> roomRates,
         java.util.List<com.posadas.nochespremio.dto.PaymentDTO> totalPayment,
         float iva)
   {
      this.requestId = requestId;
      this.status = status;
      this.programName = programName;
      this.factor = factor;
      this.rateExchange = rateExchange;
      this.currency = currency;
      this.claveHotel = claveHotel;
      this.dateInicialPromo = dateInicialPromo;
      this.dateFinalPromo = dateFinalPromo;
      this.porcentaje = porcentaje;
      this.roomRates = roomRates;
      this.totalPayment = totalPayment;
      this.iva = iva;
   }

}

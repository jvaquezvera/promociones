package com.posadas.nochespremio.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "payment")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentDTO implements Comparable<PaymentDTO>
{

   @XmlAttribute(name = "identifier")
   private Short identifier;

   @XmlAttribute(name = "points")
   private Integer points;

   @XmlAttribute(name = "roomType")
   private String roomType;

   @XmlAttribute(name = "mpoints")
   private int mixedPoints;

   @XmlAttribute(name = "mcash")
   private float cash;

   private float cashPromo;

   private Integer pointsPromo;

   private int mixedPointsPromo;

   private int percentage;

   public int getPercentage()
   {
      return percentage;
   }

   public void setPercentage(int percentage)
   {
      this.percentage = percentage;
   }	

   public float getCashPromo()
   {
      return cashPromo;
   }

   public void setCashPromo(float cashPromo)
   {
      this.cashPromo = cashPromo;
   }

   public Integer getPoints()
   {
      return points;
   }

   public void setPoints(Integer points)
   {
      this.points = points;
   }

   public String getRoomType()
   {
      return roomType;
   }

   public void setRoomType(String roomType)
   {
      this.roomType = roomType;
   }

   public int getMixedPoints()
   {
      return mixedPoints;
   }

   public void setMixedPoints(int mixedPoints)
   {
      this.mixedPoints = mixedPoints;
   }

   public float getCash()
   {
      return cash;
   }

   public void setCash(float cash)
   {
      this.cash = cash;
   }

   public Short getIdentifier()
   {
      return identifier;
   }

   public void setIdentifier(Short identifier)
   {
      this.identifier = identifier;
   }

   @Override
   public String toString()
   {
      return "PaymentDTO [points=" + points + ", roomType=" + roomType + ", mixedPoints=" + mixedPoints + ", cash="
            + cash + "]";
   }

   @Override
   public int compareTo(PaymentDTO o)
   {
      return this.getPoints().compareTo(o.getPoints());
   }

   public int getMixedPointsPromo()
   {
      return this.mixedPointsPromo;
   }

   public void setMixedPointsPromo(int mixedPointsPromo)
   {
      this.mixedPointsPromo = mixedPointsPromo;
   }

   public java.lang.Integer getPointsPromo()
   {
      return this.pointsPromo;
   }

   public void setPointsPromo(java.lang.Integer pointsPromo)
   {
      this.pointsPromo = pointsPromo;
   }

   public PaymentDTO()
   {
   }

   public PaymentDTO(java.lang.Short identifier, java.lang.Integer points,
         java.lang.String roomType, int mixedPoints, float cash,
         float cashPromo, java.lang.Integer pointsPromo, int mixedPointsPromo)
   {
      this.identifier = identifier;
      this.points = points;
      this.roomType = roomType;
      this.mixedPoints = mixedPoints;
      this.cash = cash;
      this.cashPromo = cashPromo;
      this.pointsPromo = pointsPromo;
      this.mixedPointsPromo = mixedPointsPromo;
   }

}

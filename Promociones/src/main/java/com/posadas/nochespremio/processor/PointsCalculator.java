package com.posadas.nochespremio.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.posadas.nochespremio.dto.AvailavilityResDTO;
import com.posadas.nochespremio.dto.PaymentDTO;
import com.posadas.nochespremio.dto.RoomRateDTO;

public class PointsCalculator {
	
	
	private float factorFR;
	private float factorMO;
	private float factorAP;
	private float factorISH;
	private float factorPoints;
	private float factorCash;
	
	private float iva;
	
	private static final String MXN="MXN";
	private static final String USD="USD";
//	private static final String DECIMAL_FORMAT="#.##";
	
	private static final float CONSTANT_POINTS = 1000;

	
	
 	public PaymentDTO calculate(String currency, float amount, float factor, float tc){
// 		DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
		PaymentDTO payment = new PaymentDTO();
		
		float totalAmount = amount * factorISH;
		int points=0;
		int mpoints=0;
		float cash=0;
		
		if(currency.equals(USD)){
			points = Math.round((totalAmount / factor) * CONSTANT_POINTS);
//			Calculo de puntos combinados
			mpoints = Math.round((totalAmount * factorPoints / factor) * CONSTANT_POINTS);
//			Redondeo siguiente decena
			points = ((int) Math.ceil(points/10.0)) * 10;
			mpoints = ((int) Math.ceil(mpoints/10.0)) * 10;
			
			cash = Math.round((totalAmount * factorCash * iva * tc));
			
		}else if(currency.equals(MXN)){
			points = Math.round(((totalAmount / tc) / factor) * CONSTANT_POINTS);
//			Calculo de puntos combinados
			mpoints = Math.round(((totalAmount * factorPoints / tc) / factor) * CONSTANT_POINTS);
//			Redondeo siguiente decena
			points = ((int) Math.ceil(points/10.0)) * 10;
			mpoints = ((int) Math.ceil(mpoints/10.0)) * 10;
			
			cash = Math.round(totalAmount * factorCash * iva);
		}

		payment.setPoints(points);
//		payment.setCash(new Float(df.format(cash)));
		payment.setCash(cash);
		payment.setMixedPoints(mpoints);
		
		return payment;
	}
 	
 	private float getFactor(String program){

 		if(program.equals("Motiva"))
 			return this.factorMO;
 		else if(program.equals("Apreciare"))
 			return this.factorAP;
 		else
 			return this.factorFR;
 		
 	}
 	
 	public void getPoints(AvailavilityResDTO avail) {
 		System.out.println("Usando tipo de cambio: "+avail.getRateExchange());
 		Map<String,TotalPoints> tot=new HashMap<>();
 		short identifier = 1;
 		float factor = this.getFactor(avail.getProgramName());
 		avail.setProgramName(avail.getProgramName());
 		avail.setFactor(factor);
 		avail.setRateExchange(avail.getRateExchange());
 		System.out.println("Calculando puntos con Factor: " + factor);
 		
 		for(RoomRateDTO roomRate : avail.getRoomRates()){
// 			Calculo de puntos por noche
 			PaymentDTO payment = this.calculate(roomRate.getCurrency(), roomRate.getAmount(), factor, avail.getRateExchange());
 			payment.setIdentifier(identifier++);
 			roomRate.setPayment(payment);
 			
 			if(tot.containsKey(roomRate.getRoomType())){
 				TotalPoints totAcum = tot.get(roomRate.getRoomType());
 				
// 				Calculo de puntos acumulados por tipo de habitacion
 				totAcum.cash += payment.getCash();
 				totAcum.mixedPoints += payment.getMixedPoints();
 				totAcum.points += payment.getPoints();
 				
 				tot.put(roomRate.getRoomType(), totAcum);
 			}else{
 				System.out.println("Agregando tipo de habitacion: " + roomRate.getRoomType());
 				TotalPoints totAcum = new TotalPoints(payment.getPoints(), 
 											payment.getMixedPoints(), 
 											payment.getCash());
 				tot.put(roomRate.getRoomType(), totAcum);
 			}
 		}
 		
 		
 		this.createTotalPayment(tot, avail);
 		Collections.sort(avail.getRoomRates(), new roomRatesComparator());
 		System.out.println("Avail con totales --> "+avail);
 		
 	}
 	
	private void createTotalPayment(Map<String,TotalPoints> tot, AvailavilityResDTO avail) {
		List<PaymentDTO> payments=new ArrayList<>(tot.size());
		avail.setTotalPayment(payments);
		
		for(Entry<String, TotalPoints> entry : tot.entrySet()){
//			DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
			PaymentDTO p=new PaymentDTO();
			p.setRoomType(entry.getKey());
			p.setPoints(entry.getValue().points);
//			p.setCash(new Float(df.format(entry.getValue().cash)));
			p.setCash(new Float(entry.getValue().cash));
			p.setMixedPoints(entry.getValue().mixedPoints);
			payments.add(p);
		}
		
		Collections.sort(payments);
	}
	
	private class roomRatesComparator implements Comparator<RoomRateDTO>{

		public int compare(RoomRateDTO rm1, RoomRateDTO rm2) {
			int result = rm1.compareTo(rm2);
			if(result != 0)
				return result;
			else
				return rm1.getPayment().compareTo(rm2.getPayment());
		}
	}
	
	private class TotalPoints{
		
		int points;
		int mixedPoints;
		float cash;
		
		public TotalPoints(int points, int mixedPoints, float cash) {
			this.cash = cash;
			this.points = points;
			this.mixedPoints = mixedPoints;
		}
		
		@Override
		public String toString() {
			return "TotalPoints [points=" + points + ", pointsMixed=" + mixedPoints + ", cash=" + cash + "]";
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.factorCash = 1 - this.factorPoints;
		
	}


	   public float getFactorFR() {
        return factorFR;
    }

    public void setFactorFR(float factorFR) {
        this.factorFR = factorFR;
    }

    public float getFactorMO() {
        return factorMO;
    }

    public void setFactorMO(float factorMO) {
        this.factorMO = factorMO;
    }

    public float getFactorAP() {
        return factorAP;
    }

    public void setFactorAP(float factorAP) {
        this.factorAP = factorAP;
    }

    public float getFactorISH() {
        return factorISH;
    }

    public void setFactorISH(float factorISH) {
        this.factorISH = factorISH;
    }

    public float getFactorPoints() {
        return factorPoints;
    }

    public void setFactorPoints(float factorPoints) {
        this.factorPoints = factorPoints;
    }

    public float getFactorCash() {
        return factorCash;
    }

    public void setFactorCash(float factorCash) {
        this.factorCash = factorCash;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }
	
        


}
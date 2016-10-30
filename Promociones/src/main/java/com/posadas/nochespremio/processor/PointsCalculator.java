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
import java.text.ParseException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PointsCalculator {
	
	
	private float factorFR;
	private float factorMO;
	private float factorAP;
	private float factorISH;
	private float factorPoints;
	private float factorCash;
	
	
	
	private static final String MXN="MXN";
	private static final String USD="USD";
//	private static final String DECIMAL_FORMAT="#.##";
	
	private static final float CONSTANT_POINTS = 1000;

	
	
 	public PaymentDTO calculate(String currency, float amount, float factor, float tc, float iva){
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
 	
 	public void getPoints(AvailavilityResDTO avail) throws ParseException {
 		System.out.println("Usando tipo de cambio: "+avail.getRateExchange());
		System.out.println("1"+factorFR);
	System.out.println("--"+factorMO);
	System.out.println("--"+factorAP);
	System.out.println("--"+factorISH);
	System.out.println("--"+factorPoints);
	System.out.println("-dateInicialPromo-"+avail.getDateInicialPromo());
   	System.out.println("-dateFinalPromo-"+avail.getDateFinalPromo());
   	System.out.println("-porcentaje-"+avail.getPorcentaje());
	System.out.println("-6-"+factorCash);



 		Map<String,TotalPoints> tot=new HashMap<>();
 		short identifier = 1;
 		float factor = this.getFactor(avail.getProgramName());
 		avail.setProgramName(avail.getProgramName());
 		avail.setFactor(factor);
 		System.out.println("Calculando puntos con Factor: " + factor);
                System.out.println("Calculando puntos con Factor 2: " + avail.getRoomRates());
 		
 		for(RoomRateDTO roomRate : avail.getRoomRates()){
// 			Calculo de puntos por noche
			roomRate.calculaStart();
			
 			PaymentDTO payment = this.calculate(roomRate.getCurrency(), roomRate.getAmount(), factor, avail.getRateExchange(),avail.getIva());
 			payment.setIdentifier(identifier++);
 			payment.setRoomType( roomRate.getRoomType());
			roomRate.setPayment(payment);
			
			System.out.println("roomRate despues del calculo: " + roomRate);
 			//aplicando descuentos
			System.out.println("validando fecha dentro de rango de promocion");
			if( isInPromotion(roomRate.getStart(),avail.getDateInicialPromo(),avail.getDateFinalPromo() )){
				System.out.println("aplicando promocion.....apicando"+avail.getPorcentaje());
				float descuento= 1- (avail.getPorcentaje()/100);
				roomRate.setPromotion(true);
			roomRate.getPayment().setPercentage(Math.round(avail.getPorcentaje()));
			roomRate.getPayment().setPointsPromo(Math.round(payment.getPoints() * descuento));
			roomRate.getPayment().setCashPromo(payment.getCash() * descuento );
			roomRate.getPayment().setMixedPointsPromo(Math.round(payment.getMixedPoints() * 					 descuento));							
		
			}else{
				System.out.println("fuera de promocion.....apicando"+avail.getPorcentaje());
			roomRate.getPayment().setPercentage(0);
			roomRate.getPayment().setPointsPromo(payment.getPoints());
			roomRate.getPayment().setCashPromo(payment.getCash());
			roomRate.getPayment().setMixedPointsPromo(payment.getMixedPoints());							
				
			}
					
	
 			if(tot.containsKey(roomRate.getRoomType())){
 				TotalPoints totAcum = tot.get(roomRate.getRoomType());
 				
// 				Calculo de puntos acumulados por tipo de habitacion
 				totAcum.cash += payment.getCash();
 				totAcum.mixedPoints += payment.getMixedPoints();
 				totAcum.points += payment.getPoints();
				totAcum.cashPromo += payment.getCashPromo();
 				totAcum.mixedPointsPromo += payment.getMixedPointsPromo();
 				totAcum.pointsPromo += payment.getPointsPromo();
				totAcum.porcentaje=payment.getPercentage();
 				
 				tot.put(roomRate.getRoomType(), totAcum);
 			}else{
 				System.out.println("Agregando tipo de habitacion: " + roomRate.getRoomType());
 				TotalPoints totAcum = new TotalPoints(payment.getPoints(), 
 											payment.getMixedPoints(), 
 											payment.getCash(),
											payment.getPointsPromo(),
											payment.getMixedPointsPromo(),
											payment.getCashPromo(),
											payment.getPercentage());
 				tot.put(roomRate.getRoomType(), totAcum);
 			}
 		}
 		
 		
 		this.createTotalPayment(tot, avail);
 		Collections.sort(avail.getRoomRates(), new roomRatesComparator());
 		System.out.println("Avail con totales --> "+avail);
 		
 	}
 	

	private boolean isInPromotion(String fechaReservaStr, String fechaInicalPromoStr, String fechaFinalPromoStr) {  
  System.out.println("fecha Reservacion = "+fechaReservaStr+"\n" +
    "inicio promocion = "+fechaInicalPromoStr+"\n fin de promocion: "+fechaFinalPromoStr);  
  
  boolean resultado=false;
  try {
   
   SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd"); 
   Date fechaReserva = formateador.parse(fechaReservaStr);
   Date fechaInicialPromo = formateador.parse(fechaInicalPromoStr);
   Date fechaFinalPromo = formateador.parse(fechaFinalPromoStr);
    
    if ( (fechaReserva.before(fechaFinalPromo)  || (fechaReserva.equals(fechaFinalPromo)) )&& 
            (fechaReserva.after(fechaInicialPromo)  || (fechaReserva.equals(fechaInicialPromo)) )
            ){
            
            resultado = true;
     }
  } catch (ParseException e) {
             return false;
  }  
    return resultado;
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
			p.setPointsPromo(entry.getValue().pointsPromo);
			p.setCashPromo(new Float(entry.getValue().cashPromo));
			p.setMixedPointsPromo(entry.getValue().mixedPointsPromo);
			p.setPercentage(Math.round(entry.getValue().porcentaje));
		
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
		int pointsPromo;
		int mixedPointsPromo;
		float cashPromo;
		private float porcentaje;
		
		public TotalPoints(int points, int mixedPoints, float cash,int pointsPromo, int mixedPointsPromo, float cashPromo, float porcentaje) {
			this.cash = cash;
			this.points = points;
			this.mixedPoints = mixedPoints;
			this.cashPromo = cashPromo;
			this.pointsPromo = pointsPromo;
			this.mixedPointsPromo = mixedPointsPromo;
			this.porcentaje=porcentaje;
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

    


}
package br.com.pontek.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import br.com.pontek.enums.FrequenciaDeLancamento;

public class DataUtil {
	
	
	/**Gera uma data a partir do indic da parcela e a frequencia*/
	public static Date geraDataVencimentoParcela(Date dataInicial, Integer indice, FrequenciaDeLancamento frequenciaDeLancamento){

		LocalDate data = dataInicial.toInstant().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDate();
		
		if(frequenciaDeLancamento.equals(FrequenciaDeLancamento.Di�rio)){
			data = data.plusDays(indice);
		}else if(frequenciaDeLancamento.equals(FrequenciaDeLancamento.Semanal)){
			data = data.plusWeeks(indice);
		}else if(frequenciaDeLancamento.equals(FrequenciaDeLancamento.Quinzenal)){
			data = data.plusWeeks(indice*2);
		}else if(frequenciaDeLancamento.equals(FrequenciaDeLancamento.Mensal)){
			data = data.plusMonths(indice);
		}
		//S� para conferir
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println(frequenciaDeLancamento+" Data Inicial: "+sdf.format(dataInicial) +" Indice: "+indice+ " Data gerada: "+data);
		 
		    return Date.from(data.atStartOfDay().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());  
	}
	
	
	/**Fun��o que compara se uma data � maior  ou n�o que outra data
	 * @param dataInicial primeira data
	 * @param dataFinal segunda data
	 * @author Elves Gama
	 * @return true - Se a primeira data for maior */
	public static boolean comparaDataInicialDataFinal(Date dataInicial, Date dataFinal){
		if(dataInicial!=null && dataFinal!=null){
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			 String dtInicial = sdf.format(dataInicial);
			 String dtFinal = sdf.format(dataFinal);
			if(dtFinal.compareTo(dtInicial)<0){		
				return true;//A data final � MENOR que a data inicial
			}
		}
		return false;
	}
	
	/**Fun��o que converte LocalDate para Date
	 * @param localDate
	 * @return Date  */
	public static Date localDateParaDate(LocalDate localDate){
		if(localDate!=null){
			 return Date.from(localDate.atStartOfDay().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
		}
		return null;
	}
	
}


package br.com.pontek.util;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;

import br.com.pontek.enums.FiltroData;
import br.com.pontek.enums.FrequenciaDeLancamento;
import br.com.pontek.util.filtro.FiltroLancamento;

public class DataUtil {
	
	/**Gera uma Date baseado em uma data inicial e um indice
	 * @param dataInicial primeira data
	 * @param indice
	 * @param frequenciaDeLancamento
	 * @return Date*/
	public static Date geraDataVencimentoParcela(Date dataInicial, Integer indice, FrequenciaDeLancamento frequenciaDeLancamento){
		LocalDate data = dataInicial.toInstant().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDate();
		if(frequenciaDeLancamento.equals(FrequenciaDeLancamento.Diário)){
			data = data.plusDays(indice);
		}else if(frequenciaDeLancamento.equals(FrequenciaDeLancamento.Semanal)){
			data = data.plusWeeks(indice);
		}else if(frequenciaDeLancamento.equals(FrequenciaDeLancamento.Quinzenal)){
			data = data.plusWeeks(indice*2);
		}else if(frequenciaDeLancamento.equals(FrequenciaDeLancamento.Mensal)){
			data = data.plusMonths(indice);
		}
		//Só para conferir
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println(frequenciaDeLancamento+" Data Inicial: "+sdf.format(dataInicial) +" Indice: "+indice+ " Data gerada: "+data);
		 
		    return Date.from(data.atStartOfDay().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());  
	}
	
	
	/**Compara se uma data é maior ou não que outra data
	 * @param dataInicial primeira data
	 * @param dataFinal segunda data
	 * @return true - Se a primeira data for maior */
	public static boolean comparaDataInicialDataFinal(Date dataInicial, Date dataFinal){
		if(dataInicial!=null && dataFinal!=null){
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			 String dtInicial = sdf.format(dataInicial);
			 String dtFinal = sdf.format(dataFinal);
			if(dtFinal.compareTo(dtInicial)<0){		
				return true;//A data final é MENOR que a data inicial
			}
		}
		return false;
	}
	
	
	/**Converte LocalDate para Date com time-zone ajustado
	 * @param  LocalDate
	 * @return Date formatado com time-zone - America/Sao_Paulo*/
	public static Date localDateParaDate(LocalDate localDate){
		if(localDate!=null){
			 return Date.from(localDate.atStartOfDay().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
		}
		return null;
	}
	
	/*Função auxiliar para  a função "intervaloDeFiltroLancamento" e formatar as data de intervalo*/
	private static String localDateParaStringFormatado(LocalDate localDate){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date data=null;
		if(localDate!=null){
			 data = Date.from(localDate.atStartOfDay().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
			 return sdf.format(data);
		}
		return "";
	}
	
	/**Personalisa informação do relatório, gera mensagem sobre o intervalo de datas do relatório baseada no filtro
	 * @param  FiltroLancamento filtro de lançamento, onde possui um intervalo de datas
	 * @return String informação referente ao intervalo de datas e as datas*/
	public static String intervaloDeFiltroLancamento(FiltroLancamento filtro){
		String intervalo="";
		LocalDate dataHoje = LocalDate.now();
			if (filtro.getFitroData() != null) {
				if (filtro.getFitroData().equals(FiltroData.Hoje)) {
					intervalo="Lançamentos de hoje  "+localDateParaStringFormatado(dataHoje);
				} else if (filtro.getFitroData().equals(FiltroData.Ontem)) {
					intervalo="Lançamentos de ontem  "+localDateParaStringFormatado(dataHoje.plusDays(-1));
				}else if (filtro.getFitroData().equals(FiltroData.Amanhã)) {
					intervalo="Lançamentos de amanha  "+localDateParaStringFormatado(dataHoje.plusDays(+1));
				}else if (filtro.getFitroData().equals(FiltroData.Está_semana)) {
					LocalDate inicio = dataHoje.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
					LocalDate fim = dataHoje.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
					intervalo="Lançamentos dessa semana  "+localDateParaStringFormatado(inicio)+" a "+localDateParaStringFormatado(fim);
				}else if (filtro.getFitroData().equals(FiltroData.Próxima_semana)){
					LocalDate inicio = dataHoje.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
					LocalDate fim = inicio.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
					intervalo="Lançamentos da próxima semana  "+localDateParaStringFormatado(inicio)+" a "+localDateParaStringFormatado(fim);
				}else if (filtro.getFitroData().equals(FiltroData.Últimos_7_dias)) {
					intervalo="Lançamentos dos últimos 7 dias  "+localDateParaStringFormatado(dataHoje.plusDays(-7))+" a "+localDateParaStringFormatado(dataHoje);
				}else if (filtro.getFitroData().equals(FiltroData.Próximos_7_dias)) {
					intervalo="Lançamentos dos próximos 7 dias  "+localDateParaStringFormatado(dataHoje)+" a "+localDateParaStringFormatado(dataHoje.plusDays(+7));
				}else if (filtro.getFitroData().equals(FiltroData.Últimos_14_dias)) {
					intervalo="Lançamentos dos últimos 14 dias  "+localDateParaStringFormatado(dataHoje.plusDays(-14))+" a "+localDateParaStringFormatado(dataHoje);
				}else if (filtro.getFitroData().equals(FiltroData.Próximos_14_dias)) {
					intervalo="Lançamentos dos próximos 14 dias  "+localDateParaStringFormatado(dataHoje)+" a "+localDateParaStringFormatado(dataHoje.plusDays(+14));
				}else if (filtro.getFitroData().equals(FiltroData.Últimos_30_dias)) {
					intervalo="Lançamentos dos últimos 30 dias  "+localDateParaStringFormatado(dataHoje.plusDays(-30))+" a "+localDateParaStringFormatado(dataHoje);
				}else if (filtro.getFitroData().equals(FiltroData.Próximos_30_dias)) {
					intervalo="Lançamentos dos próximos 30 dias  "+localDateParaStringFormatado(dataHoje)+" a "+localDateParaStringFormatado(dataHoje.plusDays(+30));
				}else if (filtro.getFitroData().equals(FiltroData.Esse_mês)) {
					 LocalDate primeiroDiaMes = dataHoje.with(TemporalAdjusters.firstDayOfMonth());
					 LocalDate ultimoDiaMes = dataHoje.with(TemporalAdjusters.lastDayOfMonth());
					intervalo="Lançamentos desse mês  "+localDateParaStringFormatado(primeiroDiaMes)+" a "+localDateParaStringFormatado(ultimoDiaMes);
				}else if (filtro.getFitroData().equals(FiltroData.Mês_passado)) {
					 LocalDate dataMesPassado = dataHoje.plusMonths(-1);
					 LocalDate primeiroDiaMes = dataMesPassado.with(TemporalAdjusters.firstDayOfMonth());
					 LocalDate ultimoDiaMes = dataMesPassado.with(TemporalAdjusters.lastDayOfMonth());
					intervalo="Lançamentos do mês passado  "+localDateParaStringFormatado(primeiroDiaMes)+" a "+localDateParaStringFormatado(ultimoDiaMes);
				}else if (filtro.getFitroData().equals(FiltroData.Próximo_mês)) {
					LocalDate dataProximoMes = dataHoje.plusMonths(+1);
					LocalDate primeiroDiaMes = dataProximoMes.with(TemporalAdjusters.firstDayOfMonth());
					LocalDate ultimoDiaMes = dataProximoMes.with(TemporalAdjusters.lastDayOfMonth());
					intervalo="Lançamentos do próximo mês  "+localDateParaStringFormatado(primeiroDiaMes)+" a "+localDateParaStringFormatado(ultimoDiaMes);
				}else if (filtro.getFitroData().equals(FiltroData.Passado_mais_30_dias)) {
					intervalo="Lançamentos anteriores até a data  "+localDateParaStringFormatado(dataHoje.plusDays(+30));
				}else if (filtro.getFitroData().equals(FiltroData.Esse_ano)) {
					LocalDate primeiroDiaAno = dataHoje.with(TemporalAdjusters.firstDayOfYear());
					LocalDate ultimoDiaAno = dataHoje.with(TemporalAdjusters.lastDayOfYear());
					intervalo="Lançamentos desse ano  "+localDateParaStringFormatado(primeiroDiaAno)+" a "+localDateParaStringFormatado(ultimoDiaAno);
				}else if (filtro.getFitroData().equals(FiltroData.Últimos_12_meses)) {
					intervalo="Lançamentos dos últimos 12 meses  "+localDateParaStringFormatado(dataHoje.plusYears(-1))+" a "+localDateParaStringFormatado(dataHoje);
				}else if (filtro.getFitroData().equals(FiltroData.Sem_filtro_de_data)) {
					intervalo="Sem filtro de data";
				}
			}
		return intervalo;
	}
	
	/**Formata Date (dd-MM-yy)
	 * @param  data a ser convertido
	 * @return String dd-MM-yy*/
	public static String ddMMyy(Date data){
		if(data!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
			return sdf.format(data);
		}
		return null;
	}
	
	/**Formata Date (dd-MM-yyyy)
	 * @param  data a ser convertido
	 * @return String dd-MM-yyyy*/
	public static String ddMMyyyy(Date data){
		if(data!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			return sdf.format(data);
		}
		return null;
	}
	
	/**Formata Date (dd-MM-yyyy, HH:mm:ss)
	 * @param  data a ser convertido
	 * @return String dd-MM-yyyy, HH:mm:ss*/
	public static String ddMMyyyy_HHmmss(Date data){
		if(data!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
			return sdf.format(data);
		}
		return null;
	}
	
	/**Formata Date
	 * @param  data a ser convertido
	 * @return String ex: 22 de agosto de 2016*/
	public static String extenso(Date data){
		LocalDate lDate =data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String mes_estenso = lDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt"));
		Integer dia= lDate.getDayOfMonth();
		Integer ano= lDate.getYear();
		return dia.toString()+" de "+mes_estenso+" de "+ano.toString();
	}
	
	
}//fim
	



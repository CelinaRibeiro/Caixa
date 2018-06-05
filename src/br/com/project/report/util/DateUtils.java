/**
 * 
 */
package br.com.project.report.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Celina
 *
 */
public class DateUtils implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static String getDataAtualReportName() {
		DateFormat df = new SimpleDateFormat("ddMMyyyy");
		return df.format(Calendar.getInstance().getTime());
	}
	
	//metodo formato DB
	public static String formatDateSql(Date data) {
		StringBuffer retorno = new StringBuffer();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		retorno.append("'");
		retorno.append(df.format(data));
		retorno.append("'");
		return retorno.toString();
	}
	
	//metodo formato DB - retona sem aspas simples
		public static String formatDateSqlSimple(Date data) {
			StringBuffer retorno = new StringBuffer();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			retorno.append(df.format(data));
			return retorno.toString();
		}


}

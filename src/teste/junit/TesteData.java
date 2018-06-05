/**
 * 
 */
package teste.junit;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import br.com.project.report.util.DateUtils;

/**
 * @author Celina
 *
 */
public class TesteData {

	@Test
	public void testData() {
		//System.out.println(DateUtils.getDataAtualReportName());
		
		try {
		assertEquals("05062018", DateUtils.getDataAtualReportName());
		
		assertEquals("'2018-06-05'", DateUtils.formatDateSql(Calendar.getInstance().getTime()));
		
		assertEquals("2018-06-05", DateUtils.formatDateSqlSimple(Calendar.getInstance().getTime()));
		
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}

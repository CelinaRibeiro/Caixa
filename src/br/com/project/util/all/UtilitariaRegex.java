/**
 * 
 */
package br.com.project.util.all;

/**
 * @author Celina
 *
 */
public class UtilitariaRegex {
	
	public String retiraAcentos(String string) {
		String aux = new String (string);
		aux = aux.replaceAll("[êèéëÊÈÉË]", "e");
		aux = aux.replaceAll("[ûúùüÛÙÚÜ]", "u");
		aux = aux.replaceAll("[îíìïÎÍÌÏ]", "i");
		aux = aux.replaceAll("[âãàáäÂÀÁÄÃ]", "a");
		aux = aux.replaceAll("[ôõòóöÔÕÒÓÖ]", "o");
		return aux;
	}

}

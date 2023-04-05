/* 
* nombre del archivo: XmlFileFilter.java
*  
* @Fecha Generaci�n: 	D�a/Mes/ A�o 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripci�n:		Permite filtrar los archivos con extenci�n xml en
*					un directorio.
*@Fecha Actualizaci�n:	25/01/2005
*@Autor Actualizaci�n: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) a�o Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package frames;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/*
 * Esta clase extiende de FileFilter y permite filtrar los 
 * archivos con extenci�n xml en un directorio.
 *
 * @Author      Mauricio Rodriguez
 * @Versi�n     1.0, 25/01/2005
 */

public class XmlFileFilter extends FileFilter{
	
	/**
	 * Este metodo recibe un File y si es de extencion xml o es un folder 
	 * retorna true en otro caso retorna false.
	 * 
	 * @param	File
	 * @return	boolean 
	 */
	public boolean accept(File file) {
		String ext = null;
		String s = file.getName();
		int i = s.lastIndexOf('.');
		
		if (i > 0 &&  i < s.length() - 1) ext = s.substring(i+1).toLowerCase();

		if (ext != null) 
			if (ext.equals("xml") || ext.equals("XML")) return true;
			else return false;

		return true;
	}

	/**
	 * Este metodo retorna la descripcion del filtro.
	 * 
	 * @return	String 
	 */
	public String getDescription() {
		return "Archivo XML";
	}
}

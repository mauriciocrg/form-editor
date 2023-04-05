/* 
* nombre del archivo: XmlFileFilter.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Permite filtrar los archivos con extención xml en
*					un directorio.
*@Fecha Actualización:	25/01/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package frames;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/*
 * Esta clase extiende de FileFilter y permite filtrar los 
 * archivos con extención xml en un directorio.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
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

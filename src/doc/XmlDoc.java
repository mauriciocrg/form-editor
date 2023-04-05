/* 
* nombre del archivo: XmlDoc.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Arma la estructura principal de un documento XML que 
*					va a alojar la información de un formulario XForms.
*@Fecha Actualización:	25/01/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package doc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * Esta clase arma la estructura principal de un documento XML que 
 * va a alojar la información de un formulario XForms.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
 */

public class XmlDoc {
	private static Document document;		//contiene el documento completo.
	private Element xml;					//contiene el cuerpo del archivo xml.
	private String name = null;				//es nombre del formulario y del documento.
	
	/**
	 * Este es el constructor de la clase, al cual se le pasan como parametros
	 * el nombre del documento y el Document donde se construye. Este metodo crea
	 * la estructura principal del documento XML, crea el elemento form y a este 
	 * le crea el atributo name.
	 * 
	 * @param		name		es el nombre o titulo del cocumento XML. 
	 * @param  		document 	es el Document que contiene los nodos del XML.
	 */
	public XmlDoc(String name, Document document) {
		this.name = name;
		if(document != null){
			XmlDoc.document = document;
			xml = (Element) document.createElement("form");
			
			xml.getAttributes().setNamedItem(document.createAttribute("name"));
			xml.getAttributes().getNamedItem("name").setNodeValue(name);
		}
	}
	
	/**
	 * Este metodo retorna el contenido del elemento xml, osea el cuerpo 
	 * del formulario.
	 * 
	 * @return 		xml		es el elemento xml que contiene el cuerpo del documento.
	 */
	public Element getXml(){
		return xml;
	}
	
	/**
	 * Este metodo retorna el nombre del formulario.
	 * 
	 * @return 		name	es el nombre del formulario.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Este metodo permite agregar elelentos control al formulario.
	 * 
	 * @param 		control		es un elemento control.
	 */
	public void addComponent(Element Control) {
		xml.appendChild(Control);
	}
	
	/**
	 * Este metodo permite agregar elelentos control al formulario.
	 * 
	 * @param 		control		es un elemento control.
	 */
	public void delComponent(Node tree, String name) {
		
		NodeList list = tree.getChildNodes();
		
		for(int i=0; i < list.getLength(); i++){
			if(list.item(i).getAttributes().getNamedItem("name").getNodeValue().equals(name)){
				tree.removeChild(list.item(i));
			}else
			if(list.item(i).hasChildNodes())
				delComponent(list.item(i), name);
		}
	}
}

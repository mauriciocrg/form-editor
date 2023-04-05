/* 
* nombre del archivo: XhtmlDoc.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Arma la estructura principal de un documento XHTML que 
*					va a alojar controles XForms.
*@Fecha Actualización:	25/01/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package doc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*
 * Esta clase arma la estructura principal de un documento XHTML que 
 * va a alojar controles XForms.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
 */

public class XhtmlDoc {
	
	private static Document document;		//contiene el documento completo.
	private Element html;					//contiene el cuerpo del tag html.
	private Element head;					//contiene el cuerpo tag head.
	private Element title;					//contiene el tag title.
	private Element body;					//contiene el tag body.
	
	/**
	 * Este es el constructor de la clase, al cual se le pasan como parametros
	 * el nombre del documento y el Document donde se construye. Este metodo crea
	 * la estructura principal del documento XHTML.
	 * 
	 * @param		name		es el nombre o titulo del cocumento XHTML. 
	 * @param  		document 	es el Document que contiene los nodos del XHTML.
	 */
	public XhtmlDoc(String name, Document document) {
		XhtmlDoc.document = document;
	
		title = (Element) document.createElement("title");
		title.appendChild(document.createTextNode("Nuevo Formulario (XHTML): "+name));
		
		body = (Element) document.createElement("body");
		
		head = (Element) document.createElement("head");
		head.appendChild(title);
		
		html = (Element) document.createElement("html");
		
		html.getAttributes().setNamedItem(document.createAttribute("xmlns:xforms"));
		html.getAttributes().getNamedItem("xmlns:xforms").setNodeValue("http://www.w3.org/2002/xforms");
		
		html.getAttributes().setNamedItem(document.createAttribute("xmlns:xxforms"));
		html.getAttributes().getNamedItem("xmlns:xxforms").setNodeValue("http://www.w3.org/2002/xforms");
		
		html.getAttributes().setNamedItem(document.createAttribute("xmlns:xi"));
		html.getAttributes().getNamedItem("xmlns:xi").setNodeValue("http://www.w3.org/2003/XInclude");
		
		html.getAttributes().setNamedItem(document.createAttribute("xmlns:f"));
		html.getAttributes().getNamedItem("xmlns:f").setNodeValue("http://orbeon.org/oxf/xml/formatting");
		
		html.getAttributes().setNamedItem(document.createAttribute("xmlns:claim"));
		html.getAttributes().getNamedItem("xmlns:claim").setNodeValue("http://orbeon.org/oxf/examples/bizdoc/claim");
		
		html.getAttributes().setNamedItem(document.createAttribute("xmlns:xhtml"));
		html.getAttributes().getNamedItem("xmlns:xhtml").setNodeValue("http://www.w3.org/1999/xhtml");
		
		html.getAttributes().setNamedItem(document.createAttribute("xmlns"));
		html.getAttributes().getNamedItem("xmlns").setNodeValue("http://www.w3.org/1999/xhtml");
		
		html.appendChild(head);
		html.appendChild(body);
	}
	
	/**
	 * Este metodo retorna el tag html del documento y su contenido. 
	 * 
	 * @return		html 		retorna el tag html y su contenido.
	 */
	public Element getXhtmlDoc() {
		return html;
	}
	
	/**
	 * Este metodo retorna el tag body del documento y su contenido. 
	 * 
	 * @return		body 		retorna el tag body y su contenido.
	 */
	public Element getBodyDoc() {
		return body;
	}
}

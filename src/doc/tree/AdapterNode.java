/* 
* nombre del archivo: AdapterNode.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Administra los distintos tipos de nodo para el DOM.
*@Fecha Actualización:	25/01/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package doc.tree;

import org.w3c.dom.Node;

/*
 * Esta clase permite administrar los distintos tipos de nodo para el DOM.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
 */

public class AdapterNode { 
	private boolean compress = false;			//indica que el nombre del nodo tenga o no espacios en blanco.
	private org.w3c.dom.Node domNode;			//nodo.
	static final String[] typeName = {			//array de nombres de los nodos.
		"none",
		"Element",
		"Attr",
		"Text",
		"CDATA",
		"EntityRef",
		"Entity",
		"ProcInstr",
		"Comment",
		"Document",
		"DocType",
		"DocFragment",
		"Notation",
	};
	static final int ELEMENT_TYPE =   Node.ELEMENT_NODE;				//constante igual al valor de Node.ELEMENT_NODE
	static final int ATTR_TYPE =      Node.ATTRIBUTE_NODE;				//constante igual al valor de Node.ATTRIBUTE_NODE
	static final int TEXT_TYPE =      Node.TEXT_NODE;					//constante igual al valor de Node.TEXT_NODE
	static final int CDATA_TYPE =     Node.CDATA_SECTION_NODE;			//constante igual al valor de Node.CDATA_SECTION_NODE
	static final int ENTITYREF_TYPE = Node.ENTITY_REFERENCE_NODE;		//constante igual al valor de Node.ENTITY_REFERENCE_NODE
	static final int ENTITY_TYPE =    Node.ENTITY_NODE;					//constante igual al valor de Node.ENTITY_NODE
	static final int PROCINSTR_TYPE = Node.PROCESSING_INSTRUCTION_NODE;	//constante igual al valor de Node.PROCESSING_INSTRUCTION_NODE
	static final int COMMENT_TYPE =   Node.COMMENT_NODE;				//constante igual al valor de Node.COMMENT_NODE
	static final int DOCUMENT_TYPE =  Node.DOCUMENT_NODE;				//constante igual al valor de Node.DOCUMENT_NODE
	static final int DOCTYPE_TYPE =   Node.DOCUMENT_TYPE_NODE;			//constante igual al valor de Node.DOCUMENT_TYPE_NODE
	static final int DOCFRAG_TYPE =   Node.DOCUMENT_FRAGMENT_NODE;		//constante igual al valor de Node.DOCUMENT_FRAGMENT_NODE
	static final int NOTATION_TYPE =  Node.NOTATION_NODE;    			//constante igual al valor de Node.NOTATION_NODE
	
	static String[] treeElementNames = {		//La lista de elementos para mostrar en el árbol
	    "slideshow",
	    "slide",
	    "title",         
	    "slide-title",   
	    "item",
	};
	
	/**
	 * Este metodo retorna true si encuentra un nombre de elmento que 
	 * coincida en el treeElementNames.
	 *  
	 * @return		boolean
	 * 
	 * @param		String		es le nombre del elemento que va a ser 
	 * 							buscado en el array treeElementNames.
	 */
	boolean treeElement(String elementName) {
		for (int i=0; i<treeElementNames.length; i++) 
		{
			if ( elementName.equals(treeElementNames[i]) ) return true;
		}
		return false;
	}

	/**
	 * Este metodo es el constuctor de la clase. Setea el domNode miembro con 
	 * el parametro node.
	 *  
	 * @param		node
	 */
	public AdapterNode(org.w3c.dom.Node node) {
		this.domNode = node;
	}

	/**
	 * Este metodo retorna un string para identificar este nodo en el árbol.
	 * 
	 * @return		String		es el nombre que identifica al nodo en el 
	 * 							tree.
	 */
	public String toString() {
		String s = typeName[domNode.getNodeType()];
		String nodeName = domNode.getNodeName();
		
		if (! nodeName.startsWith("#")) {
			s += ": <" + nodeName +">";
		}
		
		if (compress) {
			String t = content().trim();
			int x = t.indexOf("\n");
			
			if (x >= 0) t = t.substring(0, x);
			
			s += " " + t;
			return s;
		}
		if (domNode.getNodeValue() != null) {
			if (s.startsWith("ProcInstr")) 
				s += ", "; 
			else 
				s += ": ";
			String t = domNode.getNodeValue().trim();
			int x = t.indexOf("\n");
			if (x >= 0) t = t.substring(0, x);
			s += t;
		}
	    return s;
	}
	
	/**
	 * Este metodo retorna un string con el valor del atributo name del nodo.
	 * 
	 * @return		String		es el valor del atributo name del nodo.
	 */	
	public String getName() {
		String s = typeName[domNode.getNodeType()];
		if(domNode.hasAttributes())
			return domNode.getAttributes().getNamedItem("name").getNodeValue();
		else
			return "";
	}

	/**
	 * Este metodo retorna un string que identifica el tipo de nodo que es.
	 * 
	 * @return		String		indica que tipo de nodo es.
	 */
	public String content()	{
		String s = "";
		org.w3c.dom.NodeList nodeList = domNode.getChildNodes();
		
		for (int i=0; i<nodeList.getLength(); i++) {
			org.w3c.dom.Node node = nodeList.item(i);
			int type = node.getNodeType();
			AdapterNode adpNode = new AdapterNode(node); 
		
			if (type == ELEMENT_TYPE) {
				// Salta los subelementos que se encuentran en el arbol.   
				if ( treeElement(node.getNodeName()) ) continue;

			    // EXTRA-CREDIT HOMEWORK:
			    //   un caso especial el elemento SLIDE para usar el texto del TITLE 
			    //   e ingorar el elemento TITLE para construir el árbol.
			
			    // EXTRA-CREDIT
			    //   Convierte elementos ITEM a una lista html usando 
			    //   tags <ul>, <li>, </ul> 

				s += "<" + node.getNodeName() + ">";
				s += adpNode.content();
				s += "</" + node.getNodeName() + ">";
			} else if (type == TEXT_TYPE) {
				s += node.getNodeValue();
			} else if (type == ENTITYREF_TYPE) {
				// El contenido esta en el nodo TEXT debajo de el
				s += adpNode.content();
			} else if (type == CDATA_TYPE) {
			    // el "value" tiene texto, igual que un nodo de texto.
			    //   mientras que EntityRef lo tiene en un nodo de texto debajo.
			    //   (porque EntityRef puede tener multiples subelements)
				
				StringBuffer sb = new StringBuffer( node.getNodeValue() );
			    for (int j=0; j<sb.length(); j++) {
			    	if (sb.charAt(j) == '<') {
			    		sb.setCharAt(j, '&');
			    		sb.insert(j+1, "lt;");
			    		j += 3;
			    	} else if (sb.charAt(j) == '&') {
			    		sb.setCharAt(j, '&');
			    		sb.insert(j+1, "amp;");
			    		j += 4;
			    	}
			    }
			    s += "<pre>" + sb + "\n</pre>";
			}
			
		   // Ignora los siguietnes nodos:
		   //   ATTR_TYPE      -- no en el árbol DOM
		   //   ENTITY_TYPE    -- no aparece en DOM
		   //   PROCINSTR_TYPE -- ningun dato
		   //   COMMENT_TYPE   -- ningun dato
		   //   DOCUMENT_TYPE  -- Solo la ruta del nodo. Ningún dato para mostrar.
		   //   DOCTYPE_TYPE   -- solo aparece sobre la ruta.
		   //   DOCFRAG_TYPE   -- equivalente a los documentos 
		   //   NOTATION_TYPE  -- nada, pero guarda datos binarios 
		}
		return s;
	}

	/**
	 * Este metodo dado un nodo, retorna el indice de éste.
	 * 
	 * @return		int		en caso de encontrar el nodo, es el 
	 * 						indice del nodo, en caso contrario es
	 * 						-1.
	 * 
	 * @param		AdapterNode		es el nodo hijo.
	 */
	public int index(AdapterNode child) {
		int count = childCount();
		for (int i=0; i<count; i++) {
			AdapterNode n = this.child(i);
			if (child.domNode == n.domNode) return i;
		}
		return -1; // si no lo encuentra retorna -1.
	}

	/**
	 * Este metodo dado un entero, retorna el nodo hijo que tenga ese indice.
	 * 
	 * @return		AdapterNode		en caso de no existir un nodo hijo con 
	 * 								indice searchIndex, se retorna el AdapterNode,
	 *	 							en caso contrario es retornado el ultimo
	 *								AdapterNode existente.
	 * 
	 * @param		int				es el valor del indice (searchIndex).
	 */
	public AdapterNode child(int searchIndex) {
		//Nota: el indice de JTree esta basado en cero. 
		org.w3c.dom.Node node =	domNode.getChildNodes().item(searchIndex);
		
		if (compress) {
			// retorna el nodo que este en la posicion searchIndex
			// y que sea del tipo ELEMENT_TYPE.
			int elementNodeIndex = 0;
			for (int i=0; i<domNode.getChildNodes().getLength(); i++) {
				node = domNode.getChildNodes().item(i);
				if (node.getNodeType() == ELEMENT_TYPE 
					&& treeElement( node.getNodeName() )
					&& elementNodeIndex++ == searchIndex) {
					break; 
				}
			}
		}
		return new AdapterNode(node); 
	}

	/**
	 * Este metodo retorna la cantidad de nodos hijos que existen.
	 * 
	 * @return		int		es la cantidad de nodos hijos que existen.
	 */
	public int childCount() {
		if (!compress) {
			return domNode.getChildNodes().getLength();  
		} 
		
		int count = 0;
		for (int i=0; i<domNode.getChildNodes().getLength(); i++) {
			org.w3c.dom.Node node = domNode.getChildNodes().item(i); 
			if (node.getNodeType() == ELEMENT_TYPE && treeElement( node.getNodeName() )) {
		         // Nota: 
		         //   Debe chequear que el tipo se apropiado. 
				++count;
			}
		}
		return count;
	}
}

/* 
* nombre del archivo: AdapterNode.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Administra el documento DOM.
*@Fecha Actualización:	25/01/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package doc.tree;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

import org.w3c.dom.Document;

/*
 * Esta clase implementa TreeModel y permite administrar el documento DOM.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
 */

public class DomToTreeModelAdapter implements javax.swing.tree.TreeModel {
	
	private static Document document;		//documento que contiene la configuracion del formulario.

	/**
	 * Este metodo es el constructor de la clase. Setea la variable miembro 
	 * document con el document que contiene la configuración del formulario.
	 * 
	 * @param		Document		contiene la configuración del formulario.
	 */
	public DomToTreeModelAdapter(Document document) {
		DomToTreeModelAdapter.document = document;
	}
	
	/**
	 * Este metodo retorna un AdaptarNode construido con el document.
	 * 
	 * @return 		Object		es un AdapterNode.
	 */
	public Object  getRoot() {
		return new AdapterNode(document);
	}
	
	/**
	 * Este metodo retorna true si el nodo que es pasado por referencia
	 * tiene al menos un nodo hijo, en otro caso retorna false.
	 * 
	 * @return 		boolean		es verdadero si existe al menos un nodo
	 * 							hijo, en otro caso es falso.
	 */
	public boolean isLeaf(Object aNode) {
		// Determina que nodo se muestra a la izquierda.
		// Retorna true si el nodo no tiene hijos.
		AdapterNode node = (AdapterNode) aNode;
		if (node.childCount() > 0) return false;
	
		return true;
	}
	
	/**
	 * Este metodo dado un nodo, retorna la cantidad de nodos hijo
	 * que tiene.
	 * 
	 * @return 		int		es la cantidad de nodos hijo que tiene.
	 */
	public int getChildCount(Object parent) {
		AdapterNode node = (AdapterNode) parent;
		return node.childCount();
	}
	
	/**
	 * Este metodo dado un nodo y un entero, retorna el nodo hijo que 
	 * tiene indice index.
	 * 
	 * @return 		Object		es el nodo hijo de param que corresponde 
	 * 							al indice index.
	 */
	public Object getChild(Object parent, int index) {
		AdapterNode node = (AdapterNode) parent;
		return node.child(index);
	}
	
	/**
	 * Este metodo dado un nodo y un entero, retorna el nodo hijo que 
	 * tiene indice index.
	 * 
	 * @return 		Object		es el nodo hijo de param que corresponde 
	 * 							al indice index.
	 */
	public int getIndexOfChild(Object parent, Object child) {
		AdapterNode node = (AdapterNode) parent;
		return node.index((AdapterNode) child);
	}
	
	public void valueForPathChanged(TreePath path, Object newValue) {
	}

	/*
	 * Usar este metodo para agregar o quitar event listeners.
	 */
	private Vector listenerList = new Vector();

	/**
	 * Este metodo captura el evento cuando se agrega un nuevo nodo
	 * al árbol.
	 * 
	 * @param 	TreeModelListener
	 */
	public void addTreeModelListener(TreeModelListener listener) {
		if ( listener != null && ! listenerList.contains( listener ) ) {
			listenerList.addElement( listener );
		}
	}

	/**
	 * Este metodo captura el evento cuando se remueve un nodo
	 * del árbol.
	 * 
	 * @param 	TreeModelListener
	 */
	public void removeTreeModelListener(TreeModelListener listener) {
		if ( listener != null ) {
			listenerList.removeElement( listener );
		}
	}

	public void fireTreeNodesChanged( TreeModelEvent e ) {
		Enumeration listeners = listenerList.elements();
		while ( listeners.hasMoreElements() ) {
			TreeModelListener listener = (TreeModelListener) listeners.nextElement();
			listener.treeNodesChanged( e );
		}
	}
	
	public void fireTreeNodesInserted( TreeModelEvent e ) {
		Enumeration listeners = listenerList.elements();
		while ( listeners.hasMoreElements() ) {
			TreeModelListener listener = (TreeModelListener) listeners.nextElement();
			listener.treeNodesInserted( e );
		}
	}
	
	public void fireTreeNodesRemoved( TreeModelEvent e ) {
		Enumeration listeners = listenerList.elements();
		while ( listeners.hasMoreElements() ) {
			TreeModelListener listener = (TreeModelListener) listeners.nextElement();
			listener.treeNodesRemoved( e );
		}
	}
	
	public void fireTreeStructureChanged( TreeModelEvent e ) {
		Enumeration listeners = listenerList.elements();
		while ( listeners.hasMoreElements() ) {
			TreeModelListener listener = (TreeModelListener) listeners.nextElement();
			listener.treeStructureChanged( e );
		}
	}
}


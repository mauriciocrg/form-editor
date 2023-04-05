/* 
* nombre del archivo: Properties.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Muestra las propiedades standard de un control  
*					XForms en una JTable.
*@Fecha Actualización:	25/01/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package control.controls.properties;

import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JTable;

import control.JControl;
import frames.internalFrame.JInternalEditFrame;

/*
 * Esta clase muestra las propiedades standard de un control  
 * XForms en una JTable.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
 */

public class Properties {
	
	private Vector name = new Vector();				//vector que representa a la fila name en la jTable
	private Vector hint = new Vector();				//vector que representa a la fila hint en la jTable
	private Vector bind = new Vector();				//vector que representa a la fila bind en la jTable
	private Vector ref = new Vector();				//vector que representa a la fila ref en la jTable
	private Vector model = new Vector();			//vector que representa a la fila model en la jTable
	private Vector height = new Vector();			//vector que representa a la fila height en la jTable
	private Vector width = new Vector();			//vector que representa a la fila width en la jTable
	private Vector value = new Vector();			//vector que representa a la fila value en la jTable
	private Vector top = new Vector();				//vector que representa a la fila top en la jTable
	private Vector left = new Vector();				//vector que representa a la fila left en la jTable
	private Vector max = new Vector();				//vector que representa a la fila max en la jTable
	private Vector step = new Vector();				//vector que representa a la fila step en la jTable
	private Vector display = new Vector();			//vector que representa a la fila display en la jTable
	private Vector appearence = new Vector();		//vector que representa a la fila apparence en la jTable
	private Vector listItems = new Vector();		//vector que representa a la fila listItems en la jTable
	private Vector tabPosition = new Vector();		//vector que representa a la fila tabPosition en la jTable
	private Vector tickInterval = new Vector();		//vector que representa a la fila tickInterval en la jTable
	private Vector helpText = new Vector();			//vector que representa a la fila helpText en la jTable
	private Vector foreignAttributes = new Vector();//vector que representa a la fila foreignAttributes en la jTable
	private Vector eventScript = new Vector();		//vector que representa a la fila eventScript en la jTable
	
	private JInternalEditFrame editFrame = null;	//Es el internal frame que esta activo.
	
	private JTable jTable;							//el jTable que pertenece al FirsFrame
	private Vector table;							//el contenido de jTable
	
	/**
	 * Este es el constructor de la clase, al cual se le pasan como parametros
	 * un jTable del FisrtFrame, y el vector Table que tiene el contenido de la
	 * jTable.
	 * 
	 * @param		jTable		es el JTable que es mostrada en FirstFrame. 
	 * @param  		table 		es el Vector que tiene el contenido de jTable.
	 */
	public Properties(final JTable jTable, Vector table) {
		this.table = table;
		this.jTable = jTable;
	}
	
	/**
	 * Este metodo setea el JIternalEdirFrame local en el que se esta editando 
	 * el control al que pertenecen enstas Properties.
	 * 
	 * @param		editFrame		es el JIternalEdirFrame en el que se esta 
	 * 								editando el control al que pertenesen estas
	 * 								Properties. 
	 */
	public void setInternalFrame(JInternalEditFrame editFrame){
		this.editFrame = editFrame;
	}
	
	/**
	 * Este metodo setea el contenido de los vectores de las propiedades, con 
	 * las correspondientes propiedades del JControl. 
	 * 
	 * @param		control		es el control al que pertencen estas Properties.
	 */
	public void setProperties(JComponent control) {
		this.name.removeAllElements();
		this.name.add("Name:");
		this.name.add(((JControl) control).getName());
		this.helpText.removeAllElements();
		this.helpText.add("HelpText:");
		this.helpText.add(((JControl) control).getHelpText());
		this.hint.removeAllElements();
		this.hint.add("Hint:");
		this.hint.add(((JControl) control).getHint());
		this.bind.removeAllElements();
		this.bind.add("Bind:");
		this.bind.add(((JControl) control).getBind());
		this.ref.removeAllElements();
		this.ref.add("Ref:"); 
		this.ref.add(((JControl) control).getRef());
		this.model.removeAllElements();
		this.model.add("Model:"); 
		this.model.add(((JControl) control).getModel());
		this.height.removeAllElements();
		this.height.add("Height:");
		this.height.add(""+control.getHeight());
		this.width.removeAllElements();
		this.width.add("Width:");
		this.width.add(""+control.getWidth());
		this.top.removeAllElements();
		this.top.add("Top:");
		this.top.add(""+control.getY());
		this.left.removeAllElements();
		this.left.add("Left:"); 
		this.left.add(""+control.getX());
		this.tabPosition.removeAllElements();
		this.tabPosition.add("TabPosition:"); 
		this.tabPosition.add(((JControl) control).getTabPosition());
	}

	/**
	 * Este metodo remueve todos los elementos de table y lueo los
	 * agrega actualizados.
	 */
	public void update() {
		this.table.removeAllElements();
		this.jTable.addNotify();
		this.table.addElement(name);
		this.table.addElement(helpText);
		this.table.addElement(hint);
		this.table.addElement(bind);
		this.table.addElement(ref);
		this.table.addElement(model);
		this.table.addElement(height);
		this.table.addElement(width);
		this.table.addElement(top);
		this.table.addElement(left);
		this.table.addElement(tabPosition);
		this.jTable.addNotify();
		this.jTable.repaint();
		if(this.editFrame != null)
			this.editFrame.setSave(false);
	}
}

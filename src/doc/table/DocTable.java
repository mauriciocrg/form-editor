/* 
* nombre del archivo: DocTable.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Implementa un JToolBar que contiene el JTable en 
*					el cual	se despliegan las propiedades de los 
*					controles XForm.
*@Fecha Actualización:	25/01/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package doc.table;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Vector;

/*
 * Esta clase implementa un JToolBar que contiene el JTable en 
 * el cual se despliegan las propiedades de los 
 * controles XForm.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
 */

public class DocTable {

	private Vector table = null;					//Es el contenido del jTable.
	private Vector columnas = null;					//Contiene el nombre de las columnas del jTable.
	private JComponent jComponent = null;			//Contiene jComponent que se esta editando.
	
	private JToolBar jToolBar = null;  				//Es el JToolBar que contiene el JPanel del JTable.
	private JPanel jPanel = null;					//Es el JPanel que contiene al JTable.
	private JScrollPane jScrollPane = null;			//Es el JScrollPane que visualiza al JTable.
	private JTable jTable = null;					//Es el JTable que muestra las popiedades del JComponent seleccionado.
	
	private TxtFieldCellEditor cellEditor = null;   //Es el DefaultCellEditor que customiza la edición 
													//de las celdas de el JTable.
	
	/**
	 * Este metodo inicializa el jToolBar, si el jToolBar es igual a null
	 * lo crea y le asigna propiedades, como el tamaño y el nombre.	
	 * 	
	 * @return	 	JToolBar	retorna el jToolBar inicializado.
	 */    
	public JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setToolTipText("Tabla de Propiedades");
			jToolBar.setName("Propiedades");
			jToolBar.setSize(180, 250);
			jToolBar.setPreferredSize(new java.awt.Dimension(150,250));
			jToolBar.setOrientation(javax.swing.JToolBar.VERTICAL);
			jToolBar.add(getJPanel());
			jToolBar.addPropertyChangeListener(new java.beans.PropertyChangeListener() { 
				public void propertyChange(java.beans.PropertyChangeEvent e) { 
					if ((e.getPropertyName().equals("orientation"))) { 
						jToolBar.setOrientation(javax.swing.JToolBar.VERTICAL);
					} 
				}
			});
		}
		return jToolBar;
	}
	
	/**
	 * Este metodo inicializa el jPanel, este jPanel contiene un jScrollPane,
	 * si el jPanel es igual a null, entonces lo crea y le signa propiedades, 
	 * como el tamaño y e borderLayout.
	 * 	
	 * @return 		JPanel		retorna el jPanel inicializado.	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			BorderLayout borderLayout3 = new BorderLayout();
			jPanel = new JPanel();
			jPanel.setLayout(borderLayout3);
			borderLayout3.setHgap(2);
			borderLayout3.setVgap(2);
			jPanel.setSize(180, 250);
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
			jPanel.setPreferredSize(new java.awt.Dimension(150,250));
			jPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		}
		return jPanel;
	}
	
	/**
	 * Este metodo inicializa el jScrollPane, este jScrollPane contiene el jTable,
	 * si el jScrollPanel es igual a null, entonces lo crea.
	 * 	
	 * @return 		JScrollPane		retorna el jScrollPane inicializado.	
	 */    	
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}
	
	/**
	 * Este metodo inicializa el jTable, si el jTable es igual a null, 
	 * entonces lo crea con dos vectores: table y columnas. table contien
	 * el contenido de la tabla, y columnas, el nombre de las columnas.
	 * 	
	 * @return 		JTable		retorna el JTable inicializado.	
	 */
	public JTable getJTable() {
		if (jTable == null) {
			table = new Vector();
			columnas = new Vector();
			columnas.add("Propiedad");
			columnas.add("Valor");
			jTable = new JTable(table, columnas) {
				public boolean isCellEditable(int rowIndex, int colIndex) {
					if (colIndex == 0)	return false;
					else				return true;
				}
			};
			RowHeaderRenderer rowHeader = new RowHeaderRenderer();
			cellEditor = new TxtFieldCellEditor(new JCheckBox());
			this.jTable.getColumnModel().getColumn(0).setCellRenderer(rowHeader);
			this.jTable.getColumn("Valor").setCellEditor(cellEditor);
		}
		return jTable;
	}
	
	/**
	 * Este metodo retorna el vector table, que contiene los valores que 
	 * son mostrados en la tabla.
	 * 	
	 * @return 		Vector		retorna el Vector table.	
	 */
	public Vector getTable() {
		return table;
	} 
	
	/**
	 * Este metodo setea el jComponent en el cellEditor para que sus porpiedades 
	 * puedan ser modificadas mediante el jTable.
	 * 	
	 * @param 		JComponent		es el JComponent que ha sido seleccionado,
	 * 								cuyos valores podran ser mostrados y modifidados
	 * 								en el jTable.	
	 */
	public void setJComponent(JComponent jComponent) {
		this.jComponent = jComponent;
		this.cellEditor.setJControl(jComponent);
	}
	
	/**
	 * Este metodo setea el array de controles que componen el formulario que 
	 * esta siendo editado en el cellEditor, para luego poder controlar la 
	 * modificación de los nombres del cada control (que no sean iguales).
	 * 	
	 * @param 		Component[]		es el array de controles que componen el 
	 * 								formulario que esta siendo editado.
	 */
	public void setControles(Component[] Controles){
		this.cellEditor.setComponentes(Controles);
	}
}

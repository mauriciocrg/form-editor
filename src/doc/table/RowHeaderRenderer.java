/* 
* nombre del archivo: RowHeaderRenderer.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Hace que una celda de un JTable se vea como un JButton.
*@Fecha Actualización:	25/01/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package doc.table;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/*
 * Esta clase extiende de JLabel e implementa TableCellRenderer.
 * Hace que una celda de un JTable se vea como un JButton.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
 */

public class RowHeaderRenderer extends JLabel implements TableCellRenderer {
	
	/**
	 * Este metodo inicializa la apariencia del Component, que va a determinar
	 * la apariencia de algunas celdas de la tabla.	
	 * 	
	 * @return	 	Component	retorna this.
	 * 
	 * @param		JTable 		jTable
	 * @param		Object 		value
	 * @param		boolean 	isSelected si es seleccionada.
	 * @param		boolean 	hasFocus si es enfocada.
	 * @param		int 		rowIndex es el valor de la fila.	
	 * @param		int 		vColIndex es el valor de la columna.
	 */    
	public Component getTableCellRendererComponent(JTable jTable, Object value,
		boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
		
    	JTableHeader header = jTable.getTableHeader();
        setText(value.toString());

        setToolTipText((String)value);
        setOpaque(true);
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setHorizontalAlignment(CENTER);
		setForeground(header.getForeground());
		setBackground(header.getBackground());
		setFont(header.getFont());
        return this;
    }

    public void validate() {}
    public void revalidate() {}
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
}

/* 
* nombre del archivo: TxtFieldCellEditor.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Permite especificar la edición de una celda en un JTable.
*@Fecha Actualización:	25/01/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package doc.table;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;

import control.JControl;
import control.controls.InputControl;
import control.controls.LabelControl;
import control.controls.OutputControl;
import control.controls.RangeControl;
import control.controls.RepeatControl;
import control.controls.SecretControl;
import control.controls.Select1Control;
import control.controls.SelectControl;
import control.controls.SubmitControl;
import control.controls.TextAreaControl;
import control.controls.TriggerControl;
import control.controls.UploadControl;

/*
 * Esta clase extiende de DefaultCellEditor y permite especificar la 
 * edición de una celda en un JTable.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
 */

public class TxtFieldCellEditor extends DefaultCellEditor{

	private JTable jTable = null;							//Es el jTable que se muestra en FirstFrame.
	private JComponent jComponent = null;					//Es el jComponent que ha sido seleccionado.
	private Component[] componentes = null;					//Es el array de Componentes que componen el formulario.
	private JTextField txtEditor = new JTextField();		//Es el JTextField que se usa para especificar la edición de una celda.
	private String valor = "";								//Es el String que se usa para guardar el valor previo a la edicion de la celda.
	private int row = 0;									//Es el int que guarda el valor de la fila que esta siendo editada.
	
	/**
	 * Este metodo es el constructor de la clase, la cual inicializa 
	 * el FocusListener de txtEditor.
	 * 
	 * @param 	JCheckBox
	 */
	public TxtFieldCellEditor(JCheckBox checkBox) {
		super(checkBox);
		txtEditor.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent e) {    
				checkUpdate();
			}
		});
	}
	
	/**
	 * Este metodo retorna el txtEditor, setea el String valor que va a 
	 * guardar el valor previo a la edición, y setea el valor de la fila
	 * que es editada en row.
	 * 
	 * @return 	Component	es el txtEditor.
	 * 
	 * @param	JTable		es la jTable que se muestra en FirstFrame.
	 * @param	Object		value.
	 * @param	boolean		isSelected determina si la selda es seleccinada.
	 * @param	int			row da el numero de la fila.
	 * @param	int			column da el numero de la columna.
	 */
	public Component getTableCellEditorComponent(JTable jTable, Object value, boolean isSelected, int row, int column) {
		valor = (String) jTable.getValueAt(row, column);
		txtEditor.setText(valor);
		this.row = row;
		this.jTable = jTable;
		return txtEditor;
	}
	
	/**
	 * Este metodo retorna el valor de txtEditor, luego de verificar que 
	 * sea correcto.
	 * 
	 * @return 	Object	es el valor de txtEditor.
	 */
	public Object getCellEditorValue() {
		check();
		return txtEditor.getText();
	}
	
	/**
	 * Este metodo checkea que el valor que esta siendo editado sea 
	 * correcto, segun la fila, si es correcto lo setea en la celda 
	 * correspondiente de la jTable.
	 */
	public void check() {
		switch(row)
		{
			case 0:	if(!checkName()) txtEditor.setText(valor);
				break;
			case 6:	case 7:	case 8:	case 9: case 10:
					if(!checkInt()) txtEditor.setText(valor);
				break;
		}
	}
	
	/**
	 * Este metodo checkea que el valor que esta siendo editado sea 
	 * correcto, segun la fila, si es correcto hace un update en las
	 * propiedades del JComponent correspondiente, y lo setea en la celda 
	 * correspondiente de la jTable.
	 */
	public void checkUpdate() {
		switch(row)
		{
			case 0: if(checkName()) update();
					else txtEditor.setText(valor);
					break;
			case 6:	case 7:	case 8:	case 9: case 10: 
					if(checkInt()) update();
					else txtEditor.setText(valor);
					break;
			default: update();
					break;
		}
	}
	
	/**
	 * Este metodo en el caso que se este modificando el valor del nombre
	 * checkea que no sea igual al de otro control de el formulario que esta 
	 * siendo editado.
	 * 
	 * @return		boolean  	retorna true si no existe ningun nombre igual, 
	 * 							y false en el caso contrario. 
	 */
	private boolean checkName() {
		int size = 0;
		if(componentes != null) size = this.componentes.length;
		for(int i = 0; i < size; i++) {
			if(componentes[i] instanceof InputControl) {
				if(((InputControl) componentes[i]).getName().equals(txtEditor.getText()))
					return false;
			} 
			else
			if(componentes[i] instanceof LabelControl) { 
				if(((LabelControl) componentes[i]).getName().equals(txtEditor.getText()))
					return false;
			}
			else
			if(componentes[i] instanceof OutputControl) { 
				if(((OutputControl) componentes[i]).getName().equals(txtEditor.getText()))
					return false;
			}else
			if(componentes[i] instanceof RangeControl) { 
				if(((RangeControl) componentes[i]).getName().equals(txtEditor.getText()))
					return false;
			}else
			if(componentes[i] instanceof SecretControl) { 
				if(((SecretControl) componentes[i]).getName().equals(txtEditor.getText()))
					return false;
			}else
			if(componentes[i] instanceof SelectControl) { 
				if(((SelectControl) componentes[i]).getName().equals(txtEditor.getText()))
					return false;
			}else
			if(componentes[i] instanceof Select1Control) { 
				if(((Select1Control) componentes[i]).getName().equals(txtEditor.getText()))
					return false;
			}else
			if(componentes[i] instanceof SubmitControl) { 
				if(((SubmitControl) componentes[i]).getName().equals(txtEditor.getText()))
					return false;
			}else
			if(componentes[i] instanceof TextAreaControl) { 
				if(((TextAreaControl) componentes[i]).getName().equals(txtEditor.getText()))
					return false;
			}else
			if(componentes[i] instanceof TriggerControl) { 
				if(((TriggerControl) componentes[i]).getName().equals(txtEditor.getText())) 
					return false;
			}else
			if(componentes[i] instanceof UploadControl) { 
				if(((UploadControl) componentes[i]).getName().equals(txtEditor.getText()))
					return false;
			}
			if(componentes[i] instanceof RepeatControl) { 
				if(((RepeatControl) componentes[i]).getName().equals(txtEditor.getText()))
					return false;
				else
					if(!checkAndFixNameInRepeatControl(((JControl) componentes[i]).getComponents()))
						return false;
			}
		}
		return true;
	}
	
	/**
	 * Este metodo chequea que el nombre del control sea correcto, que no 
	 * este repitido en otro contol, iterando sobre el array de controles 
	 * pertenecientes a un control repeat.
	 * 
	 * @param	Component[]	Contiene los controles pertenecientes a 
	 *						un control repeat.
	 */
	private boolean checkAndFixNameInRepeatControl(Component[] controles){
		int i = 0;
		
		while(i < controles.length ) {
			if(controles[i] instanceof InputControl) {
				if(((InputControl) controles[i]).getName().equals(txtEditor.getText()))
					return false;
			} 
			else
			if(controles[i] instanceof LabelControl) { 
				if(((LabelControl) controles[i]).getName().equals(txtEditor.getText()))
					return false;
			}
			else
			if(controles[i] instanceof OutputControl) { 
				if(((OutputControl) controles[i]).getName().equals(txtEditor.getText()))
					return false;
			}else
			if(controles[i] instanceof RangeControl) { 
				if(((RangeControl) controles[i]).getName().equals(txtEditor.getText()))
					return false;
			}else
			if(controles[i] instanceof SecretControl) { 
				if(((SecretControl) controles[i]).getName().equals(txtEditor.getText()))
					return false;
			}else
			if(controles[i] instanceof SelectControl) { 
				if(((SelectControl) controles[i]).getName().equals(txtEditor.getText()))
					return false;
			}else
			if(controles[i] instanceof Select1Control) { 
				if(((Select1Control) controles[i]).getName().equals(txtEditor.getText()))
					return false;
			}else
			if(controles[i] instanceof SubmitControl) { 
				if(((SubmitControl) controles[i]).getName().equals(txtEditor.getText()))
					return false;
			}else
			if(controles[i] instanceof TextAreaControl) { 
				if(((TextAreaControl) controles[i]).getName().equals(txtEditor.getText()))
					return false;
			}else
			if(controles[i] instanceof TriggerControl) { 
				if(((TriggerControl) controles[i]).getName().equals(txtEditor.getText())) 
					return false;
			}else
			if(controles[i] instanceof UploadControl) { 
				if(((UploadControl) controles[i]).getName().equals(txtEditor.getText()))
					return false;
			}
			if(controles[i] instanceof RepeatControl) { 
				if(((RepeatControl) controles[i]).getName().equals(txtEditor.getText()))
					return false;
				else
					if(!checkAndFixNameInRepeatControl(((JControl) controles[i]).getComponents()))
						return false;
			}
			i++;
		}
		return true;
	}
	
	/**
	 * Este metodo en el caso que se este modificando el valor de una celda que 
	 * contiene un valor entero, verifica que el valor ingresado sigua siendo 
	 * entero.
	 * 
	 * @return		boolean  	retorna true si el valor editado es entero, 
	 * 							y false en el caso contrario. 
	 */
	private boolean checkInt() {
		try{
			Integer.parseInt(txtEditor.getText());
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Este metodo realiza un update de las propiedades de tipo texto, y el 
	 * tabPosition del JComponent que esta siendo editado.
	 */
	private void update() {
		if(!this.jTable.isEditing()){
			switch(row)
			{
				case 0: ((JControl) jComponent).setName((String) jTable.getValueAt(0, 1));
						break;
				case 1: ((JControl) jComponent).setHelpText((String) jTable.getValueAt(1, 1)); 
						break;																
				case 2: ((JControl) jComponent).setHint((String) jTable.getValueAt(2, 1));
						break;
				case 3: ((JControl) jComponent).setBind((String) jTable.getValueAt(3, 1));
						break;															
				case 4: ((JControl) jComponent).setRef((String) jTable.getValueAt(4, 1));
						break;
				case 5: ((JControl) jComponent).setModel((String) jTable.getValueAt(5, 1));
						break;															
				case 6:	case 7:	case 8:	case 9: updateBound();
						break;
				case 10: ((JControl) jComponent).setTabPosition((String) jTable.getValueAt(10, 1));
						break;
			}
			jComponent.repaint();
			((JControl) jComponent).updateControl();
		}
	}
	
	/**
	 * Este metodo realiza un update de las propiedades del JComponent 
	 * que determinan la posicion y la dimención de éste.
	 */
	private void updateBound() {
		if(jComponent instanceof TextAreaControl ||
		   jComponent instanceof SelectControl ||
		   jComponent instanceof RepeatControl) {
		   jComponent.setBounds(Integer.parseInt((String) jTable.getValueAt(9, 1)),
		   Integer.parseInt((String) jTable.getValueAt(8, 1)),
		   Integer.parseInt((String) jTable.getValueAt(7, 1)),
		   Integer.parseInt((String) jTable.getValueAt(6, 1)));
		}else{
		   jComponent.setBounds(Integer.parseInt((String) jTable.getValueAt(9, 1)),
		   Integer.parseInt((String) jTable.getValueAt(8, 1)),
		   Integer.parseInt((String) jTable.getValueAt(7, 1)),
		   jComponent.getHeight());
		   if(row == 6) txtEditor.setText(Integer.toString(jComponent.getHeight()));
		}
		
		if(jComponent instanceof InputControl) 
			((InputControl) jComponent).reDraw();
		else
		if(jComponent instanceof LabelControl) 
			((LabelControl) jComponent).reDraw();
		else
		if(jComponent instanceof OutputControl) 
			((OutputControl) jComponent).reDraw();
		else
		if(jComponent instanceof RangeControl) 
			((RangeControl) jComponent).reDraw();
		else
		if(jComponent instanceof SecretControl) 
			((SecretControl) jComponent).reDraw();
		else
		if(jComponent instanceof SelectControl) 
			((SelectControl) jComponent).reDraw();
		else
		if(jComponent instanceof Select1Control) 
			((Select1Control) jComponent).reDraw();
		else
		if(jComponent instanceof SubmitControl) 
			((SubmitControl) jComponent).reDraw();
		else
		if(jComponent instanceof TextAreaControl) 
			((TextAreaControl) jComponent).reDraw();
		else
		if(jComponent instanceof TriggerControl) 
			((TriggerControl) jComponent).reDraw();
		else
		if(jComponent instanceof UploadControl) 
			((UploadControl) jComponent).reDraw();
		else
		if(jComponent instanceof RepeatControl) 
			((RepeatControl) jComponent).reDraw();
	}
	
	/**
	 * Este metodo setea el JComponent que fue seleccionado en el entorno de
	 * diseño.
	 * 
	 * @param		JComponent		es el JComponent que se esta modificando.
	 */
	public void setJControl(JComponent jComponent) {
		this.jComponent = jComponent;
	}
	
	/**
	 * Este metodo setea el array de componentes que estan siendo editados en
	 * el formulario activo, permite verificar los nombres del JCompnent para 
	 * que no coincidan.
	 * 
	 * @param		Component[]		son los JComponent que pertenecen al formulario
	 * 								que esta siendo editado.
	 */
	public void setComponentes(Component[] componentes) {
		this.componentes = componentes;
	}
}

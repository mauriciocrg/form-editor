/* 
* nombre del archivo: InputControl.java
*  
* @Fecha Generación: 	10/04/2005 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Extiende de la clase JPopupMenu, e implementa el menu popup 
*					que permite cortar, copiar, pegar y eliminar un control del 
*					formulario que se este editando.  
*@Fecha Actualización:	11/04/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package control.controls.popupMenu;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import control.ClipBoard;
import control.JControl;
import control.controls.RepeatControl;

import frames.internalFrame.panel.JEditPanel;

/*
 * Esta clase hereda de la clase JPopupMenu, e implementa el menu popup 
 * que permite cortar, copiar, pegar y eliminar un control del 
 * formulario que se este editando.  
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 11/04/2005
 */
public class PopupMenu extends JPopupMenu {
	
	private JMenuItem cutItem;		// Es el item del menu, cortar.
	private JMenuItem copyItem;		// Es el item del menu, copiar.
	private JMenuItem pasteItem;	// Es el item del menu, pegar.
	private JMenuItem deleteItem;	// Es el item del menu, borrar.
	
	private JEditPanel jEditPanel;		/* Es el jEditPanel en el que se encuentra
									 	 * el control al que pertenece este popupMenu.
									 	 */
	private JControl  jControl;			/* Es el control al que pertenece
									 	 * este popupMenu.
									 	 */
	
	/**
	 * Este método es el constructor de la clase, se encarga de crear e
	 * inicializar los items del menu contextual.
	 * 
	 * @param	RepeatControl 
	 * @param	JControl  
	 */
	public PopupMenu(JEditPanel jEditPanel, JControl  jControl){
		this.jEditPanel = jEditPanel;
		this.jControl = jControl;
		this.add(getCutItem(), null);
		this.add(getCopyItem(), null);
		this.add(getPasteItem(), null);
		this.add(new JSeparator());
		this.add(getDeleteItem(), null);
	}
	
	/**
	 * Este metodo permite setear enabled o disable al 
	 * item Paste del menu contextual.
	 * 
	 * @param	boolean 	indica si el item paste es enambled o disabled.
	 */
	public void setPasteEnabled(boolean value){
		this.pasteItem.setEnabled(value);
	}
	
	/**
	 * Si el cutItem no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenuItem
	 */
	public JMenuItem getCutItem() {
		if (cutItem == null) {
			cutItem = new JMenuItem();
			cutItem.setText("Cortar");
			cutItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JComponent control = jEditPanel.getSelectedJControl(jControl.getName());
					if(control != null)
						ClipBoard.setJComponent(control);
					jEditPanel.delSelectedJControl(jControl.getName());
				}
			});
		}
		return cutItem;
	}
	
	/**
	 * Si el copyItem no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenuItem
	 */
	public JMenuItem getCopyItem() {
		if (copyItem == null) {
			copyItem = new JMenuItem();
			copyItem.setText("Copiar");
			copyItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JComponent control = jEditPanel.getSelectedJControl(jControl.getName());
					if(control != null)	ClipBoard.setJComponent(control);
				}
			});
		}
		return copyItem;
	}
	
	/**
	 * Si el pasteItem no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenuItem
	 */
	public JMenuItem getPasteItem() {
		if (pasteItem == null) {
			pasteItem = new JMenuItem();
			pasteItem.setText("Pegar");
			pasteItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JControl control = (JControl) ClipBoard.getCopyOfJComponent();
					control.setRepeatControl(((RepeatControl) jControl).getElement());
					((RepeatControl) jControl).getRepeatManager().addControl(control);
					ClipBoard.setJComponent(control);
				}
			});
		}
		return pasteItem;
	}
	
	/**
	 * Si el deleteItem no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenuItem
	 */
	public JMenuItem getDeleteItem() {
		if (deleteItem == null) {
			deleteItem = new JMenuItem();
			deleteItem.setText("Borrar");
			deleteItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jControl.getJEditPanel().delSelectedJControl(jControl.getName());
				}
			});
		}
		return deleteItem;
	}
}

/* 
 * nombre del archivo: PopupListener.java
 *  
 * @Fecha Generación: 	Día/Mes/ Año 
 * @Autor: 	       	Mauricio Rodriguez  
 *
 * @Descripción:	Esta clase ereda de la clase MauseAdapter, e implementa 
 * 					la captura de eventos del mause para desplegar el menu 
 * 					contextual.
 * 
 * @Fecha Actualización:	16/03/2005
 * @Autor Actualización: 	Mauricio Rodriguez  
 * --------------------------------------------------------------------------------------- 
 * Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
 * --------------------------------------------------------------------------------------- 
 */

package control.controls.popupMenu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

import control.ClipBoard;
import control.JControl;
import control.controls.RepeatControl;

/*
 * Esta clase ereda de la clase MauseAdapter, e implementa 
 * la captura de eventos del mause para desplegar el menu 
 * contextual.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 16/03/2005
 */
public class PopupListener extends MouseAdapter {
    PopupMenu jPopupMenu;		// Es el menu contextual
    private JControl jControl;	// Es el JControl al que pertenece el popupMenu 

    /**
     * Este metodo es el costructor de la clase, setea la 
     * variable miembro JPopupMenu, que es el menu contextual.
     * 
     * @param	JPopupMenu
     */
    public PopupListener(JPopupMenu jPopupMenu, JControl jControl) {
    	this.jPopupMenu = (PopupMenu) jPopupMenu;
    	this.jControl = jControl;
    }

    /**
     * Este metodo captura el evento del mause cuando este es
     * precionado y despliega el menú contextual.
     * 
     * @param	MouseEvent
     */
    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    /**
     * Este metodo captura el evento del mause cuando el boton de
     * este es soltado y despliega el menú contextual.
     * 
     * @param	MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    /**
     * Este metodo despliega el menu contextual en el lugar donde 
     * fue del hecho el evento.
     * 
     * @param	MouseEvent
     */
    private void maybeShowPopup(MouseEvent e) {
    	PopupMenu p;
        if (e.isPopupTrigger()) {
        	if(ClipBoard.containElement() &&
        	  (this.jControl instanceof RepeatControl))
        		((PopupMenu) jPopupMenu).setPasteEnabled(true);		
        	else
        		((PopupMenu) jPopupMenu).setPasteEnabled(false);
        	
        	jPopupMenu.show(e.getComponent(),
        					e.getX(), e.getY());
        }
    }
}

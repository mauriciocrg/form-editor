/* 
 * nombre del archivo: RepeatControlManager.java
 *  
 * @Fecha Generación: 	Día/Mes/ Año 
 * @Autor: 	       	Mauricio Rodriguez  
 *
 *@Descripción:		Esta clase implementa la funcionalidad de el control RepeatControl
 *					que permite que se dibujen controles sobre este.
 *@Fecha Actualización:	15/03/2005
 *@Autor Actualización: 	Mauricio Rodriguez  
 * --------------------------------------------------------------------------------------- 
 * Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
 * --------------------------------------------------------------------------------------- 
 */
package control.controls.properties;

import javax.swing.JComponent;

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
import frames.internalFrame.panel.JEditPanel;

/**
 * Esta clase implementa la funcionalidad de el control RepeatControl
 * que permite que se dibujen controles sobre este.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 15/03/2005
 */
public class RepeatControlManager {
	
	private int x1;			// Indica la posicion X donde se preciono el boton del mause.
	private int y1;			// Indica la posicion Y donde se preciono el boton del mause.
	private int x2;			// Indica la posicion X donde se solto el boton del mause.
	private int y2;			// Indica la posicion Y donde se solto el boton del mause.
	private RepeatControl repeatControl;	// Es el control Repeat
	private JEditPanel editPanel;			// Es el panel donde se esta editando el fomrmulario
	
	
	/**
	 * Este metodo es el constructor de la clase, que inicializa 
	 * variables miembro.
	 * 
	 * @param	RepeatControl	
	 * @param	JEditPanel
	 */
	public RepeatControlManager(RepeatControl repeatControl, JEditPanel editPanel){
		this.repeatControl = repeatControl;
		this.editPanel = editPanel; 
	}
	
	/**
	 * Este metodo es llamado cuando se mueve el mause sobre
	 * el control Repeat, setea variables miembro y setea la 
	 * posición del control.
	 * 
	 * @param	int		X1 es el valor x de el puntero del mause.	
	 * @param	int		Y1 es el valor y de el puntero del mause.
	 */
	public void mouseMoved(int X1, int Y1){
		this.x1 = X1;
		this.y1 = Y1;
		repeatControl.setLocationRect(X1, Y1);
		repeatControl.repaint();
	}
	
	/**
	 * Este metodo es llamado cuando se hace un dragg con el mause 
	 * sobre el control Repeat, setea variables miembro y dibuja 
	 * un rectangulo azul en el lugar en el que se hizo el drag.
	 * 
	 * @param	int		X2 es el valor x de el puntero del mause.	
	 * @param	int		Y2 es el valor y de el puntero del mause.
	 */
	public void mouseDragged(int X2, int Y2){
		this.x2 = X2;
		this.y2 = Y2;
		repeatControl.setSizeRect(X2, Y2); 
		
		if(x2 > x1 && y2 > y1) {
			repeatControl.getRectDraw().reDraw(0, 0, x2 - x1, y2 - y1);
			repeatControl.getRectDraw().setLocation(x1, y1);
		}else
		if(x2 < x1 && y2 < y1) {
			repeatControl.getRectDraw().reDraw(0, 0, x1 - x2, y1 - y2);
			repeatControl.getRectDraw().setLocation(x2, y2);
		}else
		if(x2 < x1 && y2 > y1) {
			repeatControl.getRectDraw().reDraw(0, 0, x1 - x2, y2 - y1);
			repeatControl.getRectDraw().setLocation(x2, y1);
		}else{
			repeatControl.getRectDraw().reDraw(0, 0, x2 - x1, y1 - y2);
			repeatControl.getRectDraw().setLocation(x1, y2);
		}
		repeatControl.repaint();
	}
	
	/**
	 * Este metodo es llamado cuando se suelta el boton del mause 
	 * luego de un drag sobre el control Repeat, setea variables 
	 * miembro y dibuja el control seleccionado sobre el area
	 * en que fue dibujado el rectangulo azul.
	 * 
	 * @param	int		x2 es el valor x de el puntero del mause.	
	 * @param	int		y2 es el valor y de el puntero del mause.
	 */
	public void mouseReleased(int x2, int y2){
		if(editPanel.getDrawMode() != JEditPanel.ARROW){
			if(x2 > x1 && y2 > y1)
				addControl(editPanel.getControlCount(editPanel.getDrawMode()), x1, y1, x2, y2);
			else
			if(x2 < x1 && y2 < y1)						
				addControl(editPanel.getControlCount(editPanel.getDrawMode()), x2, y2, x1, y1);
			else
			if(x2 < x1 && y2 > y1)
				addControl(editPanel.getControlCount(editPanel.getDrawMode()), x2, y1, x1, y2);
			else
				addControl(editPanel.getControlCount(editPanel.getDrawMode()), x1, y2, x2, y1);
			
		}
		repeatControl.getRectDraw().setVisible(false);
		repeatControl.repaint();
	}
	/**
	 * Este metodo es llamado cuando se preciona el boton sobre el 
	 * control repeat, setea variables miembro y hace visible el 
	 * rectangulo azul.
	 */
	public void mousePressed(){
		repeatControl.getRectDraw().setVisible(true);
		repeatControl.getRectDraw().reDraw(0, 0, 0, 0);
	}
	
	/**
	 * Este metodo se encarga de agregar controles (JComponent) a este control repeat.
	 *
	 * @param	int		n_control es el numero que identifica que tipo de control se va adibujar.
	 * @param	int 	valor_x1 se el valor de distancia left de posicion del control.
	 * @param	int		int valor_y1 es el valor de distancia top de posicion del control.
	 * @param	int 	valor_x2 es el valor del ancho del control.
	 * @param	int		valor_y2 es el valor del alto del control.
	 */
	public void addControl(int n_control, int valor_x1, int valor_y1, int valor_x2, int valor_y2) {
		switch (editPanel.getDrawMode()) {
	        case JEditPanel.INPUT:
	        	InputControl ic = new InputControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, editPanel.getDocTable(), editPanel.getDocTree(), repeatControl.getElement(), editPanel);
	        	editPanel.CheckAndFixName(ic.getControl());
	        	ic.getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
	        	this.repeatControl.add(ic);
	        	ic.updateProperties();
	        	break;
	        case JEditPanel.LABEL:
	        	LabelControl lc = new LabelControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, editPanel.getDocTable(), editPanel.getDocTree(), repeatControl.getElement(), editPanel);
	        	editPanel.CheckAndFixName(lc.getControl());
	        	lc.getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
	        	this.repeatControl.add(lc);
	        	lc.updateProperties();
	        	break;
	        case JEditPanel.OUTPUT:
	        	OutputControl oc = new OutputControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, editPanel.getDocTable(), editPanel.getDocTree(), repeatControl.getElement(), editPanel);
	        	editPanel.CheckAndFixName(oc.getControl());
	        	oc.getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
	        	this.repeatControl.add(oc);
	        	oc.updateProperties();
	        	break;
	        case JEditPanel.UPLOAD:
	        	UploadControl uc = new UploadControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, editPanel.getDocTable(), editPanel.getDocTree(), repeatControl.getElement(), editPanel);
	        	editPanel.CheckAndFixName(uc.getControl());
	        	uc.getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
	        	this.repeatControl.add(uc);
	        	uc.updateProperties();
				break;
	        case JEditPanel.SECRET:
	        	SecretControl sc = new SecretControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, editPanel.getDocTable(), editPanel.getDocTree(), repeatControl.getElement(), editPanel);
	        	editPanel.CheckAndFixName(sc.getControl());
	        	sc.getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
	        	this.repeatControl.add(sc);
	        	sc.updateProperties();
				break;
	        case JEditPanel.TEXTAREA:
	        	TextAreaControl tc = new TextAreaControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, editPanel.getDocTable(), editPanel.getDocTree(), repeatControl.getElement(), editPanel);
	        	editPanel.CheckAndFixName(tc.getControl());
	        	tc.getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
	        	this.repeatControl.add(tc);
	        	tc.updateProperties();
				break;
	        case JEditPanel.SELECT:
	        	SelectControl ssc = new SelectControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, editPanel.getDocTable(), editPanel.getDocTree(), repeatControl.getElement(), editPanel);
	        	editPanel.CheckAndFixName(ssc.getControl());
	        	ssc.getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
	        	this.repeatControl.add(ssc);
	        	ssc.updateProperties();
				break;
	        case JEditPanel.SELECT1:
	        	Select1Control ss1c = new Select1Control( n_control, valor_x1, valor_y1, valor_x2, valor_y2, editPanel.getDocTable(), editPanel.getDocTree(), repeatControl.getElement(), editPanel);
	        	editPanel.CheckAndFixName(ss1c.getControl());
	        	ss1c.getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
	        	this.repeatControl.add(ss1c);
	        	ss1c.updateProperties();
				break;
	        case JEditPanel.SUBMIT:
	        	SubmitControl suc = new SubmitControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, editPanel.getDocTable(), editPanel.getDocTree(), repeatControl.getElement(), editPanel);
	        	editPanel.CheckAndFixName(suc.getControl());
	        	suc.getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
	        	this.repeatControl.add(suc);
	        	suc.updateProperties();
				break;
	        case JEditPanel.TRIGGER:
	        	TriggerControl trc = new TriggerControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, editPanel.getDocTable(), editPanel.getDocTree(), repeatControl.getElement(), editPanel);
	        	editPanel.CheckAndFixName(trc.getControl());
	        	trc.getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
	        	this.repeatControl.add(trc);
	        	trc.updateProperties();
				break;
	        case JEditPanel.RANGE:
	        	RangeControl rc = new RangeControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, editPanel.getDocTable(), editPanel.getDocTree(), repeatControl.getElement(), editPanel);
	        	editPanel.CheckAndFixName(rc.getControl());
	        	rc.getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
	        	this.repeatControl.add(rc);
	        	rc.updateProperties();
				break;
	        case JEditPanel.REPEAT:
	        	RepeatControl rec = new RepeatControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, editPanel.getDocTable(), editPanel.getDocTree(), repeatControl.getElement(), this.editPanel);
	        	editPanel.CheckAndFixName(rec.getControl());
	        	rec.getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
	        	this.repeatControl.add(rec);
	        	rec.updateProperties();
	        	break;
	        default:
				break;
		}
		editPanel.getDocTree().setControles(editPanel.getComponents());
		editPanel.getDocTable().setControles(editPanel.getComponents());
		editPanel.addControlCount(editPanel.getDrawMode());
		editPanel.setDrawMode(JEditPanel.ARROW);
		editPanel.getJInternalEditFrame().selectedButtons(false);
		editPanel.getJInternalEditFrame().setArrow();
	}
	
	/**
	 * Este metodo se encarga de agregar un JComponent al control repeat.
	 *
	 * @param	JComponent		jComponent es el control que va a ser agregado
	 * 							al control repeat.
	 */
	public void addControl(JComponent jComponent) {
		if(jComponent instanceof InputControl){
			editPanel.CheckAndFixName(((InputControl) jComponent).getControl());
			((InputControl) jComponent).getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
			this.repeatControl.add(((InputControl) jComponent));
		}else
		if(jComponent instanceof LabelControl){
			editPanel.CheckAndFixName(((LabelControl) jComponent).getControl());
			((LabelControl) jComponent).getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
			this.repeatControl.add(((LabelControl) jComponent));
		}else			
		if(jComponent instanceof OutputControl){
			editPanel.CheckAndFixName(((OutputControl) jComponent).getControl());
			((OutputControl) jComponent).getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
			this.repeatControl.add(((OutputControl) jComponent));
		}else
		if(jComponent instanceof UploadControl){
			editPanel.CheckAndFixName(((UploadControl) jComponent).getControl());
			((UploadControl) jComponent).getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
			this.repeatControl.add(((UploadControl) jComponent));
		}else
		if(jComponent instanceof SecretControl){
			editPanel.CheckAndFixName(((SecretControl) jComponent).getControl());
			((SecretControl) jComponent).getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
			this.repeatControl.add(((SecretControl) jComponent));
		}else			
		if(jComponent instanceof TextAreaControl){
			editPanel.CheckAndFixName(((TextAreaControl) jComponent).getControl());
			((TextAreaControl) jComponent).getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
			this.repeatControl.add(((TextAreaControl) jComponent));
		}else			
		if(jComponent instanceof SelectControl){
			editPanel.CheckAndFixName(((SelectControl) jComponent).getControl());
			((SelectControl) jComponent).getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
			this.repeatControl.add(((SelectControl) jComponent));
		}else						
		if(jComponent instanceof Select1Control){
			editPanel.CheckAndFixName(((Select1Control) jComponent).getControl());
			((Select1Control) jComponent).getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
			this.repeatControl.add(((Select1Control) jComponent));
		}else						
		if(jComponent instanceof SubmitControl){
			editPanel.CheckAndFixName(((SubmitControl) jComponent).getControl());
			((SubmitControl) jComponent).getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
			this.repeatControl.add(((SubmitControl) jComponent));
		}else
		if(jComponent instanceof TriggerControl){
			editPanel.CheckAndFixName(((TriggerControl) jComponent).getControl());
			((TriggerControl) jComponent).getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
			this.repeatControl.add(((TriggerControl) jComponent));
		}else										
		if(jComponent instanceof RangeControl){
			editPanel.CheckAndFixName(((RangeControl) jComponent).getControl());
			((RangeControl) jComponent).getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
			this.repeatControl.add(((RangeControl) jComponent));
		}else										
		if(jComponent instanceof RepeatControl){
			editPanel.CheckAndFixName(((RepeatControl) jComponent).getControl());
			((RepeatControl) jComponent).getProperties().setInternalFrame(editPanel.getJInternalEditFrame());
			this.repeatControl.add(((RepeatControl) jComponent));
		}
		((JControl) jComponent).setJEditPanel(editPanel);
		editPanel.getDocTree().setControles(editPanel.getComponents());
		editPanel.getDocTable().setControles(editPanel.getComponents());
		editPanel.setDrawMode(JEditPanel.ARROW);
		editPanel.getJInternalEditFrame().selectedButtons(false);
		editPanel.getJInternalEditFrame().setArrow();
	}
	
	/**
	 * Este metodo permite setear el JEditPanel al que pertenece el control repeat.
	 * 
	 * @param JEditPanel
	 */
	public void setJEditPanel(JEditPanel editPanel){
		this.editPanel = editPanel;
	}
}

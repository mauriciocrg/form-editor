/* 
* nombre del archivo: JEditPanel.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Implementa un JPanel donde es posible dibujar los controles 
*					y asi podr crear un Formulario.
*@Fecha Actualización:	25/01/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package frames.internalFrame.panel;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;
import shapes.JRectangle;

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
import doc.XmlDoc;
import doc.table.DocTable;
import doc.tree.DocTree;
import frames.internalFrame.JInternalEditFrame;

/**
 * Esta clase extiende de JPanel y permite que en el se 
 * puedan dibujar los controles del Formulario.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
 */

public class JEditPanel extends JPanel {

	public static final int INPUT    = 0;	// Constante que identifica al control input.
    public static final int LABEL    = 1;	// Constante que identifica al control label.
    public static final int OUTPUT   = 2;	// Constante que identifica al control output.
    public static final int UPLOAD   = 3;	// Constante que identifica al control upload.
    public static final int SECRET   = 4;	// Constante que identifica al control secret.
    public static final int TEXTAREA = 5;	// Constante que identifica al control textarea.
    public static final int SELECT   = 6;	// Constante que identifica al control select.
    public static final int SELECT1  = 7;	// Constante que identifica al control select1.
    public static final int SUBMIT   = 8;	// Constante que identifica al control submit.
    public static final int TRIGGER  = 9;	// Constante que identifica al control trigger.
    public static final int RANGE    = 10;	// Constante que identifica al control range.
    public static final int REPEAT   = 11;	// Constante que identifica al control repeat.
    public static final int ARROW    = 12;	// Constante que indica que no se va a dibujar ningun control.
	
	private JInternalEditFrame editFrame = null;	// Es el JInternalEditFrame al que pertenece.
	private XmlDoc xmldoc = null;					// Es el contenido del primer nodo de Document.
	private DocTree docTree = null;					// Es el DocTree de la ventana principal. 
	private DocTable docTable = null;				// Es el DocTable de la ventana principal.
	
    private int CONTROL_X [] = new int [12];	/* Cuenta la cantidad de controles creados por tipo
    											 * es solo para asignar numero al nombre, cuando se 
    											 * crean dos o mas controles del mismo tipo. */			
    private int	mode = ARROW;				// Indica el control que va a ser dibujado.
	
	private int x1;		// Indica la posicion X donde se preciono el boton del mause.
	private int y1;		// Indica la posicion Y donde se preciono el boton del mause.
	private int x2;		// Indica la posicion X donde se solto el boton del mause.
	private int y2;		// Indica la posicion Y donde se solto el boton del mause.

	private JRectangle rect = null;	// Es el rectangulo que se dibuja en el panel.

	/**
	 * Este metodo es el constructor de la clase, inicializa variables miembro y 
	 * agrega funcionalidad a los eventos del mause sobre el panel principal.
	 * 
	 * @param	int			width determina el ancho del panel principal.
	 * @param	int			height determina el alto del panel principal.
	 * @param	DocTable 	docTable es el DocTable de la ventana principal.
	 * @param	DocTree 	docTree es el DocTree de la ventana principal.
	 * @param	XmlDoc 		xmldoc es la clase que administra el nodo principal del Document.
	 * @param	JInternalEditFrame editFrame es el JInternalFrame al que pertenece este JPanel.
	 */
	public JEditPanel(int width, int height, DocTable docTable, DocTree docTree, XmlDoc xmldoc, JInternalEditFrame editFrame) {
		rect = new JRectangle(0,0,0,0);
		
		this.docTree = docTree;
		this.docTable = docTable;
		
		this.xmldoc = xmldoc;
		this.editFrame = editFrame;
		this.setLayout(null);
		this.add(rect, null);
		this.setPreferredSize(new Dimension(width, height));
		this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseMoved(java.awt.event.MouseEvent event) {    
				event.consume();
				x1 = event.getX();
				y1 = event.getY();
		        repaint();
			} 
			public void mouseDragged(java.awt.event.MouseEvent event) {    
				event.consume();
				x2 = event.getX();
				y2 = event.getY();
				if(x2 > x1 && y2 > y1) {
					rect.reDraw(0, 0, x2 - x1, y2 - y1);
					rect.setLocation(x1, y1);
				}else
				if(x2 < x1 && y2 < y1) {
					rect.reDraw(0, 0, x1 - x2, y1 - y2);
					rect.setLocation(x2, y2);
				}else
				if(x2 < x1 && y2 > y1) {
					rect.reDraw(0, 0, x1 - x2, y2 - y1);
					rect.setLocation(x2, y1);
				}else{
					rect.reDraw(0, 0, x2 - x1, y1 - y2);
					rect.setLocation(x1, y2);
				}
		        repaint();
			}
		});
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			private Object docTable;
			public void mouseReleased(java.awt.event.MouseEvent event) {    
				event.consume();
				
				if(mode != ARROW){
					if(x2 > x1 && y2 > y1)
						addControl(CONTROL_X[mode], x1, y1, event.getX(), event.getY());
					else
					if(x2 < x1 && y2 < y1)						
						addControl(CONTROL_X[mode], event.getX(), event.getY(), x1, y1);
					else
					if(x2 < x1 && y2 > y1)
						addControl(CONTROL_X[mode], event.getX(), y1, x1, event.getY());
					else
						addControl(CONTROL_X[mode], x1, event.getY(), event.getX(), y1);
					CONTROL_X[mode]++;
				}
			    repaint();
				rect.setVisible(false);
		        mode = JEditPanel.ARROW;
			} 
			public void mousePressed(java.awt.event.MouseEvent e) {
				setControles();
				e.consume();
				rect.setVisible(true);
				rect.reDraw(0, 0, 0, 0);
			}
		});
	}
	
	/**
	 * Este metodo se encarga de agregar controles (JComponent) a este JPanel.
	 *
	 * @param	int		n_control es el numero que identifica que tipo de control se va adibujar.
	 * @param	int 	valor_x1 se el valor de distancia left de posicion del control.
	 * @param	int		int valor_y1 es el valor de distancia top de posicion del control.
	 * @param	int 	valor_x2 es el valor del ancho del control.
	 * @param	int		valor_y2 es el valor del alto del control.
	 */
	public void addControl(int n_control, int valor_x1, int valor_y1, int valor_x2, int valor_y2) {
		switch (mode) {
	        case INPUT:
	        	InputControl ic = new InputControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, docTable, docTree, xmldoc, this);
	        	CheckAndFixName(ic.getControl());
	        	ic.getProperties().setInternalFrame(this.editFrame);
	        	ic.setJEditPanel(this);
	        	this.add(ic);
	        	ic.updateProperties();
	        	break;
	        case LABEL:
	        	LabelControl lc = new LabelControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, docTable, docTree, xmldoc, this);
	        	CheckAndFixName(lc.getControl());
	        	lc.getProperties().setInternalFrame(this.editFrame);
	        	lc.setJEditPanel(this);
	        	this.add(lc);
	        	lc.updateProperties();
	        	break;
	        case OUTPUT:
	        	OutputControl oc = new OutputControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, docTable, docTree, xmldoc, this);
	        	CheckAndFixName(oc.getControl());
	        	oc.getProperties().setInternalFrame(this.editFrame);
	        	oc.setJEditPanel(this);
	        	this.add(oc);
	        	oc.updateProperties();
	        	break;
	        case UPLOAD:
	        	UploadControl uc = new UploadControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, docTable, docTree, xmldoc, this);
	        	CheckAndFixName(uc.getControl());
	        	uc.getProperties().setInternalFrame(this.editFrame);
	        	uc.setJEditPanel(this);
	        	this.add(uc);
	        	uc.updateProperties();
				break;
	        case SECRET:
	        	SecretControl sc = new SecretControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, docTable, docTree, xmldoc, this);
	        	CheckAndFixName(sc.getControl());
	        	sc.getProperties().setInternalFrame(this.editFrame);
	        	sc.setJEditPanel(this);
	        	this.add(sc);
	        	sc.updateProperties();
				break;
	        case TEXTAREA:
	        	TextAreaControl tc = new TextAreaControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, docTable, docTree, xmldoc, this);
	        	CheckAndFixName(tc.getControl());
	        	tc.getProperties().setInternalFrame(this.editFrame);
	        	tc.setJEditPanel(this);
	        	this.add(tc);
	        	tc.updateProperties();
				break;
	        case SELECT:
	        	SelectControl ssc = new SelectControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, docTable, docTree, xmldoc, this);
	        	CheckAndFixName(ssc.getControl());
	        	ssc.getProperties().setInternalFrame(this.editFrame);
	        	ssc.setJEditPanel(this);
	        	this.add(ssc);
	        	ssc.updateProperties();
				break;
	        case SELECT1:
	        	Select1Control ss1c = new Select1Control( n_control, valor_x1, valor_y1, valor_x2, valor_y2, docTable, docTree, xmldoc, this);
	        	CheckAndFixName(ss1c.getControl());
	        	ss1c.getProperties().setInternalFrame(this.editFrame);
	        	ss1c.setJEditPanel(this);
	        	this.add(ss1c);
	        	ss1c.updateProperties();
				break;
	        case SUBMIT:
	        	SubmitControl suc = new SubmitControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, docTable, docTree, xmldoc, this);
	        	CheckAndFixName(suc.getControl());
	        	suc.getProperties().setInternalFrame(this.editFrame);
	        	suc.setJEditPanel(this);
	        	this.add(suc);
	        	suc.updateProperties();
				break;
	        case TRIGGER:
	        	TriggerControl trc = new TriggerControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, docTable, docTree, xmldoc, this);
	        	CheckAndFixName(trc.getControl());
	        	trc.getProperties().setInternalFrame(this.editFrame);
	        	trc.setJEditPanel(this);
	        	this.add(trc);
	        	trc.updateProperties();
				break;
	        case RANGE:
	        	RangeControl rc = new RangeControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, docTable, docTree, xmldoc, this);
	        	CheckAndFixName(rc.getControl());
	        	rc.getProperties().setInternalFrame(this.editFrame);
	        	rc.setJEditPanel(this);
	        	this.add(rc);
	        	rc.updateProperties();
				break;
	        case REPEAT:
	        	RepeatControl rec = new RepeatControl( n_control, valor_x1, valor_y1, valor_x2, valor_y2, docTable, docTree, xmldoc, this);
	        	CheckAndFixName(rec.getControl());
	        	rec.getProperties().setInternalFrame(this.editFrame);
	        	rec.setJEditPanel(this);
	        	this.add(rec);
	        	rec.updateProperties();
	        	break;
	        default:
				break;
		}
		this.docTree.setControles(this.getComponents());
		this.docTable.setControles(this.getComponents());
		editFrame.selectedButtons(false);
		editFrame.setArrow();
	}
	
	/**
	 * Este metodo se encarga de agregar un JComponent a este JPanel.
	 *
	 * @param	JComponent		jComponent es el control que va a ser agregado
	 * 							al JPanel.
	 */
	public void addControl(JComponent jComponent) {
		if(jComponent instanceof InputControl){
			CheckAndFixName(((InputControl) jComponent).getControl());
			((InputControl) jComponent).getProperties().setInternalFrame(this.editFrame);
			this.add(((InputControl) jComponent));
		}else
		if(jComponent instanceof LabelControl){
			CheckAndFixName(((LabelControl) jComponent).getControl());
			((LabelControl) jComponent).getProperties().setInternalFrame(this.editFrame);
			this.add(((LabelControl) jComponent));
		}else			
		if(jComponent instanceof OutputControl){
			CheckAndFixName(((OutputControl) jComponent).getControl());
			((OutputControl) jComponent).getProperties().setInternalFrame(this.editFrame);
			this.add(((OutputControl) jComponent));
		}else
		if(jComponent instanceof UploadControl){
			CheckAndFixName(((UploadControl) jComponent).getControl());
			((UploadControl) jComponent).getProperties().setInternalFrame(this.editFrame);
			this.add(((UploadControl) jComponent));
		}else
		if(jComponent instanceof SecretControl){
			CheckAndFixName(((SecretControl) jComponent).getControl());
			((SecretControl) jComponent).getProperties().setInternalFrame(this.editFrame);
			this.add(((SecretControl) jComponent));
		}else			
		if(jComponent instanceof TextAreaControl){
			CheckAndFixName(((TextAreaControl) jComponent).getControl());
			((TextAreaControl) jComponent).getProperties().setInternalFrame(this.editFrame);
			this.add(((TextAreaControl) jComponent));
		}else			
		if(jComponent instanceof SelectControl){
			CheckAndFixName(((SelectControl) jComponent).getControl());
			((SelectControl) jComponent).getProperties().setInternalFrame(this.editFrame);
			this.add(((SelectControl) jComponent));
		}else						
		if(jComponent instanceof Select1Control){
			CheckAndFixName(((Select1Control) jComponent).getControl());
			((Select1Control) jComponent).getProperties().setInternalFrame(this.editFrame);
			this.add(((Select1Control) jComponent));
		}else						
		if(jComponent instanceof SubmitControl){
			CheckAndFixName(((SubmitControl) jComponent).getControl());
			((SubmitControl) jComponent).getProperties().setInternalFrame(this.editFrame);
			this.add(((SubmitControl) jComponent));
		}else
		if(jComponent instanceof TriggerControl){
			CheckAndFixName(((TriggerControl) jComponent).getControl());
			((TriggerControl) jComponent).getProperties().setInternalFrame(this.editFrame);
			this.add(((TriggerControl) jComponent));
		}else										
		if(jComponent instanceof RangeControl){
			CheckAndFixName(((RangeControl) jComponent).getControl());
			((RangeControl) jComponent).getProperties().setInternalFrame(this.editFrame);
			this.add(((RangeControl) jComponent));
		}else										
		if(jComponent instanceof RepeatControl){
			CheckAndFixName(((RepeatControl) jComponent).getControl());
			((RepeatControl) jComponent).getProperties().setInternalFrame(this.editFrame);
			this.add(((RepeatControl) jComponent));
		}
		((JControl) jComponent).setJEditPanel(this);
		this.docTree.setControles(this.getComponents());
		this.docTable.setControles(this.getComponents());
		editFrame.selectedButtons(false);
		editFrame.setSave(false);
		editFrame.setArrow();
	}
	
	/**
	 * Este metodo se encarga de actualizar el JTree de la ventana 
	 * principal con el control que fue agregado. 
	 */
	public void setControles() {
		if(docTree.getDocument().getFirstChild() != null) 
		   docTree.getDocument().removeChild(docTree.getDocument().getFirstChild());			
		
		docTree.getDocument().appendChild(xmldoc.getXml());
		docTree.getJTree().addNotify();
		docTree.getJTree().updateUI();
		docTree.getJTree().expandRow(1);
		docTree.getJTree().repaint();
		docTree.setControles(this.getComponents());
	}
	
	/**
	 * Este metodo permite setear el tipo de control que va a ser 
	 * dibujado.
	 * 
	 * @param	 int	mode es el valor que indica el control que 
	 * 					va a ser dibjado.
	 */
	public void setDrawMode(int mode) {
		this.mode = mode;
	}
	
	/**
	 * Este metodo retorna el valor del mode.
	 * 
	 * @return	 int	mode es el valor que indica el control que 
	 * 					va a ser dibjado.
	 */
	public int getDrawMode() {
		return this.mode;
	}
	
	/**
	 * Este metodo dado un entero que representa el tipo de control
	 * retorna la cantidad de controles de ese tipo que existen en el
	 * formulario.
	 * 
	 * @param	int
	 */
	public int getControlCount(int type){
		return CONTROL_X[type];
	}
	
	/**
	 * Este metodo dado un entero que representa el tipo de control
	 * aumenta el valor de la cantidad de controles de ese tipo que 
	 * existen en el formulario.
	 * 
	 * @param	int
	 */
	public void addControlCount(int type){
		CONTROL_X[type]++;
	}
	
	/**
	 * Este metodo chequea que el nombre del control sea correcto, que
	 * no este repitido en otro contol. 
	 * 
	 * @param	 JControl 	Control	es el control al que se le va a 
	 * 						chequear el nombre.
	 */
	public void CheckAndFixName(JControl Control) {
		Component[] Controles = this.getComponents();
		int size = 0;
		if(Controles != null) size = Controles.length;
		for(int i = 0; i < size; i++) 
			if(Controles[i] instanceof InputControl){ 
				if(((InputControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof LabelControl){ 				
				if(((LabelControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof OutputControl) { 
				if(((OutputControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof RangeControl){  
				if(((RangeControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof SecretControl){  
				if(((SecretControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof SelectControl){  
				if(((SelectControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof Select1Control){  
				if(((Select1Control) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof SubmitControl){  
				if(((SubmitControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof TextAreaControl){  
				if(((TextAreaControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof TriggerControl){  
				if(((TriggerControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof UploadControl){  
				if(((UploadControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof RepeatControl){  
				if(((RepeatControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				} else
					checkAndFixNameInRepeatControl(((JControl)Controles[i]).getComponents(), Control);
			}
	}
	
	/**
	 * Este metodo chequea que el nombre del control sea correcto, que no 
	 * este repitido en otro contol, iterando sobre el array de controles 
	 * pertenecientes a un control repeat.
	 * 
	 * @param	Component[]	Contiene los controles pertenecientes a 
	 *						un control repeat.
	 * @param	JControl 	Control	es el control al que se le va a 
	 * 						chequear el nombre.
	 */
	private void checkAndFixNameInRepeatControl(Component[] Controles, JControl Control){
		int i = 0;
		
		while(i < Controles.length ) {
		if(Controles[i] instanceof InputControl){ 
				if(((InputControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof LabelControl){ 				
				if(((LabelControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof OutputControl) { 
				if(((OutputControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof RangeControl){  
				if(((RangeControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof SecretControl){  
				if(((SecretControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof SelectControl){  
				if(((SelectControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof Select1Control){  
				if(((Select1Control) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof SubmitControl){  
				if(((SubmitControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof TextAreaControl){  
				if(((TextAreaControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof TriggerControl){  
				if(((TriggerControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof UploadControl){  
				if(((UploadControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				}
			}else
			if(Controles[i] instanceof RepeatControl){  
				if(((RepeatControl) Controles[i]).getName().equals(Control.getName())){
					Control.setName(Control.getName()+0);
					CheckAndFixName(Control);
				} 
				checkAndFixNameInRepeatControl(((JControl)Controles[i]).getComponents(), Control);
			}
			i++;
		}
	}
	
	/**
	 * Este metodo setea todos los controles que no tengan el 
	 * identificador name, como no seleccionados.
	 * 
	 * @param	String
	 */
	public void setSelected(String name){
		Component[] controles = this.getComponents();
		for(int i = 0; i<controles.length; i++){
			if(controles[i] instanceof UploadControl ||
			   controles[i] instanceof TriggerControl ||
			   controles[i] instanceof TextAreaControl ||
			   controles[i] instanceof SubmitControl ||
			   controles[i] instanceof Select1Control ||
			   controles[i] instanceof SelectControl ||
			   controles[i] instanceof SecretControl ||
			   controles[i] instanceof RangeControl ||
			   controles[i] instanceof OutputControl ||
			   controles[i] instanceof LabelControl ||
			   controles[i] instanceof InputControl ||
			   controles[i] instanceof RepeatControl )
				if(!((JControl) controles[i]).getName().equals(name))
					((JControl) controles[i]).setSelected(false);
			if(controles[i] instanceof RepeatControl)
				setSelected(name, ((JControl)controles[i]).getComponents());
		}
	}
	
	/**
	 * Este metodo dado un array de Component y un String, setea todos 
	 * los controles del array que no tengan el identificador name, como 
	 * no seleccionados.
	 * 
	 * @param	String
	 * @param	Component []
	 */
	private void setSelected(String name, Component [] controles){
		for(int i = 0; i<controles.length; i++){
			if(controles[i] instanceof UploadControl ||
			   controles[i] instanceof TriggerControl ||
			   controles[i] instanceof TextAreaControl ||
			   controles[i] instanceof SubmitControl ||
			   controles[i] instanceof Select1Control ||
			   controles[i] instanceof SelectControl ||
			   controles[i] instanceof SecretControl ||
			   controles[i] instanceof RangeControl ||
			   controles[i] instanceof OutputControl ||
			   controles[i] instanceof LabelControl ||
			   controles[i] instanceof InputControl ||
			   controles[i] instanceof RepeatControl )
				if(!((JControl)controles[i]).getName().equals(name))
					((JControl)controles[i]).setSelected(false);
			if(controles[i] instanceof RepeatControl)
				setSelected(name, ((JControl)controles[i]).getComponents());
		}
	}
	
	/**
	 * Este metodo retorna el JControl que este seleccionado, si no
	 * existe ninguno seleccionado retorna null.
	 * 
	 * @return	JComponent	es el JControl que esta seleccionado.
	 */
	public JComponent getSelectedJControl(){
		Component[] controles = this.getComponents();
		for(int i = 0; i<controles.length; i++){
			if(controles[i] instanceof UploadControl ||
			   controles[i] instanceof TriggerControl ||
			   controles[i] instanceof TextAreaControl ||
			   controles[i] instanceof SubmitControl ||
			   controles[i] instanceof Select1Control ||
			   controles[i] instanceof SelectControl ||
			   controles[i] instanceof SecretControl ||
			   controles[i] instanceof RangeControl ||
			   controles[i] instanceof OutputControl ||
			   controles[i] instanceof LabelControl ||
			   controles[i] instanceof InputControl ||
			   controles[i] instanceof RepeatControl) 
			   if(((JControl) controles[i]).isSelected())
			   		return (JComponent) controles[i];
			   else
			   if(controles[i] instanceof RepeatControl){
			   	JComponent control = getSelectedJControl(((RepeatControl)controles[i]).getComponents());
			   		if(control != null)
						return control;  
			   }
		}
		return null;
	}
	
	/**
	 * Este metodo retorna el JControl que este seleccionado, si no
	 * existe ninguno seleccionado retorna null.
	 * 
	 * @param	Component [] es el conjunto de controles que se encuentran en un repeat.
	 * @return	JComponent	es el JControl que esta seleccionado.
	 */
	public JComponent getSelectedJControl(Component [] controles){
		for(int i = 0; i<controles.length; i++){
			if(controles[i] instanceof UploadControl ||
			   controles[i] instanceof TriggerControl ||
			   controles[i] instanceof TextAreaControl ||
			   controles[i] instanceof SubmitControl ||
			   controles[i] instanceof Select1Control ||
			   controles[i] instanceof SelectControl ||
			   controles[i] instanceof SecretControl ||
			   controles[i] instanceof RangeControl ||
			   controles[i] instanceof OutputControl ||
			   controles[i] instanceof LabelControl ||
			   controles[i] instanceof InputControl ||
			   controles[i] instanceof RepeatControl) 
			   if(((JControl) controles[i]).isSelected())
			   		return (JComponent) controles[i];
			   else
			   if(controles[i] instanceof RepeatControl){
			   	JComponent control = getSelectedJControl(((RepeatControl)controles[i]).getComponents());
			   		if(control != null)
						return control;  
			   }
		}
		return null;
	}
	
	/**
	 * Este metodo ermueve el JControl que este seleccionado, si no
	 * existe ninguno seleccionado retorna null.
	 */
	public void delSelectedJControl(){
		Component[] controles = this.getComponents();
		for(int i = 0; i<controles.length; i++){
			if(controles[i] instanceof UploadControl ||
			   controles[i] instanceof TriggerControl ||
			   controles[i] instanceof TextAreaControl ||
			   controles[i] instanceof SubmitControl ||
			   controles[i] instanceof Select1Control ||
			   controles[i] instanceof SelectControl ||
			   controles[i] instanceof SecretControl ||
			   controles[i] instanceof RangeControl ||
			   controles[i] instanceof OutputControl ||
			   controles[i] instanceof LabelControl ||
			   controles[i] instanceof InputControl ||
			   controles[i] instanceof RepeatControl) 
			   if(((JControl) controles[i]).isSelected()){
				   	xmldoc.delComponent(xmldoc.getXml(), ((JControl) controles[i]).getName());	
			   		this.remove(i);
			   		docTree.getJTree().updateUI();
					docTree.getJTree().expandRow(1);
					docTree.getJTree().repaint();
					docTree.setControles(this.getComponents());
			   		return; 
			   	}
			   else
			   if(controles[i] instanceof RepeatControl){
			   		delSelectedJControl(((RepeatControl)controles[i]).getComponents());
			   }
		}
	}
	
	/**
	 * Este metodo remueve el JControl que este seleccionado, si no
	 * existe ninguno seleccionado retorna null.
	 * 
	 * @param	Component [] es el conjunto de controles que se encuentran en un repeat.
	 */
	public void delSelectedJControl(Component [] controles){
		for(int i = 0; i<controles.length; i++){
			if(controles[i] instanceof UploadControl ||
			   controles[i] instanceof TriggerControl ||
			   controles[i] instanceof TextAreaControl ||
			   controles[i] instanceof SubmitControl ||
			   controles[i] instanceof Select1Control ||
			   controles[i] instanceof SelectControl ||
			   controles[i] instanceof SecretControl ||
			   controles[i] instanceof RangeControl ||
			   controles[i] instanceof OutputControl ||
			   controles[i] instanceof LabelControl ||
			   controles[i] instanceof InputControl ||
			   controles[i] instanceof RepeatControl) 
			   if(((JControl) controles[i]).isSelected()){
				   	xmldoc.delComponent(xmldoc.getXml(), ((JControl) controles[i]).getName());	
			   		this.remove(i);
			   		docTree.getJTree().updateUI();
					docTree.getJTree().expandRow(1);
					docTree.getJTree().repaint();
					docTree.setControles(this.getComponents());
			   		return;
			   }
			   else
			   if(controles[i] instanceof RepeatControl){
			   	 	delSelectedJControl(((RepeatControl)controles[i]).getComponents());
			   }
		}
	}
	
	/**
	 * Este metodo dado el nombre de un control lo retorna.
	 * 
	 * @param 	String 		es el nombre del componente.
	 * @return	JComponent	es el JControl seleccionado.
	 */
	public JComponent getSelectedJControl(String name){
		Component[] controles = this.getComponents();
		for(int i = 0; i<controles.length; i++){
			if(controles[i] instanceof UploadControl ||
			   controles[i] instanceof TriggerControl ||
			   controles[i] instanceof TextAreaControl ||
			   controles[i] instanceof SubmitControl ||
			   controles[i] instanceof Select1Control ||
			   controles[i] instanceof SelectControl ||
			   controles[i] instanceof SecretControl ||
			   controles[i] instanceof RangeControl ||
			   controles[i] instanceof OutputControl ||
			   controles[i] instanceof LabelControl ||
			   controles[i] instanceof InputControl ||
			   controles[i] instanceof RepeatControl)
			   if(((JControl) controles[i]).getName().equals(name))
			   		return (JComponent) controles[i];
			   else
			   if(controles[i] instanceof RepeatControl){
			   		JComponent control = getSelectedJControl(name, ((RepeatControl)controles[i]).getComponents());
			   		if(control != null)
						return control;  
			   }
		}
		return null;
	}
	
	/**
	 * Este metodo retorna el JControl que este seleccionado, si no
	 * existe ninguno seleccionado retorna null.
	 * 
	 * @param	Component [] es el conjunto de controles que se encuentran en un repeat.
	 * @return	JComponent	es el JControl que esta seleccionado.
	 */
	public JComponent gelSelectedJControl(Component [] controles){
		for(int i = 0; i<controles.length; i++){
			if(controles[i] instanceof UploadControl ||
			   controles[i] instanceof TriggerControl ||
			   controles[i] instanceof TextAreaControl ||
			   controles[i] instanceof SubmitControl ||
			   controles[i] instanceof Select1Control ||
			   controles[i] instanceof SelectControl ||
			   controles[i] instanceof SecretControl ||
			   controles[i] instanceof RangeControl ||
			   controles[i] instanceof OutputControl ||
			   controles[i] instanceof LabelControl ||
			   controles[i] instanceof InputControl ||
			   controles[i] instanceof RepeatControl) 
			   if(((JControl) controles[i]).isSelected())
			   		return (JComponent) controles[i];
			   else
			   if(controles[i] instanceof RepeatControl){
			   	JComponent control = getSelectedJControl(((RepeatControl)controles[i]).getComponents());
			   		if(control != null)
						return control; 
			   }
		}
		return null;
	}
	
	/**
	 * Este metodo dado el nombre de un control lo retorna.
	 * 
	 * @param 	String 		es el nombre del componente.
	 * @param 	Component []es el conjunto de componentes de un repeat.
	 * @return	JComponent	es el JControl seleccionado.
	 */
	public JComponent getSelectedJControl(String name, Component [] controles){
		for(int i = 0; i<controles.length; i++){
			if(controles[i] instanceof UploadControl ||
			   controles[i] instanceof TriggerControl ||
			   controles[i] instanceof TextAreaControl ||
			   controles[i] instanceof SubmitControl ||
			   controles[i] instanceof Select1Control ||
			   controles[i] instanceof SelectControl ||
			   controles[i] instanceof SecretControl ||
			   controles[i] instanceof RangeControl ||
			   controles[i] instanceof OutputControl ||
			   controles[i] instanceof LabelControl ||
			   controles[i] instanceof InputControl ||
			   controles[i] instanceof RepeatControl)
			   if(((JControl) controles[i]).getName().equals(name))
			   		return (JComponent) controles[i];
			   else
			   if(controles[i] instanceof RepeatControl){
			   		JComponent control = getSelectedJControl(name, ((RepeatControl)controles[i]).getComponents());
			   		if(control != null)
						return control;  
			   }
		}
		return null;
	}
	
	/**
	 * Este metodo dado el nombre de un control lo borra.
	 * 
	 * @param 	String 		es el nombre del componente.
	 */
	public void delSelectedJControl(String name){
		
		Component[] controles = this.getComponents();
		for(int i = 0; i<controles.length; i++){
			if(controles[i] instanceof UploadControl ||
			   controles[i] instanceof TriggerControl ||
			   controles[i] instanceof TextAreaControl ||
			   controles[i] instanceof SubmitControl ||
			   controles[i] instanceof Select1Control ||
			   controles[i] instanceof SelectControl ||
			   controles[i] instanceof SecretControl ||
			   controles[i] instanceof RangeControl ||
			   controles[i] instanceof OutputControl ||
			   controles[i] instanceof LabelControl ||
			   controles[i] instanceof InputControl ||
			   controles[i] instanceof RepeatControl)
			   if(((JControl) controles[i]).getName().equals(name)){
			   		//xmldoc.getXml().removeChild(((JControl) controles[i]).getElement());
			   		xmldoc.delComponent(xmldoc.getXml(), ((JControl) controles[i]).getName());	
			   		this.remove(i);
			   		docTree.getJTree().updateUI();
					docTree.getJTree().expandRow(1);
					docTree.getJTree().repaint();
					docTree.setControles(this.getComponents());
			   		return;
			   } else
			   if(controles[i] instanceof RepeatControl){
			   		delSelectedJControl(name, (JControl) controles[i],((RepeatControl)controles[i]).getComponents());
			   }
		}
	}
	
	/**
	 * Este metodo dado el nombre de un control lo borra.
	 * 
	 * @param 	String 		es el nombre del componente.
	 * @param 	Component []es el conjunto de componentes de un repeat.
	 */
	public void delSelectedJControl(String name, JControl control,Component [] controles){
		for(int i = 0; i<controles.length; i++){
			if(controles[i] instanceof UploadControl ||
			   controles[i] instanceof TriggerControl ||
			   controles[i] instanceof TextAreaControl ||
			   controles[i] instanceof SubmitControl ||
			   controles[i] instanceof Select1Control ||
			   controles[i] instanceof SelectControl ||
			   controles[i] instanceof SecretControl ||
			   controles[i] instanceof RangeControl ||
			   controles[i] instanceof OutputControl ||
			   controles[i] instanceof LabelControl ||
			   controles[i] instanceof InputControl ||
			   controles[i] instanceof RepeatControl)
			   if(((JControl) controles[i]).getName().equals(name)){
			   		control.remove(i);	
			   		xmldoc.delComponent(xmldoc.getXml(),((JControl) controles[i]).getName());
			   		docTree.getJTree().updateUI();
					docTree.getJTree().expandRow(1);
					docTree.getJTree().repaint();
					docTree.setControles(this.getComponents());
			   		return;
		   	   }
			   else
			   if(controles[i] instanceof RepeatControl)
			   		delSelectedJControl(name, (JControl) controles[i],((RepeatControl)controles[i]).getComponents());
		}
	}
	
	/**
	 * Este metodo retorna el JInternalEditFrame al que pertenece 
	 * este panel.
	 * 
	 * @return	JInternalEditFrame
	 */
	public JInternalEditFrame getJInternalEditFrame(){
		return this.editFrame;
	}
	
	/**
	 * Este metodo retorna el DocTable de la ventana principal.
	 * 
	 * @return	DocTable
	 */
	public DocTable getDocTable(){
		return this.docTable;
	}
	
	/**
	 * Este metodo retorna el DocTree de la ventana principal.
	 * 
	 * @return	DocTree
	 */
	public DocTree getDocTree(){
		return this.docTree;
	}
	
	/**
	 * Este metodo retorna el XmlDoc del formulario.
	 * 
	 * @return	XmlDoc
	 */
	public XmlDoc getXmlDoc(){
		return this.xmldoc;
	}
}
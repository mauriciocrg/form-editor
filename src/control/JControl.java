/* 
* nombre del archivo: JControl.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Implementa las caracteristicas y propiedades generales 
*					de los controles (XForms).
*@Fecha Actualización:	25/01/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package control;

import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.w3c.dom.Element;

import shapes.JShape;

import control.controls.popupMenu.PopupMenu;
import control.controls.properties.Properties;

import doc.XmlDoc;
import doc.table.DocTable;
import doc.tree.DocTree;
import frames.internalFrame.panel.JEditPanel;

/*
 * Esta clase implementa las caracteristicas y propiedades generales 
 * de los controles (XForms).
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
 */

public class JControl extends JComponent {
	protected boolean visible = false;      //Determina que los shapes alrededor del control sean visibles o no 
	
	protected JLabel actionEvent;			//Label que se encarga del evento Drag 
	protected JLabel jLabel;				//equivale a un control label
	protected JButton jButton;				//equivale a un control submit o tregger
	protected JButton uploadButton;			//equivale a el boton del control upload
	
	protected String name = ""; 			//Propiedad name
	protected String hint = "";				//Propiedad hint
	protected String bind = "";				//Propiedad bind
	protected String ref  = "";				//Propiedad ref
	protected String model = "";			//Propiedad model
	protected String helpText = "";			//Propiedad helpText
	protected String tabPosition = "";		//Propiedad tabPosition
	
	protected String type;					//Indica el tipo de control
	
	protected JTable jTable;				//es la JTable de DocTable
	protected Vector table;					//el contenido de jTable
	protected Element control;				//contiene el tag del control XForm con sus propiedades
	protected Element repeatControl = null;	//en caso de que este control pertenesca a un control repeat
	protected DocTree docTree;				//el DocTree que es usado en FirsFrame
	protected DocTable docTable;			//el DocTable que es usado en FirsFrame
	protected XmlDoc xmldoc = null; 		//contiene la estructura principal del archivo XML del formulario
	
	protected Properties properties;		//muestra las propiedades de este control en la jTable
	
	protected JShape shapeSuperior;			//shape superior del JComponent.
	protected JShape shapeInferior;			//shape inferior del JComponent.
	protected JShape shapeIzquierdo;		//shape izquierdo del JComponent.
	protected JShape shapeDerecho;			//shape derecho del JComponent.
	
	protected JEditPanel jEditPanel;		//es el Panel donde esta siendo editado el formulario.
	
	
	/**
	 * Este es el metodo constructor de la clase.
	 */
	public JControl(JEditPanel jEditPanel){
		this.jEditPanel = jEditPanel;
		actionEvent = new JLabel();
		jLabel = new JLabel();
		
		PopupMenu popup = new PopupMenu(this.jEditPanel, this);
		MouseListener popupListener = new control.controls.popupMenu.PopupListener(popup, this);
		
		this.addMouseListener(popupListener);
		jLabel.addMouseListener(popupListener);
		actionEvent.addMouseListener(popupListener);
	}
	
	/**
	 * Este metodo retorna true si el control fue seleccionado (si los shapes 
	 * son visibles).
	 * 
	 * @return	boolean 
	 */
	public boolean isSelected(){
		return visible; 
	}
	
	/**
	 * Este hace que los shapes sean visibles o no, esto indica si el control 
	 * es seleccionado o no.
	 * 
	 * @param	boolean 
	 */
	public void setSelected(boolean valor){
		shapesVisible(valor);
	}
	
	/**
	 * Este metodo hace que los shapes sean visibles o no.
	 * 
	 * @param	value		Es el valor booleano que va a determinar que 
	 * 						los shapes sean visibles o no.
	 */
	protected void shapesVisible(boolean value) {
		shapeSuperior.setVisible(value);
		shapeInferior.setVisible(value);
		shapeIzquierdo.setVisible(value);
		shapeDerecho.setVisible(value);
		visible = value;
	}
	
	/**
	 * Este metodo retorna la propiedad name del control.
	 * 
	 * @return  retorna la propiedad name 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Este metodo retorna la propiedad hint del control.
	 * 
	 * @return  retorna la propiedad hint 
	 */
	public String getHint() {
		return this.hint;
	}
	
	/**
	 * Este metodo retorna la propiedad bind del control.
	 * 
	 * @return  retorna la propiedad bind
	 */
	public String getBind() {
		return this.bind;
	}
	
	/**
	 * Este metodo retorna la propiedad ref del control.
	 * 
	 * @return  retorna la propiedad ref
	 */
	public String getRef() {
		return this.ref;
	}
	
	/**
	 * Este metodo retorna la propiedad model del control.
	 * 
	 * @return  retorna la propiedad model
	 */
	public String getModel() {
		return this.model;
	}
	
	/**
	 * Este metodo retorna la propiedad tabPosition del control.
	 * 
	 * @return  retorna la propiedad tabPosition
	 */
	public String getTabPosition() {
		return this.tabPosition;
	}
	
	/**
	 * Este metodo retorna la propiedad helpText del control.
	 * 
	 * @return  retorna la propiedad helpText
	 */
	public String getHelpText() {
		return this.helpText;
	}
	
	/**
	 * Este metodo setea la propiedad name de este componente.
	 * Setea el Atributo name en el elemento control.
	 * En caso de que sean distintos de null tambien setea 
	 * el texto de jLabel y de jButon
	 * 
	 * @param	name	Valor del nombre del control.
	 */
	public void setName(String name) {
		this.name = name;
		this.control.getAttributes().getNamedItem("name").setNodeValue(name);
		if(jLabel != null)	jLabel.setText(name);
		if(jButton != null)	jButton.setText(name);
	}
	
	/**
	 * Este metodo setea la propiedad hint de este componente.
	 * Tambien setea el Atributo hint en el elemento control.
	 * 
	 * @param	hint	Valor del texto comentario del control. 
	 */
	public void setHint(String hint) {
		this.hint = hint;
		this.control.getAttributes().getNamedItem("hint").setNodeValue(hint);
	}
	
	/**
	 * Este metodo setea la propiedad bind de este componente.
	 * Tambien setea el Atributo bind en el elemento control.
	 * 
	 * @param	bind	Valor del predicado que valida los datos de etste 
	 * 					control en el XFoms Model.  
	 */
	public void setBind(String bind) {
		this.bind = bind;
		this.control.getAttributes().getNamedItem("bind").setNodeValue(bind);
	}
	
	/**
	 * Este metodo setea la propiedad ref de este componente.
	 * Tambien setea el Atributo ref en el elemento control.
	 * 
	 * @param	ref		Valor del enlace de este control con un 
	 * 					campo del XForms Model. 
	 */
	public void setRef(String ref) {
		this.ref = ref;
		this.control.getAttributes().getNamedItem("ref").setNodeValue(ref);
	}
	
	/**
	 * Este metodo setea la propiedad model de este componente.
	 * Tambien setea el Atributo model en el elemento control.
	 * 
	 * @param	model	Valor de la ubicación del XForms Model.  
	 */
	public void setModel(String model) {
		this.model = model;
		this.control.getAttributes().getNamedItem("model").setNodeValue(model);
	}
	
	/**
	 * Este metodo setea la propiedad tabPosition de este componente.
	 * Tambien setea el Atributo tabPosition en el elemento control.
	 * 
	 * @param	tabPosition		Valor de la posición de tabulación del control. 
	 */
	public void setTabPosition(String tabPosition) {
		this.tabPosition = tabPosition;
		this.control.getAttributes().getNamedItem("tabPosition").setNodeValue(tabPosition);
	}
	
	/**
	 * Este metodo setea la propiedad helpText de este componente.
	 * Tambien setea el Atributo helpText en el elemento control.
	 * 
	 * @param	tabPosition		Valor del texto de ayuda del control. 
	 */
	public void setHelpText(String helpText) {
		this.helpText = helpText;
		this.control.getAttributes().getNamedItem("helpText").setNodeValue(helpText);
	}

	/**
	 * Si jTable no estea siendo editada actualiza la tabla con los
	 * datos de este control. En caso contrario despliega un mensaje
	 * de advertencia.
	 */
	public void updateProperties() {
		if(!docTable.getJTable().isEditing()){
			docTable.setJComponent(this);
			properties.setProperties(this);
			properties.update();
			updateControl();
		}else
            JOptionPane.showMessageDialog(this, 
            "Asegurese de que ninguna celda de la tabla 'Propiedades'\n"+
            "este siendo editada.", "Mensaje de Advertencia", 
			JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Este metodo setea de que tipo es el control (input, label, textarea, etc)
	 * y crea los atributos sobre el elemento control que corresponde a este 
	 * control en el documento xmldoc.
	 * 
	 * @param	type	Valor del typo de control XForms.
	 */
	protected void setControlType(String type){
		this.type = type;
		this.control = (Element) docTree.getDocument().createElement(this.type);	
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("name"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("hint"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("bind"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("ref"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("model"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("height"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("width"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("top"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("left"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("tabPosition"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("helpText"));
		if(xmldoc != null)
			xmldoc.addComponent(this.control);
		else
		if(this.repeatControl != null)
			this.repeatControl.appendChild(this.control);
		docTree.getJTree().addNotify();
		docTree.getJTree().updateUI();
		docTree.getJTree().expandRow(1);
		docTree.getJTree().repaint();
	}
	
	/**
	 * Este metodo setea de que tipo es el control (input, label, textarea, etc)
	 * y crea los atributos sobre el elemento control que corresponde a este 
	 * control en el documento xmldoc.
	 */
	public void setControlType(){
		this.control = (Element) docTree.getDocument().createElement(this.type);	
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("name"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("hint"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("bind"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("ref"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("model"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("height"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("width"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("top"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("left"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("tabPosition"));
		this.control.getAttributes().setNamedItem(docTree.getDocument().createAttribute("helpText"));
		if(xmldoc != null)
			xmldoc.addComponent(this.control);
		else
		if(this.repeatControl != null)
			this.repeatControl.appendChild(this.control);
		docTree.getJTree().addNotify();
		docTree.getJTree().updateUI();
		docTree.getJTree().expandRow(1);
		docTree.getJTree().repaint();
	}
	
	/**
	 * Este metodo actualiza los valores de los atributos del elemento control con 
	 * los datos de las variables miebro de la esta clase (name, hint, ...).
	 */
	public void updateControl(){
		this.control.getAttributes().getNamedItem("name").setNodeValue(this.name);
		this.control.getAttributes().getNamedItem("hint").setNodeValue(this.hint);
		this.control.getAttributes().getNamedItem("bind").setNodeValue(this.bind);
		this.control.getAttributes().getNamedItem("ref").setNodeValue(this.ref);
		this.control.getAttributes().getNamedItem("model").setNodeValue(this.model);
		this.control.getAttributes().getNamedItem("height").setNodeValue(Integer.toString(this.getHeight()));
		this.control.getAttributes().getNamedItem("width").setNodeValue(Integer.toString(this.getWidth()));
		this.control.getAttributes().getNamedItem("top").setNodeValue(Integer.toString(this.getY()));
		this.control.getAttributes().getNamedItem("left").setNodeValue(Integer.toString(this.getX()));
		this.control.getAttributes().getNamedItem("tabPosition").setNodeValue(this.tabPosition);
		this.control.getAttributes().getNamedItem("helpText").setNodeValue(this.helpText);
	}
	
	/**
	 * Este metodo retorna el objeto properties.
	 * 
	 * @return retorna el objeto properties de tipo Properties.
	 */
	public Properties getProperties() {
		return this.properties;
	}
	
	/**
	 * Este metodo retorna el Elemento control.
	 * 
	 * @return retorna el elemento control de tipo Element.
	 */
	public Element getElement(){
		return this.control;
	}
	
	/**
	 * Este metodo retorna el JEditPanel al que pertenece.
	 * 
	 * @return JEditPanel.
	 */
	public JEditPanel getJEditPanel(){
		return this.jEditPanel;
	}
	
	/**
	 * Este metodo permite setear el panel en el que se esta editando 
	 * el formulario.
	 * 
	 * @param	JEditPanel
	 */
	public void setJEditPanel(JEditPanel jEditPanel){
		this.jEditPanel = jEditPanel; 
	}
	
	/**
	 * Este metodo permite setear el XmlDoc del formulario. 
	 * el formulario.
	 * 
	 * @param	XmlDoc
	 */
	public void setXmlDoc(XmlDoc xmldoc){
		this.xmldoc = xmldoc; 
		if(xmldoc != null)
			xmldoc.addComponent(this.control);
		docTree.getJTree().addNotify();
		docTree.getJTree().updateUI();
		docTree.getJTree().expandRow(1);
		docTree.getJTree().repaint();
	}
	
	/**
	 * Este medodo permite setear el elemento repeatControl, en caso
	 * de que este control este dibujado dentro de un control repeat.
	 * 
	 * @param Element
	 */
	public void setRepeatControl(Element repeatControl){
		this.repeatControl = repeatControl;
		if(this.repeatControl != null)
			this.repeatControl.appendChild(this.control);
		docTree.getJTree().addNotify();
		docTree.getJTree().updateUI();
		docTree.getJTree().expandRow(1);
		docTree.getJTree().repaint();
	}
	
	/**
	 * Este metodo retorna el XmlDoc del formulario al que pertenece
	 * este control.
	 * 
	 * @return XmlDoc
	 */
	public XmlDoc getXmlDoc(){
		return this.xmldoc;
	}
	
	/**
	 * Este metodo permite setear el tipo de control que.
	 * 
	 * @param	String
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * Este metodo retorna el tipo del control.
	 * 
	 * @return	String
	 */
	public String getType(){
		return this.type;
	}	
	
	/**
	 * Este metodo hace que solo quede seleccionado este control en el 
	 * JEditorPanel.
	 */
	public void selected(){
		this.jEditPanel.setSelected(name);
	}
}

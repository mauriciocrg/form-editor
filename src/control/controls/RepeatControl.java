/*
 * Created on 08-mar-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package control.controls;

import javax.swing.JComponent;

import org.w3c.dom.Element;

import shapes.JRectangle;
import shapes.JShape;
import control.JControl;
import control.controls.properties.Properties;
import control.controls.properties.RepeatControlManager;
import doc.XmlDoc;
import doc.table.DocTable;
import doc.tree.DocTree;
import frames.internalFrame.panel.JEditPanel;

/**
 * @author mrodriguez
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RepeatControl extends JControl {
	
    private final int ALTO_EXTRA = 8;		//alto extra del JComponent para poder visualizar los shapes 
    private final int ANCHO_EXTRA = 8;		//ancho extra del JComponent para poder visualizar los shapes
    private int top;						//contiene la distancia Y del JComponent.
    private int left;						//contiene la distancia X del JComponent.
    private int alto;						//contiene el alto del JComponent.
    private int ancho;						//contiene el ancho del JComponent.
    
    private JRectangle rect;				//rectangulo que rodea al JComponent.
	
	private int x1;							
	private int y1;
	private int x2;
	private int y2;
	private JRectangle rectDraw = new JRectangle(0,0,0,0); /* es el rectangulo que se dibuja dentro del 
															* control.
															*/
	private RepeatControlManager repeatManager;	/* es la clase que permite agregar controles dentro de
												 * este control.	
												 */
	private JEditPanel editPanel;	//es el JEditPanel al que pertenece este control.		

	/**
	 * Este método se el constructor que se utiliza cuando se lee un archivo 
	 * con la información de un formulario.
	 *
	 * @param	    docTable   Es el DocTable que es usado en FirstFrame.
	 * @param	    docTree    Es el DocTree que es usado en FirstFrame.
	 * @param	    xmldoc     Es el XmlDoc que contiene la estructura del 
	 * 						   documento del formulario al que pertenece este 
	 * 						   control.
	 */		
	public RepeatControl(DocTable docTable, DocTree docTree, XmlDoc xmldoc, JEditPanel editPanel){
		super(editPanel);
		this.docTree = docTree;
		this.docTable = docTable;
		this.table = docTable.getTable();
		this.jTable = docTable.getJTable();
		this.editPanel = editPanel;
		repeatManager = new RepeatControlManager(this, editPanel);
		this.xmldoc = xmldoc;
		this.setLayout(null);
		this.initShapes();
		this.initRectangle();
		this.addActionListener(this);
		this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,102,153),5));
		this.add(rectDraw);
		this.properties = new Properties(this.jTable, this.table);
		this.setControlType("repeat");
	}
	
	/**
	 * Este método se el constructor que se utiliza cuando se lee un archivo 
	 * con la información de un formulario.
	 *
	 * @param	    docTable   		Es el DocTable que es usado en FirstFrame.
	 * @param	    docTree    		Es el DocTree que es usado en FirstFrame.
	 * @param	    repeatControl   Es el Element al que pertenece este control.
	 */		
	public RepeatControl(DocTable docTable, DocTree docTree, Element repeatControl, JEditPanel editPanel){
		super(editPanel);
		this.docTree = docTree;
		this.docTable = docTable;
		this.table = docTable.getTable();
		this.jTable = docTable.getJTable();
		this.editPanel = editPanel;
		repeatManager = new RepeatControlManager(this, editPanel);
		this.repeatControl = repeatControl;
		this.setLayout(null);
		this.initShapes();
		this.initRectangle();
		this.addActionListener(this);
		this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,102,153),5));
		this.add(rectDraw);
		this.properties = new Properties(this.jTable, this.table);
		this.setControlType("repeat");
	}
	
	/**
	 * Este método se el constructor que se utiliza cuando se esta editando 
	 * el formulario, cuando el usuario esta dibujando el control.
	 *
	 * @param		control_x  Es el numero que representa a la cantidad de 
	 * 						   controles de este tipo que ya han sido dibujados 
	 * 						   en el formulario -1.
	 * @param		X1		   Indica la coordenada X donde esta el control (Top).
	 * @param		Y1		   Indica la coordenada Y donde esta el control (Left). 						
	 * @param		X2		   Indica el alto del control (Height). 
	 * @param		Y2		   Indica el ancho del control (Weigth).
	 * @param	    docTable   Es el DocTable que es usado en FirstFrame.
	 * @param	    docTree    Es el DocTree que es usado en FirstFrame.
	 * @param	    xmldoc     Es el XmlDoc que contiene la estructura del 
	 * 						   documento del formulario al que pertenece este 
	 * 						   control.
	 */
	public RepeatControl(int control_x, int X1, int Y1, int X2, int Y2, DocTable docTable, 
						 DocTree docTree, XmlDoc xmldoc, JEditPanel editPanel) {
		super(editPanel);
		this.docTree = docTree;
		this.docTable = docTable;
		this.table = docTable.getTable();
		this.jTable = docTable.getJTable();
		this.xmldoc = xmldoc;
		this.editPanel = editPanel;
		repeatManager = new RepeatControlManager(this, editPanel);				
		this.setLayout(null);
		this.addActionListener(this);
		this.setBounds(X1, Y1, (X2 - X1) + ANCHO_EXTRA, (Y2 - Y1) + ALTO_EXTRA);
		this.initShapes();
		this.initRectangle();
		this.properties = new Properties(this.jTable, this.table);
		this.setControlType("repeat");
		if(control_x != 0) this.setName("repeat_"+control_x);
		else this.setName("repeat");
		this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,102,153),5));
		this.updateControl();
		shapesVisible(false);
		this.add(rectDraw);
	}
	
	/**
	 * Este método se el constructor que se utiliza cuando se esta editando 
	 * el formulario, cuando el usuario esta dibujando el control.
	 *
	 * @param		control_x  Es el numero que representa a la cantidad de 
	 * 						   controles de este tipo que ya han sido dibujados 
	 * 						   en el formulario -1.
	 * @param		X1		   Indica la coordenada X donde esta el control (Top).
	 * @param		Y1		   Indica la coordenada Y donde esta el control (Left). 						
	 * @param		X2		   Indica el alto del control (Height). 
	 * @param		Y2		   Indica el ancho del control (Weigth).
	 * @param	    docTable   Es el DocTable que es usado en FirstFrame.
	 * @param	    docTree    Es el DocTree que es usado en FirstFrame.
	 */
	public RepeatControl(int control_x, int X1, int Y1, int X2, int Y2, DocTable docTable, 
						 DocTree docTree, JEditPanel editPanel) {
		super(editPanel);
		this.docTree = docTree;
		this.docTable = docTable;
		this.table = docTable.getTable();
		this.jTable = docTable.getJTable();
		this.editPanel = editPanel;
		repeatManager = new RepeatControlManager(this, editPanel);				
		this.setLayout(null);
		this.addActionListener(this);
		this.setBounds(X1, Y1, (X2 - X1) + ANCHO_EXTRA, (Y2 - Y1) + ALTO_EXTRA);
		this.initShapes();
		this.initRectangle();
		this.properties = new Properties(this.jTable, this.table);
		this.setControlType("repeat");
		if(control_x != 0) this.setName("repeat_"+control_x);
		else this.setName("repeat");
		this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,102,153),5));
		this.updateControl();
		shapesVisible(false);
		this.add(rectDraw);
	}
	
	/**
	 * Este método se el constructor que se utiliza cuando se esta editando 
	 * el formulario, cuando el usuario esta dibujando el control.
	 *
	 * @param		control_x  		Es el numero que representa a la cantidad de 
	 * 						   		controles de este tipo que ya han sido dibujados 
	 * 						   		en el formulario -1.
	 * @param		X1		   		Indica la coordenada X donde esta el control (Top).
	 * @param		Y1		   		Indica la coordenada Y donde esta el control (Left). 						
	 * @param		X2		   		Indica el alto del control (Height). 
	 * @param		Y2		   		Indica el ancho del control (Weigth).
	 * @param	    docTable   		Es el DocTable que es usado en FirstFrame.
	 * @param	    docTree    		Es el DocTree que es usado en FirstFrame.
	 * @param	    repeatControl   Es el Element al que pertenece este control.
	 */
	public RepeatControl(int control_x, int X1, int Y1, int X2, int Y2, DocTable docTable, DocTree docTree, Element repeatControl, JEditPanel editPanel) {
		super(editPanel);
		this.docTree = docTree;
		this.docTable = docTable;
		this.table = docTable.getTable();
		this.jTable = docTable.getJTable();
		this.repeatControl = repeatControl;
		this.editPanel = editPanel;
		repeatManager = new RepeatControlManager(this, editPanel);				
		this.setLayout(null);
		this.addActionListener(this);
		this.setBounds(X1, Y1, (X2 - X1) + ANCHO_EXTRA, (Y2 - Y1) + ALTO_EXTRA);
		this.initShapes();
		this.initRectangle();
		this.properties = new Properties(this.jTable, this.table);
		this.setControlType("repeat");
		if(control_x != 0) this.setName("repeat_"+control_x);
		else this.setName("repeat");
		this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,102,153),5));
		this.updateControl();
		shapesVisible(false);
		this.add(rectDraw);
	}
	
	/**
	 * Este metodo inicializa los Shapes y el Rectangulo que rodean el JComponent.
	 */
	public void initShape_Rectangle(){
		this.initShapes();
		this.initRectangle();
		shapesVisible(false);
    }
	
	/**
	 * Este metodo crea e inicializa el Rectangulo que rodea el JComponent.
	 */
	private void initRectangle() {
		rect = new JRectangle(0,0,0,0);
		rect.setVisible(false);
		this.add(rect, null);
	}

	/**
	 * Este metodo crea e inicializa los shapes que van a rodear al
	 * JComponent.
	 */
	private void initShapes() {
		shapeSuperior  = new JShape();
		shapeInferior  = new JShape();
		shapeIzquierdo = new JShape();
		shapeDerecho   = new JShape();

		this.setLocationShapes();
		
		this.add(shapeSuperior, null);
		this.add(shapeInferior, null);
		this.add(shapeIzquierdo, null);
		this.add(shapeDerecho, null);
		this.addShapeDerechoActionListener(shapeDerecho);
		this.addShapeIzquierdoActionListener(shapeIzquierdo);
		this.addShapeInferiorActionListener(shapeInferior);
		this.addShapeSuperiorActionListener(shapeSuperior);
	}
	
	/**
	 * Este metodo agrega funcionalidad a los eventos de este JComponent:
	 * 	Cuando es arrastrado lo redibuja en nuevas coordenadas. 
	 * 	Cuando se le hace clik, visualiza los shapes.
	 * 
	 * @param 	control		Es pasado por referencia el control que va a 
	 * 						ser dotado con la funcionalidad descrita.
	 */
	private void addActionListener(JComponent control){
		control.addMouseListener(new java.awt.event.MouseAdapter() { 
			public void mouseExited(java.awt.event.MouseEvent e) {
				e.consume();
				//setCursor(Cursor.DEFAULT_CURSOR);
			} 
			public void mouseEntered(java.awt.event.MouseEvent e) {
				e.consume();
				//setCursor(Cursor.MOVE_CURSOR);
			}
			public void mouseReleased(java.awt.event.MouseEvent e) {    
				e.consume();
				updateProperties();
				if(visible)
					repeatManager.mouseReleased(e.getX(), e.getY());
			}   
			public void mousePressed(java.awt.event.MouseEvent e) {    
				e.consume();
				ancho = getWidth();
				alto = getHeight();
				if(visible)
					repeatManager.mousePressed();
			}
			public void mouseClicked(java.awt.event.MouseEvent e) {
				e.consume();
				visible = ! visible;
				if(visible)	selected();
				shapesVisible(visible);
				ancho = getWidth();
				alto = getHeight();
			}
		});
		control.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() { 
			public void mouseMoved(java.awt.event.MouseEvent e) {    
				e.consume();
				if(visible)
					repeatManager.mouseMoved(e.getX(), e.getY());
			} 
			public void mouseDragged(java.awt.event.MouseEvent e) {
				e.consume();
				if(visible)
					repeatManager.mouseDragged(e.getX(), e.getY());
				else
					setLocation((getX() + e.getX())-(ancho/2), (getY() + e.getY())-(alto/2));
					
			}
		});
	}
	
	/**
	 * Este metodo agrega funcionalidad a los eventos de este JComponent:
	 * Cuando es arrastrado lo redibuja en nuevas coordenadas. 
	 * Cuando se le hace clik, visualiza los shapes.
	 * 
	 * @param 	control		Es pasado por referencia el control que va a 
	 * 						ser dotado con la funcionalidad descrita.
	 */
	public void setLocationRect(int x1, int y1){
		this.x1 = x1;
		this.y1 = y1;
	}
	
	/**
	 * Este metod permite redimencionar el rectangulo.
	 * 
	 * @param x2 indica el ancho del rectangulo.
	 * @param y2 indica el alto del rectangulo.
	 */
	public void setSizeRect(int x2, int y2){
		this.x2 = x2;
		this.y2 = y2;
	}
	
	/**
	 * Este netodo retorna el rectangulo.
	 * 
	 * @return JRectangle
	 */
	public JRectangle getRectDraw(){
		return this.rectDraw;
	}
	
	/**
	 * Este metodo determina la ubicación de los shapes que rodean
	 * al JComponent.
	 */
	private void setLocationShapes()	{
		//Shape centro superior
		shapeSuperior.setLocation((getWidth() - shapeSuperior.getWidth()) / 2, 0);
		//Shape centro inferior
		shapeInferior.setLocation((getWidth() - shapeInferior.getWidth()) / 2, getHeight() - ALTO_EXTRA / 2);
		//Shape izquierda centro
		shapeIzquierdo.setLocation(0, (getHeight() - shapeIzquierdo.getHeight()) / 2);
		//Shape deracha centro
		shapeDerecho.setLocation(getWidth() - (ALTO_EXTRA / 2), (getHeight() - shapeDerecho.getHeight()) / 2);
	}
	
	/**
	 * Este metodo redibuja el JComponent según las nuevas
	 * dimenciones. 
	 */
	public void reDraw() {
		rect.setVisible(false);
		setLocationShapes();
	}
	
	/**
	 * Este metodo retorna el JControl del cual hereda. 
	 * 
	 * @return	JControl	retorna este control casteado como JControl,
	 * 						del cual hereda. 
	 */
	public JControl getControl() {
		return (JControl) this;
	}
	
	/**
	 * Este metodo agrega funcionalidad a el Shape Derecho:
	 * 	Cuando es arrastrado redimenciona el JComponet. 
	 * 	Cuando es precionado se hace visible el Rectangulo que 
	 * 	rodea al JComponent.
	 * 
	 * @param 	control		Es pasado por referencia el control que va a 
	 * 						ser dotado con la funcionalidad descrita.
	 */
	private void addShapeDerechoActionListener(JComponent control) {
		control.addMouseListener(new java.awt.event.MouseAdapter() {   
			public void mouseReleased(java.awt.event.MouseEvent e) {    
				e.consume();
				reDraw();
				ancho = getWidth();
				properties.setProperties(getControl());
				properties.update();
			}   
			public void mousePressed(java.awt.event.MouseEvent e) {    
				e.consume();
				rect.setVisible(true);
			}
		});
		control.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent e) {
				e.consume();
	        	setSize(e.getX() + ancho, getHeight());
	        	rect.reDraw(0, 0, (e.getX() + ancho), getHeight());
	        }
		});
	}
	
	/**
	 * Este metodo agrega funcionalidad a el Shape Izquierdo:
	 * 	Cuando es arrastrado redimenciona el JComponet. 
	 * 	Cuando es precionado se hace visible el Rectangulo que 
	 * 	rodea al JComponent.
	 * 
	 *	@param 	control		Es pasado por referencia el control que va a 
	 * 						ser dotado con la funcionalidad descrita. 
	 */
	private void addShapeIzquierdoActionListener(JComponent control) {
		control.addMouseListener(new java.awt.event.MouseAdapter() {   
			public void mouseReleased(java.awt.event.MouseEvent e) {    
				e.consume();
				reDraw();
				ancho = getWidth();
				properties.setProperties(getControl());
				properties.update();
			}   
			public void mousePressed(java.awt.event.MouseEvent e) {    
				e.consume();
				left = (int) getLocation().getX();
				rect.setVisible(true);
			}
		});
		control.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent e) {
				e.consume();
				
				setSize((left - ((int) getLocation().getX())) + ancho , getHeight());
	        	rect.reDraw(0, 0,(left - ((int) getLocation().getX())) + ancho,  getHeight());
	        	setLocation(getX() + e.getX(), getY());
	        }
		});
	}
	
	/**
	 * Este metodo agrega funcionalidad a el Shape Inferior:
	 * 	Cuando es arrastrado redimenciona el JComponet. 
	 * 	Cuando es precionado se hace visible el Rectangulo que 
	 * 	rodea al JComponent.
	 * 
	 *	@param 	control		Es pasado por referencia el control que va a 
	 * 						ser dotado con la funcionalidad descrita. 
	 */
	private void addShapeInferiorActionListener(JComponent control) {
		control.addMouseListener(new java.awt.event.MouseAdapter() {   
			public void mouseReleased(java.awt.event.MouseEvent e) {    
				rect.setVisible(false);
				reDraw();
				properties.setProperties(getControl());
				properties.update();
			}   
			public void mousePressed(java.awt.event.MouseEvent e) {    
				e.consume();
				alto = getHeight();
				rect.setVisible(true);
			}
		});
		control.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent e) {
				e.consume();
				
				setSize(ancho, e.getY() + alto);
				rect.reDraw(0, 0, ancho, e.getY() + alto);
	        }
		});
	}
	
	/**
	 * Este metodo agrega funcionalidad a el Shape Superior:
	 * 	Cuando es arrastrado redimenciona el JComponet. 
	 * 	Cuando es precionado se hace visible el Rectangulo que 
	 * 	rodea al JComponent.
	 * 
	 *	@param 	control		Es pasado por referencia el control que va a 
	 * 						ser dotado con la funcionalidad descrita. 
	 */
	private void addShapeSuperiorActionListener(JComponent control) {
		control.addMouseListener(new java.awt.event.MouseAdapter() {   
			public void mouseReleased(java.awt.event.MouseEvent e) {    
				e.consume();
				reDraw();
				properties.setProperties(getControl());
				properties.update();
			}   
			public void mousePressed(java.awt.event.MouseEvent e) {    
				e.consume();
				top = (int) getLocation().getY();
				alto = getHeight();
				rect.setVisible(true);
			}
		});
		control.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent e) {
				e.consume();
				setSize(ancho, alto + ((top - ((int) getLocation().getY()))));
	        	rect.reDraw(0, 0, ancho,  alto + ((top - ((int) getLocation().getY()))));
	        	setLocation(getX(),getY() + e.getY());
	        }
		});
	}
	/**
	 * Este metodo retorna el RepeatControlManager del control
	 * 
	 * @return RepeatControlManager
	 */
	public RepeatControlManager getRepeatManager(){
		return this.repeatManager;
	}
}

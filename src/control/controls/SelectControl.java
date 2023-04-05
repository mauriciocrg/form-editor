/* 
* nombre del archivo: SelectControl.java
*  
* @Fecha Generaci�n: 	D�a/Mes/ A�o 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripci�n:		Implementa las caracteristicas y propiedades de un
*					control XForm Select. 
*@Fecha Actualizaci�n:	25/01/2005
*@Autor Actualizaci�n: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) a�o Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package control.controls;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.w3c.dom.Element;

import control.JControl;
import control.controls.properties.Properties;

import doc.XmlDoc;
import doc.table.DocTable;
import doc.tree.DocTree;
import frames.internalFrame.panel.JEditPanel;

import shapes.JRectangle;
import shapes.JShape;

/*
 * Esta clase extiende de JControl ycontiene las caracteristicas y propiedades de 
 * un control XForm Select.  
 *
 * @Author      Mauricio Rodriguez
 * @Versi�n     1.0, 25/01/2005
 */

public class SelectControl extends JControl {
	
	private JList jList;				//representa a un control select.
	private JScrollPane jScrollPane;	//contiene al jList.
	
	private final int ALTO = 20;			//alto predeterminado del control.
    private final int ALTO_EXTRA = 8;		//alto extra del JComponent para poder visualizar los shapes 
    private final int ANCHO_EXTRA = 8;		//ancho extra del JComponent para poder visualizar los shapes
    private int top;						//contiene la distancia Y del JComponent.
    private int left;						//contiene la distancia X del JComponent.
    private int alto;						//contiene el alto del JComponent.
    private int ancho;						//contiene el ancho del JComponent.
    
    private JRectangle rect;				//rectangulo que rodea al JComponent.
	
	/**
	 * Este m�todo se el constructor que se utiliza cuando se lee un archivo 
	 * con la informaci�n de un formulario.
	 *
	 * @param	    docTable   Es el DocTable que es usado en FirstFrame.
	 * @param	    docTree    Es el DocTree que es usado en FirstFrame.
	 * @param	    xmldoc     Es el XmlDoc que contiene la estructura del 
	 * 						   documento del formulario al que pertenece este 
	 * 						   control.
	 */	
	public SelectControl(DocTable docTable, DocTree docTree, XmlDoc xmldoc, JEditPanel editPanel) {
		super(editPanel);
    	this.docTree = docTree;
    	this.docTable = docTable;
    	this.table = docTable.getTable();
		this.jTable = docTable.getJTable();
		this.xmldoc = xmldoc;
		jList = new JList();
		jScrollPane = new JScrollPane();
		actionEvent.setLocation(ANCHO_EXTRA / 2, ALTO_EXTRA / 2);
		jScrollPane.setLocation(ANCHO_EXTRA / 2, ALTO_EXTRA / 2);
		jScrollPane.setViewportView(jList);
		this.addActionListener(actionEvent);
		this.setLayout(null);
		this.add(actionEvent, null);
		this.add(jScrollPane, null);
		this.properties = new Properties(this.jTable,this.table);
		this.setControlType("select");
    }

	/**
	 * Este m�todo se el constructor que se utiliza cuando se lee un archivo 
	 * con la informaci�n de un formulario.
	 *
	 * @param	    docTable   		Es el DocTable que es usado en FirstFrame.
	 * @param	    docTree   		Es el DocTree que es usado en FirstFrame.
	 * @param	    repeatControl   Es el Element al que pertenece este control.
	 */	
	public SelectControl(DocTable docTable, DocTree docTree, Element repeatControl, JEditPanel editPanel) {
		super(editPanel);
    	this.docTree = docTree;
    	this.docTable = docTable;
    	this.table = docTable.getTable();
		this.jTable = docTable.getJTable();
		this.repeatControl = repeatControl;
		jList = new JList();
		jScrollPane = new JScrollPane();
		actionEvent.setLocation(ANCHO_EXTRA / 2, ALTO_EXTRA / 2);
		jScrollPane.setLocation(ANCHO_EXTRA / 2, ALTO_EXTRA / 2);
		jScrollPane.setViewportView(jList);
		this.addActionListener(actionEvent);
		this.setLayout(null);
		this.add(actionEvent, null);
		this.add(jScrollPane, null);
		this.properties = new Properties(this.jTable,this.table);
		this.setControlType("select");
    }
	
	/**
	 * Este m�todo se el constructor que se utiliza cuando se esta editando 
	 * el formulario, cuando el usuario esta dibujando el control.
	 *
	 * @param		CONTROL_X  Es el numero que representa a la cantidad de 
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
    public SelectControl(int CONTROL_X, int X1, int Y1, int X2, int Y2, DocTable docTable, 
    		DocTree docTree, XmlDoc xmldoc, JEditPanel editPanel) {
		super(editPanel);
    	this.docTree = docTree;
    	this.docTable = docTable;
    	this.table = docTable.getTable();
		this.jTable = docTable.getJTable();
		this.xmldoc = xmldoc;
		jList = new JList();
		jScrollPane = new JScrollPane();
		actionEvent.setBounds(ANCHO_EXTRA / 2, ALTO_EXTRA / 2, X2 - X1, Y2 - Y1);
		jScrollPane.setBounds(ANCHO_EXTRA / 2, ALTO_EXTRA / 2, X2 - X1, Y2 - Y1);
		jScrollPane.setViewportView(jList);
		this.addActionListener(actionEvent);
		this.setLayout(null);
		this.setBounds(X1, Y1, (X2 - X1) + ANCHO_EXTRA, (Y2 - Y1) + ALTO_EXTRA);
		this.add(actionEvent, null);
		this.add(jScrollPane, null);
		this.initShapes();
		this.initRectangle();
		this.properties = new Properties(this.jTable,this.table);
		this.setControlType("select");
		if(CONTROL_X != 0) this.setName("Select_"+CONTROL_X);
		else this.setName("Select");
		this.updateControl();
		shapesVisible(false);
    }
    
    /**
	 * Este m�todo se el constructor que se utiliza cuando se esta editando 
	 * el formulario, cuando el usuario esta dibujando el control.
	 *
	 * @param		CONTROL_X  Es el numero que representa a la cantidad de 
	 * 						   controles de este tipo que ya han sido dibujados 
	 * 						   en el formulario -1.
	 * @param		X1		   Indica la coordenada X donde esta el control (Top).
	 * @param		Y1		   Indica la coordenada Y donde esta el control (Left). 						
	 * @param		X2		   Indica el alto del control (Height). 
	 * @param		Y2		   Indica el ancho del control (Weigth).
	 * @param	    docTable   Es el DocTable que es usado en FirstFrame.
	 * @param	    docTree    Es el DocTree que es usado en FirstFrame.
	 */
    public SelectControl(int CONTROL_X, int X1, int Y1, int X2, int Y2, DocTable docTable, 
    		DocTree docTree, JEditPanel editPanel) {
		super(editPanel);
    	this.docTree = docTree;
    	this.docTable = docTable;
    	this.table = docTable.getTable();
		this.jTable = docTable.getJTable();
		jList = new JList();
		jScrollPane = new JScrollPane();
		actionEvent.setBounds(ANCHO_EXTRA / 2, ALTO_EXTRA / 2, X2 - X1, Y2 - Y1);
		jScrollPane.setBounds(ANCHO_EXTRA / 2, ALTO_EXTRA / 2, X2 - X1, Y2 - Y1);
		jScrollPane.setViewportView(jList);
		this.addActionListener(actionEvent);
		this.setLayout(null);
		this.setBounds(X1, Y1, (X2 - X1) + ANCHO_EXTRA, (Y2 - Y1) + ALTO_EXTRA);
		this.add(actionEvent, null);
		this.add(jScrollPane, null);
		this.initShapes();
		this.initRectangle();
		this.properties = new Properties(this.jTable,this.table);
		this.setControlType("select");
		if(CONTROL_X != 0) this.setName("Select_"+CONTROL_X);
		else this.setName("Select");
		this.updateControl();
		shapesVisible(false);
    }
    
    /**
	 * Este m�todo se el constructor que se utiliza cuando se esta editando 
	 * el formulario, cuando el usuario esta dibujando el control.
	 *
	 * @param		CONTROL_X  		Es el numero que representa a la cantidad de 
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
    public SelectControl(int CONTROL_X, int X1, int Y1, int X2, int Y2, DocTable docTable, 
    		DocTree docTree, Element repeatControl, JEditPanel editPanel) {
		super(editPanel);
    	this.docTree = docTree;
    	this.docTable = docTable;
    	this.table = docTable.getTable();
		this.jTable = docTable.getJTable();
		this.repeatControl = repeatControl;
		jList = new JList();
		jScrollPane = new JScrollPane();
		actionEvent.setBounds(ANCHO_EXTRA / 2, ALTO_EXTRA / 2, X2 - X1, Y2 - Y1);
		jScrollPane.setBounds(ANCHO_EXTRA / 2, ALTO_EXTRA / 2, X2 - X1, Y2 - Y1);
		jScrollPane.setViewportView(jList);
		this.addActionListener(actionEvent);
		this.setLayout(null);
		this.setBounds(X1, Y1, (X2 - X1) + ANCHO_EXTRA, (Y2 - Y1) + ALTO_EXTRA);
		this.add(actionEvent, null);
		this.add(jScrollPane, null);
		this.initShapes();
		this.initRectangle();
		this.properties = new Properties(this.jTable,this.table);
		this.setControlType("select");
		if(CONTROL_X != 0) this.setName("Select_"+CONTROL_X);
		else this.setName("Select");
		this.updateControl();
		shapesVisible(false);
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
	 * Este metodo determina la ubicaci�n de los shapes que rodean
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
	 * Este metodo redibuja el JComponent seg�n las nuevas
	 * dimenciones. 
	 */
	public void reDraw() {
		rect.setVisible(false);
		jScrollPane.setSize(getWidth() - ANCHO_EXTRA, getHeight() - ALTO_EXTRA);
		jList.setSize(getWidth() - ANCHO_EXTRA, getHeight() - ALTO_EXTRA);
		actionEvent.setSize(getWidth() - ANCHO_EXTRA, getHeight() - ALTO_EXTRA);
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
			}   
			public void mousePressed(java.awt.event.MouseEvent e) {    
				e.consume();
				ancho = getWidth();
				alto = getHeight();
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
			public void mouseDragged(java.awt.event.MouseEvent e) {
				e.consume();
				setLocation((getX() + e.getX())-(ancho/2), (getY() + e.getY())-(alto/2));
			}
		});
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
}

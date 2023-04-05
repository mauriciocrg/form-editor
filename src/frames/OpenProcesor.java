/* 
* nombre del archivo: OpenProcesor.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		A partir de un objeto Document reconstruye un 
*					Formulario XForm, en un jEditPanel.
*@Fecha Actualización:	25/01/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package frames;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
import frames.internalFrame.JInternalEditFrame;

/*
 * Esta clase a partir de un objeto Document reconstruye un 
 * Formulario XForm, en un jEditPanel.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
 */

public class OpenProcesor {
	
	private Document document = null;	// Contiene el documento del Formulario que esta siendo editado.
	private FirstFrame jFrame = null;	// Instancia de la Ventana Principal.
	private XmlDoc xmldoc = null;		// Contiene el primer nodo dl Document.
	
	private JInternalEditFrame jInternalEditFrame = null;	/* Es el JInternalFrame en el que va a se editara 
															 * el formulario cargado.*/
	/**
	 * Este metodo es el constructor de la clase, inicializa variables 
	 * miembro, reconstruye el formulario a partir de el Document y luego
	 * agrega el JInternalEditFrame en el FirstFrame que contiene 
	 * el formulario.
	 * 
	 * @param	Document
	 * @param	FirstFrame
	 */
	public OpenProcesor(Document document,FirstFrame jFrame){
		this.document = document;
		this.jFrame = jFrame;
		this.IterateConstruction();
		this.jFrame.addJInternalEditFrame(this.jInternalEditFrame);
	}
	
	/**
	 * Este metodo pregunta si el primer nodo de Document es de tipo
	 * form, si lo es lo procesa, en otro caso despliega un mensaje de 
	 * error. 
	 */
	private void IterateConstruction(){
		if(document.getFirstChild().getNodeName().equals("form"))
			this.Procesar();
		else
			errorMesagge();
	}
	
	/**
	 * Este metodo se encarga de procesar el primer nodo de Document, primero pide
	 * el atributo name de dicho nodo, si no existe retorna un mensaje de error,  
	 * luego a mdedida que recorre sus nodos hijo va generando el formulario. Si 
	 * encuentra un nodo no identificado, retorna un mensaje de advertencia y sigue 
	 * con el procesado.
	 */
	private void Procesar(){
		String name ="";
		
		if(document.getFirstChild().getAttributes().getNamedItem("name") != null) {
			
			xmldoc = new XmlDoc(document.getFirstChild().getAttributes().getNamedItem("name").getNodeValue(), 
								 this.jFrame.getDocTree().getDocument());
			
			if(this.jFrame.getDocTree().getDocument().getFirstChild() != null) 
				this.jFrame.getDocTree().getDocument().removeChild(this.jFrame.getDocTree().getDocument().getFirstChild());
			
			this.jFrame.getDocTree().getDocument().appendChild(xmldoc.getXml());
			this.jInternalEditFrame = new JInternalEditFrame(this.jFrame.getDocTable(), 
														   	this.jFrame.getDocTree(), 
															xmldoc, 
															this.jFrame);
			
			int size = document.getFirstChild().getChildNodes().getLength();
			for(int i = 0; i<size; i++){
				if(document.getFirstChild().getChildNodes().item(i).getNodeName().equals("input")){
					InputControl ic = new InputControl(this.jFrame.getDocTable(), 
						   								 this.jFrame.getDocTree(),  
														 xmldoc,
														 this.jInternalEditFrame.getJEditPanel());
					this.setProperties(document.getFirstChild().getChildNodes().item(i),
									   ic.getControl());
					ic.initShape_Rectangle();
					this.jInternalEditFrame.getJEditPanel().addControl(ic);
					ic.reDraw();
				}else
				if(document.getFirstChild().getChildNodes().item(i).getNodeName().equals("label")){
					LabelControl lc = new LabelControl(this.jFrame.getDocTable(), 
								 						 this.jFrame.getDocTree(),  
														 xmldoc,
														 this.jInternalEditFrame.getJEditPanel());
					this.setProperties(document.getFirstChild().getChildNodes().item(i),
									   lc.getControl());
					lc.initShape_Rectangle();
					this.jInternalEditFrame.getJEditPanel().addControl(lc);
					lc.reDraw();
				}else			
				if(document.getFirstChild().getChildNodes().item(i).getNodeName().equals("output")){
					OutputControl oc = new OutputControl(this.jFrame.getDocTable(), 
	 						 							   this.jFrame.getDocTree(),  
														   xmldoc,
														   this.jInternalEditFrame.getJEditPanel());
					this.setProperties(document.getFirstChild().getChildNodes().item(i),
									   oc.getControl());
					oc.initShape_Rectangle();
					this.jInternalEditFrame.getJEditPanel().addControl(oc);
					oc.reDraw();
				}else
				if(document.getFirstChild().getChildNodes().item(i).getNodeName().equals("upload")){
					UploadControl uc = new UploadControl(this.jFrame.getDocTable(), 
							   							   this.jFrame.getDocTree(),  
														   xmldoc,
														   this.jInternalEditFrame.getJEditPanel());
					this.setProperties(document.getFirstChild().getChildNodes().item(i),
									   uc.getControl());
					uc.initShape_Rectangle();
					this.jInternalEditFrame.getJEditPanel().addControl(uc);
					uc.reDraw();
				}else
				if(document.getFirstChild().getChildNodes().item(i).getNodeName().equals("secret")){
					SecretControl sc = new SecretControl(this.jFrame.getDocTable(), 
							   							   this.jFrame.getDocTree(),  
														   xmldoc,
														   this.jInternalEditFrame.getJEditPanel());
					this.setProperties(document.getFirstChild().getChildNodes().item(i),
									   sc.getControl());
					sc.initShape_Rectangle();
					this.jInternalEditFrame.getJEditPanel().addControl(sc);
					sc.reDraw();
				}else			
				if(document.getFirstChild().getChildNodes().item(i).getNodeName().equals("textArea")){
					TextAreaControl tc = new TextAreaControl(this.jFrame.getDocTable(), 
															   this.jFrame.getDocTree(),  
															   xmldoc,
															   this.jInternalEditFrame.getJEditPanel());
					this.setProperties(document.getFirstChild().getChildNodes().item(i),
									   tc.getControl());
					tc.initShape_Rectangle();
					this.jInternalEditFrame.getJEditPanel().addControl(tc);
					tc.reDraw();
				}else			
				if(document.getFirstChild().getChildNodes().item(i).getNodeName().equals("select")){
					SelectControl sc = new SelectControl(this.jFrame.getDocTable(), 
							   							   this.jFrame.getDocTree(),  
														   xmldoc,
														   this.jInternalEditFrame.getJEditPanel());
					this.setProperties(document.getFirstChild().getChildNodes().item(i),
									   sc.getControl());
					sc.initShape_Rectangle();
					this.jInternalEditFrame.getJEditPanel().addControl(sc);
					sc.reDraw();
				}else						
				if(document.getFirstChild().getChildNodes().item(i).getNodeName().equals("select1")){
					Select1Control s1c = new Select1Control(this.jFrame.getDocTable(), 
							   								 this.jFrame.getDocTree(),  
															 xmldoc,
															 this.jInternalEditFrame.getJEditPanel());
					this.setProperties(document.getFirstChild().getChildNodes().item(i),
									   s1c.getControl());
					s1c.initShape_Rectangle();
					this.jInternalEditFrame.getJEditPanel().addControl(s1c);
					s1c.reDraw();
				}else						
				if(document.getFirstChild().getChildNodes().item(i).getNodeName().equals("submit")){
					SubmitControl sc = new SubmitControl(this.jFrame.getDocTable(), 
							   							   this.jFrame.getDocTree(),  
														   xmldoc,
														   this.jInternalEditFrame.getJEditPanel());
					this.setProperties(document.getFirstChild().getChildNodes().item(i),
									   sc.getControl());
					sc.initShape_Rectangle();
					this.jInternalEditFrame.getJEditPanel().addControl(sc);
					sc.reDraw();
				}else
				if(document.getFirstChild().getChildNodes().item(i).getNodeName().equals("trigger")){
					TriggerControl tc = new TriggerControl(this.jFrame.getDocTable(), 
															this.jFrame.getDocTree(),  
															xmldoc,
															this.jInternalEditFrame.getJEditPanel());
					this.setProperties(document.getFirstChild().getChildNodes().item(i),
									   tc.getControl());
					tc.initShape_Rectangle();
					this.jInternalEditFrame.getJEditPanel().addControl(tc);
					tc.reDraw();
				}else										
				if(document.getFirstChild().getChildNodes().item(i).getNodeName().equals("range")){
					RangeControl rc = new RangeControl(this.jFrame.getDocTable(), 
							 							this.jFrame.getDocTree(),  
														xmldoc,
														this.jInternalEditFrame.getJEditPanel());
					this.setProperties(document.getFirstChild().getChildNodes().item(i),
									   rc.getControl());
					rc.initShape_Rectangle();
					this.jInternalEditFrame.getJEditPanel().addControl(rc);
					rc.reDraw();
				}else
				if(document.getFirstChild().getChildNodes().item(i).getNodeName().equals("repeat")){
					RepeatControl rec = new RepeatControl(this.jFrame.getDocTable(), 
														 this.jFrame.getDocTree(),  
														 xmldoc,
														 this.jInternalEditFrame.getJEditPanel());
					this.setProperties(document.getFirstChild().getChildNodes().item(i),
									   rec.getControl());
					rec.initShape_Rectangle();
					this.jInternalEditFrame.getJEditPanel().addControl(rec);
					rec.reDraw();
					if(document.getFirstChild().getChildNodes().item(i).hasChildNodes())
						ProcesarRepeatControl(document.getFirstChild().getChildNodes().item(i).getChildNodes(), rec);
				}else
					warningMessagge();
			}
		}else
			errorMesagge();
	}
	
	/**
	 * Este metodo se encarga de procesar los nodos pertenecientes a un control repeat,
	 * primero pide el atributo name de cada nodo, si no existe retorna un mensaje de error,  
	 * luego a mdedida que recorre sus nodos hijo va generando el formulario. Si 
	 * encuentra un nodo no identificado, retorna un mensaje de advertencia y sigue 
	 * con el procesado.
	 */
	private void ProcesarRepeatControl(NodeList nodeList, RepeatControl repeatControl){
		for(int i = 0; i<nodeList.getLength(); i++){
			if(nodeList.item(i).getNodeName().equals("input")){
				InputControl ic = new InputControl(this.jFrame.getDocTable(), 
					  							   this.jFrame.getDocTree(),  
												   repeatControl.getElement(),
												   this.jInternalEditFrame.getJEditPanel());
				this.setProperties(nodeList.item(i),
								   ic.getControl());
				ic.initShape_Rectangle();
				ic.setJEditPanel(this.jInternalEditFrame.getJEditPanel());
				repeatControl.add(ic);
				ic.reDraw();
			}else
			if(nodeList.item(i).getNodeName().equals("label")){
				LabelControl lc = new LabelControl(this.jFrame.getDocTable(), 
												   this.jFrame.getDocTree(),  
												   repeatControl.getElement(),
												   this.jInternalEditFrame.getJEditPanel());
				this.setProperties(nodeList.item(i),
								   lc.getControl());
				lc.initShape_Rectangle();
				lc.setJEditPanel(this.jInternalEditFrame.getJEditPanel());
				repeatControl.add(lc);
				lc.reDraw();
			}else			
			if(nodeList.item(i).getNodeName().equals("output")){
				OutputControl oc = new OutputControl(this.jFrame.getDocTable(), 
 						 							 this.jFrame.getDocTree(),  
 						 							 repeatControl.getElement(),
													 this.jInternalEditFrame.getJEditPanel());
				this.setProperties(nodeList.item(i),
								   oc.getControl());
				oc.initShape_Rectangle();
				oc.setJEditPanel(this.jInternalEditFrame.getJEditPanel());
				repeatControl.add(oc);
				oc.reDraw();
			}else
			if(nodeList.item(i).getNodeName().equals("upload")){
				UploadControl uc = new UploadControl(this.jFrame.getDocTable(), 
						   							 this.jFrame.getDocTree(),  
						   							 repeatControl.getElement(),
													 this.jInternalEditFrame.getJEditPanel());
				this.setProperties(nodeList.item(i),
								   uc.getControl());
				uc.initShape_Rectangle();
				uc.setJEditPanel(this.jInternalEditFrame.getJEditPanel());
				repeatControl.add(uc);
				uc.reDraw();
			}else
			if(nodeList.item(i).getNodeName().equals("secret")){
				SecretControl sc = new SecretControl(this.jFrame.getDocTable(), 
						   							 this.jFrame.getDocTree(),  
						   							 repeatControl.getElement(),
													 this.jInternalEditFrame.getJEditPanel());
				this.setProperties(nodeList.item(i),
								   sc.getControl());
				sc.initShape_Rectangle();
				sc.setJEditPanel(this.jInternalEditFrame.getJEditPanel());
				repeatControl.add(sc);
				sc.reDraw();
			}else			
			if(nodeList.item(i).getNodeName().equals("textArea")){
				TextAreaControl tc = new TextAreaControl(this.jFrame.getDocTable(), 
														 this.jFrame.getDocTree(),  
														 repeatControl.getElement(),
														 this.jInternalEditFrame.getJEditPanel());
				this.setProperties(nodeList.item(i),
								   tc.getControl());
				tc.initShape_Rectangle();
				tc.setJEditPanel(this.jInternalEditFrame.getJEditPanel());
				repeatControl.add(tc);
				tc.reDraw();
			}else			
			if(nodeList.item(i).getNodeName().equals("select")){
				SelectControl sc = new SelectControl(this.jFrame.getDocTable(), 
						   							 this.jFrame.getDocTree(),  
						   							 repeatControl.getElement(),
													 this.jInternalEditFrame.getJEditPanel());
				this.setProperties(nodeList.item(i),
								   sc.getControl());
				sc.initShape_Rectangle();
				sc.setJEditPanel(this.jInternalEditFrame.getJEditPanel());
				repeatControl.add(sc);
				sc.reDraw();
			}else						
			if(nodeList.item(i).getNodeName().equals("select1")){
				Select1Control s1c = new Select1Control(this.jFrame.getDocTable(), 
						   								this.jFrame.getDocTree(),  
						   								repeatControl.getElement(),
														this.jInternalEditFrame.getJEditPanel());
				this.setProperties(nodeList.item(i),
								   s1c.getControl());
				s1c.initShape_Rectangle();
				s1c.setJEditPanel(this.jInternalEditFrame.getJEditPanel());
				repeatControl.add(s1c);
				s1c.reDraw();
			}else						
			if(nodeList.item(i).getNodeName().equals("submit")){
				SubmitControl sc = new SubmitControl(this.jFrame.getDocTable(), 
						   							 this.jFrame.getDocTree(),  
						   							 repeatControl.getElement(),
													 this.jInternalEditFrame.getJEditPanel());
				this.setProperties(nodeList.item(i),
								   sc.getControl());
				sc.initShape_Rectangle();
				sc.setJEditPanel(this.jInternalEditFrame.getJEditPanel());
				repeatControl.add(sc);
				sc.reDraw();
			}else
			if(nodeList.item(i).getNodeName().equals("trigger")){
				TriggerControl tc = new TriggerControl(this.jFrame.getDocTable(), 
													   this.jFrame.getDocTree(),  
													   repeatControl.getElement(),
													   this.jInternalEditFrame.getJEditPanel());
				this.setProperties(nodeList.item(i),
								   tc.getControl());
				tc.initShape_Rectangle();
				tc.setJEditPanel(this.jInternalEditFrame.getJEditPanel());
				repeatControl.add(tc);
				tc.reDraw();
			}else										
			if(nodeList.item(i).getNodeName().equals("range")){
				RangeControl rc = new RangeControl(this.jFrame.getDocTable(), 
						 						   this.jFrame.getDocTree(),  
						 						   repeatControl.getElement(),
												   this.jInternalEditFrame.getJEditPanel());
				this.setProperties(nodeList.item(i),
								   rc.getControl());
				rc.initShape_Rectangle();
				rc.setJEditPanel(this.jInternalEditFrame.getJEditPanel());
				repeatControl.add(rc);
				rc.reDraw();
			}else
			if(nodeList.item(i).getNodeName().equals("repeat")){
				RepeatControl rec = new RepeatControl(this.jFrame.getDocTable(), 
													  this.jFrame.getDocTree(),  
													  repeatControl.getElement(),
													  this.jInternalEditFrame.getJEditPanel());
				this.setProperties(nodeList.item(i),
								   rec.getControl());
				rec.initShape_Rectangle();
				rec.setJEditPanel(this.jInternalEditFrame.getJEditPanel());
				repeatControl.add(rec);
				rec.reDraw();
				if(nodeList.item(i).hasChildNodes())
					ProcesarRepeatControl(nodeList.item(i).getChildNodes(), rec);
			}else
				warningMessagge();
		}
	}
	
	/**
	 * Este metodo se encarga de procesar un nodo de tipo control, que es pasado como
	 * parametro, y obtiene los atributos de este y los setea en el JControl que es 
	 * pasado ocmo parametro.  
	 * 
	 * @param	Node
	 * @param	JControl
	 */	
	private void setProperties(Node node, JControl jControl){
		if(node.getAttributes().getNamedItem("name") != null)
			jControl.setName(node.getAttributes().getNamedItem("name").getNodeValue());
		if(node.getAttributes().getNamedItem("hint") != null)
			jControl.setHint(node.getAttributes().getNamedItem("hint").getNodeValue());
		if(node.getAttributes().getNamedItem("bind") != null)
			jControl.setBind(node.getAttributes().getNamedItem("bind").getNodeValue());
		if(node.getAttributes().getNamedItem("ref") != null)
			jControl.setRef(node.getAttributes().getNamedItem("ref").getNodeValue());
		if(node.getAttributes().getNamedItem("model") != null)
			jControl.setModel(node.getAttributes().getNamedItem("model").getNodeValue());
		if(node.getAttributes().getNamedItem("tabPosition") != null)
			jControl.setModel(node.getAttributes().getNamedItem("tabPosition").getNodeValue());
		if(node.getAttributes().getNamedItem("helpText") != null)
			jControl.setHelpText(node.getAttributes().getNamedItem("helpText").getNodeValue());
		
		jControl.getElement().getAttributes().getNamedItem("height").setNodeValue(node.getAttributes().getNamedItem("height").getNodeValue());
		jControl.getElement().getAttributes().getNamedItem("width").setNodeValue(node.getAttributes().getNamedItem("width").getNodeValue());
		jControl.getElement().getAttributes().getNamedItem("top").setNodeValue(node.getAttributes().getNamedItem("top").getNodeValue());
		jControl.getElement().getAttributes().getNamedItem("left").setNodeValue(node.getAttributes().getNamedItem("left").getNodeValue());
		
		jControl.setSize(Integer.parseInt(node.getAttributes().getNamedItem("width").getNodeValue()),
						 Integer.parseInt(node.getAttributes().getNamedItem("height").getNodeValue()));
		jControl.setLocation(Integer.parseInt(node.getAttributes().getNamedItem("left").getNodeValue()),
							 Integer.parseInt(node.getAttributes().getNamedItem("top").getNodeValue()));
	}
	
	/**
	 * Este metodo despliega un mensaje de error de sintaxis del archivo xml.
	 */
	private void errorMesagge(){
		JOptionPane.showMessageDialog(this.jFrame, 
        "La sintaxis del archivo que acaba de abrir no es correcta.",
        "Mensaje de Advertencia", 
		JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Este metodo despliega un mensaje de error de sintaxis del archivo xml
	 * (cuando existen controles no identificados).
	 */
	private void warningMessagge(){
		JOptionPane.showMessageDialog(this.jFrame, 
        "La sintaxis del archivo que acaba de abrir no es correcta,\n"+
		"existe al menos un objeto que no puede ser interpretado.",
        "Mensaje de Advertencia", 
		JOptionPane.WARNING_MESSAGE);
	}
}

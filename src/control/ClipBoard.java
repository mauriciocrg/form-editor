/* 
* nombre del archivo: FirstFrame.java
*  
* @Fecha Generación: 	11/04/2005 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Esta clase implementa un ClipBoard que permite almacenar 
*					el JControl que va a ser cortado o copiado.
*@Fecha Actualización:	11/04/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------- 
*/
package control;

import java.awt.Component;

import javax.swing.JComponent;

import org.w3c.dom.Element;

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
import frames.internalFrame.panel.JEditPanel;

/*
 * Esta clase implementa un ClipBoard que permite almacenar 
 * el JControl que va a ser cortado o copiado.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 11/04/2005
 */
public class ClipBoard {
	private final static int ALTO_EXTRA = 8;		//alto extra del JComponent para poder visualizar los shapes 
    private final static int ANCHO_EXTRA = 8;		//ancho extra del JComponent para poder visualizar los shape
	
	private static JComponent jComponent = null;	// es el control que fue copiado o cortado.
	private static final RepeatControl JComponent = null;
	
	private static JEditPanel jEditPanel;
	private static XmlDoc xmldoc;
	private static Element repeatControl;
	
	/**
	 * Este metodo retorna el control que ha sido copiado 
	 * o cortado. 
	 * 
	 * @return 	JComponent es el control que ha sido cortado o copiado.
	 */
	public static JComponent getJComponent(){
		return jComponent;
	}
	
	/**
	 * Este metodo retorna la copia de el JComponent que se encuentra en la variable
	 * miembro jComponent.
	 * 
	 * @param	JEditPanel	es el JEditPanel al que va a pertenecer la copia del JComponent.
	 * @param	XmlDoc		es el XmlDoc del formulario.
	 * @return	JComponent	es la copia del jComponent. 
	 */
	public static JComponent getCopyOfJComponent(JEditPanel jEditPanel,XmlDoc xmldoc){
		ClipBoard.jEditPanel = jEditPanel;
		ClipBoard.xmldoc = xmldoc;
		return copyOf();
	}
	
	/**
	 * Este metodo retorna la copia de el JComponent que se encuentra en la variable
	 * miembro jComponent.
	 * 
	 * @return	JComponent	es la copia del jComponent. 
	 */
	public static JComponent getCopyOfJComponent(){
		return copyOf();
	}
	
	/**
	 * Este metodo setea el control que ha sido copiado 
	 * o cortado. 
	 * 
	 * @param 	JComponent es el control que ha sido cortado o copiado.
	 */
	public static void setJComponent(JComponent jComponent){
		ClipBoard.jComponent = jComponent;
	}
	
	/**
	 * Este metodo setea en null el JComponent miembro de la clase,
	 * esto sucede cuando se borra un control.
	 */
	public static void resetClipBoard(){
		ClipBoard.jComponent = null;
	}
	
	/**
	 * Este metodo retorna true si la JComponent no es vacia, y falso
	 * en el caso contrario.
	 * 
	 * @return 	boolean		indica si JComponent es vacio o no.
	 */
	public static boolean containElement(){
		if(ClipBoard.jComponent == null)
			return false;
		else
			return true;
	}
	
	/**
	 * Este metodo retorna una copia de la variable jComponent miemboro
	 * parametro.
	 */
	private static JComponent copyOf(){
		JControl control = null;
		if(ClipBoard.jComponent instanceof InputControl){
        	InputControl ic = new InputControl(0,
        									   0, 
											   0,
											   ClipBoard.jComponent.getWidth() - ANCHO_EXTRA,
											   ClipBoard.jComponent.getHeight() - ALTO_EXTRA, 
											   ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTable(),
											   ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTree(),											    
											   ((JControl)ClipBoard.jComponent).getJEditPanel());
        	control = ic;
        	
        	setProperties(ClipBoard.jComponent, (JComponent) control);
        	
        	control.setXmlDoc(ClipBoard.xmldoc);
		}else
		if(ClipBoard.jComponent instanceof LabelControl){
			LabelControl lc = new LabelControl(0,
											   0, 
											   0,
											   ClipBoard.jComponent.getWidth() - ANCHO_EXTRA,
											   ClipBoard.jComponent.getHeight() - ALTO_EXTRA, 
											   ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTable(),
											   ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTree(),
											   ((JControl)ClipBoard.jComponent).getJEditPanel());
        	control = lc;
        	
        	setProperties(ClipBoard.jComponent, (JComponent) control);
        	
        	control.setXmlDoc(ClipBoard.xmldoc);
        }else
		if(ClipBoard.jComponent instanceof OutputControl){
			OutputControl oc = new OutputControl(0,
												 0, 
												 0,
												 ClipBoard.jComponent.getWidth() - ANCHO_EXTRA,
												 ClipBoard.jComponent.getHeight() - ALTO_EXTRA, 
												 ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTable(),
												 ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTree(),
												 ((JControl)ClipBoard.jComponent).getJEditPanel());
        	control = oc;
        	
        	setProperties(ClipBoard.jComponent, (JComponent) control);
        	
        	control.setXmlDoc(ClipBoard.xmldoc);
		}else		
		if(ClipBoard.jComponent instanceof UploadControl){
			UploadControl uc = new UploadControl(0,
												 0, 
												 0,
												 ClipBoard.jComponent.getWidth() - ANCHO_EXTRA,
												 ClipBoard.jComponent.getHeight() - ALTO_EXTRA, 
												 ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTable(),
												 ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTree(),
												 ((JControl)ClipBoard.jComponent).getJEditPanel());
			control = uc;
			
			setProperties(ClipBoard.jComponent, (JComponent) control);
			
			control.setXmlDoc(ClipBoard.xmldoc);
		}else						
		if(ClipBoard.jComponent instanceof SecretControl){
			SecretControl sc = new SecretControl(0,
												 0, 
												 0,
												 ClipBoard.jComponent.getWidth() - ANCHO_EXTRA,
												 ClipBoard.jComponent.getHeight() - ALTO_EXTRA, 
												 ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTable(),
												 ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTree(),
												 ((JControl)ClipBoard.jComponent).getJEditPanel());
			control = sc;
			
			setProperties(ClipBoard.jComponent, (JComponent) control);
			
			control.setXmlDoc(ClipBoard.xmldoc);
		}else						
		if(ClipBoard.jComponent instanceof TextAreaControl){
			TextAreaControl tc = new TextAreaControl(0,
													 0, 
													 0,
													 ClipBoard.jComponent.getWidth() - ANCHO_EXTRA,
													 ClipBoard.jComponent.getHeight() - ALTO_EXTRA, 
													 ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTable(),
													 ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTree(),
													 ((JControl)ClipBoard.jComponent).getJEditPanel());
			control = tc;
			
			setProperties(ClipBoard.jComponent, (JComponent) control);
			
			control.setXmlDoc(ClipBoard.xmldoc);
		}else						
		if(ClipBoard.jComponent instanceof Select1Control){
			Select1Control ss1c = new Select1Control(0,
													 0, 
													 0,
													 ClipBoard.jComponent.getWidth() - ANCHO_EXTRA,
													 ClipBoard.jComponent.getHeight() - ALTO_EXTRA, 
													 ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTable(),
													 ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTree(),
													 ((JControl)ClipBoard.jComponent).getJEditPanel());
			control = ss1c;
			
			setProperties(ClipBoard.jComponent, (JComponent) control);
			
			control.setXmlDoc(ClipBoard.xmldoc);
		}else									
		if(ClipBoard.jComponent instanceof SelectControl){
			SelectControl ssc = new SelectControl(0,
												  0, 
												  0,
												  ClipBoard.jComponent.getWidth() - ANCHO_EXTRA,
												  ClipBoard.jComponent.getHeight() - ALTO_EXTRA, 
												  ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTable(),
												  ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTree(),
												  ((JControl)ClipBoard.jComponent).getJEditPanel());
			control = ssc;
			
			setProperties(ClipBoard.jComponent, (JComponent) control);
			
			control.setXmlDoc(ClipBoard.xmldoc);
		}else									
		if(ClipBoard.jComponent instanceof SubmitControl){
			SubmitControl suc = new SubmitControl(0,
												  0, 
												  0,
												  ClipBoard.jComponent.getWidth() - ANCHO_EXTRA,
												  ClipBoard.jComponent.getHeight() - ALTO_EXTRA, 
												  ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTable(),
												  ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTree(),
												  ((JControl)ClipBoard.jComponent).getJEditPanel());
			control = suc;
			
			setProperties(ClipBoard.jComponent, (JComponent) control);
			
			control.setXmlDoc(ClipBoard.xmldoc);
		}else									
		if(ClipBoard.jComponent instanceof TriggerControl){
			TriggerControl trc = new TriggerControl(0,
													0, 
													0,
													ClipBoard.jComponent.getWidth() - ANCHO_EXTRA,
													ClipBoard.jComponent.getHeight() - ALTO_EXTRA, 
													((JControl)ClipBoard.jComponent).getJEditPanel().getDocTable(),
													((JControl)ClipBoard.jComponent).getJEditPanel().getDocTree(),
													((JControl)ClipBoard.jComponent).getJEditPanel());
			control = trc;
			
			setProperties(ClipBoard.jComponent, (JComponent) control);
			
			control.setXmlDoc(ClipBoard.xmldoc);
		}else									
		if(ClipBoard.jComponent instanceof RangeControl){
			RangeControl rc = new RangeControl(0,
											   0, 
											   0,
											   ClipBoard.jComponent.getWidth() - ANCHO_EXTRA,
											   ClipBoard.jComponent.getHeight() - ALTO_EXTRA, 
											   ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTable(),
											   ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTree(),
											   ((JControl)ClipBoard.jComponent).getJEditPanel());
			control = rc;
			
			setProperties(ClipBoard.jComponent, (JComponent) control);
			
			control.setXmlDoc(ClipBoard.xmldoc);
		}else														
		if(ClipBoard.jComponent instanceof RepeatControl){
			RepeatControl rec = new RepeatControl(0,
												  0, 
												  0,
												  ClipBoard.jComponent.getWidth() - ANCHO_EXTRA,
												  ClipBoard.jComponent.getHeight() - ALTO_EXTRA, 
												  ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTable(),
												  ((JControl)ClipBoard.jComponent).getJEditPanel().getDocTree(),
												  ((JControl)ClipBoard.jComponent).getJEditPanel());
			
			rec.getRepeatManager().setJEditPanel(ClipBoard.jEditPanel);
			ClipBoard.repeatControl = rec.getElement();
			control = rec;
			
			setProperties(ClipBoard.jComponent, (JComponent) control);
			
			control.setXmlDoc(ClipBoard.xmldoc);
			
			addControls(control, ClipBoard.jComponent.getComponents());
		}
		
		return control;
	}
	
	/**
	 * Este metodo agrega los componentes que pertenecen a un repeat control.
	 * 
	 * @param 	JComponent		repeatControl.
	 * @param	Component [] 	es el conjunto de JControls que pertenecen al
	 * 							repeatControl.
	 */
	private static void addControls(JComponent repeatControl, Component [] components){
		JControl control = null;
		for(int i = 0; i < components.length; i++){
			if(components[i] instanceof InputControl){
	        	InputControl ic = new InputControl(0,
	        									   components[i].getX(), 
	        									   components[i].getY(),
	        									   (components[i].getX() + components[i].getWidth()) - ANCHO_EXTRA,
	        									   (components[i].getY() + components[i].getHeight()) - ALTO_EXTRA, 
												   ((JControl)components[i]).getJEditPanel().getDocTable(),
												   ((JControl)components[i]).getJEditPanel().getDocTree(),											    
												   ((JControl)components[i]).getJEditPanel());
	        	control = ic;
	        	
	        	setProperties((JComponent) components[i], (JComponent) control);
	        	control.setRepeatControl(((RepeatControl) repeatControl).getElement());
				((RepeatControl) repeatControl).getRepeatManager().addControl(control);
			}else
			if(components[i] instanceof LabelControl){
				LabelControl lc = new LabelControl(0,
												   components[i].getX(), 
												   components[i].getY(),
												   (components[i].getX() + components[i].getWidth()) - ANCHO_EXTRA,
												   (components[i].getY() + components[i].getHeight()) - ALTO_EXTRA, 
												   ((JControl)components[i]).getJEditPanel().getDocTable(),
												   ((JControl)components[i]).getJEditPanel().getDocTree(),
												   ((JControl)components[i]).getJEditPanel());
	        	control = lc;
	        	
	        	setProperties((JComponent) components[i], (JComponent) control);
				control.setRepeatControl(((RepeatControl) repeatControl).getElement());
				((RepeatControl) repeatControl).getRepeatManager().addControl(control);
	        }else
			if(components[i] instanceof OutputControl){
				OutputControl oc = new OutputControl(0,
													 components[i].getX(), 
													 components[i].getY(),
													 (components[i].getX() + components[i].getWidth()) - ANCHO_EXTRA,
													 (components[i].getY() + components[i].getHeight()) - ALTO_EXTRA, 
													 ((JControl)components[i]).getJEditPanel().getDocTable(),
													 ((JControl)components[i]).getJEditPanel().getDocTree(),
													 ((JControl)components[i]).getJEditPanel());
	        	control = oc;
	        	
	        	setProperties((JComponent) components[i], (JComponent) control);
				control.setRepeatControl(((RepeatControl) repeatControl).getElement());
				((RepeatControl) repeatControl).getRepeatManager().addControl(control);
			}else		
			if(components[i] instanceof UploadControl){
				UploadControl uc = new UploadControl(0,
													 components[i].getX(), 
													 components[i].getY(),
													 (components[i].getX() + components[i].getWidth()) - ANCHO_EXTRA,
													 (components[i].getY() + components[i].getHeight()) - ALTO_EXTRA, 
													 ((JControl)components[i]).getJEditPanel().getDocTable(),
													 ((JControl)components[i]).getJEditPanel().getDocTree(),
													 ((JControl)components[i]).getJEditPanel());
				control = uc;
				
				setProperties((JComponent) components[i], (JComponent) control);
				control.setRepeatControl(((RepeatControl) repeatControl).getElement());
				((RepeatControl) repeatControl).getRepeatManager().addControl(control);
			}else						
			if(components[i] instanceof SecretControl){
				SecretControl sc = new SecretControl(0,
													 components[i].getX(), 
													 components[i].getY(),
													 (components[i].getX() + components[i].getWidth()) - ANCHO_EXTRA,
													 (components[i].getY() + components[i].getHeight()) - ALTO_EXTRA, 
													 ((JControl)components[i]).getJEditPanel().getDocTable(),
													 ((JControl)components[i]).getJEditPanel().getDocTree(),
													 ((JControl)components[i]).getJEditPanel());
				control = sc;
				
				setProperties((JComponent) components[i], (JComponent) control);
				control.setRepeatControl(((RepeatControl) repeatControl).getElement());
				((RepeatControl) repeatControl).getRepeatManager().addControl(control);
			}else						
			if(components[i] instanceof TextAreaControl){
				TextAreaControl tc = new TextAreaControl(0,
														 components[i].getX(), 
														 components[i].getY(),
														 (components[i].getX() + components[i].getWidth()) - ANCHO_EXTRA,
														 (components[i].getY() + components[i].getHeight()) - ALTO_EXTRA, 
														 ((JControl)components[i]).getJEditPanel().getDocTable(),
														 ((JControl)components[i]).getJEditPanel().getDocTree(),
														 ((JControl)components[i]).getJEditPanel());
				control = tc;
				
				setProperties((JComponent) components[i], (JComponent) control);
				control.setRepeatControl(((RepeatControl) repeatControl).getElement());
				((RepeatControl) repeatControl).getRepeatManager().addControl(control);
			}else						
			if(components[i] instanceof Select1Control){
				Select1Control ss1c = new Select1Control(0,
														 components[i].getX(), 
														 components[i].getY(),
														 (components[i].getX() + components[i].getWidth()) - ANCHO_EXTRA,
														 (components[i].getY() + components[i].getHeight()) - ALTO_EXTRA, 
														 ((JControl)components[i]).getJEditPanel().getDocTable(),
														 ((JControl)components[i]).getJEditPanel().getDocTree(),
				 										 ((JControl)components[i]).getJEditPanel());
				control = ss1c;
				
				setProperties((JComponent) components[i], (JComponent) control);
				control.setRepeatControl(((RepeatControl) repeatControl).getElement());
				((RepeatControl) repeatControl).getRepeatManager().addControl(control);
			}else									
			if(components[i] instanceof SelectControl){
				SelectControl ssc = new SelectControl(0,
													  components[i].getX(), 
													  components[i].getY(),
													  (components[i].getX() + components[i].getWidth()) - ANCHO_EXTRA,
													  (components[i].getY() + components[i].getHeight()) - ALTO_EXTRA, 
													  ((JControl)components[i]).getJEditPanel().getDocTable(),
													  ((JControl)components[i]).getJEditPanel().getDocTree(),
													  ((JControl)components[i]).getJEditPanel());
				control = ssc;
				
				setProperties((JComponent) components[i], (JComponent) control);
				control.setRepeatControl(((RepeatControl) repeatControl).getElement());
				((RepeatControl) repeatControl).getRepeatManager().addControl(control);
			}else									
			if(components[i] instanceof SubmitControl){
				SubmitControl suc = new SubmitControl(0,
													  components[i].getX(), 
													  components[i].getY(),
													  (components[i].getX() + components[i].getWidth()) - ANCHO_EXTRA,
													  (components[i].getY() + components[i].getHeight()) - ALTO_EXTRA, 
													  ((JControl)components[i]).getJEditPanel().getDocTable(),
													  ((JControl)components[i]).getJEditPanel().getDocTree(),
													  ((JControl)components[i]).getJEditPanel());
				control = suc;
				
				setProperties((JComponent) components[i], (JComponent) control);
				control.setRepeatControl(((RepeatControl) repeatControl).getElement());
				((RepeatControl) repeatControl).getRepeatManager().addControl(control);
			}else									
			if(components[i] instanceof TriggerControl){
				TriggerControl trc = new TriggerControl(0,
														components[i].getX(), 
														components[i].getY(),
														(components[i].getX() + components[i].getWidth()) - ANCHO_EXTRA,
														(components[i].getY() + components[i].getHeight()) - ALTO_EXTRA, 
														((JControl)components[i]).getJEditPanel().getDocTable(),
														((JControl)components[i]).getJEditPanel().getDocTree(),
														((JControl)components[i]).getJEditPanel());
				control = trc;
				
				setProperties((JComponent) components[i], (JComponent) control);
				control.setRepeatControl(((RepeatControl) repeatControl).getElement());
				((RepeatControl) repeatControl).getRepeatManager().addControl(control);
			}else									
			if(components[i] instanceof RangeControl){
				RangeControl rc = new RangeControl(0,
												   components[i].getX(), 
												   components[i].getY(),
												   (components[i].getX() + components[i].getWidth()) - ANCHO_EXTRA,
												   (components[i].getY() + components[i].getHeight()) - ALTO_EXTRA, 
												   ((JControl)components[i]).getJEditPanel().getDocTable(),
												   ((JControl)components[i]).getJEditPanel().getDocTree(),
												   ((JControl)components[i]).getJEditPanel());
				control = rc;
			
				setProperties((JComponent) components[i], (JComponent) control);
				control.setRepeatControl(((RepeatControl) repeatControl).getElement());				
				((RepeatControl) repeatControl).getRepeatManager().addControl(control);
				
			}else														
			if(components[i] instanceof RepeatControl){
				RepeatControl rec = new RepeatControl(0,
													  components[i].getX(), 
													  components[i].getY(),
													  (components[i].getX() + components[i].getWidth()) - ANCHO_EXTRA,
													  (components[i].getY() + components[i].getHeight()) - ALTO_EXTRA, 
													  ((JControl)components[i]).getJEditPanel().getDocTable(),
													  ((JControl)components[i]).getJEditPanel().getDocTree(),
													  ((JControl)components[i]).getJEditPanel());
				rec.getRepeatManager().setJEditPanel(ClipBoard.jEditPanel);
				control = rec;
				
				setProperties((JComponent) components[i], (JComponent) control);
				control.setRepeatControl(((RepeatControl) repeatControl).getElement());
				((RepeatControl) repeatControl).getRepeatManager().addControl(control);
				
				addControls(control, ((JComponent)components[i]).getComponents());
			}
		}
	}
	
	
	/**
	 * Este metodo se encarga de setear las propiedades del JControl origen al JControl 
	 * destino.
	 * 
	 * @param	JComponent	componentSrc, es el JControl origen.
	 * @param	JComponent	componentDest, es el JControl destino.
	 */
	public static void setProperties(JComponent componentSrc, JComponent componentDest){
		((JControl)componentDest).setName("Copia de "+((JControl)componentSrc).getName());
		((JControl)componentDest).setHelpText(((JControl)componentSrc).getHelpText());
		((JControl)componentDest).setBind(((JControl)componentSrc).getBind());
		((JControl)componentDest).setHint(((JControl)componentSrc).getHint());
		((JControl)componentDest).setRef(((JControl)componentSrc).getRef());
		((JControl)componentDest).setModel(((JControl)componentSrc).getModel());
		((JControl)componentDest).setTabPosition(((JControl)componentSrc).getTabPosition());
		
		((JControl)componentDest).setType(((JControl)componentSrc).getType());
		((JControl)componentDest).setControlType();
		((JControl)componentDest).setJEditPanel(ClipBoard.jEditPanel);
		((JControl)componentDest).updateProperties();
	}
}

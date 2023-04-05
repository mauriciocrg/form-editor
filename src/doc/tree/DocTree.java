/* 
* nombre del archivo: DocTree.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Implementa un JToolBar que contiene el JTree en el
*					cual se despliega la arquitectura del documento 
*					XForm.
*@Fecha Actualización:	25/01/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package doc.tree;

import javax.swing.JToolBar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
import doc.table.DocTable;

/*
 * Esta clase implementa un JToolBar que contiene el JTree en el
 * cual se despliega la arquitectura del documento 
 * XForm.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
 */

public class DocTree {

	private DocTable docTable;				//es el DocTable que esta en FirstFrame.
	private Component[] controles;			//array de componentes que pertenecen al formulario que se esta editando.
	private JToolBar jToolBar = null;  		//es el JToolBar que contiene el JPanel.
	private JPanel jPanel = null;			//es el JPanel que contien al JScrollPane.
	private JTree jTree = null;  			//es el JTree que contiene la estructura del formulario. 	
	private JScrollPane jScrollPane = null;	//es el JScrollPane que muestra el JTree.
	
	private static Document document;		//es el documento que contiene la configuración del formulario.
	
	/**
	 * Este metodo inicializa el jToolBar, si igual a null lo crea, le 
	 * asigna propiedades y luego lo retorna.	
	 * 	
	 * @return		JToolBar	este JToolBar contiene un JPanel.
	 */    
	public JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setSize(250, 300);
			jToolBar.setToolTipText("Árbol del Documento");
			jToolBar.setPreferredSize(new java.awt.Dimension(200,400));
			jToolBar.setName("Árbol del Documento");
			jToolBar.setOrientation(javax.swing.JToolBar.VERTICAL);
			jToolBar.add(getJPanel());

			jToolBar.addPropertyChangeListener(new java.beans.PropertyChangeListener() { 
				public void propertyChange(java.beans.PropertyChangeEvent e) { 
					if ((e.getPropertyName().equals("orientation"))) { 
						jToolBar.setOrientation(javax.swing.JToolBar.VERTICAL);
					} 
				}
			});
		}
		return jToolBar;
	}
	
	/**
	 * Este metodo inicializa el jPanel, si es igual a null lo crea, le 
	 * asigna propiedades y luego lo retorna.
	 *  	
	 * @return		JPanel		este JPanel contiene un JScrollPane.
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			BorderLayout borderLayout = new BorderLayout();
			jPanel.setLayout(borderLayout);
			jPanel.setPreferredSize(new java.awt.Dimension(250,300));
			borderLayout.setHgap(2);
			borderLayout.setVgap(2);
			jPanel.setSize(280, 400);
			jPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}
	
	/**
	 * Este metodo inicializa al jTree, si es null lo crea, le asigna 
	 * propiedades y luego lo retorna.	
	 * 	
	 * @return		JTree		es el JTree que esta en FirstFrame.
	 */    
	public JTree getJTree() {
		if (jTree == null) {
			buildDom();
			jTree = new JTree(new DomToTreeModelAdapter(document));
			jTree.setShowsRootHandles(true);
			jTree.setToolTipText("Árbol del Documento");
			jTree.setToggleClickCount(1);
			jTree.setName("Árbol del Documento");
			jTree.setPreferredSize(new java.awt.Dimension(250,500));
			jTree.setVisibleRowCount(200);
			jTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() { 
				public void valueChanged(javax.swing.event.TreeSelectionEvent e) {    
					TreePath p = e.getNewLeadSelectionPath();
					AdapterNode adpNode = (AdapterNode) p.getLastPathComponent();
					updateControl(adpNode.getName());
				}
			});
		}
		return jTree;
	}
	
	/**
	 * Este metodo inicializa el JScrollPane, si es null no crea, 
	 * le asigna propiedades y luego lo retorna.	
	 * 	
	 * @return		JScrollPane		este JScrollPane muestra al JTree.	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTree());
		}
		return jScrollPane;
	}
	
	/**
	 * Este metodo crea el documento que va a contener la configuración 
	 * del formulario.
	 */  
	public static void buildDom() {
	  	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
        	DocumentBuilder builder = factory.newDocumentBuilder();
        	document = builder.newDocument();  
       } catch (ParserConfigurationException pce) {
       		pce.printStackTrace();
        }
	}
	
	/**
	 * Este metodo retrona el documento miemboro que contiene la configuracón 
	 * del formulario que se esta editando.
	 * 
	 * @return		Document	es el documento del formulario que se esta 
	 * 							editando.
	 */
	public Document getDocument() {
		return DocTree.document;
	}
	
	/**
	 * Este metodo permite agragar un elemento a el documento.
	 * 
	 * @param		Element		es el elemento que va a ser agragado.
	 */
	public void addElement(Element element) {
		document.appendChild(element);
	}
	
	/**
	 * Este metodo permite remover un elemento del documento.
	 * 
	 * @param		Element		es el elemento que va a ser removido.
	 */
	public void removeElement(Element element) {
		document.removeChild(element);
	}
	
	/**
	 * Este metodo permite setear el array de componentes miembro con el
	 * array de componentes del formulario que esta siendo editado.
	 * 
	 * @param		Component[]		es el array de componetes del formulario 
	 * 								que esta siendo editado.
	 */
	public void setControles(Component[] controles){
		this.controles = controles; 
	}
	
	/**
	 * Este metodo permite setear el DocTable miembro con el DocTable de 
	 * FirstFrame.
	 * 
	 * @param		DocTable		es el DocTable que pertenece a FirstFrame.
	 */
	public void setDocTable(DocTable docTable){
		this.docTable = docTable; 
	}

	/**
	 * Este metodo dado el String name, busca el JComponent que tenga ese nombre
	 * y visuliza su popiedades en el JTable.
	 * 
	 * @param		String		es el nombre del JComponent al que se le van a
	 *							se le van a visualizar las propiedades en el JTable.
	 */
	private void updateControl(String name) {
		int size = 0;
		if(controles != null) size = this.controles.length;
		
		for(int i=0; i < size; i++) {
			if(controles[i] instanceof InputControl) { 
				if(((InputControl) controles[i]).getName().equals(name)) { 
					((InputControl) controles[i]).updateProperties();
					((InputControl) controles[i]).setSelected(true);
					((InputControl) controles[i]).selected();
					this.docTable.setJComponent((InputControl) controles[i]);
					break;
				}
			} 
			else
			if(controles[i] instanceof LabelControl) { 
				if(((LabelControl) controles[i]).getName().equals(name)) {
					((LabelControl) controles[i]).updateProperties();
					((LabelControl) controles[i]).setSelected(true);
					((LabelControl) controles[i]).selected();
					this.docTable.setJComponent((LabelControl) controles[i]);
					break;
				}
			}
			else
			if(controles[i] instanceof OutputControl) { 
				if(((OutputControl) controles[i]).getName().equals(name)) {
					((OutputControl) controles[i]).updateProperties();
					((OutputControl) controles[i]).setSelected(true);
					((OutputControl) controles[i]).selected();
					this.docTable.setJComponent((OutputControl) controles[i]);
					break;
				}
			}else
			if(controles[i] instanceof RangeControl) { 
				if(((RangeControl) controles[i]).getName().equals(name)) {
					((RangeControl) controles[i]).updateProperties();
					((RangeControl) controles[i]).setSelected(true);
					((RangeControl) controles[i]).selected();
					this.docTable.setJComponent((RangeControl) controles[i]);
					break;
				}
			}else
			if(controles[i] instanceof SecretControl) { 
				if(((SecretControl) controles[i]).getName().equals(name)) {
					((SecretControl) controles[i]).updateProperties();
					((SecretControl) controles[i]).setSelected(true);
					((SecretControl) controles[i]).selected();
					this.docTable.setJComponent((SecretControl) controles[i]);
					break;
				}
			}else
			if(controles[i] instanceof SelectControl) { 
				if(((SelectControl) controles[i]).getName().equals(name)) {
					((SelectControl) controles[i]).updateProperties();
					((SelectControl) controles[i]).setSelected(true);
					((SelectControl) controles[i]).selected();
					this.docTable.setJComponent((SelectControl) controles[i]);
					break;
				}
			}else
			if(controles[i] instanceof Select1Control) { 
				if(((Select1Control) controles[i]).getName().equals(name)) {
					((Select1Control) controles[i]).updateProperties();
					((Select1Control) controles[i]).setSelected(true);
					((Select1Control) controles[i]).selected();
					this.docTable.setJComponent((Select1Control) controles[i]);
					break;
				}
			}else
			if(controles[i] instanceof SubmitControl) { 
				if(((SubmitControl) controles[i]).getName().equals(name)) {
					((SubmitControl) controles[i]).updateProperties();
					((SubmitControl) controles[i]).setSelected(true);
					((SubmitControl) controles[i]).selected();
					this.docTable.setJComponent((SubmitControl) controles[i]);
					break;
				}
			}else
			if(controles[i] instanceof TextAreaControl) { 
				if(((TextAreaControl) controles[i]).getName().equals(name)) {
					((TextAreaControl) controles[i]).updateProperties();
					((TextAreaControl) controles[i]).setSelected(true);
					((TextAreaControl) controles[i]).selected();
					this.docTable.setJComponent((TextAreaControl) controles[i]);
					break;
				}
			}else
			if(controles[i] instanceof TriggerControl) { 
				if(((TriggerControl) controles[i]).getName().equals(name)) {
					((TriggerControl) controles[i]).updateProperties();
					((TriggerControl) controles[i]).setSelected(true);
					((TriggerControl) controles[i]).selected();
					this.docTable.setJComponent((TriggerControl) controles[i]);
					break;
				}
			}else
			if(controles[i] instanceof UploadControl) { 
				if(((UploadControl) controles[i]).getName().equals(name)) {
					((UploadControl) controles[i]).updateProperties();
					((UploadControl) controles[i]).setSelected(true);
					((UploadControl) controles[i]).selected();
					this.docTable.setJComponent((UploadControl) controles[i]);
					break;
				}
			}else
			if(controles[i] instanceof RepeatControl) { 
				if(((RepeatControl) controles[i]).getName().equals(name)) {
					((RepeatControl) controles[i]).updateProperties();
					((RepeatControl) controles[i]).setSelected(true);
					((RepeatControl) controles[i]).selected();
					this.docTable.setJComponent((RepeatControl) controles[i]);
					break;
				}else
				if(updateControlInRepeatControl( ((JControl) controles[i]).getComponents() , name))
					break;
			}
		}
	}
	
	/**
	 * Este metodo dado el String name, busca el JComponent que tenga ese nombre
	 * iterando sobre los controles contenidos en un repeat y visuliza su 
	 * popiedades en el JTable.
	 * 
	 * @param		Component []es el array de controles pertenecientes al repeat
	 * 							Control.
	 * @param		String		es el nombre del JComponent al que se le van a
	 *							se le van a visualizar las propiedades en el JTable.
	 * @return		boolean		indica si encontro o no.
	 */
	private boolean updateControlInRepeatControl(Component [] controlesInRepeat, String name){
		int i = 0;
		
		while(i < controlesInRepeat.length ) {
			if(controlesInRepeat[i] instanceof InputControl) { 
				if(((InputControl) controlesInRepeat[i]).getName().equals(name)) { 
					((InputControl) controlesInRepeat[i]).updateProperties();
					((InputControl) controlesInRepeat[i]).setSelected(true);
					((InputControl) controlesInRepeat[i]).selected();
					this.docTable.setJComponent((InputControl) controlesInRepeat[i]);
					return true;
				}
			} 
			else
			if(controlesInRepeat[i] instanceof LabelControl) { 
				if(((LabelControl) controlesInRepeat[i]).getName().equals(name)) {
					((LabelControl) controlesInRepeat[i]).updateProperties();
					((LabelControl) controlesInRepeat[i]).setSelected(true);
					((LabelControl) controlesInRepeat[i]).selected();
					this.docTable.setJComponent((LabelControl) controlesInRepeat[i]);
					return true;
				}
			}
			else
			if(controlesInRepeat[i] instanceof OutputControl) { 
				if(((OutputControl) controlesInRepeat[i]).getName().equals(name)) {
					((OutputControl) controlesInRepeat[i]).updateProperties();
					((OutputControl) controlesInRepeat[i]).setSelected(true);
					((OutputControl) controlesInRepeat[i]).selected();
					this.docTable.setJComponent((OutputControl) controlesInRepeat[i]);
					return true;
				}
			}else
			if(controlesInRepeat[i] instanceof RangeControl) { 
				if(((RangeControl) controlesInRepeat[i]).getName().equals(name)) {
					((RangeControl) controlesInRepeat[i]).updateProperties();
					((RangeControl) controlesInRepeat[i]).setSelected(true);
					((RangeControl) controlesInRepeat[i]).selected();
					this.docTable.setJComponent((RangeControl) controlesInRepeat[i]);
					return true;
				}
			}else
			if(controlesInRepeat[i] instanceof SecretControl) { 
				if(((SecretControl) controlesInRepeat[i]).getName().equals(name)) {
					((SecretControl) controlesInRepeat[i]).updateProperties();
					((SecretControl) controlesInRepeat[i]).setSelected(true);
					((SecretControl) controlesInRepeat[i]).selected();
					this.docTable.setJComponent((SecretControl) controlesInRepeat[i]);
					return true;
				}
			}else
			if(controlesInRepeat[i] instanceof SelectControl) { 
				if(((SelectControl) controlesInRepeat[i]).getName().equals(name)) {
					((SelectControl) controlesInRepeat[i]).updateProperties();
					((SelectControl) controlesInRepeat[i]).setSelected(true);
					((SelectControl) controlesInRepeat[i]).selected();
					this.docTable.setJComponent((SelectControl) controlesInRepeat[i]);
					return true;
				}
			}else
			if(controlesInRepeat[i] instanceof Select1Control) { 
				if(((Select1Control) controlesInRepeat[i]).getName().equals(name)) {
					((Select1Control) controlesInRepeat[i]).updateProperties();
					((Select1Control) controlesInRepeat[i]).setSelected(true);
					((Select1Control) controlesInRepeat[i]).selected();
					this.docTable.setJComponent((Select1Control) controlesInRepeat[i]);
					return true;
				}
			}else
			if(controlesInRepeat[i] instanceof SubmitControl) { 
				if(((SubmitControl) controlesInRepeat[i]).getName().equals(name)) {
					((SubmitControl) controlesInRepeat[i]).updateProperties();
					((SubmitControl) controlesInRepeat[i]).setSelected(true);
					((SubmitControl) controlesInRepeat[i]).selected();
					this.docTable.setJComponent((SubmitControl) controlesInRepeat[i]);
					return true;
				}
			}else
			if(controlesInRepeat[i] instanceof TextAreaControl) { 
				if(((TextAreaControl) controlesInRepeat[i]).getName().equals(name)) {
					((TextAreaControl) controlesInRepeat[i]).updateProperties();
					((TextAreaControl) controlesInRepeat[i]).setSelected(true);
					((TextAreaControl) controlesInRepeat[i]).selected();
					this.docTable.setJComponent((TextAreaControl) controlesInRepeat[i]);
					return true;
				}
			}else
			if(controlesInRepeat[i] instanceof TriggerControl) { 
				if(((TriggerControl) controlesInRepeat[i]).getName().equals(name)) {
					((TriggerControl) controlesInRepeat[i]).updateProperties();
					((TriggerControl) controlesInRepeat[i]).setSelected(true);
					((TriggerControl) controlesInRepeat[i]).selected();
					this.docTable.setJComponent((TriggerControl) controlesInRepeat[i]);
					return true;
				}
			}else
			if(controlesInRepeat[i] instanceof UploadControl) { 
				if(((UploadControl) controlesInRepeat[i]).getName().equals(name)) {
					((UploadControl) controlesInRepeat[i]).updateProperties();
					((UploadControl) controlesInRepeat[i]).setSelected(true);
					((UploadControl) controlesInRepeat[i]).selected();
					this.docTable.setJComponent((UploadControl) controlesInRepeat[i]);
					return true;
				}
			}else
			if(controlesInRepeat[i] instanceof RepeatControl) { 
				if(((RepeatControl) controlesInRepeat[i]).getName().equals(name)) {
					((RepeatControl) controlesInRepeat[i]).updateProperties();
					((RepeatControl) controlesInRepeat[i]).setSelected(true);
					((RepeatControl) controlesInRepeat[i]).selected();
					this.docTable.setJComponent((RepeatControl) controlesInRepeat[i]);
					return true;
				}else
				if(updateControlInRepeatControl( ((JControl) controlesInRepeat[i]).getComponents() , name))
					return true;
			}
			i++;
		}
		return false;
	}
}

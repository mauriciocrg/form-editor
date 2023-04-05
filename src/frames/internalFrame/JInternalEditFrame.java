/* 
* nombre del archivo: JInternalEditFrame.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Implementa un JInteralFrame que contiene el entorno de
*					edición para diseñar un Formulario con controles XForms.
*@Fecha Actualización:	25/01/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package frames.internalFrame;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import doc.XmlDoc;
import doc.table.DocTable;
import doc.tree.DocTree;
import frames.FirstFrame;
import frames.internalFrame.panel.JEditPanel;

/*
 * Esta clase extiende de JInteralFrame y contiene el entorno 
 * de edición para diseñar un Formulario con controles XForms. 
 * Contiene un JEditPanel, y un JToolBar con un JButton por 
 * cada control XForm que puede ser ingresado al formulario.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
 */

public class JInternalEditFrame extends JInternalFrame {

	private DocTree docTree = null;		// Contiene la instancia DocTree de FirstFrame
	private DocTable docTable = null;	// Contiene la instancia DocTable de FirstFrame
	private XmlDoc xmldoc = null;		// Contiene la instancia XmlDoc de FirstFrame
	
	private JPanel jContentPane = new JPanel();	// Contiene todos los controles del JInternalFrame.
	private JPanel jToolBarPanel = null;		// Contiene los controles del formulario que se esta editando
		
	private JScrollPane jScrollPane = null;		// Es el scroll del panel que contiene el toolbar.
	private JScrollPane jScrollEditPane = null; // Es el scroll del panel principal.
	
	private boolean save = false;		// Indica que se guardaron los cambios del formulario.
	private boolean saved = false;		// Indica que se guardaron los cambios del formulario por primera vez.
	
	private String name;				// Indica el nombre del formulario.
	
	private FirstFrame jFrame;			// Contiene la instancia de la ventana principal (FirstFrame).
	private JEditPanel jEditPanel;		// Contiene los controles del formulario que se esta editando. 

	private JToolBar jToolBarDesktop = null;		// Es el ToolBar que contiene los botones con los controles XForms.
	private JToggleButton jButton_input = null;		// Es el boton del XForm input.
	private JToggleButton jButton_label = null;		// Es el boton del XForm label.
	private JToggleButton jButton_output = null;	// Es el boton del XForm output.
	private JToggleButton jButton_upload = null;	// Es el boton del XForm upload.
	private JToggleButton jButton_secret = null;	// Es el boton del XForm secret.
	private JToggleButton jButton_textArea = null;	// Es el boton del XForm textArea.
	private JToggleButton jButton_select = null;	// Es el boton del XForm select.
	private JToggleButton jButton_select1 = null;	// Es el boton del XForm select1.
	private JToggleButton jButton_submit = null;	// Es el boton del XForm submit.
	private JToggleButton jButton_trigger = null;	// Es el boton del XForm trigger.
	private JToggleButton jButton_range = null;		// Es el boton del XForm range.
	private JToggleButton jButton_arrow = null;		// Es el boton del XForm arrow.
	private JToggleButton jButton_repeat = null;	// Es el boton del XForm repeat.
	private JButton jButton_Browser = null;			// Es el boton que permite realizar un vista previa.
	
	/**
	 * Este metodo es el constructor de la clase, inicializa variables miembro y 
	 * crea e inicializa componentes.
	 * 
	 * @param 	DocTable	docTable es el DocTable de FirsFrame.
	 * @param	DocTree 	docTree es el DocTree de FirsFrame.
	 * @param	XmlDoc 		xmldoc
	 * @param	FirstFrame 	jFrame
	 */
	public JInternalEditFrame(DocTable docTable, DocTree docTree, final XmlDoc xmldoc, final FirstFrame jFrame)	{
		super("", true, true, true, true);
		this.xmldoc = xmldoc;
		this.jFrame = jFrame;

		this.docTree = docTree;
		this.docTable = docTable;
		this.setDefaultCloseOperation(JFrame.NORMAL);
		jEditPanel = new JEditPanel(1500, 3000, this.docTable, docTree, xmldoc, this);
		getJScrollEditPane().setViewportView(jEditPanel);
		
		BorderLayout borderLayout = new BorderLayout();
		jContentPane.setLayout(borderLayout);
		jContentPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(2,2,2,2));
		borderLayout.setHgap(2);
		borderLayout.setVgap(2);
		
		jContentPane.add(getJScrollEditPane(), java.awt.BorderLayout.CENTER);
		jContentPane.add(getJToolBarPanel(), java.awt.BorderLayout.EAST);
		this.setFrameIcon(new ImageIcon(getClass().getResource("/images/newframe_wiz.gif")));
		this.setContentPane(jContentPane);
		this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() { 
			public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {    
				checkClose();
			} 
			public void internalFrameActivated(javax.swing.event.InternalFrameEvent e) {    
				updateTree();
			}
		});
		this.setTitle("<XForm> - "+xmldoc.getName());
		this.setBounds(0, 0, 300, 400);
		this.show();
	}
	
	/**
	 * Este metodo chequea que se hallan guardado los cambios del formulario,
	 * antes de cerrar la vantana. Si no se han guardado los cambios despliega
	 * un mensaje de advertencia, en el caso opuesto se cierra la ventana.
	 */
	private void checkClose(){
		if(!isSave()){
			int n = JOptionPane.showConfirmDialog(jFrame, 
		    		"Aún no se han guardado los cambios del\n"+
		    		"formulario: "+xmldoc.getName()+".\n\n"+
		    		"Esta seguro que desea cerrar la ventana?", 
		    		"Mensaje de Advertencia", 
					JOptionPane.YES_NO_OPTION);
			if(n == 0) close();
		} else
			close();
	}
	
	/**
	 * Este metodo limpia el DocTable, el DocTree , el Document y cierra la
	 * ventana.
	 */
	private void close(){
		if(docTree.getDocument().getFirstChild() != null) 
			docTree.getDocument().removeChild(docTree.getDocument().getFirstChild());
		
		docTable.getTable().removeAllElements();
		docTable.getJTable().addNotify();
		docTable.getJTable().repaint();
		docTree.getJTree().addNotify();
		docTree.getJTree().updateUI();
		docTree.getJTree().repaint();
		
		this.dispose();
	}
	
	/**
	 * Este metodo verifica si no se esta editando ninguna celda del DocTable,
	 * si no esta ciendo editada ninguna celda se actualisan el DocTree y el 
	 * DocTable.
	 */
	public void updateTree() {
		if(!docTable.getJTable().isEditing()){
			if(docTree.getDocument().getFirstChild() != null) 
				docTree.getDocument().removeChild(docTree.getDocument().getFirstChild());
			
			docTable.getTable().removeAllElements();
			docTable.getJTable().addNotify();
			docTable.getJTable().repaint();
			
			docTree.getDocument().appendChild(xmldoc.getXml());
			docTree.getJTree().addNotify();
			docTree.getJTree().updateUI();
			docTree.getJTree().expandRow(1);
			docTree.getJTree().repaint();
			setControles(jEditPanel.getComponents());
		}/*else 
			JOptionPane.showMessageDialog(this, 
            "Asegurece de que ninguna celda de la tabla 'Propiedades'\n"+
            "este siendo editada.", "Mensaje de Advertencia", 
			JOptionPane.WARNING_MESSAGE);*/
	}
	
	/**
	 * Este metodo actualisa verifica si no se esta editando ninguna celda del DocTable,
	 * si no esta ciendo editada ninguna celda se actualisan el DocTree y el DocTable.
	 */
	private void setControles(Component[] Controles){
		docTree.setControles(Controles);
		docTable.setControles(Controles);
	}
	
	/**
	 * Este metodo retorna el JEditPanel que es el panel que contiene los controles del
	 * formulario que se esta editado.
	 * 
	 * @return	JEditPanel
	 */
	public JEditPanel getJEditPanel() {
		return this.jEditPanel;
	}

	/**
	 * Este metodo permite abilitar o desabilitar los botones del ToolBar.
	 * 
	 * @param	boolean
	 */
	public void selectedButtons(boolean value){
		jButton_input.setSelected(value);
		jButton_label.setSelected(value);
		jButton_output.setSelected(value);
		jButton_upload.setSelected(value);
		jButton_secret.setSelected(value);
		jButton_textArea.setSelected(value);
		jButton_select.setSelected(value);
		jButton_select1.setSelected(value);
		jButton_submit.setSelected(value);
		jButton_trigger.setSelected(value);
		jButton_range.setSelected(value);
		jButton_repeat.setSelected(value);
		jButton_arrow.setSelected(value);
	}
	
	/**
	 * Este metodo permite abilitar el boton jButton_arrow 
	 */
	public void setArrow() {
		jButton_arrow.setSelected(true);
	}

	/**
	 * Si el jToolBarPanel no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JPanel
	 */
	private JPanel getJToolBarPanel() {
		if (jToolBarPanel == null) {
			jToolBarPanel = new JPanel();
			jToolBarPanel.setLayout(new BorderLayout());
			jToolBarPanel.setPreferredSize(new java.awt.Dimension(35, 35));
			jToolBarPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jToolBarPanel;
	}

	/**
	 * Si el jScrollPane no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJToolBarDesktop());
			jScrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}
		return jScrollPane;
	}
	
	/**
	 * Si el jScrollEditPane no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JScrollPane
	 */
	private JScrollPane getJScrollEditPane() {
		if (jScrollEditPane == null) {
			jScrollEditPane = new JScrollPane();
		}
		return jScrollEditPane;
	}

	/**
	 * Si el jToolBarDesktop no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JToolBar
	 */
	private JToolBar getJToolBarDesktop() {
		if (jToolBarDesktop == null) {
			jToolBarDesktop = new JToolBar();
			jToolBarDesktop.setName("Controles (XForms)");
			jToolBarDesktop.add(getJButton_arrow());
			jToolBarDesktop.addSeparator();
			jToolBarDesktop.add(getJButton_input());
			jToolBarDesktop.add(getJButton_label());
			jToolBarDesktop.add(getJButton_output());
			jToolBarDesktop.add(getJButton_upload());
			jToolBarDesktop.add(getJButton_secret());
			jToolBarDesktop.add(getJButton_textArea());
			jToolBarDesktop.add(getJButton_select());
			jToolBarDesktop.add(getJButton_select1());
			jToolBarDesktop.add(getJButton_submit());
			jToolBarDesktop.add(getJButton_trigger());
			jToolBarDesktop.add(getJButton_range());
			jToolBarDesktop.add(getJButton_repeat());
			jToolBarDesktop.addSeparator();
			jToolBarDesktop.add(getJButton_Browser());
			jToolBarDesktop.setOrientation(javax.swing.JToolBar.VERTICAL);
			getJButton_arrow().setSelected(true);
		}
		return jToolBarDesktop;
	}

	/**
	 * Si el jButton_input no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JToggleButton
	 */
	private JToggleButton getJButton_input() {
		if (jButton_input == null) {
			jButton_input = new JToggleButton();
			jButton_input.setIcon(new ImageIcon(getClass().getResource("/images/textfield_obj.gif")));
			jButton_input.setToolTipText("Agregar - Control Input");
			jButton_input.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						getJEditPanel().setDrawMode(JEditPanel.INPUT);
						selectedButtons(false);
						jButton_input.setSelected(true);
					}
			});
		}
		return jButton_input;
	}

	/**
	 * Si el jButton_label no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JToggleButton
	 */
	private JToggleButton getJButton_label() {
		if (jButton_label == null) {
			jButton_label = new JToggleButton();
			jButton_label.setIcon(new ImageIcon(getClass().getResource("/images/label_obj.gif")));
			jButton_label.setToolTipText("Agregar - Control Label");
			jButton_label.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						selectedButtons(false);
						jButton_label.setSelected(true);
						getJEditPanel().setDrawMode(JEditPanel.LABEL);
					}
			});
		}
		return jButton_label;
	}

	/**
	 * Si el jButton_output no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JToggleButton
	 */
	private JToggleButton getJButton_output() {
		if (jButton_output == null) {
			jButton_output = new JToggleButton();
			jButton_output.setIcon(new ImageIcon(getClass().getResource("/images/label16.gif")));
			jButton_output.setToolTipText("Agregar - Control Output");
			jButton_output.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						selectedButtons(false);
						jButton_output.setSelected(true);
						getJEditPanel().setDrawMode(JEditPanel.OUTPUT);
					}
			});
		}
		return jButton_output;
	}

	/**
	 * Si el jButton_upload no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JToggleButton
	 */
	private JToggleButton getJButton_upload() {
		if (jButton_upload == null) {
			jButton_upload = new JToggleButton();
			jButton_upload.setIcon(new ImageIcon(getClass().getResource("/images/browser.gif")));
			jButton_upload.setText("");
			jButton_upload.setToolTipText("Agregar - Control Upload");
			jButton_upload.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						selectedButtons(false);
						jButton_upload.setSelected(true);
						getJEditPanel().setDrawMode(JEditPanel.UPLOAD);
					}
			});
		}
		return jButton_upload;
	}

	/**
	 * Si el jButton_secret no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JToggleButton
	 */
	private JToggleButton getJButton_secret() {
		if (jButton_secret == null) {
			jButton_secret = new JToggleButton();
			jButton_secret.setIcon(new ImageIcon(getClass().getResource("/images/passwordfield_obj.gif")));
			jButton_secret.setText("");
			jButton_secret.setToolTipText("Agregar - Control Secret");
			jButton_secret.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						selectedButtons(false);
						jButton_secret.setSelected(true);
						getJEditPanel().setDrawMode(JEditPanel.SECRET);
					}
			});
		}
		return jButton_secret;
	}

	/**
	 * Si el jButton_textArea no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JToggleButton
	 */
	private JToggleButton getJButton_textArea() {
		if (jButton_textArea == null) {
			jButton_textArea = new JToggleButton();
			jButton_textArea.setIcon(new ImageIcon(getClass().getResource("/images/textarea_obj.gif")));
			jButton_textArea.setText("");
			jButton_textArea.setToolTipText("Agregar - Control TextArea");
			jButton_textArea.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						selectedButtons(false);
						jButton_textArea.setSelected(true);
						getJEditPanel().setDrawMode(JEditPanel.TEXTAREA);
					}
			});
		}
		return jButton_textArea;
	}

	/**
	 * Si el jButton_select no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JToggleButton
	 */
	private JToggleButton getJButton_select() {
		if (jButton_select == null) {
			jButton_select = new JToggleButton();
			jButton_select.setIcon(new ImageIcon(getClass().getResource("/images/listbox_obj.gif")));
			jButton_select.setText("");
			jButton_select.setToolTipText("Agregar - Control Select");
			jButton_select.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						selectedButtons(false);
						jButton_select.setSelected(true);
						getJEditPanel().setDrawMode(JEditPanel.SELECT);
					}
			});
		}
		return jButton_select;
	}

	/**
	 * Si el jButton_select1 no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JToggleButton
	 */
	private JToggleButton getJButton_select1() {
		if (jButton_select1 == null) {
			jButton_select1 = new JToggleButton();
			jButton_select1.setIcon(new ImageIcon(getClass().getResource("/images/choice_obj.gif")));
			jButton_select1.setText("");
			jButton_select1.setToolTipText("Agregar - Control Select1");
			jButton_select1.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						selectedButtons(false);
						jButton_select1.setSelected(true);
						getJEditPanel().setDrawMode(JEditPanel.SELECT1);
					}
			});
		}
		return jButton_select1;
	}

	/**
	 * Si el jButton_submit no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JToggleButton
	 */
	private JToggleButton getJButton_submit() {
		if (jButton_submit == null) {
			jButton_submit = new JToggleButton();
			jButton_submit.setIcon(new ImageIcon(getClass().getResource("/images/button_obj.gif")));
			jButton_submit.setToolTipText("Agregar - Control Submit");
			jButton_submit.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						selectedButtons(false);
						jButton_submit.setSelected(true);
						getJEditPanel().setDrawMode(JEditPanel.SUBMIT);
					}
			});
		}
		return jButton_submit;
	}

	/**
	 * Si el jButton_trigger no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JToggleButton
	 */
	private JToggleButton getJButton_trigger() {
		if (jButton_trigger == null) {
			jButton_trigger = new JToggleButton();
			jButton_trigger.setIcon(new ImageIcon(getClass().getResource("/images/butt16.gif")));
			jButton_trigger.setToolTipText("Agregar - Control Trigger");
			jButton_trigger.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						selectedButtons(false);
						jButton_trigger.setSelected(true);
						getJEditPanel().setDrawMode(JEditPanel.TRIGGER);
					}
			});
		}
		return jButton_trigger;
	}

	/**
	 * Si el jButton_range no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JToggleButton
	 */
	private JToggleButton getJButton_range() {
		if (jButton_range == null) {
			jButton_range = new JToggleButton();
			jButton_range.setIcon(new ImageIcon(getClass().getResource("/images/slider_obj.gif")));
			jButton_range.setToolTipText("Agregar - Control Range");
			jButton_range.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						selectedButtons(false);
						jButton_range.setSelected(true);
						getJEditPanel().setDrawMode(JEditPanel.RANGE);
					}
			});
		}
		return jButton_range;
	}

	/**
	 * Si el jButton_arrow no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JToggleButton
	 */
	private JToggleButton getJButton_arrow() {
		if (jButton_arrow == null) {
			jButton_arrow = new JToggleButton();
			jButton_arrow.setIcon(new ImageIcon(getClass().getResource("/images/arrow16.gif")));
			jButton_arrow.setToolTipText("...");
			jButton_arrow.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						selectedButtons(false);
						jButton_arrow.setSelected(true);
						getJEditPanel().setDrawMode(JEditPanel.ARROW);
					}
			});
		}
		return jButton_arrow;
	}

	/**
	 * Si el jButton_repeat no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JToggleButton
	 */
	private JToggleButton getJButton_repeat() {
		if (jButton_repeat == null) {
			jButton_repeat = new JToggleButton();
			jButton_repeat.setIcon(new ImageIcon(getClass().getResource("/images/exprs_only.gif")));
			jButton_repeat.setToolTipText("Agregar - Control Repeat");
			jButton_repeat.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						selectedButtons(false);
						jButton_repeat.setSelected(true);
						getJEditPanel().setDrawMode(JEditPanel.REPEAT);
					}
			});
		}
		return jButton_repeat;
	}

	/**
	 * Si el jButton_Browser no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JToggleButton
	 */
	private JButton getJButton_Browser() {
		if (jButton_Browser == null) {
			jButton_Browser = new JButton();
			jButton_Browser.setIcon(new ImageIcon(getClass().getResource("/images/browser_obj.gif")));
			jButton_Browser.setToolTipText("Vista Previa");
			jButton_Browser.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						jFrame.NewBrowser();
					}
			});
		}
		return jButton_Browser;
	}
	
	/**
	 * Este metodo retorna XmlDoc.
	 * 
	 * @return	XmlDoc
	 */
	public XmlDoc getXML_Doc(){
		return this.xmldoc;
	}
	
	/**
	 * Este metodo retorna un boolean que indica si se salvaron los cambios
	 * del forumlario o no.
	 * 
	 * @return	boolean
	 */
	public boolean isSave() {
		return this.save;
	}
	
	/**
	 * Este metodo retorna un boolean que indica si se salvaron los cambios
	 * del forumlario por primera vez o no.
	 * 
	 * @return	boolean
	 */
	public boolean isSaved() {
		return this.saved;
	}
	
	/**
	 * Este metodo setea si fueron guardados los cambios o no, si fueron 
	 * guardados setea el menu Guardar desactivado, en el caso contrario 
	 * lo activa.
 	 * 
	 * @param	boolean
	 */
	public void setSave(boolean value){
		if(!value) this.jFrame.setMenuGuardar(true);
		else this.jFrame.setMenuGuardar(false);
		this.save = value;
	}
	
	/**
	 * Este metodo setea si fueron guardados los cambios por primera ves 
	 * o no, si fueron guardados setea el menu Guardar desactivado, en el 
	 * caso contrario lo activa.
 	 * 
	 * @return	boolean
	 */
	public void setSaved(boolean value){
		if(!value) this.jFrame.setMenuGuardar(true);
		else this.jFrame.setMenuGuardar(false);
		this.save = value;
		this.saved = value;
	}
	
	/**
	 * Este metodo retorna el nombre del formulario que se seta editando.
 	 * 
	 * @return	String
	 */
	public String getName() {
		return xmldoc.getName();
	}
} 

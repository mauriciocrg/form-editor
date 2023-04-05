/* 
* nombre del archivo: FirstFrame.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Implementa la ventana principal de la aplicación.
*					Y contiene el main.
*@Fecha Actualización:	25/01/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------- 
*/
package frames;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import control.ClipBoard;
import control.JControl;
import doc.XmlDoc;
import doc.tree.DocTree;
import doc.table.DocTable;
import frames.internalFrame.JInternalEditFrame;

/*
 * Esta clase extiende de JFrame y despliega la ventana 
 * principal de la aplicación, crea un JFrame donde se 
 * alojan un JToolBar, JDesktopPane, JMenuBar, DocTree, 
 * DocTable. E implementa el main.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
 */

public class FirstFrame extends JFrame {
	private DisplayMode displayMode;						//Me permite obtener la resolucion del sistema.
	
	//private JInternalTextFrame jInternalTextFrame = null;	
	private JInternalEditFrame jInternalEditFrame = null;	// Es el JInternalFrame que permite editar formularios.
	private JSplitPane jSplitPane = null;					/* Me permite separar las secciones entre el JDesktopPane 
															 * y el JTable con el JTree.*/
	private JDesktopPane jDesktopPane = null;		// Contiene JInternalFrames de edición de formularios.
	
	private Document document;						// Contiene el documento del Formulario que esta siendo editado.
	private XmlDoc xmldoc = null;					/* Contiene el nodo principal del Documento del Formulario que esta
													 * siendo editado.*/
	private DocTree docTree = null;					// Muestra la estructura del Documento.
	private DocTable docTable = null;				/* Mustra las propiedades del control del formulario que se esta 
													 * editando.*/
	private JPanel jContentPane = null;				// Contiene todos los componentes de la ventana principal.
	private JPanel jPanelLeft = null;				// Contiene los componentes que estan a la derecha del JSplitPane.
	private JPanel jPanelNorth = null;				// Contiene el JToolBar.
	private JPanel jPanelStatusBar = null;			// Contiene el label que reprecenta un Status Bar.
	 
	private JMenuBar jMenuBar = null;				// Es el menu bar.
	private JMenu jMenu_Archivo = null;				// Es la opcion del menu "Archivo".
	private JMenu jMenu_Nuevo = null;				// Es la opcion del menu "Nuevo".
	private JMenu jMenu_Edicion = null;				// Es la opcion del menu "Edicion".
	private JMenu jMenu_Ayuda = null;				// Es la opcion del menu "Ayuda".
	private JMenuItem jMenuItem_Formulario = null;	// Es la opcion del menu "Nuevo Formulario".
	private JMenuItem jMenuItem_Salir = null;		// Es la opcion del menu "Salir".
	private JMenuItem jMenuItem_Abrir = null;		// Es la opcion del menu "Abrir".
	private JMenuItem jMenuItem_Guardar = null;		// Es la opcion del menu "Guardar".
	private JMenuItem jMenuItem_GuardarComo = null;	// Es la opcion del menu "Guardar Como".
	private JMenuItem jMenuItem_XML = null;			// Es la opcion del menu "Nuevo XML".
	private JMenuItem jMenuItem_Copy = null;	// Es la opcion del menu "Copiar".
	private JMenuItem jMenuItem_Cut = null;	// Es la opcion del menu "Cortar".
	private JMenuItem jMenuItem_Paste = null;// Es la opcion del menu "Pegar".	
	private JMenuItem jMenuItem_Del = null;	// Es la opcion del menu "Borrar".
	private JMenuItem jMenuItem_AcercaDe = null;	// Es la opcion del menu "Acerca De".
	
	private JToolBar jToolBar = null;				// Es el Tool Bar.  

	private JLabel StatusBar = null;				// Es el Label que reprecenta un Status Bar.

	private JButton jButton_XForm = null;			// Es un boton del Tool Bar.
	private JButton jButton_XML = null;				// Es un boton del Tool Bar.
	
	private JFrame jFrame;							// Guarda la instancia de la Ventana Principal.
	private InternalFrameListener internalFrameListener = null; /* Permite administrar los eventos de un
																 * JInternalFrame.*/
	private File file;								// Permite manejar el archivo del formulario editado. 
	private JFileChooser jFileChooser;				/* Permite determinar el Path del archivo del formulario 
													 * editado.*/
	private DocumentBuilderFactory factory;			// Permite construir el documento del formulario.
	
	/**
	 * Este metodo es el constructor de la clase, se encarga de crear todos los componentes 
	 * que integran la ventana principal, e inicializa variables de manejo global.
	 */
	public FirstFrame () {
		this.jFrame = this;
		this.setTitle("<<XForms - Designer>>");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/orderproj.gif")));
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		displayMode = env.getDefaultScreenDevice().getDisplayMode();
		this.setSize(displayMode.getWidth(), displayMode.getHeight() - 50);
		this.setContentPane(getJContentPane());
		this.setJMenuBar(getJMenuBar());
		this.docTree.setDocTable(this.docTable);
		this.setDefaultCloseOperation(JFrame.NORMAL);
		this.factory = DocumentBuilderFactory.newInstance();
		this.jFileChooser = new JFileChooser();
		this.jFileChooser.setAutoscrolls(true);
		this.jFileChooser.setMultiSelectionEnabled(false);
		this.jFileChooser.setFileFilter(new XmlFileFilter());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		public void windowClosing(java.awt.event.WindowEvent e) {
				CheckSave();
			}
		});
	}
	
	/**
	 * Este metodo chequea que los formularios que estan siendo editados,
	 * hallan sido guardados. En caso de existir al menos un formulario 
	 * que no se halla sido guardado, despliega un mensaje. En otro caso 
	 * cierra la aplicación.
	 */
	private void CheckSave() {
		if(!CheckSaveInternalFrame()) {
			int n = JOptionPane.showConfirmDialog(this, 
		    		"Existe al menos un Formulario que esta siendo editado,\n"+
		    		"y aún no se ha guardado.\n\n"+
		    		"Esta seguro que desea cerrar la aplicación?", 
		    		"Mensaje de Advertencia", 
					JOptionPane.YES_NO_OPTION);
			if(n == 0) System.exit(0);
		}else
			System.exit(0);
	}
	
	/**
	 * Este metodo chequea que los formularios que estan siendo editados,
	 * hallan sido guardados. En caso de existir al menos un formulario 
	 * que no se halla sido guardado, retorna false. En otro caso 
	 * retorna true.
	 * 
	 * @return	boolean		es falso si existe al menos un formulario no guardado,
	 * 						es true si todos los formularios estan guardados.
	 */
	private boolean CheckSaveInternalFrame() {
		int size = jDesktopPane.getComponentCount();
		for(int i = 0; i < size; i++){
			if(getJDesktopPane().getSelectedFrame().getClass().equals(JInternalEditFrame.class))
			if(!((JInternalEditFrame)getJDesktopPane().getSelectedFrame()).isSave())
				return false;
		}
		return true;
	}
	
	/**
	 * Si el jContentPane no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setSize(382, 186);
			jContentPane.add(getJPanelNorth(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanelStatuBar(), java.awt.BorderLayout.SOUTH);
			
			internalFrameListener = new javax.swing.event.InternalFrameAdapter(){ 
				public void internalFrameActivated(javax.swing.event.InternalFrameEvent e) {
					if(getJDesktopPane().getSelectedFrame().getClass().equals(JInternalEditFrame.class)) {
						((JInternalEditFrame) getJDesktopPane().getSelectedFrame()).updateTree();
						jFrame.setTitle("<<XForms - Designer>> - "+((JInternalEditFrame) getJDesktopPane().getSelectedFrame()).getXML_Doc().getName());
						jMenuItem_GuardarComo.setEnabled(true);
						if(!((JInternalEditFrame)getJDesktopPane().getSelectedFrame()).isSave())
							jMenuItem_Guardar.setEnabled(true);
				    }
				}
				public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent e) {    
					jFrame.setTitle("<<XForms>>");
					jMenuItem_Guardar.setEnabled(false);
					jMenuItem_GuardarComo.setEnabled(false);
				} 
			};
		}
		return jContentPane;
	}

	/**
	 * Si el JSplitPane no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if(jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getJPanelLeft());
			jSplitPane.setSize(223, 126);
			jSplitPane.setRightComponent(getJDesktopPane());
		}
		return jSplitPane;
	}
	
	/**
	 * Si el jMenu_Archivo no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenu
	 */
	private JMenu getJMenu_Archivo() {
		if (jMenu_Archivo == null) {
			JSeparator jSeparator1 = new JSeparator();
			jMenu_Archivo = new JMenu();
			jMenu_Archivo.setText("Archivo");
			jMenu_Archivo.setEnabled(true);
			jMenu_Archivo.add(getJMenu_Nuevo());
			jMenu_Archivo.add(new JSeparator());
			jMenu_Archivo.add(getJMenuItem_Abrir());
			jMenu_Archivo.add(new JSeparator());
			jMenu_Archivo.add(getJMenuItem_Guardar());
			jMenu_Archivo.add(getJMenuItem_GuardarComo());
			jMenu_Archivo.add(new JSeparator());
			jMenu_Archivo.add(getJMenuItem_Salir());
		}
		return jMenu_Archivo;
	}

	/**
	 * Si el jDesktopPane no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JDesktopPane
	 */
	private JDesktopPane getJDesktopPane() {
		if (jDesktopPane == null) {
			jDesktopPane = new JDesktopPane();
			/*jDesktopPane.addContainerListener(new java.awt.event.ContainerAdapter() {   
			 public void componentAdded(java.awt.event.ContainerEvent e) {
			 if(jDesktopPane.getComponentCount() > 0) {
			 enabledButtons(true);
			 }
			 } 
			 public void componentRemoved(java.awt.event.ContainerEvent e) {
			 if(jDesktopPane.getComponentCount() <= 0) {
			 enabledButtons(false);
			 }
			 }
			 });*/
		}
		return jDesktopPane;
	}

	/**
	 * Si el jMenuBar no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenuBar
	 */
	public JMenuBar getJMenuBar() {
		if (jMenuBar == null) {
			jMenuBar = new JMenuBar();
			jMenuBar.add(getJMenu_Archivo());
			jMenuBar.add(getJMenu_Edicion());
			jMenuBar.add(getJMenu_Ayuda());
		}
		return jMenuBar;
	}

	/**
	 * Si el jMenuItem_Formulario no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenuItem
	 */
	private JMenuItem getJMenuItem_Formulario() {
		if (jMenuItem_Formulario == null) {
			jMenuItem_Formulario = new JMenuItem();
			jMenuItem_Formulario.setText("Formulario");
			jMenuItem_Formulario.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						NewJInternalEditFrame();
					}
			});
		}
		return jMenuItem_Formulario;
	}

	/**
	 * Este metodo despliega un JInputDialogBox, el cual da la posibilidad de ingresar el nombre
	 * del formulario que va a ser editado. Y luego crea una nueva JInternalEditFrame para editar
	 * dicho formulario.	
	 */
	private void NewJInternalEditFrame() {
		String name = (String)JOptionPane.showInputDialog(this, 
				"Nombre del Nuevo Formulario: ",
				"Nuevo Formulario", -1,
				null,
				null, 
				null);
		if(name != null && !name.equals("")) {
			xmldoc = new XmlDoc(name, docTree.getDocument());
			
			if(docTree.getDocument().getFirstChild() != null) docTree.getDocument().removeChild(docTree.getDocument().getFirstChild());			
			docTree.getDocument().appendChild(xmldoc.getXml());
			docTree.getJTree().addNotify();
			docTree.getJTree().updateUI();
			docTree.getJTree().expandRow(1);
			docTree.getJTree().repaint();
			
			jInternalEditFrame = new JInternalEditFrame(docTable, docTree, xmldoc, this); 
			jDesktopPane.add(jInternalEditFrame);
			jInternalEditFrame.moveToFront();
			jInternalEditFrame.addInternalFrameListener(internalFrameListener);
			try {
				jInternalEditFrame.setSelected(true);
				jInternalEditFrame.setMaximum(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Este metodo recive como parametro un JInternalEdirFrame, y lo despliega 
	 * en el JDesktopPane (Esto sucede cuando se habre un archivo xml que contiene
	 * la definicion de un formulario).
	 * 
	 * @param		JInternalEditFrame.
	 */
	public void addJInternalEditFrame(JInternalEditFrame jInternalEditFrame){
		this.jInternalEditFrame = jInternalEditFrame;
		jDesktopPane.add(this.jInternalEditFrame);
		this.jInternalEditFrame.moveToFront();
		this.jInternalEditFrame.addInternalFrameListener(internalFrameListener);
		try {
			this.jInternalEditFrame.setSelected(true);
			this.jInternalEditFrame.setMaximum(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		getDocTree().getJTree().addNotify();
		getDocTree().getJTree().updateUI();
		getDocTree().getJTree().expandRow(1);
		getDocTree().getJTree().repaint();
		((JInternalEditFrame)getJDesktopPane().getSelectedFrame()).setSave(true);
		((JInternalEditFrame)getJDesktopPane().getSelectedFrame()).setSaved(true);
	}
	
	/**
	 * Si el jMenuItem_Salir no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenuItem
	 */
	private JMenuItem getJMenuItem_Salir() {
		if (jMenuItem_Salir == null) {
			jMenuItem_Salir = new JMenuItem();
			jMenuItem_Salir.setText("Salir");
			jMenuItem_Salir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CheckSave();
				}
			});
		}
		return jMenuItem_Salir;
	}

	/**
	 * Si el jMenu_Nuevo no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenu
	 */
	private JMenu getJMenu_Nuevo() {
		if (jMenu_Nuevo == null) {
			JSeparator jSeparator2 = new JSeparator();
			jMenu_Nuevo = new JMenu();
			jMenu_Nuevo.setText("Nuevo");
			//jMenu_Nuevo.add(getJMenuItem_XML());
			//jMenu_Nuevo.add(new JSeparator());
			jMenu_Nuevo.add(getJMenuItem_Formulario());
		}
		return jMenu_Nuevo;
	}

	/**
	 * Si el jMenuItem_XML no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenuItem
	 */
/*	private JMenuItem getJMenuItem_XML() {
		if (jMenuItem_XML == null) {
			jMenuItem_XML = new JMenuItem();
			jMenuItem_XML.setText("Archivo - XML");
			jMenuItem_XML.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						jInternalTextFrame = new JInternalTextFrame();
						jDesktopPane.add(jInternalTextFrame);
						jInternalTextFrame.moveToFront();
					}
			});
		}
		return jMenuItem_XML;
	}*/

	/**
	 * Si el jToolBar no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
		}
		jToolBar.setName("Barra de Herramientas");
		jToolBar.add(getJButton_XForm());
	//	jToolBar.add(getJButton_XML());
		return jToolBar;
	}

	/**
	 * Si el jButton_XForm no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JButton
	 */
	private JButton getJButton_XForm() {
		if (jButton_XForm == null) {
			jButton_XForm = new JButton();
			jButton_XForm.setIcon(new ImageIcon(getClass().getResource("/images/newframe_wiz.gif")));
			jButton_XForm.setToolTipText("Crear un Nuevo XForm");
			jButton_XForm.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						NewJInternalEditFrame();
					}
			});
		}
		return jButton_XForm;
	}

	/**
	 * Si el jButton_XML no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JButton
	 */
/*	private JButton getJButton_XML() {
		if (jButton_XML == null) {
			jButton_XML = new JButton();
			jButton_XML.setIcon(new ImageIcon(getClass().getResource("/images/html_tag_obj.gif")));
			jButton_XML.setToolTipText("Crear un Nuevo XML");
			jButton_XML.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jInternalTextFrame = new JInternalTextFrame();
					jDesktopPane.add(jInternalTextFrame);
					jInternalTextFrame.moveToFront();
				}
			});
		}
		return jButton_XML;
	}*/

	/**
	 * Si el jPanelLeft no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JPanel
	 */
	private JPanel getJPanelLeft() {
		if (jPanelLeft == null) {
			jPanelLeft = new JPanel();
			jPanelLeft.setLayout(new BoxLayout(jPanelLeft, BoxLayout.Y_AXIS));
			docTree = new DocTree();
			docTable = new DocTable();
			jPanelLeft.add(docTree.getJToolBar());
			jPanelLeft.add(docTable.getJToolBar());
			jPanelLeft.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
		}
		return jPanelLeft;
	}
	
	/**
	 * Si el jPanelNorth no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JPanel
	 */
	private JPanel getJPanelNorth() {
		if (jPanelNorth == null) {
			jPanelNorth = new JPanel();
			jPanelNorth.setLayout(new BoxLayout(jPanelNorth, BoxLayout.X_AXIS));
			jPanelNorth.add(getJToolBar());
			jPanelNorth.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
		}
		return jPanelNorth;
	}
	
	/**
	 * Si el jPanelStatusBar no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JPanel
	 */    
	private JPanel getJPanelStatuBar() {
		if (jPanelStatusBar == null) {
			StatusBar = new JLabel();
			jPanelStatusBar = new JPanel();
			jPanelStatusBar.setLayout(new BorderLayout());
			StatusBar.setBounds(5, 5, 10, 10);
			StatusBar.setText("...");
			jPanelStatusBar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			jPanelStatusBar.add(StatusBar, null);
		}
		return jPanelStatusBar;
	}

	/**
	 * Si el jMenuItem_Copy no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenuItem
	 */
	public JMenuItem getJMenuItem_Copy() {
		if (jMenuItem_Copy == null) {
			jMenuItem_Copy = new JMenuItem();
			jMenuItem_Copy.setText("Copiar");
			jMenuItem_Copy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalEditFrame internalFrame =(JInternalEditFrame) getJDesktopPane().getSelectedFrame();
					
					if(internalFrame != null){
						JComponent control = internalFrame.getJEditPanel().getSelectedJControl();
						if(control != null)	ClipBoard.setJComponent(control);
					}
				}
			});
		}
		return jMenuItem_Copy;
	}
	
	/**
	 * Si el jMenuItem_Cut no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenuItem
	 */
	public JMenuItem getJMenuItem_Cut() {
		if (jMenuItem_Cut == null) {
			jMenuItem_Cut = new JMenuItem();
			jMenuItem_Cut.setText("Cortar");
			jMenuItem_Cut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalEditFrame internalFrame =(JInternalEditFrame) getJDesktopPane().getSelectedFrame();
					
					if(internalFrame != null){
						JComponent control = internalFrame.getJEditPanel().getSelectedJControl();
						if(control != null){	
							ClipBoard.setJComponent(control);
							internalFrame = (JInternalEditFrame) getJDesktopPane().getSelectedFrame();
							internalFrame.getJEditPanel().delSelectedJControl();
						}
					}
				}
			});
		}
		return jMenuItem_Cut;
	}
	
	/**
	 * Si el jMenuItem_Paste no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenuItem
	 */
	public JMenuItem getJMenuItem_Paste() {
		if (jMenuItem_Paste == null) {
			jMenuItem_Paste = new JMenuItem();
			jMenuItem_Paste.setText("Pegar");
			jMenuItem_Paste.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalEditFrame internalFrame =(JInternalEditFrame) getJDesktopPane().getSelectedFrame();
					if(internalFrame != null){
						JControl control = (JControl) ClipBoard.getCopyOfJComponent(internalFrame.getJEditPanel(),
																				    internalFrame.getJEditPanel().getXmlDoc());
						internalFrame.getJEditPanel().addControl(control);
						ClipBoard.setJComponent(control);
					}
				}
			});
		}
		return jMenuItem_Paste;
	}
	
	/**
	 * Si el jMenuItem_Del no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenuItem
	 */
	public JMenuItem getJMenuItem_Del() {
		if (jMenuItem_Del == null) {
			jMenuItem_Del = new JMenuItem();
			jMenuItem_Del.setText("Borrar");
			jMenuItem_Del.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalEditFrame internalFrame = (JInternalEditFrame) getJDesktopPane().getSelectedFrame();
					if(internalFrame != null){
						internalFrame.getJEditPanel().delSelectedJControl();
					}
				}
			});
		}
		return jMenuItem_Del;
	}

	/**
	 * Si el jMenuItem_Abrir no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenuItem
	 */
	private JMenuItem getJMenuItem_Abrir() {
		if (jMenuItem_Abrir == null) {
			jMenuItem_Abrir = new JMenuItem();
			jMenuItem_Abrir.setText("Abrir");
			jMenuItem_Abrir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					open(); 
				}
			});
		}
		return jMenuItem_Abrir;
	}
	
	/**
	 * Si el jMenuItem_Guardar no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenuItem
	 */
	private JMenuItem getJMenuItem_Guardar() {
		if (jMenuItem_Guardar == null) {
			jMenuItem_Guardar = new JMenuItem();
			jMenuItem_Guardar.setText("Guardar");
			jMenuItem_Guardar.setEnabled(false);
			jMenuItem_Guardar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(getJDesktopPane().getSelectedFrame().getClass().equals(JInternalEditFrame.class))
					if(!((JInternalEditFrame)getJDesktopPane().getSelectedFrame()).isSaved()) 
						saveAs();
					else 
						save();	  
				}
			});
		}
		return jMenuItem_Guardar;
	}

	/**
	 * Si el jMenuItem_GuardarComo no esta creado, este metodo lo crea y lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenuItem
	 */
	private JMenuItem getJMenuItem_GuardarComo() {
		if (jMenuItem_GuardarComo == null) {
			jMenuItem_GuardarComo = new JMenuItem();
			jMenuItem_GuardarComo.setText("Guardar Como");
			jMenuItem_GuardarComo.setEnabled(false);
			jMenuItem_GuardarComo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(getJDesktopPane().getSelectedFrame().getClass().equals(JInternalEditFrame.class))
						saveAs();	 
				}
			});
		}
		return jMenuItem_GuardarComo;
	}
	
	/**
	 * Este metodo se encagra de desplegar un jFileChooser que permite que el 
	 * usuario pueda seleccionar el lugar donde quiere guardar el archivo con 
	 * la definicion del formulario editado. 
	 */
	private void saveAs() {
		boolean flag = false;
		jFileChooser.setDialogTitle("Guardar Archivo - (XML)");
		
		do{
			jFileChooser.setSelectedFile(new File("C:/" + ((JInternalEditFrame) getJDesktopPane().getSelectedFrame()).getName() + ".xml"));
			int returnVal = jFileChooser.showSaveDialog(jFrame);
			file = jFileChooser.getSelectedFile();
			 
			String path = "";
			if(file != null && returnVal == JFileChooser.APPROVE_OPTION){
				try {
					path = file.getCanonicalPath();
				} catch (IOException e) {	e.printStackTrace();	}
				
				if(!jFileChooser.accept(file))
					file.renameTo(new File(path + file.getName() + ".xml"));
				if(file.isFile()){
					int n = JOptionPane.showConfirmDialog(this, 
				    		"El archivo: "+file.getName()+" ya Existe.\n\n"+
				    		"Esta seguro que desea reemplazarlo?", 
				    		"Mensaje de Advertencia", 
							JOptionPane.YES_NO_OPTION);
					if(n == 0){ 
						flag = true;
						save();
					}
				}else{
					flag = true;
					save();
				}
			}else
				flag = true;
		}
		while(!flag);
	}

	/**
	 * Este metodo se encarga de guardar la definicion del formulario editado en
	 * un archivo xml.
	 */
	private void save()	{
		DOMSource source = new DOMSource(((JInternalEditFrame) getJDesktopPane().getSelectedFrame()).getXML_Doc().getXml());
		try {
		    Transformer transformer = null;
			DocumentBuilder builder = factory.newDocumentBuilder();
			TransformerFactory tFactory = TransformerFactory.newInstance();
	        transformer = tFactory.newTransformer();
	        StreamResult result = new StreamResult(file);
	        transformer.transform(source, result);
			((JInternalEditFrame)getJDesktopPane().getSelectedFrame()).setSave(true);
			((JInternalEditFrame)getJDesktopPane().getSelectedFrame()).setSaved(true);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Este metodo se encagra de desplegar un jFileChooser que permite que el 
	 * usuario pueda seleccionar el archivo xml con la definición de un 
	 * formulario, luego lo abre para poder continuar editandolo. 
	 */
	private void open(){
		jFileChooser.setDialogTitle("Abrir Formulario - (XML)");
		int returnVal = jFileChooser.showOpenDialog(jFrame);
		file = jFileChooser.getSelectedFile();
		if(file != null && returnVal == JFileChooser.APPROVE_OPTION){
			try {
				DocumentBuilder builder = factory.newDocumentBuilder();
				document = builder.parse(file);
				OpenProcesor op = new OpenProcesor(document, this);  
			} catch (ParserConfigurationException e0) {
				// TODO Auto-generated catch block
				e0.printStackTrace();
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 * Este metodo hace que el jMenuItem_Guardar pueda ete 
	 * abilitado o no. 
	 * 
	 * @param	boolean		valor indica si el jMenuItem_Guardar es bailitado o no.
	 */
	public void setMenuGuardar(boolean valor){
		jMenuItem_Guardar.setEnabled(valor);
	}

	/**
	 * Si el jMenu_Edicion no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenu
	 */
	private JMenu getJMenu_Edicion() {
		if (jMenu_Edicion == null) {
			jMenu_Edicion = new JMenu();
			jMenu_Edicion.setText("Edición");
			jMenu_Edicion.add(getJMenuItem_Cut());
			jMenu_Edicion.add(getJMenuItem_Copy());
			jMenu_Edicion.add(getJMenuItem_Paste());
			jMenu_Edicion.add(new JSeparator());
			jMenu_Edicion.add(getJMenuItem_Del());
			jMenu_Edicion.addMouseListener(new java.awt.event.MouseAdapter() { 
				public void mouseEntered(java.awt.event.MouseEvent e) {    
					if(ClipBoard.containElement())
						jMenuItem_Paste.setEnabled(true);		
		        	else
		        		jMenuItem_Paste.setEnabled(false);
					
					jMenuItem_Copy.setEnabled(false);
					jMenuItem_Cut.setEnabled(false);
					jMenuItem_Del.setEnabled(false);
					
					JInternalEditFrame internalFrame =(JInternalEditFrame) getJDesktopPane().getSelectedFrame();
					
					if(internalFrame != null){
						JComponent control = internalFrame.getJEditPanel().getSelectedJControl();
						
						if(control != null)	{
							jMenuItem_Copy.setEnabled(true);
							jMenuItem_Cut.setEnabled(true);
							jMenuItem_Del.setEnabled(true);
						}
					}
				}
			});
		}
		return jMenu_Edicion;
	}

	/**
	 * Si el jMenu_Ayuda no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenu
	 */
	private JMenu getJMenu_Ayuda() {
		if (jMenu_Ayuda == null) {
			jMenu_Ayuda = new JMenu();
			jMenu_Ayuda.setText("Ayuda");
			jMenu_Ayuda.add(getJMenuItem_AcercaDe());
		}
		return jMenu_Ayuda;
	}

	/**
	 * Si el jMenuItem_AcercaDe no esta creado, este metodo lo crea, lo inicializa, 
	 * y luego lo retorna. 	
	 * 	
	 * @return	JMenuItem
	 */
	private JMenuItem getJMenuItem_AcercaDe() {
		if (jMenuItem_AcercaDe == null) {
			jMenuItem_AcercaDe = new JMenuItem();
			jMenuItem_AcercaDe.setText("Acerca de");
			jMenuItem_AcercaDe.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						//NewBrowser();
					}
				});
		}
		return jMenuItem_AcercaDe;
	}

	/**
	 * Este metodo se encarga de desplegar un JWebBrowser con la vista previa del 
	 * formulario que está siendo editado. 
	 */
	public void NewBrowser() {
		
	}

	/**
	 * Este metodo retorna el DocTree de la Ventana Principal.
	 * 
	 * @return	DocTree
	 */
	public DocTree getDocTree(){
		return this.docTree;
	}
	
	/**
	 * Este metodo retorna el DocTable de la Ventana Principal.
	 * 
	 * @return	DocTable
	 */
	public DocTable getDocTable(){
		return this.docTable;
	}

   	/**
	 * Este metodo es el MAIN de la aplicación, que setea la forma de visualización de la
	 * aplicación y luego crea una instancia de la Ventana Principal (FirstFrame).
	 * 
	 * @param	String[]
	 */
	public static void main(String[] args) {
		try {
	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    } catch (Exception e) { }
	    FirstFrame V = new FirstFrame();
		V.show();
	}
}

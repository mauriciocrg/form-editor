import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
/*
 * Created on 15-mar-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author mrodriguez
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class prueba extends JFrame {

	private javax.swing.JPanel jContentPane = null;

	private JButton jButton = null;
	private JPopupMenu jPopupMenu = null;
	private JMenuItem jMenuItem = null;
	private JMenuItem jMenuItem1 = null;
	private JMenuBar jJMenuBar = null;
	private JMenu jMenu = null;
	/**
	 * This is the default constructor
	 */
	public prueba() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setJMenuBar(getJJMenuBar());
		this.setSize(300,200);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJPopupMenu(), null);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(138, 30, 96, 24);
			 MouseListener popupListener = new PopupListener(getJPopupMenu());
			 jButton.addMouseListener(popupListener);
		}
		return jButton;
	}
	/**
	 * This method initializes jPopupMenu	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */    
	private JPopupMenu getJPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			jPopupMenu.setBounds(172, 38, 64, 111);
			jPopupMenu.setLabel("Menu");
			jPopupMenu.setName("Menu");
			jPopupMenu.add(getJMenuItem());
			jPopupMenu.add(getJMenuItem1());
		}
		return jPopupMenu;
	}
	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setName("item 1");
			jMenuItem.setText("item 1");
		}
		return jMenuItem;
	}
	/**
	 * This method initializes jMenuItem1	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */    
	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setName("item 2");
			jMenuItem1.setText("item 2");
		}
		return jMenuItem1;
	}
	
	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */    
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenu());
		}
		return jJMenuBar;
	}
	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */    
	private JMenu getJMenu() {
		if (jMenu == null) {
			jMenu = new JMenu();
			jMenu.addMouseListener(new java.awt.event.MouseAdapter() { 
				public void mouseEntered(java.awt.event.MouseEvent e) {    
					System.out.println("mouseEntered()"); // TODO Auto-generated Event stub mouseEntered()
				}
			});
			jMenu.addMenuDragMouseListener(new javax.swing.event.MenuDragMouseListener() { 
				public void menuDragMouseEntered(javax.swing.event.MenuDragMouseEvent e) {    
					System.out.println("menuDragMouseEntered()"); // TODO Auto-generated Event stub menuDragMouseEntered()
				}
				public void menuDragMouseDragged(javax.swing.event.MenuDragMouseEvent e) {} 
				public void menuDragMouseExited(javax.swing.event.MenuDragMouseEvent e) {} 
				public void menuDragMouseReleased(javax.swing.event.MenuDragMouseEvent e) {} 
			});
			jMenu.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return jMenu;
	}
  	public static void main(String[] args) {
		prueba p = new prueba();
		p.show();
	}
	
	class PopupListener extends MouseAdapter {
        JPopupMenu jPopupMenu;

        PopupListener(JPopupMenu jPopupMenu) {
        	this.jPopupMenu = jPopupMenu;
        }

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
            	jPopupMenu.show(e.getComponent(),
            					e.getX(), e.getY());
            }
        }
    }
}  //  @jve:decl-index=0:visual-constraint="137,70"

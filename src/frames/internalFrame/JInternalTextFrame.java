package frames.internalFrame;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import javax.swing.JList;

public class JInternalTextFrame extends JInternalFrame{
	
	private JPanel jContentPane = new JPanel();
	private JTextPane jTextPane = new JTextPane();
	private JScrollPane jContentTextPane = new JScrollPane();
	
	private JList jList = null;
	public JInternalTextFrame()	{
		super("", true, true, true, true);
			initialize();

		jContentTextPane.setViewportView(jTextPane);
		jContentPane.setLayout(new BorderLayout());
		jContentPane.add(jContentTextPane);
		this.setFrameIcon(new ImageIcon(getClass().getResource("/images/html_tag_obj.gif")));
		this.setContentPane(jContentPane);
		this.setTitle("XML");
		this.setBounds(0, 0, 300, 150);
		this.show();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setSize(288, 109);
	}

	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList
	 * 
	 * @uml.property name="jList"
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
		}
		return jList;
	}

  }  //  @jve:decl-index=0:visual-constraint="107,74"

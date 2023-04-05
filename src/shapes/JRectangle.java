/* 
* nombre del archivo: JRectangle.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Implementa un JComponent en el cual se dibuja un Rectangulo.
*@Fecha Actualización:	25/01/2005
*@Autor Actualización: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) año Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/
package shapes;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

/*
 * Esta clase extiende de JComponent y dibuja un Rectangulo.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
 */

public class JRectangle extends JComponent{

	private int X1;		// Indica la distancia left del rectangulo. 
	private int X2;		// Indica el ancho del rectangulo.
	private int Y1;		// Indica la distancia top del rectangulo.
	private int Y2;		// Indica el alto del rectangulo.
	private Color c;	// Indica el color del rectangulo.

	/**
	 * Este metodo es el constructor de la clase, inicializa las variables 
	 * miembro.
	 * 
	 * @param	int		x1 Indica la distancia left del rectangulo.
	 * @param	int 	y1 Indica la distancia top del rectangulo.
	 * @param	int		x2 Indica el ancho del rectangulo.
	 * @param	int		y2 Indica el alto del rectangulo.
	 */
	public JRectangle(int x1, int y1, int x2, int y2){
		this.setLayout(null);
		this.setBounds(x1, y1, x2, y2);
		X1 = x1;
		Y1 = y1;
		X2 = x2;
		Y2 = y2;
		c = Color.blue;
	}
	
	/**
	 * Este metodo pinta el rectangulo en el Graphics que va a estar contenido
	 * en el JComponent
	 * 
	 * @param	Graphics
	 */
	final public void paint(Graphics g) {
		g.setColor(c);
        g.drawRect(X1, Y1, X2-1, Y2-1);
	}
	
	/**
	 * Este metodo redibuja el rectangulo, en el JComponent, con los 
	 * parametros que le son pasados.
	 * 
	 * @param	int 	x1 es la nueva posicion x del rectangulo.
	 * @param	int		y1 es la nueva posicion y del rectangulo.
	 * @param	int		x2 es el nuevo ancho del rectangulo.
	 * @param	int		y2 es el nuevo alto del rectangulo.
	 */
	public void reDraw (int x1, int y1, int x2, int y2) {
		X1 = x1;
		Y1 = y1;
		X2 = x2;
		Y2 = y2;
		this.setBounds(0, 0  , X2, Y2);
		this.repaint();
	}
	
	/**
	 * Este metodo setea la variable Color en negro. 
	 */
	public void setColorBlack() {
		c = Color.BLACK;
	}
}

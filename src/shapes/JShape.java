/* 
* nombre del archivo: JShape.java
*  
* @Fecha Generación: 	Día/Mes/ Año 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripción:		Implementa un JComponent en el cual se dibuja un pequeño 
*					rectangulo azul, que simula ser un shape.
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
 * Esta clase extiende de JComponent y dibuja un pequeño Rectangulo
 * azul, que simula ser un shape.
 *
 * @Author      Mauricio Rodriguez
 * @Versión     1.0, 25/01/2005
 */

public class JShape extends JComponent {
	
	/**
	 * Este metodo es el constructor de la clase, inicializa 
	 * el JComponent.
	 */
	public JShape (){
		this.setLayout(null);
		this.setBounds(0,0,4,4);
	}
	
	/**
	 * Este metodo pinta el Shape en el Graphics que esta conteido en el 
	 * JComponent
	 * 
	 * @param	Graphics
	 */
	final public void paint(Graphics g) {
		g.setColor(Color.blue);
        g.fillRect(0,0,4,4);
	}
}

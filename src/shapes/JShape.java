/* 
* nombre del archivo: JShape.java
*  
* @Fecha Generaci�n: 	D�a/Mes/ A�o 
* @Autor: 	       	Mauricio Rodriguez  
*
*@Descripci�n:		Implementa un JComponent en el cual se dibuja un peque�o 
*					rectangulo azul, que simula ser un shape.
*@Fecha Actualizaci�n:	25/01/2005
*@Autor Actualizaci�n: 	Mauricio Rodriguez  
* --------------------------------------------------------------------------------------- 
* Todos los derechos reservados. Copyright (c) a�o Softpoint, Software House.
* --------------------------------------------------------------------------------------- 
*/

package shapes;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

/*
 * Esta clase extiende de JComponent y dibuja un peque�o Rectangulo
 * azul, que simula ser un shape.
 *
 * @Author      Mauricio Rodriguez
 * @Versi�n     1.0, 25/01/2005
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

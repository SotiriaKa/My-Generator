package example_of_simulator;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class SelectPanel extends JPanel{

	private static final long serialVersionUID = -6291233936414618049L;
	
	public SelectPanel() {
	}
	
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 1400, 100);
    }

}

package example_of_simulator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JPanel;
import javax.swing.*;

//categories for floor plan 1:
//1. offers
//2. TV&Image
//3. Books&Comics
//4. CDs&Vinyls
//5. Gaming
//6. Computers&Peripherals
//7. Photos&Videos
//8. Mobile_phones&Tablets

//   xrwmatizontas etsi ola ta proionta poy vriskonte ston pinaka kathe thesis toy array list 
public class VisualizationProducts extends JPanel {

	private static final long serialVersionUID = -6291233936414618049L;
	private String productsPath="C:\\Users\\Σωτηρία\\Desktop\\eclipse-workspace\\simulator\\src\\example_of_simulator\\Floor Plans and Products\\products_floor_plan_1.txt";
	
	public VisualizationProducts() {}
	
    protected void paintComponent(Graphics g) {
        Scanner s;
        
        //kathe thesi  eine mia katigoria !!
        ArrayList<String> products = new ArrayList<String>();
        
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
		
		//products
		try {
			s = new Scanner(new File(productsPath));
	        while (s.hasNext()){
	            products.add(s.next());
	        }
	        s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		for(int i=0; i<products.size(); i++) { 
			String[] product = products.get(i).split(",");
			String category=product[4];
			double x= Double.parseDouble(product[2].substring(1));
			double y= Double.parseDouble(product[3].substring(0,product[3].length()-1));
			Graphics2D g2d = (Graphics2D) g;
			Shape l;
			if(category.equals("offers")) {
				g2d.setPaint(Color.red);
				l = new Line2D.Double(x, y, x, y);
		        g2d.draw(l);
			}else if(category.equals("TV&Image")) {
				g2d.setPaint(Color.cyan);
				l = new Line2D.Double(x, y, x, y);
		        g2d.draw(l);				
			}else if(category.equals("Books&Comics")) {
				g2d.setPaint(Color.yellow);
				l = new Line2D.Double(x, y, x, y);
		        g2d.draw(l);				
			}else if(category.equals("CDs&Vinyls")) {
				g2d.setPaint(Color.orange);
				l = new Line2D.Double(x, y, x, y);
		        g2d.draw(l);				
			}else if(category.equals("Gaming")) {
				g2d.setPaint(Color.magenta);
				l = new Line2D.Double(x, y, x, y);
		        g2d.draw(l);				
			}else if(category.equals("Computers&Peripherals")) {
				g2d.setPaint(Color.blue);
				l = new Line2D.Double(x, y, x, y);
		        g2d.draw(l);				
			}else if(category.equals("Photos&Videos")) {
				g2d.setPaint(Color.pink);
				l = new Line2D.Double(x, y, x, y);
		        g2d.draw(l);				
			}else if(category.equals("Mobile_phones&Tablets")) {
				g2d.setPaint(Color.green);
				l = new Line2D.Double(x, y, x, y);
		        g2d.draw(l);				
			}
		}
    }

}

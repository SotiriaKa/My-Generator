package example_of_simulator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JPanel;

public class MoovingPanel extends JPanel {
    
	private static final long serialVersionUID = -6291233936414618049L;
    private Object[] ObjectsArray;
	private String filePath;
	private String fileName;
	

    public MoovingPanel(Object[] ObjectsArray, int width, int height,String filePath,String fileName) {
        this.ObjectsArray = ObjectsArray;
        setPreferredSize(new Dimension(width, height));
        this.filePath=filePath;
        this.fileName=fileName;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Scanner s;
        ArrayList<String> floor = new ArrayList<String>();
        ArrayList<Integer[]> products = new ArrayList<Integer[]>();
        
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        //----------KATOPSI SXEDIASMOS-------------------
    	//file form: #{(x_left_top, y_left_top),(x_left_bottom,y_left_bottom),(x_right_top,y_right_top),(x_right_bottom,y_right_bottom)}
        //floor plan 
		try {
			s = new Scanner(new File(filePath));
	        while (s.hasNext()){
	            floor.add(s.next());
	        }
	        s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
				
		for(int i=0; i<floor.size()-1; i++) { 
			String[] part = floor.get(i).split(",");
			int xleft = Integer.parseInt(part[0].substring(1));
			int ytop = Integer.parseInt(part[1].substring(0,part[1].length()-1));	
			int xright = Integer.parseInt(part[4].substring(1));
			int ybottom = Integer.parseInt(part[3].substring(0,part[3].length()-1));	
			int height = ybottom - ytop;
			int width = xright-xleft;
			g.setColor(Color.blue);
			g.drawRect(xleft, ytop, width, height);
		}
		
		for (int i = 0; i < ObjectsArray.length; i++) {      	
	        ObjectsArray[i].draw(g);
	    }		

    }

}
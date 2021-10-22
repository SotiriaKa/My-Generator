package example_of_simulator;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class MainVisual{

    private static JFrame frame;
    private static VisualizationProducts visualPanel;
	
	public static void runn() {

        frame = new JFrame();
        frame.setTitle("Visualization Products");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitProcedure();
            }
        });
        
        visualPanel = new VisualizationProducts();             
 
        frame.getContentPane().add(visualPanel);        
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
	}

	   private static void exitProcedure() {
	        frame.dispose();
	        System.exit(0);
	    }

	    public void repaintMovingPanel() {
	        visualPanel.repaint();
	    }
	    
	    public static void main(String[] args) {
	    	runn();
	    }
}

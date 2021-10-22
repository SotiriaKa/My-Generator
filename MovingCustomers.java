package example_of_simulator;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovingCustomers implements ActionListener, Runnable {
    private static final int DRAWING_WIDTH = 1290; 
    private static final int DRAWING_HEIGHT = 580; 
    private static final int NUMOBJECTS = 50;
    private int before_counter = NUMOBJECTS/2; 
    private int N; 
	private BufferedWriter bw=null;
	private File file = new File("C:\\Users\\Σωτηρία\\Desktop\\eclipse-workspace\\simulator\\src\\example_of_simulator\\Floor Plans and Products\\Costumer_Behavior_Data.txt");

    private Object[] ObjectsArray = new Object[NUMOBJECTS];

    private JButton btnSelect;
    private JPanel mainPanel;
    private JFrame frame;

    private MoovingPanel moovingPanel;
    private SelectPanel selectPanel;

    private ObjectsRun objectsRun;
	private String filePath="";
	private String fileName="";
	private RunBefore beforeRun;
	private ArrayList<String> floor = new ArrayList<String>();
	private ArrayList<ArrayList<Integer>> floorLimits = new ArrayList<>();

	private int x_door, y_door;
	
	private boolean click=false;
	private int x_real_door;
	private int y_real_door;
	
	private long real_time;
	private long time_visualization;
	private long tv1;
	private long tv2;
	private boolean visualization=false;

    public MovingCustomers() {
        for (int i = 0; i < ObjectsArray.length; i++) {
            ObjectsArray[i] = new Object(DRAWING_WIDTH, DRAWING_HEIGHT,floorLimits,NUMOBJECTS);
        }
        Random rand = new Random();
        N = rand.nextInt(8)+3;
    }

    @Override
    public void run() {
        frame = new JFrame();
        frame.setTitle("Moving Objects");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitProcedure();
            }
        });
        
        
        if(click) {        	
             moovingPanel = new MoovingPanel(ObjectsArray, DRAWING_WIDTH, DRAWING_HEIGHT,filePath,fileName);             
             mainPanel = new JPanel();
             mainPanel.setLayout((LayoutManager) new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
             mainPanel.add(selectPanel);
             mainPanel.add(moovingPanel);
      
             frame.getContentPane().add(mainPanel);        
             frame.pack();
             frame.setLocationByPlatform(true);
             frame.setVisible(true);
             
             if (!visualization) {
            	 tv1=System.currentTimeMillis();
            	 visualization=true;
             }
             
             objectsRun = new ObjectsRun(this, ObjectsArray, before_counter,N);
             new Thread(objectsRun).start();

         } else {        	
             selectPanel = new SelectPanel();       
             btnSelect = new JButton("Select File");
     		 btnSelect.setBackground(Color.WHITE);
     		 btnSelect.setFont(new Font("Lucida Grande", Font.ITALIC, 14));
     		 btnSelect.setBounds(1200, 0, 20, 30);
     	     btnSelect.setForeground(Color.BLUE);
     		 selectPanel.add(btnSelect);
             btnSelect.addActionListener(this);

             frame.add(selectPanel);
             frame.pack();
             frame.setLocationByPlatform(true);
             frame.setVisible(true);
             
             beforeRun = new RunBefore();
             new Thread(beforeRun).start();        	
         }        
    }
    

    private void exitProcedure() {
    	if(click) {
    		objectsRun.setRunning(false);
    		
    		//time for end visualization
    		tv2 = System.currentTimeMillis();
    		time_visualization = tv2 - tv1;
    		System.out.println("Visualization duration: "+time_visualization+" milliseconds");
    		
			//start time for writing data
			real_time = System.nanoTime();
			System.out.println("Output file creation starts at: "+real_time+" nanoseconds");
    		
    	}
        beforeRun.setRunning(false,true);
        frame.dispose();

		try {		
			FileOutputStream fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(int no=0; no<NUMOBJECTS; no++) {
				bw.write(ObjectsArray[no].writeFile(no));
				bw.newLine();
			}
			
			//end time for writing data
			real_time = System.nanoTime();
			System.out.println("Output file creation ends at: "+real_time+" nanoseconds");
			
			bw.close();
	
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        
        System.exit(0);
    }

    public void repaintMovingPanel() {
        moovingPanel.repaint();
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MovingCustomers());
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser(".");
	    fileChooser.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        System.out.println("Action");

	      }
	    });
	    
	    int status = fileChooser.showOpenDialog(null);
	    if (status == JFileChooser.APPROVE_OPTION) {
	        File file = fileChooser.getSelectedFile();
	        filePath= file.getPath();
	        fileName=file.getName();
	        click=true;
	        beforeRun.stop();
	        
	        //find the door
	        Scanner s;
			try {
				s = new Scanner(new File(filePath));
		        while (s.hasNext()){
		            floor.add(s.next());
		        }
		        s.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}	 
			String[] part_door = floor.get(floor.size()-1).split(",");
			int x1_door= Integer.parseInt(part_door[0].substring(1));
			int y1_door= Integer.parseInt(part_door[1].substring(0,part_door[1].length()-1));
			int x2_door= Integer.parseInt(part_door[2].substring(1));
			int y2_door= Integer.parseInt(part_door[3].substring(0,part_door[3].length()-1));
			if(x1_door-x2_door==0) {
				x_door = x1_door;
				y_door = (y1_door+y2_door)/2;
				x_real_door = x1_door; 
				y_real_door = y1_door;
			}else if(y1_door-y2_door==0) {
				x_door = (x1_door+x2_door)/2;
				y_door = y1_door;
				x_real_door = x1_door;
				y_real_door = y1_door;
			}	
			
			for(int i=0; i<floor.size()-1; i++) { 
				String[] part = floor.get(i).split(",");
				ArrayList<Integer>limits = new ArrayList<Integer>(4);
				int xleft = Integer.parseInt(part[0].substring(1));
				int ytop = Integer.parseInt(part[1].substring(0,part[1].length()-1));	
				int xright = Integer.parseInt(part[4].substring(1));
				int ybottom = Integer.parseInt(part[3].substring(0,part[3].length()-1));	
				limits.add(xleft);
				limits.add(xright);
				limits.add(ybottom);
				limits.add(ytop);
				floorLimits.add(limits);
		        for (int j = 0; j < ObjectsArray.length; j++) {
		        	ObjectsArray[j].setFloor(floorLimits);
		        }
				
			}								
	        this.run();       
	      } else if (status == JFileChooser.CANCEL_OPTION) {
	        System.out.println("calceled");
	      }		
	}
	
	public Integer[] getDoor() {
		Integer [] door= new Integer[4];
		door[0]=x_door;
		door[1]=y_door;
		door[2]=x_real_door;
		door[3]=y_real_door;
		return door;		
	}    
    
}

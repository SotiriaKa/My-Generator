package example_of_simulator;

import java.util.Random;

import javax.swing.SwingUtilities;

public class ObjectsRun implements Runnable {

    private volatile boolean running;
    private Object[] ObjectsArray;
    private MovingCustomers MovingCustomers;   
    private int counter = 0;
    private int count = 0;
    private int time;
    
    private int x_door, y_door;
	private Integer y_real_door;
	private Integer x_real_door;
	
	private long start; 
	private long end; 	
	private long elapsedTime; 
	
	private int before_counter;
	private int N;
	
    public ObjectsRun(MovingCustomers MovingCustomers,Object[] ObjectsArray, int before_counter, int N) {
        this.MovingCustomers = MovingCustomers;
        this.ObjectsArray = ObjectsArray;
        this.before_counter = before_counter;
        this.N=N;
        Random r = new Random();
        time = (r.nextInt(2)+3)*1000;
        count += this.before_counter;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
        	if (counter==0) {
        		start = System.currentTimeMillis();
        		initializeObjects(); 
        		counter=1;
        	}
            updateObjects();
            xxx();
            sleep();
            end = System.currentTimeMillis();
            elapsedTime = end - start;
        }
    }
    
    
    private void updateObjects() { 
    	int c;
    	Random r = new Random();
    	
    	for (int i = 0; i < before_counter; i++) { 
            ObjectsArray[i].move(i);
        } 
    	
    	if(counter>=1 && counter<N) { 
        	for (int i = before_counter; i < count; i++) {
                ObjectsArray[i].move(i);
            } 
    	}
    	
    	if (counter<N) {
            end = System.currentTimeMillis();
            elapsedTime = end - start;
            if (elapsedTime > time) {
            	c = r.nextInt(3)+1;
            	if (count+c < ObjectsArray.length) {
                	for (int i = count; i < count+c; i++) {
                        ObjectsArray[i].move(i);
                    } 
                	counter++;
                	count += c;
            	}
            	time += (r.nextInt(5)+4)*1000;
            }
    	}else if (counter>=N){ //dld otan exoyn eiselthei ola sto katatima, totoe kinoynte ola xoris na perimenoyme i kati
    		for (int i = before_counter; i < ObjectsArray.length; i++) {
                ObjectsArray[i].move(i);
            }    		
    	}
    	
    }    

    private void initializeObjects() {
		Integer [] door = MovingCustomers.getDoor();
		x_door = door[0];
		y_door = door[1];
		x_real_door = door[2];
		y_real_door = door[3];
		
		//diaxvwrismos gia poia tha fenonte k poia oxi(lefka)
		for (int i = 0; i < before_counter; i++) {
            ObjectsArray[i].init_move(x_door,y_door,x_real_door,y_real_door);
        }	
		//ta lefka:
		for (int j = before_counter; j < ObjectsArray.length; j++) {
			if (x_real_door==450 && y_real_door==600) {
	            ObjectsArray[j].init_move(x_door+10,y_door+10,x_real_door,y_real_door);
			}else {
	            ObjectsArray[j].init_move(x_door-10,y_door-10,x_real_door,y_real_door);				
			}
        }
  	
    }

    private void xxx() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MovingCustomers.repaintMovingPanel();
            }
        });
    }

    private void sleep() {
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
        }
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }

} 


package example_of_simulator;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Object {
    private static final int size = 10; //size of objects
    private double x;
    private double y;
    private long time_per_step;
    private double dx;
    private double dy;
    private int drawingWidth;
    private int drawingHeight;
    private int x_real_door;
    private int y_real_door;
	private long start; 
	private long end; 	
	private long elapsedTime;
	private int stand_wait_random;
    private long stand_wait;
    private ArrayList<ArrayList<Integer>> floorLimits = new ArrayList<>();
	private ArrayList<ArrayList<String>> coordinates_products = new ArrayList<ArrayList<String>>();
	private int numobjects;
	private int stand_wait_search=0;
	private boolean first_time=true;
	private boolean initialize_products = true;
	private long start_time;
	private boolean first_random = true;
	private long first_random_time1;
	private long first_random_time2;
	private long first_random_time;
	private long first_random_wait;
	private boolean color_first;
	private Color col;
	private String productsPath="C:\\Users\\Σωτηρία\\Desktop\\eclipse-workspace\\simulator\\src\\example_of_simulator\\Floor Plans and Products\\products_floor_plan_1.txt";
	private ArrayList<String> products = new ArrayList<String>();
	
	private ArrayList<String[]> select_product = new ArrayList<String[]>();;
	private boolean first = true;
	private int lines = 0;
	private ArrayList<boolean[]> stages = new ArrayList<boolean[]>();
	private ArrayList<Integer> category_moove = new ArrayList<Integer>();
	
	private ArrayList<String> profs= new ArrayList<String>();
	private ArrayList<Integer> profile_ids = new ArrayList<Integer>();
	
	private String[][] category_product = {{"offers","500.0,20.0"},{"TV&Image","400.0,600.0"},{"Books&Comics","10.0,310.0"},{"CDs&Vinyls","1000.0,500.0"},{"Gaming","160.0,200.0"},{"Computers&Peripherals","430.0,70.0"},{"Photos&Videos","610.0,500.0"},{"Mobile_phones&Tablets","800.0,500.0"}};
        
    public static int count = 0;
    
    public Object(int drawingWidth, int drawingHeight, ArrayList<ArrayList<Integer>> floorLimits,int numobjects) {
    	x = Math.random() * drawingWidth;
    	y = Math.random() * drawingHeight;
    	time_per_step = System.nanoTime();
    	dx = Math.random() * size;//30D - 15D;
    	dy = Math.random() * size;//30D - 15D;
        this.drawingWidth = drawingWidth;
        this.drawingHeight = drawingHeight;
        this.floorLimits=null;
        this.numobjects=numobjects;
        color_first=true;
        Random r = new Random();
        stand_wait_random = (r.nextInt(2)+3)*1000; //ya nai tyxaio plithos milliseconds (1000ms=1sec)!!
    }
    
    public void setFloor(ArrayList<ArrayList<Integer>> limits) {
    	floorLimits=limits;
    }
    
    //ya na ksekinane ola apo tin porta
    public void init_move(int x_door, int y_door, int x_real_door, int y_real_door) {
    	x=x_door;
    	y=y_door;
    	this.x_real_door=x_real_door;
    	this.y_real_door=y_real_door;
    }
    
    public void move(int i) {       
        //afto ya ta oria tis katopsis twn ekswterikwn toixwn
		int xleft_f=floorLimits.get(0).get(0)+10; //  ta +- 1o einai giati to kathe object exei size = 10
		int xright_f=floorLimits.get(0).get(1)-10; 
		int ybottom_f=floorLimits.get(0).get(2)-10; //na shmeiothei oti epeidi ta ytop kai ybottom exoyn kathoristei me ton tropo poy emfanizonte
		int ytop_f=floorLimits.get(0).get(3)+10;  //to ybottom>ytop 
		boolean rand=true;
		if (i%3 == 1 || i%3 == 2) { //pososto peripoy 66.6% -> !random
			rand = false;
		}else { //if () { //posossto peripoy 33.3% -> random
			rand = true;
		}
		
		if(first) {
	    	Scanner s;
	    	Path path = Paths.get(productsPath);
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
			
	        try {
				lines = (int) Files.lines(path).count();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
	        boolean stage[] = new boolean[4];
	        stage[0]=true; //to stadio mexri na ftasei sto proion
	        stage[1]=false; //to stadio poy kanei voltes
	        stage[2]=false; //to stadio mexri na ftasei sto tameio
	        stage[3]=false; //to stadio poy paei pros tin porta ya na fygei
	        
	        for(int no=0; no<numobjects; no++) {
	        	stages.add(stage);
	        }
	        //initialize ya kathe pelati poia katigoria kinisis
	        double rn;
	        for(int n=0; n<numobjects; n++) {
	        	rn = Math.random();
	        	if (rn>=0.0 && rn<0.25) {
	        		category_moove.add(1);
	        	}else if(rn<=0.25 && rn<0.5) {
	        		category_moove.add(2);
	        	}else if(rn<=0.5 && rn<0.75) {
	        		category_moove.add(3);
	        	}else {
	        		category_moove.add(4);
	        	}
	        }
	        
	        //initialize ya kathe pelati an exei profile i oxi
	        String fileProfiles = "C:\\Users\\Σωτηρία\\Desktop\\eclipse-workspace\\simulator\\src\\example_of_simulator\\Floor Plans and Products\\Profiles.txt";
			try {
				s = new Scanner(new File(fileProfiles));
		        while (s.hasNext()){
		            profs.add(s.next());
		        }
		        s.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			for(int p=0; p<profs.size(); p++) { //-1giati i teleftea grammi einai i porta!!
				String[] pr = profs.get(p).split(",");
				int id = Integer.parseInt(pr[0]);
				profile_ids.add(id);
			}			
	        first=false;
		}
		
		if(rand) {
			random(i, xleft_f, xright_f, ybottom_f, ytop_f);	
		}else {
			if (x_real_door==450 && y_real_door==600) {
				random(i, xleft_f, xright_f, ybottom_f, ytop_f);	
			}else {
				search(i, xleft_f, xright_f, ybottom_f, ytop_f);
			}
		}
		
		if(first_time) {
			ArrayList<String> coor_prods = new ArrayList<String>();
			coor_prods.add("path in coordinates/time: ");
			for(int c=0; c<numobjects; c++) {
				coordinates_products.add(coor_prods);	
			}
			first_time=false;
		}
		coordinates_products.get(i).add("("+x+","+y+")"+"/"+time_per_step);
}
   
    public void random(int i, int xleft_f, int xright_f, int ybottom_f, int ytop_f) {
		//afto tha to allaksw se 4 functions kalitera! KAI NA DW POIA KOMMATIA EINAI IDIA YA NA MIN EPANALAMVANO KWDIKA
    	time_per_step = System.nanoTime();
    	if(y_real_door==(ytop_f-10)) {	 // if DOOR: PANW
			y+=dy;
	    	int xx=1000;
	    	if(i%2==0) {
	    		if (stages.get(i)[3]==false) {
	    			x+=dx;	//0.5*dx;
		    		xx=0;
	    		}
	    		
	    	}else {
	    		if (stages.get(i)[3]==false) {
	    			x-=dx;	//0.5*dx;
	    			xx=1;
	    		}
	    	}
	    	
       		if (stages.get(i)[3]==true) {       			
       			if(x<x_real_door) {
       				x+=dx;
       				xx=0;
       				if(x>x_real_door) {
       					x-=dx;
       					xx=1;
       				}
       			}else if(x>x_real_door){
       				x-=dx;
       				xx=1;
       				if(x<x_real_door) {
       					x+=dx;
       					xx=0;
       				}
       			} else {} 
       			y-=1.2*dy;
       			if (x>=x_real_door-10 && y<=y_real_door+10) {
       				y-=15;
       			}
       		}
	    	if(x<=xleft_f) {
	    		x+=dx;
	    		y-=dy;	//1; 
   				boolean stage[] = new boolean[4];
   				stage[0]=false; 
   				stage[1]=false; 
   				stage[2]=false; 
   				stage[3]=true; //ya na paei pros tin porta
   				stages.set(i, stage);
	    	}
	    	if(x>=xright_f) {
	    		x-=dx;
	    		y-=dy;	//1; 	
   				boolean stage[] = new boolean[4];
   				stage[0]=false; 
   				stage[1]=false; 
   				stage[2]=false; 
   				stage[3]=true; //ya na paei pros tin porta
   				stages.set(i, stage);
	    	}
	    	
	    	if(x>=1185) {
	    		x-=dx;
	    		y-=dy;	//1; 	
   				boolean stage[] = new boolean[4];
   				stage[0]=false; 
   				stage[1]=false; 
   				stage[2]=false; 
   				stage[3]=true; //ya na paei pros tin porta
   				stages.set(i, stage);
	    	}
	    	
	    	if(y>=ybottom_f) {
	    		y-=dy;
	    		x-=dx;
	    	} 
	    	if(y<=ytop_f) {
	    		y+=dy;
	    		
	    	}
	    	for(int j=1; j<floorLimits.size(); j++) {
	    		int xleft=floorLimits.get(j).get(0)-10; //-10 to vazoyme giati to obj exei size=10
	    		int xright=floorLimits.get(j).get(1);
	    		int ybottom=floorLimits.get(j).get(2); 
	    		int ytop=floorLimits.get(j).get(3)-10;	    		
	    		if (y>=ytop && y<=ybottom && x>=xleft && x<=xright) { 
	    			y-=dy; //edo ya mia fora ya na afairesei ato poy eixe parei
	    			while (y>=ytop && y<=ybottom && x>=xleft && x<=xright) {
	    				if(!(ytop==ytop_f || ybottom==ybottom_f)){
	    					y+=dy;
	    				}else {
	    					y-=dy;
	    				}
	    				if (xx==0) { x-=dx; }	//0.5*dx; }			    				
	    				if (xx==1) { x+=dx;	}	//0.5*dx; }		

	    			}
	    			break;
	    		}
	    	}			
		}else if(y_real_door==(ybottom_f+10)) { // if DOOR: KATW
			int yy=1000;
	    	int xx=1000;

	    	if (i%4==0 || i%4==3) {
    			y-=dy;
    			yy=1;
    			x+=dx;
    			xx=0;
	    		if (i%4==0) {
	    			if(i==4 || i==12 || i==20 || i==24 || i==32|| i==40 || i==44 || i==52) {
	    				if(y>270 && y<300) {
	    					if(i==4|| i==20 || i==32 || i==44 || i==52 ) {
	    						x-=1.5*dx;
		    					xx=1;
	    					}else {
	    						x-=1.1*dx;
		    					xx=1;	
	    					}
	    					y+=dy;
	    					yy=0;
		    			}
	    			}else {
		    			if(y>270 && y<330) {
		    				if(i==8 || i==28 || i==48 || i==56) {
		    					x-=1.3*dx;
		    					xx=1;
		    				}else {
		    					x-=1.8*dx;
		    					xx=1;
		    				}
	    					y+=dy;
	    					yy=0;
		    			}
	    			}
	    		}else {
	    			if(i==3 || i==11 || i==19 || i==31|| i==39 || i==43 || i==47 ) {
		    			if (y>25 && y<35) {
		    				if(i==3 || i==19 || i==35 || i==43) {
			    				x-=1.9*dx;
		    					xx=1;	    					
		    				}else {
			    				x-=1.45*dx;
		    					xx=1;
		    				}
	    					y+=dy;
	    					yy=0;
		    			}	    				
	    			}else {
		    			if (y>20 && y<25) {
		    				if(i==7 || i==27 || i==23) {
		    					x-=1.3*dx;
		    					xx=1;
		    				} else {
		    					x-=1.85*dx;
		    					xx=1;
		    				}
	    					y+=dy;
	    					yy=0;
		    			}	    				
	    			}
	    		}
	    	}
	    	

	    	if (i%4==1 || i%4==2) {
    			y-=dy;
    			yy=1;
    			x-=dx;
    			xx=1;
	    		if (i%4==1) {
	    			if(i==5 || i==13 || i==25 || i==33 || i==37 || i==45 || i==49) {
	    				if(y>250 && y<335) {
		    				y+=dy;
		    				yy=0;
		    				if(i==5 || i==25 || i==45 || i==57) {
		    					x+=1.4*dx;
			    				xx=0;	
		    				}else {
		    					x+=2*dx;
			    				xx=0;
		    				}
		    				
		    			}	
	    			}else {
	    				if(y>250 && y<320) {
		    				y+=dy;
		    				yy=0;
		    				if(i==9 || i==21 || i==41 || i==53) {
			    				x+=1.2*dx;
			    				xx=0;   					
		    				}else {
			    				x+=1.9*dx;
			    				xx=0;	    					
		    				}

		    			}	
	    			}
	    			
	    		}else {
	    			if(i==2 || i==6 || i==14 || i==18 || i==26 || i==34 || i==38 || i==44 || i==52) {
	    				if (y>110 & y<185) {
		    				y+=dy;
		    				yy=0;
		    				if(i==38 || i==44 ||  i==18 || i==6) {
		    					x+=2*dx;
		    					xx=0;
		    				}else {
		    					x+=1.3*dx;
		    					xx=0;
		    				}
		    			}		
	    			}else {
	    				if (y>110 & y<160) {
		    				y+=dy;
		    				yy=0;
		    				if (i==10 || i==40 || i==56 || i==60) {
		    					x+=1.8*dx;
			    				xx=0;
		    				}else {
			    				x+=1.2*dx;
			    				xx=0;
		    				}
		    			}	
	    			}
	    			
	    		}
	    	}
	    	
	 /*   	if (i%2==0) {
	    		x+=dx;
	    		xx=0;
	    	}else {
	    		x-=dx;
	    		xx=1;
	    	}
			
	    	y-=dy;
			yy=1;
			
			if(i%3==0) {
				if(y>=ybottom_f/2) {
					if (yy==1) {
						y+=dy;
						yy=0;
					}
					if (xx==0) {
						x-=dx;
						xx=1;
					}else {
						x+=dx;
						xx=0;
					}
				}else if (y>=10 && y<=50){
					if (yy==1) {
						y+=dy;
						yy=0;
					}
					if (xx==0) {
						x-=dx;
						xx=1;
					}else {
						x+=dx;
						xx=0;
					}				
				}
			}*/
			
	/*		if(x<=xright_f/2) {
				if (y>=ybottom_f/2) {
					if (i%3==0) {
						x+=dx;
						xx=0;
					}
				}else {
					if (i%3==1) {
						x+=dx;
						xx=0;
					}
				}
			}else {
				if (y>=ybottom_f/2) {
					if (i%3==2) {
						x-=dx;
						xx=1;
					}
				}
				
			}
			
*/			
			

	    	if(x<=xleft_f) {
	    	//	if(y>ybottom_f/2) {
	    			x+=dx;
	    			xx=0;
	    			y-=dy;
	    			yy=1;
	    	}
	    	if(x>=xright_f) {
	    	//	if(y>ybottom_f/2) {
	    			x-=dx;
	    			xx=1;
		    		y-=dy;
		    		yy=1;
	    	}
	    	if(y<=ytop_f) {
	    		y+=dy;
	    		yy=0;
	    		if (xx==0) {
	    			x-=dx;
	    			xx=1;
	    		} else if (xx==1) {
	    			x+=dx;
	    			xx=0;
	    		}
	    	}  
	    	if(y>=ybottom_f) {
	    		y-=dy;
	    		yy=1;
	    		if (xx==0) {
	    			x-=dx;
	    			xx=1;
	    		} else if (xx==1) {
	    			x+=dx;
	    			xx=0;
	    		}
	    	} 
	    	for(int j=1; j<floorLimits.size(); j++) {
	    		int xleft=floorLimits.get(j).get(0)-10; //-10 to vazoyme giati to obj exei size=10
	    		int xright=floorLimits.get(j).get(1);
	    		int ybottom=floorLimits.get(j).get(2); 
	    		int ytop=floorLimits.get(j).get(3)-10;	    		
	    		if (y>=ytop && y<=ybottom && x>=xleft && x<=xright) { 
	    			if(x<=561  && x>=539) {
		    			while (y>=ytop && y<=ybottom && x>=xleft && x<=xright) {
		    				y-=dy;
		    				if (xx==0) { x-=dx; }	//0.5*dx; }			    				
		    				if (xx==1) { x+=dx;	}	
		    			}
		    			break;
	    			}else {
		    			if (yy==0) { y-=dy; }
	    				if (yy==1) { y+=dy; }
		    			while (y>=ytop && y<=ybottom && x>=xleft && x<=xright) {
		    				if(i%2==0) {
		    					if (j%2==0) {
		    						y-=dy;
		    					}else {
		    						y+=dy;
		    					}
		    				}else {
		    					y+=dy;
		    				}
		    				if (xx==0) { x+=dx; }	//0.5*dx; }			    				
		    				if (xx==1) { x-=dx;	}	//0.5*dx; }		
	
		    			}
		    			break;
	    			}
	    		}
	    	}			
		}else if(x_real_door==(xleft_f-10)) { // if DOOR : LEFT
			x+=dx;
	    	int xx=1000;
	    	if (i%2==1) {
	    		y+=dy;
	    		xx=0;
	    	}else {
	    		y-=dy;
	    		xx=1; 		
	    	}	    	
	    	if(x>=xright_f) {
	    		x-=dx;
	    	}
	    	if(x<=xleft_f) {
	    		x+=dx;
	    	}
	    	if(y>=ybottom_f) {
	    		y-=dy;
	    	}
	    	if(y<=ytop_f) {
	    		y+=dy;
	    	}	    	
	    	for(int j=1; j<floorLimits.size(); j++) {
	    		int xleft=floorLimits.get(j).get(0)-10; //-10 to vazoyme giati to obj exei size=10
	    		int xright=floorLimits.get(j).get(1);
	    		int ybottom=floorLimits.get(j).get(2); 
	    		int ytop=floorLimits.get(j).get(3)-10;	    		
	    		if (y>=ytop && y<=ybottom && x>=xleft && x<=xright) { 
	    			x-=dx; 
	    			while (y>=ytop && y<=ybottom && x>=xleft && x<=xright) {
	    				x+=dx;
	    				if (xx==0) { y-=dy; }
	    				if (xx==1) { y+=dy; }
	    			}
	    			break;
	    		}
	    	}			
		}else if(x_real_door==(xright_f+10)) { // if DOOR : RIGHT
			x-=dx;
	    	int xx=1000;
	    	if (i%2==0) {
	    		y+=dy;
	    		xx=0;
	    	}else {
	    		y-=dy;
	    		xx=1; 		
	    	}	    	
	    	if(x<=xleft_f) {
	    		x+=dx;
	    	}	
	    	if(x>=xright_f) {
	    		x-=dx;
	    	}
	    	if(y>=ybottom_f) {
	    		y-=dy;
	    	}
	    	if(y<=ytop_f) {
	    		y+=dy;
	    	}	    	
	    	for(int j=1; j<floorLimits.size(); j++) {
	    		int xleft=floorLimits.get(j).get(0)-10; //-10 to vazoyme giati to obj exei size=10
	    		int xright=floorLimits.get(j).get(1);
	    		int ybottom=floorLimits.get(j).get(2); 
	    		int ytop=floorLimits.get(j).get(3)-10;	    		
	    		if (y>=ytop && y<=ybottom && x>=xleft && x<=xright) { 
	    			x+=dx; 
	    			while (y>=ytop && y<=ybottom && x>=xleft && x<=xright) {
	    				x-=dx;
	    				if (xx==0) { y-=dy; }
	    				if (xx==1) { y+=dy; }
	    			}
	    			break;
	    		}
	    	}
		}
    }
 
    public void search(int i, int xleft_f, int xright_f, int ybottom_f, int ytop_f) {
    	time_per_step = System.nanoTime();
    	double dxp = 0.0;
    	double dyp = 0.0;
    	if(initialize_products) {
        	for(int p=0; p<numobjects; p++) {
        		String sel[]= new String[3];
        		sel[0]="no";
        		select_product.add(sel);
        	}
        	initialize_products=false;
        } 
    	
    	if(select_product.get(i)[0].equals("no")) {
    		//tha epilegei tyxaia mia grammi apo to arxeio me ta proionta
    		//kai meta mono afti tha vriskei pio einai to produuct
    		//den xreiazete na diatrexei olo ton pinaka me ta proionta
    		///1st way: me tin random dialego enan integer apo 0 mexri to #lines toy arxeiou
    		//kai meta pigeneo stin antistixi thesi toy products lalala
    		int product_number = (int)(Math.random() * lines);
			String[] productp = products.get(product_number).split(",");
			String categoryp=productp[4];
			String xp = productp[2].substring(1);
			String yp = productp[3].substring(0,productp[3].length()-1);
			String info_product[] = new String[3];
			info_product[0] = categoryp;
			info_product[1] = xp;
			info_product[2] = yp;
    		select_product.set(i,info_product);
    	}else { //dld an exoyn vrei product ya na pane pros ta ekei
    		dxp = Double.parseDouble(select_product.get(i)[1]);
    		dyp = Double.parseDouble(select_product.get(i)[2]);
    	}

    	if(profile_ids.contains(i)) { //ara pigainei i pros katigoria polysyxnasti i pros polysyxnasto eidos proion poy exei agorasei!!
    		//random category 
    		//prepei na vrei to polysyxnasto proion kai thn polysyxnasti katigoria
    		//kai meta tyxaia kapoia na kinithoyn me vasi to proion(alla oysiastika katigoria poy anikei) kai me vasi tin katigoria
			int position = profile_ids.indexOf(i); //ya na vro tin thesi sto profile ids kathos ine idia me tin thesi sto profs!!!
			String[] ctg_prds = profs.get(position).split(",");
			String[] ctgs = ctg_prds[2].split("/");
			//tora afto to kanw ya na do to plithos ton emfanisewn kathe katigorias proiontwn
			//isos ine ligo poly fail - L O L -
			ArrayList<String[]> ctgs_al = new ArrayList<String[]>();
			for(int ct=0; ct<ctgs.length; ct++) {
				if(!ctgs_al.contains(ctgs[ct])) {
					String lala[] = new String[2];
					lala[0]=ctgs[ct];
					lala[1]=Integer.toString(1);
					ctgs_al.add(lala);
				}else {
					int pos = ctgs_al.indexOf(ctgs[ct]);
					String lolo[] = new String[2];
					lolo[0]=ctgs[ct];
					int xa=Integer.parseInt(ctgs_al.get(pos)[1])+1;
					lolo[1]=Integer.toString(xa);
					ctgs_al.set(pos, lolo);
				}
			}
			int max=0;
			int maxposition=0;
			for(int m=0; m<ctgs_al.size(); m++) {
				if(max > Integer.parseInt(ctgs_al.get(m)[1])) {
					max = Integer.parseInt(ctgs_al.get(m)[1]);
					maxposition=m;
				}
			}
			//kai tora se afto to simeio exoyme tin pio polysuxnasti ya to kathe pelati me profile katigoria proiontwn
			//SYNEPWS arkei na exoyme ena arraylist me tin kathe katigoria poies einai oi syntetagmenes tis 
			//ya efkolia ya kathe katigoria tha valw kapoia enddeiktika simeia poy simenei oti pigenei se ena proion aftis tis katigorias
			double x_category=0.0;
			double y_category=0.0;
			for(int cps=0; cps<category_product.length; cps++) {
				if(category_product[cps][0]==ctgs_al.get(maxposition)[0]) {
					String[] category_coordinates = category_product[cps][1].split(",");
					x_category = Double.parseDouble(category_coordinates[0]);
					y_category = Double.parseDouble(category_coordinates[1]);
				}
			}
			
			if(category_moove.get(i)==1) {
	    			category1(i, xleft_f, xright_f, ybottom_f, ytop_f, x_category, y_category);
	    		}else if (category_moove.get(i)==2) {
	    			category2(i, xleft_f, xright_f, ybottom_f, ytop_f, x_category, y_category);
	    		}else if (category_moove.get(i)==3) {
	    			category3(i, xleft_f, xright_f, ybottom_f, ytop_f, x_category, y_category);
	    		}else if (category_moove.get(i)==4) {
	    			category1(i, xleft_f, xright_f, ybottom_f, ytop_f, x_category, y_category);
	    		}
    	}else {
    		//random category
    		if(dxp!=0.0 && dyp!=0.0) {
	    		if(category_moove.get(i)==1) {
	    			category1(i, xleft_f, xright_f, ybottom_f, ytop_f, dxp, dyp);
	    		}else if (category_moove.get(i)==2) {
	    			category2(i, xleft_f, xright_f, ybottom_f, ytop_f, dxp, dyp);
	    		}else if (category_moove.get(i)==3) {
	    			category3(i, xleft_f, xright_f, ybottom_f, ytop_f, dxp, dyp);
	    		}else if (category_moove.get(i)==4) {
	    			category2(i, xleft_f, xright_f, ybottom_f, ytop_f, dxp, dyp);
	    		}
    		}
    	}
    	
  }

    //afta doylevoyn mono sti periptosi poy i porta einai panw deksia 
    public void category1(int i, int xleft_f, int xright_f, int ybottom_f, int ytop_f, double dxp, double dyp) {
    	//epilegei proion-> tameio -> fevgei
       	if(dxp < x_real_door) { //1st case
       		//if ya stadia kai analoga na paei pros tin antistoixi katefthinsi //prepei na ta kanei me tin seira ara tha ecxo  3 metavlites tis opoies px otan ftasei sto proion tha kanei true afti poy einai ya na paei sto tameio//meta otan tha ftastei sto tameio tha kanei true afti poy einai ya na fygei//kai ennoeite i proti poy tha mpainei tha nai i true metavliti mexri na ftasei k me to poy ftanei tin kanei false ya na min ksanampei //eite mporoyme na exoume enan array 2d me 3 theseis ya kathe boolean metavliti toy analogoy stadioy//opoy i kathe thesi toy array tha antistoixei se kathe object//giati den ginete na xoyme mia ksexoy metavliti boolean kathos trexei afti i sinartisi ya kathe vima kathe antikimenoy!!
       		
       		int xx=1000; 	//xx=0 -> x+=dx alliws xx=1 -> x-=dx, ara meta an xx=0 -> x-=dx k an xx=1 -> x+=dx
       		int yy=1000;
       		
       		if (stages.get(i)[0]==true) { //dld an einai sto stadio poy prospathei na paei pros to proion
       			if(x>dxp) {
       				x-=dx;
       				xx=1;
       			}
       			if(y<dyp) {
       				y+=dy;
       				yy=0;
       			}
       			
       			if(x<=dxp && y>=dyp) { //shmainei oti efase sto prooion
       				boolean stage[] = new boolean[4];
       				stage[0]=false; //afto to kanoyme false giati den eimaste allo se afto to stadio
       				stage[1]=false; //afto to afinoyme ws exxei giati einai afto poy synexizei tin volta sto katastima
       				stage[2]=true; //afto to allazoyme se true giati imaste se afto to stadio poy theloyme na paei sto tameio
       				stage[3]=false; //to afisnoyme ws exei
       				stages.set(i, stage);
       				long ws_end = System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
       			}
       		}
       		//afto okei, to allazo na lew oti i morfi toy arxeioy exei sugkekrimeni tami to tameio -> proteleftea -> prin tin porta!!!
       		if(stages.get(i)[2]==true) { //dld mexri na paei sto tameio      			
       			//to tameio einai ya x>=1200 kai ta y poy mporei na paei einai [170,235], [285,355], [405,470]
       			if(x<1200-10) {
       				x+=dx;
       				xx=0;
       			}
       			
       			if(y<235+10) {
       				y+=dy;
       				yy=0;
       			}
       			
       			if(y>470) {
       				y-=dy;
       				yy=1;
       			}
       			
       			if(x>=1200-10 && (y<=470 || y>=170+10)) {
       				boolean stage[] = new boolean[4];
       				stage[0]=false; 
       				stage[1]=false; 
       				stage[2]=false; 
       				stage[3]=true; //ya na paei pros tin porta
       				long ws_end = System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
       				stages.set(i, stage);
       			}      				
       			
       		}
       		
       		if (stages.get(i)[3]==true) {       			
       			if(x<=x_real_door) {
       				x+=dx;
       				xx=0;
       				if(x>=x_real_door) {
       					x-=dx;
       					xx=1;
       				}
       			}else {
       				x-=dx;
       				xx=1;
       				if(x<=x_real_door) {
       					x+=dx;
       					xx=0;
       				}
       			}
       			if(y>y_real_door) {
       				y-=dy;
       				yy=1;
       			}
       			if (x>=x_real_door-10 && y<=y_real_door+10) {
       				y-=15;
       			}
       		}
       		
       		//Oi parakatw elegxoi ya ta eswterika empodia k tin katopsi tha nai idioi kai ya ta 3 stadia (proion, tameio, fevgei)
   			//afta einai ya ta oria tis katopsis
       		//synepws aftoi oi elegxoi tha ginonte sto teleos meta tis if ya na doyme se poio stadio vriskomaste!!!
	    	if(x<=xleft_f) {
	    		x+=dx;
	    		xx=0;
	    		y-=dy;	//giati door panw
   				boolean stage[] = new boolean[4];
   				if(i%2==1) { //na paei tameio
	   				stage[0]=false; 
	   				stage[1]=false; 
	   				stage[2]=true; 
	   				stage[3]=false;
       				long ws_end =System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
	   				stages.set(i, stage);
   				} else {
	   				stage[0]=false; 
	   				stage[1]=false; 
	   				stage[2]=false; 
	   				stage[3]=true; //alliws na fygoyn oi alloi misoi
       				long ws_end = System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
	   				stages.set(i, stage);  					
   				}
	    	}
	    	if(x>=xright_f) {
	    		x-=dx; 
	    		xx=1;
	    		y-=dy;	
	    		yy=1; 
	    		
	    	}
	    	if(y>=ybottom_f) {
	    		y-=dy;
	    		yy=1;
	    		x-=dx;
	    		xx=1;
	    	} 
	    	if(y<=ytop_f) {
	    		if(stages.get(i)[3]==false) {
	    			y+=dy;
	    			yy=0;
	    		}
	    	}

	    	for(int j=1; j<floorLimits.size(); j++) {
	    		int xleft=floorLimits.get(j).get(0)-10; //-10 to vazoyme giati to obj exei size=10
	    		int xright=floorLimits.get(j).get(1);
	    		int ybottom=floorLimits.get(j).get(2); 
	    		int ytop=floorLimits.get(j).get(3)-10;	    		
	    		if (y>=ytop && y<=ybottom && x>=xleft && x<=xright) { 
	    			if (yy==0) { y-=dy; }
    				if (yy==1) { y+=dy; }
	    			while (y>=ytop && y<=ybottom && x>=xleft && x<=xright) {
	    			/*	if (yy==0) { y+=dy; } //sto y einai i idia antistoixia, giati theloyme na synexisoyn stin poreia poy pigenan
	    				if (yy==1) { y-=dy; }*/
	    				//afto ya na yparxei poikilia ana stand k ana pelati
	    				if(i%2==0) {
	    					if (j%2==0) {
	    						y-=dy;
	    					}else {
	    						y+=dy;
	    					}
	    				}else {
	    					y+=dy;
	    				}
	    				if (xx==0) { x-=dx; }	//0.5*dx; }			    				
	    				if (xx==1) { x+=dx;	}	//0.5*dx; }		

	    			}
	    			break;
	    		}
	    	}
	    	
	    	//NA DW MIPOS AFTOS O TROPOS YA TA ESWTERIKA EMPODIA DINEI KALITERI KINISI!!!!!!!!!
	    	/* 	    	for(int j=1; j<floorLimits.size(); j++) {
	    		int xleft=floorLimits.get(j).get(0)-10; //-10 to vazoyme giati to obj exei size=10
	    		int xright=floorLimits.get(j).get(1);
	    		int ybottom=floorLimits.get(j).get(2); 
	    		int ytop=floorLimits.get(j).get(3)-10;
	    		if (y>=ytop && y<=ybottom && x>=xleft && x<=xright) { 
	    			if(stages.get(i)[0]==true) {
						if (y>dyp) {
							y-=dy;
						}else {
							y+=dy;
						}
	    			}else if(stages.get(i)[2]==true) {
						if (y>300) {
							y-=dy;
						}else {
							y+=dy;
						}
	    			}else if(stages.get(i)[3]==true) {
	    				if (y>y_real_door) {
							y-=dy;
						}else {
							y+=dy;
						}
	    			}else {}
	    			while (y>=ytop && y<=ybottom && x>=xleft && x<=xright) {
    					if(stages.get(i)[0]==true) {	 //pros proion
							if (x>dxp) {
								x-=dx;
							}else {
								x+=dx;
							}
							if (y>dyp) {
								y-=dy;
							}else {
								y+=dy;
							}
    						
    					} else if (stages.get(i)[2]==true) {		//pros tameio
							if (x>1200-10) {
								x-=dx;
							}else {
								x+=dx;
							}
							if (y>300) {
								y-=dy;
							}else {
								y+=dy;
							}

    					} else if (stages.get(i)[3]==true) {	//pros porta
    						if (x>x_real_door) {
								x-=dx;
							}else {
								x+=dx;
							}
							if (y>y_real_door) {
								y-=dy;
							}else {
								y+=dy;
							}
    					}else {}		    			
	    			}
	    			break;
	    		}
	    	}*/
       	}else {				//if (dxp>=x_real_door) 
       		int xx=1000; 	//xx=0 -> x+=dx alliws xx=1 -> x-=dx, ara meta an xx=0 -> x-=dx k an xx=1 -> x+=dx
       		int yy=1000;
       		//edw to proion vriskete pio deksia tis portas, ara me to poy mpainei o pelatis simenei oti to x toy ine mikrotero apo to proion, afoy mpainei apo tin porta!!!
       		
       		if (stages.get(i)[0]==true) { //proion
       			
       			if(x<dxp) {			//if dxp>x_real_door, an ine iso den xreiazete na kanei kati exei vrei idi to swsto x!!!
       				x+=dx;
       				xx=0;
       			}
       			if(y<dyp) {
       				y+=dy;
       				yy=0;
       			}
       			
       			//ara allazo kai edo prepei x>=dxp ya na simenei oti eftase!!, efoson pigene apo aristera pros deksia !!
       			//to y idio giati ola ta proionta katw apo tin door 
       			if(x>=dxp && y>=dyp) { //shmainei oti efase sto prooion
       				boolean stage[] = new boolean[4];
       				stage[0]=false; //afto to kanoyme false giati den eimaste allo se afto to stadio
       				stage[1]=false; //afto to afinoyme ws exxei giati einai afto poy synexizei tin volta sto katastima
       				stage[2]=true; //afto to allazoyme se true giati imaste se afto to stadio poy theloyme na paei sto tameio
       				stage[3]=false; //to afisnoyme ws exei
       				stages.set(i, stage);
       				long ws_end = System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
       			}
       		}
       		
       		//afto okei, to allazo na lew oti i morfi toy arxeioy exei sugkekrimeni tami to tameio -> proteleftea -> prin tin porta!!!
       		if(stages.get(i)[2]==true) { //dld mexri na paei sto tameio      			
       			//to tameio einai ya x>=1200 kai ta y poy mporei na paei einai [170,235], [285,355], [405,470]
       			if(x<1200-10) {
       				x+=dx;
       				xx=0;
       			}
       			
       			if(y<235+10) {
       				y+=dy;
       				yy=0;
       			}
       			
       			if(y>470) {
       				y-=dy;
       				yy=1;
       			}
       			
       			if(x>=1200-10 && (y<=470 || y>=170+10)) {
       				boolean stage[] = new boolean[4];
       				stage[0]=false; 
       				stage[1]=false; 
       				stage[2]=false; 
       				stage[3]=true; //ya na paei pros tin porta
       				long ws_end = System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
       				stages.set(i, stage);
       			}      				
       			
       		}
       		
       		if (stages.get(i)[3]==true) {  //porta     			
       			if(x<=x_real_door) {
       				x+=dx;
       				xx=0;
       				if(x>=x_real_door) {
       					x-=dx;
       					xx=1;
       				}
       			}else {
       				x-=dx;
       				xx=1;
       				if(x<=x_real_door) {
       					x+=dx;
       					xx=0;
       				}
       			}
       			if(y>y_real_door) {
       				y-=dy;
       				yy=1;
       			}
       			if (x>=x_real_door-10 && y<=y_real_door+10) {
       				y-=15;
       			}
       		}
       		
       		//Oi parakatw elegxoi ya ta eswterika empodia k tin katopsi tha nai idioi kai ya ta 3 stadia (proion, tameio, fevgei)
   			//afta einai ya ta oria tis katopsis
       		//synepws aftoi oi elegxoi tha ginonte sto teleos meta tis if ya na doyme se poio stadio vriskomaste!!!
	    	if(x<=xleft_f) {
	    		x+=dx;
	    		xx=0;
	    		y-=dy;	//giati door panw
   				boolean stage[] = new boolean[4];
   				if(i%2==1) { //na paei tameio
	   				stage[0]=false; 
	   				stage[1]=false; 
	   				stage[2]=true; 
	   				stage[3]=false;
       				long ws_end =System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
	   				stages.set(i, stage);
   				} else {
	   				stage[0]=false; 
	   				stage[1]=false; 
	   				stage[2]=false; 
	   				stage[3]=true; //alliws na fygoyn oi alloi misoi
       				long ws_end = System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
	   				stages.set(i, stage);  					
   				}
	    	}
	    	if(x>=xright_f) {
	    		x-=dx; 
	    		xx=1;
	    		y-=dy;	
	    		yy=1; 
	    		
	    	}
	    	if(y>=ybottom_f) {
	    		y-=dy;
	    		yy=1;
	    		x-=dx;
	    		xx=1;
	    	} 
	    	if(y<=ytop_f) {
	    		if(stages.get(i)[3]==false) {
	    			y+=dy;
	    			yy=0;
	    		}
	    	}

	    	for(int j=1; j<floorLimits.size(); j++) {
	    		int xleft=floorLimits.get(j).get(0)-10; //-10 to vazoyme giati to obj exei size=10
	    		int xright=floorLimits.get(j).get(1);
	    		int ybottom=floorLimits.get(j).get(2); 
	    		int ytop=floorLimits.get(j).get(3)-10;	    		
	    		if (y>=ytop && y<=ybottom && x>=xleft && x<=xright) { 
	    			if (yy==0) { y-=dy; }
    				if (yy==1) { y+=dy; }
	    			while (y>=ytop && y<=ybottom && x>=xleft && x<=xright) {
	    			/*	if (yy==0) { y+=dy; } //sto y einai i idia antistoixia, giati theloyme na synexisoyn stin poreia poy pigenan
	    				if (yy==1) { y-=dy; }*/
	    				//afto ya na yparxei poikilia ana stand k ana pelati
	    				if(i%2==0) {
	    					if (j%2==0) {
	    						y-=dy;
	    					}else {
	    						y+=dy;
	    					}
	    				}else {
	    					y+=dy;
	    				}
	    				if (xx==0) { x-=dx; }	//0.5*dx; }			    				
	    				if (xx==1) { x+=dx;	}	//0.5*dx; }		

	    			}
	    			break;
	    		}
	    	} 
	    } 
    }
    
    public void category2(int i, int xleft_f, int xright_f, int ybottom_f, int ytop_f, double dxp, double dyp) {
    	//epilegei proion->volta katastima->tameio->fevgei
    	//idio me categoyry1  mono poy apo to stage[0] tora prin paei sto stage[2] pernaei prota apo to stage[1]. Dld stage[0]-> [1] -> [2] -> [3]
      	if(dxp < x_real_door) { //1st case
       		//if ya stadia kai analoga na paei pros tin antistoixi katefthinsi //prepei na ta kanei me tin seira ara tha ecxo  3 metavlites tis opoies px otan ftasei sto proion tha kanei true afti poy einai ya na paei sto tameio//meta otan tha ftastei sto tameio tha kanei true afti poy einai ya na fygei//kai ennoeite i proti poy tha mpainei tha nai i true metavliti mexri na ftasei k me to poy ftanei tin kanei false ya na min ksanampei //eite mporoyme na exoume enan array 2d me 3 theseis ya kathe boolean metavliti toy analogoy stadioy//opoy i kathe thesi toy array tha antistoixei se kathe object//giati den ginete na xoyme mia ksexoy metavliti boolean kathos trexei afti i sinartisi ya kathe vima kathe antikimenoy!!
       		
       		int xx=1000; 	//xx=0 -> x+=dx alliws xx=1 -> x-=dx, ara meta an xx=0 -> x-=dx k an xx=1 -> x+=dx
       		int yy=1000;
       		
       		if (stages.get(i)[0]==true) { //dld an einai sto stadio poy prospathei na paei pros to proion
       			if(x>dxp) {
       				x-=dx;
       				xx=1;
       			}
       			if(y<dyp) {
       				y+=dy;
       				yy=0;
       			}
       			
       			if(x<=dxp && y>=dyp) { //shmainei oti efase sto prooion
       				boolean stage[] = new boolean[4];
       				stage[0]=false; //afto to kanoyme false giati den eimaste allo se afto to stadio
       				stage[1]=true; //afto to afinoyme ws exxei giati einai afto poy synexizei tin volta sto katastima
       				stage[2]=false; //afto to allazoyme se true giati imaste se afto to stadio poy theloyme na paei sto tameio
       				stage[3]=false; //to afisnoyme ws exei
       				stages.set(i, stage);
       				long ws_end = System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
       			}
       		}
       		
       		if (stages.get(i)[1]==true) { //dld an einai sto stadio poy prospathei na paei pros to proion
       			
       			if(first_random) {
       				first_random_time1 = System.currentTimeMillis();
       				first_random_time2 = System.currentTimeMillis();
       				first_random_time = first_random_time2 - first_random_time1; 
       				Random r = new Random();
       				first_random_wait = (r.nextInt(4)+5)*1000; //ya nai tyxaio plithos milliseconds (1000ms=1sec) kai na nai sigoyra > 5 seconds
       				first_random = false;
       			}
       			
   				first_random_time2 = System.currentTimeMillis();
   				first_random_time = first_random_time2 - first_random_time1; 
       			if(first_random_time > first_random_wait) {
       				boolean stage[] = new boolean[4];
       				stage[0]=false; //afto to kanoyme false giati den eimaste allo se afto to stadio
       				stage[1]=false; //afto to afinoyme ws exxei giati einai afto poy synexizei tin volta sto katastima
       				stage[2]=true; //afto to allazoyme se true giati imaste se afto to stadio poy theloyme na paei sto tameio
       				stage[3]=false; //to afisnoyme ws exei
       				stages.set(i, stage);      				
       			}else {
       				random(i, xleft_f, xright_f, ybottom_f, ytop_f);	
       			}
       		}
       		
       		//afto okei, to allazo na lew oti i morfi toy arxeioy exei sugkekrimeni tami to tameio -> proteleftea -> prin tin porta!!!
       		if(stages.get(i)[2]==true) { //dld mexri na paei sto tameio      			
       			//to tameio einai ya x>=1200 kai ta y poy mporei na paei einai [170,235], [285,355], [405,470]
       			if(x<1200-10) {
       				x+=dx;
       				xx=0;
       			}
       			
       			if(y<235+10) {
       				y+=dy;
       				yy=0;
       			}
       			
       			if(y>470) {
       				y-=dy;
       				yy=1;
       			}
       			
       			if(x>=1200-10 && (y<=470 || y>=170+10)) {
       				boolean stage[] = new boolean[4];
       				stage[0]=false; 
       				stage[1]=false; 
       				stage[2]=false; 
       				stage[3]=true; //ya na paei pros tin porta
       				long ws_end = System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
       				stages.set(i, stage);
       			}      				
       			
       		}
       		
       		if (stages.get(i)[3]==true) {       			
       			if(x<=x_real_door) {
       				x+=dx;
       				xx=0;
       				if(x>=x_real_door) {
       					x-=dx;
       					xx=1;
       				}
       			}else {
       				x-=dx;
       				xx=1;
       				if(x<=x_real_door) {
       					x+=dx;
       					xx=0;
       				}
       			}
       			if(y>y_real_door) {
       				y-=dy;
       				yy=1;
       			}
       			if (x>=x_real_door-10 && y<=y_real_door+10) {
       				y-=15;
       			}
       		}
   
	    	if(x<=xleft_f) {
	    		x+=dx;
	    		xx=0;
	    		y-=dy;	//giati door panw
   				boolean stage[] = new boolean[4];
   				if(i%2==1) { //na paei tameio
	   				stage[0]=false; 
	   				stage[1]=false; 
	   				stage[2]=true; 
	   				stage[3]=false;
       				long ws_end =System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
	   				stages.set(i, stage);
   				} else {
	   				stage[0]=false; 
	   				stage[1]=false; 
	   				stage[2]=false; 
	   				stage[3]=true; //alliws na fygoyn oi alloi misoi
       				long ws_end = System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
	   				stages.set(i, stage);  					
   				}
	    	}
	    	if(x>=xright_f) {
	    		x-=dx; 
	    		xx=1;
	    		y-=dy;	
	    		yy=1; 
	    		
	    	}
	    	if(y>=ybottom_f) {
	    		y-=dy;
	    		yy=1;
	    		x-=dx;
	    		xx=1;
	    	} 
	    	if(y<=ytop_f) {
	    		if(stages.get(i)[3]==false) {
	    			y+=dy;
	    			yy=0;
	    		}
	    	}

	    	for(int j=1; j<floorLimits.size(); j++) {
	    		int xleft=floorLimits.get(j).get(0)-10; //-10 to vazoyme giati to obj exei size=10
	    		int xright=floorLimits.get(j).get(1);
	    		int ybottom=floorLimits.get(j).get(2); 
	    		int ytop=floorLimits.get(j).get(3)-10;	    		
	    		if (y>=ytop && y<=ybottom && x>=xleft && x<=xright) { 
	    			if (yy==0) { y-=dy; }
    				if (yy==1) { y+=dy; }
	    			while (y>=ytop && y<=ybottom && x>=xleft && x<=xright) {
	    				if(i%2==0) {
	    					if (j%2==0) {
	    						y-=dy;
	    					}else {
	    						y+=dy;
	    					}
	    				}else {
	    					y+=dy;
	    				}
	    				if (xx==0) { x-=dx; }	//0.5*dx; }			    				
	    				if (xx==1) { x+=dx;	}	//0.5*dx; }		

	    			}
	    			break;
	    		}
	    	}
	    	
     	}else {				//if (dxp>=x_real_door) 
       		int xx=1000; 	//xx=0 -> x+=dx alliws xx=1 -> x-=dx, ara meta an xx=0 -> x-=dx k an xx=1 -> x+=dx
       		int yy=1000;
       		//edw to proion vriskete pio deksia tis portas, ara me to poy mpainei o pelatis simenei oti to x toy ine mikrotero apo to proion, afoy mpainei apo tin porta!!!
       		
    		if (stages.get(i)[0]==true) { //dld an einai sto stadio poy prospathei na paei pros to proion
       			if(x<dxp) {
       				x+=dx;
       				xx=0;
       			}
       			if(y<dyp) {
       				y+=dy;
       				yy=0;
       			}
       			
       			if(x>=dxp && y>=dyp) { //shmainei oti efase sto prooion
       				boolean stage[] = new boolean[4];
       				stage[0]=false; //afto to kanoyme false giati den eimaste allo se afto to stadio
       				stage[1]=true; //afto to afinoyme ws exxei giati einai afto poy synexizei tin volta sto katastima
       				stage[2]=false; //afto to allazoyme se true giati imaste se afto to stadio poy theloyme na paei sto tameio
       				stage[3]=false; //to afisnoyme ws exei
       				stages.set(i, stage);
       				long ws_end = System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
       			}
       		}
       		
       		if (stages.get(i)[1]==true) { //dld an einai sto stadio poy prospathei na paei pros to proion
       			
       			if(first_random) {
       				first_random_time1 = System.currentTimeMillis();
       				first_random_time2 = System.currentTimeMillis();
       				first_random_time = first_random_time2 - first_random_time1; 
       				Random r = new Random();
       				first_random_wait = (r.nextInt(4)+5)*1000; //ya nai tyxaio plithos milliseconds (1000ms=1sec) kai na nai sigoyra > 5 seconds
       				first_random = false;
       			}
       			
   				first_random_time2 = System.currentTimeMillis();
   				first_random_time = first_random_time2 - first_random_time1; 
       			if(first_random_time > first_random_wait) {
       				boolean stage[] = new boolean[4];
       				stage[0]=false; //afto to kanoyme false giati den eimaste allo se afto to stadio
       				stage[1]=false; //afto to afinoyme ws exxei giati einai afto poy synexizei tin volta sto katastima
       				stage[2]=true; //afto to allazoyme se true giati imaste se afto to stadio poy theloyme na paei sto tameio
       				stage[3]=false; //to afisnoyme ws exei
       				stages.set(i, stage);      				
       			}else {
       				random(i, xleft_f, xright_f, ybottom_f, ytop_f);	
       			}
       		}
       		
       		//afto okei, to allazo na lew oti i morfi toy arxeioy exei sugkekrimeni tami to tameio -> proteleftea -> prin tin porta!!!
       		if(stages.get(i)[2]==true) { //dld mexri na paei sto tameio      			
       			//to tameio einai ya x>=1200 kai ta y poy mporei na paei einai [170,235], [285,355], [405,470]
       			if(x<1200-10) {
       				x+=dx;
       				xx=0;
       			}
       			
       			if(y<235+10) {
       				y+=dy;
       				yy=0;
       			}
       			
       			if(y>470) {
       				y-=dy;
       				yy=1;
       			}
       			
       			if(x>=1200-10 && (y<=470 || y>=170+10)) {
       				boolean stage[] = new boolean[4];
       				stage[0]=false; 
       				stage[1]=false; 
       				stage[2]=false; 
       				stage[3]=true; //ya na paei pros tin porta
       				long ws_end = System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
       				stages.set(i, stage);
       			}      				
       			
       		}
       		
       		if (stages.get(i)[3]==true) {  //porta     			
       			if(x<=x_real_door) {
       				x+=dx;
       				xx=0;
       				if(x>=x_real_door) {
       					x-=dx;
       					xx=1;
       				}
       			}else {
       				x-=dx;
       				xx=1;
       				if(x<=x_real_door) {
       					x+=dx;
       					xx=0;
       				}
       			}
       			if(y>y_real_door) {
       				y-=dy;
       				yy=1;
       			}
       			if (x>=x_real_door-10 && y<=y_real_door+10) {
       				y-=15;
       			}
       		}
       		
       		//Oi parakatw elegxoi ya ta eswterika empodia k tin katopsi tha nai idioi kai ya ta 3 stadia (proion, tameio, fevgei)
   			//afta einai ya ta oria tis katopsis
       		//synepws aftoi oi elegxoi tha ginonte sto teleos meta tis if ya na doyme se poio stadio vriskomaste!!!
	    	if(x<=xleft_f) {
	    		x+=dx;
	    		xx=0;
	    		y-=dy;	//giati door panw
   				boolean stage[] = new boolean[4];
   				if(i%2==1) { //na paei tameio
	   				stage[0]=false; 
	   				stage[1]=false; 
	   				stage[2]=true; 
	   				stage[3]=false;
       				long ws_end =System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
	   				stages.set(i, stage);
   				} else {
	   				stage[0]=false; 
	   				stage[1]=false; 
	   				stage[2]=false; 
	   				stage[3]=true; //alliws na fygoyn oi alloi misoi
       				long ws_end = System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
	   				stages.set(i, stage);  					
   				}
	    	}
	    	if(x>=xright_f) {
	    		x-=dx; 
	    		xx=1;
	    		y-=dy;	
	    		yy=1; 
	    		
	    	}
	    	if(y>=ybottom_f) {
	    		y-=dy;
	    		yy=1;
	    		x-=dx;
	    		xx=1;
	    	} 
	    	if(y<=ytop_f) {
	    		if(stages.get(i)[3]==false) {
	    			y+=dy;
	    			yy=0;
	    		}
	    	}

	    	for(int j=1; j<floorLimits.size(); j++) {
	    		int xleft=floorLimits.get(j).get(0)-10; //-10 to vazoyme giati to obj exei size=10
	    		int xright=floorLimits.get(j).get(1);
	    		int ybottom=floorLimits.get(j).get(2); 
	    		int ytop=floorLimits.get(j).get(3)-10;	    		
	    		if (y>=ytop && y<=ybottom && x>=xleft && x<=xright) { 
	    			if (yy==0) { y-=dy; }
    				if (yy==1) { y+=dy; }
	    			while (y>=ytop && y<=ybottom && x>=xleft && x<=xright) {
	    			/*	if (yy==0) { y+=dy; } //sto y einai i idia antistoixia, giati theloyme na synexisoyn stin poreia poy pigenan
	    				if (yy==1) { y-=dy; }*/
	    				//afto ya na yparxei poikilia ana stand k ana pelati
	    				if(i%2==0) {
	    					if (j%2==0) {
	    						y-=dy;
	    					}else {
	    						y+=dy;
	    					}
	    				}else {
	    					y+=dy;
	    				}
	    				if (xx==0) { x-=dx; }	//0.5*dx; }			    				
	    				if (xx==1) { x+=dx;	}	//0.5*dx; }		

	    			}
	    			break;
	    		}
	    	} 
	    }    	
    }
    
    public void category3(int i, int xleft_f, int xright_f, int ybottom_f, int ytop_f, double dxp, double dyp) {
    	//epilegei proion->fevge (oxi tameio, dld den to agorazei)
    	//dld apo stage[0] -> stage[3]
      	if(dxp < x_real_door) { //1st case
       		//if ya stadia kai analoga na paei pros tin antistoixi katefthinsi //prepei na ta kanei me tin seira ara tha ecxo  3 metavlites tis opoies px otan ftasei sto proion tha kanei true afti poy einai ya na paei sto tameio//meta otan tha ftastei sto tameio tha kanei true afti poy einai ya na fygei//kai ennoeite i proti poy tha mpainei tha nai i true metavliti mexri na ftasei k me to poy ftanei tin kanei false ya na min ksanampei //eite mporoyme na exoume enan array 2d me 3 theseis ya kathe boolean metavliti toy analogoy stadioy//opoy i kathe thesi toy array tha antistoixei se kathe object//giati den ginete na xoyme mia ksexoy metavliti boolean kathos trexei afti i sinartisi ya kathe vima kathe antikimenoy!!
       		
       		int xx=1000; 	//xx=0 -> x+=dx alliws xx=1 -> x-=dx, ara meta an xx=0 -> x-=dx k an xx=1 -> x+=dx
       		int yy=1000;
       		
       		if (stages.get(i)[0]==true) { //dld an einai sto stadio poy prospathei na paei pros to proion
       			if(x>dxp) {
       				x-=dx;
       				xx=1;
       			}
       			if(y<dyp) {
       				y+=dy;
       				yy=0;
       			}
       			
       			if(x<=dxp && y>=dyp) { //shmainei oti efase sto prooion
       				boolean stage[] = new boolean[4];
       				stage[0]=false; 
       				stage[1]=false; 
       				stage[2]=false; 
       				stage[3]=true; 
       				stages.set(i, stage);
       				long ws_end = System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
       			}
       		}
       		
       		if (stages.get(i)[3]==true) {       			
       			if(x<=x_real_door) {
       				x+=dx;
       				xx=0;
       				if(x>=x_real_door) {
       					x-=dx;
       					xx=1;
       				}
       			}else {
       				x-=dx;
       				xx=1;
       				if(x<=x_real_door) {
       					x+=dx;
       					xx=0;
       				}
       			}
       			if(y>y_real_door) {
       				y-=dy;
       				yy=1;
       			}
       			if (x>=x_real_door-10 && y<=y_real_door+10) {
       				y-=15;
       			}
       		}
   
	    	if(x<=xleft_f) {
	    		x+=dx;
	    		xx=0;
	    		y-=dy;	//giati door panw
   				boolean stage[] = new boolean[4];
   				stage[0]=false; 
   				stage[1]=false; 
   				stage[2]=false; 
   				stage[3]=true;
   				long ws_end =System.currentTimeMillis();
   				stand_wait = ws_end - start_time;
   				while (stand_wait < stand_wait_search) { 	//ms
   					x+=0.01;
   					x-=0.01;
   					y+=0.01;
   					y-=0.01;
   					ws_end = System.nanoTime();
   					stand_wait = ws_end - start_time;
   				}
   				stages.set(i, stage);  					
	    	}
	    	if(x>=xright_f) {
	    		x-=dx; 
	    		xx=1;
	    		y-=dy;	
	    		yy=1; 
	    		
	    	}
	    	if(y>=ybottom_f) {
	    		y-=dy;
	    		yy=1;
	    		x-=dx;
	    		xx=1;
	    	} 
	    	if(y<=ytop_f) {
	    		if(stages.get(i)[3]==false) {
	    			y+=dy;
	    			yy=0;
	    		}
	    	}

	    	for(int j=1; j<floorLimits.size(); j++) {
	    		int xleft=floorLimits.get(j).get(0)-10; //-10 to vazoyme giati to obj exei size=10
	    		int xright=floorLimits.get(j).get(1);
	    		int ybottom=floorLimits.get(j).get(2); 
	    		int ytop=floorLimits.get(j).get(3)-10;	    		
	    		if (y>=ytop && y<=ybottom && x>=xleft && x<=xright) { 
	    			if (yy==0) { y-=dy; }
    				if (yy==1) { y+=dy; }
	    			while (y>=ytop && y<=ybottom && x>=xleft && x<=xright) {
	    				if(i%2==0) {
	    					if (j%2==0) {
	    						y-=dy;
	    					}else {
	    						y+=dy;
	    					}
	    				}else {
	    					y+=dy;
	    				}
	    				if (xx==0) { x-=dx; }	//0.5*dx; }			    				
	    				if (xx==1) { x+=dx;	}	//0.5*dx; }		

	    			}
	    			break;
	    		}
	    	}
	    	
     	}else {				//if (dxp>=x_real_door) 
       		int xx=1000; 	//xx=0 -> x+=dx alliws xx=1 -> x-=dx, ara meta an xx=0 -> x-=dx k an xx=1 -> x+=dx
       		int yy=1000;
       		//edw to proion vriskete pio deksia tis portas, ara me to poy mpainei o pelatis simenei oti to x toy ine mikrotero apo to proion, afoy mpainei apo tin porta!!!
       		
    		if (stages.get(i)[0]==true) { //dld an einai sto stadio poy prospathei na paei pros to proion
       			if(x<dxp) {
       				x+=dx;
       				xx=0;
       			}
       			if(y<dyp) {
       				y+=dy;
       				yy=0;
       			}
       			
       			if(x>=dxp && y>=dyp) { //shmainei oti efase sto prooion
       				boolean stage[] = new boolean[4];
       				stage[0]=false; //afto to kanoyme false giati den eimaste allo se afto to stadio
       				stage[1]=false; //afto to afinoyme ws exxei giati einai afto poy synexizei tin volta sto katastima
       				stage[2]=false; //afto to allazoyme se true giati imaste se afto to stadio poy theloyme na paei sto tameio
       				stage[3]=true; //to afisnoyme ws exei
       				stages.set(i, stage);
       				long ws_end = System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
       			}
       		}
    		
    		if (stages.get(i)[3]==true) {  //porta     			
       			if(x<=x_real_door) {
       				x+=dx;
       				xx=0;
       				if(x>=x_real_door) {
       					x-=dx;
       					xx=1;
       				}
       			}else {
       				x-=dx;
       				xx=1;
       				if(x<=x_real_door) {
       					x+=dx;
       					xx=0;
       				}
       			}
       			if(y>y_real_door) {
       				y-=dy;
       				yy=1;
       			}
       			if (x>=x_real_door-10 && y<=y_real_door+10) {
       				y-=15;
       			}
       		}
    		
	    	if(x<=xleft_f) {
	    		x+=dx;
	    		xx=0;
	    		y-=dy;	//giati door panw
   				boolean stage[] = new boolean[4];
   				stage[0]=false; 
   				stage[1]=false; 
   				stage[2]=false; 
   				stage[3]=true;
   				long ws_end =System.currentTimeMillis();
   				stand_wait = ws_end - start_time;
   				while (stand_wait < stand_wait_search) { 	//ms
   					x+=0.01;
   					x-=0.01;
   					y+=0.01;
   					y-=0.01;
   					ws_end = System.nanoTime();
   					stand_wait = ws_end - start_time;
   				}
   				stages.set(i, stage);
	    	}
	    	if(x>=xright_f) {
	    		x-=dx; 
	    		xx=1;
	    		y-=dy;	
	    		yy=1; 
	    		
	    	}
	    	if(y>=ybottom_f) {
	    		y-=dy;
	    		yy=1;
	    		x-=dx;
	    		xx=1;
	    	} 
	    	if(y<=ytop_f) {
	    		if(stages.get(i)[3]==false) {
	    			y+=dy;
	    			yy=0;
	    		}
	    	}

	    	for(int j=1; j<floorLimits.size(); j++) {
	    		int xleft=floorLimits.get(j).get(0)-10; //-10 to vazoyme giati to obj exei size=10
	    		int xright=floorLimits.get(j).get(1);
	    		int ybottom=floorLimits.get(j).get(2); 
	    		int ytop=floorLimits.get(j).get(3)-10;	    		
	    		if (y>=ytop && y<=ybottom && x>=xleft && x<=xright) { 
	    			if (yy==0) { y-=dy; }
    				if (yy==1) { y+=dy; }
	    			while (y>=ytop && y<=ybottom && x>=xleft && x<=xright) {
	    				if(i%2==0) {
	    					if (j%2==0) {
	    						y-=dy;
	    					}else {
	    						y+=dy;
	    					}
	    				}else {
	    					y+=dy;
	    				}
	    				if (xx==0) { x-=dx; }	//0.5*dx; }			    				
	    				if (xx==1) { x+=dx;	}	//0.5*dx; }		

	    			}
	    			break;
	    		}
	    	} 
	    }    	
    }
    
    public void category4(int i, int xleft_f, int xright_f, int ybottom_f, int ytop_f, double dxp, double dyp) {
    	//epilegei proion->volta katastima (oxi tameio, den fevgei)
    	//ara apo stage[0] -> stage[1]
    	if(dxp < x_real_door) { //1st case
       		//if ya stadia kai analoga na paei pros tin antistoixi katefthinsi //prepei na ta kanei me tin seira ara tha ecxo  3 metavlites tis opoies px otan ftasei sto proion tha kanei true afti poy einai ya na paei sto tameio//meta otan tha ftastei sto tameio tha kanei true afti poy einai ya na fygei//kai ennoeite i proti poy tha mpainei tha nai i true metavliti mexri na ftasei k me to poy ftanei tin kanei false ya na min ksanampei //eite mporoyme na exoume enan array 2d me 3 theseis ya kathe boolean metavliti toy analogoy stadioy//opoy i kathe thesi toy array tha antistoixei se kathe object//giati den ginete na xoyme mia ksexoy metavliti boolean kathos trexei afti i sinartisi ya kathe vima kathe antikimenoy!!
       		
       		int xx=1000; 	//xx=0 -> x+=dx alliws xx=1 -> x-=dx, ara meta an xx=0 -> x-=dx k an xx=1 -> x+=dx
       		int yy=1000;
       		
       		if (stages.get(i)[0]==true) { //dld an einai sto stadio poy prospathei na paei pros to proion
       			if(x>dxp) {
       				x-=dx;
       				xx=1;
       			}
       			if(y<dyp) {
       				y+=dy;
       				yy=0;
       			}
       			
       			if(x<=dxp && y>=dyp) { //shmainei oti efase sto prooion
       				boolean stage[] = new boolean[4];
       				stage[0]=false; //afto to kanoyme false giati den eimaste allo se afto to stadio
       				stage[1]=true; //afto to afinoyme ws exxei giati einai afto poy synexizei tin volta sto katastima
       				stage[2]=false; //afto to allazoyme se true giati imaste se afto to stadio poy theloyme na paei sto tameio
       				stage[3]=false; //to afisnoyme ws exei
       				stages.set(i, stage);
       				long ws_end = System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
       			}
       		}
       		
       		if (stages.get(i)[1]==true) { //dld an einai sto stadio poy prospathei na paei pros to proion
       			random(i, xleft_f, xright_f, ybottom_f, ytop_f);
       		}

	    	if(x<=xleft_f) {
	    		x+=dx;
	    		xx=0;
	    		y-=dy;	//giati door panw
	    		yy=1;
	    		random(i, xleft_f, xright_f, ybottom_f, ytop_f);
	    	}
	    	if(x>=xright_f) {
	    		x-=dx; 
	    		xx=1;
	    		y-=dy;	
	    		yy=1; 
	    		random(i, xleft_f, xright_f, ybottom_f, ytop_f);
	    	}
	    	if(y>=ybottom_f) {
	    		y-=dy;
	    		yy=1;
	    		x-=dx;
	    		xx=1;
	    		random(i, xleft_f, xright_f, ybottom_f, ytop_f);
	    	} 
	    	if(y<=ytop_f) {
    			y+=dy;
    			yy=0;
    			random(i, xleft_f, xright_f, ybottom_f, ytop_f);
	    	}

	    	for(int j=1; j<floorLimits.size(); j++) {
	    		int xleft=floorLimits.get(j).get(0)-10; //-10 to vazoyme giati to obj exei size=10
	    		int xright=floorLimits.get(j).get(1);
	    		int ybottom=floorLimits.get(j).get(2); 
	    		int ytop=floorLimits.get(j).get(3)-10;	    		
	    		if (y>=ytop && y<=ybottom && x>=xleft && x<=xright) { 
	    			if (yy==0) { y-=dy; }
    				if (yy==1) { y+=dy; }
	    			while (y>=ytop && y<=ybottom && x>=xleft && x<=xright) {
	    				if(i%2==0) {
	    					if (j%2==0) {
	    						y-=dy;
	    					}else {
	    						y+=dy;
	    					}
	    				}else {
	    					y+=dy;
	    				}
	    				if (xx==0) { x-=dx; }	//0.5*dx; }			    				
	    				if (xx==1) { x+=dx;	}	//0.5*dx; }		

	    			}
	    			break;
	    		}
	    	}
	    	
     	}else {				//if (dxp>=x_real_door) 
       		int xx=1000; 	//xx=0 -> x+=dx alliws xx=1 -> x-=dx, ara meta an xx=0 -> x-=dx k an xx=1 -> x+=dx
       		int yy=1000;
       		//edw to proion vriskete pio deksia tis portas, ara me to poy mpainei o pelatis simenei oti to x toy ine mikrotero apo to proion, afoy mpainei apo tin porta!!!
       		
    		if (stages.get(i)[0]==true) { //dld an einai sto stadio poy prospathei na paei pros to proion
       			if(x<dxp) {
       				x+=dx;
       				xx=0;
       			}
       			if(y<dyp) {
       				y+=dy;
       				yy=0;
       			}
       			
       			if(x>=dxp && y>=dyp) { //shmainei oti efase sto prooion
       				boolean stage[] = new boolean[4];
       				stage[0]=false; //afto to kanoyme false giati den eimaste allo se afto to stadio
       				stage[1]=true; //afto to afinoyme ws exxei giati einai afto poy synexizei tin volta sto katastima
       				stage[2]=false; //afto to allazoyme se true giati imaste se afto to stadio poy theloyme na paei sto tameio
       				stage[3]=false; //to afisnoyme ws exei
       				stages.set(i, stage);
       				long ws_end = System.currentTimeMillis();
       				stand_wait = ws_end - start_time;
       				while (stand_wait < stand_wait_search) { 	//ms
       					x+=0.01;
       					x-=0.01;
       					y+=0.01;
       					y-=0.01;
       					ws_end = System.nanoTime();
       					stand_wait = ws_end - start_time;
       				}
       			}
       		}
       		
    		if (stages.get(i)[1]==true) { //dld an einai sto stadio poy prospathei na paei pros to proion
    			random(i, xleft_f, xright_f, ybottom_f, ytop_f);
       		}      		

	    	if(x<=xleft_f) {
	    		x+=dx;
	    		xx=0;
	    		y-=dy;	//giati door panw
	    		yy=1;
	    		random(i, xleft_f, xright_f, ybottom_f, ytop_f);
	    	}
	    	if(x>=xright_f) {
	    		x-=dx; 
	    		xx=1;
	    		y-=dy;	
	    		yy=1; 
	    		random(i, xleft_f, xright_f, ybottom_f, ytop_f);
	    	}
	    	if(y>=ybottom_f) {
	    		y-=dy;
	    		yy=1;
	    		x-=dx;
	    		xx=1;
	    		random(i, xleft_f, xright_f, ybottom_f, ytop_f);
	    	} 
	    	if(y<=ytop_f) {
    			y+=dy;
    			yy=0;
    			random(i, xleft_f, xright_f, ybottom_f, ytop_f);
	    	}

	    	for(int j=1; j<floorLimits.size(); j++) {
	    		int xleft=floorLimits.get(j).get(0)-10; //-10 to vazoyme giati to obj exei size=10
	    		int xright=floorLimits.get(j).get(1);
	    		int ybottom=floorLimits.get(j).get(2); 
	    		int ytop=floorLimits.get(j).get(3)-10;	    		
	    		if (y>=ytop && y<=ybottom && x>=xleft && x<=xright) { 
	    			if (yy==0) { y-=dy; }
    				if (yy==1) { y+=dy; }
	    			while (y>=ytop && y<=ybottom && x>=xleft && x<=xright) {
	    				if(i%2==0) {
	    					if (j%2==0) {
	    						y-=dy;
	    					}else {
	    						y+=dy;
	    					}
	    				}else {
	    					y+=dy;
	    				}
	    				if (xx==0) { x-=dx; }	//0.5*dx; }			    				
	    				if (xx==1) { x+=dx;	}	//0.5*dx; }		

	    			}
	    			break;
	    		}
	    	} 
	    }    	
    }

	public String writeFile(int no) {
		int num=no;
		//eksagogi data for costumer behavior
		String writeline = "Costumer: "+num+" -> "+coordinates_products.get(num);
		return writeline;
    }
    
    public void draw(Graphics g) {
    	//afto einai gia aftoys toys pelates poy tha mpoune meta!!!
    	//950,20 gia floor plan 1 kai 450,600
    	
    	if (x_real_door==450 && y_real_door==600) {
        	if(x==485 && y==610) { //einai apla ta x_door kai y_door poy exo hdh yplogismena, apla ya syntomia, sto telos na to allaksw..
        		g.setColor(java.awt.Color.white);
        		g.fillRect((int) x, (int) y, size, size);    		
        	}else {
        		g.setColor(generateRandomColor());
        		g.fillRect((int) x, (int) y, size, size);
        	}   		
    	}else {
        	if(x==960 && y==10) { //einai apla ta x_door kai y_door poy exo hdh yplogismena, apla ya syntomia, sto telos na to allaksw..
        		g.setColor(java.awt.Color.white);
        		g.fillRect((int) x, (int) y, size, size);    		
        	}else {
        		g.setColor(generateRandomColor());
        		g.fillRect((int) x, (int) y, size, size);
        	}    		
    	}
    }

    private Color generateRandomColor() {
        int R = (int) (Math.random() * 256);
        int G = (int) (Math.random() * 256);
        int B = (int) (Math.random() * 256);
        if (color_first) {
        	Color c = new Color(R, G, B);
        	color_first=false;
        	col=c;
        	return c;
        } else {
        	return col;
        }
    }
    
    
}

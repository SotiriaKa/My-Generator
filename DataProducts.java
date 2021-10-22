package example_of_simulator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


//8 categories for floor plan :
//1. offers
//2. TV&Image
//3. Books&Comics
//4. CDs&Vinyls
//5. Gaming
//6. Computers&Peripherals
//7. Photos&Videos
//8. Mobile_phones&Tablets

public class DataProducts {
	public static void main(String[] args) {
				File file1 = null;
				BufferedWriter bw=null;
				int j=1;
				double x=10.0;
				double y=20.0;
				try {
					file1 = new File("C:\\Users\\Σωτηρία\\Desktop\\eclipse-workspace\\simulator\\src\\example_of_simulator\\Floor Plans and Products\\products_floor_plan_1.txt");
					FileOutputStream fos = new FileOutputStream(file1);
					bw = new BufferedWriter(new OutputStreamWriter(fos));

					while (j<500) {
						bw.write(j+",offer"+j+",("+x+","+y+"),offers");
						bw.newLine();
						x+=1.84;
						j++;
					}
					
					y=600.0;
					x=10.0;
					int k=0;
					while(k<400) {
						bw.write(j+",TV"+j+",("+x+","+y+"),TV&Image");
						bw.newLine();
						j++;
						x+=2.3;

						k++;
					}
					k=0;
					y=20.0;
					x=10.0;
					while(k<1000) {
						bw.write(j+",Book"+j+",("+x+","+y+"),Books&Comics");
						bw.newLine();
						j++;
						//(600-20)/1000
						y+=0.58;
						k++;
					}
					
					//ya ta CD kai vinylia
					x=950.0;
					y=500.0;
					k=0;
					while(k<30) {
						bw.write(j+",CD"+j+",("+x+","+y+"),CDs&Vinyls");
						bw.newLine();
						j++;
						y+=3.3;
						k++;
					}
					x=950.0;
					y=500.0;
					k=0;
					while(k<100) {
						bw.write(j+",CD"+j+",("+x+","+y+"),CDs&Vinyls");
						bw.newLine();
						j++;
						x+=3.5;
						k++;
					}
					
					//Gaming
					x=60.0;
					y=70.0;
					k=0;
					while(k<40) {
						bw.write(j+",gaming"+j+",("+x+","+y+"),Gaming");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}
					x=160.0;
					y=70.0;
					k=0;
					while(k<40) {
						bw.write(j+",gaming"+j+",("+x+","+y+"),Gaming");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}
					x=60.0;
					y=70.0;
					k=0;
					while(k<20) {
						bw.write(j+",gaming"+j+",("+x+","+y+"),Gaming");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=60.0;
					y=270.0;
					k=0;
					while(k<20) {
						bw.write(j+",gaming"+j+",("+x+","+y+"),Gaming");
						bw.newLine();
						j++;
						//(600-20)/1000
						x+=5.0;
						k++;
					}
					x=60.0;
					y=350.0;
					k=0;
					while(k<40) {
						bw.write(j+",gaming"+j+",("+x+","+y+"),Gaming");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}
					x=160.0;
					y=350.0;
					k=0;
					while(k<40) {
						bw.write(j+",gaming"+j+",("+x+","+y+"),Gaming");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}
					x=60.0;
					y=350.0;
					k=0;
					while(k<20) {
						bw.write(j+",gaming"+j+",("+x+","+y+"),Gaming");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=60.0;
					y=550.0;
					k=0;
					while(k<20) {
						bw.write(j+",gaming"+j+",("+x+","+y+"),Gaming");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					
					x=240.0;
					y=70.0;
					k=0;
					while(k<20) {
						bw.write(j+",PC"+j+",("+x+","+y+"),Computers&Peripherals");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=240.0;
					y=70.0;
					k=0;
					while(k<40) {
						bw.write(j+",PC"+j+",("+x+","+y+"),Computers&Peripherals");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}
					x=240.0;
					y=270.0;
					k=0;
					while(k<20) {
						bw.write(j+",PC"+j+",("+x+","+y+"),Computers&Peripherals");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=340.0;
					y=70.0;
					k=0;
					while(k<40) {
						bw.write(j+",PC"+j+",("+x+","+y+"),Computers&Peripherals");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}
					
					x=420.0;
					y=70.0;
					k=0;
					while(k<20) {
						bw.write(j+",PC"+j+",("+x+","+y+"),Computers&Peripherals");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=420.0;
					y=70.0;
					k=0;
					while(k<40) {
						bw.write(j+",PC"+j+",("+x+","+y+"),Computers&Peripherals");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}
					x=420.0;
					y=270.0;
					k=0;
					while(k<20) {
						bw.write(j+",PC"+j+",("+x+","+y+"),Computers&Peripherals");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=520.0;
					y=70.0;
					k=0;
					while(k<40) {
						bw.write(j+",PC"+j+",("+x+","+y+"),Computers&Peripherals");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}

					x=240.0;
					y=350.0;
					k=0;
					while(k<20) {
						bw.write(j+",PC"+j+",("+x+","+y+"),Computers&Peripherals");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=240.0;
					y=350.0;
					k=0;
					while(k<40) {
						bw.write(j+",PC"+j+",("+x+","+y+"),Computers&Peripherals");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}
					x=240.0;
					y=550.0;
					k=0;
					while(k<20) {
						bw.write(j+",PC"+j+",("+x+","+y+"),Computers&Peripherals");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=340.0;
					y=350.0;
					k=0;
					while(k<40) {
						bw.write(j+",PC"+j+",("+x+","+y+"),Computers&Peripherals");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}					

					x=420.0;
					y=350.0;
					k=0;
					while(k<20) {
						bw.write(j+",PC"+j+",("+x+","+y+"),Computers&Peripherals");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=420.0;
					y=350.0;
					k=0;
					while(k<40) {
						bw.write(j+",PC"+j+",("+x+","+y+"),Computers&Peripherals");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}
					x=420.0;
					y=550.0;
					k=0;
					while(k<20) {
						bw.write(j+",PC"+j+",("+x+","+y+"),Computers&Peripherals");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=520.0;
					y=350.0;
					k=0;
					while(k<40) {
						bw.write(j+",PC"+j+",("+x+","+y+"),Computers&Peripherals");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}					
					//photos and videos
					x=600.0;
					y=70.0;
					k=0;
					while(k<20) {
						bw.write(j+",photo"+j+",("+x+","+y+"),Photos&Videos");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=600.0;
					y=70.0;
					k=0;
					while(k<40) {
						bw.write(j+",photo"+j+",("+x+","+y+"),Photos&Videos");
						bw.newLine();
						j++;
						//(600-20)/1000
						y+=5.0;
						k++;
					}
					x=600.0;
					y=270.0;
					k=0;
					while(k<20) {
						bw.write(j+",photo"+j+",("+x+","+y+"),Photos&Videos");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=700.0;
					y=70.0;
					k=0;
					while(k<40) {
						bw.write(j+",photo"+j+",("+x+","+y+"),Photos&Videos");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}
					
					x=600.0;
					y=350.0;
					k=0;
					while(k<20) {
						bw.write(j+",photo"+j+",("+x+","+y+"),Photos&Videos");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=600.0;
					y=350.0;
					k=0;
					while(k<40) {
						bw.write(j+",photo"+j+",("+x+","+y+"),Photos&Videos");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}
					x=600.0;
					y=550.0;
					k=0;
					while(k<20) {
						bw.write(j+",photo"+j+",("+x+","+y+"),Photos&Videos");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=700.0;
					y=350.0;
					k=0;
					while(k<40) {
						bw.write(j+",photo"+j+",("+x+","+y+"),Photos&Videos");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}
					//mobiles kai tablets
					x=780.0;
					y=70.0;
					k=0;
					while(k<20) {
						bw.write(j+",mobile"+j+",("+x+","+y+"),Mobile_phones&Tablets");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=780.0;
					y=70.0;
					k=0;
					while(k<40) {
						bw.write(j+",mobile"+j+",("+x+","+y+"),Mobile_phones&Tablets");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}
					x=780.0;
					y=270.0;
					k=0;
					while(k<20) {
						bw.write(j+",mobile"+j+",("+x+","+y+"),Mobile_phones&Tablets");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=880.0;
					y=70.0;
					k=0;
					while(k<40) {
						bw.write(j+",mobile"+j+",("+x+","+y+"),Mobile_phones&Tablets");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}
					
					x=780.0;
					y=350.0;
					k=0;
					while(k<20) {
						bw.write(j+",mobile"+j+",("+x+","+y+"),Mobile_phones&Tablets");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=780.0;
					y=350.0;
					k=0;
					while(k<40) {
						bw.write(j+",mobile"+j+",("+x+","+y+"),Mobile_phones&Tablets");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}
					x=780.0;
					y=550.0;
					k=0;
					while(k<20) {
						bw.write(j+",mobile"+j+",("+x+","+y+"),Mobile_phones&Tablets");
						bw.newLine();
						j++;
						x+=5.0;
						k++;
					}
					x=880.0;
					y=350.0;
					k=0;
					while(k<40) {
						bw.write(j+",mobile"+j+",("+x+","+y+"),Mobile_phones&Tablets");
						bw.newLine();
						j++;
						y+=5.0;
						k++;
					}					
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
			}
}

package example_of_simulator;

public class RunBefore implements Runnable {

    private volatile boolean running;
	private boolean exit=false;

    public RunBefore() {}

    @Override
    public void run() {
        while (running && exit==false) {
            sleep();
        }
    }
    
    private void sleep() {
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {}
    }

    public synchronized void setRunning(boolean running,boolean exit) {
        this.running = running;
        this.exit=exit;
    }

    public void stop(){
        exit = true;
    }
}

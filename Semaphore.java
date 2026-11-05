public class Semaphore {
    private int value;
    public Semaphore() { value = 0 ;}
    public Semaphore(int initial) {value = initial;}
       
    public synchronized void P() throws InterruptedException {
        while (value == 0) {
            wait();
        }
        value--;
    }
    
    public synchronized void P(String message) throws InterruptedException { 
    	boolean print= false;
    	while(value==0) {
    		if(!print&& message !=null) {
    			System.out.println(message);
    			print= true;
    		}
    		wait();
    	}
    	value--;
    }
   

    public synchronized void V() {
        value++;
        notifyAll();
    }

    public synchronized boolean check(long t)
            throws InterruptedException {

        long start = System.nanoTime();
        long timeout = t * 1_000_000L;
        long remaining = timeout;

        while (value == 0 && remaining > 0) {

            long millis = remaining / 1_000_000L;
            int nanos = (int) (remaining % 1_000_000L);

            wait(millis, nanos);

            remaining = timeout - (System.nanoTime() - start);
        }
        if (value > 0) {
            value--;
            return true;
        }
        return false;
    }
}

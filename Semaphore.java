public class Semaphore {   
    protected int value = 0 ;
    
    protected Semaphore() { value = 0 ; }
    protected Semaphore(int initial) { value = initial ; }
    public synchronized void P()
    {
        value-- ;
        while (value < 0){
            try{ 
                wait();
            }
            catch( InterruptedException e )
            {
                Thread.currentThread().interrupt();
                value++;
            }
        }
    }
    public synchronized void V()
    {
        value++; 
        notifyAll();
    }
    public synchronized boolean check (long t) {
        if (value > 0) {
            value--;
            return true;
        }

        long startTime = System.currentTimeMillis();
        long remained=t;

        while (remained>0 && value<=0)
        {
            try{
                wait(remained);
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
                value++;  
            } 
            remained=t-(System.currentTimeMillis()-startTime);   
        }
        if (value > 0){
                value--;
                return true;
        }
        return false; 
    } 
}
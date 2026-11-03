public class WaitingRoom {
    private int size; // the buffer bound
    private Patient Capacity[] ;
    private int inptr = 0;
    private int outptr = 0;
    private Semaphore key;
    Semaphore spaces ;
    Semaphore elements ;
    WaitingRoom(int n){
        size = n;
        key =new Semaphore(1);
        Capacity = new Patient[size];
        spaces = new Semaphore(size);
        elements=new Semaphore(0);
    }
    public  void  produce(Patient value) {
        spaces.P();
        key.P();
        Capacity[inptr] = value;
        inptr = (inptr + 1) % size;
        elements.V();
        key.V();
    }
    public  Patient consume() {
        Patient value;
        elements.P();
        key.P();
        value = Capacity[outptr];
        outptr = (outptr + 1) % size;
        spaces.V();
        key.V();
        return value;
    }
}

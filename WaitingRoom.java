import java.util.Scanner;
public class WaitingRoom {
    private int size; // the buffer bound
    private Object Capacity[] = new Object[size];
    private int inptr = 0;
    private int outptr = 0;

    Semaphore spaces = new Semaphore(size);
    Semaphore elements = new Semaphore(0);
    public void produce(Object value) {
        spaces.P();
        Capacity[inptr] = value;
        inptr = (inptr + 1) % size;
        elements.V();
    }
    public Object consume() {
        Object value;
        elements.P();
        value = Capacity[outptr];
        outptr = (outptr + 1) % size;
        spaces.V();
        return value;
    }
}

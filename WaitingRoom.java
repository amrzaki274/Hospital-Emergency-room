
public class WaitingRoom {
	private int size;
	private  Patient Capacity[];
	private int inptr=0;
	private int outptr=0;
	 private Semaphore key;
	 private Semaphore spaces;
	 private Semaphore elements;
	 
	public WaitingRoom(int n ) {
		size = n; 
		Capacity = new Patient[size];
		key = new Semaphore(1);
		spaces = new Semaphore(size);
		elements = new Semaphore(0);
	}
	
	public void produce(Patient val) throws InterruptedException {
		spaces.P("The waiting room is full\n" + val.getPatientId() + " is waiting outside for an available seat\n");
		try {
			key.P();  // lock the buffer so no other patient or doctor can use it
		}catch(InterruptedException e) {
			spaces.V();
			throw e ;
		}
		 
		Capacity[inptr]=val;
		inptr = (inptr + 1) % size;
		System.out.print(val.getPatientId() + " eneterd the waiting room\n");
		key.V();
		elements.V();
	}
	  public Patient consume() throws InterruptedException {
	        Patient value;
	        elements.P();
	        try {
	            key.P();
	        } catch (InterruptedException e) {
	            elements.V();
	            throw e;
	        }
	        value = Capacity[outptr];
	        Capacity[outptr] = null;
	        outptr = (outptr + 1) % size;
	        key.V();
	        spaces.V();
	        return value;
	    }
}

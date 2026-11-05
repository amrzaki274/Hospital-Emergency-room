import java.util.Random;
public class Doctor extends Thread {
	private int id;
	private WaitingRoom waitingroom;
	private Semaphore treatmentrooms;
	private Random random = new Random();
	
	public Doctor(int ID , WaitingRoom r , Semaphore treatmentRooms) {
		id= ID;
		waitingroom= r;
		treatmentrooms =  treatmentRooms;
	}
		@Override
		public void run() {

	        while (!Thread.currentThread().isInterrupted()) {

	            Patient p = null;

	            boolean acquired = false;

	            try {

	                p = waitingroom.consume(); //  Doctor takes a patient from the buffer (Waiting room )

	                System.out.println("Doctor " + id+ " is assigned to " + p.getPatientId() + ".");
	                System.out.println("Doctor " + id  + " is trying to acquire a treatment room.");

	                acquired = treatmentrooms.check(3000);  //  check if both treatment rooms are booked within 3 seconds

	                if (!acquired) {

	                    System.out.println("Doctor " + id   + " could not acquire a treatment room.");

	                    System.out.println("Doctor " + id+ " is sending " + p.getPatientId ()+ " back to the waiting room.");

	                    waitingroom.produce(p); // return patient to waiting room

	                    p = null; // the doctor has returned responsibility for that patient to the waiting room.
	                    System.out.println("Doctor " + id
	                            + " is now available.");
	                    continue;
	                }

	                System.out.println("Doctor " + id
	                        + " acquired a treatment room. ");
	                System.out.println("Doctor " + id
	                        + " begins treating " + p.getPatientId() + ".");

	                int treatmentTime = random.nextInt(4) + 1; // The doctor treats the patient for a random duration between 1 and 4 seconds.

	                Thread.sleep(treatmentTime * 1000L);

	                System.out.println("Doctor " + id
	                        + " finished treating  " + p.getPatientId());

	                treatmentrooms.V(); // release treatment room 

	                acquired = false;
	                System.out.println("Doctor " + id
	                        + " released the treatment room.");

	                System.out.println("Doctor " + id
	                        + " is now available.");

	                p.finish();  // notify the patient that the treatment has finished 

	                p = null;

	            } catch (InterruptedException e) {

	                if (acquired) {
	                    treatmentrooms.V();
	                    acquired = false;
	                }

	                if (p != null) {
	                	try {
	                		waitingroom.produce(p);
	                		p= null;
	                	}catch(InterruptedException ex) {
	                		 Thread.currentThread().interrupt();
	                	}

	                
	                }

	                Thread.currentThread().interrupt();
	                break;
	            }
	        }

	        System.out.println("Doctor " + id + " stopped.");
	    }
}

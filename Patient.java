public class Patient extends Thread {
    private int id;
    private WaitingRoom waitingRoom;
    private Semaphore completed = new Semaphore(0); // each patient has their own semaphore

    public Patient(int id, WaitingRoom waitingRoom) {

        this.id = id;
        this.waitingRoom = waitingRoom;
    }

    public String getPatientId() {
        return "P" + id;
    }

    public void finish() {
        completed.V();
    }

    @Override
    public void run() {
        try {

            System.out.println(getPatientId() +  " arrived.");

            waitingRoom.produce(this); // inserting into the waiting room

           // System.out.println("Patient " + id + " is waiting for a doctor.");

            completed.P(); // wait until treatments is completed

            System.out.println(getPatientId() + " left.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(getPatientId() + " was interrupted.");
        }
    }
}

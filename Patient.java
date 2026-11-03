public class Patient extends Thread {
    protected int id ;
    protected String name;
    protected WaitingRoom wRoom;
    public Patient(int i, String n,WaitingRoom r){
        id =i ; name=n; wRoom = r;
    }
    @Override 
    public void run (){
        
        System.out.println("Patient "+name+" has arrived");
        wRoom.produce(this);
        
    }

}

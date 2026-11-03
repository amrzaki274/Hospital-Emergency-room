import java.util.Scanner;

public class Patient extends Thread {
    protected int id ;
    protected String name;
    protected WaitingRoom wroom;
    public Patient(int i, String n){
        id =i ;name=n;
        wroom = new WaitingRoom();
    }

}

import java.util.Scanner;

public class Hospital {
	
	public static void main(String args[]) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter waiting room capacity, Please : ");
		int capacity = input.nextInt();
		
		System.out.print("Enter number of doctors, Please : ");
		int DoctorsCount=input.nextInt();
		
		System.out.print("Enter number of Patients, Please : ");
		int PatientsCount = input.nextInt();
		
		while(capacity<=0 || DoctorsCount<=0 || PatientsCount<=0) {
			System.out.println("Counts must be positive numbers.");
			System.out.print("Enter waiting room capacity, Please : ");
			 capacity = input.nextInt();
			 System.out.print("Enter number of doctors, Please : ");
			 DoctorsCount=input.nextInt();
			 System.out.print("Enter number of Patients, Please : ");
			 PatientsCount = input.nextInt();
		}
		 WaitingRoom waitingRoom = new WaitingRoom(capacity);

	        Semaphore treatmentRooms = new Semaphore(2);

	        Doctor doctors[] = new Doctor[DoctorsCount];

	        Patient patients[] = new Patient[PatientsCount];
	        
	        for (int i=0; i <DoctorsCount; i++) {
	            doctors[i] = new Doctor(i + 1, waitingRoom, treatmentRooms);
	        }

	        // Create patients
	        for (int i=0; i <PatientsCount; i++) {
	            patients[i] = new Patient(i + 1, waitingRoom);
	        }
	        
	        System.out.println("Patients arriving:");
	        
	        for (int i=0; i <PatientsCount; i++) {
	           System.out.print("P" + (i + 1) + " ");
	        }
	        
	        System.out.print("\n");
	        for(int i=0;i < DoctorsCount;i++)
	        {
	        	doctors[i].start();
	        }
	        for(int i=0;i<PatientsCount;i++) {
	        	patients[i].start();
	        }
	        try {
	        	for(int i=0;i<PatientsCount;i++) {
		        	patients[i].join(); // waiting until all patients finish
		        }
	        	System.out.println("ALL PATIENTS HAVE BEEN TREATED..");
	        	for (int i = 0; i < DoctorsCount; i++) {

	                doctors[i].interrupt();
	            }

	            // Wait until doctors stop
	            for (int i=0; i< DoctorsCount; i++) {

	                doctors[i].join();
	            }
	        
	        }catch(InterruptedException e)
	        {
	        	for (int i = 0; i < DoctorsCount; i++) {

	                doctors[i].interrupt();
	            }

	            Thread.currentThread().interrupt();
	        }
	        
	        System.out.println("Simulation completed.");

	        input.close();
	        
	}
	
}

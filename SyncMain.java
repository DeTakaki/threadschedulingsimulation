import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDateTime;	

public class SyncMain {

	public static void main(String[] args) {
		
		int num, inter, unidade, quantum;
		ArrayList<Process> processes = new ArrayList<Process>();
		int x, y;
		Scheduler scheduler;

		System.out.println("Insira num, inter, unidade, quantum: ");
		Scanner scanner = new Scanner(System.in);
		num = scanner.nextInt();
		inter = scanner.nextInt();
		unidade = scanner.nextInt();
		quantum = scanner.nextInt();

		scheduler = new Scheduler(inter, quantum);

		int i = 0;
		while(i < num){
			int pId = scanner.nextInt();
			int prio = scanner.nextInt();	
			int processTime = 2 * (pId + 1) * unidade;
			Process p = new Process(pId, inter, prio, processTime, scheduler, quantum);
			processes.add(p);	
			i++;		
		}	

		scheduler.setProcesses(processes);

		System.out.println(getTime() + "Início da simulação\n");
		for(Process p : processes){
			p.start();
		}

		scheduler.execute();

			
	}

	public static String getTime(){
		return "[" + LocalDateTime.now() + "] ";
	}
}
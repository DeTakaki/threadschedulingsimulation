import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Scheduler{

	ArrayList<Process> processes;
	ArrayList<Process> readyProcesses = new ArrayList<Process>();
	ArrayList<Process> finishedProcesses = new ArrayList<Process>();
	int interval, quantum;
	Boolean processExecuting = false;
	Boolean finishedProcessing = false;
	Process highestPrio;

	public Scheduler(int interval, int quantum){
		this.interval = interval;
		this.quantum = quantum;
	}

	public void setProcesses(ArrayList<Process> processes){
		this.processes = processes;
	}

	public synchronized void addToReadyList(Process p){
		this.readyProcesses.add(p);
		// System.out.println("Thread Adicionada\n");
		// this.execute(p);

	}



	public void execute(){

		// this.readyProcesses.add(p);
		// System.out.println("Tamanho " + readyProcesses.size());

		while(!finishedProcessing){

			synchronized(this){
				if(readyProcesses.size() > 0){
					this.highestPrio = readyProcesses.get(0);
				}
			}

			while(processExecuting);
			// {
			// 	System.out.print("preso ");
			// }
				// Conjunto de teste 
				// 4 300 100 500 0 50 1 52 2 54 3 57
			if(readyProcesses.size() > 0){
				if(readyProcesses.size() > 1){
					for(int i = 0; i <= readyProcesses.size() - 2 ; i++){
						Process p1 = readyProcesses.get(i);
						Process p2 = readyProcesses.get(i+1);
						highestPrio = (p1.getPrio() >= p2.getPrio()) ? p1:p2;
					}
				}
				highestPrio.setState(Process.STATUS_EXECUTING);
				processExecuting = true;
				System.out.println(SyncMain.getTime() + "A Thread " + highestPrio.getPId() + " está executando" + highestPrio.formatedMsg());
				if(highestPrio.getResta() <= quantum){
					try{Thread.sleep(highestPrio.getResta());}
					catch(InterruptedException e){}
					highestPrio.finishProcessing();	
				}
				else{
					try{Thread.sleep(quantum);}
					catch(InterruptedException e){}
					highestPrio.decreaseResta();
				}
				highestPrio.decreasePrio();
				if(highestPrio.getResta() <= 0){
					highestPrio.setState(Process.STATUS_FINISHED);
					highestPrio.finishProcessing();
					System.out.println(SyncMain.getTime() + "A Thread " + highestPrio.getPId() + " esgotou seu tempo total de processamento " + highestPrio.formatedMsg());
					readyProcesses.remove(highestPrio);
					finishedProcesses.add(highestPrio);
				}else{
					highestPrio.setState(Process.STATUS_READY);
					System.out.println(SyncMain.getTime() + "A Thread " + highestPrio.getPId() + " voltou a fila de prontos " + highestPrio.formatedMsg());
				}
				processExecuting = false;
			}
			if(finishedProcesses.size() == processes.size()){
				System.out.println(SyncMain.getTime() + "Término da simulação\n");
				finishedProcessing = true;
			}
		}
	}

}
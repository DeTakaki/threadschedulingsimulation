public class Process extends Thread{

	private String estado;
	private int resta, prio, pId, inter, quantum;
	private Scheduler scheduler;

	public static String STATUS_READY = "pronto";
	public static String STATUS_EXECUTING = "executando";
	public static String STATUS_FINISHED = "terminado";

	public Process(int pId, int inter, int prio, int resta, Scheduler scheduler, int quantum){
		this.prio = prio;
		this.inter = inter;
		this.pId = pId;
		this.resta = resta;
		this.scheduler = scheduler;
		this.quantum = quantum;
	}

	public void setState(String state){
		this.estado = state;
	}

	public void setResta(int resta){
		this.resta = resta;
	}

	public void setPrio(int prio){
		this.prio = prio;
	}

	public void decreaseResta(){
		this.resta -= quantum;
	}

	public void decreasePrio(){
		this.prio -= 1;
	}

	public int getPId(){
		return this.pId;
	}

	public int getResta(){
		return this.resta;
	}

	public int getPrio(){
		return this.prio;
	}

	public void finishProcessing(){
		this.resta = 0;
		this.prio = 0;
	}

	public String formatedMsg(){
		return " ["+ this.pId + ": " + this.estado + "," + this.prio + "," + this.resta + "] \n";
	}

	public void run(){
		System.out.println(SyncMain.getTime() + "Thread " + this.pId + " com prioridade " + this.prio + " foi criada!\n");
		try{Thread.sleep(this.pId*this.inter);}
		catch(InterruptedException e){}
		this.setState(Process.STATUS_READY);
		System.out.println(SyncMain.getTime() + "A Thread " + this.pId + " chegou inicialmente a fila de prontos" + formatedMsg());
		scheduler.addToReadyList(this);

	}

}
package AG;

import java.util.ArrayList;

public class Process{

	private String name;
	private int burstTime;
	private int arrivalTime;
	private int priority;
	private int quantum;
	private int completionTime;
	private int turnAroundTime;
	private int waitingTime;
	private int orignalBurstTime;
	ArrayList<Integer> updatedQuantum = new ArrayList(); 
	
	public Process(String name, int burstTime, int arrivalTime, int priority, int quantum){
		this.name = name;
		this.burstTime = burstTime;
		this.arrivalTime = arrivalTime;
		this.priority = priority;
		this.quantum = quantum;
		orignalBurstTime = burstTime;
		updatedQuantum.add(quantum);
	}
	
	//setters
	public void setName(String name){
		this.name = name;
	}
	
	public void setBurstTime(int d){
		this.burstTime = d;
	}
	
	public void setArrivalTime(int arrivalTime){
		this.arrivalTime = arrivalTime;
	}
	
	public void setPriority(int priority){
		this.priority = priority;
	}
	
	public void setQuantum(int quantum){
		this.quantum = quantum;
	}
	
	public void setCompletionTime(int completionTime){
		this.completionTime = completionTime;
		this.setTurnAroundTime();
		this.setwaitingTime();
	}
	public void setTurnAroundTime(){
		this.turnAroundTime = this.getCompletionTime() - this.getArrivalTime();
	}
	public void setwaitingTime(){
		this.waitingTime = this.getTurnAroundTime() - this.getOrignalBurstTime();
	}
	
	public void addToQuantumArray(int q){
		updatedQuantum.add(q);
	}
	
	//getters
	public String getName(){
		return name;
	}
	
	public int getArrivalTime(){
		return arrivalTime;
	}
	
	public int getBurstTime(){
		return burstTime;
	}
	
	public int getOrignalBurstTime(){
		return orignalBurstTime;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public int getQuantum(){
		return quantum;
	}
	public int getCompletionTime(){
		return completionTime;
	}
	public int getTurnAroundTime(){
		return turnAroundTime;
	}
	public int getWaitingTime(){
		return waitingTime;
	}
	
	public ArrayList<Integer> getUpdatedQuantum(){
		return updatedQuantum;
	}
	
	
	//
//	public void run(){
//		
//		int low = (int) Math.ceil(quantum/4);
//		int high = (int) Math.ceil(quantum/2);
//		this.setQuantum(quantum-=low);
//		
//	}

//	public int compareTo(Process compareProcess) {
//		// TODO Auto-generated method stub
//		int compareArrivalTime = ((Process) compareProcess).getArrivalTime(); 
//		
//		//ascending order
//		return this.arrivalTime - compareArrivalTime;
//		
//	}

}

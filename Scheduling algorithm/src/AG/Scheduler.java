package AG;

import java.awt.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Formatter;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Scheduler {
	int x=0;
	int start = 0;
	int end = 0;
	int cs = 0;
	

	ArrayList<Process> run = new ArrayList<Process>();
	Comparator<Process> burstTimeComparetor = new Comparator<Process>() {
        @Override
        public int compare(Process p1, Process p2) {
            return p1.getBurstTime() - p2.getBurstTime();
        }
	};
    Comparator<Process> priorityComparetor = new Comparator<Process>() {
        @Override
        public int compare(Process p1, Process p2) {
            return p1.getPriority() - p2.getPriority();
        }
    };
    
    Comparator<Process> arrivalTimeComparetor = new Comparator<Process>() {
        @Override
        public int compare(Process p1, Process p2) {
            return p1.getArrivalTime() - p2.getArrivalTime();
        }
	};
	PriorityQueue<Process> waitProcesses = new PriorityQueue<>(arrivalTimeComparetor);
	PriorityQueue<Process> readyProcesses = new PriorityQueue<>(arrivalTimeComparetor);
	PriorityQueue<Process> priorityBurstTime = new PriorityQueue<>(burstTimeComparetor);
    PriorityQueue<Process> priorityQueue = new PriorityQueue<>(priorityComparetor);
	
    
    Scheduler(PriorityQueue<Process> waitProcesses, int cs){
		this.waitProcesses = waitProcesses;
		this.cs = cs;
	}
    
	public void Run(Process p){
	
		start = end;
		
		run.add(p);
		double low =  Math.ceil(p.getQuantum()/4.0);   
		double miniLow = Math.min(low, p.getBurstTime()); 
		x += miniLow;                                        
		int high = (int) Math.ceil(p.getQuantum()/2.0);
		int q = (int) (p.getQuantum()-miniLow);                	
		
		p.setBurstTime((int) (p.getBurstTime() - miniLow));     

		if(p.getBurstTime() != 0){
			priorityBurstTime.add(p);
			priorityQueue.add(p);
		}
		else{
			p.setQuantum(0);
			setProcessAtributes(p);
			priorityBurstTime.remove(p);
			priorityQueue.remove(p);
			readyProcesses.remove(p);
			
			Process p4 = readyProcesses.poll();

			printCurrentRunP(p);
			if(p4 != null){
				
				priorityBurstTime.remove(p4);
				priorityQueue.remove(p4);
				System.out.println("System time: "+x+" switching from process "+p.getName()+" to process "+p4.getName());
				
				Run(p4);
			}
		}
		
		if(waitProcesses.size()>0){
			arrivalProcess(x);
		}
		if (priorityQueue.isEmpty()) {

            return;
        }
		
		Process p2 = priorityQueue.peek();

		if(p2.getName().equals(p.getName())){
			priorityQueue.poll();
			priorityBurstTime.remove(p);
			p.setBurstTime((int) (p.getBurstTime()+miniLow-high)); 
			
			x-=miniLow;
			x+=high;                                              

			priorityBurstTime.add(p);
			priorityQueue.add(p);
			Process p3 = priorityBurstTime.peek();
			
			if((p3.getBurstTime() == p.getBurstTime() && !(p3.getName().equals(p.getName()))) || p3.getName() == p.getName()){
				x+=p.getBurstTime();
				p.setBurstTime(0);
				p.setQuantum(0);

				setProcessAtributes(p);
				priorityBurstTime.remove(p);
				priorityQueue.remove(p);
				readyProcesses.remove(p);
				Process p4 = readyProcesses.poll();
		
				priorityBurstTime.remove(p4);
				priorityQueue.remove(p4);

				printCurrentRunP(p);
				System.out.println("System time: "+x+" switching from process "+p.getName()+" to process "+p4.getName());
				
				Run(p4);
			}
			else{

				//setProcessAtributes(p);
				p.setQuantum(p.getQuantum()+(p.getQuantum()-high));
				readyProcesses.remove(p3);
				readyProcesses.add(p);
				priorityQueue.remove(p3);
				setProcessAtributes(p);

				printCurrentRunP(p);
				System.out.println("System time: "+x+" switching from process "+p.getName()+" to process "+p3.getName());
				
				Run(priorityBurstTime.poll());
			}
			}
		
		
		else{
			
			priorityQueue.remove(p);
			priorityBurstTime.remove(p);
			p.setQuantum((int) (p.getQuantum()+Math.ceil(q/2.0)));
			setProcessAtributes(p);
			priorityBurstTime.add(p);
			priorityQueue.add(p);
			readyProcesses.remove(p2);
			readyProcesses.add(p);
			priorityBurstTime.remove(p2);
			printCurrentRunP(p);
			System.out.println("System time: "+x+" switching from process "+p.getName()+" to process "+p2.getName());
			Run(priorityQueue.poll());
		}
		
		
	}
		
		
		
	
	private void arrivalProcess(int low) {
		for(int i=0; i<waitProcesses.size(); i++){
			
			Process p = waitProcesses.peek();
			
			if(p.getArrivalTime() <= low){

				readyProcesses.add(p);    
				
				priorityBurstTime.add(p);   
				priorityQueue.add(p);   
				waitProcesses.poll();
			}
		}
	}
	
	public void setProcessAtributes(Process p){
		x+=cs;
		p.setCompletionTime(x-cs);
		p.addToQuantumArray(p.getQuantum());
	}
	
	private void printCurrentRunP(Process p){
		end = x;
		for(int i=start; i<=end-cs; i++){
			System.out.println("System time: "+i+" process "+p.getName()+" is running.");
			System.out.println("-------------------------------------------------------------");
		}
	}
	
	public void print(){
		double totalWaitingTime = 0;
		double totalTturnAround = 0;
		java.util.List<Object> newList = run.stream()
                .distinct()
                .collect(Collectors.toList());
		Formatter fmt = new Formatter();

        fmt.format("%11s %11s %19s %13s %20s %20s %20s  %20s\n", "Process Name", "Bursts", "Arrival Time", "Priority", "Completion Time", "Waiting Time", "TurnAround Time", "updated quantum");
        
       
        
		for(int i=0; i<newList.size(); i++){
			
			
			Process process = (Process) newList.get(i);
			fmt.format("%6s %14s %16s %15s %16s %21s %18s %30s\n", process.getName(), process.getOrignalBurstTime(), process.getArrivalTime(), process.getPriority(),
			process.getCompletionTime(), process.getWaitingTime(), process.getTurnAroundTime(), process.getUpdatedQuantum());
			totalWaitingTime += process.getWaitingTime();
			totalTturnAround += process.getTurnAroundTime();
			}
	System.out.println(fmt);
	
	System.out.println("Average waiting time: "+totalWaitingTime / newList.size());
	System.out.println("Average turn around time: "+totalTturnAround / newList.size());
	}

}





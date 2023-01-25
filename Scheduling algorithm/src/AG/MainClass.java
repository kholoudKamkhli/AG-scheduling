package AG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Comparator;

public class MainClass {
	public static void main(String[] args){
		
		
		Comparator<Process> arrivalTimeComparetor = new Comparator<Process>() {
	        @Override
	        public int compare(Process p1, Process p2) {
	            return p1.getArrivalTime() - p2.getArrivalTime();
	        }
		};
		PriorityQueue<Process> waitProcesses = new PriorityQueue<>(arrivalTimeComparetor);
		
		Scanner input = new Scanner(System.in);
		System.out.print("enter number of process: ");
		int processNum = input.nextInt();
		String name;
		int burstTime, arrivalTime, quantum, priority;
		
		System.out.println("process name, burst time, arrival time, priority, quantum ");
		for(int i=0; i< processNum; i++){
			name = input.next();
			burstTime = input.nextInt();
			arrivalTime = input.nextInt();
			priority = input.nextInt();
			quantum = input.nextInt();
			waitProcesses.add(new Process(name, burstTime, arrivalTime, priority, quantum));
		}
		System.out.print("Enter context switching time: ");
		int cs = input.nextInt();
		
		Scheduler s = new Scheduler(waitProcesses, cs);
		if(!(waitProcesses.isEmpty())){
			s.Run(waitProcesses.poll());}
		s.print();	
	}
		
		
		
}
		
		
		

	

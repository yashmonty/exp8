import java.util.LinkedList;
import java.util.Queue;

class Process {
    int id;         // Process ID
    int burstTime;  // Burst time
    int remainingTime; // Remaining time to execute
    int waitingTime;   // Waiting time
    int turnaroundTime; // Turnaround time

    Process(int id, int burstTime) {
        this.id = id;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
    }
}

public class Exp8 {
    public static void main(String[] args) {
        // Hardcoded input: Process burst times
        int[] burstTimes = {10, 5, 8}; // Burst times for processes 1, 2, and 3
        int quantum = 2; // Time slice (quantum)

        // Initialize processes
        Queue<Process> processQueue = new LinkedList<>();
        for (int i = 0; i < burstTimes.length; i++) {
            processQueue.add(new Process(i + 1, burstTimes[i]));
        }

        // Variables to track time and total calculations
        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        // Round Robin execution
        while (!processQueue.isEmpty()) {
            Process currentProcess = processQueue.poll();

            // Process executes for the quantum time or its remaining time, whichever is smaller
            int executionTime = Math.min(currentProcess.remainingTime, quantum);
            currentTime += executionTime;
            currentProcess.remainingTime -= executionTime;

            // If the process still has remaining burst time, re-add it to the queue
            if (currentProcess.remainingTime > 0) {
                processQueue.add(currentProcess);
            } else {
                // Process completes
                currentProcess.turnaroundTime = currentTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;

                // Add to total times
                totalWaitingTime += currentProcess.waitingTime;
                totalTurnaroundTime += currentProcess.turnaroundTime;

                // Display completion details
                System.out.println("Process " + currentProcess.id + " completed at time " + currentTime);
            }
        }

        // Display process summary
        System.out.println("\nProcess ID\tBurst Time\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < burstTimes.length; i++) {
            int waitingTime = totalWaitingTime - totalTurnaroundTime + burstTimes[i];
            System.out.println((i + 1) + "\t\t" + burstTimes[i] + "\t\t" + waitingTime + "\t\t" + (waitingTime + burstTimes[i]));
        }

        // Display average waiting and turnaround times
        System.out.println("\nAverage Waiting Time: " + (double) totalWaitingTime / burstTimes.length);
        System.out.println("Average Turnaround Time: " + (double) totalTurnaroundTime / burstTimes.length);
    }
}

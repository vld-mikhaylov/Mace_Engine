package GUI.GUI_Objects;

import GUI.GUI_Objects.MatrixObject.MatrixObject;

public class ThreadManager {
    /** An array of boolean variables that are responsible for performing of any instance of the Object class in thread.*/
    public volatile static boolean isThreadAlive[];

    /** An array of all instances of the Object class that will be executed in parallel.*/
    private Object[] objectInstance;
    /** An array of all instances of the Thread class that are executed in parallel and contain different instances of the Object class.*/
    private Thread[] threadInstance;

    public ThreadManager() {
        objectInstance = new Object[1];
        threadInstance = new Thread[1];
        isThreadAlive = new boolean[1];
    }
    public void init() {
        // 0 - MatrixObject
        objectInstance[0] = new MatrixObject();

        // Create threads with all GUI Objects.
        threadInstance[0] = new Thread(objectInstance[0], "MatrixObject Thread");
    }

    /** Starts all idle threads with isThreadAlive status enabled.*/
    public void run() {
        for (int i = 0; i < isThreadAlive.length; i++) {
            if (isThreadAlive[i] && !threadInstance[0].isAlive()) {
                threadInstance[i].start();
            }
        }
    }

    /** Changes the priority of a specific thread.*/
    public void setThreadPriority(int thread, int priority) {
        threadInstance[thread].setPriority(priority);
    }
    /** Changes the execution status of a specific thread.*/
    public void setThreadStatus(int thread, int status) {
        if (status == 0) {
            isThreadAlive[thread] = false;
        } else if (status == 1) {
            isThreadAlive[thread] = true;
        }
    }
    /** TTerminates all running threads and then terminates the main thread.*/
    public void end() {
        for (int i = 0; i < threadInstance.length; i++) {
            if (threadInstance[i].isAlive() && isThreadAlive[i]) {
                try {
                    isThreadAlive[i] = false;
                    threadInstance[i].join();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}

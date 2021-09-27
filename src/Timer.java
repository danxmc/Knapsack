public class Timer {
    long startTime = System.nanoTime();
    long endTime = System.nanoTime();
    long totalTime = endTime - startTime;

    public Timer() {
    }

    public void start() {
        this.startTime = System.nanoTime();
    }

    public void end() {
        this.endTime = System.nanoTime();
        this.totalTime = endTime - startTime;
    }

    public long getTotalTime() {
        return totalTime;
    }
}

package Util;

public class TimeTool {
    private float timerStartTime, timerEndTime;
    private float secondmeterStartTime, secondmeterEndTime;

    public void setTimer() {
        timerStartTime = Time.getTime();
    }
    public boolean getTimer(float timeDifference) {
        timerEndTime = Time.getTime();
        return timerEndTime - timerStartTime >= timeDifference;
    }

    public void setSecondmeter() {
        secondmeterStartTime = Time.getTime();
    }
    public float getSecondmeter() {
        secondmeterEndTime = Time.getTime();
        return secondmeterEndTime - secondmeterStartTime;
    }
}

class Time {
    public static float timeStarted = System.nanoTime();

    public static float getTime() {
        return (float) ((System.nanoTime() - timeStarted) * 1E-9);
    }
}
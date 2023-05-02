package Fitur;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class Time {

    public static void main(String[] args) {
        int timeLimit = 720; // set the time limit in seconds (12 minutes)
        int secondsPassed = 0;

        while (true) {
            System.out.println("Seconds passed: " + secondsPassed);

            if (secondsPassed >= timeLimit) {
                secondsPassed = 0;
                System.out.println("Clock counter has been reset.");
            }

            try {
                Thread.sleep(1000); // pause for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            secondsPassed++;
        }
    }
}

 

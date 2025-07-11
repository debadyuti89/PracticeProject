package Strings;

import java.util.Random;
import java.awt.Robot;

public class SimulateMouseMoveAndKeyPress {

	public static final int FIVE_SECONDS = 5000;
    public static final int MAX_Y = 400;
    public static final int MAX_X = 400;

    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();
        Random random = new Random();
        while (true) {
            robot.mouseMove(random.nextInt(MAX_X), random.nextInt(MAX_Y));
            Thread.sleep(FIVE_SECONDS);
        }
    }

}

package team2robot;
import battlecode.common.*;

public class Refinery extends RobotPlayer{
    static void runRefinery() throws GameActionException {
        for(Direction dir : directions) {
            if(tryRefine(dir)) {
                System.out.println("I refined soup"+rc.getSoupCarrying());
            }
            System.out.println("Pollution: " + rc.sensePollution(rc.getLocation()));
        }
    }
}

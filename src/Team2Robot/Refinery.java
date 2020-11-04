package Team2Robot;
import battlecode.common.*;

public class Refinery extends RobotPlayer{
    static void runRefinery() throws GameActionException {
        for(Direction dir : directions) {
            if(tryRefine(dir)) {
                System.out.println("I refines soup"+rc.getSoupCarrying());
            }
            System.out.println("Pollution: " + rc.sensePollution(rc.getLocation()));
        }
    }
}

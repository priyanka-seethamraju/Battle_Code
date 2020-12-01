package team2robot;
import battlecode.common.*;

public class Refinery extends Robot{

    public Refinery(RobotController r){
        super(r);
    }

    public void takeTurn() throws GameActionException {
        runRefinery();
    }

    static void runRefinery() throws GameActionException {
        for(Direction dir : directions) {
            if(tryRefine(dir)) {
                System.out.println("I refined soup"+rc.getSoupCarrying());
            }
            System.out.println("Pollution: " + rc.sensePollution(rc.getLocation()));
        }
    }
}

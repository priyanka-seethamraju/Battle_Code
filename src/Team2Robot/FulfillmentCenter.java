package team2robot;
import battlecode.common.*;

public class FulfillmentCenter extends Robot{

    public FulfillmentCenter(RobotController r){
        super(r);
    }

    public void takeTurn() throws GameActionException {
        runFulfillmentCenter();
    }

    static void runFulfillmentCenter() throws GameActionException {

        if(buildDrone())
            System.out.println("I built a drone");

        /*
        for (Direction dir : directions)
            // Only build delivery bots if we have enough soup. If we have 500+ soup than we should have already built all our other bots
            if(droneCount < 2){
                if(tryBuild(RobotType.DELIVERY_DRONE, dir))
                    droneCount ++;
            }*/

    }

    static boolean buildDrone() throws GameActionException{
        for (Direction dir : directions) {
            if (droneCount < 4) {
                if (tryBuild(RobotType.DELIVERY_DRONE, dir)) {
                    droneCount++;
                    return true;
                }
            }
        }
        return false;
    }
}
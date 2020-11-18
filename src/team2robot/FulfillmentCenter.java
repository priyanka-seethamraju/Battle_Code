package team2robot;
import battlecode.common.*;

public class FulfillmentCenter extends RobotPlayer{
    static void runFulfillmentCenter() throws GameActionException {
        for (Direction dir : directions)
            // Only build delivery bots if we have enough soup. If we have 500+ soup than we should have already built all our other bots
            if(droneCount < 2){
                if(tryBuild(RobotType.DELIVERY_DRONE, dir))
                    droneCount ++;
            }
    }
}
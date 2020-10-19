package Team2Robot;
import battlecode.common.*;

public class FulfillmentCenter extends RobotPlayer{
    static void runFulfillmentCenter() throws GameActionException {
        for (Direction dir : directions)
            tryBuild(RobotType.DELIVERY_DRONE, dir);
    }
}
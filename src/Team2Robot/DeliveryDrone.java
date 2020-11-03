package Team2Robot;
import battlecode.common.*;

public class DeliveryDrone extends RobotPlayer{
    static void runDeliveryDrone() throws GameActionException {
        Team enemy = rc.getTeam().opponent();
        if (!rc.isCurrentlyHoldingUnit()) {
            // See if there are any enemy robots within capturing range
            RobotInfo[] robots = rc.senseNearbyRobots(GameConstants.DELIVERY_DRONE_PICKUP_RADIUS_SQUARED, enemy);

            if (robots.length > 0) {
                // Pick up a first robot within range
                rc.pickUpUnit(robots[0].getID());
                System.out.println("I picked up " + robots[0].getID() + "!");
                for (Direction dir : directions) {
                    if (tryMove(dir)) {
                        flooding = rc.senseFlooding(rc.getLocation());
                        if (flooding && rc.canDropUnit(dir)) {

                                rc.dropUnit(dir);
                                System.out.println("I destroyed enemy robot");

                        }
                    }
                }
            } else {
                // No close robots, so search for robots within sight radius
                tryMove(randomDirection());

            }
        }
    }
}
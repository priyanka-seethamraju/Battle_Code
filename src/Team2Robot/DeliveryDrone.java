package Team2Robot;
import battlecode.common.*;

public class DeliveryDrone extends RobotPlayer{
    static void runDeliveryDrone() throws GameActionException {
        Team enemy = rc.getTeam().opponent();
        //System.out.println("enemy team is :" + enemy);
        Team ourTeam = rc.getTeam();
        //System.out.println("Our team is :" + ourTeam);
        //System.out.println("delievry drone is currently holding unit" + rc.isCurrentlyHoldingUnit());
        //Pick up robots if the delivery drone is not currently holding units
        if (!rc.isCurrentlyHoldingUnit() && rc.isReady()) {
            // See if there are any enemy robots within capturing range
            RobotInfo[] robots = rc.senseNearbyRobots(GameConstants.DELIVERY_DRONE_PICKUP_RADIUS_SQUARED);
            //Drop enemy robots onto flooded tiles
            System.out.println("robots length:" + robots.length);
            if (robots.length > 0) {
                // Pick up a first robot within range
                for (int i = 0; i < robots.length; i++) {
                    if (robots[i].getTeam() == enemy && robots[i].getType() != RobotType.HQ && robots[i].getType() != RobotType.DESIGN_SCHOOL && robots[i].getType() != RobotType.FULFILLMENT_CENTER && robots[i].getType() != RobotType.REFINERY && robots[i].getType() != RobotType.DELIVERY_DRONE) {
                        if(rc.canPickUpUnit(robots[i].getID())) {
                            rc.pickUpUnit(robots[i].getID());
                            System.out.println("I picked up " + robots[i].getID() + "!");
                        }
                        else{
                            MapLocation enemyBot = robots[i].getLocation();
                            for (Direction dir : directions) {
                                Direction move = rc.getLocation().directionTo(enemyBot);
                                if (rc.canMove(move) && tryMove(move))
                                    System.out.println("I moved!");
                                else {
                                    Direction left = move.rotateLeft();
                                    Direction right = move.rotateRight();
                                    if (rc.canMove(left) && tryMove(left))
                                        System.out.println("I moved!");
                                    else if (rc.canMove(right) && tryMove(right))
                                        System.out.println("I moved!");
                                }
                            }
                        }
                    }
                    else
                        break;
                }
            }
            for (Direction dir : directions) {
                if (rc.canMove(dir) && tryMove(dir))
                    System.out.println("I moved...");
            }
        }
        // head towards flooded area to drop bots
        else if(rc.isCurrentlyHoldingUnit() && rc.isReady()){
            for(Direction dir : directions) {
                flooding = rc.senseFlooding(rc.getLocation());
                if (flooding && rc.canDropUnit(dir)) {
                    rc.dropUnit(dir);
                    System.out.println("I destroyed enemy robot");
                }
            }
            for (Direction dir : directions) {
                tryMove(randomDirection());
            }
        }
    }
}
package Team2Robot;
import battlecode.common.*;

public class DeliveryDrone extends RobotPlayer{
    static void runDeliveryDrone() throws GameActionException {
        Team enemy = rc.getTeam().opponent();
        System.out.println("enemy team is :" + enemy);
        Team ourTeam = rc.getTeam();
        System.out.println("Our team is :" + ourTeam);
        System.out.println("delievry drone is currently holding unit" + rc.isCurrentlyHoldingUnit());
        //Pick up robots if the delivery drone is not currently holding units
        if (!rc.isCurrentlyHoldingUnit()) {
            // See if there are any enemy robots within capturing range
            RobotInfo[] robots = rc.senseNearbyRobots(GameConstants.DELIVERY_DRONE_PICKUP_RADIUS_SQUARED);
            //Drop enemy robots onto flooded tiles
            if (robots.length > 0) {
                // Pick up a first robot within range
                for (RobotInfo r : robots) {
                    if (r.getTeam() == enemy) {
                        if(rc.canPickUpUnit(robots[0].getID())) {
                            System.out.println("I picked up " + robots[0].getID() + "!");
                        }
                        for (Direction dir : directions) {
                            if (tryMove(dir)) {
                                flooding = rc.senseFlooding(rc.getLocation());
                                if (flooding && rc.canDropUnit(dir)) {


                                    System.out.println("I destroyed enemy robot");

                                }
                            }
                        }
                    } else {
                        if(rc.canPickUpUnit(robots[0].getID())) {
                            System.out.println("I picked up " + robots[0].getID() + "!");
                        }
                        for (Direction dir : directions) {
                            if (tryMove(dir)) {
                                flooding = rc.senseFlooding(rc.getLocation());
                                if (!flooding && rc.canDropUnit(dir)) {

                                        System.out.println("I saved our robots from flooding");

                            }
                        }
                    }
                }
            }
        }
        else {
            for (Direction dir : directions) {
                tryMove(dir);
            }
        }
    }
    }}
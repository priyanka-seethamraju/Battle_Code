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

        int num = rc.getRoundNum();
        Transaction[] transaction = rc.getBlock(num -1);
        for(Transaction t : transaction) {
            int[] message = t.getMessage();
            if(message[0] == 6){
                enemyHQloc = new MapLocation(message[1], message[2]);
                knowEnemyHQ = true;
                System.out.println("Enemy HQ Location: " + message[1] + ", " + message[2]);
            }
            if(message[0] == 7){
                floodLoc = new MapLocation(message[1], message[2]);
                knowFlood = true;
            }
        }

        if(!knowEnemyHQ) {
            RobotInfo[] r = rc.senseNearbyRobots();
            for (int i = 0; i < r.length; i++) {
                if (r[i].team == enemy) {
                    if (r[i].type == RobotType.HQ) {
                        int[] message = new int[7];
                        message[0] = 6;
                        message[1] = r[i].location.x;
                        message[2] = r[i].location.y;
                        message[3] = 0;
                        message[4] = 0;
                        message[5] = 0;
                        message[6] = 0;

                        rc.submitTransaction(message, 1);
                    }
                }
            }
        }

        // focus on cows
        if(num < 450){
            if (!rc.isCurrentlyHoldingUnit() && rc.isReady()) {
                RobotInfo[] cows = rc.senseNearbyRobots();
                if (cows.length > 0) {
                    for (int i = 0; i < cows.length; i++) {
                        if (cows[i].type == RobotType.COW) {
                            if (rc.canPickUpUnit(cows[i].getID())) {
                                rc.pickUpUnit(cows[i].getID());
                                System.out.println("I picked up " + cows[i].getID() + "!");
                            } else {
                                MapLocation cowloc = cows[i].getLocation();
                                for (Direction dir : directions) {
                                    Direction move = rc.getLocation().directionTo(cowloc);
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
                        } else
                            break;
                    }
                }
                for (Direction dir : directions) {
                    if (tryMove(randomDirection()))
                        System.out.println("I moved...");
                }
            }
            else if (rc.isCurrentlyHoldingUnit() && rc.isReady()) {
                for (Direction dir : directions) {
                    if (rc.getLocation().isAdjacentTo(enemyHQloc) && rc.canDropUnit(dir)) {
                        rc.dropUnit(dir.opposite());
                    }
                }
                if (knowEnemyHQ) {
                    for (Direction dir : directions) {
                        Direction move = rc.getLocation().directionTo(enemyHQloc);
                        if (rc.canMove(move) && tryMove(move))
                            System.out.println("I moved!");
                    }
                } else {
                    for (Direction dir : directions) {
                        if (rc.canMove(dir) && tryMove(dir))
                            System.out.println("I moved...");
                    }
                }
            }
        }

        // only attack enemy bots after round 300. This round number can be changed
        else {
            if (!rc.isCurrentlyHoldingUnit() && rc.isReady()) {
                // See if there are any enemy robots within capturing range
                RobotInfo[] robots = rc.senseNearbyRobots();
                //Drop enemy robots onto flooded tiles
                //System.out.println("robots length:" + robots.length);
                if (robots.length > 0) {
                    // Pick up a first robot within range
                    for (int i = 0; i < robots.length; i++) {
                        if (robots[i].getTeam() == enemy && robots[i].getType() != RobotType.HQ && robots[i].getType() != RobotType.DESIGN_SCHOOL && robots[i].getType() != RobotType.FULFILLMENT_CENTER && robots[i].getType() != RobotType.REFINERY && robots[i].getType() != RobotType.DELIVERY_DRONE) {
                            if (rc.canPickUpUnit(robots[i].getID())) {
                                rc.pickUpUnit(robots[i].getID());
                                System.out.println("I picked up " + robots[i].getID() + "!");
                            } else {
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
                        } else
                            break;
                    }
                }
                if (knowEnemyHQ) {
                    for (Direction dir : directions) {
                        Direction move = rc.getLocation().directionTo(enemyHQloc);
                        if (rc.canMove(move) && tryMove(move))
                            System.out.println("I moved!");
                    }
                } else {
                    for (Direction dir : directions) {
                        if (rc.canMove(dir) && tryMove(dir))
                            System.out.println("I moved...");
                    }
                }
            }
            // head towards flooded area to drop bots
            else if (rc.isCurrentlyHoldingUnit() && rc.isReady()) {
                for (Direction dir : directions) {
                    flooding = rc.senseFlooding(rc.getLocation());
                    if (flooding && rc.canDropUnit(dir)) {
                        if (!knowFlood) {
                            int[] message = new int[7];
                            message[0] = 7;
                            message[1] = rc.getLocation().x;
                            message[2] = rc.getLocation().y;
                            message[3] = 0;
                            message[4] = 0;
                            message[5] = 0;
                            message[6] = 0;

                            rc.submitTransaction(message, 1);
                        }

                        rc.dropUnit(dir.opposite());
                        System.out.println("I destroyed enemy robot");
                    }
                }
                if (knowFlood) {
                    for (Direction dir : directions) {
                        Direction move = rc.getLocation().directionTo(floodLoc);
                        if (rc.canMove(move) && tryMove(move))
                            System.out.println("I moved!");
                    }
                } else {
                    for (Direction dir : directions) {
                        tryMove(randomDirection());
                    }
                }
            }
        }
    }
}
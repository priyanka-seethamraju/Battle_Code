package Team2Robot;
import battlecode.common.*;

import java.util.Map;

public class DeliveryDrone extends RobotPlayer{
    static void runDeliveryDrone() throws GameActionException {
        Team enemy = rc.getTeam().opponent();
        //System.out.println("enemy team is :" + enemy);
        Team ourTeam = rc.getTeam();
        //System.out.println("Our team is :" + ourTeam);
        //System.out.println("delivery drone is currently holding unit" + rc.isCurrentlyHoldingUnit());
        //Pick up robots if the delivery drone is not currently holding units
        int height = rc.getMapHeight();
        int width = rc.getMapWidth();
        //System.out.println("Turn count: " + droneTurn);
        //System.out.println("My job is: " + job);
        //System.out.println("My last job was: " + lastJob);

        if (!knowFlood) {
            flooding = rc.senseFlooding(rc.getLocation());
            for (Direction dir : directions) {
                if (flooding) {
                    int[] message = new int[7];
                    message[0] = 7;
                    message[1] = rc.getLocation().x;
                    message[2] = rc.getLocation().y;
                    message[3] = 0;
                    message[4] = 0;
                    message[5] = 0;
                    message[6] = 0;

                    floodLoc = new MapLocation(message[1], message[2]);
                    knowFlood = true;

                    rc.submitTransaction(message, 1);
                }
            }
        }

        // check messages for flooded, enemy hq and hq locations
        if(!knowEnemyHQ && !knowFlood && !knowHQ) {
            int num = rc.getRoundNum();
            for (int i = 1; i < num; i++) {
                Transaction[] transaction = rc.getBlock(num - i);
                for (Transaction t : transaction) {
                    int[] message = t.getMessage();
                    if (message[0] == 1) {
                        HQloc = new MapLocation(message[1], message[2]);
                        knowHQ = true;
                        //System.out.println("HQ Location: " + message[1] + ", " + message[2]);
                    }
                    // get enemy base location
                    if (message[0] == 6) {
                        enemyHQloc = new MapLocation(message[1], message[2]);
                        knowEnemyHQ = true;
                        //System.out.println("Enemy HQ Location: " + message[1] + ", " + message[2]);
                    }
                    // get flooded location
                    if (message[0] == 7) {
                        floodLoc = new MapLocation(message[1], message[2]);
                        knowFlood = true;
                    }
                }
            }
        }

        // set job to finding enemyHQ
        if(!knowEnemyHQ){
            job = "findHQ";
        }

        // set job to picking up cows or enemy
        else if(knowEnemyHQ && !rc.isCurrentlyHoldingUnit()) {
            if(lastJob == "dropping enemy"){
                // change jobs if drone spends too much time looking for bot
                if(droneTurn >= 100){
                    droneTurn = 0;
                    job = "pick up enemy";
                    lastJob = "dropping cows";
                }
                else
                    job = "pick up cows";
            }
            else {
                // change jobs if drone spends too much time looking for bot
                if(droneTurn >= 100){
                    droneTurn = 0;
                    job = "pick up cows";
                    lastJob = "dropping enemy";
                }
                else
                    job = "pick up enemy";
            }
        }


        // set job to dropping cows or set job to dropping enemies
        else if(knowEnemyHQ && rc.isCurrentlyHoldingUnit()){
            if(lastJob == "pick up cows"){
                job = "dropping cows";
            }
            else {
                job = "dropping enemy";
            }
        }

        // search for enemy hq
        if(job == "findHQ") {
            if(search == "0") {
                MapLocation tryHQ = new MapLocation(HQloc.x , height - HQloc.y - 1);
                if((tryHQ.x >= 0 && tryHQ.x < width) && (tryHQ.y >= 0 && tryHQ.y < height)) {
                    if (rc.getLocation().isAdjacentTo(tryHQ)) {
                        RobotInfo[] r = rc.senseNearbyRobots();
                        for (int i = 0; i < r.length; i++) {
                            if (r[i].team == enemy) {
                                if (r[i].type == RobotType.HQ) {
                                    // if found enemy hq send message
                                    int[] message = new int[7];
                                    message[0] = 6;
                                    message[1] = r[i].location.x;
                                    message[2] = r[i].location.y;
                                    message[3] = 0;
                                    message[4] = 0;
                                    message[5] = 0;
                                    message[6] = 0;

                                    search = "done";
                                    knowEnemyHQ = true;
                                    enemyHQloc = new MapLocation(r[i].location.x, r[i].location.y);

                                    rc.submitTransaction(message, 1);
                                }
                            }
                        }
                        if(r.length == 0){
                            search = "1";
                        }
                    } else {
                        for (Direction dir : directions) {
                            Direction move = rc.getLocation().directionTo(tryHQ);
                            if (tryMove(move))
                                System.out.println("I moved!");
                            else {
                                Direction left = move.rotateLeft();
                                Direction right = move.rotateRight();
                                if (tryMove(left))
                                    System.out.println("I moved!");
                                else if (tryMove(right))
                                    System.out.println("I moved!");
                            }
                        }
                    }
                }
                else
                    search = "1";
            }
            else if(search == "1") {
                MapLocation tryHQ = new MapLocation(width - HQloc.x - 1, height - HQloc.y - 1);
                if((tryHQ.x >= 0 && tryHQ.x < width) && (tryHQ.y >= 0 && tryHQ.y < height)) {
                    if (rc.getLocation().isAdjacentTo(tryHQ)) {
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

                                    search = "done";
                                    knowEnemyHQ = true;
                                    enemyHQloc = new MapLocation(r[i].location.x, r[i].location.y);

                                    rc.submitTransaction(message, 1);
                                }
                            }
                        }
                        if(r.length == 0){
                            search = "2";
                        }
                    } else {
                        for (Direction dir : directions) {
                            Direction move = rc.getLocation().directionTo(tryHQ);
                            if (tryMove(move))
                                System.out.println("I moved!");
                            else {
                                Direction left = move.rotateLeft();
                                Direction right = move.rotateRight();
                                if (tryMove(left))
                                    System.out.println("I moved!");
                                else if (tryMove(right))
                                    System.out.println("I moved!");
                            }
                        }
                    }
                }
                else
                    search = "2";
            }
            else if(search == "2") {
                MapLocation tryHQ = new MapLocation(width - HQloc.x - 1, HQloc.y);
                if((tryHQ.x >= 0 && tryHQ.x < width) && (tryHQ.y >= 0 && tryHQ.y < height)) {
                    if (rc.getLocation().isAdjacentTo(tryHQ)) {
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

                                    search = "done";
                                    knowEnemyHQ = true;
                                    enemyHQloc = new MapLocation(r[i].location.x, r[i].location.y);

                                    rc.submitTransaction(message, 1);
                                }
                            }
                        }
                        if(r.length == 0){
                            search = "0";
                        }
                    } else {
                        for (Direction dir : directions) {
                            Direction move = rc.getLocation().directionTo(tryHQ);
                            if (tryMove(move))
                                System.out.println("I moved!");
                            else {
                                Direction left = move.rotateLeft();
                                Direction right = move.rotateRight();
                                if (tryMove(left))
                                    System.out.println("I moved!");
                                else if (tryMove(right))
                                    System.out.println("I moved!");
                            }
                        }
                    }
                }
                else
                    search = "0";
            }
        }

        // head to hq to find and pick up cows
        else if(job == "pick up cows"){
            droneTurn++;
            if(rc.getLocation().distanceSquaredTo(HQloc) <= 40) {
                RobotInfo[] cows = rc.senseNearbyRobots(24);
                if (cows.length > 0) {
                    for (int i = 0; i < cows.length; i++) {
                        if (cows[i].type == RobotType.COW) {
                            droneTurn = 50; // if found cows give drone more time to focus on job
                            if (rc.canPickUpUnit(cows[i].getID())) {
                                rc.pickUpUnit(cows[i].getID());
                                droneTurn = 0;
                                lastJob = "pick up cows";
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
                        }
                        else {
                            for (Direction dir : directions) {
                                if (tryMove(randomDirection()))
                                    System.out.println("I moved...");
                            }
                        }
                    }
                }
                else {
                    for (Direction dir : directions) {
                        if (tryMove(randomDirection()))
                            System.out.println("I moved...");
                    }
                }
            }
            else{
                Direction move = rc.getLocation().directionTo(HQloc);
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

        // head to enemy hq to pick up cows
        else if(job == "pick up enemy") {
            droneTurn++;
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
                            droneTurn = 0;
                            lastJob = "pick up enemy";
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
                    }
                    else {
                        for (Direction dir : directions) {
                            Direction move = rc.getLocation().directionTo(enemyHQloc);
                            if (rc.canMove(move) && tryMove(move))
                                System.out.println("I moved!");
                        }
                    }
                }
            }
            else {
                for (Direction dir : directions) {
                    Direction move = rc.getLocation().directionTo(enemyHQloc);
                    if (rc.canMove(move) && tryMove(move))
                        System.out.println("I moved!");
                }
            }
        }

        // drop cows on enemy hw
        else if(job == "dropping cows"){
            for (Direction dir : directions) {
                if (rc.getLocation().isAdjacentTo(enemyHQloc) && rc.canDropUnit(dir)) {
                    rc.dropUnit(dir.opposite());
                    lastJob = "dropping cows";
                    droneTurn = 0;
                }
            }
            if (knowEnemyHQ) {
                for (Direction dir : directions) {
                    Direction move = rc.getLocation().directionTo(enemyHQloc);
                    if (rc.canMove(move) && tryMove(move))
                        System.out.println("I moved!");
                }
            }
        }

        // drop enemies in flooded tiles
        else{
            System.out.println("I am trying to drown you!!!!!!");
            flooding = rc.senseFlooding(rc.getLocation());
            for (Direction dir : directions) {
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

                        floodLoc = new MapLocation(message[1], message[2]);
                        knowFlood = true;

                        rc.submitTransaction(message, 1);
                    }

                    rc.dropUnit(dir.opposite());
                    lastJob = "dropping enemy";
                    droneTurn = 0;
                    System.out.println("I destroyed enemy robot");
                }
                else
                    break;
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
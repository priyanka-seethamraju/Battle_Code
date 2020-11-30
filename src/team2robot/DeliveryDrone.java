package team2robot;
import battlecode.common.*;

import java.util.Map;

public class DeliveryDrone extends Robot{

    public DeliveryDrone(RobotController r){
        super(r);
    }

    public void takeTurn() throws GameActionException { 
        runDeliveryDrone();
    }
    
    static void runDeliveryDrone() throws GameActionException {
        //System.out.println("enemy team is :" + enemy);
        //System.out.println("Our team is :" + ourTeam);
        //System.out.println("delivery drone is currently holding unit" + rc.isCurrentlyHoldingUnit());
        //Pick up robots if the delivery drone is not currently holding units
        //System.out.println("Turn count: " + droneTurn);
        System.out.println("My job is: " + job);
        System.out.println("My last job was: " + lastJob);
        //System.out.println("Search: " + search);
        //System.out.println("Bytecodes used: " + Clock.getBytecodeNum());
        //System.out.println("Bytecodes left: " + Clock.getBytecodesLeft());
        //System.out.println("Cool down turns left: " + rc.getCooldownTurns());
        
        // if dont know flooded location then sense nearby for flood
        getFlood();

        // check messages for flooded, enemy hq and hq locations
        getMessages();


        if(!rc.isCurrentlyHoldingUnit() && rc.getLocation().distanceSquaredTo(HQloc) <= 50) {
            // scan near team HQ to check for enemies
            enemiesAtHQ();
            // check if we need to send landscapers to enemy hq
            missionBuryHQ();
        }

        //set job
        setJob();

        // search for enemy hq
        if(job == "findHQ") {
            // search first location
            search1();
            // search next location
            search2();
            // search last location
            search3();
        }

        // head to hq to find and pick up cows
        else if(job == "pick up cows"){
            droneTurn++;
            // search for cows near hq
            if(rc.getLocation().distanceSquaredTo(HQloc) <= 50) {
                RobotInfo[] cows = rc.senseNearbyRobots(24);
                if (cows.length > 0) {
                    pickUpCow(cows);
                }
                else {
                    for (Direction dir : directions) {
                        if (rc.canMove(randomDirection()) && tryMove(randomDirection()))
                            System.out.println("I moved...");
                    }
                }
            }
            // move towards hq to look for cows
            else{
                headToTeamHQ();
            }
        }

        // head to enemy hq to pick up cows
        else if(job == "pick up enemy") {
            droneTurn++;
            // See if there are any enemy robots within capturing range
            RobotInfo[] robots = rc.senseNearbyRobots();
            //System.out.println("robots length:" + robots.length);\
            // search nearby bots
            if (robots.length > 0) {
                pickUpEnemy(robots);
            }
            // no bots here so head to enemy hq
            else {
                headToEnemyHQ();
            }
        }

        // drop cows on enemy hq
        else if(job == "dropping cows"){
            dropCow();
        }

        // drop enemies in flooded tiles
        else if (job == "dropping enemy"){
            drownEnemy();
        }

        // drop landscapers
        else if (job == "dropping landscapers"){
            dropLandscaper();
        }
    }

    static void search1() throws GameActionException {
        int height = rc.getMapHeight();
        int width = rc.getMapWidth();

        if(search == "0") {
            // maplocation is opposite hq on the x axis
            MapLocation tryHQ = new MapLocation(width - HQloc.x - 1, HQloc.y);
            if((tryHQ.x >= 0 && tryHQ.x < width) && (tryHQ.y >= 0 && tryHQ.y < height)) {
                if (rc.getLocation().isAdjacentTo(tryHQ)) {
                    RobotInfo[] r = rc.senseNearbyRobots();
                    for (int i = 0; i < r.length; i++) {
                        if (r[i].team == rc.getTeam().opponent()) {
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
                            else
                                search = "1";
                        }
                    }
                    if(r.length == 0){
                        search = "1";
                    }
                } else {
                    for (Direction dir : directions) {
                        Direction move = rc.getLocation().directionTo(tryHQ);
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
                search = "1";
        }
    }

    static void search2() throws GameActionException {
        int height = rc.getMapHeight();
        int width = rc.getMapWidth();

        if(search == "1") {
            // maplocation is opposite on the diagonal x and y axis
            MapLocation tryHQ = new MapLocation(width - HQloc.x - 1, height - HQloc.y - 1);
            if((tryHQ.x >= 0 && tryHQ.x < width) && (tryHQ.y >= 0 && tryHQ.y < height)) {
                if (rc.getLocation().isAdjacentTo(tryHQ)) {
                    RobotInfo[] r = rc.senseNearbyRobots();
                    for (int i = 0; i < r.length; i++) {
                        if (r[i].team == rc.getTeam().opponent()) {
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
                            else
                                search = "2";
                        }
                    }
                    if(r.length == 0){
                        search = "2";
                    }
                } else {
                    for (Direction dir : directions) {
                        Direction move = rc.getLocation().directionTo(tryHQ);
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
                search = "2";
        }
    }

    static void search3() throws GameActionException {
        int height = rc.getMapHeight();
        int width = rc.getMapWidth();

        if(search == "2") {
            // maplocation is opposite hq on y axis
            MapLocation tryHQ = new MapLocation(HQloc.x , height - HQloc.y - 1);
            if((tryHQ.x >= 0 && tryHQ.x < width) && (tryHQ.y >= 0 && tryHQ.y < height)) {
                if (rc.getLocation().isAdjacentTo(tryHQ)) {
                    RobotInfo[] r = rc.senseNearbyRobots();
                    for (int i = 0; i < r.length; i++) {
                        if (r[i].team == rc.getTeam().opponent()) {
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
                            else
                                search = "0";
                        }
                    }
                    if(r.length == 0){
                        search = "0";
                    }
                } else {
                    for (Direction dir : directions) {
                        Direction move = rc.getLocation().directionTo(tryHQ);
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
                search = "0";
        }
    }

    static void setJob() throws GameActionException {
        // set job to finding enemyHQ
        if(!knowEnemyHQ){
            job = "findHQ";
        }

        // set job to picking up cows or enemy
        else if(knowEnemyHQ && !rc.isCurrentlyHoldingUnit()) {
            if(lastJob == "dropping enemy"){
                // change jobs if drone spends too much time looking for bot
                if(droneTurn >= 20){
                    droneTurn = 0;
                    job = "pick up enemy";
                    lastJob = "dropping cows";
                }
                else
                    job = "pick up cows";
            }
            else {
                // change jobs if drone spends too much time looking for bot
                if(droneTurn >= 25){
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
    }

    static void enemiesAtHQ() throws GameActionException {
        //System.out.println(rc.getLocation().distanceSquaredTo(HQloc));
        RobotInfo[] robots = rc.senseNearbyRobots();
        for (RobotInfo rb : robots) {
            if (rb.getTeam() != rc.getTeam()) {
                job = "pick up enemy";
                System.out.println("ENEMY!!");
            }
        }
    }

    static void missionBuryHQ() throws GameActionException {
        //RobotInfo[] bot = rc.senseNearbyRobots(rc.getLocation(), 1, rc.getTeam());
        //if(rc.isCurrentlyHoldingUnit() && bot[0].type == RobotType.LANDSCAPER) {
        //    dropLandscaper();
        //}
        RobotInfo[] robots = rc.senseNearbyRobots();
        pickUpLandscaper(robots);
    }

    static void pickUpLandscaper(RobotInfo[] robots) throws GameActionException {
        if (robots.length > 0) {
            for (int i = 0; i < robots.length; i++) {
                if (robots[i].getTeam() == rc.getTeam() && robots[i].type == RobotType.LANDSCAPER) {
                    System.out.println("I sense " + robots[i].getID() + "!");
                    ls_count++;
                    if (ls_count > 3) {
                        droneTurn = 0;
                        if (rc.canPickUpUnit(robots[i].getID())) {
                            ls.add(robots[i].getID());
                            if (ls_count == 1) {
                                int[] message = new int[7];
                                message[0] = 12;
                                message[1] = robots[i].getID();
                                message[2] = 0;
                                message[3] = 0;
                                message[4] = 0;
                                message[5] = 0;
                                message[6] = 0;

                                rc.submitTransaction(message, 1);
                            }
                            if (ls_count == 2) {
                                int[] message = new int[7];
                                message[0] = 13;
                                message[1] = robots[i].getID();
                                message[2] = 0;
                                message[3] = 0;
                                message[4] = 0;
                                message[5] = 0;
                                message[6] = 0;

                                rc.submitTransaction(message, 1);
                            }
                            rc.pickUpUnit(robots[i].getID());
                            droneTurn = 0;
                            lastJob = "pick up cows";
                            //job = "dropping cows";
                            System.out.println("I picked up " + robots[i].getID() + "!");
                        }
                        // attempt to move towards bot if cant reach
                        else {
                            MapLocation landscapers = robots[i].getLocation();
                            for (Direction dir : directions) {
                                Direction move = rc.getLocation().directionTo(landscapers);
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
                    } else {
                        //lastJob = "dropping enemy";
                    }
                }
            }
        }
        else {
            for (Direction dir : directions) {
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
    }

    static void dropLandscaper() throws GameActionException {
        // attempt to drop landscaper
        for (Direction dir : directions) {
            if (rc.getLocation().isAdjacentTo(enemyHQloc) && rc.canDropUnit(dir)) {
                rc.dropUnit(dir.opposite());
                lastJob = "dropping cows";
            }
        }
        // head to enemy hq. Will know location because we searched first
        if (knowEnemyHQ) {
            for (Direction dir : directions) {
                Direction move = rc.getLocation().directionTo(enemyHQloc);
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

    static void pickUpEnemy(RobotInfo[] robots) throws GameActionException {
        // Pick up a first robot within range
        for (int i = 0; i < robots.length; i++) {
            // make sure it is a bot we can pickup
            if (robots[i].getTeam() != rc.getTeam() && (robots[i].getType() == RobotType.MINER || robots[i].getType() == RobotType.LANDSCAPER)) {
                // if sense the bot then reset drone turn counter and attempt to pick up
                droneTurn = 0;
                if (rc.canPickUpUnit(robots[i].getID())) {
                    rc.pickUpUnit(robots[i].getID());
                    droneTurn = 0;
                    lastJob = "pick up enemy";
                    System.out.println("I picked up " + robots[i].getID() + "!");
                }
                // attempt to move towards bot if cant reach
                else {
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
            // cant find bots that we can pick up so look for them
            else {
                for (Direction dir : directions) {
                    Direction move = rc.getLocation().directionTo(enemyHQloc);
                    if (rc.canMove(move) && tryMove(move))
                        System.out.println("I moved!");
                }
            }
        }
    }

    static void drownEnemy() throws GameActionException {
        //System.out.println("I am trying to drown you!!!!!!");
        // sense flooding and update know location and attempt to drop bot
        flooding = rc.senseFlooding(rc.getLocation());
        if(flooding) {
            for (Direction dir : directions) {
                if (rc.canDropUnit(dir)) {
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

                    rc.dropUnit(dir);
                    lastJob = "dropping enemy";
                    droneTurn = 0;
                    System.out.println("I destroyed enemy robot");
                }
            }
        }
        // if we know flooded location then move to it
        if (knowFlood) {
            headToFlood();
        }
        // dont know flooded area then look around
        else {
            for (Direction dir : directions) {
                if(rc.canMove(randomDirection()))
                    tryMove(randomDirection());
            }
        }
    }

    static void pickUpCow(RobotInfo[] cows) throws GameActionException {
        for (int i = 0; i < cows.length; i++) {
            // abort cow mission, we found enemies in our base!!
            if(cows[i].team == rc.getTeam().opponent() && cows[i].type == RobotType.LANDSCAPER){
                pickUpEnemy(cows);
            }
            // if found cow then attempt pick up
            else if (cows[i].type == RobotType.COW) {
                droneTurn = 0; // if found cows give drone more time to focus on job
                if (rc.canPickUpUnit(cows[i].getID())) {
                    rc.pickUpUnit(cows[i].getID());
                    droneTurn = 0;
                    lastJob = "pick up cows";
                    System.out.println("I picked up " + cows[i].getID() + "!");
                }
                // cant reach cow so head to it
                else {
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
            // cant find cows so look around base
            else {
                for (Direction dir : directions) {
                    if (rc.canMove(randomDirection()) && tryMove(randomDirection()))
                        System.out.println("I moved...");
                }
            }
        }
    }

    static void dropCow() throws GameActionException {
        // attempt to drop cow
        for (Direction dir : directions) {
            if (rc.getLocation().isAdjacentTo(enemyHQloc) && rc.canDropUnit(dir)) {
                rc.dropUnit(dir.opposite());
                lastJob = "dropping cows";
                droneTurn = 0;
            }
        }
        // head to enemy hq. Will know location because we searched first
        if (knowEnemyHQ) {
            for (Direction dir : directions) {
                Direction move = rc.getLocation().directionTo(enemyHQloc);
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
    
    static void headToEnemyHQ() throws GameActionException {
        for (Direction dir : directions) {
            Direction move = rc.getLocation().directionTo(enemyHQloc);
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
    
    static void headToTeamHQ() throws GameActionException {
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
    
    static void headToFlood() throws GameActionException {
        for (Direction dir : directions) {
            Direction move = rc.getLocation().directionTo(floodLoc);
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

    static void getFlood() throws GameActionException {
        if (!knowFlood) {
            flooding = rc.senseFlooding(rc.getLocation());
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
    
    static void getMessages() throws GameActionException {
        int num = rc.getRoundNum();
        for (int i = 1; i < num; i++) {
            Transaction[] transaction = rc.getBlock(num - i);
            for (Transaction t : transaction) {
                int[] message = t.getMessage();
                if (message[0] == 1 && !knowHQ) {
                    HQloc = new MapLocation(message[1], message[2]);
                    knowHQ = true;
                    System.out.println("HQ Location: " + message[1] + ", " + message[2]);
                }
                // get enemy base location
                if (message[0] == 6 && !knowEnemyHQ) {
                    enemyHQloc = new MapLocation(message[1], message[2]);
                    knowEnemyHQ = true;
                    search = "done";
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
}
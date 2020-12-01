package team2robot;
import battlecode.common.*;

public class Landscaper extends Robot{

    public Landscaper(RobotController r){
        super(r);
    }

    public void takeTurn() throws GameActionException {
        runLandscaper();
    }

    static void runLandscaper() throws GameActionException {

        // get transaction message to find location of hq
        int num = rc.getRoundNum();
        for (int i = 1; i < num; i++) {
            Transaction[] transaction = rc.getBlock(num - i);
            for (Transaction t : transaction) {
                int[] message = t.getMessage();
                if (message[0] == 1 && !knowHQ) {
                    HQloc = new MapLocation(message[1], message[2]);
                    knowHQ = true;
                    //System.out.println("HQ Location: " + message[1] + ", " + message[2]);
                }
                if (message[0] == 6 && !knowEnemyHQ) {
                    enemyHQloc = new MapLocation(message[1], message[2]);
                    knowEnemyHQ = true;
                    //System.out.println("HQ Location: " + message[1] + ", " + message[2]);
                }
                //if (message[0] == 8 ) {
                //    l=message[1];
                //    System.out.println("l is: " + message[1]);
                //}
                //if (message[0] == 9 ) {
                //    w=message[1];
                //    System.out.println("w is: " + message[1]);
                //}
            }
        }

        if(knowEnemyHQ && rc.getLocation().distanceSquaredTo(enemyHQloc) <= 50){
            //System.out.println("k");
            if (!rc.getLocation().isAdjacentTo(enemyHQloc)) {
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

            if (rc.getLocation().isAdjacentTo(enemyHQloc)) {
                RobotInfo[] bots = rc.senseNearbyRobots();
                for (int i = 0; i < bots.length; i++) {

                    if (bots[i].type == RobotType.HQ) {
                        //System.out.println("I");
                        if (rc.canDigDirt(rc.getLocation().directionTo(enemyHQloc).opposite())) {
                            //System.out.println("!");
                            rc.digDirt(rc.getLocation().directionTo(enemyHQloc).opposite());
                        }
                    }
                }
                if (rc.getDirtCarrying() < 3) {
                    if (rc.canDigDirt(rc.getLocation().directionTo(enemyHQloc).opposite())) {
                        //System.out.println("!!");
                        rc.digDirt(rc.getLocation().directionTo(enemyHQloc).opposite());
                    }
                }
                if (rc.getLocation().isAdjacentTo(enemyHQloc)) {
                    Direction dir = rc.getLocation().directionTo(enemyHQloc);
                    if(rc.canDepositDirt(dir)) {
                        //System.out.println("!!!");
                        rc.depositDirt(dir);
                    }
                }
            }
        }

        //System.out.println("Two landscapers"+l +w);
        //System.out.println("landscaper id"+rc.getID());
        else {
            //if (l != rc.getID() && w!=rc.getID()) {
            // move to hq if not already near it
            if (!rc.getLocation().isAdjacentTo(HQloc)) {
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

            // if the block on is already a wall then move
            if (rc.senseElevation(rc.getLocation()) == rc.senseElevation(HQloc) + floodHeight) {
                for (Direction dir : directions) {
                    if (rc.canMove(randomDirection()) && tryMove(randomDirection()))
                        System.out.println("I moved!");
                }
            }

            // if near hq dig and deposit dirt around it to build wall
            if (rc.getLocation().isAdjacentTo(HQloc)) {
                RobotInfo[] bots = rc.senseNearbyRobots();
                for (int i = 0; i < bots.length; i++) {
                    if (bots[i].team == rc.getTeam()) {
                        if (bots[i].dirtCarrying > 0 && bots[i].type == RobotType.HQ) {
                            if (rc.canDigDirt(rc.getLocation().directionTo(HQloc))) {
                                System.out.println("I dug!");
                                rc.digDirt(rc.getLocation().directionTo(HQloc));
                            }
                        }
                    }
                }
                if (rc.getDirtCarrying() < 3) {
                    if (rc.canDigDirt(rc.getLocation().directionTo(HQloc).opposite())) {
                        System.out.println("I dug!");
                        rc.digDirt(rc.getLocation().directionTo(HQloc).opposite());
                    }
                }
                if (rc.getDirtCarrying() == 3) {
                    if (rc.senseElevation(rc.getLocation()) < rc.senseElevation(HQloc) + floodHeight) {
                        if (rc.canDepositDirt(Direction.CENTER)) {
                            System.out.println("I deposited!");
                            rc.depositDirt(Direction.CENTER);
                        }
                    }
                }
            }
        }

    }
}

package Team2Robot;
import battlecode.common.*;

public class Miner extends RobotPlayer{

    static boolean refineryBuilt = false;
    static boolean designBuilt = false;
    static boolean fulfillmentBuilt = false;

    static void runMiner() throws GameActionException {
        // get transaction message to find location of hq, if buildings have been built
        int num = rc.getRoundNum();
        for(int i = 1; i < num; i++){
            Transaction[] transaction = rc.getBlock(num - i);
            for(Transaction t : transaction) {
                int[] message = t.getMessage();
                if(message[0] == 1 && !knowHQ){
                    HQloc = new MapLocation(message[1], message[2]);
                    knowHQ = true;
                    //System.out.println("Miner knows HQ Location: " + message[1] + ", " + message[2]);
                }
                if(message[0] == 2){
                    refineryBuilt = true;
                    //System.out.println("Miner knows if Refinery Built: " + refineryBuilt);
                }
                if(message[0] == 3){
                    designBuilt = true;
                    //System.out.println("Miner knows if Design Built: " + designBuilt);
                }
                if(message[0] == 4){
                    fulfillmentBuilt = true;
                    //System.out.println("Miner knows if Fulfill Built: " + fulfillmentBuilt);
                }
            }
        }

        // if carrying soup then refine it
        if(rc.getSoupCarrying() > 25){
            for (Direction dir : directions) {
                //refines at hq
                if (rc.getLocation().isAdjacentTo(HQloc)) {
                    if (tryRefine(dir))
                        System.out.println("I refined soup! " + rc.getTeamSoup());

                }
                // if not near hq then head towards it
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
        }
        else{
            // sense soup to mine, if near then refine, else head towards it
            MapLocation[] soup = rc.senseNearbySoup();
            for(int i = 0; i < soup.length; i++){
                if(rc.getLocation().isAdjacentTo(soup[i])) {
                   if(rc.canMineSoup(soup[i].directionTo(soup[i])) && tryMine(soup[i].directionTo(soup[i]))){
                       System.out.println("I mined soup!");
                   }
                }
                else{
                    for (Direction dir : directions){
                        Direction move = rc.getLocation().directionTo(soup[i]);
                        if (rc.canMove(move) && tryMove(move))
                            System.out.println("I mined soup! " + rc.getSoupCarrying());
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

        }
        // build refinery if not already built
        if (!refineryBuilt) {
            if(!rc.getLocation().isAdjacentTo(HQloc)) {
                for (Direction dir : directions) {
                    MapLocation loc = rc.getLocation().add(dir);
                    if(!loc.isAdjacentTo(HQloc) && rc.senseSoup(loc) == 0 && !loc.isWithinDistanceSquared(HQloc, 5)){
                        // send message if refinery built
                        if (rc.canBuildRobot(RobotType.REFINERY, dir) && tryBuild(RobotType.REFINERY, dir)) {
                            //System.out.println("I built a Refinery!");
                            int[] message = new int[7];
                            message[0] = 2;
                            message[1] = 0;
                            message[2] = 0;
                            message[3] = 0;
                            message[4] = 0;
                            message[5] = 0;
                            message[6] = 0;

                            rc.submitTransaction(message, 1);
                        }
                    }                }

            }
        }
        // build design school
        else if (!designBuilt) {
            if(!rc.getLocation().isAdjacentTo(HQloc)) {
                for (Direction dir : directions) {
                    MapLocation loc = rc.getLocation().add(dir);
                    if(!loc.isAdjacentTo(HQloc) && rc.senseSoup(loc) == 0 && !loc.isWithinDistanceSquared(HQloc, 5)) {
                        if (rc.canBuildRobot(RobotType.DESIGN_SCHOOL, dir) && tryBuild(RobotType.DESIGN_SCHOOL, dir)) {
                            // send message that design school is built
                            //System.out.println("I built a Design!");
                            int[] message = new int[7];
                            message[0] = 3;
                            message[1] = 0;
                            message[2] = 0;
                            message[3] = 0;
                            message[4] = 0;
                            message[5] = 0;
                            message[6] = 0;

                            rc.submitTransaction(message, 1);
                        }
                    }
                }
            }
        }
        // build fulfillment center
        else if(!fulfillmentBuilt) {
            if(!rc.getLocation().isAdjacentTo(HQloc)) {
                for (Direction dir : directions) {
                    MapLocation loc = rc.getLocation().add(dir);
                    if(!loc.isAdjacentTo(HQloc) && rc.senseSoup(loc) == 0 && !loc.isWithinDistanceSquared(HQloc, 5)) {
                        if (rc.canBuildRobot(RobotType.FULFILLMENT_CENTER, dir) && tryBuild(RobotType.FULFILLMENT_CENTER, dir)) {
                            // send message that it is built
                            //System.out.println("I built a Fulfillment!");
                            int[] message = new int[7];
                            message[0] = 4;
                            message[1] = 0;
                            message[2] = 0;
                            message[3] = 0;
                            message[4] = 0;
                            message[5] = 0;
                            message[6] = 0;

                            rc.submitTransaction(message, 1);
                        }
                    }
                }
            }
        }

    }
}
package Team2Robot;
import battlecode.common.*;

public class Miner extends RobotPlayer{
    static void runMiner() throws GameActionException {
        // get transaction message to find location of hq
        int num = rc.getRoundNum();
        Transaction[] transaction = rc.getBlock(num -1);
        if(!knowHQ){
            for(Transaction t : transaction) {
                int[] message = t.getMessage();
                if(message[0] == 1){
                    HQloc = new MapLocation(message[1], message[2]);
                    knowHQ = true;
                    System.out.println("HQ Location: " + message[1] + ", " + message[2]);
                }
            }
        }

        if(refineryCount < 1){
            for (Direction dir : directions)
                if(tryBuild(RobotType.REFINERY, dir))
                    refineryCount++;
        }
        if(rc.getSoupCarrying() == RobotType.MINER.soupLimit){
            for (Direction dir : directions)
                if(rc.getLocation().isAdjacentTo(HQloc)){
                    if (tryRefine(dir))
                        System.out.println("I refined soup! " + rc.getTeamSoup());
                }
                else {
                    Direction move = rc.getLocation().directionTo(HQloc);
                    if (tryMove(move))
                        System.out.println("I moved!");
                }
        }
        else{
            for (Direction dir : directions)
                if (tryMine(dir))
                    System.out.println("I mined soup! " + rc.getSoupCarrying());
                else{
                    if (tryMove(randomDirection()))
                        System.out.println("I moved!");
                }
        }

        if(designCount < 2){
            for (Direction dir : directions)
                if (tryBuild(RobotType.DESIGN_SCHOOL, dir)) {
                    designCount++;
                }
        }

        //tryBlockchain();
        //tryMove(randomDirection());
        //if (tryMove(randomDirection()))
        //    System.out.println("I moved!");

        //for (Direction dir : directions)
        //tryBuild(RobotType.FULFILLMENT_CENTER, dir);
        //for (Direction dir : directions)
        //if (tryRefine(dir))
        //System.out.println("I refined soup! " + rc.getTeamSoup());
        //for (Direction dir : directions)
        //if (tryMine(dir))
        //System.out.println("I mined soup! " + rc.getSoupCarrying());
    }
}
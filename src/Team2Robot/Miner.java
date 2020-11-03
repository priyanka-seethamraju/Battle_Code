package Team2Robot;
import battlecode.common.*;

public class Miner extends RobotPlayer{

    static void runMiner() throws GameActionException {
        // get transaction message to find location of hq
        int num = rc.getRoundNum();
        for(int i = 1; i < num; i++){
            Transaction[] transaction = rc.getBlock(num - i);
            for(Transaction t : transaction) {
                int[] message = t.getMessage();
                if(message[0] == 1 && !knowHQ){
                    HQloc = new MapLocation(message[1], message[2]);
                    knowHQ = true;
                    //System.out.println("HQ Location: " + message[1] + ", " + message[2]);
                }
                if(message[0] == 2){
                    refineryBuilt = true;
                }
                if(message[0] == 3){
                    designBuilt = true;
                }
                if(message[0] == 4){
                    fulfillmentBuilt = true;
                }
            }
        }

        if(rc.getSoupCarrying() > 25){
            for (Direction dir : directions)
                if(rc.getLocation().isAdjacentTo(HQloc)){
                    if (tryRefine(dir))
                        System.out.println("I refined soup! " + rc.getTeamSoup());
                }
                else {
                    Direction move = rc.getLocation().directionTo(HQloc);
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
        else{
            MapLocation[] soup = rc.senseNearbySoup();
            for(int i = 0; i < soup.length; i++){
                if(rc.getLocation().isAdjacentTo(soup[i])) {
                   if(tryMine(soup[i].directionTo(soup[i]))){
                       System.out.println("I mined soup!");
                   }
                }
                else{
                    for (Direction dir : directions){
                        Direction move = rc.getLocation().directionTo(soup[i]);
                        if (tryMove(move))
                            System.out.println("I mined soup! " + rc.getSoupCarrying());
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
        }
        if (!designBuilt) {
            for (Direction dir : directions)
                if (tryBuild(RobotType.DESIGN_SCHOOL, dir)) {
                    int[] message = new int[7];
                    message[0] = 3;
                    message[1] = 0;
                    message[2] = 0;
                    message[3] = 0;
                    message[4] = 0;
                    message[5] = 0;
                    message[6] = 0;

                    rc.submitTransaction(message,1);
                }
        }
        else if(!fulfillmentBuilt) {
            for (Direction dir : directions) {
                if(tryBuild(RobotType.FULFILLMENT_CENTER, dir)) {
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
        else if (!refineryBuilt) {
            for (Direction dir : directions)
                if (tryBuild(RobotType.REFINERY, dir)) {
                    int[] message = new int[7];
                    message[0] = 2;
                    message[1] = 0;
                    message[2] = 0;
                    message[3] = 0;
                    message[4] = 0;
                    message[5] = 0;
                    message[6] = 0;

                    rc.submitTransaction(message,1);
                }
        }
    }
}
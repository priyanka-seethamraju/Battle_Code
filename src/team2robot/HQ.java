package team2robot;
import battlecode.common.*;

public class HQ extends Robot{

    public HQ(RobotController r){
        super(r);
    }

    public void takeTurn() throws GameActionException {
        runHQ();
    }

    public static void runHQ() throws GameActionException {


        // if roundnume is 10 or below
        if(rc.getRoundNum() <= 10){
            knowHQ(); // call function below
        }

        // attempt to build miners
        buildMiner();


        System.out.println("The teams total soup is :"+rc.getTeamSoup());
        System.out.println("HQ Location Elevation: " + rc.senseElevation(rc.getLocation()));


    }






    public static boolean buildMiner() throws GameActionException{
        if(minerCount < 5){
            for (Direction dir : directions)
                if(tryBuild(RobotType.MINER, dir)) {
                    minerCount++;
                    return true;
                }
        }
        return false;
    }



    public static boolean knowHQ() throws GameActionException {
        if(!knowHQ && rc.getRoundNum() <= 10){
            int[] message = new int[7];
            message[0] = 1; // this indicates that this message is just for HQ location
            message[1] = rc.getLocation().x;
            message[2] = rc.getLocation().y;
            message[3] = 0;
            message[4] = 0;
            message[5] = 0;
            message[6] = 0;

            rc.submitTransaction(message,1);
            return true;
        }
        else{
            return false;
        }
    }

}
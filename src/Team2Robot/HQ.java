package Team2Robot;
import battlecode.common.*;

public class HQ extends RobotPlayer{
    static void runHQ() throws GameActionException {
      
        if(!knowHQ){
            int[] message = new int[7];
            message[0] = 1; // this indicates that this message is just for HQ location
            message[1] = rc.getLocation().x;
            message[2] = rc.getLocation().y;
            message[3] = 0;
            message[4] = 0;
            message[5] = 0;
            message[6] = 0;

            rc.submitTransaction(message,1);
        }

        if(minerCount < 10){
            for (Direction dir : directions)
                if(tryBuild(RobotType.MINER, dir))
                    minerCount++;
        }
        System.out.println("The teams total soup is :"+rc.getTeamSoup());
        System.out.println("HQ Location Elevation: " + rc.senseElevation(rc.getLocation()));
    }
}
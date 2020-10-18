package examplefuncsplayer;
import battlecode.common.*;

public class HQ extends RobotPlayer{
    static void runHQ() throws GameActionException {
        if(minerCount < 5){
            for (Direction dir : directions)
                if(tryBuild(RobotType.MINER, dir))
                    minerCount++;
        }
    }
}

package Team2Robot;
import battlecode.common.*;

public class HQ extends RobotPlayer{
    static void runHQ() throws GameActionException {
        for (Direction dir : directions)
            tryBuild(RobotType.MINER, dir);
    }
}
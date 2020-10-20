package Team2Robot;
import battlecode.common.*;

public class Miner extends RobotPlayer{

    static boolean refineryBuilt = false;

    static void runMiner() throws GameActionException {

        tryBlockchain();

        if (refineryBuilt == false) {
            for (Direction dir : directions)
                if (tryBuild(RobotType.REFINERY, dir) == true) {
                    refineryBuilt = true;
                }
        }
        for (Direction dir: directions) {
            tryBuild(RobotType.DESIGN_SCHOOL, dir);
        }
        for (Direction dir : directions)
            if (tryRefine(dir))
                System.out.println("I refined soup! " + rc.getTeamSoup());
        for (Direction dir : directions)
            if (tryMine(dir))
                System.out.println("I mined soup! " + rc.getSoupCarrying());
        if (tryMove(randomDirection()))
            System.out.println("I moved!");
    }
}
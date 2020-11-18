package Team2Robot;
import battlecode.common.*;

public class NetGun extends Robot{

    public NetGun(RobotController r){
        super(r);
    }

    public void takeTurn() throws GameActionException {
        runNetGun();
    }

    static void runNetGun() throws GameActionException {
        RobotInfo[] robots = rc.senseNearbyRobots();
        Team enemy = rc.getTeam().opponent();
        if(robots.length > 0){
            for(int i = 0; i < robots.length; ++i){
                if(robots[i].getTeam() == enemy && robots[i].getType() == RobotType.DELIVERY_DRONE){
                    rc.shootUnit(robots[i].getID());
                    System.out.println("I have shot down a unit!");
                }
            }
        }

    }
}

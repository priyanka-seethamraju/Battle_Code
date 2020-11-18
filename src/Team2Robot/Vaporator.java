package team2robot;
import battlecode.common.*;

public class Vaporator extends Robot{

    public Vaporator(RobotController r){
        super(r);
    }

    public void takeTurn() throws GameActionException {
        runVaporator();
    }

    static void runVaporator() throws GameActionException {
             System.out.println("I am a VAPORATOR and I just got created, I condensed soup from air: " + rc.getTeamSoup());
    }
}

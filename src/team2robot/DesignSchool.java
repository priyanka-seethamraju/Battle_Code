package team2robot;
import battlecode.common.*;

public class DesignSchool extends RobotPlayer{
    static void runDesignSchool() throws GameActionException {
        for (Direction dir : directions) {
            if (landscaperCount < 2) {
                if(tryBuild(RobotType.LANDSCAPER, dir)) {
                    System.out.println("I built a Landscaper!");
                    landscaperCount++;
                    System.out.println("Landscaper count is: " + landscaperCount);
                }
            }/*
            else{
                System.out.println("I did NOT make a landscaper");
                System.out.println("Landscaper count is: " + landscaperCount);
            }*/
        }
    }
}
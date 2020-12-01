package team2robot;
import battlecode.common.*;

public class DesignSchool extends Robot{

    public DesignSchool(RobotController r){
        super(r);
    }

    public void takeTurn() throws GameActionException {
        runDesignSchool();
    }

    static void runDesignSchool() throws GameActionException {
        if(buildLandscaper()) {
            System.out.print("I built a landscaper!");
            System.out.print("landscaperCount is: " + landscaperCount);
        }
        /*
        for (Direction dir : directions) {
            if (landscaperCount < 2) {
                if(tryBuild(RobotType.LANDSCAPER, dir)) {
                    System.out.println("I built a Landscaper!");
                    landscaperCount++;
                    System.out.println("Landscaper count is: " + landscaperCount);
                }
            }*//*
            else{
                System.out.println("I did NOT make a landscaper");
                System.out.println("Landscaper count is: " + landscaperCount);
            }*/
        //}
    }

    public static boolean buildLandscaper() throws GameActionException{
        for (Direction dir : directions){
            if(landscaperCount < 8){
                if(tryBuild(RobotType.LANDSCAPER, dir)){
                    System.out.println("I built a Landscaper!");
                    landscaperCount++;
                    System.out.println("Landscaper count is: " + landscaperCount);
                    return true;
                }
            }
        }
        return false;
    }

}
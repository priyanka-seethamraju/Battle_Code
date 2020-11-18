package Team2Robot;
import battlecode.common.*;

public strictfp class RobotPlayer {
    static RobotController rc;



    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * If this method returns, the robot dies!
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // This is the RobotController object. You use it to perform actions from this robot,
        // and to get information on its current status.

        Robot me = null;

        switch (rc.getType()) {
            case HQ:
                me = new HQ(rc);
                break;
            case MINER:
                me = new Miner(rc);
                break;
            case REFINERY:
                me = new Refinery(rc);
                break;
            case VAPORATOR:
                me = new Vaporator(rc);
                break;
            case DESIGN_SCHOOL:
                me = new DesignSchool(rc);
                break;
            case FULFILLMENT_CENTER:
                me = new FulfillmentCenter(rc);
                break;
            case LANDSCAPER:
                me = new Landscaper(rc);
                break;
            case DELIVERY_DRONE:
                me = new DeliveryDrone(rc);
                break;
            case NET_GUN:
                me = new NetGun(rc);
                break;
        }

        while (true) {
            try {
                me.takeTurn();

                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();
            } catch (Exception e) {
                System.out.println(rc.getType() + " Exception"); // darn
                e.printStackTrace();
            }
        }

    }
}

        /*
        RobotPlayer.rc = rc;

        turnCount = 0;

        System.out.println("I'm a " + rc.getType() + " and I just got created!");
        while (true) {
            turnCount += 1;
            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
                // Here, we've separated the controls into a different method for each RobotType.
                // You can add the missing ones or rewrite this into your own control structure.
                System.out.println("I'm a " + rc.getType() + "! Location " + rc.getLocation());
                switch (rc.getType()) {
                    case HQ:                 HQ.runHQ();                break;
                    case MINER:              Miner.runMiner();             break;
                    case REFINERY:           Refinery.runRefinery();          break;
                    case VAPORATOR:          Vaporator.runVaporator();         break;
                    case DESIGN_SCHOOL:      DesignSchool.runDesignSchool();      break;
                    case FULFILLMENT_CENTER: FulfillmentCenter.runFulfillmentCenter(); break;
                    case LANDSCAPER:         Landscaper.runLandscaper();        break;
                    case DELIVERY_DRONE:     DeliveryDrone.runDeliveryDrone();     break;
                    case NET_GUN:            NetGun.runNetGun();            break;
                }

                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();
            }
        }
    }

*/

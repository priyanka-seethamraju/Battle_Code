package team2robot;

import org.junit.Test;
import battlecode.common.*;
import team2robot.RobotPlayer;
import org.junit.*;
import org.mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;


public class DeliveryDroneTest {

    private RobotController rc = null;
    private RobotPlayer r = null;
    private DeliveryDrone DeliveryDroneTest =null;


    @Before
    public void beforeEachTest() throws GameActionException{
        rc = Mockito.mock(RobotController.class);
        r = Mockito.mock(RobotPlayer.class);
        DeliveryDroneTest = new DeliveryDrone(rc);
    }

    @Test
    public void testRunDeliveryDrone() throws GameActionException{
        when(rc.getTeam()).thenReturn(Team.A);
        when(rc.getTeam().opponent()).thenReturn(Team.B);
        when(rc.getMapWidth()).thenReturn(10);
        when(rc.getMapHeight()).thenReturn(10);
        MapLocation temp = new MapLocation(1,1);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.senseFlooding(rc.getLocation())).thenReturn(true);
        int[] message = new int[7];
        message[0] = 7;
        message[1] = rc.getLocation().x;
        message[2] = rc.getLocation().y;
        message[3] = 0;
        message[4] = 0;
        message[5] = 0;
        message[6] = 0;
        rc.submitTransaction(message, 1);
        when(rc.getRoundNum()).thenReturn(8);
        //when(rc.getBlock(7)).thenReturn(Transaction[] t);

        //DeliveryDroneTest.takeTurn();
        //DeliveryDroneTest.runDeliveryDrone();

    }


}
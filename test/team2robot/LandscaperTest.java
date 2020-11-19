package team2robot;

import org.junit.Test;
import battlecode.common.*;
import team2robot.RobotPlayer;
import org.junit.*;
import org.mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;


public class LandscaperTest {

    private RobotController rc = null;
    private RobotPlayer r = null;
    private Landscaper LandscaperTest =null;


    @Before
    public void beforeEachTest() throws GameActionException{
        rc = Mockito.mock(RobotController.class);
        r = Mockito.mock(RobotPlayer.class);
        LandscaperTest = new Landscaper(rc);

    }

    @Test
    public void testRunLandscaper() throws GameActionException{
        MapLocation temp = new MapLocation(1,1);
        when(rc.getType()).thenReturn(RobotType.LANDSCAPER);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeam()).thenReturn(Team.B);
        when(rc.getID()).thenReturn(821);
        when(rc.getRoundNum()).thenReturn(2);
        when(rc.getTeamSoup()).thenReturn(400);
        when(rc.senseElevation(rc.getLocation())).thenReturn(1);
        int[] message = new int[7];
        message[0] = 1; // this indicates that this message is just for HQ location
        message[1] = rc.getLocation().x;
        message[2] = rc.getLocation().y;
        message[3] = 0;
        message[4] = 0;
        message[5] = 0;
        message[6] = 0;
        when(rc.canSubmitTransaction(message,10)).thenReturn(true);
        Transaction[] transaction = new Transaction[2];
        Transaction transaction1 = new Transaction(10,message,99);
        transaction[0] = transaction1;
        transaction[1] = transaction1;
        when(rc.getBlock(1)).thenReturn(transaction);
        //for(Transaction t : transaction) {
            //when(t.getMessage()).thenReturn(message);
        //}
        RobotInfo[] robots = {Mockito.mock(RobotInfo.class)};
        when(rc.senseNearbyRobots()).thenReturn(robots);
        when(rc.getTeam()).thenReturn(Team.B);
        when(robots[0].getDirtCarrying()).thenReturn(0);


        LandscaperTest.takeTurn();
        //LandscaperTest.runLandscaper();
    }
}
package team2robot;

import org.junit.Test;
import battlecode.common.*;
import team2robot.RobotPlayer;
import org.junit.*;
import org.mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;


public class HQtest {

    private RobotController rc = null;
    private RobotPlayer r = null;
    private HQ HQtest =null;


    @Before
    public void beforeEachTest() throws GameActionException{
        rc = Mockito.mock(RobotController.class);
        r = Mockito.mock(RobotPlayer.class);
        HQtest = new HQ(rc);
    }

    @Test
    public void testRunHQ() throws GameActionException{
        MapLocation temp = new MapLocation(1,1);
        when(rc.getType()).thenReturn(RobotType.HQ);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeam()).thenReturn(Team.B);
        when(rc.getID()).thenReturn(821);
        when(rc.getRoundNum()).thenReturn(9);
        when(rc.getTeamSoup()).thenReturn(70);
        when(rc.senseElevation(rc.getLocation())).thenReturn(1);
        when(rc.isReady()).thenReturn(true);
        when(rc.canBuildRobot(RobotType.MINER,Direction.NORTH)).thenReturn(true);
        int[] message = new int[7];
        message[0] = 1; // this indicates that this message is just for HQ location
        message[1] = rc.getLocation().x;
        message[2] = rc.getLocation().y;
        message[3] = 0;
        message[4] = 0;
        message[5] = 0;
        message[6] = 0;
        when(rc.canSubmitTransaction(message,1)).thenReturn(true);

        HQtest.takeTurn();
        HQtest.runHQ();

    }


}
package team2robot;

import org.junit.Test;
import battlecode.common.*;
import team2robot.RobotPlayer;
import org.junit.*;
import org.mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;


public class NetGunTest {
    private RobotController rc = null;
    private RobotPlayer r = null;
    private NetGun NetGunTest = null;


    @Before
    public void beforeEachTest() throws GameActionException {
        rc = Mockito.mock(RobotController.class);
        r = Mockito.mock(RobotPlayer.class);
        NetGunTest = new NetGun(rc);
    }

    @Test
    public void testRunNetGun() throws GameActionException{
        RobotInfo[] robots = {Mockito.mock(RobotInfo.class)};
        when(rc.senseNearbyRobots()).thenReturn(robots);
        when(rc.getTeam()).thenReturn(Team.A);
        when(rc.getTeam().opponent()).thenReturn(Team.B);
        when(rc.getType()).thenReturn(RobotType.DELIVERY_DRONE);
        when(rc.getID()).thenReturn(999);

        NetGunTest.takeTurn();
        NetGunTest.runNetGun();
    }

    @Test
    public void testRunNetGunFail() throws GameActionException{
        RobotInfo[] robots = {Mockito.mock(RobotInfo.class)};
        when(rc.senseNearbyRobots()).thenReturn(robots);
        when(rc.getTeam()).thenReturn(Team.A);
        when(rc.getTeam().opponent()).thenReturn(Team.B);
        when(rc.getType()).thenReturn(RobotType.MINER);
        when(rc.getID()).thenReturn(2);

        NetGunTest.takeTurn();
        NetGunTest.runNetGun();
    }
}
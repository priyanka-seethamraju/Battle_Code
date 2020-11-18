package team2robot;

import org.junit.Test;
import battlecode.common.*;
import team2robot.RobotPlayer;
import org.junit.*;
import org.mockito.*;

import static org.junit.Assert.*;
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
    public void testRunNetGunShoot() throws GameActionException{
        MapLocation temp = new MapLocation(5,5);
        MapLocation temp2 = new MapLocation(5,6);
        RobotInfo[] robots = {Mockito.mock(RobotInfo.class)};
        when(rc.senseNearbyRobots()).thenReturn(robots);
        when(rc.getTeam()).thenReturn(Team.A);
        when(robots[0].getTeam()).thenReturn(Team.B);
        when(robots[0].getType()).thenReturn(RobotType.DELIVERY_DRONE);
        when(rc.getLocation()).thenReturn(temp);
        when(robots[0].getID()).thenReturn(99);
        when(robots[0].getLocation()).thenReturn(temp2);
        when(rc.isReady()).thenReturn(true);
        when(rc.canShootUnit(99)).thenReturn(true);
        rc.shootUnit(99);

        NetGunTest.takeTurn();
        NetGunTest.runNetGun();
    }

    @Test
    public void testRunNetGunFail() throws GameActionException{
        RobotInfo[] robots = {Mockito.mock(RobotInfo.class)};
        when(rc.senseNearbyRobots()).thenReturn(robots);
        when(rc.getTeam()).thenReturn(Team.A);
        when(rc.getTeam().opponent()).thenReturn(Team.B);
        when(robots[0].getTeam()).thenReturn(Team.A);
        when(robots[0].getType()).thenReturn(RobotType.MINER);
        MapLocation temp = new MapLocation(5,5);
        when(rc.getLocation()).thenReturn(temp);
        when(robots[0].getID()).thenReturn(821);
        when(rc.getRoundNum()).thenReturn(9);
        when(rc.getTeamSoup()).thenReturn(400);
        MapLocation temp2 = new MapLocation(5,6);
        when(robots[0].getLocation()).thenReturn(temp2);
        when(rc.isReady()).thenReturn(true);
        assertFalse(rc.canShootUnit(821));

        NetGunTest.takeTurn();
        NetGunTest.runNetGun();
    }

    @Test
    public void testRunNetGun() throws GameActionException{
        RobotInfo[] robots = {Mockito.mock(RobotInfo.class)};
        when(rc.senseNearbyRobots()).thenReturn(robots);
        when(rc.getTeam()).thenReturn(Team.A);
        when(rc.getTeam().opponent()).thenReturn(Team.B);
        when(robots[0].getTeam()).thenReturn(Team.A);
        when(robots[0].getType()).thenReturn(RobotType.MINER);
        MapLocation temp = new MapLocation(5,5);
        when(rc.getLocation()).thenReturn(temp);
        when(robots[0].getID()).thenReturn(821);
        when(rc.getRoundNum()).thenReturn(9);
        when(rc.getTeamSoup()).thenReturn(400);
        MapLocation temp2 = new MapLocation(5,6);
        when(robots[0].getLocation()).thenReturn(temp2);
        when(rc.isReady()).thenReturn(true);

        NetGunTest.takeTurn();
        NetGunTest.runNetGun();
    }
}
package team2robot;

import org.junit.Test;
import battlecode.common.*;
import org.junit.*;
import org.mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;
import static team2robot.Robot.directions;


public class HQtest {

    private RobotController rc = null;
    private HQ HQtest =null;


    @Before public void beforeEachTest() throws GameActionException {

        rc = Mockito.mock(RobotController.class);
        HQtest = new HQ(rc);

    }


    @Test public void HQTest_runHQ() throws GameActionException {
        MapLocation temp = new MapLocation(5,1);
        when(rc.getType()).thenReturn(RobotType.HQ);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeam()).thenReturn(Team.B);
        when(rc.getID()).thenReturn(821);
        when(rc.getRoundNum()).thenReturn(9);
        when(rc.getTeamSoup()).thenReturn(150);
        when(rc.senseElevation(rc.getLocation())).thenReturn(1);

        HQtest.runHQ();
    }

    @Test public void HQTest_takeTurn() throws GameActionException {
        MapLocation temp = new MapLocation(5,1);
        when(rc.getType()).thenReturn(RobotType.HQ);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeam()).thenReturn(Team.B);
        when(rc.getID()).thenReturn(821);
        when(rc.getRoundNum()).thenReturn(9);
        when(rc.getTeamSoup()).thenReturn(150);
        when(rc.senseElevation(rc.getLocation())).thenReturn(1);
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.MINER, dir)).thenReturn(true);
        }
        HQtest.takeTurn();
    }

    @Test public void HQTest_minerSucceed() throws GameActionException {
        MapLocation temp = new MapLocation(5,1);
        when(rc.getType()).thenReturn(RobotType.HQ);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeam()).thenReturn(Team.B);
        when(rc.getID()).thenReturn(821);
        when(rc.getRoundNum()).thenReturn(9);
        when(rc.getTeamSoup()).thenReturn(700);
        when(rc.senseElevation(rc.getLocation())).thenReturn(1);
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.MINER, dir)).thenReturn(true);
        }
        HQtest.minerCount = 0;
        assertEquals(true, HQtest.buildMiner());
    }

    @Test public void HQTest_minerSucceedMax() throws GameActionException {
        MapLocation temp = new MapLocation(5,1);
        when(rc.getType()).thenReturn(RobotType.HQ);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeam()).thenReturn(Team.B);
        when(rc.getID()).thenReturn(821);
        when(rc.getRoundNum()).thenReturn(9);
        when(rc.getTeamSoup()).thenReturn(700);
        when(rc.senseElevation(rc.getLocation())).thenReturn(1);
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.MINER, dir)).thenReturn(true);
        }
        HQtest.minerCount = 0;
        assertEquals(true, HQtest.buildMiner());
        assertEquals(true, HQtest.buildMiner());
        assertEquals(true, HQtest.buildMiner());
        assertEquals(true, HQtest.buildMiner());
        assertEquals(true, HQtest.buildMiner());
    }

    @Test public void HQTest_minerFail() throws GameActionException {
        MapLocation temp = new MapLocation(5,1);
        when(rc.getType()).thenReturn(RobotType.HQ);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeam()).thenReturn(Team.B);
        when(rc.getID()).thenReturn(821);
        when(rc.getRoundNum()).thenReturn(9);
        when(rc.getTeamSoup()).thenReturn(150);
        when(rc.senseElevation(rc.getLocation())).thenReturn(1);
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.MINER, dir)).thenReturn(true);
        }
        HQtest.minerCount = 5;
        assertEquals(false, HQtest.buildMiner());
    }

    @Test public void HQTest_MessageSucceed() throws GameActionException {
        MapLocation temp = new MapLocation(5,1);
        when(rc.getType()).thenReturn(RobotType.HQ);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeam()).thenReturn(Team.B);
        when(rc.getID()).thenReturn(821);
        when(rc.getRoundNum()).thenReturn(9);
        when(rc.getTeamSoup()).thenReturn(150);
        when(rc.senseElevation(rc.getLocation())).thenReturn(1);


        assertEquals(true, HQtest.knowHQ());
    }

    @Test public void HQTest_MessageFail() throws GameActionException {
        MapLocation temp = new MapLocation(5,1);
        when(rc.getType()).thenReturn(RobotType.HQ);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeam()).thenReturn(Team.B);
        when(rc.getID()).thenReturn(821);
        when(rc.getRoundNum()).thenReturn(11);
        when(rc.getTeamSoup()).thenReturn(150);
        when(rc.senseElevation(rc.getLocation())).thenReturn(1);

        assertEquals(false, HQtest.knowHQ());
    }
}
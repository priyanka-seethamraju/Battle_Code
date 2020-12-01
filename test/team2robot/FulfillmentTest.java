package team2robot;

import org.junit.Test;
import battlecode.common.*;
import team2robot.RobotPlayer;
import org.junit.*;
import org.mockito.*;

import java.lang.reflect.MalformedParameterizedTypeException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.when;

public class FulfillmentTest {

    private RobotController rc = null;
    private RobotPlayer r = null;
    private FulfillmentCenter FulfillmentTest =null;

    static Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST
    };


    @Before
    public void beforeEachTest() throws GameActionException {
        rc = Mockito.mock(RobotController.class);
        r = Mockito.mock(RobotPlayer.class);
        FulfillmentTest = new FulfillmentCenter(rc);
    }

    @Test
    public void testRunFulfillmentBuildOne() throws GameActionException{
        MapLocation temp = new MapLocation(5,5);
        when(rc.getType()).thenReturn(RobotType.DESIGN_SCHOOL);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeam()).thenReturn(Team.B);
        when(rc.getID()).thenReturn(821);
        when(rc.getRoundNum()).thenReturn(9);
        when(rc.getTeamSoup()).thenReturn(400);
        when(rc.senseElevation(rc.getLocation())).thenReturn(1);
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);
        }

        assertTrue(FulfillmentTest.buildDrone());
    }


    @Test
    public void testRunFulfillmentNoBuild() throws GameActionException{
        MapLocation temp = new MapLocation(5,5);
        when(rc.getType()).thenReturn(RobotType.DESIGN_SCHOOL);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeam()).thenReturn(Team.B);
        when(rc.getID()).thenReturn(821);
        when(rc.getRoundNum()).thenReturn(9);
        when(rc.getTeamSoup()).thenReturn(10);
        when(rc.senseElevation(rc.getLocation())).thenReturn(1);
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.DELIVERY_DRONE, dir)).thenReturn(false);
        }

        assertFalse(FulfillmentTest.buildDrone());
    }

    @Test
    public void testRunFulfillment() throws GameActionException{
        MapLocation temp = new MapLocation(5,5);
        when(rc.getType()).thenReturn(RobotType.DESIGN_SCHOOL);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeam()).thenReturn(Team.B);
        when(rc.getID()).thenReturn(821);
        when(rc.getRoundNum()).thenReturn(9);
        when(rc.getTeamSoup()).thenReturn(400);
        when(rc.senseElevation(rc.getLocation())).thenReturn(1);
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.DELIVERY_DRONE, dir)).thenReturn(false);
        }

        FulfillmentTest.runFulfillmentCenter();
    }
}
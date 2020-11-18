package team2robot;

import battlecode.common.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


public class RefineryTest {

    private RobotController rc = null;
    private RobotPlayer r = null;
    private Refinery RefineryTest =null;

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
    public void beforeEachTest() throws GameActionException{
        rc = Mockito.mock(RobotController.class);
        r = Mockito.mock(RobotPlayer.class);
        RefineryTest = new Refinery(rc);
    }

    @Test
    public void testRunRefinery() throws GameActionException{
        when(rc.getSoupCarrying()).thenReturn(25);
        MapLocation temp = new MapLocation(5,5);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.sensePollution(temp)).thenReturn(200);
        for(Direction dir : directions ){
            when(rc.isReady()).thenReturn(true);
            when(rc.canMineSoup(dir)).thenReturn(true);
            rc.mineSoup(dir);
        }

        RefineryTest.takeTurn();
        RefineryTest.runRefinery();

    }

}
package team2robot;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;


public class VaporatorTest {

    private RobotController rc = null;
    private RobotPlayer r = null;
    private Vaporator VaporatorTest =null;


    @Before
    public void beforeEachTest() throws GameActionException{
        rc = Mockito.mock(RobotController.class);
        r = Mockito.mock(RobotPlayer.class);
        VaporatorTest = new Vaporator(rc);
    }

    @Test
    public void testRunVaporator() throws GameActionException{
        when(rc.getTeamSoup()).thenReturn(500);
        VaporatorTest.takeTurn();
        VaporatorTest.runVaporator();

    }

}
package team2robot;

import org.junit.Test;
import battlecode.common.*;
import team2robot.RobotPlayer;
import org.junit.*;
import org.mockito.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RobotTest {
    private RobotController rc = null;
    private RobotPlayer r = null;


    @Before
    public void beforeEachTest() throws GameActionException{
        rc = Mockito.mock(RobotController.class);
        r = Mockito.mock(RobotPlayer.class);
    }

    @Test
    public void tyrMoveTest() throws GameActionException{
        when(rc.canMove(Direction.NORTH)).thenReturn(true);
        when(rc.isReady()).thenReturn(true);
    }
}

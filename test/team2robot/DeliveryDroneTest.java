package team2robot;

import org.junit.Test;
import battlecode.common.*;
import team2robot.RobotPlayer;
import org.junit.*;
import org.mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;
import static team2robot.Robot.directions;
import static team2robot.Robot.randomDirection;
import battlecode.common.RobotType;

import java.util.Map;


public class DeliveryDroneTest {

    private RobotController rc = null;
    //private RobotPlayer r = null;
    private DeliveryDrone DeliveryDroneTest =null;


    @Before
    public void beforeEachTest() throws GameActionException{
        rc = Mockito.mock(RobotController.class);
        //r = Mockito.mock(RobotPlayer.class);
        DeliveryDroneTest = new DeliveryDrone(rc);
    }

    @Test
    public void testRunDeliveryDrone() throws GameActionException{
        DeliveryDroneTest.floodLoc = new MapLocation(10, 10);
        when(rc.getTeam()).thenReturn(Team.A);
        when(rc.getTeam().opponent()).thenReturn(Team.B);
        when(rc.getMapWidth()).thenReturn(10);
        when(rc.getMapHeight()).thenReturn(10);
        MapLocation temp = new MapLocation(1,1);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.senseFlooding(rc.getLocation())).thenReturn(true);
        when(rc.getRoundNum()).thenReturn(1);
        when(rc.isCurrentlyHoldingUnit()).thenReturn(true);
        Transaction [] t = new Transaction[1];
        int [] m = {0,0,0,0,0,0,0};
        t[0] = new Transaction(10, m, 123456789);
        when(rc.getBlock(8)).thenReturn(t);

        DeliveryDroneTest.knowHQ = true;
        DeliveryDroneTest.knowEnemyHQ = true;
        DeliveryDroneTest.knowFlood = true;

        DeliveryDroneTest.takeTurn();
        DeliveryDroneTest.runDeliveryDrone();

    }

    @Test
    public void DeliveryDroneTestToEnemyHQ() throws GameActionException{
        DeliveryDroneTest.enemyHQloc = new MapLocation(20, 20);
        MapLocation t = new MapLocation(5,5);
        when(rc.getLocation()).thenReturn(t);


        DeliveryDroneTest.headToEnemyHQ();

    }

    @Test
    public void DeliveryDroneTestToTeamHQ() throws GameActionException{
        DeliveryDroneTest.HQloc = new MapLocation(10, 10);
        MapLocation t = new MapLocation(5, 5);
        when(rc.getLocation()).thenReturn(t);

        DeliveryDroneTest.headToTeamHQ();

    }

    @Test
    public void DeliveryDroneTestGetFlood() throws GameActionException{
        DeliveryDroneTest.knowFlood = false;
        MapLocation t = new MapLocation(5, 5);
        when(rc.getLocation()).thenReturn(t);
        when(rc.senseFlooding(rc.getLocation())).thenReturn(true);

        DeliveryDroneTest.getFlood();

    }

    @Test
    public void DeliveryDroneTestdropCow() throws GameActionException{

        DeliveryDroneTest.enemyHQloc = new MapLocation(6,6);
        MapLocation t = new MapLocation(5, 5);
        when(rc.getLocation()).thenReturn(t);
        when(rc.canDropUnit(randomDirection())).thenReturn(true);

        DeliveryDroneTest.dropCow();

    }

    @Test
    public void DeliveryDroneTestpickUpLandscaper() throws GameActionException{
        MapLocation m = new MapLocation(5, 5);
        RobotInfo robot = new RobotInfo(12345, Team.A, RobotType.LANDSCAPER, 0, false,0,0, 0, m);
        DeliveryDroneTest.ls_count = 5;
        DeliveryDroneTest.enemyHQloc = new MapLocation(6,6);
        MapLocation t = new MapLocation(5, 5);
        when(rc.getLocation()).thenReturn(t);
        when(rc.canDropUnit(randomDirection())).thenReturn(true);
        when(rc.getTeam()).thenReturn(Team.A);

        RobotInfo [] robots = {robot};
        DeliveryDroneTest.pickUpLandscaper(robots);

    }

    @Test
    public void DeliveryDroneTestpickUpLandscaper2() throws GameActionException{
        MapLocation m = new MapLocation(5, 5);

        DeliveryDroneTest.HQloc = new MapLocation(6,6);
        MapLocation t = new MapLocation(5, 5);
        when(rc.getLocation()).thenReturn(t);
        when(rc.canDropUnit(randomDirection())).thenReturn(true);
        when(rc.getTeam()).thenReturn(Team.B);

        RobotInfo [] robots = {};
        DeliveryDroneTest.pickUpLandscaper(robots);

    }


    @Test
    public void DeliveryDroneTestpickUpEnemy() throws GameActionException{
        MapLocation m = new MapLocation(5, 5);
        RobotInfo robot = new RobotInfo(12345, Team.B, RobotType.LANDSCAPER, 0, false,0,0, 0, m);

        DeliveryDroneTest.HQloc = new MapLocation(6,6);
        MapLocation t = new MapLocation(5, 5);
        when(rc.getLocation()).thenReturn(t);
        when(rc.canDropUnit(randomDirection())).thenReturn(true);
        when(rc.getTeam()).thenReturn(Team.B);

        RobotInfo [] robots = {robot};
        DeliveryDroneTest.pickUpEnemy(robots);

    }

    @Test
    public void DeliveryDroneTestpickUpEnemy2() throws GameActionException{
        MapLocation m = new MapLocation(5, 5);
        RobotInfo robot = new RobotInfo(12345, Team.A, RobotType.LANDSCAPER, 0, false,0,0, 0, m);

        DeliveryDroneTest.HQloc = new MapLocation(6,6);
        MapLocation t = new MapLocation(5, 5);
        when(rc.getLocation()).thenReturn(t);
        when(rc.canDropUnit(randomDirection())).thenReturn(true);
        when(rc.getTeam()).thenReturn(Team.B);

        RobotInfo [] robots = {robot};
        DeliveryDroneTest.pickUpEnemy(robots);

    }

    @Test
    public void DeliveryDroneTestPickUpCow() throws GameActionException{
        MapLocation m = new MapLocation(5, 5);
        RobotInfo robot = new RobotInfo(12345, Team.NEUTRAL, RobotType.COW, 0, false,0,0, 0, m);

        DeliveryDroneTest.HQloc = new MapLocation(6,6);
        MapLocation t = new MapLocation(5, 5);
        when(rc.getLocation()).thenReturn(t);
        when(rc.canDropUnit(randomDirection())).thenReturn(true);
        when(rc.getTeam()).thenReturn(Team.B);

        RobotInfo [] robots = {robot};
        DeliveryDroneTest.pickUpCow(robots);

    }

}
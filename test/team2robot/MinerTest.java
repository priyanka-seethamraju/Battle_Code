package team2robot;

import org.junit.Test;
import battlecode.common.*;
import scala.collection.convert.Wrappers;
import team2robot.RobotPlayer;
import org.junit.*;
import org.mockito.*;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;
import static team2robot.Robot.directions;


public class MinerTest {

    private RobotController rc = null;
    //private RobotPlayer r = null;
    private Miner MinerTest =null;


    @Before
    public void beforeEachTest() throws GameActionException {
        rc = Mockito.mock(RobotController.class);
        //r = Mockito.mock(RobotPlayer.class);
        MinerTest = new Miner(rc);
    }

    @Test
    public void testRunMinerAllBuilt() throws GameActionException{
        MapLocation[] soup = new MapLocation[2];
        MapLocation temp = new MapLocation(5,5);
        soup[0] = new MapLocation(6,6);
        soup[1] = new MapLocation(7,7);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.senseNearbySoup()).thenReturn(soup);
        //when(rc.getBlock());
        MinerTest.HQloc = new MapLocation(2, 2);
        MinerTest.refineryBuilt = true;
        MinerTest.fulfillmentBuilt = true;
        MinerTest.designBuilt = true;
        MinerTest.vaporatorBuilt = true;
        MinerTest.netGunBuilt = true;

        MinerTest.runMiner();

    }

    @Test
    public void testRunMinerNoneBuilt() throws GameActionException{
        MapLocation[] soup = new MapLocation[2];
        MapLocation temp = new MapLocation(5,5);
        soup[0] = new MapLocation(6,6);
        soup[1] = new MapLocation(7,7);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeamSoup()).thenReturn(9999);
        when(rc.senseNearbySoup()).thenReturn(soup);
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.FULFILLMENT_CENTER, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.DESIGN_SCHOOL, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.REFINERY, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.VAPORATOR, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.NET_GUN, dir)).thenReturn(true);
        }

        MinerTest.HQloc = new MapLocation(2, 2);
        MinerTest.refineryBuilt = false;
        MinerTest.fulfillmentBuilt = false;
        MinerTest.designBuilt = false;
        MinerTest.vaporatorBuilt = false;
        MinerTest.netGunBuilt = false;

        MinerTest.runMiner();

    }
    @Test
    public void testRunMinerBuildVaporator() throws GameActionException{
        MapLocation[] soup = new MapLocation[2];
        MapLocation temp = new MapLocation(5,5);
        soup[0] = new MapLocation(6,6);
        soup[1] = new MapLocation(7,7);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeamSoup()).thenReturn(9999);
        when(rc.senseNearbySoup()).thenReturn(soup);
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.FULFILLMENT_CENTER, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.DESIGN_SCHOOL, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.REFINERY, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);

        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.NET_GUN, dir)).thenReturn(true);
        }

        MinerTest.HQloc = new MapLocation(2, 2);
        MinerTest.refineryBuilt = false;
        MinerTest.fulfillmentBuilt = false;
        MinerTest.designBuilt = false;
        MinerTest.vaporatorBuilt = true;
        MinerTest.netGunBuilt = false;

        MinerTest.runMiner();

    }

    @Test
    public void testRunMinerBuildFulfillmentCenter() throws GameActionException{
        MapLocation[] soup = new MapLocation[2];
        MapLocation temp = new MapLocation(5,5);
        soup[0] = new MapLocation(6,6);
        soup[1] = new MapLocation(7,7);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeamSoup()).thenReturn(9999);
        when(rc.senseNearbySoup()).thenReturn(soup);
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.FULFILLMENT_CENTER, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.DESIGN_SCHOOL, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.REFINERY, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);

        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.NET_GUN, dir)).thenReturn(true);
        }

        MinerTest.HQloc = new MapLocation(2, 2);
        MinerTest.refineryBuilt = false;
        MinerTest.fulfillmentBuilt = false;
        MinerTest.designBuilt = false;
        MinerTest.vaporatorBuilt = true;
        MinerTest.netGunBuilt = false;

        MinerTest.runMiner();

    }

    @Test
    public void testRunMinerBuildRefinery() throws GameActionException{
        MapLocation[] soup = new MapLocation[2];
        MapLocation temp = new MapLocation(5,5);
        soup[0] = new MapLocation(6,6);
        soup[1] = new MapLocation(7,7);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeamSoup()).thenReturn(9999);
        when(rc.senseNearbySoup()).thenReturn(soup);
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.FULFILLMENT_CENTER, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.DESIGN_SCHOOL, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.REFINERY, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);

        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.NET_GUN, dir)).thenReturn(true);
        }

        MinerTest.HQloc = new MapLocation(2, 2);
        MinerTest.refineryBuilt = false;
        MinerTest.fulfillmentBuilt = true;
        MinerTest.designBuilt = false;
        MinerTest.vaporatorBuilt = true;
        MinerTest.netGunBuilt = false;

        MinerTest.runMiner();

    }

    @Test
    public void testRunMinerBuildDesignSchool() throws GameActionException{
        MapLocation[] soup = new MapLocation[2];
        MapLocation temp = new MapLocation(5,5);
        soup[0] = new MapLocation(6,6);
        soup[1] = new MapLocation(7,7);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeamSoup()).thenReturn(9999);
        when(rc.senseNearbySoup()).thenReturn(soup);
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.FULFILLMENT_CENTER, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.DESIGN_SCHOOL, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.REFINERY, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);

        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.NET_GUN, dir)).thenReturn(true);
        }

        MinerTest.HQloc = new MapLocation(2, 2);
        MinerTest.refineryBuilt = true;
        MinerTest.fulfillmentBuilt = true;
        MinerTest.designBuilt = false;
        MinerTest.vaporatorBuilt = true;
        MinerTest.netGunBuilt = false;

        MinerTest.runMiner();

    }

    @Test
    public void testRunMinerBuildNetGun() throws GameActionException{
        MapLocation[] soup = new MapLocation[2];
        MapLocation temp = new MapLocation(5,5);
        soup[0] = new MapLocation(6,6);
        soup[1] = new MapLocation(7,7);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeamSoup()).thenReturn(9999);
        when(rc.senseNearbySoup()).thenReturn(soup);
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.FULFILLMENT_CENTER, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.DESIGN_SCHOOL, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.REFINERY, dir)).thenReturn(true);
        }
        when(rc.isReady()).thenReturn(true);

        when(rc.isReady()).thenReturn(true);
        for(Direction dir : directions ){
            when(rc.canBuildRobot(RobotType.NET_GUN, dir)).thenReturn(true);
        }

        MinerTest.HQloc = new MapLocation(2, 2);
        MinerTest.refineryBuilt = true;
        MinerTest.fulfillmentBuilt = true;
        MinerTest.designBuilt = true;
        MinerTest.vaporatorBuilt = true;
        MinerTest.netGunBuilt = false;

        MinerTest.runMiner();

    }
}

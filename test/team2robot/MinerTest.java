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


public class MinerTest {

    private RobotController rc = null;
    private RobotPlayer r = null;
    private Miner MinerTest =null;


    @Before
    public void beforeEachTest() throws GameActionException {
        rc = Mockito.mock(RobotController.class);
        r = Mockito.mock(RobotPlayer.class);
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

        MinerTest.refineryBuilt = true;
        MinerTest.fulfillmentBuilt = true;
        MinerTest.designBuilt = true;
        MinerTest.vaporatorBuilt = true;
        MinerTest.netGunBuilt = true;

        MinerTest.runMiner();

    }


}

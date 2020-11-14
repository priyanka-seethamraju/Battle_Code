package Team2Robot;
import examplefuncsplayer.RobotPlayerTest;
import org.junit.*;
import battlecode.common.*;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import org.mockito.mock.*;

//work in progress
public class MinerTest extends RobotPlayerTest {

    RobotController rc;
    Miner minerobj = Mockito.mock(Miner.class);

    @BeforeClass
    public  void testMessages() throws GameActionException{
        int[] message = new int[7];
        message[0] = 5;
        message[1] = 0;
        message[2] = 0;
        message[3] = 0;
        message[4] = 0;
        message[5] = 0;
        message[6] = 0;

        rc.submitTransaction(message, 1);
    }
    @Test
     public  void testRunMiner() throws  GameActionException
    {


        assert minerobj.vaporatorBuilt = true;
    }

//    public  void setMinerobj(RobotController minerobj) {
//        MinerTest.minerobj = minerobj;
//    }
}

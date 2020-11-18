package team2robot;

import org.junit.Test;
import battlecode.common.*;
import org.junit.*;
import org.mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;
import static team2robot.Robot.directions;


public class DesignSchoolTest {


    private RobotController rc = null;
    private DesignSchool DesignSchoolTest = null;


    @Before public void beforeEachTest() throws GameActionException{
        rc = Mockito.mock(RobotController.class);
        DesignSchoolTest = new DesignSchool(rc);
    }


    @Test public void DesignSchoolTest_BuildOneLandscaper() throws GameActionException{
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
            when(rc.canBuildRobot(RobotType.LANDSCAPER, dir)).thenReturn(true);
        }
        System.out.println("Soup is " + rc.getTeamSoup());

        assertEquals(true, DesignSchoolTest.buildLandscaper());
    }

    @Test public void DesignSchoolTest_NotEnoughSoup() throws GameActionException{
        MapLocation temp = new MapLocation(1,1);
        when(rc.getType()).thenReturn(RobotType.DESIGN_SCHOOL);
        when(rc.getLocation()).thenReturn(temp);
        when(rc.getTeam()).thenReturn(Team.B);
        when(rc.getID()).thenReturn(821);
        when(rc.getRoundNum()).thenReturn(9);
        when(rc.getTeamSoup()).thenReturn(10);
        when(rc.senseElevation(rc.getLocation())).thenReturn(1);
        when(rc.isReady()).thenReturn(true);
        System.out.println("Soup is " + rc.getTeamSoup());

        assertEquals(false, DesignSchoolTest.buildLandscaper());
    }

    @Test public void DesignSchoolTest_BuildMaxLandscapers() throws GameActionException{
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
            when(rc.canBuildRobot(RobotType.LANDSCAPER, dir)).thenReturn(true);
        }
        System.out.println("Soup is " + rc.getTeamSoup());
        DesignSchoolTest.landscaperCount = 0;
        assertEquals(true, DesignSchoolTest.buildLandscaper());
        assertEquals(true, DesignSchoolTest.buildLandscaper());
        assertEquals(false, DesignSchoolTest.buildLandscaper());
    }

    @Test public void DesignSchoolTest_AtMaxLandscapers() throws GameActionException{
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
            when(rc.canBuildRobot(RobotType.LANDSCAPER, dir)).thenReturn(true);
        }
        System.out.println("Soup is " + rc.getTeamSoup());
        DesignSchoolTest.landscaperCount = 2;
        assertEquals(false, DesignSchoolTest.buildLandscaper());
    }

    @Test public void DesignSchoolTest_takeTurn() throws GameActionException{
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
            when(rc.canBuildRobot(RobotType.LANDSCAPER, dir)).thenReturn(true);
        }
        System.out.println("Soup is " + rc.getTeamSoup());
        DesignSchoolTest.takeTurn();
    }
    @Test public void DesignSchoolTest_runDesignSchool() throws GameActionException{
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
            when(rc.canBuildRobot(RobotType.LANDSCAPER, dir)).thenReturn(true);
        }
        System.out.println("Soup is " + rc.getTeamSoup());

        DesignSchoolTest.runDesignSchool();
    }
}
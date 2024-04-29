import Hotel.Misc.DateProcessor;
import Hotel.Room.Marsha;
import org.junit.jupiter.api.*;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MakeReservationTestCase {



    @BeforeAll
    static void initAll() {
        System.out.println("initAll");



    }

    @BeforeEach
    void init() {
        System.out.println("initEach");

    }

    @Test
    public void testReservationID(){


        Integer resID  = 1000000049;

        assertEquals("TVRI1000000050",Marsha.generateReservationID(resID));
    }

    @Test
    public void testTimeConflict(){
        Date start1Date;
        Date end1Date;
        start1Date = DateProcessor.stringToDate("4/25/2024");
        end1Date = DateProcessor.stringToDate("5/25/2024");
        Date start2Date;
        Date end2Date;
        start2Date = DateProcessor.stringToDate("4/30/2024");
        end2Date = DateProcessor.stringToDate("5/20/2024");


        Calendar start1 = Calendar.getInstance();
        start1.setTime(start1Date);
        Calendar end1 = Calendar.getInstance();
        end1.setTime(end1Date);

        Calendar start2 = Calendar.getInstance();
        start2.setTime(start2Date);
        Calendar end2 = Calendar.getInstance();
        end2.setTime(end2Date);


        assertEquals(true, DateProcessor.isOverlapUsingCalendarAndDuration(start1,end1,start2,end2));
    }

    @Test
    public void testNoTimeConflict(){
        Date start1Date;
        Date end1Date;
        start1Date = DateProcessor.stringToDate("4/25/2024");
        end1Date = DateProcessor.stringToDate("5/25/2024");
        Date start2Date;
        Date end2Date;
        start2Date = DateProcessor.stringToDate("5/26/2024");
        end2Date = DateProcessor.stringToDate("6/15/2024");


        Calendar start1 = Calendar.getInstance();
        start1.setTime(start1Date);
        Calendar end1 = Calendar.getInstance();
        end1.setTime(end1Date);

        Calendar start2 = Calendar.getInstance();
        start2.setTime(start2Date);
        Calendar end2 = Calendar.getInstance();
        end2.setTime(end2Date);



        assertEquals(false, DateProcessor.isOverlapUsingCalendarAndDuration(start1,end1,start2,end2));
    }

    @Test
    public void testStartAndEndConflict() {
        Date start;
        Date end;
        start = DateProcessor.stringToDate("5/30/2024");
        end = DateProcessor.stringToDate("5/27/2024");
        assertEquals(false, DateProcessor.dateTimeConflict(start,end));

    }

    @AfterEach
    void tearDown() {
        System.out.println("afterEach");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("afterAll");
    }



}

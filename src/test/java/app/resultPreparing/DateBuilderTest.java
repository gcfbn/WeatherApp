package app.resultPreparing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateBuilderTest {

    @Test
    public void timeAddsZeroToMinutes() {
        long unix = 1620821152; // 12 - 05 - 2021 | 14:05:00
        String expectedResult = "14:05";
        String actualResult = DateBuilder.time(unix);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void timeMidnight(){
        long unix = 1620770430; // 12 - 05 - 2021 | 00:00:30
        String expectedResult = "0:00";
        String actualResult = DateBuilder.time(unix);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void timeNegativeValue(){
        long unix = -100;   // 100 seconds before 01 - 01 - 1970 | 00:00:00
        String expectedResult = "0:58";
        String actualResult = DateBuilder.time(unix);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testNoNeedToAddZeros(){
        long unix = 1620791415; // 12 - 05 - 2021 | 05:50:15
        String expectedResult = "5:50";
        String actualResult = DateBuilder.time(unix);
        assertEquals(expectedResult, actualResult);
    }

}
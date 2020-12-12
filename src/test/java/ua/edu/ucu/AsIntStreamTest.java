package ua.edu.ucu;

import ua.edu.ucu.stream.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;


public class AsIntStreamTest {
    private IntStream universalStream;
    private IntStream positiveStream;

    @Before
    public void init() {
        int[] intArr = {-8, -3, -2, -1, 0, 1, 2, 3};
        universalStream = AsIntStream.of(intArr);

        int[] positiveArr = {1, 5, 10, 15, 20};
        positiveStream = AsIntStream.of(positiveArr);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ofTest() {
        int [] emptyArr = {};
        AsIntStream.of(emptyArr);
    }

    /* -----------Test Average Method ----------- */
    @Test
    public void averageTest() {
        double result = universalStream.average();
        double expectedResult = -1;

        assertEquals(expectedResult, result, 0.0001);
    }

    @Test
    public void averagePositiveTest() {
        double result = positiveStream.average();
        double expectedResult = 10.2;

        assertEquals(expectedResult, result, 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void averageEmptyTest() {
        positiveStream.filter(x -> x < 0).average();
    }

    /* -----------Test Min/Max Method ----------- */
    @Test
    public void minTest() {
        int result = universalStream.min();
        int expectedResult = -8;

        assertEquals(expectedResult, result);
    }

    @Test
    public void minPositiveTest() {
        int result = positiveStream.min();
        int expectedResult = 1;

        assertEquals(expectedResult, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void minEmptyTest() {
        positiveStream.filter(x -> x < 0).min();
    }

    @Test
    public void maxTest() {
        int result = universalStream.max();
        int expectedResult = 3;

        assertEquals(expectedResult, result);
    }

    @Test
    public void maxPositiveTest() {
        int result = positiveStream.max();
        int expectedResult = 20;

        assertEquals(expectedResult, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void maxEmptyTest() {
        positiveStream.filter(x -> x < 0).max();
    }

    /* -----------Test Count Method ----------- */
    @Test
    public void countTest() {
        long result = universalStream.count();
        long expectedResult = 8;

        assertEquals(expectedResult, result);
    }

    @Test
    public void countEmptyTest() {
        long result = positiveStream.filter(x -> x < 0).count();
        long expectedResult = 0;

        assertEquals(expectedResult, result);
    }

    /* -----------Test Sum Method ----------- */
    @Test
    public void sumTest() {
        int result = universalStream.sum();
        int expectedResult = -8;

        assertEquals(expectedResult, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sumEmptyTest() {
        positiveStream.filter(x -> x < 0).sum();
    }

    /* -----------Test toArray Method ----------- */
    @Test
    public void toArrayTest() {
        int[] result = universalStream.filter(x -> x > 0).toArray();
        int[] expectedResult = {1, 2, 3};

        assertArrayEquals(expectedResult, result);
    }

    @Test
    public void toArrayEmptyTest() {
        int[] result = positiveStream.filter(x -> x < 0).toArray();
        int[] expectedResult = {};

        assertArrayEquals(expectedResult, result);
    }

    /* -----------Test Filter Method ----------- */
    @Test
    public void filterTest() {
        int[] result = universalStream.filter(x -> x < 0).toArray();
        int[] expectedResult = {-8, -3, -2, -1};

        assertArrayEquals(expectedResult, result);
    }

    @Test
    public void filterReturnEmptyStreamTest() {
        int[] result = positiveStream.filter(x -> x < 0).toArray();
        int[] expectedResult = {};

        assertArrayEquals(expectedResult, result);
    }

    /* -----------Test ForEach Method ----------- */
    @Test
    public void forEachTest() {
        StringBuilder str = new StringBuilder();

        universalStream.filter(x -> x < 0).forEach(str::append);
        String expectedResult = "-8-3-2-1";

        assertEquals(expectedResult, str.toString());
    }

    /* -----------Test Map Method ----------- */
    @Test
    public void mapTest() {
        int[] result = universalStream.map(x -> x * x).toArray();
        int[] expectedResult = {64, 9, 4, 1, 0, 1, 4, 9};

        assertArrayEquals(expectedResult, result);
    }

    @Test
    public void mapReturnEmptyStreamTest() {
        int[] result = positiveStream
                .filter(x -> x < 0)
                .map(x -> x * x).toArray();
        int[] expectedResult = {};

        assertArrayEquals(expectedResult, result);
    }

    /* -----------Test FlatMap Method ----------- */
    @Test
    public void flatMapTest() {
        int[] result = positiveStream
                .flatMap(x -> AsIntStream.of(x - 1, x, x + 1))
                .toArray();
        int[] expectedResult = {0, 1, 2, 4, 5, 6, 9, 10, 11, 14, 15,
                16, 19, 20, 21};

        assertArrayEquals(expectedResult, result);
    }

    @Test
    public void flatMapReturnEmptyStreamTest() {
        int[] result = positiveStream
                .filter(x -> x < 0)
                .map(x -> x * x)
                .toArray();
        int[] expectedResult = {};

        assertArrayEquals(expectedResult, result);
    }

    /* -----------Test Reduce Method ----------- */
    @Test
    public void reduceTest() {
        int result = positiveStream
                .flatMap(x -> AsIntStream.of(x - 1, x, x + 1))
                .reduce(7, (sum, x) -> sum += x);
        int expectedResult = 160;

        assertEquals(expectedResult, result);
    }

    @Test
    public void reduceEmptyStreamTest() {
        int result = positiveStream
                .filter(x -> x < 0)
                .map(x -> x * x)
                .reduce(0, (sum, x) -> sum += x);
        int expectedResult = 0;

        assertEquals(expectedResult, result);
    }
}
package ua.edu.ucu;

import ua.edu.ucu.stream.*;

public class StreamApp {

    public static int streamOperations(IntStream intStream) {
        //IntStream intStream = AsIntStream.of(-1, 0, 1, 2, 3); // input values
        return intStream
                .filter(x -> x > 0) // 1, 2, 3
                .map(x -> x * x) // 1, 4, 9
                .flatMap(x -> AsIntStream.of(x - 1, x, x + 1)) // 0, 1, 2, 3, 4, 5, 8, 9, 10
                .reduce(0, (sum, x) -> sum += x); // 42
    }

    public static int[] streamToArray(IntStream intStream) {        
        return intStream.toArray();
    }

    public static String streamForEach(IntStream intStream) {        
        StringBuilder str = new StringBuilder();
        intStream.forEach(str::append);
        return str.toString();
    }
}

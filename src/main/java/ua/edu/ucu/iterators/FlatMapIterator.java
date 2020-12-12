package ua.edu.ucu.iterators;

import ua.edu.ucu.function.IntToIntStreamFunction;
import ua.edu.ucu.stream.IntStream;

import java.util.Iterator;

public class FlatMapIterator implements Iterator<Integer> {
    private final Iterator<Integer> iterator;
    private Iterator<Integer> subStreamIterator;
    private final IntToIntStreamFunction func;

    public FlatMapIterator(Iterator<Integer> defaultIterator,
                           IntToIntStreamFunction func) {
        this.subStreamIterator = new StreamIterator();
        this.iterator = defaultIterator;
        this.func = func;
    }

    @Override
    public boolean hasNext() {
        if (!subStreamIterator.hasNext()) {
            if (iterator.hasNext()) {
                IntStream subStream = func.
                        applyAsIntStream(iterator.next());
                subStreamIterator = new StreamIterator(subStream.toArray());
                return true;
            } else {
                subStreamIterator = new StreamIterator();
            }
        } else {
            return true;
        }
        return false;
    }

    @Override
    public Integer next() {
        return subStreamIterator.next();
    }
}

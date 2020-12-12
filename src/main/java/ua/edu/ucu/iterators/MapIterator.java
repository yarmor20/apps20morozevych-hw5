package ua.edu.ucu.iterators;

import ua.edu.ucu.function.IntUnaryOperator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MapIterator implements Iterator<Integer> {
    private final Iterator<Integer> iterator;
    private final IntUnaryOperator map;
    private boolean endOfIteration = false;
    private int next;

    public MapIterator(Iterator<Integer> defaultIterator,
                       IntUnaryOperator map) {
        this.iterator = defaultIterator;
        this.map = map;
        this.next = next();
    }

    @Override
    public boolean hasNext() {
        if (this.iterator.hasNext()) {
            endOfIteration = false;
            this.next = this.map.apply(this.iterator.next());
            return true;
        }
        endOfIteration = true;
        return false;
    }

    @Override
    public Integer next() {
        if (endOfIteration) {
            throw  new NoSuchElementException("No such element.");
        }
        return this.next;
    }
}

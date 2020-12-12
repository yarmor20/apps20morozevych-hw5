package ua.edu.ucu.iterators;

import ua.edu.ucu.function.IntPredicate;
import ua.edu.ucu.function.IntUnaryOperator;

import java.util.Iterator;

public class MapIterator implements Iterator<Integer> {
    private final Iterator<Integer> iterator;
    private final IntUnaryOperator map;
    private int next;

    public MapIterator(Iterator<Integer> defaultIterator, IntUnaryOperator map) {
        this.iterator = defaultIterator;
        this.map = map;
        this.next = next();
    }

    @Override
    public boolean hasNext() {
        if (this.iterator.hasNext()) {
            this.next = this.map.apply(this.iterator.next());
            return true;
        }
        return false;
    }

    @Override
    public Integer next() {
        return this.next;
    }
}

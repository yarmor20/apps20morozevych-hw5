package ua.edu.ucu.iterators;

import ua.edu.ucu.function.IntPredicate;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FilterIterator implements Iterator<Integer> {
    private final Iterator<Integer> iterator;
    private final IntPredicate filter;
    private boolean endOfIteration = false;
    private int next;

    public FilterIterator(Iterator<Integer> defaultIterator,
                          IntPredicate predicate) {
        this.iterator = defaultIterator;
        this.filter = predicate;
    }

    @Override
    public boolean hasNext() {
        while (this.iterator.hasNext()) {
            this.endOfIteration = false;
            this.next = this.iterator.next();
            if (this.filter.test(this.next)) {
                return true;
            }
        }
        this.endOfIteration = true;
        return false;
    }

    @Override
    public Integer next() {
        if (endOfIteration) {
            throw new NoSuchElementException("No next element.");
        }
        return this.next;
    }
}

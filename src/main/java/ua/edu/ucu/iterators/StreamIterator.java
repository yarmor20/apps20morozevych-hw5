package ua.edu.ucu.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StreamIterator implements Iterator<Integer> {
    private final List<Integer> values;
    private int currentPosition = 0;
    private int length = 0;

    public StreamIterator() {
        this.values = new ArrayList<>();
    }

    public StreamIterator(int... values) {
        this.values = new ArrayList<>();

        for (int value: values) {
            this.values.add(value);
            length++;
        }
    }

    @Override
    public boolean hasNext() {
        if (currentPosition < this.length) {
            return true;
        }
        currentPosition = 0;
        return false;
    }

    @Override
    public Integer next() {
        if (this.hasNext()) {
            return values.get(currentPosition++);
        }
        return null;
    }
}

package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;
import ua.edu.ucu.iterators.FilterIterator;
import ua.edu.ucu.iterators.FlatMapIterator;
import ua.edu.ucu.iterators.MapIterator;
import ua.edu.ucu.iterators.StreamIterator;

import java.util.Comparator;
import java.util.Iterator;

public class AsIntStream implements IntStream {
    private final Iterator<Integer> itemIterator;

    private AsIntStream(Iterator<Integer> iterator) {
        this.itemIterator = iterator;
    }

    public static IntStream of(int... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException(
                    "Cannot process empty data stream.");
        }
        return new AsIntStream(new StreamIterator(values));
    }

    private void checkIfEmpty() throws IllegalArgumentException {
        if (!itemIterator.hasNext()) {
            throw new IllegalArgumentException("The stream is empty.");
        }
    }

    @Override
    public Double average() {
        return (double) sum() / count();
    }

    private int findSpecialItem(Comparator<Integer> comparator,
                                int toCompare) {
        while (itemIterator.hasNext()) {
            toCompare = comparator.compare(toCompare, itemIterator.next());
        }
        return toCompare;
    }

    @Override
    public Integer max() {
        checkIfEmpty();
        Comparator<Integer> maxComparator = (o1, o2) -> (o2 > o1) ? o2: o1;
        return findSpecialItem(maxComparator, Integer.MIN_VALUE);
    }

    @Override
    public Integer min() {
        checkIfEmpty();
        Comparator<Integer> minComparator = (o1, o2) -> (o2 < o1) ? o2: o1;
        return findSpecialItem(minComparator, Integer.MAX_VALUE);
    }

    @Override
    public long count() {
        long size = 0;
        while (itemIterator.hasNext()) {
            itemIterator.next();
            size++;
        }
        return size;
    }

    @Override
    public Integer sum() {
        checkIfEmpty();
        return reduce(0, (sum, x) -> sum += x);
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        return new AsIntStream(new FilterIterator(itemIterator, predicate));
    }

    @Override
    public void forEach(IntConsumer action) {
        while (this.itemIterator.hasNext()) {
            action.accept(this.itemIterator.next());
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        return new AsIntStream(new MapIterator(itemIterator, mapper));
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        return new AsIntStream(new FlatMapIterator(itemIterator, func));
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int res = identity;
        while (this.itemIterator.hasNext()) {
            res = op.apply(res, this.itemIterator.next());
        }
        return res;
    }

    @Override
    public int[] toArray() {
        int[] items = new int[(int) count()];

        int index = 0;
        while (itemIterator.hasNext()) {
            items[index++] = itemIterator.next();
        }
        return items;
    }

}

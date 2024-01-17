package iterator;

import java.util.Iterator;
import java.util.List;

public class GenericIterator<T> implements Iterator<T> {

    private final Iterator<T> iterator;

    public GenericIterator(List<T> list) {
        this.iterator = list.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        return iterator.next();
    }

}

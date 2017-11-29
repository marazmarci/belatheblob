package hu.marazmarci.belatheblob.utils;

import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.Prog3HF_JavaDoc_TODO;

import java.util.EmptyStackException;
import java.util.Stack;

@Prog3HF
@Prog3HF_JavaDoc_TODO
public class DoublePeekableStack<T> extends Stack<T> {

    public synchronized T peekSecond() {
        int len = size();
        if (len < 2)
            throw new EmptyStackException();
        return elementAt(len - 2);
    }

}

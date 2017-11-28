package hu.marazmarci.belatheblob.utils;

import hu.marazmarci.belatheblob.Prog3HF;

import java.util.EmptyStackException;
import java.util.Stack;

@Prog3HF
public class DoublePeekableStack<T> extends Stack<T> {

    public synchronized T peekSecond() {
        int len = size();
        if (len < 2)
            throw new EmptyStackException();
        return elementAt(len - 2);
    }

}

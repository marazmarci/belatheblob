package hu.marazmarci.belatheblob.utils;

import hu.marazmarci.belatheblob.Prog3HF;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Stack osztály, aminek a legfelső elemén kívül a legfelső alatti is lekérdezhető
 *
 * @param <T> a tárolt típus
 */
@Prog3HF
public class DoublePeekableStack<T> extends Stack<T> {

    /**
     * Visszaadja a stack teteje alatti elemet
     *
     * @return felülről a 2. elem
     */
    public synchronized T peekSecond() {
        int len = size();
        if (len < 2)
            throw new EmptyStackException();
        return elementAt(len - 2);
    }

}

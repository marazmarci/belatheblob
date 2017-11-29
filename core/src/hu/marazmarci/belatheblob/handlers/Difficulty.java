package hu.marazmarci.belatheblob.handlers;

import hu.marazmarci.belatheblob.Prog3HF;

/**
 * A játék nehézségi szintjét reprezentáló felsorolt típus.
 *
 * Az nehézségi szintek jellemzői:
 *  - Easy: A nyulak mindig random irányba ugranak.
 *  - Hard: A nyulak mindig Béla irányába ugranak (az X-tengely mentén).
 *
 */
@Prog3HF
public enum Difficulty {
    EASY, HARD
}

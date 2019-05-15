package com.ajoscram.flappy_bird;

public interface Movable {
    enum Direction {X, Y, BOTH}

    void accelerate(float amount);
    void move(Direction direction);
    void stop();
    void reset();
}
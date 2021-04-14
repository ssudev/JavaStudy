package com.java8.chapter2;

/*
 * 초록색 사과만 선택하는 Predicate
 */
public class AppleGreenColorPredicate implements ApplePredicate{
    @Override
    public boolean test(Apple apple) {
        return apple.getColor().equals(Color.GREEN);
    }
}

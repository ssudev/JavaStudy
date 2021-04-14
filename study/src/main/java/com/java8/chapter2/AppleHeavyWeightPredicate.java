package com.java8.chapter2;
/*
 * 무거운 사과만 선택하는 Predicate
 */
public class AppleHeavyWeightPredicate implements ApplePredicate{
    @Override
    public boolean test(Apple apple) {
        return apple.getWeight() > 100;
    }
}
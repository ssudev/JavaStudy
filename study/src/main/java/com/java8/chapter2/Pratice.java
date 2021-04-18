package com.java8.chapter2;

import java.lang.Comparable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Pratice {

    public static void main(String[] args) {
        List<Apple> apples = new ArrayList<>();

        filterApples(apples, Color.GREEN);

        // 익명클래스를 사용
        List<Apple> redApples = filterApples(apples, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return Color.RED.equals(apple.getColor());
            }
        });

        // 람다 사용
        List<Apple> result = filterApples(apples, (Apple apple) -> Color.RED.equals(apple.getColor()));

        // Comparator를 이용한 정렬
        apples.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight2().compareTo(o2.getWeight2());
            }
        });

        // 람다식을 이용한 정렬
        apples.sort( (Apple a1, Apple a2) -> a1.getWeight() - a2.getWeight() );
        
        for(Apple apple:apples){
            System.out.println(apple.getColor() + apple.getWeight());
        }
    }

    public static List<Apple> filterGreenApples(List<Apple> inventory){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory){
            if(Color.GREEN.equals(apple.getColor())){
                result.add(apple);
            }
        }

        return result;
    }

    // 색을 파라미터로 추가했을 경우
    public static List<Apple> filterApples(List<Apple> inventory, Color color){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory){
            if(color.equals(apple.getColor())){
                result.add(apple);
            }
        }

        return result;
    }
    
    // 무게 정보를 추가하는 경우
    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory){
            if(apple.getWeight() > weight)
                result.add(apple);
        }

        return result;
    }

    /*
     * 완전 형편없는 코드
     * flag의 true/false가 뭘 의미하는지도 모른다.
     * 요구사항이 바뀌었을때 유연하게 대응할수도 없다.
     */
    public static List<Apple> filterApples(List<Apple> inventory, Color color, int weight, boolean flag){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory){
            if( (flag && apple.getColor().equals(color)) || (!flag && apple.getWeight() > weight) )
                result.add(apple);
        }

        return result;
    }

    // 추상적 조건으로 필터링
    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory){
            if(p.test(apple))
                result.add(apple);
        }

        return result;
    }

    // 리스트 형식으로 추상화
    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for(T e : list){
            if(p.test(e))
                result.add(e);
        }

        return result;
    }
}

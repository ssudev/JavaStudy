package com.java8.chapter6;

import com.java8.chapter4.Dish;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Pratice {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );

        long howManyDishes = menu.stream().collect(counting());
        System.out.println("howManyDishes = " + howManyDishes);

        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCalorieDish = menu.stream().collect(maxBy(dishCaloriesComparator));

        System.out.println("mostCalorieDish = " + mostCalorieDish);

        int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println("totalCalories = " + totalCalories);

        double avgCalories = menu.stream().collect(averagingDouble(Dish::getCalories));
        System.out.println("avgCalories = " + avgCalories);

        IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println("menuStatistics = " + menuStatistics);

        String shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));
        System.out.println("shortMenu = " + shortMenu);

        // reducing은 인수 세 개를 받는다.
        totalCalories = menu.stream().collect(reducing(0, Dish::getCalories, (i,j) -> i+j));
        totalCalories = menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));

        mostCalorieDish = menu.stream().collect(reducing((d1,d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));


        // 그룹핑
        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
        System.out.println("dishesByType = " + dishesByType);

        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream().collect(
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if(dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                })
        );
        System.out.println("dishesByCaloricLevel = " + dishesByCaloricLevel);

        Map<Dish.Type, List<Dish>> caloricDishesByType = menu.stream().filter(dish -> dish.getCalories() > 500).collect(groupingBy(Dish::getType));
        System.out.println("caloricDishesByType = " + caloricDishesByType);

        caloricDishesByType = menu.stream().collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));
        System.out.println("caloricDishesByType = " + caloricDishesByType);

        Map<Dish.Type, List<String>> dishNamesByType = menu.stream().collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
        System.out.println("dishNamesByType = " + dishNamesByType);

        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = menu.stream().collect(
                groupingBy(Dish::getType,
                        groupingBy(dish -> {
                            if(dish.getCalories() <= 400)
                                return CaloricLevel.DIET;
                            else if(dish.getCalories() <= 700)
                                return CaloricLevel.NORMAL;
                            else
                                return CaloricLevel.FAT;
                        }))
        );
        System.out.println("dishesByTypeCaloricLevel = " + dishesByTypeCaloricLevel);

        Map<Dish.Type, Long> typesCount = menu.stream().collect(groupingBy(Dish::getType, counting()));
        System.out.println("typesCount = " + typesCount);

        Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream().collect(groupingBy(Dish::getType, maxBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println("mostCaloricByType = " + mostCaloricByType);

        Map<Dish.Type, Dish> mostCaloricByTypeTwo = menu.stream().collect(
                groupingBy(Dish::getType,
                collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)),
                Optional::get)));

        Map<Dish.Type, Integer> totalCaloriesByType = menu.stream().collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
        System.out.println("totalCaloriesByType = " + totalCaloriesByType);

        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType = menu.stream().collect(
                groupingBy(Dish::getType, mapping(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }, toSet()))
        );
        System.out.println("caloricLevelsByType = " + caloricLevelsByType);

        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByTypeTwo = menu.stream().collect(
                groupingBy(Dish::getType, mapping(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }, toCollection(HashSet::new)))
        );
        System.out.println("caloricLevelsByTypeTwo = " + caloricLevelsByTypeTwo);

        // 분할함수
        Map<Boolean, List<Dish>> partitionedMenu = menu.stream().collect(partitioningBy(Dish::isVegetarian));
        System.out.println("partitionedMenu = " + partitionedMenu);
        List<Dish> vegetrianDishes = partitionedMenu.get(true);
        vegetrianDishes = menu.stream().filter(Dish::isVegetarian).collect(toList());

        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishedByType = menu.stream().collect(
                partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType))
        );
        System.out.println("vegetarianDishedByType = " + vegetarianDishedByType);
    }

    public static boolean isPrime(int candidate){
        return IntStream.range(2, candidate).noneMatch(i -> candidate % i == 0);
    }

    public static boolean isPrimeTwo(int candidate){
        int candidateRoot = (int)Math.sqrt((double)candidate);
        return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> candidate % i == 0);
    }

    public static Map<Boolean, List<Integer>> partitionPrimes(int n){
        return IntStream.rangeClosed(2,n).boxed().collect(partitioningBy(candidate -> isPrime(candidate)));
    }

    public static enum CaloricLevel { DIET, NORMAL, FAT }
}

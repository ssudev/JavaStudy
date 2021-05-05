package com.java8.chapter5;

import com.java8.chapter4.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class Chapter5 {
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
        
        List<Dish> vegetarianMenu = menu.stream().filter(Dish::isVegetarian).distinct().collect(Collectors.toList());
        System.out.println("vegetarianMenu = " + vegetarianMenu);

        List<Dish> specialMenu = Arrays.asList(
                new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER)
        );

        List<Dish> filteredMenu = specialMenu.stream().takeWhile(dish -> dish.getCalories() < 320).collect(Collectors.toList());
        System.out.println("filteredMenu = " + filteredMenu);
        
        List<Dish> sliceMenu2 = specialMenu.stream().dropWhile(dish -> dish.getCalories() < 320).collect(Collectors.toList());
        System.out.println("sliceMenu2 = " + sliceMenu2);

        List<Dish> dishes = specialMenu.stream().filter(dish -> dish.getCalories() > 300).limit(3).collect(Collectors.toList());
        System.out.println("dishes = " + dishes);
        
        dishes = specialMenu.stream().filter(dish -> dish.getCalories() > 300).skip(2).collect(Collectors.toList());
        System.out.println("dishes = " + dishes);
        
        List<Integer> dishLength = specialMenu.stream().map(Dish::getName).map(String::length).collect(Collectors.toList());
        System.out.println("dishLength = " + dishLength);

        List<String> words = Arrays.asList("Hello", "World");
        List<String> str = words.stream().map(word -> word.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());

        if(menu.stream().anyMatch(Dish::isVegetarian)){
            System.out.println("hi");
        }

        boolean isHealthy = menu.stream().allMatch(dish -> dish.getCalories() < 1000);
        isHealthy = menu.stream().noneMatch(dish -> dish.getCalories() >= 1000);

        Optional<Dish> dish = menu.stream().filter(Dish::isVegetarian).findAny();
        dish.ifPresent(d-> System.out.println(d.getName()));

        List<Integer> someNumbers = Arrays.asList(1,2,3,4,5);

        Optional<Integer> num = someNumbers.stream().map(n -> n * n).filter(n -> n%3 == 0).findFirst();
        System.out.println(num.get());

        int product = someNumbers.stream().reduce(0, Integer::sum);
        Optional<Integer> max = someNumbers.stream().reduce(Integer::max);
        Optional<Integer> min = someNumbers.stream().reduce(Integer::min);
        System.out.println(product);

        int calories = menu.stream().mapToInt(Dish::getCalories).sum();
        OptionalInt maxCalories = menu.stream().mapToInt(Dish::getCalories).max();
        int maxC = maxCalories.orElse(1);
    }
}

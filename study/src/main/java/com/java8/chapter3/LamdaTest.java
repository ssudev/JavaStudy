package com.java8.chapter3;

import com.java8.chapter2.Apple;
import com.java8.chapter2.Predicate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class LamdaTest {
    public static void main(String[] args) throws IOException {

        Runnable r1 = () -> System.out.println("Hello World");

        process(()-> System.out.println("Hello World"));

        String oneLine = processFile( (BufferedReader br) -> br.readLine());
        String twoLine = processFile( (BufferedReader br) -> br.readLine() + br.readLine());

        forEach(
                Arrays.asList(1,2,3,4,5), (Integer i) -> System.out.println("i = " + i)
        );

        List<Integer> i = map(
          Arrays.asList("lambdas", "in", "action"), (String s) -> s.length()
        );

        List<String> str = Arrays.asList("a","b","c","d");
        str.sort((s1,s2) -> s1.compareToIgnoreCase(s2));

        str.sort(String::compareToIgnoreCase);

        ToIntFunction<String> stringToIntFunction = (String s) -> Integer.parseInt(s);
        stringToIntFunction = Integer::parseInt;

        BiPredicate<List<String> ,String> containes = (list, element) -> list.contains(element);
        containes = List::contains;

//        Predicate<String> startsWithNumber = (String string) -> this.s;


        List<Integer> weights = Arrays.asList(7,3,4,10);
        List<Apple> apples = map2(weights, Apple::new);
    }

    public static List<Apple> map2(List<Integer> list, Function<Integer, Apple> f){
        List<Apple> result = new ArrayList<>();
        for(Integer i :list){
            result.add(f.apply(i));
        }

        return result;
    }

    public static void process(Runnable r){
        r.run();
    }

    public static String processFile() throws IOException{
        try{
            BufferedReader br = new BufferedReader(new FileReader("data.txt"));
            return br.readLine();
        }catch (Exception e){
            return e.getLocalizedMessage();
        }
    }

    public static String processFile(BufferedReaderProcessor p) throws IOException{
        try{
            BufferedReader br = new BufferedReader(new FileReader("data.txt"));
            return p.process(br);
        }catch (Exception e){
            return e.getLocalizedMessage();
        }
    }

    public static <T> void forEach(List<T> list, Consumer<T> c){
        for(T t: list){
            c.accept(t);
        }
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> f){
        List<R> result = new ArrayList<>();
        for(T t : list){
            result.add(f.apply(t));
        }
        return result;
    }
}

package com.java8.chapter3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

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

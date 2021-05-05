package com.java8.chapter5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Pratice {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brain", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
          new Transaction(brian, 2011, 300),
          new Transaction(raoul, 2012, 1000),
          new Transaction(raoul, 2011, 400),
          new Transaction(mario, 2012, 710),
          new Transaction(mario, 2012, 700),
          new Transaction(alan, 2012, 950)
        );

        List<Transaction> sortTransactions = transactions.stream().filter(transaction -> transaction.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
        System.out.println(sortTransactions);

        List<String> distinctCitys = transactions.stream().map(Transaction::getTrader).map(Trader::getCity).distinct().collect(Collectors.toList());
        System.out.println("distinctCitys = " + distinctCitys);

        List<Trader> cambridgeTraders = transactions.stream().map(Transaction::getTrader).filter(trader -> trader.getCity().equals("Cambridge")).sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
        System.out.println("cambridgeTraders = " + cambridgeTraders);

        List<Trader> sortAlpa = transactions.stream().map(Transaction::getTrader).sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
        System.out.println("sortAlpa = " + sortAlpa);

        boolean milanFlag = transactions.stream().map(Transaction::getTrader).filter(trader -> trader.getCity().equals("Milan")).findAny().isPresent();
        System.out.println("milanFlag = " + milanFlag);

        int sumTranctions = transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge")).map(Transaction::getValue).reduce(0,Integer::sum);
        System.out.println("sumTranctions = " + sumTranctions);

        int maxTranction = transactions.stream().map(Transaction::getValue).reduce(0,Integer::max);
        int minTranction = transactions.stream().map(Transaction::getValue).reduce(0,Integer::min);
    }
}

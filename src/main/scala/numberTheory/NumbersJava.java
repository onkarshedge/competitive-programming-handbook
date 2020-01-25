package numberTheory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.BitSet;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class NumbersJava {
    private static MathContext mathContext = new MathContext(5, RoundingMode.CEILING);

    // can get maximum prime number which is less than (Long.Maxvalue)2 (square).
    private static Stream<BigDecimal> primesBigDecimal() {
        Predicate<BigDecimal> isPrime = number ->
                LongStream.rangeClosed(2, number.sqrt(mathContext).longValue())
                        .noneMatch(i -> BigDecimal.ZERO.compareTo(number.remainder(BigDecimal.valueOf(i))) == 0);

        return Stream
                .iterate(BigDecimal.valueOf(2), i -> i.add(BigDecimal.ONE))
                .filter(isPrime);
    }

    // this recursive stream does not work
    // because the seed also evaluated lazily
    private static Stream<Long> primesRec() {
        Predicate<Long> isPrime = number ->
                primesRec().takeWhile(prime -> prime <= Math.sqrt(number)).noneMatch(prime -> number % prime == 0);

        return Stream
                .iterate(2L, i -> i + 1)
                .filter(isPrime);
    }

    private static Stream<Long> primes() {
        Predicate<Long> isPrime = number ->
                LongStream.rangeClosed(2, (long) Math.sqrt(number)).noneMatch(i -> number % i == 0);

        return Stream
                .iterate(2L, i -> i < Long.MAX_VALUE, i -> i + 1)
                .filter(isPrime);
    }

    private static Stream<Integer> sieveOfEratosthenes() {
        int maxValue = Integer.MAX_VALUE;
        BitSet bitSet = new BitSet(maxValue);
        bitSet.set(0);
        bitSet.set(1);
        for (int i = 2; i <= Math.sqrt(maxValue); i++) {
            if (!bitSet.get(i))
                for (int prime = i * i; prime > 0 && prime < maxValue; prime = prime + i) {
                    bitSet.set(prime);
                }
        }
        bitSet.flip(0, maxValue);
        return bitSet.stream().boxed();
    }

    public static void main(String[] args) {
//        BigDecimal last = primesBigDecimal().skip(9999999999L).limit(1).findFirst().get();
//        System.out.println(primesRec().skip(9999).limit(1).findFirst().get());
        Integer tenThousandThPrime = sieveOfEratosthenes().skip(9999).limit(1).findFirst().get();
        System.out.println(tenThousandThPrime);
    }
}















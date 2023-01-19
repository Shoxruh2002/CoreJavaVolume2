package uz.sh.Chapter1_Streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 11/30/22 9:33 AM
 **/
public class Chapter1 {

    public static void main(String[] args) {
        List<String> words = new ArrayList<>() {{
            add("Hello");
            add("worldd");
            add("world");
        }};

        Stream<Stream<String>> streamStream = words.stream().map(Chapter1::codePoints); //[. . . ["y", "o", "u", "r"], ["b", "o", "a", "t"],
        Stream<String> stringStream = words.stream().flatMap(Chapter1::codePoints); // [. . . "y", "o", "u", "r", "b", "o", "a", "t", . . .],


//                     Extracting Substreams and Combining Streams


        Stream<Double> randoms = Stream.generate(Math::random).limit(100);

        Stream<String> initialDigits = codePoints("Hello123").takeWhile(
                s -> "0123456789".contains(s));// faqat raqamlarni ovoliw

        Stream<String> words2 = Stream.of("Hello world 123".split(" ")).skip(1);

        Stream<String> withoutInitialWhiteSpace = codePoints("Hello world").dropWhile(
                s -> s.trim().length() == 0);// probelni girs copiw

        Stream<String> combined = Stream.concat(
                codePoints("Hello"), codePoints("World")); // concatenate two streams


//                           Other Stream Transformations


        Stream<String> uniqueWords
                = Stream.of("merrily", "merrily", "merrily", "gently").distinct();// Only one "merrily" is retained


        Stream<String> sorted = words.stream().sorted(Comparator.comparing(String::length).reversed());

        Object[] powers = Stream.iterate(1.0, p -> p * 2)
                .peek(e -> System.out.println("Fetching " + e))
                .limit(20).toArray();


    }


    public static Stream<String> codePoints(String s) {
        var result = new ArrayList<String>();
        int i = 0;
        while (i < s.length()) {
            int j = s.offsetByCodePoints(i, 1);//s string ni i idexdan bowlab codePointOffset ca keyingi indexini oberadi
            result.add(s.substring(i, j));
            i = j;
        }
        return result.stream();
    }
}


class DownstreamCollectors {

    public static class City {
        private String name;
        private String state;
        private int population;

        public City(String name, String state, int population) {
            this.name = name;
            this.state = state;
            this.population = population;
        }

        public String getName() {
            return name;
        }

        public String getState() {
            return state;
        }

        public int getPopulation() {
            return population;
        }
    }

    public static Stream<City> readCities(String filename) throws IOException {

        return Files.lines(Paths.get(filename))
                .map(l -> l.split(", "))
                .map(a -> new City(a[0], a[1], Integer.parseInt(a[2])));

    }

    public static void main(String[] args) throws IOException {
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());

        Map<String, Set<Locale>> countryToLocaleSet = locales.collect(groupingBy(
                Locale::getCountry, toSet()));
        System.out.println("countryToLocaleSet: " + countryToLocaleSet);

        locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Long> countryToLocaleCounts = locales.collect(groupingBy(
                Locale::getCountry, counting()));
        System.out.println("countryToLocaleCounts: " + countryToLocaleCounts);

//        Stream<City> cities = readCities("cities.txt");
//        Map<String, Integer> stateToCityPopulation = cities.collect(groupingBy(
//                City::getState, summingInt(City::getPopulation)));
//        System.out.println("stateToCityPopulation: " + stateToCityPopulation);
//
//        cities = readCities("cities.txt");
//        Map<String, Optional<String>> stateToLongestCityName = cities
//                .collect(groupingBy(City::getState,
//                        mapping(City::getName, maxBy(Comparator.comparing(String::length)))));
//        System.out.println("stateToLongestCityName: " + stateToLongestCityName);

        locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Set<String>> countryToLanguages = locales.collect(groupingBy(
                Locale::getDisplayCountry, mapping(Locale::getDisplayLanguage, toSet())));
        System.out.println("countryToLanguages: " + countryToLanguages);

//        cities = readCities("cities.txt");
//        Map<String, IntSummaryStatistics> stateToCityPopulationSummary = cities
//                .collect(groupingBy(City::getState, summarizingInt(City::getPopulation)));
//        System.out.println(stateToCityPopulationSummary.get("NY"));
//
//        cities = readCities("cities.txt");
//        Map<String, String> stateToCityNames = cities.collect(groupingBy(
//                City::getState,
//                reducing("", City::getName, (s, t) -> s.length() == 0 ? t : s + ", " + t)));
//
//        cities = readCities("cities.txt");
//        stateToCityNames = cities.collect(groupingBy(City::getState,
//                mapping(City::getName, joining(", "))));
//        System.out.println("stateToCityNames: " + stateToCityNames);
    }
}

class ReductionOperations {

    public static void main(String[] args) {
        List<Integer> values = new ArrayList<>() {{
            add(1);
            add(2);
            add(3);
        }};
        Optional<Integer> sum = values.stream().reduce((x, y) -> x + y);// equals reduce(Integer::sum)
        sum.ifPresent(System.out::println);

/*

        Test bu

        BitSet bitSet = new BitSet();
        bitSet.set(1, true);
        System.out.println(bitSet.get(1));
        System.out.println(bitSet.length());
*/

        Integer sum2 = values.stream().reduce(12, (x, y) -> x + y);// Agr empty busa 12 ni quyib ketadi, bor busayam initial qiymatga 12 ni quyib quwib ketadi
        System.out.println("sum2 = " + sum2);
    }
}


class PrimitiveTypeStreams {


    public static void main(String[] args) {

        String str = "Nima Gap";

        IntStream intStream = str.codePoints();
        intStream.forEach(System.out::println);


        System.out.println("------------------------------------");


        Stream<String> words = Stream.of("Ali", "Vali", "Soli");
        IntStream lengths = words.mapToInt(String::length);
        lengths.forEach(System.out::println);

        System.out.println("------------------------------------");

//        To convert a primitive type stream to an object stream, use the boxed method:
        Stream<Integer> integers = IntStream.range(0, 100).boxed();

        System.out.println("------------------------------------");

        OptionalInt reduce = IntStream.range(0, 10).reduce(Integer::sum);
        IntSummaryStatistics intSummaryStatistics = IntStream.range(0, 10).summaryStatistics();
        System.out.println("intSummaryStatistics.toString() = " + intSummaryStatistics.toString());

        System.out.println("------------------------------------");

        SplittableRandom random = new SplittableRandom();
        IntStream ints = random.ints(10, 0, 1000);
        ints.forEach(System.out::println);

    }
}

class ParallelStreams {

    public static void main(String[] args) {
        Stream<String> words = Stream.of("Ali", "Vali", "Soli");

//        var shortWords = new int[12];
//        words.parallelStream().forEach(
//                s -> { if (s.length() < 12) shortWords[s.length()]++; });
// ERROR--race condition!
//        System.out.println(Arrays.toString(shortWords));

        Map<Integer, Long> shortWordCounts
                = words.parallel()
                .filter(s -> s.length() < 12)
                .collect(groupingBy(
                        String::length,
                        counting()));
        System.out.println(shortWordCounts.toString());

    }
}







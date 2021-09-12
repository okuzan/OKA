package PW5;

import java.util.Comparator;

@SuppressWarnings("Duplicates")
public class Robot implements Comparable<Robot> {

    String name;
    String code;
    int id;
    double age;

    public Robot(String name, String code, double age, int id) {
        this.name = name;
        this.code = code;
        this.id = id;
        this.age = age;
    }

    @Override
    public int compareTo(Robot o) {
        return 0;
    }

    public static class byAge implements Comparator<Robot> {

        @Override
        public int compare(Robot o1, Robot o2) {
            if (o2.age - o1.age > 0) return 1;
            if (o2.age - o1.age < 0) return -1;
            return 0;
        }
    }

    public static class byID implements Comparator<Robot> {

        @Override
        public int compare(Robot o1, Robot o2) {
            if (o2.id - o1.id > 0) return 1;
            if (o2.id - o1.id < 0) return -1;
            return 0;
        }
    }

    public static class byName implements Comparator<Robot> {

        @Override
        public int compare(Robot o1, Robot o2) {
//            if (o2.name - o1.name > 0) return 1;
//            if (o2.id - o1.id < 0) return -1;
            return o1.name.compareTo(o2.name);
        }
    }

    public static class byCode implements Comparator<Robot> {

        @Override
        public int compare(Robot o1, Robot o2) {
            return o1.code.compareTo(o2.code);
        }
    }

    @Override
    public String toString() {
        return "Name: " + name + ", code: " + code + ", id: " + id + ", age: " + age + "\n";
    }
}

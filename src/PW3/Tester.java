package PW3;

import java.util.Iterator;

@SuppressWarnings("Duplicates")
public class Tester {
    public static void main(String[] args) {

        Bag bag = new Bag();
        bag.add(3);
        bag.add(4);
        bag.add(5);

        Iterator d = bag.iterator();

        while (d.hasNext()){
            if((int) d.next() == 3){
                System.out.println("GOT");
                d.remove();
            }
        }
        for (Object o : bag) {
            System.out.println(o);
        }


    }
}

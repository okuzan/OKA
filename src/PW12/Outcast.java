package PW12;

public class Outcast {
    private final WordNet wn;

    public Outcast(WordNet wn) {
        this.wn = wn;
    }

    // маючи масив WordNet іменників, повернути «ізгоя»
    public String outcast(String[] nouns) {
        String outcast = null;
        int max = 0;

        for (String noun1 : nouns) {
            int dist = 0;
            for (String noun2 : nouns)
                dist += wn.distance(noun1, noun2);

            if (dist > max) {
                max = dist;
                outcast = noun1;
            }
        }
        return outcast;
    }
}

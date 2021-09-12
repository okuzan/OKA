package PW12;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("Duplicates")
public class WordNet {
    private SAP sap;
    private Map<Integer, String> idOfSets;
    private Map<String, Set<Integer>> wordofIDs;
    private Digraph dig;

    // конструктор приймає назви двох файлів

    public WordNet(String synsets, String hypernyms) throws Exception {

        getSynsets(synsets);
        dig = getHypernyms(hypernyms);
        sap = new SAP(dig);
    }

    private void getSynsets(String filename) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
        String line;

        idOfSets = new HashMap<>();
        wordofIDs = new HashMap<>();

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");

            int index = Integer.parseInt(data[0]);
            String[] words = data[1].split(" ");

            for (String word : words) {
                Set<Integer> ids = wordofIDs.get(word);

                if (ids == null)
                    ids = new HashSet<>();

                ids.add(index);
                wordofIDs.put(word, ids);
                idOfSets.put(index, word);
            }
        }
        br.close();
    }

    private Digraph getHypernyms(String filename) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
        String line;
        dig = new Digraph(idOfSets.size());

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            int id = Integer.parseInt(data[0]);
            for (int i = 1; i < data.length; i++) {
                int link = Integer.parseInt(data[i]);
                dig.addEdge(id, link);
            }
        }
        br.close();
        return dig;
    }

    // множина іменників, що повертається як ітератор (без дублікатів)
    public Iterable<String> nouns() {
        return wordofIDs.keySet();
    }

    // чи є слово серед WordNet іменників?
    public boolean isNoun(String word) {
        //check
        if (null == word || word.equals(""))
            return false;

        return wordofIDs.containsKey(word);
    }

    // відстань між nounA і nounB
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("No such nouns!");
        if (nounA.equals(nounB)) return 0;

        Set<Integer> idsOfNounA = wordofIDs.get(nounA);
        Set<Integer> idsOfNounB = wordofIDs.get(nounB);
        return sap.length(idsOfNounA, idsOfNounB);
    }

    // синсет що є спільним предком nounA і nounB
    // в найкоршому шляху до предка
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Both words must be nouns!");
        }
        Set<Integer> idsOfNounA = wordofIDs.get(nounA);
        Set<Integer> idsOfNounB = wordofIDs.get(nounB);
        int ancestor = sap.ancestor(idsOfNounA, idsOfNounB);
        return idOfSets.get(ancestor);
    }

    // тестування
    public static void main(String[] args) throws Exception {
        WordNet net = new WordNet("data/synsets.txt", "data/hypernyms.txt");
        System.out.println(net.distance("God", "narcoleptic"));
        Outcast out = new Outcast(net);
        String[] nouns = {"idiot", "God", "narcoleptic"};
        System.out.println(out.outcast(nouns));
    }
}
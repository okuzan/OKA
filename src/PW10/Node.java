package PW10;

class Node {

    //eng alphabet
    private final static int ALPH = 26;

    //ukr alphabet
    private final static int ALPH_U = 33;

    int count = 0;
    Node[] kids = new Node[ALPH];
    Node prev;

    Node() {
        for (int i = 0; i < ALPH; i++)
            kids[i] = null;
    }

    boolean isEnd = false;
}
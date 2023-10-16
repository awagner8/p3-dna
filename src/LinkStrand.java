public class LinkStrand implements IDnaStrand{

    private Node myFirst, myLast;
    private long mySize;
    private int myAppends;
    private int myIndex;
    private int myLocalIndex;
    private Node myCurrent;

    public LinkStrand() {
        this("");
    }

    public LinkStrand(String s) {
        initialize(s);
    }

    private class Node {
        String info;
        Node next;
        public Node(String s) {
            info = s;
            next = null;
        }
    }

    @Override
    public long size() {
        return mySize;
    }

    @Override
    public void initialize(String source) {
        myAppends = 0;
        myFirst = new Node(source);
        myLast = myFirst;
        mySize = source.length();
        myIndex = 0;
    }

    @Override
    public IDnaStrand getInstance(String source) {
        return new LinkStrand(source);
    }

    @Override
    public IDnaStrand append(String dna) {
        myAppends++;
        myLast.next = new Node(dna);
        myLast = myLast.next;
        mySize += dna.length();
        return this;
    }

    @Override
    public IDnaStrand reverse() {
        LinkStrand rev = new LinkStrand();
        Node current = myFirst;
        while(current != null){
            StringBuilder sb = new StringBuilder(current.info);
            sb.reverse();
            Node newNode = new Node(sb.toString());
            newNode.next = rev.myFirst;
            rev.myFirst = newNode;
            rev.mySize += newNode.info.length();
            current = current.next;
        }

        return rev;
    }

    @Override
    public int getAppendCount() {
        return myAppends;
    }

    @Override
    public String toString() {
        Node current = myFirst;
        StringBuilder sb = new StringBuilder();
        while (current != null) {
            sb.append(current.info);
            current = current.next;
        }
        return sb.toString();
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= mySize) {
            throw new IndexOutOfBoundsException();
        }
        
        if (index < myIndex) {
            myIndex = 0;
            myLocalIndex = 0;
            myCurrent = myFirst;
        }

        while (myIndex != index) {
            myIndex++;
            myLocalIndex++;
            if (myLocalIndex >= myCurrent.info.length()) {
                myLocalIndex = 0;
                myCurrent = myCurrent.next;
            }
        }

        return myCurrent.info.charAt(myLocalIndex);
        
    }

}
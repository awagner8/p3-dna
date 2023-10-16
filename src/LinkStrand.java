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
        myLocalIndex = 0;
        myCurrent = myFirst;
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
        rev.myLast = rev.myFirst;
        rev.myAppends = myAppends;
        rev.myIndex = 0;
        rev.myLocalIndex = 0;
        rev.myCurrent = rev.myFirst;
        rev.mySize = mySize;

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
        if (index>mySize-1 || index<0) {
                initialize(this.toString());
                throw new IndexOutOfBoundsException();
            }
        
        if (index>myIndex) {
            int start = myIndex-myLocalIndex;
            int stop = start+myCurrent.info.length()-1;
            while (stop<index) {
                myCurrent=myCurrent.next;
                myIndex= stop+1;
                myLocalIndex=0;
                start=myIndex;
                stop=start+myCurrent.info.length()-1;
            }
            return myCurrent.info.charAt(index-start);
        } else if (index< myIndex){
            int currentStart =0;
            int currentFinish = myFirst.info.length()-1;
            myCurrent=myFirst;
            while (currentFinish<index) {
                myCurrent=myCurrent.next;
                myIndex= currentFinish+1;
                myLocalIndex=0;
                currentStart=myIndex;
                currentFinish=currentStart+myCurrent.info.length()-1;
            }
            return myCurrent.info.charAt(index-currentStart);
        } else {
            return myCurrent.info.charAt(myLocalIndex);
        }

    }

}
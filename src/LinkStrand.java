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
        if (index>myIndex) {
            if (index>mySize-1) {
                initialize(this.toString());
                throw new IndexOutOfBoundsException();
            }
            int currentStart = myIndex-myLocalIndex;
            int currentFinish = currentStart+myCurrent.info.length()-1;
            while (currentFinish<index) {
                myCurrent=myCurrent.next;
                myIndex= currentFinish+1;
                myLocalIndex=0;
                currentStart=myIndex;
                currentFinish=currentStart+myCurrent.info.length()-1;
            }
            return myCurrent.info.charAt(index-currentStart);
        } else if (index< myIndex){
            if (index<0) {
                throw new IndexOutOfBoundsException();
            }
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
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
        myLast.next = new Node(dna);
        myLast = myLast.next;
        mySize += dna.length();
        myAppends++;
        return this;
    }

    @Override
    public IDnaStrand reverse() {
        
        LinkStrand rev = new LinkStrand();
        Node current = myFirst;
        while(current != null){
            StringBuilder sb = new StringBuilder(current.info);
            rev.appendFront(sb.reverse().toString());
            rev.mySize += sb.length();
            current = current.next;
        }

        return rev;
    }

    private void appendFront(String string) {
        Node tempNode = new Node(string);
        tempNode.next = myFirst;
        myFirst = tempNode;
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
        
        if(index == myIndex){
            return myCurrent.info.charAt(myLocalIndex);
        }

        if (index > myIndex) {
            int difference = index - myIndex;
            Node curr = myCurrent;

            while (myCurrent.info.length() - myLocalIndex <= difference) {
                difference -= myCurrent.info.length();
                difference += myLocalIndex;
                myLocalIndex = 0;
                myCurrent = myCurrent.next;
            }

            if (curr == myCurrent) {
                myLocalIndex += difference;
                myIndex = index;
                return myCurrent.info.charAt(myLocalIndex);
            }

            myIndex = index;
            myLocalIndex = difference;
            return myCurrent.info.charAt(myLocalIndex);
        }

        if (index < myIndex) {
            myLocalIndex = 0;
            int difference = index;
            myCurrent = myFirst;

            while (myCurrent.info.length() <= difference) {
                difference -= myCurrent.info.length();
                myCurrent = myCurrent.next;
            }

            myLocalIndex = difference;
            myIndex = index;
            return myCurrent.info.charAt(myLocalIndex);
        }
        return myCurrent.info.charAt(myLocalIndex);


        /*if (index>myIndex) {
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
        }*/

    }

}
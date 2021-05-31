// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
     
    public A1List() {
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key) {
        A1List temp = this.next;
        A1List curr = new A1List(address,size,key);
        this.next = curr;
        curr.prev= this;
        curr.next= temp;
        temp.prev = curr;
    
        return curr;
    }

    public boolean Delete(Dictionary d) {
        A1List curr = this;
        boolean found = false;

        while(curr.next!=null) {
            if(curr.key==d.key) {
                if(curr.address==d.address && curr.size==d.size) {
                    found = true;
                    A1List temp = curr.next;
                    curr.prev.next=temp;
                    temp.prev=curr.prev;
                    break;
                }
            }
            curr=curr.next;
        }

        curr=this;

        while(found==false && curr.prev!=null) {
            if(curr.key==d.key) {
                if(curr.address==d.address && curr.size==d.size) {
                    found = true;
                    A1List temp = curr.prev;
                    curr.next.prev=temp;
                    temp.next=curr.next;
                    break;
                }
            }
            curr=curr.prev;
        }   
     
        return found;
    }

    public A1List Find(int k, boolean exact)
    { 
        boolean found=false;
        A1List curr = this;
        while(curr.next!=null) {
            if((exact==true && curr.key==k ) || (exact==false && curr.key>=k)) {
                found=true;
                return curr;
            }
            curr=curr.next;
        }

        curr=this;
    
        while(found==false && curr.prev!=null) {
                    
            if(( exact==true && curr.key==k ) || (exact==false && curr.key>=k)) {
                found = true;
                return curr;
            }
            curr=curr.prev;
        }

        return null;
    }

    public A1List getFirst() {
        A1List curr = this;

        if(curr.prev==null && curr.next.next==null) { //empty
            return null;
        }

        else if(curr.next==null && curr.prev.prev==null) { //empty
            return null;
        }
        else { //not empty

            if(curr.prev!=null) //if not head
            {
                while(curr.prev.prev!=null) {
                    curr=curr.prev;
                }

            }

            else {
                curr= curr.next;
            }
    
            return curr;

        }

    }
    
    public A1List getNext() 
    {

        if(this.next.next!=null) {
            return this.next;
        }

        return null;
    }

    public boolean sanity()
    {
        boolean checkTail = true;
        boolean checkNextPrev = true;
        boolean checkNotCircular = true;
        boolean checkHead = true;

        A1List t=this, h=this;

        while(t!=null && h!=null && h.next!=null) {  //checking for loop in forward direction
            t=t.next;
            h=h.next.next;

            if(t==h) {
                checkNotCircular = false;
                break;
            }
        }

        t=this; 
        h=this; 

        while(t!=null && h!=null && h.prev!=null) {  //checking for loop in backward direction
            t=t.next;
            t=t.prev;
            h=h.prev.prev;

            if(t==h) {
                checkNotCircular = false;
                break;
            }
        }

        A1List curr = this.getFirst();

        while(curr!=null) {
            if(curr.next.prev!=curr) {
                checkNextPrev =false;
            }
            curr=curr.getNext();
        }

        curr = this.getFirst();

        if(curr!=null) {          //if not empty      

            if(curr.prev.prev!=null) {   //prev of head not null
                checkHead=false;
            }

            while(curr.getNext()!=null) { //while curr not equal to last element
                curr = curr.getNext();
            }

            if(curr.next.next!=null) { //next of tail not null
                checkTail=false;
            }
            
        }

        return (checkNextPrev && checkTail && checkNotCircular &&  checkHead);
    }

}



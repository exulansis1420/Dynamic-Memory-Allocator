// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }
    
    private boolean isGreater(BSTree b) { //returns true if current is 'greater' than b

        BSTree a=this;
        if(a.key>b.key) {
            return true;
        }

        else if(a.key==b.key) {
            return (a.address>b.address) ; //address can not be equal
        }

        else {
            return false;
        }
    }

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }

    public BSTree Insert(int address, int size, int key) {

        BSTree curr = this;

        while(curr.parent!=null) {  //traverse to sentinel root
            curr =curr.parent;
        }

        BSTree par = curr;          //parent of curr
        curr =curr.right;           //root
        BSTree temp = new BSTree(address,size,key);     //new Node

        if(curr!=null) {
            while(curr!=null) {             
                if(curr.isGreater(temp)) {         //if less then or equal then left
                    par=curr;
                    curr=curr.left;
                }

                else {                 
                    par=curr;
                    curr=curr.right;
                }

            }

            if(temp.isGreater(par)) {           
                par.right = temp;
                temp.parent = par;
            }

            else {
                par.left = temp;
                temp.parent = par;
            }
  
        }
        else {

            par.right=temp;
            par.left=null;
            temp.parent=par;

        }
        return temp;
    }

    private void deleteNode(BSTree curr) {
        if(curr.left==null && curr.right==null) { //debugged

            BSTree par = curr.parent;
            if(par.left==curr) {
                par.left=null;
            }
            else {
                par.right=null;
            }

            return;

        }

        else if(curr.left!=null && curr.right==null)  { //debugged
            BSTree par = curr.parent;
            if(par.left==curr) {
                par.left=curr.left;
                curr.left.parent =par;
            }

            else {
                par.right=curr.left;
                curr.left.parent=par;
            }

            return;

        }

        else if(curr.left==null && curr.right!=null) { //debugged
            BSTree par = curr.parent;
            if(par.left==curr) {
                par.left=curr.right;
                curr.right.parent =par;
            }

            else {
                par.right=curr.right;
                curr.right.parent=par;
            }

            return;   
        }

        else {
            BSTree succ = curr.getNext();

            if(succ!=null) {
                curr.key = succ.key;
                curr.address = succ.address;
                curr.size = succ.size;
            }

            deleteNode(succ);
        }

    }

    public boolean Delete(Dictionary e) { 
        BSTree curr = this;
        BSTree temp = new BSTree(e.address,e.size,e.key); //temporarily downcasting e 

        while(curr.parent!=null) {  //traverse to sentinel root
            curr =curr.parent;
        }

        curr=curr.right;

        while(curr!=null) {

            if(curr.key==e.key) {
                if(curr.address==e.address && curr.size == e.size) {
                    deleteNode(curr);
                    return true;
                }
            }

            if(temp.isGreater(curr)) {   
                curr=curr.right;
            }

            else {
                curr=curr.left;
            }

        }   
        return false;
    }
        
    public BSTree Find(int key, boolean exact) {
        BSTree curr = this;

        while(curr.parent!=null) {  //traverse to sentinel root
            curr =curr.parent;
        }

        curr=curr.right;
        BSTree res = null;

        while(curr!=null) {
            if(exact==true) {

                if(curr.key==key) {
                    res=curr;
                    curr=curr.left;
                }

                else if(curr.key>key){
                    curr=curr.left;
                }

                else {
                    curr=curr.right;
                }
   
            }

            else {

                if(curr.key>=key) {

                    res = curr;
                    curr= curr.left;
    
                }
    
                else if(curr.key<key) {
                    curr=curr.right;
                }
    
            }
  
        }

        return res;
    }

    public BSTree getFirst() { 
        BSTree curr = this;

        while(curr.parent!=null) {  //traverse to sentinel root
            curr =curr.parent;
        }

        curr=curr.right; 

        if(curr!=null)
        {
            while(curr.left!=null) {
                curr=curr.left;
            }
        }

        return curr;
    }

    public BSTree getNext() { 
        BSTree curr =this;

        if(curr.right!=null) {
            curr = curr.right;
            while(curr.left!=null) {
                curr = curr.left;
            }

            return curr;
        }

        else {
            
            while(curr.parent!=null) {

                if(curr.parent.left==curr) {
                    return curr.parent;
                }

                curr=curr.parent;

            }
        }
        
        return null;
    }

    private boolean checkBST_(BSTree root,int min,int max) {
        if(root==null) {
            return true;
        }
        
        else if(root.key<=min||root.key>=max) {
            return false;    
        }
        
        return (checkBST_(root.left,min,root.key) && checkBST_(root.right,root.key,max));
        
    }
    private boolean checkBST(BSTree root) {
        int pInf = Integer.MAX_VALUE;
        int nInf = Integer.MIN_VALUE;
        
        return checkBST_(root,nInf,pInf);
        
    }

    private boolean checkChildParent(BSTree root) {

        if(root.left==null&&root.right==null) {
            return true;
        }

        else if(root.left==null && root.right!=null) {
            if(root.right.parent!=root) {
                //System.out.println("a");
                return false;
            }

            return (checkChildParent(root.right));
        }

        else if(root.left!=null && root.right==null) {
            if(root.left.parent!=root) {
                //System.out.println("b");
                return false;
            }
            return (checkChildParent(root.left) );
        }

        else {
            if(root.left.parent!=root ||root.right.parent!=root) {
                //System.out.println("c");
                return false;
            }
            return (checkChildParent(root.left) && checkChildParent(root.right));
        }
    }
    
    public boolean sanity() { 
        boolean checkSentinel= true;
        boolean isBST = true;
        boolean checkchildParent =true;
        BSTree curr = this;


        while(curr.parent!=null) {  //traverse to sentinel root
            curr =curr.parent;
        }
        BSTree root = this.getFirst();
        
        if( curr.parent!=null) {
            checkSentinel=false;
        }

        if( curr.left!=null ) {
            checkSentinel=false;
        }


        if(checkBST(root)==false) {
            isBST=false;
        }

        if(checkChildParent(curr)==false) {
            checkchildParent=false;
        }

        return checkSentinel && checkchildParent && isBST;
        
    }

    // private void inorder() {
    //     BSTree root =this;

    //     if(root.left==null && root.right==null) {
    //         System.out.println(root.address+" "+root.size+" "+root.key);
    //         return;
    //     }

    //     else if(root.right==null && root.left!=null) {
    //         root.left.inorder();
    //         System.out.println(root.address+" "+root.size+" "+root.key);
            
    //     }

    //     else if(root.left==null && root.right!=null) {
    //         System.out.println(root.address+" "+root.size+" "+root.key);
    //         root.right.inorder();
    //     }

    //     else  {
    //         root.left.inorder();
    //         System.out.println(root.address+" "+root.size+" "+root.key);
    //         root.right.inorder();
    //     }
    // }
   
    // public static void main(String []args) {
    //     BSTree rootSentinel = new BSTree();
    //     //BSTree a = new BSTree(1,6,1);

    //     rootSentinel.Insert(0,0,4);
    //     rootSentinel.Insert(0,-1,2);
    //     rootSentinel.Insert(0,0,3);
    //     rootSentinel.Insert(0,-1,9);
    //     rootSentinel.Insert(0,0,6);
    //     rootSentinel.Insert(0,-1,14);
    //     rootSentinel.Insert(0,0,5);
    //     rootSentinel.Insert(0,-1,1);
    //     rootSentinel.Insert(0,0,7);
    //     rootSentinel.Insert(-7,-7,1);
    //     BSTree e = new BSTree(0,-1,5);
    //     rootSentinel.Delete(e);

    //     //BSTree x = rootSentinel.Find(8000,false);
    //     //BSTree b = new BSTree(1,0,4);
    //     //System.out.println(rootSentinel.Delete(a));

    //     //System.out.println(b.isGreater(a));

    //     BSTree root = rootSentinel.getFirst();
    //     // BSTree x = rootSentinel.right;
    //     // System.out.println(x.address+" "+x.size+" "+x.key);
    //     System.out.println(root.sanity());
    //     //root.inorder();

    //     // for (BSTree d = rootSentinel.getFirst(); d != null; d = d.getNext()) {
    //     //     System.out.println(d.address+" "+d.size+" "+d.key);
    //     // }

    //     // BSTree d= rootSentinel.right;
    //     // System.out.println(d.address+" "+d.size+" "+d.key);
    //     // d=d.right;
    //     // System.out.println(d.address+" "+d.size+" "+d.key);
    //     // d=d.parent.left;
    //     // System.out.println(d.address+" "+d.size+" "+d.key);
    // }

}


 



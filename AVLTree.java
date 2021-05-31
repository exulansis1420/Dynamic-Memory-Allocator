// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.

    private boolean isGreater(AVLTree b) { //returns true if current is 'greater' than b

        AVLTree a=this;
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

    private void updateHeights(AVLTree x) {
        
        while(x.parent!=null) {
            if(x.left==null && x.right==null) {
                x.height = 0;
                x=x.parent;
            }
            else if(x.left==null && x.right!=null) {
                x.height = Math.max(0,x.right.height+1);
                x=x.parent;
            }
            else if(x.left!=null && x.right==null) {
                x.height = Math.max(0,x.left.height+1);
                x=x.parent;
            }

            else if(x.left!=null && x.right!=null) {

                x.height = Math.max(x.left.height+1,x.right.height+1);   
                x=x.parent;

            }
        }
    }

    private AVLTree BSTreeInsert(int address,int size,int key) {

        AVLTree curr = this;

        while(curr.parent!=null) {  //traverse to sentinel root
            curr =curr.parent;
        }

        AVLTree par = curr;          //parent of curr
        curr =curr.right;           //root
        AVLTree temp = new AVLTree(address,size,key);     //new Node

        if(curr!=null) {
            while(curr!=null) {             
                if(curr.isGreater(temp)) {         //if less than or equal then left
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
        updateHeights(temp);
        return temp;

    }

    private int getBalance(AVLTree node) {
        if(node.left==null && node.right==null) {
            return 0;
        }
        else if(node.left==null && node.right!=null) {
            return node.right.height+1;
        }
        else if(node.left!=null && node.right==null) {
            return node.left.height+1;
        }
        else {
            return Math.abs(node.left.height-node.right.height);
        }
    }

    private void rightRotate(AVLTree a) {
        AVLTree b=a.left;
        AVLTree temp=null;

        if(b!=null) {
            temp=b.right;
            b.parent=a.parent;
        }
        if(a.parent.left==a) {
            a.parent.left=b;
        }
        else if(a.parent.right==a) {
            a.parent.right=b;
        }
        if(b!=null) {
            b.right=a;
        }
        a.parent=b;
        a.left=temp;
        if(temp!=null) {
            temp.parent=a;
        }
        updateHeights(a);
        
    }

    private void leftRotate(AVLTree a) {
        AVLTree b=a.right;

        AVLTree temp=null;

        if(b!=null) {
            temp=b.left;
            b.parent=a.parent;
        }
        
        if(a.parent.left==a) {
            a.parent.left=b;
        }
        else if(a.parent.right==a) {
            a.parent.right=b;
        }
        if(b!=null) {
            b.left=a;
        }
        a.parent=b;
        a.right=temp;
        if(temp!=null) {
            temp.parent=a;
        }
        updateHeights(a);
        
    }

    private void rebalance(AVLTree a, AVLTree b, AVLTree c) {
        if(a.left==b) {
            if(b.left==c) {
                //left-left
                rightRotate(a);
            }
            else if(b.right==c) {
                //left-right
                leftRotate(b);
                rightRotate(a);
            }
        }
        else if(a.right==b) {
            if(b.left==c) {
                //right-left
                rightRotate(b);
                leftRotate(a);
            }
            else if(b.right==c) {
                //right-right
                leftRotate(a);
            }
        }

    }

    private void checkBalance(AVLTree x) {
        AVLTree notBal =x;
        AVLTree notBalChild =null;
        AVLTree notBalGrandChild=null;
        boolean balanceNeeded =false;

        while(notBal.parent!=null) {
            if(notBal.left==null && notBal.right==null) {
                notBalGrandChild=notBalChild;
                notBalChild = notBal;
                notBal=notBal.parent;
            }
            
            else {
                if(getBalance(notBal)>1) {
                    balanceNeeded=true;
                    break;
                }
                notBalGrandChild=notBalChild;
                notBalChild=notBal;
                notBal=notBal.parent;
            }
        }

        if(balanceNeeded==true) {
            //System.out.println(notBal.key+","+notBalChild.key+","+notBalGrandChild.key);
            rebalance(notBal,notBalChild,notBalGrandChild);
        }

    }
    
    private AVLTree getDeeperChild(AVLTree notBal) {
        AVLTree notBalChild;
        if(notBal.left==null) {
            notBalChild= notBal.right;
        }
        else if (notBal.right==null){
            notBalChild = notBal.left;
        }
        else {
            if(notBal.left.height>=notBal.right.height) {
                notBalChild= notBal.left;
            }
            else {
                notBalChild= notBal.right;
            }
        }

        return notBalChild;
        
    }
    private void checkBalance_(AVLTree x) {

        AVLTree notBal =x;
        boolean balanceNeeded =false;

        while(notBal.parent!=null) {
            if(notBal.left==null && notBal.right==null) {
                
                notBal=notBal.parent;
            }
            else if(notBal.left!=null && notBal.right==null) {

                if(getBalance(notBal)>1) {
                    balanceNeeded=true;
                    break;
                }
                
                notBal=notBal.parent;
            }

            else if(notBal.left==null && notBal.right!=null) {
                if(getBalance(notBal)>1) {
                    balanceNeeded=true;
                    break;
                }
                
                notBal=notBal.parent;
            }

            else {
                if(getBalance(notBal)>1) {
                    balanceNeeded=true;
                    break;
                }
               
                notBal=notBal.parent;
            }
        }

        if(balanceNeeded==true) {
            //System.out.println(notBal.key+","+notBalChild.key+","+notBalGrandChild.key);
            
            AVLTree notBalChild = getDeeperChild(notBal);
            AVLTree notBalGrandChild = getDeeperChild(notBalChild);
            rebalance(notBal,notBalChild,notBalGrandChild);
    
        }

    }

    public AVLTree Insert(int address, int size, int key) { 
        AVLTree x = BSTreeInsert(address, size, key);
        checkBalance(x);
        return x;
    }

    private AVLTree deleteNode(AVLTree curr) { //RETURNS parent of removed node
        if(curr.left==null && curr.right==null) { //debugged
            //System.out.println("hat");
            AVLTree par = curr.parent;
            if(par.left==curr) {
                par.left=null;
            }
            else {
                par.right=null;
            }
            //System.out.println(par.key);

            return par;

        }

        else if(curr.left!=null && curr.right==null)  { //debugged
            AVLTree par = curr.parent;
            if(par.left==curr) {
                par.left=curr.left;
                curr.left.parent =par;
            }

            else {
                par.right=curr.left;
                curr.left.parent=par;
            }

            return par;

        }

        else if(curr.left==null && curr.right!=null) { //debugged
            AVLTree par = curr.parent;
            if(par.left==curr) {
                par.left=curr.right;
                curr.right.parent =par;
            }

            else {
                par.right=curr.right;
                curr.right.parent=par;
            }

            return par;   
        }

        else {
            //System.out.println("cat");
            AVLTree succ = curr.getNext();

            if(succ!=null) {
                curr.key = succ.key;
                curr.address = succ.address;
                curr.size = succ.size;
            }

            return deleteNode(succ);
        }

    }

    private boolean BSTreeDelete(Dictionary e) {
        AVLTree curr = this;
        AVLTree temp = new AVLTree(e.address,e.size,e.key); //temporarily downcasting e 

        while(curr.parent!=null) {  //traverse to sentinel root
            curr =curr.parent;
        }

        curr=curr.right;

        while(curr!=null) {

            if(curr.key==e.key) {
                if(curr.address==e.address && curr.size == e.size) {
                    AVLTree parent = deleteNode(curr);
                    //System.out.println(parent.key);
                    checkBalance_(parent);
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

    public boolean Delete(Dictionary e)
    {
        boolean deleted = BSTreeDelete(e);
        return deleted;
    }
        
    public AVLTree Find(int key, boolean exact) {
        AVLTree curr = this;

        while(curr.parent!=null) {  //traverse to sentinel root
            curr =curr.parent;
        }

        curr=curr.right;
        AVLTree res = null;

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

    public AVLTree getFirst()
    { 
        AVLTree curr = this;

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

    public AVLTree getNext()
    {
        AVLTree curr =this;

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

    private boolean checkBST_(AVLTree root,int min,int max) {
        if(root==null) {
            return true;
        }
        
        else if(root.key<=min||root.key>=max) {
            return false;    
        }
        
        return (checkBST_(root.left,min,root.key) && checkBST_(root.right,root.key,max));
        
    }
    private boolean checkBST(AVLTree root) {
        int pInf = Integer.MAX_VALUE;
        int nInf = Integer.MIN_VALUE;
        
        return checkBST_(root,nInf,pInf);
        
    }

    private boolean checkChildParent(AVLTree root) {

        if(root==null) {
            return true;
        }

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
    
    private boolean checkHeightBalance(AVLTree root) {

        if(root==null) {
            return true;
        }

        if(root.left==null && root.right==null) {
            return true;
        }
        
        else if(getBalance(root)>1) {
            return false;
        }

        return (checkHeightBalance(root.left) && checkHeightBalance(root.right));

    }
    

    public boolean sanity() { 
        boolean checkSentinel= true;
        boolean isBST = true;
        boolean checkchildParent =true;
        boolean isHeightBalanced=true;
        AVLTree curr = this;


        while(curr.parent!=null) {  //traverse to sentinel root
            curr =curr.parent;
        }
        AVLTree root = this.getFirst();
        
        if( curr.parent!=null) {
            checkSentinel=false;
        }

        if( curr.left!=null ) {
            checkSentinel=false;
        }


        if(root!=null && checkBST(root)==false) {
            isBST=false;
        }

        if(root!=null && checkChildParent(curr)==false) {
            checkchildParent=false;
        }

        if(root!=null && checkHeightBalance(root)==false) {
            isHeightBalanced=false;
        }

        return checkSentinel && checkchildParent && isBST && isHeightBalanced;
        
    }

    // public static void main(String []args) {
    //     AVLTree rootSentinel = new AVLTree();
    //     //BSTree a = new BSTree(1,6,1);

        
    //     rootSentinel.Insert(0, 0, 5);
    //     rootSentinel.Insert(0, 0, 4);
    //     rootSentinel.Insert(0, 0, 6);
    //     rootSentinel.Insert(0, 0, 7);
    //     rootSentinel.Insert(0, 0, 8);
    //     rootSentinel.Insert(0, 0, 10);
    //     rootSentinel.Insert(0, 0, 9);
    //     BSTree e = new BSTree(0,0,7);
    //     rootSentinel.Delete(e);
    //     e = new BSTree(0,0,9);
    //     rootSentinel.Delete(e);
    //     e = new BSTree(0,0,11);
    //     //System.out.println(rootSentinel.Delete(e));
      
    //     //rootSentinel.Insert(0,0,5);
    //     //rootSentinel.Insert(0,0,6);
       
    //     //BSTree x = rootSentinel.Find(8000,false);
    //     //BSTree b = new BSTree(1,0,4);
    //     //System.out.println(rootSentinel.Delete(a));

    //     //System.out.println(b.isGreater(a));

    //     AVLTree x = rootSentinel.right;

    //     // System.out.println(x.address+" "+x.size+" "+x.key);
    //     System.out.println(x.sanity());
    //     //root.inorder();

    //     for (AVLTree d = rootSentinel.getFirst(); d != null; d = d.getNext()) {
    //         System.out.println(d.address+" "+d.size+" "+d.key+" "+d.height);
    //     }

    //     // BSTree d= rootSentinel.right;
    //     // System.out.println(d.address+" "+d.size+" "+d.key);
    //     // d=d.right;
    //     // System.out.println(d.address+" "+d.size+" "+d.key);
    //     // d=d.parent.left;
    //     // System.out.println(d.address+" "+d.size+" "+d.key);
    // }
}



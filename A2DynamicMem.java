// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two. 
    public void Defragment() { 

        Dictionary tempTree;
        if(type==2) {
            tempTree = new BSTree();
        }
        else {
            tempTree = new AVLTree();
        }

        Dictionary d;

        for (d = freeBlk.getFirst(); d != null; d = d.getNext()) {
            tempTree.Insert(d.address,d.size,d.address);
        }

        d= tempTree.getFirst();

        if(d!=null) {

            while(d.getNext()!=null) {

                Dictionary n = d.getNext();

                if(d.address+d.size==n.address) {
                    int a= d.address;
                    int b= d.size+n.size;
                    Dictionary dd,nn;
                    if(type==2) {
                
                        dd= new BSTree(d.address,d.size,d.size);
                        nn= new BSTree(n.address,n.size,n.size);

                    }

                    else {
                        dd= new AVLTree(d.address,d.size,d.size);
                        nn= new AVLTree(n.address,n.size,n.size);
                    }

                    freeBlk.Delete(dd);
                    freeBlk.Delete(nn);

                    tempTree.Delete(d);
                    tempTree.Delete(n);

                    freeBlk.Insert(a, b, b);
                    d=tempTree.Insert(a, b, a);

                    //d= tempTree.getFirst();
                    if(d==null) {
                        break;
                    }
                }

                else {
                    d=d.getNext();
                }

            }

        }
        
        return ;
    }
}
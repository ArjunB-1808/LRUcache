import java.util.*;
class CDLLNode{
    int key, val; //data section
    CDLLNode prev, next;//pointers section
    public CDLLNode(int k, int v){//constructors section
        this.key = k;
        this.val = v;
    }
}
class CDLL{//circular DLL class
    CDLLNode head;
    public CDLL(){
        head = null;
    }
     CDLLNode insAtBegin(int k, int v){
        CDLLNode nn = new CDLLNode(k, v);//new node to be inserted
        nn.next=nn; nn.prev=nn;
        if(head==null){
            head=nn;//inserting first node
            return head;
        }
        CDLLNode last = head.prev;
        nn.next=head; head.prev=nn;//connect nn and head
        last.next=nn; nn.prev=last;
        head=nn;
        return head;
    }
    void printLL(){
        if(head==null) return;
        System.out.println(head.key+" ");
        
        CDLLNode temp = head.next;
        while(temp!=head){
            System.out.print(temp.key+" ");
            temp=temp.next;
        }
        System.out.println();
    }
    int delLastNode(){
        if(head==null){
            return -1;// empty LL 
        }
        CDLLNode last = head.prev;
        if(last==head){
            head=null;//deleted single node-LL now empty
            return last.key;
        }
        last.prev.next = head;
        head.prev=last.prev;
        return last.key;
    }
    void moveAtFront(CDLLNode nodeToMove){
        if(nodeToMove == head){
            return;// already at front, nothing to move
        }
        nodeToMove.prev.next = nodeToMove.next;//2-->4
        nodeToMove.next.prev = nodeToMove.prev;//4<--2

        CDLLNode last = head.prev;
        nodeToMove.next=head; head.prev=nodeToMove;//connect nn and head
        last.next=nodeToMove; nodeToMove.prev=last;
        head=nodeToMove;
    }
}
class cir{
    public static void main(String[] args){
        LRUCache ch = new LRUCache(3);
        ch.put(0,0);
        ch.put(1,1);
        System.out.println(ch.get(3));
        ch.put(3,3);
        System.out.println(ch.get(0));
        ch.put(4,4);
        System.out.println(ch.get(1));

        // CDLL list1 = new CDLL();
        // list1.insAtBegin(0,0);
        // list1.insAtBegin(1,111);
        // list1.insAtBegin(3,303);
        // list1.printLL();

        // list1.delLastNode();
        // list1.printLL();

        // list1.delLastNode();
        // list1.printLL();

        // list1.delLastNode();
        // list1.printLL();

        // list1.moveAtFront(list1.head); list1.printLL();
        // list1.moveAtFront(list1.head.next); list1.printLL();
        // list1.moveAtFront(list1.head.prev); list1.printLL();
    }
}
class LRUCache{
    int capacity;
    int size;
    CDLL list;
    Map<Integer, CDLLNode> mp;
    public LRUCache(int capacity){
        this.capacity = capacity;
        this.size = 0;
        this.list = new CDLL();
        this.mp = new HashMap<>();
    }
    int get(int key){
        if(!mp.containsKey(key))
            return -1;
        CDLLNode node = mp.get(key);
        int ret = node.val;
        list.moveAtFront(node);
        return ret;
    }
    void put(int k,int v){
        if(mp.containsKey(k)){//update the val for existing k
            CDLLNode node = mp.get(k);
            node.val = v;
            list.moveAtFront(node);
        }else{//insert new entry
            if(size<capacity){
                CDLLNode node = list.insAtBegin(k, v);
                mp.put(k,node);
                size++;
            }else{// evict LRU entry
                int deletedkey = list.delLastNode();
                mp.remove(deletedkey);
                CDLLNode node = list.insAtBegin(k, v);
                mp.put(k,node); 
            }
        }
    }
}

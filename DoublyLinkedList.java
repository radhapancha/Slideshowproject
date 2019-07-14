import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.File;
import java.awt.Color;

public class DoublyLinkedList <Item> implements Iterable<Item> { // to enable for each syntax
    private int n;          // number of elements
    private Node first;     // beginning of the list
    private Node last;      // end of the list
    static Picture picture = new Picture(298, 298);

    public DoublyLinkedList() {
        first  = null;
        last = null;
    }

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    /** Initializes an empty list. */
    /*public DoublyLinkedList() {
        first = null;
        last = null;
        n = 0;
    }*/

    public boolean isEmpty() { return n == 0; }
    public int size() { return n; }

    /* add to beginning of list */
    public void addFirst(Item item) {
        Node oldfirst = first;
        first = new Node();
        if (last == null)
            last = first;
        first.item = item;
        first.next = oldfirst;
  first.prev=null; // we added this in
        n++;
    }

    /* add to end of list */
    public void addLast(Item item) {
        Node temp = new Node();
        temp.item = item;
        if (last == null) {
            first = temp;
            last = temp;
        }
        else {
    last.next = temp;
       temp.prev=last; // added
  last = temp;
        }
        n++;
    }

    /** Removes the item at the beginning of the list. **/
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("List empty");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
  first.prev= null;   // added
        n--;
        return item;                   // return the saved item
    }

    /** Returns (but does not remove) the first element in the list. */
    public Item getFirst() {
        if (isEmpty())
            throw new NoSuchElementException("List empty");
        return first.item;
    }

    /**
     * Returns an iterator to this list that traverses the items from first to last.
     */

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

     public Iterator<Item> Reviterator() {
        return new RevListIterator();
    }


    // The Iterator<T> interface contains methods hasNext(), remove(), next()
    // (remove() is optional)
    public class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

    }
    public class RevListIterator implements Iterator<Item>
    {
      private Node current = last;
      public boolean hasNext()  {
        return current != null;
      }

  public Item next() {
          //  if (!hasNext())
    //throw new NoSuchElementException();
            Item item = current.item;
            current = current.prev;
            return item;
        }
 }

    /** Unit test with some pictures */
    public static void pause(int t) {     /** stops for t milliseconds */
 try {
            Thread.sleep(t);
        } catch (Exception e) {}
    }




}

/*////////////////////////////////////////////////////////////////
* Radha Panchap           Viewer.java
*
*  There are 4 pictures with index 0 to 3. To run all 4 in the forward direction, simply type -d:pictures -f or  -d:pictures -f 0,3
* To run the photos in the reverse direction, type -d:pictures -r or -d:pictures -r 3,0
* To run photos from reverse, choose the photo range from the reverse i.e. all of the photos to run in reverse would be -d:pictures -r 3,0 instead of 0,3
*
/////////////////////////////////////////////////////////////////*/

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.File;
import java.awt.Color;

public class Viewer < Item > {
 public static int b;
// one variable to represent beginning in the forward case. Order of how they are used will change in reverse method.
 public static int e;
// one variable to represent end in the forward case. Order of how they are used will change in the reverse method.
 public static Picture picture = new Picture(298, 298);
// declared new picture variable

 public static void pause(int t) {
  /** stops for t milliseconds */
  try {
   Thread.sleep(t);
  } catch (Exception e) {}
 }

 public static Color combine(Color c1, Color c2, double alpha) {
  int r = (int)(alpha * c1.getRed() + (1 - alpha) * c2.getRed());
  int g = (int)(alpha * c1.getGreen() + (1 - alpha) * c2.getGreen());
  int b = (int)(alpha * c1.getBlue() + (1 - alpha) * c2.getBlue());
  return new Color(r, g, b);
 }
 // fade method, fades one photo to the next
 public static void fade(int n, Picture picture1, Picture picture2) {
  int width = picture1.width();
  int height = picture1.height();
  for (int k = 0; k <= n; k++) {
   double alpha = 1.0 * k / n;
   for (int col = 0; col < width; col++) {
    for (int row = 0; row < height; row++) {
     Color c1 = picture1.get(col, row);
     Color c2 = picture2.get(col, row);
     picture.set(col, row, combine(c2, c1, alpha));
    }
   }
   picture.show();
  }
 }


 public static void main(String[] args) {
  try {
   String w = args[0];
   String[] comm;
   comm = w.split(":");
   // -d: folder
   if (comm[0].equals("-d")) {
    // checking for invalid arguments such as no folder or an invalid folder. I used if statements to check for this.
    if (comm.length == 1)
     System.out.println("Please input a valid folder name with pictures");
    if (comm.length > 1) {
     if (comm[1] == null || comm[1].equals("")) {
      System.out.println("Error: please put a name of a photos folder.");
     } else {
      File folder = new File(comm[1]); /** may need to change this */
      File[] listOfFiles = folder.listFiles();
      // new DoublyLinkedList is called photoStack
      DoublyLinkedList < Picture > photoStack = new DoublyLinkedList < Picture > ();
      for (File f: listOfFiles) /** array implements iterable */
       photoStack.addLast(new Picture(f)); // adds photo to photoStack
      Picture p = null;
      //  p.show();
      Iterator < Picture > l = photoStack.iterator(); // will be used to iterate through photoStack

      String input = args[1];
      String[] command; // = new String[];

      command = input.split(":");
      // runs pictures in forward direction
      if (command[0].equals("-f")) {
       if (command.length == 1) {
        b = 0;
        e = listOfFiles.length;
        p = l.next();
        for (int i = b; i < e - 1; i++) {
         Picture q = l.next();
         fade(200, p, q);
         p = q;
        }
       } else {
        if (command.length > 1) {
         String[] splitstring = command[1].split(",");
         b = Integer.parseInt(splitstring[0]);
         e = Integer.parseInt(splitstring[1]);
         if (e > b) {
          for (int i = 0; i <= b; i++) {
           p = l.next();
          }
          // p.show();
          for (int i = b; i < e; i++) {
           Picture q = l.next();
           fade(200, p, q);
           p = q;
          }
         }
        }
       }
      }
      // if the user types -r after -d: pictures, then this runs photos in reverse
      if (command[0].equals("-r")) {
       Picture q = null;
       // I made a forward iterator and a backward iterator. I need both in order to correctly display the photos in reverse.
       Iterator < Picture > forw = photoStack.iterator();
       Iterator < Picture > back = photoStack.Reviterator();

       if (command.length == 1) {
        b = 0;
        e = listOfFiles.length;
        // these set of for loops run all photos in reverse order

        for (int i = b; i < e; i++) {
         p = forw.next();
        }
        for (int k = e; k > e - b; k--) {
         q = forw.next();
        }
        for (int i = e; i > b; i--) {
         q = back.next();
         fade(200, p, q);
         p = q;
        }

       }
       if (command.length > 1) {
        // splits comand line to see if they can run in reverse
        String[] splitstring = command[1].split(",");
        e = Integer.parseInt(splitstring[0]); // i make e the first paramater as we are now going in reverse order
        b = Integer.parseInt(splitstring[1]);

        if (e > b) {
         // p moves forward to position e
         for (int i = 0; i <= e; i++) {
          p = forw.next();

         }
         // q moves to listOfFiles.length-e
         for (int k = 0; k < listOfFiles.length - e; k++) {
          q = back.next();
         }
         // q moves in reverse from e to b, showing photos in positions e through b.
         for (int j = e; j > b; j--) {
          q = back.next();
          fade(200, p, q);
          p = q;
         }
        }
       }
      }
     }
    }
   }
  }
  // catch clause checks for invalid input
  catch (Exception e) {
   System.out.println("Invalid input. Please try again.");
  }
 }
}


import java.util.*;
public class sample {

	public static String swapString(String s, int i, int j) {
        char[] b = s.toCharArray();
        char temp;
        temp = b[i];
        b[i] = b[j];
        b[j] = temp;
        return String.valueOf(b);
    }

    public static void generatePermutation(String s, int start, int end, HashSet < String > set) {

        if (start == end - 1)
            set.add(s + " ");
        else {
            for (int i = start; i < end; i++) {
//                System.out.println("before swap start = "+start+" end "+end +" i= "+i+" start+1 = "+(start+1));
//                System.out.println("String swapped 1st before = "+s);
                s = swapString(s, start, i);
              //  System.out.println("String swapped 1st after  = "+s);
               
                generatePermutation(s, start + 1, end, set);
//                 System.out.println("After swap start = "+start+" end "+end +" i= "+i);
//                 System.out.println("String swapped 2nd before = "+s);
                s = swapString(s, start, i);
               // System.out.println("String swapped 2nd after  = "+s);
               // System.out.println(s);
            }
        }

    }
    
    public static void main(String[] args) {
        
            HashSet < String > set = new HashSet < String > ();
            String s = "abababa";
            generatePermutation(s, 0, s.length(), set);
            System.out.println(set.size());
            for(String sa : set) {
            	System.out.println(sa);
            }
           
    }
}

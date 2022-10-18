package org.example;

import java.util.*;

public class Test {

    public void findSubstr(String input, int length, boolean verbose) {
        LinkedHashMap<String, Integer> counts =
                new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, LinkedList<Integer>> collisions =
                new LinkedHashMap<String, LinkedList<Integer>>();

        //remove all non-alpha characters
        input = input.replaceAll("[^A-Za-z]", "");
        boolean foundSubStr = false;
        int orig_len = length;
        boolean found_any = false;

        //sliding window to check for repeated substrings.
        for (int i = 0, j = length; j <= input.length(); ) {
            String sub = input.substring(i, j);

            //if the substring is in the map
            if (counts.containsKey(sub) && collisions.containsKey(sub)) {
                int count = counts.get(sub);
                counts.put(sub, count + 1);

                //store the index of the substring
                LinkedList<Integer> temp = collisions.get(sub);
                temp.add(i);
                collisions.put(sub, temp);

                //set found to true
                foundSubStr = true;
                found_any = true;
            }
            //put it in the map with a count of 1
            else {
                counts.put(sub, 1);
                LinkedList<Integer> list = new LinkedList<Integer>();
                list.add(i);
                collisions.put(sub, list);
            }


            if (j == input.length() && foundSubStr) {
                foundSubStr = false;
                i = 0;
                length += 1;
                j = length;
            } else {
                i++;
                j++;
            }
        }

        //The output must be sorted by length, then count, then alphabetically.
        // The comparator and treemap sort on theses three values
        TreeMap<String, Integer> counts_final =
                new TreeMap<String, Integer>(new LengthCompare(counts));

        //Sort elements by putting back in map
        counts_final.putAll(counts);

        if (verbose) {
            for (Map.Entry<String, LinkedList<Integer>> entry : collisions.entrySet()) {
                System.out.println("Substr: " + entry.getKey() + " list: "
                        + entry.getValue());
            }
            for (Map.Entry<String, Integer> entry : counts_final.entrySet()) {
                System.out.println("Substr: " + entry.getKey() + " count: "
                        + entry.getValue());
            }
        }
        //Don't print if we never found any!
        //if (found_any) {
            printSubstrResults(counts_final, collisions, orig_len);
        //}
    }

    public void printSubstrResults(TreeMap<String, Integer> counts,
                                   LinkedHashMap<String, LinkedList<Integer>>
                                           locations, int orig_len) {

        System.out.println("Length Count Word Location (distance)");
        System.out.println("====== ===== ==== =========");

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() >= 2) {
                LinkedList<Integer> list = locations.get(entry.getKey());
                System.out.printf("%6d %5d %4s ", (entry.getKey()).length(),
                        entry.getValue(), entry.getKey());
                int i = 0;
                for (; i < list.size(); i++) {
                    if (i > 0) {
                        System.out.printf("%d (%d) ", list.get(i),
                                list.get(i) - list.get(i - 1));
                    } else {
                        System.out.print(list.get(i) + " ");
                    }
                }
                System.out.print("\n");
            }
        }
    }

    private static class LengthCompare implements Comparator<String> {

        Map<String, Integer> base;

        public LengthCompare(Map<String, Integer> base) {
            this.base = base;
        }

        @Override
        public int compare(String s1, String s2) {
            int return_val = 0;

            //sort by length
            if (s1.length() < s2.length()) {
                return_val = 1;
            } else if (s1.length() > s2.length()) {
                return_val = -1;
            } else { //sort by count
                if (base.get(s1) < base.get(s2)) {
                    return_val = 1;
                } else if (base.get(s1) > base.get(s2)) {
                    return_val = -1;
                } else {
                    //Alphabetical
                    return_val = s1.compareTo(s2);
                }
            }
            return return_val;
        }

    }
}

package com.zainab.learning;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @author al-haidaryz
 *
 */
public class ReassembleTextFragments {
	
	class Defragmenter
	{
		//holds the number of total comparisions
		private int numberOfComparisons =0;		

		//holds the number of current comparisions
		private int numberOfcurrentComparisons =0;
		
		public int getNumberOfcurrentComparisons() {
			return numberOfcurrentComparisons;
		}

		public void setNumberOfcurrentComparisons(int numberofcurrentComparisons) {
			this.numberOfcurrentComparisons = numberofcurrentComparisons;
		}

		public int getNumberOfComparisons() {
			return numberOfComparisons;
		}

		public void setNumberOfComparisons(int numberOfComparisons) {
			this.numberOfComparisons = numberOfComparisons;
		}
		
		/**
		 * This method finds the max matching fragment to the currentFragment 
		 * within the fragmentList and merge them together. The method repeats 
		 * until there is one fragment left, which the defragmented text. 
		 * 
		 * @param currentFragment
		 * @param fragmentList
		 * @return defragmented text
		 */
		public String findAndMerge(String currentFragment, List<String> fragmentList)
		{
			if(this.getNumberOfcurrentComparisons()>this.getNumberOfComparisons())
			{
				return "";//no overlaps
			}
			this.setNumberOfcurrentComparisons(this.getNumberOfcurrentComparisons()+1);
			/**
			 * hashTable keeps track of the number of overlaps and the fragments
			 */
			Map<Integer, String> hashTable = new HashMap<Integer,String>();
			
			if(fragmentList.size()==0)
			{
				return currentFragment;//the defragmented final text
			}
					
			for(String s: fragmentList)
			{		
				//if the fragment 1 contains fragment 2 then discard fragment 2
				if(currentFragment.contains(s))
				{
					fragmentList.remove(s);
					//start the search again but a shorter list
					return findAndMerge(currentFragment,fragmentList);
				}
				//if the fragment 2 contains fragment 1 then discard fragment 1
				else if(s.contains(currentFragment))
				{
					//then ignore, and start from the next element
					return findAndMerge(fragmentList.get(0),fragmentList);
				}
				else //else means the suffix of fragment 1 equals the prefix of fragment 2 
				{
					//get the number of overlapping characters
					int i = overlappedStringLength(currentFragment, s);
					hashTable.put(i, s);
				}
			}		
			//find max number of overlaps
			int max = Collections.max(hashTable.keySet());
			
			//if there is an overlap
			if(max>0)
			{
				//merge the two fragments
				String m= merge(currentFragment, hashTable.get(max), max);
				fragmentList.remove(hashTable.get(max));
				fragmentList.add(m);
		
			}
			else
			{
				fragmentList.add(currentFragment); 
			}
			//get new current fragment
			currentFragment = fragmentList.get(0);
			fragmentList.remove(currentFragment);
			//search again through the list excluding the current fragment
			return findAndMerge(currentFragment,fragmentList);

		}
		
		/**This method merges frag1 suffix and frag2 prefix 
		 * @param frag1
		 * @param frag2
		 * @param overlap
		 * @return: merged string
		 */
		public String merge(String frag1, String frag2, int overlap){
			StringBuilder sb = new StringBuilder(frag1);
			
		    sb.append(frag2.substring(overlap));
		    return sb.toString();
		}
		
		

		/**This method returns the number of overlapping characters between s1 and s2
		 * @param s1
		 * @param s2
		 * @return
		 */
		public int overlappedStringLength(String s1, String s2) {
	        if (s1.length() > s2.length()) 
	        	s1 = s1.substring(s1.length() - s2.length());

	        int[] T = computeBackTrackTable(s2); 

	        int m = 0;
	        int i = 0;
	        while (m + i < s1.length()) {
	            if (s2.charAt(i) == s1.charAt(m + i)) {
	                i += 1;
	            } else {
	                m += i - T[i];
	                if (i > 0) i = T[i];
	            }
	        }

	        return i; 
	    }

	    public int[] computeBackTrackTable(String s) {
	        int[] arr = new int[s.length()];
	        int cnd = 0;
	        arr[0] = -1;
	        arr[1] = 0;
	        int pos = 2;
	        while (pos < s.length()) {
	            if (s.charAt(pos - 1) == s.charAt(cnd)) {
	                arr[pos] = cnd + 1;
	                pos += 1;
	                cnd += 1;
	            } else if (cnd > 0) {
	                cnd = arr[cnd];
	            } else {
	                arr[pos] = 0;
	                pos += 1;
	            }
	        }

	        return arr;
	    }
		
	}
	
    
    public static String reassemble(String s)
	{
    	
    	List<String> fList = new LinkedList<String>(Arrays.asList(s.split(";")));
		ReassembleTextFragments z = new ReassembleTextFragments();
		ReassembleTextFragments.Defragmenter d =z.new Defragmenter();
		d.setNumberOfComparisons((fList.size()-1)*(fList.size()));
		//search through the list excluding the first fragment where we started the search
		String frag = fList.remove(0);
		return d.findAndMerge(frag, fList);
	}
	

	 public static void main(String[] args) {
		 
		 try{
			 String resourcesFolder = "src/main/resource/" ;
			 String file = resourcesFolder + "reassemble-text-fragments-example";//args[0];
			 BufferedReader in = new BufferedReader(new FileReader(file));
			 String fragmentProblem;
			 while ((fragmentProblem = in.readLine()) != null) {
				 System.out.println(reassemble(fragmentProblem));
			 }
			 in.close();
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	 } 

}

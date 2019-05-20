/* This program was written
 * by Jessica Devlin
 * on 3/26/19.
 * This is a program written to take user entry of beauty pageant contestants and return the 15 finalists.
 * This program uses a Treap data structure which simultaneously maintains heap and BST ordering properties.
 */

import java.util.ArrayList;
import java.util.Scanner;	

public class Finalists {

	public static void main(String[] args) {
		ArrayList<String> names = new ArrayList<>();
		ArrayList<Integer> scores = new ArrayList<>();
		String in;	// input line
		System.out.println("How many entries are you inputting?");
		Scanner sc = new Scanner(System.in);
		int numEntries = sc.nextInt();
		System.out.println("Please enter: ");
		sc.nextLine();
		
		for(int i = 0; i < numEntries; i++) {
			in = sc.nextLine();
			String name = in.substring(0, in.indexOf(","));	// contestant name
			String strScore = in.substring(in.indexOf(",")+ 1, in.length());	// score after comma
			Integer score = new Integer(strScore);	// add score to scores
			if (names.contains(name)) {		// if there is another score entry of this contestant
				int dup = names.indexOf(name);		// index of duplicate name
				scores.set(dup, scores.get(dup)+score); // sums and replaces scores
			}
			else {
				names.add(name);	// adds contestant name, before comma, to names arrayList
				scores.add(score);	// adds score if new entry
			}
		}
		
		Treap <String, Integer> contestants = new Treap<>();
		for (int i = 0; i < names.size(); i++) {			// for each entry
			contestants.put(names.get(i), scores.get(i));	// put entry into tree
		}
		
		for (int i = 0; i < 15; i++) {			// for 15 finalists
			System.out.println(contestants.removeMax());	// return max score entry
		}
		
		sc.close();
	}
}

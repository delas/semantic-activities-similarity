package activitiesSimilarity.test;

import java.util.HashSet;
import java.util.Set;

import activitiesSimilarity.ActivitiesSimilarity;

public class Tester {

	public static void main(String[] args) {
		
		ActivitiesSimilarity as = ActivitiesSimilarity.instance();
		
		Set<String[]> tests = new HashSet<String[]>();
		tests.add(new String[]{"send confirmation", "send confirmation"});
		tests.add(new String[]{"send confirmation", "send verification"});
		tests.add(new String[]{"send employee", "send software"});
		tests.add(new String[]{"send rejection", "send acceptance"});
		tests.add(new String[]{"confirmation", "verification sent"});
		tests.add(new String[]{"send confirmation", "send verification"});
		
		System.out.println("\nTESTING");
		System.out.println("=======");
		for(String[] s : tests) {
			System.out.println("Checking `" + s[0] + "' vs `" + s[1] + "'");
			System.out.println("Are the same: " + as.sameActivity(s[0], s[1]));
			System.out.println("Syntactic score: " + as.syntacticSimilarityScore(s[0], s[1]));
			System.out.println("Semantic score : " + as.semanticSimilarityScore(s[0], s[1]));
			System.out.println("");
		}
	}
}

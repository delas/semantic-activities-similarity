package activitiesSimilarity;

import it.gonzo.assessor.SentenceSimilarityAssessor;
import it.gonzo.similarity.utils.SimilarityConstants;

import java.util.HashMap;
import java.util.Map;

import com.wcohen.ss.Levenstein;
import com.wcohen.ss.api.StringDistance;

public class ActivitiesSimilarity {

	public static double SYNTACTIC_SIMILARITY_THRESHOLD = 0.9;
	public static double SEMANTIC_SIMILARITY_THRESHOLD = 0.9;
	
	private static ActivitiesSimilarity instance = new ActivitiesSimilarity();
	
	private StringDistance stringDistance = new Levenstein();
	private SentenceSimilarityAssessor semanticSimilarity = new SentenceSimilarityAssessor();
	private Map<String, Double> syntacticCache = new HashMap<String, Double>();
	private Map<String, Double> semanticCache = new HashMap<String, Double>();
	
	private ActivitiesSimilarity() { }
	
	public static ActivitiesSimilarity instance() {
		return instance;
	}
	
	public boolean sameActivity(String activityLabelA, String activityLabelB) {
		double synct = syntacticSimilarityScore(activityLabelA, activityLabelB);
		if (synct > SYNTACTIC_SIMILARITY_THRESHOLD) {
			return true;
		} else {
			double semant = semanticSimilarityScore(activityLabelA, activityLabelB);
			if (semant > SEMANTIC_SIMILARITY_THRESHOLD) {
				return true;
			}
		}
		return false;
	}
	
	public double syntacticSimilarityScore(String activityLabelA, String activityLabelB) {
		String key = activityLabelA + " & " + activityLabelB;
		if (syntacticCache.containsKey(key)) {
			return syntacticCache.get(key);
		}
		double distance = stringDistance.score(activityLabelA, activityLabelB);
		double normalizedDistance = distance / Math.max((double) activityLabelA.length(), (double) activityLabelB.length());
		double similarity = 1 + normalizedDistance;
		syntacticCache.put(key, similarity);
		return similarity;
	}
	
	public double semanticSimilarityScore(String activityLabelA, String activityLabelB) {
		String key = activityLabelA + " & " + activityLabelB;
		if (semanticCache.containsKey(key)) {
			return semanticCache.get(key);
		}
		double similarity = semanticSimilarity .getWordNetHungarianSentenceSimilarity(
				activityLabelA,
				activityLabelB,
				SimilarityConstants.BYPOS,
				SimilarityConstants.FAITH_MEASURE,
				SimilarityConstants.TURNEY_SCORE_3);
		semanticCache.put(key, similarity);
		return similarity;
	}
}

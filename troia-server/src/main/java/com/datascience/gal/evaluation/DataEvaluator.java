package com.datascience.gal.evaluation;

import com.datascience.core.base.Category;
import com.datascience.core.base.LObject;
import com.datascience.core.nominal.NominalProject;
import com.datascience.core.nominal.decision.DecisionEngine;
import com.datascience.core.nominal.decision.IObjectLabelDecisionAlgorithm;
import com.datascience.core.nominal.decision.ObjectLabelDecisionAlgorithms;
import com.datascience.utils.ProbabilityDistributions;
import com.google.common.base.Strings;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author konrad
 */
public class DataEvaluator {
	
	protected String labelChoosingMethod;
	protected IObjectLabelDecisionAlgorithm olda;

	public DataEvaluator(String method) {
		labelChoosingMethod = method;
		if (Strings.isNullOrEmpty(labelChoosingMethod)) {
			labelChoosingMethod = "soft";
		}
		else{
			labelChoosingMethod = labelChoosingMethod.toLowerCase();
			olda = ObjectLabelDecisionAlgorithms.get(labelChoosingMethod);
		}
	}

	protected double evaluate(NominalProject project, LObject<String> datum) {
		Map<String, Double> dest_probabilities = project.getAlgorithm().calculateDistribution(datum);
		if (!labelChoosingMethod.equals("soft"))
			dest_probabilities = ProbabilityDistributions.generateOneLabelDistribution(
					datum, project, new DecisionEngine(null, olda));
		Category fromCostVector = project.getData().getCategory(datum.getEvaluationLabel());
		double cost = 0.0;
		for (Map.Entry<String, Double> e : dest_probabilities.entrySet()) {
			Double misclassification_cost = fromCostVector.getCost(e.getKey());
			cost += e.getValue() * misclassification_cost;
		}
		return cost;
	}
	
	public Map<String, Double> evaluate(NominalProject project){
		Collection<LObject<String>> evalData = project.getData().getEvaluationObjects();
		Map<String, Double> ret = new HashMap<String, Double>();
		for (LObject<String> cl: evalData) {
			ret.put(cl.getName(), evaluate(project, cl));
		}
		return ret;
	}
}

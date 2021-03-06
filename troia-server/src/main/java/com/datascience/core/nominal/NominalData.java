package com.datascience.core.nominal;

import com.datascience.core.base.AssignedLabel;
import com.datascience.core.base.Data;
import com.datascience.core.base.LObject;
import com.datascience.utils.CostMatrix;
import com.google.common.math.DoubleMath;

import java.util.*;

/**
 * User: artur
 */
public class NominalData extends Data<String> {

	protected Set<String> categories;
	protected boolean fixedPriors;
	protected Map<String, Double> categoryPriors;
	protected CostMatrix<String> costMatrix;

	public Set<String> getCategories(){
		return categories;
	}

	public void setCategories(Set<String> categories){
		this.categories = categories;
	}

	public boolean arePriorsFixed(){
		return fixedPriors;
	}

	public void setPriorFixed(boolean fixedPriors){
		this.fixedPriors = fixedPriors;
	}

	public double getCategoryPrior(String name){
		return categoryPriors.get(name);
	}

	public Map<String, Double> getCategoryPriors(){
		return categoryPriors;
	}

	public void setCategoryPriors(Collection<CategoryValue> priors){
		double priorSum = 0.;
		for (CategoryValue cv : priors){
			priorSum += cv.value;
		}
		if (priors.size() == categories.size() && DoubleMath.fuzzyEquals(1., priorSum, 1e-6)){
			fixedPriors = true;
			categoryPriors = new HashMap<String, Double>();
			for (CategoryValue cv : priors)
				categoryPriors.put(cv.categoryName, cv.value);
		}
		else
			throw new IllegalArgumentException(
				"Priors should sum up to 1. or not to be given (therefore we initialize the priors to be uniform across classes)");
	}

	public CostMatrix<String> getCostMatrix(){
		return costMatrix;
	}

	public void setCostMatrix(CostMatrix<String> cm){
		this.costMatrix = cm;
	}

	public void initialize(Collection<String> categories, Collection<CategoryValue> priors, CostMatrix<String> costMatrix){
		if (categories == null || categories.size() < 2){
			throw new IllegalArgumentException("There should be at least two categories");
		}
		this.categories = new HashSet<String>();
		this.categories.addAll(categories);

		fixedPriors = false;

		if (priors != null){
			setCategoryPriors(priors);
		}

		this.costMatrix = costMatrix;
		if (this.costMatrix == null)
			this.costMatrix = new CostMatrix<String>();
		for (String c1 : categories)
			for (String c2 : categories){
				if (!this.costMatrix.hasCost(c1, c2))
					this.costMatrix.add(c1, c2, c1.equals(c2) ? 0. : 1.);
			}
	}

	@Override
	public void addAssign(AssignedLabel<String> assign){
		checkForCategoryExist(assign.getLabel());
		super.addAssign(assign);
	}

	@Override
	public void addObject(LObject<String> object){
		if (object.isGold()) checkForCategoryExist(object.getGoldLabel());
		if (object.isEvaluation()) checkForCategoryExist(object.getEvaluationLabel());
		super.addObject(object);
	}

	private void checkForCategoryExist(String name){
		if (!categories.contains(name))
			throw new IllegalArgumentException("There is no category named: " + name);
	}
}

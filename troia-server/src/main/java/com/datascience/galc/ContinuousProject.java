package com.datascience.galc;

import java.util.Map;

import com.datascience.core.base.*;

/**
 * @Author: konrad
 */
public class ContinuousProject extends Project<ContValue, Data<ContValue>, DatumContResults, WorkerContResults> {

	public ContinuousProject(ContinuousIpeirotis ci){
		super(ci);
		results = new Results<ContValue, DatumContResults, WorkerContResults>(
				algorithm.getDatumResultCreator(),
				algorithm.getWorkerResultCreator());
		data = new Data<ContValue>();
		ci.setData(data);
		ci.setResults(results);
	}

	protected void checkComputed(){
		if (algorithm == null) {
			throw new IllegalStateException("Run compute first!");
		}
	}

	public Map<LObject<ContValue>, DatumContResults> getDataPrediction(){
		checkComputed();
		return ((ContinuousIpeirotis)algorithm).getObjectsResults();
	}

	public Map<Worker<ContValue>, WorkerContResults> getWorkerPrediction(){
		checkComputed();
		return ((ContinuousIpeirotis)algorithm).getWorkersResults();
	}


}

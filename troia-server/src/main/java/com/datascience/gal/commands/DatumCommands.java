package com.datascience.gal.commands;

import java.util.ArrayList;
import java.util.Collection;

import com.datascience.gal.AbstractDawidSkene;
import com.datascience.gal.CorrectLabel;
import com.datascience.gal.Datum;

/**
 *
 * @author artur
 */
public class DatumCommands {
	
	static public class AddGoldData extends ProjectCommand<Object> {

		private Collection<CorrectLabel> labels;
		
		public AddGoldData(AbstractDawidSkene ads, Collection<CorrectLabel> labels){
			super(ads, true);
			this.labels = labels;
		}
		
		@Override
		void realExecute() {
			ads.addCorrectLabels(labels);
			setResult("Correct data added");
		}
	}
	
	static public class GetGoldData extends ProjectCommand<Collection<CorrectLabel>> {
		
		public GetGoldData(AbstractDawidSkene ads){
			super(ads, false);
		}
		
		@Override
		void realExecute() {
			setResult(ads.getGoldDatums());
		}
	}
	
	static public class AddEvaluationData extends ProjectCommand<Object> {

		private Collection<CorrectLabel> labels;
		
		public AddEvaluationData(AbstractDawidSkene ads, Collection<CorrectLabel> labels){
			super(ads, true);
			this.labels = labels;
		}
		
		@Override
		void realExecute() {
			ads.addEvaluationDatums(labels);
			setResult("Evaluation datums added");
		}
	}
	
	static public class GetEvaluationData extends ProjectCommand<Collection<CorrectLabel>> {
		
		public GetEvaluationData(AbstractDawidSkene ads){
			super(ads, false);
		}
		
		@Override
		void realExecute() {
			setResult(ads.getEvaluationDatums());
		}
	}
	
	static public class AddData extends ProjectCommand<Object> {

		private Collection<String> objects;
		
		public AddData(AbstractDawidSkene ads, Collection<String> objects){
			super(ads, true);
			this.objects = objects;
		}
		
		@Override
		void realExecute() {
			ads.addObjects(objects);
			setResult("Object without labels added");
		}
	}
	
	static public class GetData extends ProjectCommand<Collection<Datum>> {
		
		private String type;
		
		public GetData(AbstractDawidSkene ads, String type){
			super(ads, false);
			this.type = type;
		}
		
		@Override
		void realExecute() {
			if (type.equals("only_with_assigns")) {
				setResult(ads.getObjects().values());
			}
			else if (type.equals("unassigned")) {
				setResult(ads.getObjectsWithNoLabels().values());
			}
			else {
				Collection<Datum> res = new ArrayList<Datum>();
				res.addAll(ads.getObjects().values());
				res.addAll(ads.getObjectsWithNoLabels().values());
				setResult(res);
			}
		}
	}
	
	static public class GetDatum extends ProjectCommand<Datum> {
		
		private String datumId;
		
		public GetDatum(AbstractDawidSkene ads, String datumId){
			super(ads, false);
			this.datumId = datumId;
		}
		
		@Override
		void realExecute() {
			setResult(ads.getObject(datumId));
		}
	}
}
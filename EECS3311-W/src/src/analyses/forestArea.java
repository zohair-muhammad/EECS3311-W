package src.analyses;

import java.util.HashMap;
import java.util.Map.Entry;

import src.dataExtractor;
import src.util;
import src.concrete.analyses;
import src.fetcher.Adapter;

public class forestArea implements analyses {
	private Adapter jsonObject;
	
	public forestArea(int startYear, int endYear, String countryCode) {
		if (isValid(startYear, endYear, countryCode)) {
			this.jsonObject = new Adapter(startYear, endYear, countryCode);
		}
	}
	
	private boolean isValid(int startYear, int endYear, String countryCode) {
		boolean result = true;
		if (endYear < startYear) {
			return false;
		}
		if (!util.COUNTRIES.contains(countryCode)) {
			return false;
		}
		return result;
	}
	
	public HashMap<Integer, Double> analyzeData() {
		if (jsonObject == null) {
			return null;
		}
		HashMap<Integer, Double> result = new HashMap<Integer, Double>();
		HashMap<Integer, Double> forestArea = dataExtractor.filter(jsonObject.getData("AG.LND.FRST.ZS"));
		for (Entry<Integer, Double> temp : forestArea.entrySet()) {
			  Integer year = temp.getKey();
			  Double forestPercent = temp.getValue();
			  System.out.println("forest area percentage " + year + " is "+ forestPercent);
			  System.out.println("percentage of the rest of the land used " + year + " is "+ (100-forestPercent));
			  result.put(year, forestPercent);
		return result;
		}
}
		
	public static void main(String args[]) {
		forestArea test = new forestArea(2000, 2004, "can");
		System.out.println(test.analyzeData());
	}
	
}

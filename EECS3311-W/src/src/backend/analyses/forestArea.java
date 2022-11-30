package src.backend.analyses;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import src.backend.concrete.Adapter;
import src.backend.concrete.GeneralGraphTemplate;
import src.backend.concrete.dataExtractor;
import src.backend.concrete.dataFetcher;
import src.backend.concrete.linkedList;
import src.backend.interfaces.analyses;

public class forestArea implements analyses {
	private dataFetcher jsonObject;
	private Set<String> acceptGraph;
	public forestArea(int startYear, int endYear, String countryCode) {
		if (isValid(startYear, endYear, countryCode)) {
			this.jsonObject = new Adapter(startYear, endYear, countryCode);
		}
		this.acceptGraph = (new GeneralGraphTemplate()).getTemplate();
	}
	@Override
	public boolean isValid(int startYear, int endYear, String countryCode) {
		boolean result = true;
		if (endYear < startYear) {
			JOptionPane.showMessageDialog(null, "Years not Valid", "Years Selction", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		/*if (!util.COUNTRIES.contains(countryCode)) {
			return false;
		}*/
		return result;
	}
	public Set<String> getAcceptGraph() {
		return acceptGraph;
	}
	
	public linkedList analyzeData() {
		if (jsonObject == null) {
			return null;
		}
		LinkedHashMap<Integer, Double> series1 = new LinkedHashMap<Integer, Double>();
		LinkedHashMap<Integer, Double> forestArea = dataExtractor.filter(jsonObject.getData("AG.LND.FRST.ZS"));
		
		if(forestArea == null) {
			JOptionPane.showMessageDialog(null, "World Bank Does Not Have Data For The Selected Year(s)", "Data Not Available", JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		
		for (Entry<Integer, Double> temp : forestArea.entrySet()) {
			  Integer year = temp.getKey();
			  Double forestPercent = temp.getValue();
			  System.out.println("forest area percentage " + year + " is "+ forestPercent);
			  System.out.println("percentage of the rest of the land used " + year + " is "+ (100-forestPercent));
			  series1.put(year, forestPercent);
		}
		linkedList result = new linkedList(series1, "Forest Area", null);
		
		return result;
}
		
	public static void main(String args[]) {
		forestArea test = new forestArea(2000, 2004, "can");
		System.out.println(test.analyzeData());
	}
	
}


import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Analise  {
	
	/**
	 * 
	 */
	public Analise() {

	}

	public Map<String, Integer> getIpCounts(ArrayList<Hits> hits) {
		Map<String, Integer> countMap = new TreeMap<String, Integer>();
		for (int i = 0; i < hits.size(); i++) {
			String key = hits.get(i).getiPaddr();
			if (countMap.containsKey(key)) {
				int count = countMap.get(key);
				count++;
				countMap.put(key, count);
			}else {
				countMap.put(key, 1);
			}
		}
		for (Entry<String, Integer> val : countMap.entrySet()) {
			System.out.println(val.getKey() +"shows up"+ val.getValue());
		}
		return countMap;
	}
	public int getTotalData(ArrayList<Hits> hits) {
		int total=0;
		for (Hits h : hits) {
			total = total + h.getSize();
		}
		return total;
	}
	public int getTotalHits(ArrayList<Hits> hits) {
		return hits.size();
	}

}

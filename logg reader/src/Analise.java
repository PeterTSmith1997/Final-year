import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Analise {

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
			} else {
				countMap.put(key, 1);
			}
		}
		for (Entry<String, Integer> val : countMap.entrySet()) {
			System.out.println(
					val.getKey() + " shows up " + val.getValue() + " times");
		}
		return countMap;
	}

	public Map<String, Integer> getRefererCounts(ArrayList<Hits> hits) {
		Map<String, Integer> countMap = new TreeMap<String, Integer>();
		for (int i = 0; i < hits.size(); i++) {
			String key = hits.get(i).getReferer();
			if (countMap.containsKey(key)) {
				int count = countMap.get(key);
				count++;
				countMap.put(key, count);
			} else {
				countMap.put(key, 1);
			}
		}
		for (Entry<String, Integer> val : countMap.entrySet()) {
			System.out.println(
					val.getKey() + " shows up " + val.getValue() + " times");
		}

		return countMap;
	}

	public Map<String, Integer> getProtocalCounts(ArrayList<Hits> hits) {
		Map<String, Integer> countMap = new TreeMap<String, Integer>();
		for (int i = 0; i < hits.size(); i++) {
			String key = hits.get(i).getProtocal();
			if (countMap.containsKey(key)) {
				int count = countMap.get(key);
				count++;
				countMap.put(key, count);
			} else {
				countMap.put(key, 1);
			}
		}
		for (Entry<String, Integer> val : countMap.entrySet()) {
			System.out.println(
					val.getKey() + " shows up " + val.getValue() + " times");
		}

		return countMap;
	}

	public Map<String, Integer> getTimeCounts(ArrayList<Hits> hits) {
		Map<String, Integer> countMap = new TreeMap<String, Integer>();
		for (int i = 0; i < hits.size(); i++) {
			String dateTime = hits.get(i).getDateTime();
			String[] data = dateTime.split(":");
			String key = data[1] + ":" + data[2];
			if (countMap.containsKey(key)) {
				int count = countMap.get(key);
				count++;
				countMap.put(key, count);
			} else {
				countMap.put(key, 1);
			}
		}
		for (Entry<String, Integer> val : countMap.entrySet()) {
			if (val.getValue() < 20) {
				System.out.println(val.getKey() + " shows up " + val.getValue()
						+ " times");
			}
			else {
				System.err.println(val.getKey() + " shows up " + val.getValue()
				+ " times");
		
			}
		}
		return countMap;
	}
	public Map<String, Integer> getPageCounts(ArrayList<Hits> hits) {
		Map<String, Integer> countMap = new TreeMap<String, Integer>();
		for (int i = 0; i < hits.size(); i++) {
			String key = hits.get(i).getRequest();
			if (countMap.containsKey(key)) {
				int count = countMap.get(key);
				count++;
				countMap.put(key, count);
			} else {
				countMap.put(key, 1);
			}
		}
		for (Entry<String, Integer> val : countMap.entrySet()) {
			if (val.getValue() < 20) {
				System.out.println(val.getKey() + " shows up " + val.getValue()
						+ " times");
			}
			else {
				System.err.println(val.getKey() + " shows up " + val.getValue()
				+ " times");
		
			}
		}
		return countMap;
	}

	public int getTotalData(ArrayList<Hits> hits) {
		int total = 0;
		for (Hits h : hits) {
			total = total + h.getSize();
		}
		return total;
	}
	public int getTotalDataForIP(ArrayList<Hits> hits, String ip) {
		int total = 0;
		for (Hits h : hits) {
			if(h.getiPaddr().equals(ip)) {
			total = total + h.getSize();
			}
		}
		return total;
	}

	public int getTotalHits(ArrayList<Hits> hits) {
		return hits.size();
	}

}

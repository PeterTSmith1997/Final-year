package main;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Analise {
	private Double dbRiskMod = 0.1;
	private Double rawRiskMod = 0.90;

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
			} else {
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
			} else {
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
			if (h.getiPaddr().equals(ip)) {
				total = total + h.getSize();
			}
		}
		return total;
	}

	public int getTotalHits(ArrayList<Hits> hits) {
		return hits.size();
	}

	public double risk(String ip, DataStore dataStore) {
		double risk = 0;
		IPFunctions functions = new IPFunctions();
		System.err.println(functions.getLocation(ip));
		double avTime = dataStore.getOrrcancesOfip().get(ip)
				/ DataStore.monthMins;
		double orrcancesOfipLog = Math
				.log(dataStore.getOrrcancesOfip().get(ip));
		if (orrcancesOfipLog == 0.00) {
			orrcancesOfipLog = 0.01;
		}
		int orrcancesOfip = dataStore.getOrrcancesOfip().get(ip);
		int totalData = getTotalDataForIP(dataStore.getHits(), ip);
		// look at resposes/requests
		double responseRisk = 0;
		double requestRisk = 0;
		for (Hits h : dataStore.getHits()) {
			if (h.getiPaddr().equals(ip)) {
				int response = h.getResponse();
				if (response == 400) {
					responseRisk = +0.5;
				} else if (response == 401) {
					responseRisk = +5;
				} else if (response == 403) {
					responseRisk = +4;
				} else if (response == 404) {
					responseRisk = +2;
				} else if (response == 500) {
					responseRisk = +0.2;
				} else if (response == 429) {
					responseRisk = +2;
				} else if (response == 200) {
					responseRisk = -2;
				}
				if (containIgnoreCase(h.getRequest(), "wp-admin")) {
					requestRisk = +3;
				}
				if (containIgnoreCase(h.getRequest(), "login")) {
					requestRisk = +2;
				}
			}

		}
		assert (rawRiskMod+dbRiskMod == 1);
		risk =  (orrcancesOfipLog * (Math.log(totalData / orrcancesOfip))
				+ avTime + (responseRisk * requestRisk)) *rawRiskMod;
		if (risk > 100) {
			return 100;
		} else {
			return risk;
		}
	}

	/**
	 * @return the dbRiskMod
	 */
	public Double getDbRiskMod() {
		return dbRiskMod;
	}

	/**
	 * @return the rawRiskMod
	 */
	public Double getRawRiskMod() {
		return rawRiskMod;
	}

	/**
	 * @param dbRiskMod the dbRiskMod to set
	 */
	public void setDbRiskMod(Double dbRiskMod) {
		this.dbRiskMod = dbRiskMod;
	}

	/**
	 * @param rawRiskMod the rawRiskMod to set
	 */
	public void setRawRiskMod(Double rawRiskMod) {
		this.rawRiskMod = rawRiskMod;
	}

	private static boolean containIgnoreCase(String str, String sub) {
		return str.toLowerCase().contains(sub.toLowerCase());
	}

}

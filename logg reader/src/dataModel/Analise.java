package dataModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author peter
 * @version 18 Jul 2019
 */
public class Analise {
	private static boolean containIgnoreCase(String str, String sub) {
		return str.toLowerCase().contains(sub.toLowerCase());
	}

	private Double dbRiskMod = 0.1;

	private Double rawRiskMod = 0.90;

	/**
	 *
	 */
	public Analise() {

	}

	/**
	 * @return the dbRiskMod
	 */
	public Double getDbRiskMod() {
		return dbRiskMod;
	}

	/**
	 * sets the map of IP counts
	 * 
	 * @param hits
	 *            - the ArrayList to be sorted
	 * @return The map of sorted IPs with the no. of times in the dataset
	 */
	public HashMap<String, Integer> getIpCounts(ArrayList<Hits> hits) {
		HashMap<String, Integer> countMap = new HashMap<String, Integer>();
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
		return countMap;
	}

	/**
	 *
	 * @param hits
	 * @return
	 */
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

		return countMap;
	}

	/**
	 *
	 * @param hits
	 * @return
	 */
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

		return countMap;
	}

	/**
	 * @return the rawRiskMod
	 */
	public Double getRawRiskMod() {
		return rawRiskMod;
	}

	/**
	 *
	 * @param hits
	 * @return
	 */
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
		return countMap;
	}

	/**
	 *
	 * @param hits
	 * @return
	 */
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
		return countMap;
	}

	/**
	 *
	 * @param hits
	 * @return
	 */
	public int getTotalData(ArrayList<Hits> hits) {
		int total = 0;
		for (Hits h : hits) {
			total = total + h.getSize();
		}
		return total;
	}

	/**
	 *
	 * @param hits
	 * @param ip
	 * @return
	 */
	public int getTotalDataForIP(ArrayList<Hits> hits, String ip) {
		int total = 0;
		for (Hits h : hits) {
			if (h.getiPaddr().equals(ip)) {
				total = total + h.getSize();
			}
		}
		return total;
	}

	/**
	 *
	 * @param hits
	 * @return
	 */
	public int getTotalHits(ArrayList<Hits> hits) {
		return hits.size();
	}

	/**
	 *
	 * @param ip
	 * @param dataStore
	 * @return
	 */
	public double risk(String ip, DataStore dataStore, String countryCode) {
		double risk = 0;
		Database database = new Database();
		if (!database.knownBots(ip).equals("n/a")) {
			risk = risk - 25;
		}
		double orrcancesOfipLog = Math
				.log(dataStore.getOrrcancesOfip().get(ip));
		if (orrcancesOfipLog == 0.00) {
			orrcancesOfipLog = 0.01;
		}
		double countryRisk = database.countryRisk(countryCode); 
		int orrcancesOfip = dataStore.getOrrcancesOfip().get(ip);
		int totalData = getTotalDataForIP(dataStore.getHits(), ip);
		// look at resposes/requests
		double avTime = orrcancesOfip/ DataStore.monthMins;
		double responseRisk = 0;
		double requestRisk = 0;
		for (Hits h : dataStore.getHits()) {
			if (h.getiPaddr().equals(ip)) {
				switch (h.getResponse()) {
				case 400:
					responseRisk = +0.5;
					break;
				case 401:
					responseRisk = +5;
					break;
				case 403:
					responseRisk = +2;
					break;
				case 429:
					responseRisk = +2;
					break;
				case 500:
					responseRisk = +0.2;
					break;
				case 200:
					responseRisk = -1;
					break;
				}
				if (containIgnoreCase(h.getRequest(), "wp-admin")) {
					requestRisk = +3;
				}
				if (containIgnoreCase(h.getRequest(), "login")) {
					requestRisk = +2;
				}
				if (h.getSize() == 0) {
					responseRisk = +6;
				}
			}

		}
		risk = (orrcancesOfipLog * (Math.log(totalData / orrcancesOfip))
				+ avTime + (responseRisk * requestRisk) + countryRisk)
				* rawRiskMod;

		if (risk > 100) {
			return 100;
		} else if (risk < 1) {
			return 1;
		} else {
			return risk;
		}
	}

	/**
	 * @param dbRiskMod
	 *            the dbRiskMod to set
	 */
	public void setDbRiskMod(Double dbRiskMod) {
		this.dbRiskMod = dbRiskMod;
	}

	/**
	 * @param rawRiskMod
	 *            the rawRiskMod to set
	 */
	public void setRawRiskMod(Double rawRiskMod) {
		this.rawRiskMod = rawRiskMod;
	}

}

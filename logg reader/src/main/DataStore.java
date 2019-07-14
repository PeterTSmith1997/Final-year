package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataStore{
	private ArrayList<Hits> hits = new ArrayList<>();
	private ArrayList<String> reportedIps = new ArrayList<>();
	private Map<String, Integer> orrcancesOfip = new HashMap<String, Integer>();
	private Map<String, Integer> referers = new HashMap<String, Integer>();
	private Map<String, Integer> protcals = new HashMap<String, Integer>();
	private Map<String, Integer> pages = new HashMap<String, Integer>();
	private Map<Integer, Integer> responses = new HashMap<Integer, Integer>();
	public static final long monthMins = 43800;
	
		/**
	 * @return the responses
	 */
	public Map<Integer, Integer> getResponses() {
		return responses;
	}

	/**
	 * @param responses the responses to set
	 */
	public void setResponses(Map<Integer, Integer> responses) {
		this.responses = responses;
	}

		/**
	 * @return the reportedIps
	 */
	public ArrayList<String> getReportedIps() {
		return reportedIps;
	}

	/**
	 * @param reportedIps the reportedIps to set
	 */
	public void setReportedIps(ArrayList<String> reportedIps) {
		this.reportedIps = reportedIps;
	}
	public void addReportedIP(String ip) {
		reportedIps.add(ip);
	}

		public DataStore() {}

	/**
	 * @return the hits
	 */
	public ArrayList<Hits> getHits() {
		return hits;
	}

	/**
	 * @param hits the hits to set
	 */
	public void setHits(ArrayList<Hits> hits) {
		this.hits = hits;
	}
	public void addHit(Hits h) {
		hits.add(h);
	}

	/**
	 * @return the orrcancesOfip
	 */
	public Map<String, Integer> getOrrcancesOfip() {
		return orrcancesOfip;
	}

	/**
	 * @param orrcancesOfip the orrcancesOfip to set
	 */
	public void setOrrcancesOfip(Map<String, Integer> orrcancesOfip) {
		this.orrcancesOfip = orrcancesOfip;
	}

	/**
	 * @return the pages
	 */
	public Map<String, Integer> getPages() {
		return pages;
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(Map<String, Integer> pages) {
		this.pages = pages;
	}

	/**
	 * @return the referers
	 */
	public Map<String, Integer> getReferers() {
		return referers;
	}

	/**
	 * @param referers the referers to set
	 */
	public void setReferers(Map<String, Integer> referers) {
		this.referers = referers;
	}

	/**
	 * @return the protcals
	 */
	public Map<String, Integer> getProtcals() {
		return protcals;
	}

	/**
	 * @param protcals the protcals to set
	 */
	public void setProtcals(Map<String, Integer> protcals) {
		this.protcals = protcals;
	}

}

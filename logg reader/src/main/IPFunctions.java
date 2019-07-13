package main;
/**
 * Class to do Regex on ip adress'
 * 
 * @author peter, based off https://www.mkyong.com/regular-expressions/how-to-validate-ip-address-with-regular-expression/
 * @version 1.0.0
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPFunctions {
	private Pattern pattern;
	private Matcher matcher;
	
	private static final String IPADDRESS_PATTERN = 
			"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	/**
	 * 
	 */
	public IPFunctions() {
		pattern = Pattern.compile(IPADDRESS_PATTERN);
	}
	/**
	 * @param ip to ip to validate
	 * @return bool state of ip
	 */
	public boolean validate(String ip) {
		matcher = pattern.matcher(ip);
		return matcher.matches();
	}
	
}

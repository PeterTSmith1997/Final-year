package dataModel;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;

/**
 * @author peter
 * @version 18 Jul 2019
 */
public class IPFunctions {
	
	File database = new File("database\\GeoLite2-City.mmdb");
	
	/**
	 * 
	 */
	public IPFunctions() {
	}

	public String getLocation(String ip) {
		try {
			DatabaseReader reader = new DatabaseReader.Builder(database).build();
			InetAddress ipadress = InetAddress.getByName(ip);
			CityResponse response = reader.city(ipadress);
			Country country = response.getCountry();
			return country.getIsoCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeoIp2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	

}

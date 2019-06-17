import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Reader {
	private Scanner scanner;
	private File logg;
	Menu menu;

	public Reader(Menu menu) {
		this.menu = menu;
	}

	public void readFile() {
		try {
			scanner = new Scanner(logg);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] data = line.split(" ");
			int response = Integer.parseInt(data[8]);
			int size = Integer.parseInt(data[9]);
			String userAgent = "";
			for (int x = 11; x < data.length; x++) {
				userAgent = userAgent + data[x];
			}
			Hits h = new Hits(data[0], data[5] + " " + data[6], data[7],
					data[3] + " " + data[4], response, size, data[10],
					userAgent);
			menu.addHit(h);
			// System.out.println(h.toString());
		}
		Analise analise = new Analise();
		// menu.setOrrcancesOfip(analise.getIpCounts(menu.getHits()));
		// menu.setReferers(analise.getRefererCounts(menu.getHits()));
//		menu.setProtcals(analise.getProtocalCounts(menu.getHits()));
		analise.getTimeCounts(menu.getHits());

	}

	public void setFile(String pathToFile) {
		logg = new File(pathToFile);
	}

}

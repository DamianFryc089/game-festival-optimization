package pl.edu.pwr.student.damian_fryc.lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		ArrayList<Game> games = new ArrayList<>();
		ArrayList<Player> players = new ArrayList<>();
		ArrayList<Table> tables = new ArrayList<>();

		try {
			File gamesF = new File("gry.txt");
			File tablesF = new File("stoliki.txt");
			File playersF = new File("preferencje.txt");

			Scanner scannerG = new Scanner(gamesF);
			while (scannerG.hasNextLine()) {
				String[] row = scannerG.nextLine().split("; ");
				for (int i = 0; i < Integer.parseInt(row[1]); i++) {
					games.add(new Game( Integer.parseInt(row[0]), Integer.parseInt(row[2]), Integer.parseInt(row[3]) ));
				}
			}
			scannerG.close();
//			Scanner scannerT = new Scanner(tablesF);
//			while (scannerT.hasNextLine()) {
//				String[] row = scannerT.nextLine().split("; ");
//				for (int i = 0; i < Integer.parseInt(row[1]); i++) {
//					games.add(new Game( Integer.parseInt(row[0]), Integer.parseInt(row[2]), Integer.parseInt(row[3]) ));
//				}
//			}

		}
		catch (FileNotFoundException e) {
			System.out.println("Not all files were found.");
		}

	}
}
package pl.edu.pwr.student.damian_fryc.lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	static String gamesFileName = "gry.txt";
	static String tablesFileName = "stoliki.txt";
	static String playersFileName = "preferencje.txt";

	static ArrayList<Game> games;
	static ArrayList<Table> tables;
	static ArrayList<Player> players;

	static int chairCount = 0;

	static float[] weights = new float[]{1,1,1};

	static float bestCombinationNumber = Float.NEGATIVE_INFINITY;
	static String bestCombinationString = "";
	static long startTime;

	private static boolean loadFiles() {
        games = new ArrayList<>();
        tables = new ArrayList<>();
        players = new ArrayList<>();

			// loading games
        try (Scanner scannerG = new Scanner(new File(gamesFileName))){
            while (scannerG.hasNextLine()) {
                String[] row = scannerG.nextLine().split("; ");
                for (int i = 0; i < Integer.parseInt(row[1]); i++) {
					if(Integer.parseInt(row[2]) > Integer.parseInt(row[3])) throw new NumberFormatException("Minimum player value cannot be greater than maximum: "
							+ Integer.parseInt(row[2]) + " > " + Integer.parseInt(row[3]));
                    games.add(new Game(Integer.parseInt(row[0]), Integer.parseInt(row[2]), Integer.parseInt(row[3])));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File \""+gamesFileName+"\" not found"); return false;
        } catch (NumberFormatException e) {
            System.out.println("Invalid file content in "+gamesFileName+" - " + e.getMessage()); return false;
        }

			// loading tables
		try (Scanner scannerT = new Scanner(new File(tablesFileName))){
			while (scannerT.hasNextLine()) {
				String[] row = scannerT.nextLine().split("; ");
				tables.add(new Table(Integer.parseInt(row[0]), Integer.parseInt(row[1])));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File \""+tablesFileName+"\" not found"); return false;
		} catch (NumberFormatException e) {
			System.out.println("Invalid file content in "+tablesFileName+" - " + e.getMessage()); return false;
		}

			// loading players
		try (Scanner scannerP = new Scanner(new File(playersFileName))){
			while (scannerP.hasNextLine()) {
					// spliting into id and preferences
				String[] row = scannerP.nextLine().split("; ", 2);
				String[] preferenceStrings = row[1].split(", ");
				int[] preferences = new int[preferenceStrings.length];
				for (int i = 0; i < preferenceStrings.length; i++)
					preferences[i] = Integer.parseInt(preferenceStrings[i]);
				players.add(new Player(Integer.parseInt(row[0]), preferences));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File \""+playersFileName+"\" not found"); return false;
		} catch (NumberFormatException e) {
			System.out.println("Invalid file content in "+playersFileName+" - " + e.getMessage()); return false;
		}
		return true;
	}

	private static void setWeights(Scanner scanner){
		System.out.println("Input W1 - player count weight");
		while (!scanner.hasNextFloat()) scanner.nextLine();
		weights[0] = scanner.nextFloat();

		System.out.println("Input W2 - satisfaction weight");
		while (!scanner.hasNextFloat()) scanner.nextLine();
		weights[1] = scanner.nextFloat();

		System.out.println("Input W3 - table penalty weight");
		while (!scanner.hasNextFloat()) scanner.nextLine();
		weights[2] = scanner.nextFloat();
	}

	private static void optimisation() {

		// sorting players by preferences count
		players.sort(Comparator.comparingInt(p -> p.preferences.length));
		tables.sort(Comparator.comparingInt(p -> p.maxPlayersAtTheTable));
		games.sort(Comparator.comparingInt(p -> p.maxPlayer));

		int maxTableSize = 0;
		for (Table table : tables)
			if(table.maxPlayersAtTheTable > maxTableSize)
				maxTableSize = table.maxPlayersAtTheTable;

			// finding games that require more players than maximum amount of seats
		Iterator<Game> gIter = games.iterator();
		int minGameSize = 9999;
		while (gIter.hasNext()) {
			Game game = gIter.next();
			if(game.minPlayer < minGameSize)  minGameSize = game.minPlayer;
			if (game.maxPlayer > maxTableSize) {
				game.maxPlayer = maxTableSize;
				if(game.maxPlayer < game.minPlayer)
					gIter.remove();
			}
		}
			// removing too small tables
		Iterator<Table> tIter = tables.iterator();
		while (tIter.hasNext()) {
			Table table = tIter.next();
			if (table.maxPlayersAtTheTable < minGameSize)
				tIter.remove();
		}

	}
	private static void checkIfBest(){
		int table_penelty = Table.usedTables - Game.gamesOnTables;
		if(table_penelty > 0) table_penelty = 0;

		float playerSatisfactionCount = 0;
		int playingPlayerCount = 0;
		for (Table table : tables){
			for (Game game : table.gamesOnTable){
				for (Player player : game.assignedPlayers){
					if (player == null) continue;
					playerSatisfactionCount += player.satisfaction;
					playingPlayerCount++;
				}
			}
		}


		float combinationNumber = weights[0] * playingPlayerCount + weights[1] * playerSatisfactionCount + weights[2] * table_penelty;
		if(combinationNumber > bestCombinationNumber){
			bestCombinationNumber = combinationNumber;

			StringBuilder newBest = new StringBuilder();
			for (Table table : tables)
				newBest.append(table);
			newBest.append("\nPlaying player: ").append(playingPlayerCount).append(", out of: ").append(players.size());
			newBest.append("\nUnused seats: ").append(chairCount - playingPlayerCount);
			newBest.append("\nSatisfaction: ").append(playerSatisfactionCount);
			newBest.append("\nTable penalty: ").append(table_penelty);
			newBest.append("\nOverall score: ").append(bestCombinationNumber);
			bestCombinationString = newBest.toString();
		}
	}
	static void assignGameToTable(int i) {
		if(i == tables.size()) {
			checkIfBest();
			return;
		}

		for (int j = 0; j < games.size(); j++) {
			if(games.get(j).playing && games.get(j).assignedToTable == 0) {
				if(!tables.get(i).addGame(games.get(j))) continue;
				assignGameToTable(i+1);
			}
		}

//		if(Table.usedTables == Game.playingGames) {
		if(Game.playingGames == Game.gamesOnTables) {
			checkIfBest();
			tables.get(i).clearGames();
			return;
		}
		assignGameToTable(i+1);
		tables.get(i).clearGames();
	}
	static void assignPlayer(int i) {
		if(bestCombinationNumber != Float.NEGATIVE_INFINITY && (System.currentTimeMillis() - startTime) > 1000 * 3)
			return;
			// all players are assigned
		if(i == players.size()) {
//			for (Game game : games) {
//				if (game.asignedPlayerCount > 0 && !game.playing) return;
//			}
				// assign games to tables
			assignGameToTable(0);
			return;
		}

		Player player = players.get(i);
		for (int j = 0; j < player.preferences.length; j++) {
			for (int k = 0; k < games.size(); k++) {
				if(games.get(k).id == player.preferences[j]) {
//					if(games.get(k).minPlayer - games.get(k).asignedPlayerCount > (players.size() - i)) continue;
					if(!games.get(k).assignPlayer(player, j)) continue; // game has no slots
					assignPlayer(i+1);
					games.get(k).removePlayer(player);
				}
			}
			assignPlayer(i+1);
		}
	}
	static void findBestCombination() {
		assignPlayer(0);
	}
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (!loadFiles()){
			System.out.println("Try to load data again ('x' to exit)");
			if(Objects.equals(scanner.nextLine(), "x"))
				System.exit(1);
		}
//		setWeights(scanner);
		optimisation();
		for (Table table : tables)
			chairCount += table.maxPlayersAtTheTable;

		startTime = System.currentTimeMillis();
		findBestCombination();
		System.out.println(bestCombinationString);
		System.out.println("Time: " + (System.currentTimeMillis() - startTime) / 1000.0);
    }
}
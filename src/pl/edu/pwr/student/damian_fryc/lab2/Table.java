package pl.edu.pwr.student.damian_fryc.lab2;

import java.util.ArrayList;

public class Table {
	static int usedTables = 0;

	int id;
	int maxPlayersAtTheTable;

	ArrayList<Game> gamesOnTable = new ArrayList<>();
	int playersAtTheTable = 0;

	Table(int id, int maxPlayersAtTheTable) {
		this.id = id;
		this.maxPlayersAtTheTable = maxPlayersAtTheTable;
	}

	boolean addGame(Game game) {
		if(playersAtTheTable + game.asignedPlayerCount <= maxPlayersAtTheTable)
		{
			if(gamesOnTable.isEmpty())
				usedTables++;
			gamesOnTable.add(game);
			game.assignedToTable = id;
			Game.gamesOnTables++;
			playersAtTheTable += game.asignedPlayerCount;
			return true;
		}
		else
			return false;
	}

	void clearGames() {
		if(gamesOnTable.isEmpty()) return;

		for (Game game : gamesOnTable) {
			game.assignedToTable = 0;
			Game.gamesOnTables--;
		}
		playersAtTheTable = 0;
		usedTables--;
		gamesOnTable.clear();
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Table ").append(id).append(":\n");
		for(Game game : gamesOnTable){
			stringBuilder.append("- Game ").append(game.id).append(": ").append(game).append("\n");
		}
		stringBuilder.append("\n");
		return stringBuilder.toString();
	}
}

package pl.edu.pwr.student.damian_fryc.lab2;

import java.util.ArrayList;

public class Table {
	int id;
	int maxPlayersAtTheTable;
	ArrayList<Game> gamesOnTable = new ArrayList<>();
	static int usedTables = 0;
//	static int table_penelty = 0;
	Table(int id, int playerCount) {
		this.id = id;
		this.maxPlayersAtTheTable = playerCount;
	}
	boolean addGame(Game game) {
		if(countPlayers() + game.getPlayerCount() <= maxPlayersAtTheTable)
		{
			if(gamesOnTable.isEmpty())
				usedTables++;
			gamesOnTable.add(game);
			Game.usedGames++;
			game.assignedToTable = id;
//			if(gamesOnTable.size() > 1) table_penelty++;
			return true;
		}
		else
			return false;
	}
	void removeGame(Game game){
		gamesOnTable.remove(game);
		Game.usedGames--;
		game.assignedToTable = 0;
//		if(gamesOnTable.size() > 1) table_penelty--;
	}
	void clearGames() {
		for (Game game : gamesOnTable) {
			game.assignedToTable = 0;
			Game.usedGames--;
		}
		if(!gamesOnTable.isEmpty())
			usedTables--;
		gamesOnTable.clear();
	}
	int getGamesOnTableCount() {
		return gamesOnTable.size();
	}

	int countPlayers()
	{
		int count = 0;
		for (Game game : gamesOnTable)
			count += game.getPlayerCount();
		return  count;
	}
//	float countPlayersSatisfaction()
//	{
//		float count = 0;
//		for (Game game : gamesOnTable)
//			count += game.countPlayersSatisfaction();
//		return  count;
//	}
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

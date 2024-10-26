package pl.edu.pwr.student.damian_fryc.lab2;

public class Game {
	static int playingGames = 0;
	static int gamesOnTables = 0;

	int id;
	int minPlayer;
	int maxPlayer;

	Player[] assignedPlayers;

	int assignedToTable = 0;
	int asignedPlayerCount = 0;
	boolean playing = false;

	Game(int id, int minPlayer, int maxPlayer) {
		this.id = id;
		this.minPlayer = minPlayer;
		this.maxPlayer = maxPlayer;
		assignedPlayers = new Player[maxPlayer];
	}
	boolean assignPlayer(Player player, int preferenceNumber){
//		boolean xxx = false;
//		for (int i = 0; i < assignedPlayers.length; i++) {
//			if(xxx && assignedPlayers[i] != null)
//				System.out.println("ASDASD");
//			if(assignedPlayers[i] == null) xxx = true;
//		}

		if(asignedPlayerCount + 1 > maxPlayer) return false;

		assignedPlayers[asignedPlayerCount] = player;
		asignedPlayerCount++;

		if(asignedPlayerCount >= minPlayer) {
			playing = true;
			playingGames++;
		}

		player.satisfaction = (float) 1 / (preferenceNumber+1);
		return true;
	}
	void removePlayer(Player player){

		asignedPlayerCount--;
		assignedPlayers[asignedPlayerCount] = null;
		if(asignedPlayerCount < minPlayer) {
			playing = false;
			playingGames--;
		}
	}

	@Override
	public String toString() {
		StringBuilder playerString = new StringBuilder();
		for (int i = 0; i < assignedPlayers.length; i++) {
			if(assignedPlayers[i] == null) continue;
			playerString.append(assignedPlayers[i].id).append("(").append(String.format("%.2f", assignedPlayers[i].satisfaction)).append("), ");
		}

		return playerString.substring(0, playerString.length() - 2);
	}
}

package pl.edu.pwr.student.damian_fryc.lab2;

public class Game {
	int id;
	int minPlayer;
	int maxPlayer;
	Player[] assignedPlayers;
	int assignedToTable = 0;
	int asignedPlayerCount;

	static int usedGames = 0;

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

		if(asignedPlayerCount >= minPlayer) playing = true;

		player.satisfaction = (float) 1 / (preferenceNumber+1);

		Player.playerSatisfactionCount += player.satisfaction;
		Player.playingPlayerCount++;

		return true;

//		for (int i = 0; i < assignedPlayers.length; i++) {
//			if (assignedPlayers[i] == null) {
//				assignedPlayers[i] = player;
//
//				if(i + 1 >= minPlayer) playing = true;
//
//				for (int j = 0; j < player.preferences.length; j++)
//					if (player.preferences[j] == id)
//						player.satisfaction = (float) 1 / (j+1);
//
//				Player.playerSatisfactionCount += player.satisfaction;
//				Player.playingPlayerCount++;
////				player.setGame(this);
//				return true;
//			}
//		}
//		return false;
	}
	void removePlayer(Player player){

		asignedPlayerCount--;
		assignedPlayers[asignedPlayerCount] = null;
		Player.playerSatisfactionCount -= player.satisfaction;
		Player.playingPlayerCount--;
		if(asignedPlayerCount < minPlayer) playing = false;
//
//		for (int i = 0; i < assignedPlayers.length; i++) {
//			if(assignedPlayers[i] == player) {
//				if(i < minPlayer) playing = false;
//				Player.playerSatisfactionCount -= player.satisfaction;
//				Player.playingPlayerCount--;
//				assignedPlayers[i] = null;
////				player.removeGame();
//			}
//		}
	}
	boolean isPlayerCountGood() {
        return getPlayerCount() >= minPlayer;
	}
	int getPlayerCount() {
//		int playerCount = 0;
//		for (Player assignedPlayer : assignedPlayers) if (assignedPlayer != null) playerCount++;
//		return playerCount;
		return asignedPlayerCount;
	}
//	float countPlayersSatisfaction() {
//		float count = 0;
//		for (Player assignedPlayer : assignedPlayers)
//			if (assignedPlayer != null)
//				count += assignedPlayer.satisfaction;
//		return count;
//	}

	@Override
	public String toString() {
		StringBuilder playerString = new StringBuilder();
		for (int i = 0; i < assignedPlayers.length; i++) {
			if(assignedPlayers[i] == null) continue;
			playerString.append(assignedPlayers[i].id).append(", ");
		}

		return playerString.substring(0, playerString.length() - 2);
	}
}

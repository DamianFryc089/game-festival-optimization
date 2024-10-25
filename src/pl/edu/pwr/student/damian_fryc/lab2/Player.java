package pl.edu.pwr.student.damian_fryc.lab2;

import java.util.Arrays;

public class Player {
	int id;
	int[] preferences;

	static int playingPlayerCount = 0;
	static float playerSatisfactionCount = 0;
//	Game selectedGame = null;
	float satisfaction = 0;

	Player(int id, int[] preferences) {
		this.id = id;
		this.preferences = Arrays.copyOf(preferences, preferences.length);
	}



//	boolean setGame(Game game) {
//		for (int i = 0; i < preferences.length; i++)
//			if (preferences[i] == game.id)
//				satisfaction = (float) 1 / (i+1);
//		if(satisfaction == 0)
//			return false;
//
//		selectedGame = game;
////		playerCount++;
////		playerSatisfactionCount += satisfaction;
//
//		return true;
//	}

//	void removeGame() {
//		if(selectedGame != null) {
////			playerSatisfactionCount -= satisfaction;
//			satisfaction = 0;
//			selectedGame = null;
////			playerCount--;
//		}
//	}


}

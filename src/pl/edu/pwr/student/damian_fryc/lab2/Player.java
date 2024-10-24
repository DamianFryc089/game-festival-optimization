package pl.edu.pwr.student.damian_fryc.lab2;

import java.util.Arrays;

public class Player {
	int id;
	int[] preferences;
	Player(int id, int[] preferences) {
		this.id = id;
		preferences = Arrays.copyOf(preferences, preferences.length);
	}
}

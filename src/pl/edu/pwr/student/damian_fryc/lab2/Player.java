package pl.edu.pwr.student.damian_fryc.lab2;

import java.util.Arrays;

public class Player {
	int id;
	int[] preferences;

	float satisfaction = 0;

	Player(int id, int[] preferences) {
		this.id = id;
		this.preferences = Arrays.copyOf(preferences, preferences.length);
	}
}

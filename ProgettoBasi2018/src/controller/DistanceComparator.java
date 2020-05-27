package controller;

import Entity.Stella;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Stella>{
	@Override
	public int compare(Stella s1, Stella s2) {
		return s1.getDistance() < s2.getDistance() ? -1 : s1.getDistance() == s2.getDistance() ? 0:1;
	}
}

package controller;

import Entity.Stella;

import java.util.Comparator;

public class FluxComparator implements Comparator<Stella>{
	@Override
	public int compare(Stella s1, Stella s2) {
		return s1.getFlux() < s2.getFlux() ? -1 : s1.getFlux() == s2.getFlux() ? 0:1;
	}
}

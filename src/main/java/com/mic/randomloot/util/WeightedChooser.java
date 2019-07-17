package com.mic.randomloot.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class WeightedChooser<E> {

	HashMap<E, Integer> choices;
	Random rnd;
	
	public WeightedChooser() {
		choices = new HashMap<E, Integer>();
		rnd = new Random();
	}
	
	public void addChoice(E choice, int weight) {
		choices.put(choice, weight);
	}
	
	public E getRandomObject() {
		int totalEntries = 0;
		Iterator<Integer> entryIterator = choices.values().iterator();
		while(entryIterator.hasNext()) {
			Integer i = entryIterator.next();
			totalEntries += i;
		}
		System.out.print(totalEntries + " -> ");
		totalEntries = rnd.nextInt(totalEntries);
		System.out.println(totalEntries);
		
		Iterator<E> choiceIterator = choices.keySet().iterator();
		while(choiceIterator.hasNext()) {
			E i = choiceIterator.next();
			if(totalEntries > choices.get(i)) {
				totalEntries = totalEntries - choices.get(i);
			}else {
				return i;
			}
		}
		
		return null;
		
	}
	
	public void clear() {
		choices.clear();
		rnd.setSeed(rnd.nextLong());
	}

}

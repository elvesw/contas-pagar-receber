package br.com.pontek.util;

import java.util.Random;


public class RandomUtil {
	
	public static Integer inteiro(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
}

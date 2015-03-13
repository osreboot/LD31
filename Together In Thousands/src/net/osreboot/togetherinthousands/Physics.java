package net.osreboot.togetherinthousands;

import java.util.ArrayList;


public class Physics {

	public static float spacing = 10f;
	
	public static float maxHealth = 1000;
	public static float maxRetain = 1000;
	
	public static float transferRate = 0.02f;
	
	public static float maxCapacity = 100f;
	
	
	public static float maxSpeedUse = 300;
	public static float maxResistanceUse = 10f;
	public static float zapChance = 1000;
	
	public static float reviveHealth = 10;
	public static float neutralHealth = 5;
	
	public static float distance(float x1, float y1, float x2, float y2){
		return (float)Math.sqrt(((x2 - x1)*(x2 - x1)) + ((y2 - y1)*(y2 - y1)));
	}
	
	public static Cell getNearestCell(float x, float y){
		Cell c = Cell.cells.get(0);
		for(Cell c2 : Cell.cells) if(distance(x, y, c2.o.x, c2.o.y) < distance(x, y, c.o.x, c.o.y)) c = c2;
		return c;
	}
	
	public static ArrayList<Cell> getNearCells(float x, float y, float radius){
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for(Cell c : Cell.cells) if(distance(x, y, c.o.x, c.o.y) < radius) cells.add(c);
		return cells;
	}
	
	public static float cellularFilterR(float r, float g, float b){
		float tempg = g/2;
		float tempb = b/2;
		if(g > 1) tempg = 0.5f;
		if(b > 1) tempb = 0.5f;
		return 1 - tempg - tempb;
	}
	
	public static float cellularFilterG(float r, float g, float b){
		float tempr = r/2;
		float tempb = b/2;
		if(r > 1) tempr = 0.5f;
		if(b > 1) tempb = 0.5f;
		return 1 - tempr - tempb;
	}
	
	public static float cellularFilterB(float r, float g, float b){
		float tempr = r/2;
		float tempg = g/2;
		if(r > 1) tempr = 0.5f;
		if(g > 1) tempg = 0.5f;
		return 1 - tempr - tempg;
	}
}

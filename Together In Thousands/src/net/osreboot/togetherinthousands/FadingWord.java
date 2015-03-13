package net.osreboot.togetherinthousands;

import java.util.ArrayList;

public class FadingWord {

	public static ArrayList<FadingWord> fws = new ArrayList<FadingWord>();
	
	protected float x, y, transparency, tgoal, color;
	
	protected String word;
	
	public FadingWord(float x, float y, String word, float color){
		this.x = x;
		this.y = y;
		this.word = word;
		this.transparency = 0;
		this.tgoal = 0;
		this.color = color;
		fws.add(this);
	}
	
	public void draw(){
		if(this.transparency < this.tgoal) this.transparency += 0.01f; else if(this.transparency > this.tgoal) this.transparency -= 0.01f;
		Font.drawWord(this.word, this.transparency, this.x, this.y, this.color);
	}
	
	public void fadeIn(){
		this.tgoal = 1.0f;
	}
	
	public void fadeOut(){
		this.tgoal = 0f;
	}
	
}

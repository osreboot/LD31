package net.osreboot.togetherinthousands;


public class FalseCell {

	protected float resistance, speed, zap;
	protected float health, retainr, retaing, retainb;
	protected int team;
	protected float x, y;

	public FalseCell(float x, float y, float r, float g, float b, int team, float health, float retain){
		this.resistance = r;
		this.speed = g;
		this.zap = b;
		this.team = team;
		this.health = health;
		this.retainr = retain;
		this.retaing = retain;
		this.retainb = retain;
		this.x = x;
		this.y = y;
	}

}

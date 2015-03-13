package net.osreboot.togetherinthousands;

import java.util.ArrayList;

public class Droplet {

	public static ArrayList<Droplet> droplets = new ArrayList<Droplet>();

	protected Object o;
	protected Cell c;
	protected float goaly, r, g, b, y, ys;
	protected boolean dead = false;
	protected int special = 0;

	public Droplet(Cell cell, float r, float g, float b){
		this.o = new Object(cell.o.x, -32, 16, 16, Physics.cellularFilterR(r, g, b), Physics.cellularFilterG(r, g, b), Physics.cellularFilterB(r, g, b));
		this.r = r;
		this.g = g;
		this.b = b;
		this.goaly = cell.o.y;
		this.y = -32;
		this.ys = 1.0f;
		this.c = cell;
		droplets.add(this);
	}

	public Droplet(Cell cell, float r, float g, float b, int special){
		this.o = new Object(cell.o.x, -32, 16, 16, Physics.cellularFilterR(r, g, b), Physics.cellularFilterG(r, g, b), Physics.cellularFilterB(r, g, b));
		this.r = r;
		this.g = g;
		this.b = b;
		this.goaly = cell.o.y;
		this.y = -32;
		this.ys = 1.0f;
		this.c = cell;
		this.special = special;
		droplets.add(this);
	}

	public void update(){
		if(!this.dead){
			this.y += this.ys;
			this.ys += 0.05f;
			this.o.y = this.y - (this.y%10);
			if(this.o.y + this.ys > this.goaly){
				this.dead = true;
				Object.objects.remove(o);
				if(this.special == 0){
					this.c.resistance += this.r;
					this.c.speed += this.g;
					this.c.zap += this.b;
				}else if(special == 1){
					for(Cell c : Physics.getNearCells(this.c.o.x, this.c.o.y, 40)){
						c.retainr = 1;
						c.retaing = 1;
						c.retainb = 1;
					}
				}else if(special == 2){
					for(Cell c : Physics.getNearCells(this.c.o.x, this.c.o.y, 40)){
						c.retainr = 100;
						c.retaing = 100;
						c.retainb = 100;
					}
				}
				else if(special == 3){
					c.team = 5;
				}
			}
		}
	}

}

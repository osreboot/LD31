package net.osreboot.togetherinthousands;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Object {

	public static ArrayList<Object> objects = new ArrayList<Object>();
	
	protected float x, y, xs = 0, ys = 0, xl, yl;
	
	protected Texture texture;
	
	protected float r, g, b;
	
	protected boolean textured;
	
	public Object(float x, float y, float xl, float yl, Texture texture){
		this.x = x;
		this.y = y;
		this.xl = xl;
		this.yl = yl;
		this.texture = texture;
		this.textured = true;
		objects.add(this);
	}
	
	public Object(float x, float y, float xl, float yl, float r, float g, float b){
		this.x = x;
		this.y = y;
		this.xl = xl;
		this.yl = yl;
		this.r = r;
		this.g = g;
		this.b = b;
		this.textured = false;
		objects.add(this);
	}
	
	public float getCenterX(){
		return this.x + (this.xl/2);
	}
	
	public float getCenterY(){
		return this.y + (this.yl/2);
	}
	
	public void draw(){
		this.x += this.xs;
		this.y += this.ys;
		
		if(this.textured){
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			this.texture.bind();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(this.x, this.y);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(this.x + this.xl, this.y);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(this.x + this.xl, this.y + this.yl);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(this.x, this.y + this.yl);
		}else{
			GL11.glColor3f(this.r, this.g, this.b);
			Main.white.bind();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(this.x, this.y);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(this.x + this.xl, this.y);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(this.x + this.xl, this.y + this.yl);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(this.x, this.y + this.yl);
		}
		GL11.glEnd();
	}
	
}

package by.zti.test;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Draw {
	
	public static void Rect(float x, float y, float width, float hight, float rotation){
		glPushMatrix();
		{
			glTranslatef(x, y, 0);
			glRotatef(rotation, 0, 0, 1);
			glBegin(GL_QUADS);
			{
				glVertex2f(0, 0);
				glVertex2f(0, hight);
				glVertex2f(width, hight);
				glVertex2f(width, 0);
			}
		glEnd();
		}
		glPopMatrix();
	}
	
	public static void Rect(float x, float y, float width, float hight){
		Rect(x, y, width, hight, 0);
	}
}

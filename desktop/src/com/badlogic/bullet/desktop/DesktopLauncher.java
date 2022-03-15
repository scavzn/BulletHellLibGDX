package com.badlogic.bullet.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.bullet.Bullet;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration(); //base
		config.title = "Bullet Hell!";
		config.width = 480;
		config.height = 800;
		new LwjglApplication(new Bullet(), config); //base
	}
}

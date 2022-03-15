package com.badlogic.bullet;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Bullet extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture playerImage;
	private Texture rocketImage;
	private Rectangle player;
	private Rectangle rocket;
	private Array<Rectangle> rockets;
	private long lastRocket;
	private long score = 1; //non puo essere 0 per via di log
	private float playerSpeed = 200f;
	private float rocketSpeed = 300f;
	BitmapFont font;

	@Override
	public void create () {
		font = new BitmapFont();
		batch = new SpriteBatch();
		playerImage = new Texture("player.png");
		rocketImage = new Texture("rocket.png");
		player = new Rectangle();
		player.x = 480 / 2 - 128 / 2;
		player.y = 20;
		rockets = new Array<Rectangle>();
		spawnRocket();

	}

	private void spawnRocket() {
		Rectangle rocket = new Rectangle();
		rocket.x = MathUtils.random(0, 480-63);
		rocket.y = 800;
		rockets.add(rocket);
		lastRocket = TimeUtils.nanoTime();
	}

	private String highScore() {
		String s = "";
		int digits = (int)(Math.log10(score)+1);
		for (int i = 0; i < 8 - digits; i++) { // s.repeat(digits); Java 11+
			s += "0";
		}
		return s + score;
	}

	private void reset() {
		player.x = 480 / 2 - 128 / 2;
		player.y = 20;
		score = 0;
		rockets.clear();
	}
	@Override
	public void render () {
		System.out.println(score);
		ScreenUtils.clear(0, 0, 1, 0.1f);

		batch.begin();
		font.draw(batch, highScore(), 410, 780);
		batch.draw(playerImage, player.x, player.y);
		for (Rectangle rocket : rockets) {
			batch.draw(rocketImage, rocket.x, rocket.y);
		}

		batch.end();

		if(Gdx.input.isKeyPressed(Keys.A))
			player.x -= Gdx.graphics.getDeltaTime() * playerSpeed;
		if(Gdx.input.isKeyPressed(Keys.D))
			player.x += Gdx.graphics.getDeltaTime() * playerSpeed;
		if(Gdx.input.isKeyPressed(Keys.W))
			player.y += Gdx.graphics.getDeltaTime() * playerSpeed;
		if(Gdx.input.isKeyPressed(Keys.S))
			player.y -= Gdx.graphics.getDeltaTime() * playerSpeed;

		if (player.x < 0)
			player.x = 0;
		if (player.x > 480 - 63)
			player.x = 480 - 63;
		if (player.y < 0)
			player.y = 0;
		if (player.y > 800 - 125)
			player.y = 800 - 125;

		if (TimeUtils.nanoTime() - lastRocket > 700000000) //1000000000
			spawnRocket();

		if (TimeUtils.nanoTime() - lastRocket > 100000000)
			score += 1;

		for (Iterator<Rectangle> iter = rockets.iterator(); iter.hasNext(); ) {
			Rectangle rocket = iter.next();
			rocket.y -= rocketSpeed * Gdx.graphics.getDeltaTime();
			if (rocket.y + 63 < 0) {
				iter.remove();
			}
			if (rocket.x < player.x + 63 && rocket.x + 63 > player.x && rocket.y < player.y + 125 && rocket.y + 108 > player.y) {
				reset();
			}
		}

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		playerImage.dispose();
		rocketImage.dispose();
	}
}

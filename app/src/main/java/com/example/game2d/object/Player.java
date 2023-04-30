package com.example.game2d.object;

import static com.example.game2d.Game.getScreenHeight;
import static com.example.game2d.Game.getScreenWidth;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.game2d.Game;
import com.example.game2d.GameLoop;
import com.example.game2d.Joystick;
import com.example.game2d.R;

/**
 * Player is the circular character that the user can control with a touch joystick.
 * Player is an extension of a Circle, which is an extension of a GameObject
 */
public class Player extends Circle {
    private boolean selectedShortHair;
    Bitmap spriteIdleRight;
    Bitmap spriteIdleLeft;
    Bitmap spriteWalkRight;
    Bitmap spriteWalkLeft;
    Bitmap currentSprite;
    Bitmap previousDirection;
    // position boundaries
    private final double relativeMinYpercent = 0.27;
    private final double relativeMaxYpercent = 0.65;
    private final int relativeMinYpixels = (int) (getScreenHeight() * relativeMinYpercent);
    private final int relativeMaxYpixels = (int) (getScreenHeight() * relativeMaxYpercent);
    private final double relativeMinXpercent = 0.04;
    private final double relativeMaxXpercent = 0.82;
    private final int relativeMinXpixels = (int) (getScreenWidth() * relativeMinXpercent);
    private final int relativeMaxXpixels = (int) (getScreenWidth() * relativeMaxXpercent);
    // max speed
    public static final double SPEED_PIXELS_PER_SECOND = 300.0;
    // dimensional analysis lol gives you pixels per update
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private final Joystick joystick;
    private Context context;
    // create as doubles for higher precision

    // player constructor
    public Player(Context context, Joystick joystick, float positionX, float positionY, double radius) {
        // specify parent constructor because there is no default no arg one
        // player color defined in colors xml
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);
        this.joystick = joystick;
        this.context = context;
        // Sprites
        SharedPreferences preferences = context.getSharedPreferences("MY_PREFS", 0);
        selectedShortHair = preferences.getBoolean("shortHairSelection", false);

        if(selectedShortHair) {
            Bitmap originalIdleRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.boy_idle_right);
            spriteIdleRight = Bitmap.createScaledBitmap(originalIdleRight, 200, 200, false);
            Bitmap originalIdleLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.boy_idle_fliped);
            spriteIdleLeft = Bitmap.createScaledBitmap(originalIdleLeft, 200, 200, false);
            Bitmap originalWalkRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.boy_walk_right);
            spriteWalkRight = Bitmap.createScaledBitmap(originalWalkRight, 200, 200, false);
            Bitmap originalWalkLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.boy_walk_left);
            spriteWalkLeft = Bitmap.createScaledBitmap(originalWalkLeft, 200, 200, false);
        }
        else {
            Bitmap originalIdleRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.girl_idle_right);
            spriteIdleRight = Bitmap.createScaledBitmap(originalIdleRight, 200, 200, false);
            Bitmap originalIdleLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.girl_idle_left);
            spriteIdleLeft = Bitmap.createScaledBitmap(originalIdleLeft, 200, 200, false);
            Bitmap originalWalkRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.girl_walk_right);
            spriteWalkRight = Bitmap.createScaledBitmap(originalWalkRight, 200, 200, false);
            Bitmap originalWalkLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.girl_walk_left);
            spriteWalkLeft = Bitmap.createScaledBitmap(originalWalkLeft, 200, 200, false);
        }
        currentSprite = spriteIdleRight;
        previousDirection = currentSprite;
    }

    public void update() {
        // update velocity based on joystick actuator
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        // later can abstract out position updates
        // Don't allow to go off screen
        int height = getScreenHeight();
        int width = getScreenWidth();


        float relativePositionY = positionY / height;
        float relativePositionX = positionX / width;

        // Check for room boundaries in X direction
        if (relativePositionX > relativeMaxXpercent) {
            positionX = relativeMaxXpixels;
        } else if (relativePositionX < relativeMinXpercent) {
            positionX = relativeMinXpixels;
        }
        // Check for room boundaries in Y direction
        if (relativePositionY > relativeMaxYpercent) {
            positionY = relativeMaxYpixels;
        } else if (relativePositionY < relativeMinYpercent) {
            positionY = relativeMinYpixels;
        }

        /*
        if (!Game.canMove) {
            velocityX = 0;
            velocityY = 0;
        }
         */
        // update position
        positionX += velocityX;
        positionY += velocityY;
    }

    public void setPosition(float positionX, float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void draw(Canvas canvas) {
        if (velocityX > 0) {
            currentSprite = spriteWalkRight;
        } else if (velocityX < 0) {
            currentSprite = spriteWalkLeft;
        } else {
            // idle
            // check previous direction
            if (previousDirection == spriteWalkRight) {
                currentSprite = spriteIdleRight;
            } else if (previousDirection == spriteWalkLeft) {
                currentSprite = spriteIdleLeft;
            }
        }

        Paint paint = new Paint();
        canvas.drawBitmap(currentSprite, positionX, positionY, paint);

        previousDirection = currentSprite;
    }
}
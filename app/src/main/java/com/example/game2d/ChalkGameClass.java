package com.example.game2d;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.logging.Handler;

/**
 * Custom view of the ChalkGame/Chalk dodging activity. Draws
 * the selected character repeatedly along with the health system,
 * pause button and chalk. There are also mechanics so the player can
 * jump by tapping on the screen. The aim is to dodge the chalk.
 */

public class ChalkGameClass extends View {
    //PLAYER
    private Bitmap player[] = new Bitmap[2];
    private boolean selectedShortHair; //Stores whether the player selected the short haired character at the start of the game
    private int ranGravity; //random integer that determines whether gravity is normal or not
    private boolean normGravity; //stores whether gravity is normal or not depending on weather ranGravity is even or odd
    private int playerX = 10;
    private int playerY;
    private int minplayerY;
    private int maxplayerY;
    private int playerSpeed;
    private boolean touch = false; //stores whether the user has tapped on the screen;
    private int jumpcount;

    //CANVAS
    private int canvasWidth, canvasHeight;
    private Bitmap backgroundImage ;

    //MEDIA
    MediaPlayer bonk; //sound when player hits their head on the ceiling
    MediaPlayer oof; //sound when player is hit by chalk
    MediaPlayer bossMusic; //background music

    //CHALK
    private Bitmap chalk[] = new Bitmap[5]; //array of bitmaps storing the various chalks
        private int chalkNum = 7;
        private int chalkCol; //random number between 0 and 4 that determines color of chalk
        private int[] chalkX = new int[chalkNum];
        private int[] chalkY = new int[chalkNum];
        private int chalkXSpeed;
        private int chalkPassed;

    //HEALTH SYSTEM
    private Bitmap lives[] = new Bitmap[2];
        private int lifecounter;
        private int lifeXstart; //stores the X coordinate of where the first heart is drawn
        private int lifeY;


    private Bitmap pauseButton;
        private int pauseX = 1800;
        private int pauseY = 20;
    private boolean pauseTouch = false; //stores whether user has tapped on the pause

    SharedPreferences preferences;

    /**
     * Initializes most variables above and calculates ranGravity and determines
     * whether normal or abnormal gravity is used. All the bitmaps are initialized here
     * from the resources.
     * @param context the current context
     */
    public ChalkGameClass(Context context)
    {
        super(context);

        bonk = MediaPlayer.create(getContext(), R.raw.bonk);
        oof = MediaPlayer.create(getContext(), R.raw.oof);
        bossMusic = MediaPlayer.create(getContext(), R.raw.aramid);

        preferences = getContext().getSharedPreferences("MY_PREFS", 0);
        selectedShortHair = preferences.getBoolean("shortHairSelection", false);

       ranGravity = (int) Math.floor(Math.random() * 5) + 1; //ranGravity is a random integer between 1 and 5

        if(ranGravity%2 ==1)
            normGravity = true; //if ranGravity is odd, normal gravity is used where you jump up but rest at the bottom
        else
            normGravity = false; //if ranGravity is even, abnormal gravity is used where you jump down but rest at the top


        if(normGravity) {
            playerY = 650; //make the player rest at the bottom if normal gravity
            if(selectedShortHair == false)
            {
                player[0] = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
                player[1] = BitmapFactory.decodeResource(getResources(), R.drawable.girl_jump_big);
            }
            else
            {
                player[0] = BitmapFactory.decodeResource(getResources(), R.drawable.boy_big);
                player[1] = BitmapFactory.decodeResource(getResources(), R.drawable.boy_jump_big);
            }

            backgroundImage= BitmapFactory.decodeResource(getResources(), R.drawable.background_normal);
            lives[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
            lives[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);
            lifeY = 20;

        }
        else {
            playerY = 0; //make the player rest at the top if abnormal gravity
            if(selectedShortHair == false) {
                player[0] = BitmapFactory.decodeResource(getResources(), R.drawable.girl_upsidedown);
                player[1] = BitmapFactory.decodeResource(getResources(), R.drawable.girl_jump_upsidedown);
            }
            else {
                player[0] = BitmapFactory.decodeResource(getResources(), R.drawable.boy_idle_upsidedown);
                player[1] = BitmapFactory.decodeResource(getResources(), R.drawable.boy_jump_upsidedown);
            }
            backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.unspide_down);
            lives[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts_upsidedown);
            lives[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey_upsidedown);
        }

        jumpcount = 0;
        lifeXstart = 1430;


        //Initializing the chalk array
        chalk[0] = BitmapFactory.decodeResource(getResources(), R.drawable.green_chalk);
        chalk[1] = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_chalk);
        chalk[2] = BitmapFactory.decodeResource(getResources(), R.drawable.red_chalk);
        chalk[3] = BitmapFactory.decodeResource(getResources(), R.drawable.white_chalk);
        chalk[4] = BitmapFactory.decodeResource(getResources(), R.drawable.blue_chalk);
        chalkPassed = 0;

        lifecounter = 3;

        pauseButton = BitmapFactory.decodeResource(getResources(), R.drawable.pause_new);

        chalkXSpeed = 20;
        // boss music start
        bossMusic.start();

    }

    /**
     * Creates the canvas and draws all the elements mentioned at the beginning. Also manages
     * the background music and sound effects as well as the mechanisms for movements
     * and the health system.
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        if (backgroundImage == null) return;
        // Use the same Matrix over and over again to minimize
        // allocation in onDraw.


        Matrix matrix = new Matrix();
        matrix = StretchMatrix(matrix, canvasWidth, canvasHeight, backgroundImage);

        canvas.drawBitmap(backgroundImage, matrix, null);
        int floorHeight = 70;

        if (normGravity)
        {
            minplayerY = 0;

            maxplayerY = canvasHeight - player[1].getHeight() - floorHeight;
        }
        else
        {
            minplayerY = 0 + floorHeight;
            maxplayerY = canvasHeight - player[1].getHeight();
        }
        //PLAYER MECHANICS
        if(normGravity)
            playerY = playerY + playerSpeed; //makes character jump up and naturally fall down
        else
            playerY = playerY - playerSpeed; //makes character jump down and naturally float up

        if (playerY < minplayerY) {
            playerY = minplayerY;
            touch = false;
            if(normGravity)
            {
                playerSpeed = 125;
                bonk.start();
            }
        }       //limiting the player's y to the top of the screen

        if (playerY > maxplayerY) {
            playerY = maxplayerY;
            touch = false;
            if(!normGravity)
            {
                playerSpeed = 125;
                bonk.start();
            }
        }       //limiting the player's y tot he bottom of the screen

        playerSpeed = playerSpeed + 2; //gravity

        if (touch) { //jumping animation
            if (jumpcount > 0) { //double jump, draw idle character before drawing jump again
                canvas.drawBitmap(player[0], playerX, playerY, null);
                jumpcount = 0; //reset double jump
            } else
                canvas.drawBitmap(player[1], playerX, playerY, null);

        } else {
            canvas.drawBitmap(player[0], playerX, playerY, null);
        }

        //CHALK MECHANICS

        for(int ii = 0; ii < chalkNum; ii ++) {
            if (hitChalkChecker(chalkX[ii], chalkY[ii])) //if player collides with chalk
            {
                chalkX[ii] = chalkX[ii] - 200; //makes chalk disappear
                oof.start();
                lifecounter--; //reduces health;

                if(lifecounter == 0)  //if all lives used up
                {
                    bossMusic.stop();
                    Intent RIPIntent = new Intent(getContext(), ChalkGameRIPActivity.class);
                    RIPIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getContext().startActivity(RIPIntent);

                }
            }

            chalkX[ii] = chalkX[ii] - chalkXSpeed; //making the chalk move across the screen from right to left
            if (chalkX[ii] < 0)
            {
                chalkPassed++;
                chalkX[ii] = canvasWidth + (ii * 200);
                if (ii == chalkNum - 1)
                    chalkXSpeed += 2;
                chalkY[ii] = (int) Math.floor(Math.random() * ((maxplayerY + player[0].getHeight()) - chalk[0].getHeight())) + chalk[0].getHeight(); //making the chalk appear at random  heights
            }


            if(chalkPassed == chalkNum * 3)
            {
                chalkPassed = 0;
                bossMusic.stop();

                    Intent returnQuiz = new Intent(getContext(), ChalkToQuizActivity.class);
                    returnQuiz.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    getContext().startActivity(returnQuiz);
                    finishFunction();

            }

            bossMusic.start();
            chalkCol = ii;
            while(chalkCol > 4) //keeping chalkCol between 0 and 4;
            {
                chalkCol-= 5;
            }

            canvas.drawBitmap(chalk[chalkCol], chalkX[ii], chalkY[ii],  null);
        }

        //Health System Display

        for(int ii = 0; ii < 3; ii++)
        {
            int lifeX = (int) (lifeXstart + lives[0].getWidth() * 1.5 * ii);


            if(ii < lifecounter)
            {
                canvas.drawBitmap(lives[0], lifeX, lifeY, null); //drawing full heart
            }
            else
            {
                canvas.drawBitmap(lives[1], lifeX, lifeY, null); //drawing lost heart
            }
        }

        //PAUSE BUTTON
        canvas.drawBitmap(pauseButton, pauseX, pauseY, null);
        if (pauseTouch == true)
        {
            bossMusic.stop();
            pauseTouch = false;
            Intent pauseIntent = new Intent(getContext(), PauseWindow.class);
            pauseIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            getContext().startActivity(pauseIntent);

        }


    }

    /**
     * Scales a matrix to scale a bitmap to specific dimensions
     * @param fnMatrix the matrix to be scaled
     * @param x the width to scale to
     * @param y the height to scale to
     * @param img the bitmap to sclale
     * @return
     */
    private Matrix StretchMatrix(Matrix fnMatrix, int x, int y, Bitmap img)
    {
        float vw = x;
        float vh = y;
        float bw = (float) img.getWidth ();
        float bh = (float) img.getHeight ();

        // Scale the bitmap to fit into the view.
        float s1x = vw / bw;
        float s1y = vh / bh;
       fnMatrix.postScale (s1x, s1y);

       return fnMatrix;
    }

    /**
     * Checks if the user has tapped on the screen and calls onSingelTap
     * as well as allows the user to jump when they aren't tapping on
     * the pause button
     * @param event the instance of the user interacting/ tapping on the screen
     * @return boolean value (unused)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        pauseTouch = onSingleTap(event);
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            if(pauseTouch == false)
            {
                touch = true;
                playerSpeed = -30;
                jumpcount++;
            }
        }
        return true;
    }

    /**
     * Checks if the chalk has collided with the player
     * @param x the chalk's x coordinate
     * @param y the chalk's y coordinate
     * @return boolean value of whether or not the object/chalk collided with the player
     */
    public boolean hitChalkChecker(int x, int y)
    {
        if(playerX < x && x < (playerX + player[0].getWidth())
                && playerY < y && y < (playerY + player[0].getHeight()))
        {
            return true;
        }
        return false;
    }

    /**
     * Checks if the user has touched the pause button
     * @param event the instance of the user interaction/tapping ont he screen
     * @return boolean value of whether or not the user has tapped the pause button.
     */
    public boolean onSingleTap(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if ((pauseX < x && x < (pauseX + pauseButton.getWidth()) //if user touches pause button
                && pauseY < y && y < (pauseY + pauseButton.getHeight())))
        {
            return true;
        }

        return false;
    }

    /**
     * Checks if the activity/window is in focus to see
     * if the player is interacting with it or another activity at the moment
     * @param hasWindowFocus boolean determining if the window is in focus or not.
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) { //onresume() called
            bossMusic.start();
        } else { // onPause() called
            bossMusic.pause();
        }
    }

    /**
     * A substitute for the finish() for a view,
     * kills the view/activity when called.
     */
    private void finishFunction() {
        Activity activity = (Activity)getContext();
        activity.finish();
    }
}

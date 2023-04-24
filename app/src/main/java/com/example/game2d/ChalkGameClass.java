package com.example.game2d;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class ChalkGameClass extends View {
    //PLAYER
    private Bitmap player[] = new Bitmap[2];
    private int playerX = 10;
    private int playerY;
    private int playerSpeed;
    private boolean touch = false;
    private int jumpcount;

    //CANVAS
    private int canvasWidth, canvasHeight;
    private Bitmap backgroundImage ;

    //CHALK
    private Bitmap chalk[] = new Bitmap[5];
        private int chalkNum = 7;
        private int chalkCol;
        private int[] chalkX = new int[chalkNum];
        private int[] chalkY = new int[chalkNum];
        private int chalkXSpeed = 20;
        private int chalkPassed;

    //HEALTH SYSTEM
    private Bitmap lives[] = new Bitmap[2];
    private int lifecounter;
    private Paint lostLifePaint = new Paint();

    private Bitmap pauseButton;
        private int pauseX = 20;
        private int pauseY = 20;
    private boolean pauseTouch = false;

    private Bitmap hintButton;
        private int hintX = 110;
        private int hintY = 20;



    public ChalkGameClass(Context context)
    {
        super(context);
        player[0] = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
        player[1] = BitmapFactory.decodeResource(getResources(), R.drawable.girl_jump);
        playerY = 650;
        jumpcount = 0;

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        //Initializing the chalk array
        chalk[0] = BitmapFactory.decodeResource(getResources(), R.drawable.green_chalk);
        chalk[1] = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_chalk);
        chalk[2] = BitmapFactory.decodeResource(getResources(), R.drawable.red_chalk);
        chalk[3] = BitmapFactory.decodeResource(getResources(), R.drawable.white_chalk);
        chalk[4] = BitmapFactory.decodeResource(getResources(), R.drawable.blue_chalk);
        chalkPassed = 0;

        lives[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        lives[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);
        lifecounter = 3;

        lostLifePaint.setColor(Color.WHITE);
        lostLifePaint.setAntiAlias(false);

        pauseButton = BitmapFactory.decodeResource(getResources(), R.drawable.pause);
        hintButton = BitmapFactory.decodeResource(getResources(), R.drawable.question);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        if (backgroundImage == null) return;
        // Use the same Matrix over and over again to minimize
        // allocation in onDraw.
        Matrix matrix = new Matrix();

        float vw = canvasWidth;
        float vh = canvasHeight;
        float bw = (float) backgroundImage.getWidth ();
        float bh = (float) backgroundImage.getHeight ();

        // First scale the bitmap to fit into the view.
        float s1x = vw / bw;
        float s1y = vh / bh;
        matrix.postScale (s1x, s1y);

        canvas.drawBitmap(backgroundImage, matrix, null);

        int minplayerY = 0;
        int floorHeight = 80;
        int maxplayerY = canvasHeight - player[0].getHeight() - floorHeight;

        //PLAYER MECHANICS
        playerY = playerY + playerSpeed;

        if (playerY < minplayerY) {
            playerY = minplayerY;
            touch = false;
            playerSpeed = 125;
        }       //limiting the player's y to the top of the screen

        if (playerY > maxplayerY) {
            playerY = maxplayerY;
            touch = false;
        }       //limiting the player's y tot he bottom of the screen
        playerSpeed = playerSpeed + 2; //gravity

        if (touch) { //jumping animation
            if (jumpcount > 0) { //double jump
                canvas.drawBitmap(player[0], playerX, playerY, null);
                jumpcount = 0;
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
                lifecounter--; //reduces health;

                if(lifecounter == 0)  //if all lives used up
                {
                    Intent mainIntent = new Intent(getContext(), MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getContext().startActivity(mainIntent);

                }
            }
            chalkX[ii] = chalkX[ii] - chalkXSpeed; //making the chalk move across the screen


            if (chalkX[ii] < 0)
            {
                chalkPassed++;
                chalkX[ii] = canvasWidth + (ii * 200);
                if(ii == chalkNum -1)
                    chalkXSpeed += 2;
                chalkY[ii] = (int) Math.floor(Math.random() * ((maxplayerY + player[0].getHeight()) - chalk[0].getHeight())) + chalk[0].getHeight(); //making the chalk appear at random  heights
            }

            if(chalkPassed == chalkNum * 3)
            {
                chalkX[ii] = chalkX[ii] - 200;
                chalkPassed = 0;
                Intent chalkQuestionIntent  = new Intent(getContext(), ChalkActivity.class);
                chalkQuestionIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                getContext().startActivity(chalkQuestionIntent);
            }
            chalkCol = ii;
            while(chalkCol > 4) //keeping chalkCol between 0 and 4;
            {
                chalkCol-= 5;
            }
            //chalkY[ii] = chalkY[ii] - chalkYSpeed; //gravity
            canvas.drawBitmap(chalk[chalkCol], chalkX[ii], chalkY[ii],  null);
        }

        //Health System Display

        for(int ii = 0; ii < 3; ii++)
        {
            int lifeX = (int) (1550 + lives[0].getWidth() * 1.5 * ii);
            int lifeY = 30;

            if(ii < lifecounter)
            {
                canvas.drawBitmap(lives[0], lifeX, lifeY, null); //drawing full heart
            }
            else
            {
                canvas.drawBitmap(lives[1], lifeX, lifeY, lostLifePaint); //drawing lost heart
            }
        }

        //PAUSE BUTTON
        canvas.drawBitmap(pauseButton, pauseX, pauseY, null);
        if (pauseTouch == true)
        {
            pauseTouch = false;
            Intent pauseIntent = new Intent(getContext(), ChalkGamePauseWindow.class);
            pauseIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            getContext().startActivity(pauseIntent);

        }

        canvas.drawBitmap(hintButton,hintX, hintY, null);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        pauseTouch = onSingleTap(event);
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            touch = true;
            playerSpeed = -30;
            jumpcount ++;
        }
        return true;
    }

    public boolean hitChalkChecker(int x, int y)
    {
        if(playerX < x && x < (playerX + player[0].getWidth())
                && playerY < y && y < (playerY + player[0].getHeight()))
        {
            return true;
        }
        return false;
    }

    public boolean onSingleTap(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if ((pauseX < x && x < (pauseX + pauseButton.getWidth()) //if user touches pause button
                && pauseY < y && y < (pauseY + pauseButton.getHeight())) ||

                ( hintX < x && x < (hintX + hintButton.getWidth()) // or if user touches hint button
                    && hintY < y && y < (hintY + hintButton.getHeight())) )
        {
            return true;
        }

        return false;
    }
}

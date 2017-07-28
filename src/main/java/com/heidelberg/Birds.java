package com.heidelberg;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Birds extends PApplet {

    ArrayList<Ball> balls = new ArrayList<Ball>();

    Scrollbar scrollbar[] = new Scrollbar[1];
    Slider slider[] = new Slider[1];



    class Ball {
        PVector location;
        PVector velocity;
        PVector change;
        float r;
        float g;
        float b;

        Ball() {
            this.location = new PVector(random(1, width - 1), random(1, height - 1));
            this.velocity = new PVector(random(-40, 40), random(-40, 40));
            this.change = new PVector(5,-5);
            this.r = random(255);
            this.g = random(255);
            this.b = random(255);


        }


        /*void gravity() {
            this.velocity.x = velocity.x + 0;
            this.velocity.y = velocity.y + 1;
        }*/

        void move() {
            this.location.add(velocity);
        }


        void drawCircle() {
            fill(this.r, this.g, this.b);
            ellipse(this.location.x, this.location.y, 20, 20);
        }

        float distanceTo(float x, float y) {
            float dx = x - location.x;
            float dy = y - location.y;
            return sqrt(dx * dx + dy * dy);
        }
    }




    void collide() {

        for (int i = 0; i < balls.size(); i++) {
            Ball ball1 = balls.get(i);
            for (int k = 0; k < i; k++) {
                Ball ball2 = balls.get(k);
                if (ball1.distanceTo(ball2.location.x, ball2.location.y) < 10) {
                    //println("Boom");
                    ball2.velocity.x = ball2.velocity.x * -1;
                    ball2.velocity.y = ball2.velocity.y * -1;
                    ball1.velocity.x = ball1.velocity.x * -1;
                    ball1.velocity.y = ball1.velocity.y * -1;

                   /* float x = (ball1.location.x + ball2.location.x) / 2;
                    float y = (ball1.location.y + ball2.location.y) / 2;
                    float phi = random(TWO_PI);
                    float dx = cos(phi) * 20;
                    float dy = sin(phi) * 20;

                    ball1.location.x = x + dx;
                    ball1.location.y = y + dy;
                    ball2.location.x = x - dx;
                    ball2.location.y = y - dy;*/
                }
                /*if (ball1.distanceTo(ball2.location.x, ball2.location.y) > 20){
                    ball2.velocity.x = ball2.velocity.x * -1;
                    ball2.velocity.y = ball2.velocity.y * -1;
                    ball1.velocity.x = ball1.velocity.x * -1;
                    ball1.velocity.y = ball1.velocity.y * -1;

                   float x = (ball1.location.x + ball2.location.x) / 2;
                    float y = (ball1.location.y + ball2.location.y) / 2;
                    float phi = random(TWO_PI);
                    float dx = cos(phi) * 20;
                    float dy = sin(phi) * 20;

                    ball1.location.x = x + dx;
                    ball1.location.y = y + dy;
                    ball2.location.x = x - dx;
                    ball2.location.y = y - dy;
                }*/
            }
        }

    }











    class Scrollbar{
        PVector location;
        Scrollbar(){
            location = new PVector(10, 0);
        }
        void drawScrollbar(){
            rect(location.x, location.y, 20, 100);
        }
    }
    class Slider{
        PVector location;
        Slider(){
            location = new PVector(10,0);
        }
        void drawSlider(){
            rect(10, location.y,20, 10);
        }
    }



    @Override
    public void settings() {
        size(1000,1000);
        smooth();
    }

    public void setup() {

        println("Test");

        background(255);

        stroke(0);
        for (int i = 0; i < 12; i++) {
            Ball newBall = new Ball();
            balls.add(newBall);
        }

    }

    public void draw() {

        //frameRate(slider[0].location.y+1);
        //println();
        background(255);
        stroke(0);
        fill(250);
        rect(0, 0, width - 1, height - 1);
        //scrollbar[0].drawScrollbar();
       // slider[0].drawSlider();

        for (Ball balli : balls) {
            balli.drawCircle();
            balli.move();

        }

        collide();





        fill(0);
        text("fps:" + frameRate, 40,40);

    }

    public void mousePressed() {

    }
    public void keyPressed(){
        if (keyCode == DOWN){
            slider[0].location.y = slider[0].location.y +1;
        }
        if (keyCode == UP){
            slider[0].location.y = slider[0].location.y -1;
        }
        if (slider[0].location.y >= 100){
            slider[0].location.y = 100;
        }
        if (slider[0].location.y <= 0){
            slider[0].location.y = 0;
        }

    }
    public void keyReleased(){

    }
}

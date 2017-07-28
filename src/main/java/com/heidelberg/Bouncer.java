package com.heidelberg;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Bouncer extends PApplet {

    ArrayList<Ball> balls = new ArrayList<Ball>();
    Border border[] = new Border[1];
    ArrayList<Hole> holes = new ArrayList<Hole>();
    Block block[] = new Block[1];
    Scrollbar scrollbar[] = new Scrollbar[1];
    Slider slider[] = new Slider[1];
    Bumper bumper[] = new Bumper[1];
    Bumper2 bumper2[] = new Bumper2[1];
    Corner corner[] = new Corner[1];
    Corner2 corner2[] = new Corner2[1];

    void protect(){
        for(Ball ball : balls){
            if ( ball.location.x >= width - 100){
                if ( ball.location.y >= height - 100){
                    ball.location.x = 350;
                    ball.location.y = 350;

                }
            }
            if ( ball.location.x <= 100){
                if ( ball.location.y <= height - 100){
                    ball.location.x = 350;
                    ball.location.y = 350;

                }
            }
        }
    }

    void overlapped() {
        for (int t = 0; t < holes.size(); t++) {
            Hole holes1 = holes.get(t);
            for (int h = 0; h < t; h++) {
                Hole holes2 = holes.get(h);
                if (holes1.distanceTo(holes2.location.x, holes2.location.y) < 40) {
                    holes1.location.x = holes2.location.x - 40;
                    holes1.location.y = holes2.location.y - 40;
                }
            }
        }
    }

    void collide() {

        for (int i = 0; i < balls.size(); i++) {
            Ball ball1 = balls.get(i);
            for (int k = 0; k < i; k++) {
                Ball ball2 = balls.get(k);
                if (ball1.distanceTo(ball2.location.x, ball2.location.y) < 20) {
                    //println("Boom");
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
                }
            }
        }

    }

    void collision() {
        for (Ball ball : balls) {
            if (ball.distanceTo(border[0].location.x, border[0].location.y) < 50) {
                ball.velocity.x = ball.velocity.x * -1;
                ball.velocity.y = ball.velocity.y * -1;
            }
        }
    }

    void hit() {
        for (Ball ball : balls) {
            if (ball.distanceTo(bumper[0].location.x, bumper[0].location.y) < 105) {
                ball.velocity.x = ball.velocity.x * -2;
                ball.velocity.y = ball.velocity.y * -2;
            }

        }
    }

    void hit2() {
        for (Ball ball : balls) {
            if (ball.distanceTo(bumper2[0].location.x, bumper2[0].location.y) < 105) {
                ball.velocity.x = ball.velocity.x * -2;
                ball.velocity.y = ball.velocity.y * -2;
            }
        }
    }

    void trapped() {

        ArrayList<Ball> badBalls = new ArrayList<Ball>();
        for (Ball ball : balls) {
            for (int t = 0; t < holes.size(); t++) {
                for(Hole hole : holes){
                    if (ball.distanceTo(hole.location.x, hole.location.y) < 10) {

                        badBalls.add(ball);
                        print("Badballs:" + badBalls.size());
                        println("balls:" + balls.size());
                    }
                }
            }
        }

        balls.removeAll(badBalls);
        if(balls == badBalls){
            println("Caught all!!!");
            println("Caught all!!!");
        }
    }

    void jump() {
        for (Ball ball : balls) {
            for (int w = 0; w < block.length; w++) {
                if (ball.distanceTo(block[w].location.x, block[w].location.y) <50) {
                    ball.velocity.x = ball.velocity.x * -2;
                    ball.velocity.y = ball.velocity.y * -2;
                }
            }
        }
        for (Ball ball : balls){
            if (ball.location.y > height - 60) {
                if (ball.location.x > 310){

                    if (ball.location.x < 390){
                        ball.location.y = height - 60;
                    }
                }
            }
        }
    }

    void cornercollision() {
        for (Ball ball : balls) {
            if (ball.distanceTo(corner[0].location.x, corner[0].location.y) < 105) {
                ball.velocity.x = ball.velocity.x * -2;
                ball.velocity.y = ball.velocity.y * -2;
            }

        }
    }

    void cornercollision2() {
        for (Ball ball : balls) {
            if (ball.distanceTo(corner2[0].location.x, corner2[0].location.y) < 105) {
                ball.velocity.x = ball.velocity.x * -2;
                ball.velocity.y = ball.velocity.y * -2;
            }

        }
    }

    @Override
    public void settings() {
        size(700, 700);
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
        border[0] = new Border();
        for (int t = 0; t < 5; t++) {
            Hole newHole = new Hole();
            holes.add(newHole);
        }
        for (int w = 0; w < block.length; w++) {
            block[w] = new Block();
        }
        scrollbar[0] = new Scrollbar();
        slider[0] = new Slider();
        bumper[0] = new Bumper();
        bumper2[0] = new Bumper2();
        corner[0] = new Corner();
        corner2[0] = new Corner2();
    }

    public void draw() {

        frameRate(slider[0].location.y+1);
        //println();
        background(255);
        stroke(0);
        fill(250);
        rect(0, 0, width - 1, height - 1);
        scrollbar[0].drawScrollbar();
        slider[0].drawSlider();

        for (Ball balli : balls) {
            balli.drawCircle();
            balli.move();
            balli.gravity();
        }

        collide();
        border[0].drawBorder();
        collision();
        for (Hole holet : holes) {

            holet.drawHoles();



        }
        overlapped();
        for (int w = 0; w < block.length; w++) {
            block[w].drawBlock();
        }
        jump();
        trapped();
        fill(0);
    text("fps:" + frameRate, 40,40);
    bumper[0].drawBumper();
    hit();
    bumper2[0].drawBumper2();
    hit2();
        corner[0].drawCorner();
        corner2[0].drawCorner2();
        cornercollision();
        cornercollision2();
        protect();
    }

    public void mousePressed() {
        border[0].location.x = mouseX;
        border[0].location.y = mouseY;

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
        if (keyCode == TAB){
            bumper[0].location2.y = 600;
            bumper2[0].location2.y = 600;
        }
    }

    public void keyReleased(){
        if (keyCode == TAB){
            bumper[0].location2.y = height;
            bumper2[0].location2.y = height;
        }
    }

    class Border {
        PVector location;
        float r1;
        float g1;
        float b1;

        Border() {
            r1 = random(255);
            g1 = random(255);
            b1 = random(255);
            location = new PVector();
        }

        void drawBorder() {
            fill(r1, g1, b1);
            line(location.x - 50, location.y, location.x + 50, location.y);
        }
    }

    class Bumper2{
        PVector location;
        PVector location2;
        Bumper2(){
            location = new PVector(0,height - 100);
            location2 = new PVector(100 , height);
        }
        void drawBumper2(){
            line(location.x, location.y, location2.x,location2.y);
        }
    }

    class Ball {
        PVector location;
        PVector velocity;
        PVector change;
        float r;
        float g;
        float b;

        Ball() {
            this.location = new PVector(random(1, width - 1), random(1, height - 1));
            this.velocity = new PVector(random(-4, 4), random(-4, 4));
            this.change = new PVector(5,-5);
            this.r = random(255);
            this.g = random(255);
            this.b = random(255);


        }


        void gravity() {
            this.velocity.x = velocity.x + 0;
            this.velocity.y = velocity.y + 1;
        }

        void move() {
            this.location.add(velocity);
            if ((this.location.x > width - 25) || (this.location.x < 25)) {
                this.velocity.x = -this.velocity.x;
            }
            if ((this.location.y > height-25) || (this.location.y < 25)) {
                this.velocity.y = -this.velocity.y;
            }
            if (this.location.y > height - 25) {
                this.location.y = height - 35;
            }
            if (this.location.y <  25) {
                this.location.y = 35;
            }
            if (this.location.x > width - 25) {
                this.location.x = width - 35;
            }
            if (this.location.x <  25) {
                this.location.x =  35;
            }

            if ( this.location.x > width - 100){
                if ( this.location.y > height - 70){
                    this.location.x = 350;
                    this.location.y = 350;
                }
            }
            if ( this.location.x < 100){
                if ( this.location.y < height - 70){
                    this.location.x = 350;
                    this.location.y = 350;
                }
            }
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

    class Block {
        PVector location;
        float r3;
        float g3;
        float b3;

        Block() {
            location = new PVector(350, height);
            r3 = random(255);
            g3 = random(255);
            b3 = random(255);
        }

        void drawBlock() {
            fill(this.r3, this.g3, this.b3);

            rect(location.x, location.y - 15, 40, 20);
            rect(location.x, location.y - 15, -40, 20);
        }

    }

    class Corner{
        PVector location;
        Corner(){

            location = new PVector(0,0);
        }
        void drawCorner(){
            noFill();
            triangle(location.x,location.y,100,0,0,100);
        }
    }

    class Corner2{
        PVector location;
        Corner2(){

            location = new PVector(width,0);
        }
        void drawCorner2(){
            noFill();
            triangle(location.x,location.y,width - 100,0,width,100);
        }
    }

    class Hole {
        float r2;
        float g2;
        float b2;
        PVector location;

        Hole() {
            r2 = random(255);
            g2 = random(255);
            b2 = random(255);

            location = new PVector(random(45, width - 45), random(45, height - 45));
        }

        void drawHoles() {
            fill(r2, g2, b2);
            ellipse(location.x, location.y, second(), second());

        }
        float distanceTo(float x, float y) {
            float dx = x - location.x;
            float dy = y - location.y;
            return sqrt(dx * dx + dy * dy);

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

    class Bumper{
        PVector location;
        PVector location2;
        Bumper(){
            location = new PVector(width,height-100);
            location2 = new PVector(width-100 , height);
        }
        void drawBumper(){
            line(location.x, location.y, location2.x,location2.y);
        }
        float distanceTo(float x, float y) {
            float dx = x - location.x;
            float dy = y - location.y;
            return sqrt(dx * dx + dy * dy);

        }
    }
}



package com.heidelberg;

import processing.core.PApplet;

public class Cellular extends PApplet {

    Grid grid;

    @Override
    public void settings() {
        size(1000, 1000);
        smooth();
    }

    @Override
    public void setup() {
        grid = new Grid(100);
        grid.kindle();
        grid.noWood();
        frameRate(1);
    }

    @Override
    public void draw() {
        grid.draw();
        grid.process();
    }

    enum State {
        BURNING,
        BURNED,
        GREEN,
        WOODLESS,
        EXTINGUISHED
    }

    class Grid {
        int n;
        Automaton[] automata;

        Grid(int n) {
            automata = new Automaton[n * n];
            for (int ix = 0; ix < n; ix++) {
                for (int iy = 0; iy < n; iy++) {
                    automata[ix + iy * n] = new Automaton(ix, iy);
                }
            }
            this.n = n;

        }

        Automaton getAutomaton(int ix, int iy) {
            return automata[ix + n * iy];
        }

        void draw() {
            for (int ix = 0; ix < n; ix++) {
                for (int iy = 0; iy < n; iy++) {
                    automata[ix + iy * n].draw();
                }
            }
        }

        void kindle() {
            for (int i = 0; i < 12; i++) {
                int ix = (int) random(n);
                int iy = (int) random(n);
                getAutomaton(ix, iy).setState(State.BURNING);
            }
        }
        void noWood() {
            for (int i = 0; i < 320; i++) {
                int ix = (int) random(n);
                int iy = (int) random(n);
                getAutomaton(ix, iy).setState(State.WOODLESS);
            }
        }

        int getNumberOfBurningTrees() {
            int count = 0;
            for (int iy = 0; iy < n; iy++) {
                for (int ix = 0; ix < n; ix++) {
                    if(getAutomaton(ix, iy).getState() == State.BURNING) {
                        count = count + 1;
                    }
                }
            }
            return count;
        }

        void process() {
            int l = getNumberOfBurningTrees();
            for (int iy = 0; iy < n; iy++) {
                for (int ix = 0; ix < n; ix++) {
                    Automaton automaton = getAutomaton(ix, iy);
                    switch (automaton.getState()) {
                        case GREEN:
                            automaton.setNewState(State.GREEN);

                            boolean found = false;

                            for (int dx = -1; dx < 2 && !found; dx++) {
                                for (int dy = -1; dy < 2 && !found; dy++) {
                                    if (dx == 0 && dy == 0) continue;
                                    if (dx + ix < 0 || dy + iy < 0 || dx + ix >= n || dy + iy >= n) continue;
                                    Automaton automaton1 = getAutomaton(ix + dx, iy + dy);
                                    if (automaton1.getState() == State.BURNING) {
                                        automaton.setNewState(State.BURNING);
                                        automaton.setBurningTime(0);
                                        found = true;
                                    }
                                }
                            }

                            if (automaton.getNewState() == State.GREEN) {
                                automaton.setGreenTime(automaton.getGreenTime() + 1);
                                if (automaton.getGreenTime() > 20 && random(1) > 0.9999995) {
                                    automaton.setNewState(State.BURNING);
                                }
                            }
                            break;
                        case BURNING:
                            if (automaton.getBurningTime() > 2) {
                                automaton.setNewState(State.BURNED);
                            } else {
                                automaton.setBurningTime(automaton.getBurningTime() + 1);
                                automaton.setNewState(State.BURNING);
                                if (l > 100 && random(1) > 0.995) {
                                    automaton.setNewState(State.EXTINGUISHED);
                                }
                            }
                            break;
                        case BURNED:

                            automaton.setNewState(State.BURNED);
                            if (automaton.getBurnedTime() > 3) {
                                if (random(1) > 0.9997) {
                                    automaton.setNewState(State.WOODLESS);
                                } else {
                                    automaton.setNewState(State.GREEN);
                                    automaton.setGreenTime(0);

                                }

                            } else {
                                automaton.setBurnedTime(automaton.getBurnedTime() + 1);
                                automaton.setNewState(State.BURNED);

                            }
                            break;
                        case WOODLESS:
                            automaton.setNewState(State.WOODLESS);
                            if (automaton.getState() == State.WOODLESS) {
                                automaton.setNewState(State.WOODLESS);
                            }
                            break;
                        case EXTINGUISHED:
                            if (random(1) > 0.7) {
                                automaton.setNewState(State.GREEN);
                            } else {
                                if (random(1) > 0.8) {
                                    automaton.setNewState(State.BURNED);
                                } else {
                                    if (random(1) > 0.8) {
                                        automaton.setNewState(State.BURNING);
                                    }
                                }
                            }
                            break;
                    }
                }
            }
            for (int ix = 0; ix < n; ix++) {
                for (int iy = 0; iy < n; iy++) {
                    Automaton automaton = getAutomaton(ix, iy);
                    automaton.setState(automaton.getNewState());

                }
            }
            println("Burning trees:" + l);
        }
    }

    private class Automaton {
        int iy;
        int ix;
        State state;
        private int burningTime;
        private int burnedTime;
        private int greenTime;
        private State newState;


        Automaton(int ix, int iy) {
            state = State.GREEN;
            this.ix = ix;
            this.iy = iy;
        }


        public void draw() {
            int r = 0;
            int g = 0;
            int b = 0;
            switch(state) {
                case GREEN:
                    r = 0;
                    g = (int)(255 * exp(- (float)greenTime / 50));;
                    b = 0;
                    break;
                case BURNING:
                    r = (int)(255 * exp(- (float)burningTime / 4));
                    g = 0;
                    b = 0;
                    break;
                case BURNED:
                    r = 0;
                    g = 0;
                    b = 0;
                    break;
                case WOODLESS:
                    r = 255;
                    g = 255;
                    b = 255;
                    break;
                case EXTINGUISHED:
                    r = 190;
                    g = 190;
                    b = 190;
                    break;
            }


            stroke(128);
            fill(r, g, b);
            rect(ix * 10, iy * 10,10,10);




        }

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }

        public int getBurningTime() {
            return burningTime;
        }

        public void setBurningTime(int burningTime) {
            this.burningTime = burningTime;
        }

        public State getNewState() {
            return newState;
        }

        public void setNewState(State newState) {
            this.newState = newState;
        }

        public int getBurnedTime() {
            return burnedTime;
        }
        public void setBurnedTime(int burnedTime) {
            this.burnedTime = burnedTime;
        }

        public int getGreenTime() {
            return greenTime;
        }
        public void setGreenTime(int greenTime){
            this.greenTime = greenTime;
        }
    }

}

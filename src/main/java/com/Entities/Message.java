package com.Entities;

import com.Graphics.Screen;
import java.awt.Color;

public class Message extends Entity{

    protected String message;
    protected int duration;
    protected Color color;
    protected int size;

    public Message(String message, double x, double y, int duration, Color color, int size) {
        this.x = x;
        this.y = y;
        this.message = message;
        this.duration = duration * 60; // seconds
        this.color = color;
        this.size = size;
    }
    @Override
    public void update() {
    }

    @Override
    public void render(Screen screen) {
    }
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getMessage() {
        return message;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }



    @Override
    public boolean collide(Entity e) {
        return true;
    }
}

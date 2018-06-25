package by.zti.main.model;

import java.awt.*;

public class Node {
    public static final int SIZE = 50;
    public static final int MOVE = 10;

    private int x, y, g, f, h;
    private Node parent;

    private boolean open, close, collide, start, end, path;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void render(Graphics g){
        if(open){
            g.setColor(Color.GREEN);
        }else if(close && !start) {
            g.setColor(Color.RED);
        }else if(collide) {
            g.setColor(Color.BLACK);
        }else if(start){
            g.setColor(Color.BLUE);
        }else if(end){
            g.setColor(Color.ORANGE);
        }else{
            g.setColor(Color.WHITE);
        }
        g.fillRect(x * SIZE, y * SIZE, SIZE, SIZE);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(h), (x * SIZE) + 2, (y * SIZE) + 12);
        g.setColor(Color.CYAN);
        if(this.g!=0){g.drawString(String.valueOf(this.g), (x * SIZE) + 2, (y * SIZE) + 22);}
        g.setColor(Color.GRAY);
        if(this.g!=0){g.drawString(String.valueOf(f), (x * SIZE) - 5 + SIZE/2, (y * SIZE) + 8 + SIZE/2);}
        g.setColor(Color.BLUE);
        if(path){g.fillOval((x * SIZE) + SIZE/2 - SIZE/8, (y * SIZE) + SIZE/2 - SIZE/8, SIZE/4, SIZE/4);}
    }

    public void set(int h, int g){
        this.h = h;
        this.g = g;
        f = h + g;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    public boolean isCollide() {
        return collide;
    }

    public void setCollide(boolean collide) {
        this.collide = collide;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public void setPath(boolean path) {
        this.path = path;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getH() {
        return h;
    }

    public int getG() {
        return g;
    }

    public int getF() {
        return f;
    }
}

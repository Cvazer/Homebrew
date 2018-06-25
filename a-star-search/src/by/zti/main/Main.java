package by.zti.main;

import by.zti.main.model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static by.zti.main.model.Node.MOVE;
import static by.zti.main.model.Node.SIZE;

public class Main {
    private static Node[][] nodes = new Node[12][12];

    private static Node start;
    private static Node end;

    private static ArrayList<Node> open = new ArrayList<>();
    private static ArrayList<Node> close = new ArrayList<>();

    private static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("A Star Test");
        frame.setSize(565, 590);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(new NodeCanvas());
        frame.setVisible(true);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                calc();
            }
        });

        frame.getComponents()[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getButton()==MouseEvent.BUTTON1){
                    setStart(nodes[e.getX()/50][e.getY()/50]);
                    frame.repaint();
                }
                if(e.getButton()==MouseEvent.BUTTON3){
                    setEnd(nodes[e.getX()/50][e.getY()/50]);
                    frame.repaint();
                }
            }
        });

        create();
        collide();

        setStart(nodes[0][0]);
        setEnd(nodes[6][6]);
    }

    private static void calc() {
        open.clear();
        close.clear();
        for (Node[] row : nodes) {
            for (Node node : row) {
                node.setOpen(false);
                node.setClose(false);
                node.setPath(false);
                node.set(0, 0);
                node.setParent(null);
                calcH(node);
            }
        }
        Node less = start;
        while((less != null ? less.getH() : 0) !=1){
            less = findParents(less);
        }
        while(less.getX()!=start.getX() || less.getY()!=start.getY()){
            less.setPath(true);
            less = less.getParent();
        }

        frame.repaint();
    }

    private static void setEnd(Node node) {
        if(end!=null){end.setEnd(false);}
        node.setEnd(true);
        end = node;
        frame.repaint();
    }

    private static void setStart(Node node) {
        if(start!=null){start.setStart(false);}
        node.setStart(true);
        start = node;
        frame.repaint();
    }

    private static void collide() {
        nodes[0][3].setCollide(true);
        nodes[0][4].setCollide(true);
        nodes[0][5].setCollide(true);
        nodes[0][6].setCollide(true);
        nodes[0][7].setCollide(true);
        nodes[1][3].setCollide(true);
        nodes[1][7].setCollide(true);
        nodes[1][8].setCollide(true);
        nodes[1][9].setCollide(true);
        nodes[1][11].setCollide(true);
        nodes[2][3].setCollide(true);
        nodes[2][5].setCollide(true);
        nodes[2][11].setCollide(true);
        nodes[3][3].setCollide(true);
        nodes[3][5].setCollide(true);
        nodes[3][7].setCollide(true);
        nodes[3][8].setCollide(true);
        nodes[3][9].setCollide(true);
        nodes[3][11].setCollide(true);
        nodes[4][5].setCollide(true);
        nodes[4][9].setCollide(true);
        nodes[4][11].setCollide(true);
        nodes[5][1].setCollide(true);
        nodes[5][2].setCollide(true);
        nodes[5][3].setCollide(true);
        nodes[5][5].setCollide(true);
        nodes[5][7].setCollide(true);
        nodes[5][11].setCollide(true);
        nodes[6][5].setCollide(true);
        nodes[6][7].setCollide(true);
        nodes[6][8].setCollide(true);
        nodes[6][11].setCollide(true);
        nodes[7][0].setCollide(true);
        nodes[7][2].setCollide(true);
        nodes[7][3].setCollide(true);
        nodes[7][4].setCollide(true);
        nodes[7][5].setCollide(true);
        nodes[7][7].setCollide(true);
        nodes[7][11].setCollide(true);
        nodes[8][0].setCollide(true);
        nodes[8][7].setCollide(true);
        nodes[8][9].setCollide(true);
        nodes[8][11].setCollide(true);
        nodes[9][0].setCollide(true);
        nodes[9][1].setCollide(true);
        nodes[9][2].setCollide(true);
        nodes[9][3].setCollide(true);
        nodes[9][4].setCollide(true);
        nodes[9][5].setCollide(true);
        nodes[9][6].setCollide(true);
        nodes[9][7].setCollide(true);
        nodes[9][9].setCollide(true);
        nodes[9][11].setCollide(true);
        nodes[10][1].setCollide(true);
        nodes[10][9].setCollide(true);
        nodes[10][11].setCollide(true);
        nodes[11][1].setCollide(true);
        nodes[11][2].setCollide(true);
        nodes[11][3].setCollide(true);
        nodes[11][5].setCollide(true);
        nodes[11][6].setCollide(true);
        nodes[11][7].setCollide(true);
        nodes[11][8].setCollide(true);
        nodes[11][9].setCollide(true);
    }

    private static void create() {
        for(int i  = 0; i < nodes.length; i++){
            for(int k = 0; k < nodes[i].length; k++){
                nodes[i][k] = new Node(i, k);
            }
        }
    }

    private static Node findParents(Node node) {
        if(open.contains(node)){removeOpen(node);}
        addClosed(node);
        ArrayList<Node> toCheck = new ArrayList<>();
        if(node.getX()-1>=0){
            toCheck.add(nodes[node.getX()-1][node.getY()]);
        }
        if(node.getX()+1<=nodes.length-1){
            toCheck.add(nodes[node.getX()+1][node.getY()]);
        }
        if(node.getY()-1>=0){
            toCheck.add(nodes[node.getX()][node.getY()-1]);
        }
        if(node.getY()+1<=nodes[node.getX()].length-1){
            toCheck.add(nodes[node.getX()][node.getY()+1]);
        }
        for(Node node_tc: toCheck){
            if(!close.contains(node_tc) && !open.contains(node_tc) && !node_tc.isCollide()){
                node_tc.setParent(node);
                node_tc.set(node_tc.getH(), node.getG() + MOVE);
                addOpen(node_tc);
            }else if(!close.contains(node_tc) && open.contains(node_tc) && !node_tc.isCollide()){
                if((node.getG() + MOVE) < node_tc.getG()){
                    node_tc.setParent(node);
                }
            }
        }
        if(open.size()==0){return null;}
        Node less = open.get(0);
        for(Node o_node: open){
            if(o_node.getF()<less.getF()){less = o_node;}
        }
        return less;
    }

    private static void removeOpen(Node node) {
        node.setOpen(false);
        open.remove(node);
    }

    private static void addOpen(Node node){
        node.setOpen(true);
        open.add(node);
    }

    private static void addClosed(Node node) {
        node.setClose(true);
        close.add(node);
    }

    private static void calcH(Node node) {
        int h = 0;
        int node_x = node.getX();
        int node_y = node.getY();
        while(node_x!=end.getX()){
            h++;
            if(node_x<end.getX()){
                node_x++;
            }else{
                node_x--;
            }
        }
        while(node_y!=end.getY()){
            h++;
            if(node_y<end.getY()){
                node_y++;
            }else{
                node_y--;
            }
        }
        node.set(h, 0);
    }


    static class NodeCanvas extends JPanel{
        @Override
        public void paint(Graphics g) {

            for(int i  = 0; i < nodes.length; i++) {
                for (int k = 0; k < nodes[i].length; k++) {
                    nodes[i][k].render(g);
                    g.setColor(Color.BLACK);
                    g.drawRect(i * SIZE, k * SIZE, SIZE, SIZE);
                }
            }
        }
    }
}

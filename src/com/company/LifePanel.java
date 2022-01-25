package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LifePanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

    final int xPanel = 1300;
    final int yPanel = 700;

    final int size = 10;
    final int xWidth = xPanel/size;
    final int yHeight = yPanel/size;
    int[][] life = new int[xWidth][yHeight];
    int[][] previousLife = new int[xWidth][yHeight];

    // boolean starts = true;
    int initial = -1;
    boolean gridStatus = true;

    Color gameColor = new Color(114, 171, 16);

    Timer time;

    JButton start;
    JButton stop;
    JButton random;
    JButton clear;
    JButton cellColor;
    JButton exit;

    JRadioButton gridOn;
    JRadioButton gridOff;

    LifePanel(){

        // Setting up the buttons

        start = new JButton("Start");
        stop = new JButton("Pause/Stop");
        random = new JButton("Random");
        clear = new JButton("Clear");
        cellColor = new JButton("Color");
        exit = new JButton("Exit");

        start.setFocusable(false);
        stop.setFocusable(false);
        random.setFocusable(false);
        clear.setFocusable(false);
        cellColor.setFocusable(false);
        exit.setFocusable(false);


        start.setFont(new Font("Comic sans MS", Font.BOLD,20));
        stop.setFont(new Font("Comic sans MS", Font.BOLD,20));
        random.setFont(new Font("Comic sans MS", Font.BOLD,20));
        clear.setFont(new Font("Comic sans MS", Font.BOLD,20));
        cellColor.setFont(new Font("Comic sans MS", Font.BOLD,20));
        exit.setFont(new Font("Comic sans MS", Font.BOLD,20));

        start.setBounds(0,700,150,30);
        stop.setBounds(160,700,150,30);
        random.setBounds(320,700,150,30);
        clear.setBounds(480,700,150,30);
        cellColor.setBounds(640,700,150,30);
        exit.setBounds(1180,705,100,25);

        // Action Listeners for Buttons

        start.addActionListener(e -> {time.start();
        repaint();});

        stop.addActionListener(e->{time.stop();
        repaint();});

        random.addActionListener(e->{
            spawn();
            time.start();
            repaint();
        });

        cellColor.addActionListener(e->{
            gameColor = JColorChooser.showDialog(null,"Pick a color", Color.green);
            repaint();
        });

        clear.addActionListener(e->{
            clear();
            time.stop(); repaint();});

        exit.addActionListener(e-> System.exit(0));

        // ************************************************************************************

        // Grid on and Off
        gridOn = new JRadioButton("Grid On");
        gridOff = new JRadioButton("Grid Off");

        ButtonGroup group = new ButtonGroup();
        group.add(gridOn);
        group.add(gridOff);

        gridOn.setFocusable(false);
        gridOff.setFocusable(false);
        gridOn.setFont(new Font("Comic sans MS", Font.BOLD,15));
        gridOff.setFont(new Font("Comic sans MS", Font.BOLD,15));
        gridOn.setBounds(800,700,90,30);
        gridOff.setBounds(900,700,90,30);

        gridOn.addActionListener(e -> gridStatus = true);
        gridOff.addActionListener(e -> gridStatus = false);

        // ************************************************************************************
        // Adding buttons to Panel
        add(start);
        add(stop);
        add(random);
        add(clear);
        add(gridOn);
        add(gridOff);
        add(cellColor);
        add(exit);

        // Panel Settings
        setBounds(0,0,xPanel,800);
        setSize(xPanel,800);
        setLayout(null);
        setBackground(Color.black);

        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);

        time = new Timer(80,this);
        // time.start();                           // Timer Start
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color (30, 30, 30));

        if (!gridStatus) {
            g.setColor(Color.black);
        }
        grid(g);

        //spawn(g);
        display(g);

    }

    private void grid(Graphics g){

        for (int i = 0; i < life.length; i++) {
            g.drawLine(i*size,0,i*size, yPanel); // Columns

            for (int j = 0; j < (yPanel / size); j++) {
                g.drawLine(0,j*size,xPanel,j*size); // Rows
            }
        }
    }

    private void spawn(){
        //if (starts){
        for (int x = 0; x < life.length; x++) {

            for (int y = 0; y < (yHeight); y++) {

                if((int)(Math.random() * 5) == 0){
                    previousLife[x][y] = 1;
                }
            }
        }
        //starts = false;
        //}
    }

    private void display (Graphics g){
        g.setColor(gameColor);
        copyArray();

        for (int x = 0; x < life.length; x++) {

            for (int y = 0; y < yHeight; y++) {

                if (life[x][y] == 1){
                    g.fillRect(x*size, y*size, size, size);
                }
            }
        }
    }

    // Clear screen
    private void clear(){
        for (int x = 0; x < life.length; x++) {

            for (int y = 0; y < yHeight; y++) {

                previousLife[x][y] = 0;
            }
        }
    }

    // Copying Array
    private void copyArray(){
        for (int x = 0; x < life.length; x++) {

            for (int y = 0; y < yHeight; y++) {
                life [x][y] = previousLife[x][y];
            }
        }
    }

    // Rules
    private int check(int x, int y){
        int alive = 0;

        alive += life[(x+xWidth-1) % xWidth]     [(y+yHeight-1) % yHeight];
        alive += life[(x+xWidth) % xWidth]       [(y+yHeight-1) % yHeight];
        alive += life[(x+xWidth+1) % xWidth]     [(y+yHeight-1) % yHeight];

        alive += life[(x+xWidth-1) % xWidth]     [(y+yHeight)% yHeight];
        alive += life[(x+xWidth+1) % xWidth]     [(y+yHeight) % yHeight];

        alive += life[(x+xWidth-1) % xWidth]     [(y+yHeight+1) % yHeight];
        alive += life[(x+xWidth) % xWidth]       [(y+yHeight+1) % yHeight];
        alive += life[(x+xWidth+1) % xWidth]     [(y+yHeight+1) % yHeight];

        return alive;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int alive;

        for (int x = 0; x < life.length; x++) {

            for (int y = 0; y < yHeight; y++) {

                alive = check(x,y);
                if(alive == 3){
                    previousLife[x][y] = 1;
                }
                else if(alive == 2 && life [x][y]==1){
                    previousLife[x][y] = 1;
                }
                else{
                    previousLife[x][y] = 0;
                }
            }
        }

        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX()/size;
        int y = e.getY()/size;

        if (life[x][y] == 0 && initial == 0){
            previousLife[x][y] = 1;
        }
        else if (life[x][y] == 1 && initial == 1){
            previousLife[x][y] = 0;
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // time.stop(); // Pause

        int x = e.getX()/size;
        int y = e.getY()/size;

        if (life[x][y] == 0 ){
            initial = 0;
        }
        else {
            initial = 1;
        }

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        initial = -1;
        // time.start(); // Start Again
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        /*
         * R - Reset
         * S - Start
         * A - Abandon
         * C - Clear
         * */

        switch (code) {
            case KeyEvent.VK_R -> {
                spawn();
                time.start();
            }
            case KeyEvent.VK_C -> {
                clear();
                time.stop();
            }
            case KeyEvent.VK_S -> time.start();

            case KeyEvent.VK_A -> time.stop();
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

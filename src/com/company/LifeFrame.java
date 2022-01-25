package com.company;

import javax.swing.*;

public class LifeFrame extends JFrame {

    ImageIcon mainIcon = new ImageIcon("D:\\Personal\\CodingPractice\\JavaCodes\\GameOfLifeUIx\\src\\mainIcon.png");

    public LifeFrame(){
        setTitle("Game of Life");
        setIconImage(mainIcon.getImage());
        setSize(1300,772);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        add(new LifePanel());
        setVisible(true);
    }
    public static void main(String[] args) {
        new LifeFrame();
    }
}

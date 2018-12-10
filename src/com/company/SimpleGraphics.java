package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public enum SimpleGraphics {
    INSTANCE;

    JFrame frame;
    JPanel panel;

    Integer scale = 3;
    int width = 1000;
    int height = 1000;

    boolean done;
    int iterationNum = 0;

    Main main;


    public Color[] visibleBuffer = new Color[256 * 240];


    SimpleGraphics() {

        int n = 90;
        int m = 90;

        height = 30 + n * 11;
        width = 30 + m * 11;

        frame = new JFrame("Emul");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setTitle("Emul");
        frame.setResizable(false);
        //setUndecorated(true);

        panel = new GPanel();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        main = new Main(n, m);


    }


    private void step() {
        iterationNum++;
        done = main.step();

    }


    class GPanel extends JPanel implements ActionListener {
        Timer timer = new Timer(1, this);

        public GPanel() {
            super();

            setDoubleBuffered(true);

            setBackground(Color.black);
            setPreferredSize(new Dimension(width, height));


            timer.start();
        }

        public void paint(Graphics g) {
            super.paint(g);

            g.setColor(Color.white);
            int leftUp = 12;
            int b = 11;
            int c = 15;
            g.drawLine(leftUp, leftUp, b * main.m + c, leftUp);
            g.drawLine(b * main.m + c, leftUp, b * main.m + c, b * main.n + c);
            g.drawLine(b * main.m + c, b * main.n + c, leftUp, b * main.n + c);
            g.drawLine(leftUp, b * main.n + c, leftUp, leftUp);
            char[] statIter = ("" + iterationNum).toCharArray();
            g.drawChars(statIter, 0, statIter.length, leftUp + 50, b * main.n + c + 12);

            for (int i = 0; i < main.n; i++) {
                for (int j = 0; j < main.m; j++) {

                    switch (main.mainEnvironment[i][j]) {
                        case 0:
                            //g.setColor(Color.black);
                            continue;
                        case 1:
                            g.setColor(Color.white);
                            break;
                        case 2:
                            g.setColor(Color.cyan);
                            main.mainEnvironment[i][j] = 1;
                            break;
                        default:
                            System.out.println("Errrrr");
                    }

                    int y = 15 + (i * 11);
                    int x = 15 + (j * 11);
                    g.fillRect(x, y, 9, 9);
                }
            }
            if (done) {
                System.out.println("Done");
                timer.stop();
            }
        }


        @Override
        public void actionPerformed(ActionEvent e) {

            step();

            repaint();
        }

    }

    public void init() {

    }


}
package com.company;

import java.util.ArrayDeque;
import java.util.Scanner;

public class Main {

    public int n, m;
    public int[][] mainEnvironment;

    class QueueElem {
        public int i;
        public int j;

        QueueElem(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    private ArrayDeque<QueueElem> queue;

    Main(int n, int m) {
        this.n = n;
        this.m = m;

        mainEnvironment = new int[this.n][this.m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mainEnvironment[i][j] = 0;
            }
        }

    }

    public boolean step() {
        int i, j = 0;
        int rnd;

        do {
            rnd = 0 + (int) (Math.random() * ((m * n - 1) + 1));
            //System.out.println(rnd);

            j = rnd % m;
            i = (rnd - j) / m;
        } while (mainEnvironment[i][j] != 0);


       /* if (i == 0) {
            mainEnvironment[i][j] = 2;
            return;
        } else if (mainEnvironment[i - 1][j] == 2) {
            mainEnvironment[i][j] = 2;
            return;
        }*/


        mainEnvironment[i][j] = 1;

        for (int jj = 0; jj < m; jj++) {
            if (mainEnvironment[0][j] == 1) {
                boolean res = BFS(new QueueElem(0, j));
                if (res) {
                    return true;

                }
            }
        }
        return false;

    }

    private boolean BFS(QueueElem startElem) {
        queue = new ArrayDeque<QueueElem>();
        queue.add(startElem);

        while (!queue.isEmpty()) {
            QueueElem curElem = queue.poll();

            mainEnvironment[curElem.i][curElem.j] = 2;
            if (curElem.i == n - 1) {
                return true;
            }

            if (curElem.j > 0) {
                if (mainEnvironment[curElem.i][curElem.j - 1] == 1) {
                    queue.add(new QueueElem(curElem.i, curElem.j - 1));
                }
            }
            if (curElem.j < m - 1) {
                if (mainEnvironment[curElem.i][curElem.j + 1] == 1) {
                    queue.add(new QueueElem(curElem.i, curElem.j + 1));
                }
            }


            if (curElem.i > 0) {
                if (mainEnvironment[curElem.i - 1][curElem.j] == 1) {
                    queue.add(new QueueElem(curElem.i - 1, curElem.j));
                }
            }
            if (curElem.i < n - 1) {
                if (mainEnvironment[curElem.i + 1][curElem.j] == 1) {
                    queue.add(new QueueElem(curElem.i + 1, curElem.j));
                }
            }


        }
        return false;

    }


    public static void main(String[] args) {

        while (true) {
            Scanner in = new Scanner(System.in);

            System.out.println("1 - plural modeling; 2 - visual modeling");
            int userEnter = in.nextInt();
            in.nextLine();

            switch (userEnter) {
                case 1:
                    //TODO read from file n*m, calc in cycle
                    break;
                case 2:
                    SimpleGraphics.INSTANCE.init();
                    break;
                default:
                    return;
            }


        }
    }
}

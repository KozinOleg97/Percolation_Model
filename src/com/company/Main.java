package com.company;

public class Main {

    public int n, m;
    public int[][] mainEnvironment;

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

    public void step() {
        int i, j = 0;
        int rnd;

        do {
            rnd = 0 + (int) (Math.random() * ((m * n - 1) + 1));
            //System.out.println(rnd);

            j = rnd % m;
            i = (rnd - j) / m;
        } while (mainEnvironment[i][j] != 0);


        if (i == 0) {
            mainEnvironment[i][j] = 2;
            return;
        } else if (mainEnvironment[i - 1][j] == 2) {
            mainEnvironment[i][j] = 2;
            return;
        }

        if (j > 0) {
            if (mainEnvironment[i][j - 1] == 2) {
                mainEnvironment[i][j] = 2;
                return;
            }
        }
        if (j < m - 1) {
            if (mainEnvironment[i][j + 1] == 2) {
                mainEnvironment[i][j] = 2;
                return;
            }
        }
        if (i < n - 1) {
            if (mainEnvironment[i + 1][j] == 2) {
                mainEnvironment[i][j] = 2;
                return;
            }
        }


        mainEnvironment[i][j] = 1;
    }


    public static void main(String[] args) {
        SimpleGraphics.INSTANCE.init();
    }
}

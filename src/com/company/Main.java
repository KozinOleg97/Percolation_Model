package com.company;

import java.io.*;
import java.util.*;

public class Main {

    public int n, m;
    public int[][] mainEnvironment;

    private static List<resultElementAverage> additionalResult = new ArrayList<>();

    class resultElem {
        int endIteration;
        int n, m;

        resultElem(int endIteration, int n, int m) {
            this.endIteration = endIteration;
            this.n = n;
            this.m = m;
        }
    }

    class resultElementAverage {
        int average;
        long summ;
        int numb;
        int n, m;

        resultElementAverage(int n, int m, int numb) {
            this.n = n;
            this.m = m;
            this.numb = numb;
            this.summ = 0;
            this.average = 0;
        }

        public void addToSumm(int endIteration) {
            this.summ += endIteration;
        }

        public long getAverage() {
            return summ / numb;
        }
    }

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
        init();
    }

    private void init() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mainEnvironment[i][j] = 0;
            }
        }
    }

    public boolean step() {
        int i, j = 0;
        int rnd;

        Random rndN = new Random();

        do {
            //rnd = 0 + (int) (rndN.nextGaussian() * ((m * n - 1) + 1));
            rnd = 0 + (int) (Math.random() * ((m * n - 1) + 1));
            //System.out.println(rnd);

            j = rnd % m;
            i = (rnd - j) / m;
        } while (mainEnvironment[i][j] != 0);


        mainEnvironment[i][j] = 1;

        for (int jj = 0; jj < m; jj++) {
            if (mainEnvironment[0][j] == 1) {

                //long start = System.currentTimeMillis();
                boolean res = BFS(new QueueElem(0, j));
                //long time = System.currentTimeMillis() - start;

                //System.out.println("BFS " + time + " ms");

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
            //System.out.println(queue.size());

            QueueElem curElem = queue.poll();

            mainEnvironment[curElem.i][curElem.j] = 2;

            if (curElem.i == n - 1) {
                return true;
            }

            if (curElem.j > 0) {
                if (mainEnvironment[curElem.i][curElem.j - 1] == 1) {
                    queue.add(new QueueElem(curElem.i, curElem.j - 1));
                    mainEnvironment[curElem.i][curElem.j - 1] = 2;
                }
            }
            if (curElem.j < m - 1) {
                if (mainEnvironment[curElem.i][curElem.j + 1] == 1) {
                    queue.add(new QueueElem(curElem.i, curElem.j + 1));
                    mainEnvironment[curElem.i][curElem.j + 1] = 2;
                }
            }


            if (curElem.i > 0) {
                if (mainEnvironment[curElem.i - 1][curElem.j] == 1) {
                    queue.add(new QueueElem(curElem.i - 1, curElem.j));
                    mainEnvironment[curElem.i - 1][curElem.j] = 2;
                }
            }
            if (curElem.i < n - 1) {
                if (mainEnvironment[curElem.i + 1][curElem.j] == 1) {
                    queue.add(new QueueElem(curElem.i + 1, curElem.j));
                    mainEnvironment[curElem.i + 1][curElem.j] = 2;
                }
            }


        }
        return false;

    }

    private void clearFromBFS() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                if (mainEnvironment[i][j] == 2) {
                    mainEnvironment[i][j] = 1;
                }
            }
        }
    }

    private List<resultElem> massStep(int testNumb) {
        List<resultElem> curRes = new ArrayList<resultElem>();
        resultElementAverage curResAv = new resultElementAverage(n, m, testNumb);
        for (int i = 0; i < testNumb; i++) {


            int iter = 0;
            boolean done = false;
            while (!done) {
                done = step();
                clearFromBFS();
                iter++;

            }
            curRes.add(new resultElem(iter, n, m));
            curResAv.addToSumm(iter);

            init();
        }
        additionalResult.add(curResAv);
        return curRes;
    }

    public static void saveResultToFile(List<resultElem> resultElemList, String fileName) {

        String lineSeparator = System.getProperty("line.separator");
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (resultElem elem : resultElemList) {
                int n = elem.n;
                int m = elem.m;
                int iter = elem.endIteration;

                String str = n + "\t" + m + "\t" + m * n + "\t" + iter + lineSeparator;
                writer.write(str);
            }

            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }


        try (FileWriter writer = new FileWriter(
                fileName.substring(0, fileName.length() - 4)
                        + "_average.txt", false)) {
            for (resultElementAverage elem : additionalResult) {
                int n = elem.n;
                int m = elem.m;
                long iter = elem.getAverage();

                String str = n + "\t" + m + "\t" + m * n + "\t" + iter + lineSeparator;
                writer.write(str);
            }

            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
        additionalResult.clear();

    }

    public static void main(String[] args) throws IOException {

        while (true) {
            Scanner in = new Scanner(System.in);

            System.out.println("1 - mass modeling; 2 - visual modeling");
            int userEnter = in.nextInt();
            in.nextLine();

            switch (userEnter) {
                case 1:
                    System.out.println("Enter configuration file");
                    String fileName = in.nextLine();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(fileName)));

                    List<resultElem> result = new ArrayList<resultElem>();
                    String curStr;
                    while ((curStr = reader.readLine()) != null) {

                        String[] strMas = curStr.split("[\t ]");
                        int testNumb = 1;

                        if (strMas.length > 2) {
                            testNumb = Integer.parseInt(strMas[2]);
                        }

                        Main curMain = new Main(Integer.parseInt(strMas[0]), Integer.parseInt(strMas[1]));

                        long start = System.currentTimeMillis();
                        result.addAll(curMain.massStep(testNumb));
                        long time = System.currentTimeMillis() - start;

                        /*System.out.println("Done " + result.get(result.size() - 1).n + " " +
                                result.get(result.size() - 1).m + " " +
                                result.get(result.size() - 1).endIteration + " " +
                                time + " ms");*/

                    }
                    reader.close();

                    saveResultToFile(result, "Result_" + fileName);


                    break;
                case 2:
                    //SimpleGraphics.INSTANCE.init();

                    System.out.println("Enter N");
                    int n = in.nextInt();
                    in.nextLine();
                    System.out.println("Enter M");
                    int m = in.nextInt();
                    in.nextLine();

                    Main mainProc = new Main(n, m);
                    SimpleGraphics graph = new SimpleGraphics(n, m);

                    graph.panel.repaint();

                    boolean done = false;
                    while (!done) {
                        done = mainProc.step();
                        graph.step(mainProc);

                        try {
                            Thread.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (!done) {
                            mainProc.clearFromBFS();
                        }

                    }
                    graph.panel.repaint();

                    break;
                default:
                    return;
            }


        }
    }
}

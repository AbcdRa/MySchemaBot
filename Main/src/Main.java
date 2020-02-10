import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        int[] mas = {10,20,30,40,50,60,70,80,90,100,200,500};
        ArrayList<Resistor> resistors = MyToolkit.formBoxWithResistors(mas);
        Population population = new Population(resistors);
        population.setGoal(77);
        long timeStart = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++) {
            oneCycle(population);
        }
        long timeSpent = System.currentTimeMillis() - timeStart;
        System.out.println("Time: " + timeSpent/1000. + " sec.");
        population.getBestBots().get(0).showGens();
    }

    public static void oneCycle(Population population) {
        population.formBestBots();
        population.showShortInfo();
        //MyToolkit.decryptGens(population.getBestBots().get(0).getGens(), 13);
        population.updatePopulation();
    }

    public static ArrayList<Resistor> getStartPackResistors() {
        Resistor res1 = new Resistor(360);
        Resistor res2 = new Resistor(750);
        Resistor res3 = new Resistor(82);
        Resistor res4 = new Resistor(100);
        Resistor res5 = new Resistor(300);
        Resistor res6 = new Resistor(100);
        Resistor res7 = new Resistor(150);
        Resistor res8 = new Resistor(470);
        Resistor res9 = new Resistor(510);
        Resistor res10 = new Resistor(390);
        Resistor res11 = new Resistor(82);
        Resistor res12 = new Resistor(40);
        Resistor res13 = new Resistor(470);
        ArrayList<Resistor> ress = new ArrayList<>();
        ress.add(res1);
        ress.add(res2);
        ress.add(res3);
        ress.add(res4);
        ress.add(res5);
        ress.add(res6);
        ress.add(res7);
        ress.add(res8);
        ress.add(res9);
        ress.add(res10);
        ress.add(res11);
        ress.add(res12);
        ress.add(res13);
        return ress;
    }
}

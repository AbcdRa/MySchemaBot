import java.util.ArrayList;
import java.util.Random;

public class Bot {

    final private static int MAX_GENS = 36;
    final private int MUTATION_CHANCE = 50;
    private static Random random = new Random();
    private Schema schema;
    private ArrayList<Resistor> resistors;
    private ArrayList<Integer> gens;


    public static int getMaxGens() {
        return MAX_GENS;
    }

    public Schema getSchema() {return schema;}

    public void getResistors(ArrayList<Resistor> resistors) {
        this.resistors.addAll(resistors);
    }

    public Bot() {
        resistors = new ArrayList<>();
        gens = new ArrayList<>(MAX_GENS);
        for (int i = 0; i < MAX_GENS; i++) {
            gens.add(Math.abs(random.nextInt() % 255));
        }
    }

    public Bot(ArrayList<Integer> gens) {
        resistors = new ArrayList<>();
        this.gens = gens;
    }

    public void showGens() {
        for(Integer i : gens) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public Schema buildSchema() {
        if(resistors.size() == 0) {
            System.out.println("Haven't resistors!!!");
            return null;
        }
        Resistor firstResistor = resistors.get(gens.get(0) % resistors.size());
        resistors.remove(firstResistor);
        schema = new Schema(firstResistor);
        for(int i = 1; i < MAX_GENS; i++) {
            if(resistors.size() == 0) return schema;
            if(gens.get(i) < 7) continue;
            if(gens.get(i) < 15 && (i+1) < MAX_GENS) {
                int index = gens.get(++i) % resistors.size();
                Resistor r = resistors.get(index);
                resistors.remove(index);
                schema.seriesConnect(r);
                continue;
            }
            if(gens.get(i) < 23 && (i+1) < MAX_GENS)  {
                int index = gens.get(++i) % resistors.size();
                Resistor r = resistors.get(index);
                resistors.remove(index);
                schema.parallelConnect(r);
                continue;
            }
            if(gens.get(i) < 31 && resistors.size() >= 2) {
                if((i+2) < MAX_GENS) {
                    int index1 = gens.get(++i) % resistors.size();
                    int index2 = gens.get(++i) % resistors.size();
                    if(index1 == index2) {
                        schema.parallelConnect(resistors.get(index1));
                        resistors.remove(index1);
                        continue;
                    }
                    Resistor r1 = resistors.get(index1);
                    Resistor r2 = resistors.get(index2);
                    resistors.remove(r1);
                    resistors.remove(r2);
                    schema.parallelConnect(r1, r2);
                    continue;
                }
            }
            if(gens.get(i) < 39 && resistors.size() >= 2) {
                if((i+2) < MAX_GENS) {
                    int index1 = gens.get(++i) % resistors.size();
                    int index2 = gens.get(++i) % resistors.size();
                    if(index1 == index2) {
                        schema.seriesConnect(resistors.get(index1));
                        resistors.remove(index1);
                        continue;
                    }
                    Resistor r1 = resistors.get(index1);
                    Resistor r2 = resistors.get(index2);
                    resistors.remove(r1);
                    resistors.remove(r2);
                    schema.seriesConnect(r1, r2);
                }
            }
            if(gens.get(i) < 47 && (i+2) < MAX_GENS) {
                int numRes = gens.get(++i);
                ArrayList<Resistor> collectedResistors = new ArrayList<>(numRes);
                for(int j = 0; j < numRes; j++) {
                    if ((i + 1) < MAX_GENS&& resistors.size() > 0) {
                        int index = gens.get(++i) % resistors.size();
                        collectedResistors.add(resistors.get(index));
                        resistors.remove(index);
                    }
                }
                schema.parallelConnect(collectedResistors);
            }
            if(gens.get(i) < 55 && (i+2) < MAX_GENS) {
                int numRes = gens.get(++i);
                ArrayList<Resistor> collectedResistors = new ArrayList<>(numRes);
                for(int j = 0; j < numRes; j++) {
                    if ((i + 1) < MAX_GENS && resistors.size() > 0) {
                        int index = gens.get(++i) % resistors.size();
                        collectedResistors.add(resistors.get(index));
                        resistors.remove(index);
                    }
                }
                schema.seriesConnect(collectedResistors);
            }
        }
        return schema;
    }

    public ArrayList<Integer> getGens() {
        return gens;
    }

    public ArrayList<Bot> cross(Bot bot) {
        ArrayList<Integer> gens2 = new ArrayList<>();
        gens2.addAll(bot.gens);
        int pointCross = random.nextInt(MAX_GENS);
        ArrayList<Integer> xGens = new ArrayList<>();
        ArrayList<Integer> yGens = new ArrayList<>();
        for(int i = 0; i < pointCross; i++) {
            xGens.add(gens.get(i));
            yGens.add(gens2.get(i));
        }
        for (int i = pointCross; i < MAX_GENS; i++) {
            yGens.add(gens.get(i));
            xGens.add(gens2.get(i));
        }
        mutation(xGens);
        mutation(yGens);
        Bot bot1 = new Bot(xGens);
        Bot bot2 = new Bot(yGens);
        ArrayList<Bot> newBots = new ArrayList<>(2);
        newBots.add(bot1);
        newBots.add(bot2);
        return newBots;
    }

    private void mutation(ArrayList<Integer> gens) {
        if(random.nextInt(100) < MUTATION_CHANCE) {
            gens.set(random.nextInt(MAX_GENS), random.nextInt(255));
            mutation(gens);
        }
    }


}

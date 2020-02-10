import java.util.ArrayList;

public class MyToolkit {
    public static ArrayList<Resistor> formBoxWithResistors(int[] mas) {
        ArrayList<Resistor> box = new ArrayList<>();
        for (int o : mas) {
            Resistor resistor = new Resistor(o);
            box.add(resistor);
        }
        return box;
    }

    public static void decryptGens(ArrayList<Integer> gens, int key) {
        System.out.println(gens.get(0) + " : Взять " + (gens.get(0) % key + 1) + " резистор");
        for(int i = 0; i < Bot.getMaxGens(); i++) {
            if(gens.get(i) < 7) System.out.println(gens.get(i) + " : Мусорный ген");
        }
    }

    public static ArrayList<Resistor> formResistorsUsageGens(Bot bot) {
        int numRes = bot.getGens().get(bot.incI());
        ArrayList<Resistor> collectedResistors = new ArrayList<>(numRes);
        for(int j = 0; j < numRes; j++) {
            if ((bot.getI() + 1) < Bot.getMaxGens() && bot.getResistors().size() > 0) {
                int index = bot.getGens().get(bot.incI()) % bot.getResistors().size();
                collectedResistors.add(bot.getResistors().get(index));
                bot.getResistors().remove(index);
            }
        }
        return collectedResistors;
    }
}

import java.util.ArrayList;

public class MyToolkit {
    public static ArrayList<Resistor> formBoxWithResistors(int[] mas) {
        ArrayList<Resistor> box = new ArrayList<>();
        for(int i = 0; i < mas.length; i++) {
            Resistor resistor = new Resistor(mas[i]);
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
}

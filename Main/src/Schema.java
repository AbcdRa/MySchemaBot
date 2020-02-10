import java.util.ArrayList;

public class Schema {
    private double resistance;
    private String descriptor;
    private int countResistor;
    private ArrayList<String> usedResistor;

    public double getResistance() {
        return resistance;
    }

    public Schema(Resistor resistor) {
        resistance = resistor.getResistance();
        descriptor = "R"+resistor.getId();
        countResistor = 1;
        usedResistor = new ArrayList<>();
        usedResistor.add("Resistor" + resistor.getId() + " - " + resistor.getResistance());
    }

    public void seriesConnect(Resistor r1, Resistor r2) {
        double tempResistance = r1.getResistance() * r2.getResistance() / (r1.getResistance() + r2.getResistance());
        resistance += tempResistance;
        descriptor += "+R" + r1.getId() + "/R" + r2.getId();
        countResistor += 2;
        usedResistor.add("Resistor" + r1.getId() + " - " + r1.getResistance());
        usedResistor.add("Resistor" + r2.getId() + " - " + r2.getResistance());
    }

    public void seriesConnect(Resistor r) {
        resistance += r.getResistance();
        descriptor += "+R" + r.getId();
        countResistor++;
        usedResistor.add("Resistor" + r.getId() + " - " + r.getResistance());
    }

    public void seriesConnect(ArrayList<Resistor> res) {
        double tempResistance = 0;
        StringBuilder tempDescription = new StringBuilder("(");
        for(Resistor r : res) {
            tempResistance += 1/r.getResistance();
            usedResistor.add("Resistor" + r.getId() + " - " + r.getResistance());
            countResistor ++;
            tempDescription.append("R").append(r.getId()).append("/");
        }
        tempDescription = new StringBuilder(tempDescription.substring(0, tempDescription.length() - 1));
        tempDescription.append(")");
        resistance = resistance + tempResistance;
        descriptor =  descriptor + "+" + tempDescription.toString();
    }

    public void parallelConnect(Resistor r1, Resistor r2) {
        double tempResistance = r1.getResistance() + r2.getResistance();
        resistance = resistance * tempResistance / (resistance + tempResistance);
        descriptor = "(" + descriptor + ")/(R" + r1.getId() + "+R" +r2.getId() + ")";
        countResistor += 2;
        usedResistor.add("Resistor" + r1.getId() + " - " + r1.getResistance());
        usedResistor.add("Resistor" + r2.getId() + " - " + r2.getResistance());
    }

    public void parallelConnect(ArrayList<Resistor> res) {
        double tempResistance = 0;
        StringBuilder tempDescription = new StringBuilder("(");
        for(Resistor r : res) {
            tempResistance += r.getResistance();
            usedResistor.add("Resistor" + r.getId() + " - " + r.getResistance());
            countResistor ++;
            tempDescription.append("R").append(r.getId()).append("+");
        }
        tempDescription = new StringBuilder(tempDescription.substring(0, tempDescription.length() - 1));
        tempDescription.append(")");
        resistance = resistance * tempResistance / (resistance + tempResistance);
        descriptor = "(" + descriptor + ")/" + tempDescription.toString();
    }


    public void parallelConnect(Resistor r) {
        resistance = resistance * r.getResistance() / (resistance + r.getResistance());
        descriptor = "(" + descriptor + ")/R" + r.getId() ;
        countResistor++;
        usedResistor.add("Resistor" + r.getId() + " - " + r.getResistance());
    }

    public void show() {
        System.out.println("Resistance = " + resistance);
        System.out.println(descriptor);
        System.out.println("Resistors used " + countResistor);
        usedResistor.forEach(System.out::println);
    }


}

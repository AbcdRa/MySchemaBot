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
        resistance = resistor.resistance;
        descriptor = "R"+resistor.getId();
        countResistor = 1;
        usedResistor = new ArrayList<>();
        usedResistor.add("Resistor" + resistor.getId() + " - " + resistor.resistance);
    }

    public void seriesConnect(Resistor r1, Resistor r2) {
        double tempResistance = r1.resistance * r2.resistance / (r1.resistance + r2.resistance);
        resistance += tempResistance;
        descriptor += "+R" + r1.getId() + "/R" + r2.getId();
        countResistor += 2;
        usedResistor.add("Resistor" + r1.getId() + " - " + r1.resistance);
        usedResistor.add("Resistor" + r2.getId() + " - " + r2.resistance);
    }

    public void seriesConnect(Resistor r) {
        resistance += r.resistance;
        descriptor += "+R" + r.getId();
        countResistor++;
        usedResistor.add("Resistor" + r.getId() + " - " + r.resistance);
    }

    public void seriesConnect(ArrayList<Resistor> res) {
        double tempResistance = 0;
        String tempDescription = "(";
        for(Resistor r : res) {
            tempResistance += 1/r.resistance;
            usedResistor.add("Resistor" + r.getId() + " - " + r.resistance);
            countResistor ++;
            tempDescription += "R" + r.getId() + "/";
        }
        tempDescription = tempDescription.substring(0, tempDescription.length()-1);
        tempDescription += ")";
        resistance = resistance + tempResistance;
        descriptor =  descriptor + "+" + tempDescription;
    }

    public void parallelConnect(Resistor r1, Resistor r2) {
        double tempResistance = r1.resistance + r2.resistance;
        resistance = resistance * tempResistance / (resistance + tempResistance);
        descriptor = "(" + descriptor + ")/(R" + r1.getId() + "+R" +r2.getId() + ")";
        countResistor += 2;
        usedResistor.add("Resistor" + r1.getId() + " - " + r1.resistance);
        usedResistor.add("Resistor" + r2.getId() + " - " + r2.resistance);
    }

    public void parallelConnect(ArrayList<Resistor> res) {
        double tempResistance = 0;
        String tempDescription = "(";
        for(Resistor r : res) {
            tempResistance += r.resistance;
            usedResistor.add("Resistor" + r.getId() + " - " + r.resistance);
            countResistor ++;
            tempDescription += "R" + r.getId() + "+";
        }
        tempDescription = tempDescription.substring(0, tempDescription.length()-1);
        tempDescription += ")";
        resistance = resistance * tempResistance / (resistance + tempResistance);
        descriptor = "(" + descriptor + ")/" + tempDescription;
    }


    public void parallelConnect(Resistor r) {
        resistance = resistance * r.resistance / (resistance + r.resistance);
        descriptor = "(" + descriptor + ")/R" + r.getId() ;
        countResistor++;
        usedResistor.add("Resistor" + r.getId() + " - " + r.resistance);
    }

    public void show() {
        System.out.println("Resistance = " + resistance);
        System.out.println(descriptor);
        System.out.println("Resistors used " + countResistor);
        usedResistor.forEach(System.out::println);
    }


}

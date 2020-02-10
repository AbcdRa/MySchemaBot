
public class Resistor {
    private static int static_id = 1;
    private int id;
    private double resistance;

    public double getResistance() {return resistance;}

    public int getId() {
        return id;
    }

    public Resistor(int resistance) {
        this.resistance = resistance;
        id = static_id++;
    }

    public void showInfo() {
        System.out.println("Resistor " + id);
        System.out.println("Resistance " + resistance + " Om");
    }

}



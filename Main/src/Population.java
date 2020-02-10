import java.util.ArrayList;
import java.util.Collections;

//Удобный массив для работы с большим кол-вом ботов
public class Population {
    //Здесь будут хранится основные боты популяции
    private ArrayList<Bot> bots;
    //В этот массив попадут только лучшие
    private ArrayList<Bot> bestBots = new ArrayList<>();
    //Переменная содержит цель этой популяции
    private double goal;
    //Максимальное кол-во ботов в популяции и максимальное кол-во лучших
    private int maxNumBots = 64;
    private int maxNumBestBots = 4;
    private int maxRandomBots = 1;
    //Резисторы с которыми имеет дело эта популяция
    private ArrayList<Resistor> resistors = new ArrayList<>();

    public ArrayList<Bot> getBestBots() {return bestBots;}
    //Создаем популяцию на основе существующих ботов и резисторов
    public Population(ArrayList<Bot> bots, ArrayList<Resistor> resistors) {
        this.resistors.addAll(resistors);
        this.bots.addAll(bots);
        //Стандартная цель
        goal = 1000.;
    }

    public Population(ArrayList<Resistor> resistors) {
        this.resistors.addAll(resistors);
        //Инициализация ботов
        this.bots = new ArrayList<>(maxNumBots);
        for(int i = 0; i < maxNumBots; i++) bots.add(new Bot());
        //Стандартная цель
        goal = 1000.;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    public double norma(double a, double b) {
        return Math.sqrt(Math.abs(a*a - b*b));
    }

    //Находит лучшего бота в популяции
    public Bot findBestBot() {
        bots.get(0).getResistors(resistors);
        double minScore = norma(bots.get(0).buildSchema().getResistance(), goal);
        Bot bestBot = bots.get(0);
        for(Bot bot : bots){
            bot.getResistors(resistors);
            double resistance = bot.buildSchema().getResistance();
            double score = norma(resistance, goal);
            if(score <= minScore){
                minScore = score;
                bestBot = bot;
            }
        }
        return bestBot;
    }

    public void formBestBots() {
        bestBots.clear();
        for(int i = 0; i < maxNumBestBots - maxRandomBots; i++) {
            Bot bestBot = findBestBot();
            bestBots.add(bestBot);
            bots.remove(bestBot);
        }
        for(int i = 0; i < maxRandomBots; i++) {
            Collections.shuffle(bots);
            Bot randomBot = bots.get(0);
            bestBots.add(randomBot);
            bots.remove(0);
        }
    }

    public double getAverageResistance() {
        double average = 0;
        for(Bot bot : bots) {
            average += bot.getSchema().getResistance();
        }
        average /= bots.size();
        for (Bot bot : bestBots) {
            average += bot.getSchema().getResistance();
        }
        average /= (bestBots.size() + 1);
        return average;
    }

    public void updatePopulation() {
        ArrayList<Bot> newBots = new ArrayList<>(maxNumBots);
        for(Bot bot: bestBots) {
            for(Bot bot2 : bestBots)
                newBots.addAll(bot.cross(bot2));
        }
        bots = newBots;
    }


    public void showInfo() {
        System.out.println("Bots " + bots.size());
        System.out.println("Average - " + getAverageResistance());
        System.out.println("Best bots: ");
        for(Bot bot : bestBots) {
            bot.getSchema().show();
        }
    }

    public void showShortInfo() {
        System.out.println("Average - " + getAverageResistance());
        System.out.println("Best - ");
        bestBots.get(0).getSchema().show();
        System.out.println("\n");
    }
}

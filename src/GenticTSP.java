import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GenticTSP {

    private static final int POPULATION_SIZE = 50;
    private static final double MUTATION_PROBABILITY = 0.02;
    private static final int K = 10; //how many chromosome selected in K tourment
    private static ArrayList<City> allCites = new ArrayList<City>();
    private static ArrayList<City> []population = new ArrayList[POPULATION_SIZE];
    private static double fitnessArray [] = new double[POPULATION_SIZE];
    private static ArrayList<City> parent1, parent2, offspring1, offspring2;


    public void addCity(City city){
        allCites.add(city);
    }

    private ArrayList<City> generateChromsome(){
        ArrayList<City> chromsome = allCites;
        Collections.shuffle(chromsome);
        return chromsome;
    }

    public void initialization(){
        for(int i=0;i<POPULATION_SIZE;i++){
            population[i] = new ArrayList<>(generateChromsome());
        }
    }

    private double getFitness(ArrayList<City> chromosome){
        double totalDistance = 0.0d;
        for (int i = 0; i < chromosome.size()-1; i++) {
            totalDistance += Sphere.havrsineDistance(chromosome.get(i),chromosome.get(i+1));
        }
        totalDistance += Sphere.havrsineDistance(chromosome.get(chromosome.size()-1),chromosome.get(0));
        return totalDistance;
    }

    public void calculateFitness(){
        for (int i = 0; i < POPULATION_SIZE; i++) {
            fitnessArray[i] = getFitness(population[i]);
        }
    }
    public void selection(){
        Random random = new Random();
        //select first parent
        int selectP1[] = new int[K];
        //select randomly K chromosome
        for (int i = 0; i < K; i++) {
            selectP1[i] = random.nextInt(POPULATION_SIZE);
            System.out.println("p1-random"+i+"= "+selectP1[i]+"-f= "+fitnessArray[selectP1[i]]);
        }
        int bestOne = selectP1[0];
        double bestFittness = getFitness(population[selectP1[0]]);
        //pick the best one as first parent
        for (int i = 1; i < K; i++) {
            if(getFitness(population[selectP1[i]]) < bestFittness){
                bestOne = selectP1[i];
                bestFittness = getFitness(population[selectP1[i]]);
            }
        }
        parent1 = new ArrayList<City>(population[bestOne]);
        System.out.println("p1: "+parent1 + "best: "+bestOne);

        //select the second parent
        int selectP2[] = new int[K];
        //select randomly K chromosome
        for (int i = 0; i < K; i++) {
            selectP2[i] = random.nextInt(POPULATION_SIZE);
            System.out.println("p2-random"+i+"= "+selectP2[i]+"-f= "+fitnessArray[selectP2[i]]);
        }
        bestOne = selectP1[0];
        bestFittness = getFitness(population[selectP2[0]]);
        //pick the best one as first parent
        for (int i = 1; i < K; i++) {
            if(getFitness(population[selectP2[i]]) < bestFittness){
                bestOne = selectP2[i];
                bestFittness = getFitness(population[selectP2[i]]);
            }
        }
        parent2 = new ArrayList<City>(population[bestOne]);

        System.out.println("p2: "+parent2+ "best: "+bestOne);

    }

    public void cycleCrossover(){
        int i=0;
        City []o1 = new City[parent1.size()];
        City []o2 = new City[parent2.size()];
        do {
            o1[i] = parent1.get(i);
            i = parent1.indexOf(parent2.get(i));
        }while(i != 0);
        for (int j = 0; j < o1.length; j++) {
            if (o1[j]==null){
                o1[j] = parent2.get(j);
            }
        }
        for (int j = 0; j < o1.length; j++) {
            System.out.println(o1[j]);
        }

    }

    public void printPopulation(){
        for(int i=0;i<POPULATION_SIZE;i++){
            System.out.println(i+"->"+population[i]+" - fitness = "+fitnessArray[i]);
        }
    }

}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GenticTSP {

    private static final int POPULATION_SIZE = 50;
    private static final double MUTATION_PROBABILITY = 0.06;
    private static final int K = 10; //how many chromosome selected in K tourment
    private static ArrayList<City> allCites = new ArrayList<City>();
    private static ArrayList<City> []population = new ArrayList[POPULATION_SIZE];
    private static double fitnessArray [] = new double[POPULATION_SIZE];
    private static ArrayList<City> parent1, parent2, offspring1, offspring2;
    private static int p1Index, p2Index;


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
                p1Index = bestOne;
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
        bestOne = selectP2[0];
        bestFittness = getFitness(population[selectP2[0]]);
        //pick the best one as first parent
        for (int i = 1; i < K; i++) {
            if(getFitness(population[selectP2[i]]) < bestFittness){
                bestOne = selectP2[i];
                bestFittness = getFitness(population[selectP2[i]]);
                p2Index = bestOne;
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
        System.out.print("o1: ");
        for (int j = 0; j < o1.length; j++) {
            System.out.print(o1[j]+",");
        }

        i=0;
        do {
            o2[i] = parent2.get(i);
            i = parent2.indexOf(parent1.get(i));
        }while(i != 0);
        for (int j = 0; j < o2.length; j++) {
            if (o2[j]==null){
                o2[j] = parent1.get(j);
            }
        }
        System.out.print("o2: ");
        for (int j = 0; j < o2.length; j++) {
            System.out.print(o2[j]+",");
        }
        offspring1 = new ArrayList<City>(Arrays.asList(o1));
        offspring2 = new ArrayList<City>(Arrays.asList(o2));

    }

    public void mutation(){
        Random r = new Random();
        double value = r.nextDouble();
        if(value<MUTATION_PROBABILITY){
            mutate(offspring1);
        }
        value = r.nextDouble();
        if(value<MUTATION_PROBABILITY){
            mutate(offspring2);
        }
    }
    private static void mutate(ArrayList<City> chromosome){
        Random r = new Random();
        int i1, i2;
        i1 = r.nextInt(chromosome.size());
        i2 = r.nextInt(chromosome.size());
        Collections.swap(chromosome,i1,i2);
    }
    public void printPopulation(){
        for(int i=0;i<POPULATION_SIZE;i++){
            System.out.println(i+"->"+population[i]+" - fitness = "+fitnessArray[i]);
        }
    }
    public void updateChromosomes(){
        double p1Fit = getFitness(parent1);
        double p2Fit = getFitness(parent2);
        double o1Fit = getFitness(offspring1);
        double o2Fit = getFitness(offspring2);
        ArrayList<City> best1, best2;
        double bestFitTwo = p1Fit+p2Fit;
        int state = 1;
        if((p1Fit+o1Fit)<bestFitTwo){
            bestFitTwo = p1Fit+o1Fit;
            state = 2;
        }
        if((p1Fit+o2Fit)<bestFitTwo){
            bestFitTwo = p1Fit+o2Fit;
            state = 3;
        }
        if((p2Fit+o1Fit)<bestFitTwo){
            bestFitTwo = p2Fit+o1Fit;
            state = 4;
        }
        if((p2Fit+o2Fit)<bestFitTwo){
            bestFitTwo = p2Fit+o2Fit;
            state = 5;
        }
        if((o1Fit+o2Fit)<bestFitTwo){
            bestFitTwo = o1Fit+o2Fit;
            state = 6;
        }
        switch (state){
            case 1:
                best1 = new ArrayList<City>(parent1);
                best2 = new ArrayList<City>(parent2);
                break;
            case 2:
                best1 = new ArrayList<City>(parent1);
                best2 = new ArrayList<City>(offspring1);
                break;
            case 3:
                best1 = new ArrayList<City>(parent1);
                best2 = new ArrayList<City>(offspring2);
                break;
            case 4:
                best1 = new ArrayList<City>(parent2);
                best2 = new ArrayList<City>(offspring1);
                break;
            case 5:
                best1 = new ArrayList<City>(parent2);
                best2 = new ArrayList<City>(offspring2);
                break;
            case 6:
                best1 = new ArrayList<City>(offspring1);
                best2 = new ArrayList<City>(offspring2);
                break;
            default:
                best1 = new ArrayList<>();
                best2 = new ArrayList<>();
        }
        System.out.println("state = "+state);
        population[p1Index] = new ArrayList<City>(best1);
        population[p2Index] = new ArrayList<City>(best2);
    }
    public ArrayList<City> getPath(){
        double bestFitness = fitnessArray[0];
        int bestIndex = 0;
        for (int i = 0; i < fitnessArray.length; i++) {
            if(fitnessArray[i]<bestFitness){
                bestFitness = fitnessArray[i];
                bestIndex = i;
            }
        }
        System.out.println(bestFitness);
        ArrayList<City> path = new ArrayList<City>(population[bestIndex]);
        path.add(path.get(0));
        return path;
    }
}

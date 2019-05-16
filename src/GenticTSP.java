import java.util.ArrayList;
import java.util.Collections;

public class GenticTSP {

    private static final int POPULATION_SIZE = 50;
    private static ArrayList<City> allCites = new ArrayList<City>();
    private ArrayList<City> []population = new ArrayList[POPULATION_SIZE];

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

    public double fitness(ArrayList<City> chromosome){
        double totalDistance = 0.0d;
        for (int i = 0; i < chromosome.size()-1; i++) {
            totalDistance += Sphere.havrsineDistance(chromosome.get(i),chromosome.get(i+1));
        }
        totalDistance += Sphere.havrsineDistance(chromosome.get(chromosome.size()-1),chromosome.get(0));
        return totalDistance;
    }

    public void printPopulation(){
        for(int i=0;i<POPULATION_SIZE;i++){
            System.out.println(i+"->"+population[i]+" - fitness = "+fitness(population[i]));
        }
    }

}

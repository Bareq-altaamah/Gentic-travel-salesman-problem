public class Program {

    private static final int maxGeneration = 200;

    public static void main(String[] args) {
        GenticTSP tsp = new GenticTSP();
        tsp.addCity(new City("Baghdad",33.312805, 44.361488));
        tsp.addCity(new City("Basra",30.508102, 47.783489));
        tsp.addCity(new City("Sulaymania",35.566864, 45.416107));
        tsp.addCity(new City("Hilla",32.483334, 44.433334));
        tsp.addCity(new City("Erbil",36.191113, 44.009167));
        tsp.addCity(new City("Mosul",36.340000, 43.130001));
        tsp.addCity(new City("kirkuk",35.478565, 44.401932));
        tsp.addCity(new City("Karbala",32.607380,44.081657));
        int generation = 0;
        tsp.initialization();
        System.out.println("G0:");
        tsp.printPopulation();
        while (generation < maxGeneration){
            tsp.calculateFitness();
            tsp.selection();
            tsp.cycleCrossover();
            tsp.mutation();
            tsp.updateChromosomes();

            generation++;
            System.out.println("G"+generation);
            tsp.printPopulation();

        }
        System.out.println("best path found is: "+tsp.getPath());

    }
}

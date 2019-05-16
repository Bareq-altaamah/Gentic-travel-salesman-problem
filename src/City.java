public class City {

    private  String name;
    private double latitude;
    private double longitude;

    public City(String name, double latitude,double longitude){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    @Override
    public boolean equals(Object obj) {
        return (this.name).equals(((City)obj).name);
    }
}

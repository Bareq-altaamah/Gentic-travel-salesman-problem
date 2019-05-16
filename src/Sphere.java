public class Sphere {
    private final static int R = 6371;

    public static double havrsineDistance(City c1, City c2) {
        Double lat1 = c1.getLatitude();
        Double lon1 = c1.getLongitude();
        Double lat2 = c2.getLatitude();
        Double lon2 = c2.getLongitude();
        Double deltaLatitiude = degToRad(lat2 - lat1);
        Double deltaLongtiude = degToRad(lon2 - lon1);

        Double a = Math.sin(deltaLatitiude / 2) * Math.sin(deltaLatitiude / 2) +
                        Math.cos(degToRad(lat1)) * Math.cos(degToRad(lat2)) *
                        Math.sin(deltaLongtiude / 2) * Math.sin(deltaLongtiude / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distance = R * c;

        return distance;
    }
    private static Double degToRad(Double theta) {
        return theta * (Math.PI / 180);
    }
}

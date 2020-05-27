package Entity;

public class Stella {
    private int idstar;
    private String nome;
    private double lat;
    private double longi;
    private double flux;
    private String type;
    private double percentuale;
    private  double distance;
    private boolean inFil;


    public Stella(int idstar, double distance,  double flux) {
        this.idstar = idstar;
        this.distance = distance;
        this.flux = flux;
    }

    public Stella(int idstar, double lat, String type,double longi){
        this.idstar = idstar;
        this.type = type;
        this.longi = longi;
        this.lat = lat;
        this.inFil = false;
    }

    public Stella(int idstar, String nome, Double longi, double lat, double flux, String type) {
        this.idstar = idstar;
        this.nome = nome;
        this.longi = longi;
        this.lat = lat;
        this.flux = flux;
        this.type = type;
    }


    public int getIdstar() {
        return idstar;
    }

    public String getType() {
        return type;
    }

    public double getLat() {
        return lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setIdstar(int idstar) {
        this.idstar = idstar;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getFlux() {
        return flux;
    }

    public void setFlux(double flux) {
        this.flux = flux;
    }

    public double getPercentuale() {
        return percentuale;
    }

    public void setPercentuale(double percentuale) {
        this.percentuale = percentuale;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public boolean isInFil() {
        return inFil;
    }

    public void setInFil(boolean inFil) {
        this.inFil = inFil;
    }
}

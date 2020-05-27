package Entity;

public class PuntoContorno {
    private int idfil;
    private double latitudine;
    private double longitudine;
    private int n;

    public PuntoContorno(int idfil, double longitudine, double latitudine){
        this.idfil = idfil;
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }

    public PuntoContorno(double longitudine, double latitudine) {
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }

    public PuntoContorno(int idfil) {
        this.idfil = idfil;
    }

    public int getIdfil() {
        return idfil;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public void setIdfil(int idfil) {
        this.idfil = idfil;
    }
}

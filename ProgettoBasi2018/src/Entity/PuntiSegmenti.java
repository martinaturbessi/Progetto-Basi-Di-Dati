package Entity;

public class PuntiSegmenti {
    int idfil;
    int idbranch;
    double glonBr;
    double glatBr;
    int n;
    double flux;
    double distance;



    public PuntiSegmenti(int idfil)  {
        this.idfil = idfil;
    }

    public PuntiSegmenti(double glonBr, double glatBr) {

    }

    public int getIdfil() {
        return idfil;
    }

    public void setIdfil(int idfil) {
        this.idfil = idfil;
    }

    public int getN()   {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getGlonBr() {
        return glonBr;
    }

    public void setGlonBr(double glonBr) {
        this.glonBr = glonBr;
    }

    public double getGlatBr() {
        return glatBr;
    }

    public void setGlatBr(double glatBr) {
        this.glatBr = glatBr;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


}

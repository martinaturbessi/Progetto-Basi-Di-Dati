package Entity;

public class Strumento {
    private String nomestrumento;
    private String nomesat;
    private Double banda;

    public Strumento(String nomestrumento,String nomesat, double banda) {
        this.nomestrumento = nomestrumento;
        this.nomesat = nomesat;
        this.banda = banda;
    }

    /**
     * @return the nomestrumento
     */
    public String getNomeStrumento() {
        return nomestrumento;
    }

    /**
     * @param nomestrumento the nomestrumento to set
     */
    public void setNomeStrumento(String nomestrumento) {
        this.nomestrumento = nomestrumento;
    }

    /**
     * @return the nomesat
     */
    public String getNomeSat() {
        return nomesat;
    }

    /**
     * @param nomesat the nomesat to set
     */
    public void setNomeSat(String nomesat) {
        this.nomesat = nomesat;
    }

    public Double getBanda() {
        return banda;
    }

    public void setBanda(Double banda) {
        this.banda = banda;
    }
}
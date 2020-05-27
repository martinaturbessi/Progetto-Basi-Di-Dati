package Entity;

import java.time.LocalDate;

public class Satellite {
    private String nomesat;
    private LocalDate primaoss;
    private LocalDate termineop;

    public Satellite(String nomesat, LocalDate primaoss, LocalDate termineop) {
        this.nomesat = nomesat;
        this.primaoss = primaoss;
        this.termineop = termineop;
    }

    /**
     * @return the nomesat
     */
    public String getNomesat() {
        return nomesat;
    }

    /**
     * @param nomesat the nomesat to set
     */
    public void setNomesat(String nomesat) {
        this.nomesat = nomesat;
    }

    /**
     * @return the primaoss
     */
    public LocalDate getPrimaoss() {
        return primaoss;
    }

    /**
     * @param primaoss the primaoss to set
     */
    public void setPrimaoss(LocalDate primaoss) {
        this.primaoss = primaoss;
    }

    /**
     * @return the termineop
     */
    public LocalDate getTermineop() {
        return termineop;
    }

    /**
     * @param termineop the termineop to set
     */
    public void setTermineop(LocalDate termineop) {
        this.termineop = termineop;
    }

}

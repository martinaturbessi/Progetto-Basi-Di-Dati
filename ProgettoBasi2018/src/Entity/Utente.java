package Entity;

public class Utente {

    private String nome;
    private String cognome;
    private String username;
    private String password;
    private String email;
    private String ruolo;

    public Utente(String nome,String cognome,String username,String password,String email,String ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
        this.email = email;
    }


    public Utente() {
        // TODO Auto-generated constructor stub
    }


    public String getNome() {
        return nome;
    }


    public String getCognome() {
        return cognome;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @param cognome the cognome to set
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }


}

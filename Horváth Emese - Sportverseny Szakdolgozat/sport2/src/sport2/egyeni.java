package sport2;

public class egyeni {
        private Integer id;
    private String nev;
    private String datum;
    private String I;
    private String II;
    private String III;

    public egyeni(Integer id, String nev, String datum, String I, String II, String III) {
        this.id = id;
        this.nev = nev;
        this.datum = datum;
        this.I = I;
        this.II = II;
        this.III = III;
    }

    public Integer getId() {
        return id;
    }

    public String getNev() {
        return nev;
    }

    public String getDatum() {
        return datum;
    }

    public String getI() {
        return I;
    }

    public String getII() {
        return II;
    }

    public String getIII() {
        return III;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public void setI(String I) {
        this.I = I;
    }

    public void setII(String II) {
        this.II = II;
    }

    public void setIII(String III) {
        this.III = III;
    }
    
    
    
    
}

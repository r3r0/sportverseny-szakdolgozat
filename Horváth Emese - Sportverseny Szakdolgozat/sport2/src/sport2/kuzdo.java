package sport2;

public class kuzdo {
    private Integer id;
    private String nev;
    private String datum;
    private String win;
    private String lose;

    public kuzdo(Integer id, String nev, String datum, String win, String lose) {
        this.id = id;
        this.nev = nev;
        this.datum = datum;
        this.win = win;
        this.lose = lose;
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

    public String getWin() {
        return win;
    }

    public String getLose() {
        return lose;
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

    public void setWin(String win) {
        this.win = win;
    }

    public void setLose(String lose) {
        this.lose = lose;
    }
    
    

}

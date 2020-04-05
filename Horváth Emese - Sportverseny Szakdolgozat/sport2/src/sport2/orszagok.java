
package sport2;


public class orszagok {
    private Integer  id;  
    private String nev;
    private Integer versenyzok;
    private Integer pont;

    public orszagok(Integer id, String nev, Integer versenyzok, Integer pont) {
        this.id = id;
        this.nev = nev;
        this.versenyzok = versenyzok;
        this.pont = pont;
    }

    public Integer getId() {
        return id;
    }

    public String getNev() {
        return nev;
    }

    public Integer getVersenyzok() {
        return versenyzok;
    }

    public Integer getPont() {
        return pont;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public void setVersenyzok(Integer versenyzok) {
        this.versenyzok = versenyzok;
    }

    public void setPont(Integer pont) {
        this.pont = pont;
    }
    
    
    
    
}

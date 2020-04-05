package sport2;

/**
 *
 * @author Horváth Emese
 */
public class versenyzok {
    private Integer id;
    private String nev;
    private String orszag;
    private Integer pont;
    private Integer kor;
    private String edzo;
    private String status;
    private String comment;
    


    public versenyzok(Integer id, String nev, String orszag, Integer pont, Integer kor, String edzo, String status, String comment) {
        this.id = id;
        this.nev = nev;
        this.orszag = orszag;
        this.pont = pont;
        this.kor = kor;
        this.edzo = edzo;
        this.status = status;
        this.comment = comment;

    }

    public Integer getId() {
        return id;
    }

    public String getNev() {
        return nev;
    }

    public String getOrszag() {
        return orszag;
    }

    public Integer getPont() {
        return pont;
    }

    public Integer getKor() {
        return kor;
    }

    public String getEdzo() {
        return edzo;
    }

    public String getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public void setOrszag(String orszag) {
        this.orszag = orszag;
    }

    public void setPont(Integer pont) {
        this.pont = pont;
    }

    public void setKor(Integer kor) {
        this.kor = kor;
    }

    public void setEdzo(String edzo) {
        this.edzo = edzo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
  

    

   
    
}

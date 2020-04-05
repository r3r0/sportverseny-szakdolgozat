
package sport2;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import panel.Panel;

/*
 * @author Horváth Emese
 */

/*
 * Adatbázis kezelő osztály az adatbázis beolvasását, módosítását és
 * törlését végzi.
 * db = az adatbázishoz tartozó útvonal megadása
 * user = felhasználónév
 * pass = jelszó
 */


public class DB {
    
        final String db = "jdbc:mysql://localhost:3306/sport"
            + "?useUnicode=true&characterEncoding=UTF-8";
    final String user = "root";
    final String pass = "";
    
    private FileInputStream fis;
    
    
     /**
     * adatbázisban az eddigi meglévő országok beolvasása
     * input adatok:
     * @param tabla az ország adatai (id, név, versenyzők létszáma, pontszám)
     * @param lista az ország neve, ez majd egy ChoiceBox-ba fog kelleni
     */
    

    public void orszagBe(ObservableList<orszagok> tabla, ObservableList<String> lista) {
         String s = "SELECT * FROM orszag ORDER BY pont DESC;";
        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ResultSet eredmeny = ekp.executeQuery();
            tabla.clear();
            lista.clear();
            while (eredmeny.next()) {
                tabla.add(new orszagok(
                        eredmeny.getInt("id"),
                        eredmeny.getString("name"),
                        eredmeny.getInt("letszam"),
                        eredmeny.getInt("pont"))
                );
                    lista.add((eredmeny.getString("name")));                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Panel.hiba("hiba", ex.getMessage());
        }
    }
    /**
     * adatbázisban az ország táblában új ország hozzáadása
     * @param name az ország neve
     * @return hiba esetén megadja a hibaüzenetet
     */    
           public String orszag_hozzaad(String name) {
        String s = "INSERT INTO orszag (name) VALUES (?);";
        

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, name);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }
    /**
     * adatbázisban egy kijelölt ország módosítása
     * @param name az ország új neve
     * @param id a kijelölt ország azonosítója
     */             
   public int orszag_modosit(String name, Integer id) {
        String s = "UPDATE `orszag` SET `name` = ? "
                + "WHERE `id` = ?;"; 
        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, name);
            ekp.setInt(2, id);

            return ekp.executeUpdate();
        } catch (SQLException ex) {
            Panel.hiba("Orszag módosítás: ", ex.getMessage());
            return 0;
        }
    }           
    /**
     * adatbázisban egy kijelölt ország törlése
     * @param id a kijelölt ország azonosítója
     */         
     public String orszag_torol(int id) {
        String p = "DELETE FROM orszag WHERE id=?;";
        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(p)) {
            ekp.setInt(1, id);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            Panel.hiba("Orszag módosítás: ", ex.getMessage());
            return "";
        }
    }  
     
   /**
     * adatbázisban az országok pontszámának és versenyzőlétszámnak frissítése
     * 
     * pontszám = azoknak a versenyzőknek összesíti a pontszámát, akik ugyanazt
     * az országot képviselik
     * 
     * létszám = az összes aktív vagy beteg versenyző országonkénti megszámolása,
     * azonban azokat a versenyzőket, akik visszavonultak vagy diszkvalifikálva
     * lettek nem veszi itt figyelembe
     * 
     */    
   
    public String orszag_PV_frissit(){
                String s = "UPDATE orszag set pont = (select sum(versenyzok.pont) from versenyzok"
                        + " where versenyzok.orszagid = orszag.id);"; 
                String v = "UPDATE orszag set letszam = (select count(versenyzok.id) from versenyzok "
                        + "where versenyzok.orszagid = orszag.id and versenyzok.status in('Aktív', 'Beteg'));";        
        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s);
                PreparedStatement ekp2 = kapcs.prepareStatement(v)) {   
            ekp.executeUpdate();
            ekp2.executeUpdate();
            return "";
        } catch (SQLException ex) {
            Panel.hiba("Orszag frissites: ", ex.getMessage());
            return "";
        }
    }     
    
     
     /**
     * adatbázisban az eddigi meglévő versenyzők beolvasása
     * input adatok:
     * @param tabla az versenyzők adatai (id, név, ország, pont, kor, edző, státusz,
     * komment, fénykép)
     */    
 
     
  public void versenyzokBe(ObservableList<versenyzok> tabla) {
       String s = "SELECT versenyzok.id, versenyzok.nev, orszag.name AS orszag,versenyzok.pont, "
               + "versenyzok.kor, versenyzok.edzo, versenyzok.status, versenyzok.comment "
               + "FROM versenyzok JOIN orszag WHERE versenyzok.orszagid=orszag.id ORDER BY "
               + "versenyzok.pont DESC;";
        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ResultSet eredmeny = ekp.executeQuery();
            tabla.clear();

            while (eredmeny.next()) {
                tabla.add(new versenyzok(
                        eredmeny.getInt("id"),
                        eredmeny.getString("nev"),
                        eredmeny.getString("orszag"),
                        eredmeny.getInt("pont"),
                        eredmeny.getInt("kor"),
                        eredmeny.getString("edzo"),
                        eredmeny.getString("status"),
                        eredmeny.getString("comment"))
                ); }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Panel.hiba("hiba", ex.getMessage());
        }
    }    
  
  
     /**
     * adatbázisban az eddigi meglévő országok beolvasása
     *
     * input adatok:
     * @param h1 a versenyzők neveinek beolvasása az első helyezettekhez 
     * tartozó ChoiceBox-ba
     *
     * @param h2 a versenyzők neveinek beolvasása az második helyezettekhez 
     * tartozó ChoiceBox-ba  
     *
     * @param h3 a versenyzők neveinek beolvasása az harmadik helyezettekhez 
     * tartozó ChoiceBox-ba  
     *
     * @param win a versenyzők neveinek beolvasása a küzdő sport nyerteseihez
     * tartozó ChoiceBox-ba  
     *
     * @param lose a versenyzők neveinek beolvasása a küzdő sport veszteseihez
     * tartozó ChoiceBox-ba  
     *
     */  

  public void cbxBe(ObservableList<String> h1,ObservableList<String> h2, ObservableList<String> h3, 
          ObservableList<String> win, ObservableList<String> lose) {
       String s = "SELECT nev FROM versenyzok ORDER BY status, nev";
        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ResultSet eredmeny = ekp.executeQuery();
            h1.clear();
            h2.clear();
            h3.clear();
            win.clear();
            lose.clear();

            while (eredmeny.next()) {
            h1.add((eredmeny.getString("nev")));
            h2.add((eredmeny.getString("nev")));
            h3.add((eredmeny.getString("nev")));
            win.add((eredmeny.getString("nev")));
            lose.add((eredmeny.getString("nev")));
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Panel.hiba("hiba", ex.getMessage());
        }

    }     
     
   /**
     * adatbázisban a versenyző táblában új versenyző hozzáadása
     * @param nev a versenyző neve
     * @param nev2 az ország neve
     * @param kor a versenyző kora
     * @param edzo az edző neve, ha van a versenyzőnek
     * @param status a versenyző státusza (pl. Aktív, Beteg, stb)
     * @param comment egyéb hozzáfűzés a versenyzővel kapcsolatban
     * @return hiba esetén megadja a hibaüzenetet
     */       
        public String versenyzok_hozzaad(String nev, String nev2, Integer kor, String edzo, String status, String comment) {
        String s = "INSERT INTO versenyzok (nev, orszagid, kor, edzo, status, comment) " +
"VALUES (?, (SELECT id from orszag where name=?), ?, ?, ?, ?);";
        

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, nev);
            ekp.setString(2, nev2);
            ekp.setInt(3, kor);
            ekp.setString(4, edzo);
            ekp.setString(5, status);
            ekp.setString(6, comment);
           ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    } 
        
    /**
     * adatbázisban a versenyző táblában egy kijelölt versenyző módosítása
     * @param nev a versenyző neve
     * @param orszag az ország neve
     * @param kor a versenyző kora
     * @param edzo az edző neve, ha van a versenyzőnek
     * @param status a versenyző státusza (pl. Aktív, Beteg, stb)
     * @param comment egyéb hozzáfűzés a versenyzővel kapcsolatban
     * @param id a versenyző azonosítója
     * @return hiba esetén megadja a hibaüzenetet
     */     
       
  public String versenyzok_modosit(String nev, String orszag, Integer kor, String edzo, String status,
          String comment, Integer id) {
        String s = "UPDATE versenyzok SET `nev` = ?, " +
"orszagid = (SELECT orszag.id from orszag where orszag.`name`=?), " +
"`kor`=?, " +
"`edzo`=?, " +
"`status`=?, " +
"`comment`=? " +
"WHERE `id`=?;";        
        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)
                )                {    
            ekp.setString(1, nev);
            ekp.setString(2, orszag);
            ekp.setInt(3, kor);
            ekp.setString(4, edzo);
            ekp.setString(5, status);
            ekp.setString(6, comment);
            ekp.setInt(7, id);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            Panel.hiba("Versenyzok módosítás: ", ex.getMessage());
            return "";
        }
    }
    /**
     * adatbázisban egy kijelölt versenyző törlése
     * @param id a kijelölt versenyző azonosítója
     */    
    public String versenyzo_torol(int id) {
        String p = "DELETE FROM versenyzok WHERE id=?;";
        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(p)) {
            ekp.setInt(1, id);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            Panel.hiba("Orszag módosítás: ", ex.getMessage());
            return "";
        }
    }


   /**
     * adatbázisban a versenyzők pontszámának frissítése
     * 
     * pontszám = a versenyző összes elért pontszáma, függ attól, hogy milyen
     * típusú versenyben vett részt, valamint, hogy milyen helyezét ért el
     * egyéni sport esetén
     * 
     * Egyéni sportban I. helyezés -> +3 pont
     * Egyéni sportban II. helyezés -> +2 pont
     * Egyéni sportban III. helyezés -> +1 pont
     * Küzdősportban nyerés -> +1 pont
     * 
     * Ebből pontszámitásból csak a diszkvalifikált versenyzők térnek ki, az ő
     * pontszámuk mindig 0
     * 
     */  

    public String versenyzok_pont_frissit(){
                String s = "UPDATE versenyzok set pont = ((select count(egyeni.id) from egyeni where "
                        + "versenyzok.id = egyeni.1st and status != 'Diszkvalifikált')*3) +" +
"((select count(egyeni.id) from egyeni where versenyzok.id = egyeni.2nd and status != 'Diszkvalifikált')*2)"
                        + " + (select count(egyeni.id) from egyeni where versenyzok.id = egyeni.3rd"
                        + " and status != 'Diszkvalifikált') "
                        + "+ (select count(kuzdo.id) from kuzdo where versenyzok.id = kuzdo.win"
                        + " and status != 'Diszkvalifikált');";          
        try (Connection kapcs = DriverManager.getConnection(db, user, pass);  
                PreparedStatement ekp = kapcs.prepareStatement(s)) {   

            ekp.executeUpdate();


            return "";
        } catch (SQLException ex) {
            Panel.hiba("Versenyzok frissites: ", ex.getMessage());
            return "";
        }
    }    



     /**
     * adatbázisban az eddigi meglévő egyéni sportok beolvasása
    
     * input adatok:
     * @param tabla az egyéni sportok adatai (id, név, dátum, első helyezett,
     * második helyezett, harmadik helyezett)
     */ 

       public void egyeniBe(ObservableList<egyeni> tabla) {
       String s = "SELECT vs.id, vs.name, vs.date, " +
"(SELECT versenyzok.nev FROM versenyzok JOIN egyeni " +
"WHERE versenyzok.id = egyeni.1st and egyeni.id = vs.id) as h1," +
"(SELECT versenyzok.nev FROM versenyzok JOIN egyeni " +
"WHERE versenyzok.id = egyeni.2nd and egyeni.id = vs.id) as h2, " +
"(SELECT versenyzok.nev FROM versenyzok JOIN egyeni " +
"WHERE versenyzok.id = egyeni.3rd and egyeni.id = vs.id) as h3 " +
"FROM egyeni as vs ORDER BY date;";
        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ResultSet eredmeny = ekp.executeQuery();
            tabla.clear();
            while (eredmeny.next()) {
                tabla.add(new egyeni(
                        eredmeny.getInt("id"),
                        eredmeny.getString("name"),
                        eredmeny.getString("date"),
                        eredmeny.getString("h1"),
                        eredmeny.getString("h2"),
                        eredmeny.getString("h3"))
                ); 
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Panel.hiba("hiba", ex.getMessage());
        }
    }   
       
  /**
     * adatbázisban az egyéni sportok táblában új egyéni sport hozzáadása
     * @param nev a sportág neve
     * @param datum a dátum, amikor a sport történt
     * @param elso az első helyezett neve
     * @param masodik a második helyezett neve
     * @param harmadik a harmadik helyezett neve
     * @return hiba esetén megadja a hibaüzenetet
     */        
       
        public String egyeni_hozzaad(String nev, String datum, String elso, String masodik, String harmadik) {
        String s = "INSERT INTO egyeni (name,date,1st,2nd,3rd) " +
"VALUES (?,?,(SELECT id from versenyzok where `nev` = ?)," +
"(SELECT id from versenyzok where `nev` = ?)," +
"(SELECT id from versenyzok where `nev` =  ?));";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, nev);
            ekp.setString(2, datum);
            ekp.setString(3, elso);
            ekp.setString(4, masodik);
            ekp.setString(5, harmadik);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            return ex.getMessage();
        }

    } 
        
  /**
     * adatbázisban az egyéni sportok táblában egy kijelölt egyéni sport 
     * modosítása
     * 
     * @param nev a sportág neve
     * @param datum a dátum, amikor a sport történt
     * @param h1 az első helyezett neve
     * @param h2 a második helyezett neve
     * @param h3 a harmadik helyezett neve
     * @param id a sportesemény azonosítója
     */         
    public String egyeni_modosit(String nev, String nap, String h1, String h2, String h3, Integer id) {
        String s = "UPDATE egyeni SET `name` = ?, `date`=?, "
                + "1st = (SELECT id from versenyzok where `nev` = ?), "
                + "2nd = (SELECT id from versenyzok where `nev` = ?),"
                + "3rd = (SELECT id from versenyzok where `nev` = ?)"
                + "where id=?;";          
     try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)){    
            ekp.setString(1, nev);
            ekp.setString(2, nap);
            ekp.setString(3, h1);
            ekp.setString(4, h2);
            ekp.setString(5, h3);
            ekp.setInt(6, id);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            Panel.hiba("Egyeni sport módosítás: ", ex.getMessage());
            return "";
        }
    }        
    /**
     * adatbázisban egy kijelölt egyéni sport törlése
     * @param id a kijelölt versenyző azonosítója
     */           
    public String egyeni_torol(int id) {
        String p = "DELETE FROM egyeni WHERE id=?;";
        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(p)) {
            ekp.setInt(1, id);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            Panel.hiba("Egyeni sport torles: ", ex.getMessage());
            return "";
        }
    }       
     /**
     * adatbázisban az eddigi meglévő küzdősportok beolvasása
    
     * input adatok:
     * @param tabla az küzdősportok adatai (id, név, dátum, nyertes, vesztes
     */  
      
public void kuzdoBe(ObservableList<kuzdo> tabla) {
       String s = "SELECT vs.id, vs.name, vs.date, " +
"(SELECT versenyzok.nev FROM versenyzok JOIN kuzdo " +
"WHERE versenyzok.id = kuzdo.win and kuzdo.id = vs.id) as win," +
"(SELECT versenyzok.nev FROM versenyzok JOIN kuzdo " +
"WHERE versenyzok.id = kuzdo.lose and kuzdo.id = vs.id) as lose " +
"FROM kuzdo as vs ORDER BY date;";
        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ResultSet eredmeny = ekp.executeQuery();
            tabla.clear();


            while (eredmeny.next()) {
                tabla.add(new kuzdo(
                        eredmeny.getInt("id"),
                        eredmeny.getString("name"),
                        eredmeny.getString("date"),
                        eredmeny.getString("win"),
                        eredmeny.getString("lose"))
                );

     

                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Panel.hiba("hiba", ex.getMessage());
        }

    }   

  /**
     * adatbázisban a küzdősportok táblában új küzdősportok hozzáadása
     * @param nev a sportág neve
     * @param datum a dátum, amikor a sport történt
     * @param win a nyertes neve
     * @param lose a vesztes  neve
     * @return hiba esetén megadja a hibaüzenetet
     */  
        public String kuzdo_hozzaad(String nev, String datum, String win, String lose) {
        String s = "INSERT INTO kuzdo (name,date,win,lose) " +
"VALUES (?,?,(SELECT id from versenyzok where `nev` = ?)," +
"(SELECT id from versenyzok where `nev` =  ?));";

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, nev);
            ekp.setString(2, datum);
            ekp.setString(3, win);
            ekp.setString(4, lose);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            return ex.getMessage();
        }

    }    
        
  /**
     * adatbázisban a küzdősportok táblában új küzdősportok modosítása
     * @param nev a sportág neve
     * @param datum a dátum, amikor a sport történt
     * @param win a nyertes neve
     * @param lose a vesztess neve
     * @param lose a sport azonosítója
     * @return hiba esetén megadja a hibaüzenetet
     */         
    public String kuzdo_modosit(String nev, String nap, String win, String lose, Integer id) {
        String s = "UPDATE kuzdo SET `name` = ?, `date`=?, "
                + "win = (SELECT id from versenyzok where `nev` = ?), "
                + "lose = (SELECT id from versenyzok where `nev` = ?)"
                + "where id=?;";          
     try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)){    
            ekp.setString(1, nev);
            ekp.setString(2, nap);
            ekp.setString(3, win);
            ekp.setString(4, lose);
            ekp.setInt(5, id);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            Panel.hiba("Kuzdosport módosítás: ", ex.getMessage());
            return "";
        }
    }      
    
    /**
     * adatbázisban egy kijelölt küzdősport törlése
     * @param id a kijelölt versenyző azonosítója
     */     
    public String kuzdo_torol(int id) {
        String p = "DELETE FROM kuzdo WHERE id=?;";
        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(p)) {
            ekp.setInt(1, id);
            ekp.executeUpdate();
            return "";
        } catch (SQLException ex) {
            Panel.hiba("Kuzdosport torles: ", ex.getMessage());
            return "";
        }
    } 
    
    /**
     * Számolást visz végre egy már megadott sql parancs alapján
     * 
     * @param s az előre megadott sql parancs
     * @param count amit meg kell számolnia
     * @return siker esetén visszaadja a megszámlálandó  adatok, hiba esetén 
     * hibaüzenetet ad vissza
     */
    public int count(String s, Integer count){
               try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
        ResultSet eredmeny = ekp.executeQuery();
        eredmeny.next();
        count=eredmeny.getInt("count");

        return count;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Panel.hiba("hiba", ex.getMessage());
        }
     return count;
    }
    
    /**
     * Egy már megadott sql parancs alapján megkeresi a szükséges String-et
     * 
     * @param s az előre megadott sql parancs
     * @param result amit meg kell keresnie
     * @return siker esetén visszaadja a megszámlálandó adatok, hiba esetén 
     * hibaüzenetet ad vissza
     */    
    public String findString(String s, String result){
               try (Connection kapcs = DriverManager.getConnection(db, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
        ResultSet eredmeny = ekp.executeQuery();
        eredmeny.next();
        result=eredmeny.getString("result");

        return result;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Panel.hiba("hiba", ex.getMessage());
        }
     return result;
    }  
    
    /** Betölti az éppen kijelölt versenyző fényképet
     * 
     * @param id a kijelölt versenyző azonosítója
     * @return visszaadja a versenyző fényképét az adatbázisból, ha az létezik
     * @Exception e amennyiben a versenyzőnek nincs megadott profilképe, vissza
     * küldi az alapértelmezett profilképet helyette (unknown.png)
     */

public Image getImageById(Integer id) throws SQLException, IOException  {
        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
        PreparedStatement ekp = kapcs.prepareStatement(
            "SELECT pic FROM versenyzok WHERE versenyzok.id=?;");
    ) {
                    ekp.setInt(1, id);
                    ResultSet results = ekp.executeQuery();
                    Image img = null ;
                    try{
                    if (results.next()) {
                        Blob foto = results.getBlob("pic");
                        InputStream is = foto.getBinaryStream();
                        img = new Image(is) ; // false = no background loading 
                        is.close();
                    }
                    }catch(Exception e){
                        img = new Image("/pfps/unknown.png");
                    }
                    results.close();
                    return img ;
                } catch (Throwable e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return null;
            }


    /** Új profilképet ad meg a kijelölt versenyzőnek és feltölti az adatbázisba
     * 
     * @param file a versenyző új profilképe
     * @param id a versenyző azonosítója
     */
public String setImageById(File file, Integer id) throws SQLException, IOException  {

        try (Connection kapcs = DriverManager.getConnection(db, user, pass);
        PreparedStatement ekp = kapcs.prepareStatement(
            "UPDATE versenyzok SET `pic` = ? WHERE `id` = ?;");
    ) {
           fis = new FileInputStream(file);
           ekp.setBinaryStream(1,(InputStream)fis, (int)file.length());          
           ekp.setInt(2, id);       
            ekp.executeUpdate();
            fis.close();
            
            return "";
        } catch (SQLException ex) {
            Panel.hiba("PFP módosítás: ", ex.getMessage());
            return "";
        }
    }       
}


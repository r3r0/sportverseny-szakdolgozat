
package sport2;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import panel.Panel;


/**
 * A grafikus megjelenítést irányító osztály
 * @author Horváth Emese
 * elnézést, ha valahol kihagytam ékezetek, eredetileg csak angol billentyűzetű
 * géphez volt hozzáférésem.
 */
public class FXMLDocumentController implements Initializable {

    DB ab = new DB();
    sounds ps = new sounds();
    
    private Image image;
     File file;

    @FXML
   private ChoiceBox<String> cbxOrszag;

    @FXML
    private TableView<orszagok> tblOrszagok;

    @FXML
    private TableColumn<orszagok, String> oONev;

    @FXML
    private TableColumn<orszagok, Integer> oOVersenyzo;

    @FXML
    private TableColumn<orszagok, Integer> oOPontszam;

    @FXML
    private TextField txtOnev;
    
    
    @FXML
    private TextField txtEdzo;

    @FXML
    private ChoiceBox<String> cbxStatus;

    @FXML
    private TextField txtComment;

    @FXML
    private ImageView pfp;
    
    
    @FXML
    private TableView<versenyzok> tblVersenyzok;

    @FXML
    private TableColumn<versenyzok, String> oVNev;

    @FXML
    private TableColumn<versenyzok, String> oVOrszag;


    @FXML
    private TableColumn<versenyzok, Integer> oVPontszam;

    @FXML
    private TextField txtVnev;

    @FXML
    private TextField txtKor;



    @FXML
    private TableView<egyeni> tblEgyeni;

    @FXML
    private TableColumn<egyeni, String> oENev;

    @FXML
    private TableColumn<egyeni, String> o1H;

    @FXML
    private TableColumn<egyeni, String> o2H;

    @FXML
    private TableColumn<egyeni, String> o3H;

    @FXML
    private TableColumn<egyeni, String> oEDatum;

    @FXML
    private TextField txtEnev;

    @FXML
    private DatePicker dpEDatum;

    @FXML
    private ChoiceBox<String> cbxH1;

    @FXML
    private ChoiceBox<String> cbxH2;

    @FXML
    private ChoiceBox<String> cbxH3;

    @FXML
    private TableView<kuzdo> tblKuzdo;

    @FXML
    private TableColumn<kuzdo, String> oKNev;

    @FXML
    private TableColumn<kuzdo, String> oWin;

    @FXML
    private TableColumn<kuzdo, String> oLose;

    @FXML
    private TableColumn<kuzdo, String> oKDatum;

    @FXML
    private TextField txtKnev;

    @FXML
    private DatePicker dpKDatum;

    @FXML
    private ChoiceBox<String> cbxWin;

    @FXML
    private ChoiceBox<String> cbxLose;
    
    @FXML
    private TabPane tabpane;
    
    /**
     * A "Keresés" gomb megnyomásakor aktiválódik, csak .jpg és .png fomátumú
     * fájlokat enged kiválasztani
     * Ezután ellenőrzi hogy a megadott kép nem túl nagy-e, ebben az esetben
     * hibaüzenetet ad vissza
     */
    @FXML
    void browsePFP(ActionEvent event) throws IOException, SQLException {
            FileChooser fileChooser = new FileChooser();         
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
            fileChooser.setTitle("Válasszon ki egy profilképet!");
          Stage stage = (Stage)tabpane.getScene().getWindow();        
            file = fileChooser.showOpenDialog(stage);            
            if (file.length() > 1000000){
                ps.playSound("error");  
                Panel.hiba("Hiba!", "Válasz egy kissebb méretű képet!");
            return;
        }            
            if(file != null){
                image = new Image(file.toURI().toString(),140,125, true, true);
                pfp.setImage(image);
                pfp.setPreserveRatio(true);
            }
    }

    /** Egy új egyéni sportot ment valamint ellenőrzi, hogy az adatok megfelelő 
     * karakter hosszoságúak és formátumúak
     */
    @FXML
    void btnEment(ActionEvent event) {
       String nev = txtEnev.getText();
       

          if (nev.length() < 1 || nev.length() > 30) {
            ps.playSound("error");  
            Panel.hiba("Hiba!", "A név 1-30 karakter hosszú lehet!");
            txtEnev.requestFocus();
            return;
        }
        String h1 = cbxH1.getValue();
        String h2 = cbxH2.getValue();
        String h3 = cbxH3.getValue();
        
       /**
        * Beolvassa az első három helyezett nemzetiségét, amennyiben van
        * ugyanolyan nemzetiégű versenyző, hibaüzenetet ír ki
        */            
        
        String h1_nationality = find("orszag.name", "orszag join versenyzok on versenyzok.orszagid = orszag.id ", ("versenyzok.nev='"+h1+"';"));
        String h2_nationality = find("orszag.name", "orszag join versenyzok on versenyzok.orszagid = orszag.id ", ("versenyzok.nev='"+h2+"';"));
        String h3_nationality = find("orszag.name", "orszag join versenyzok on versenyzok.orszagid = orszag.id ", ("versenyzok.nev='"+h3+"';"));

        if (h1_nationality.equals(h2_nationality) || h1_nationality.equals(h3_nationality)  ||  h2_nationality.equals(h3_nationality)) {
            ps.playSound("error");  
            Panel.hiba("Hiba!", "Minden ország csak egy versenyzőt küldhet verenyenként!");
            cbxWin.requestFocus();
            return;
        } 

        String stat1 = find("status", "versenyzok", "nev='"+h1+"'");
        String stat2 = find("status", "versenyzok", "nev='"+h2+"'");
        String stat3 = find("status", "versenyzok", "nev='"+h3+"'");
        
        /**
         * Ellenőrzi higy csak aktív versenyző vannak e egisztrálva az új versenyhez
         */
        
        if (!stat1.equals("Aktív")|| !stat2.equals("Aktív") || !stat3.equals("Aktív") ) {
            ps.playSound("error");  
            Panel.hiba("Hiba!", "Csak aktiv versenyzoket lehet regisztralni uj versenyekhez!");
            txtEnev.requestFocus();
            return;
        }
        
        String nap = ("" + dpEDatum.getValue());
        
        
                    String v = ab.egyeni_hozzaad(nev, nap, h1, h2, h3);
                if (v.isEmpty()) {
            ab.egyeniBe(tblEgyeni.getItems());
            txtEnev.clear();

        } else {
            ps.playSound("error");          
            Panel.hiba("Hiba", v);
        }
                
 
        tblEgyeni.requestFocus();  
        ab.versenyzok_pont_frissit();

        ab.versenyzokBe(tblVersenyzok.getItems());
        ab.orszag_PV_frissit(); 
        ab.orszagBe(tblOrszagok.getItems(), cbxOrszag.getItems());

        
 
    }
    /** Egykijelölt egyéni sportot módost valamint ellenőrzi, hogy az adatok
     *  megfelelő karakter hosszoságúak és formátumúak
     */
    @FXML
    void btnEmodosit(ActionEvent event) {
       String nev = txtEnev.getText();
        int index = tblEgyeni.getSelectionModel().getSelectedIndex();
        if (nev.length() < 1 || nev.length() > 30) {
            ps.playSound("error");  
            Panel.hiba("Hiba!", "A név 1-30 karakter hosszú lehet!");
            txtEnev.requestFocus();
            return;
        }
        
  
        
        int id = tblEgyeni.getItems().get(index).getId();
        String h1 = cbxH1.getValue();

        String h2 = cbxH2.getValue();
        String h3 = cbxH3.getValue();
        
        /**
        * Beolvassa az első három helyezett nemzetiségét, amennyiben van
        * ugyanolyan nemzetiégű versenyző, hibaüzenetet ír ki
        */            
        
        
        String h1_nationality = find("orszag.name", "orszag join versenyzok on versenyzok.orszagid = orszag.id ", ("versenyzok.nev='"+h1+"';"));
        String h2_nationality = find("orszag.name", "orszag join versenyzok on versenyzok.orszagid = orszag.id ", ("versenyzok.nev='"+h2+"';"));
        String h3_nationality = find("orszag.name", "orszag join versenyzok on versenyzok.orszagid = orszag.id ", ("versenyzok.nev='"+h3+"';"));

        if (h1_nationality.equals(h2_nationality) || h1_nationality.equals(h3_nationality)  ||  h2_nationality.equals(h3_nationality)) {
            ps.playSound("error");  
            Panel.hiba("Hiba!", "Minden ország csak egy versenyzőt küldhet verenyenként!");
            cbxWin.requestFocus();
            return;
        } 
        
        String nap = ("" + dpEDatum.getValue());

        
                String sor = ab.egyeni_modosit(nev, nap, h1, h2, h3, id);
        if (sor.isEmpty()) {
            ab.egyeniBe(tblEgyeni.getItems());
            for (int i = 0; i < tblEgyeni.getItems().size(); i++) {
                if (tblEgyeni.getItems().get(i).getId().equals(id)) {
                    tblEgyeni.getSelectionModel().select(i);
                    break;
                }
            }
        }
        tblEgyeni.requestFocus();
        
        ab.versenyzok_pont_frissit();

        ab.versenyzokBe(tblVersenyzok.getItems());
        ab.orszag_PV_frissit(); 
        ab.orszagBe(tblOrszagok.getItems(), cbxOrszag.getItems());
  
 


    }
    /** Egy kijelölt egyéni sportot töröl,
     */
    @FXML
    void btnEtorol(ActionEvent event) {
      int i = tblEgyeni.getSelectionModel().getSelectedIndex();
        if (i > -1) {
            ps.playSound("yesno");  
            if (!Panel.igennem("Törlés", "Törölni szeretnéd ezt a sort?")) {
                return;
            }
            int id = tblEgyeni.getItems().get(i).getId();
            ab.egyeni_torol(id);
            ab.egyeniBe(tblEgyeni.getItems());
            int utolso = tblEgyeni.getItems().size() - 1;
            if (i < utolso) {
                tblEgyeni.getSelectionModel().select(i);
            } else {
                tblEgyeni.getSelectionModel().select(i - 1);
            }
        }
        
        
        tblEgyeni.requestFocus();
        
        ab.versenyzok_pont_frissit();

        ab.versenyzokBe(tblVersenyzok.getItems());
        ab.orszag_PV_frissit(); 
        ab.orszagBe(tblOrszagok.getItems(), cbxOrszag.getItems());

    }

    @FXML
            
    /**
     * Az egyéni sportok módosítható mezőit tiszttja le
     */        
    void btnEuj(ActionEvent event) {
        txtEnev.requestFocus();
        txtEnev.clear();
        cbxH1.setValue(null);
        cbxH2.setValue(null);
        cbxH3.setValue(null);
        dpEDatum.setValue(LocalDate.now());
    }

    @FXML
    void btnKment(ActionEvent event) {
       String nev = txtKnev.getText();
          if (nev.length() < 1 || nev.length() > 30) {
            ps.playSound("error");  
            Panel.hiba("Hiba!", "A név 1-30 karakter hosszú lehet!");
            txtEnev.requestFocus();
            return;
        }
        String win = cbxWin.getValue();
        String lose = cbxLose.getValue();
        
        String statwin = find("status", "versenyzok", "nev='"+win+"'");
        String statlose = find("status", "versenyzok", "nev='"+lose+"'");
        
        /**
         * Ellenőrzi higy csak aktív versenyző vannak e egisztrálva az új versenyhez
         */
        
        if (!statwin.equals("Aktív") || !statlose.equals("Aktív")   ) {
            ps.playSound("error");  
            Panel.hiba("Hiba!", "Csak aktiv versenyzoket lehet regisztralni uj versenyekhez!");
            txtEnev.requestFocus();
            return;
        }
        /**
        * Beolvassa a két versenyző nemzetiségét, amennyiben
        * ugyanolyan nemzetiégüek, hibaüzenetet ír ki
        */            
           
        String winner_nationality = find("orszag.name", "orszag join versenyzok on versenyzok.orszagid = orszag.id ", ("versenyzok.nev='"+win+"';"));
        String loser_nationality = find("orszag.name", "orszag join versenyzok on versenyzok.orszagid = orszag.id ", ("versenyzok.nev='"+lose+"';"));

        if (winner_nationality.equals(loser_nationality)) {
            ps.playSound("error");  
            Panel.hiba("Hiba!", "Ket ugyanolyan nemzetisegu versenyzo nem versenyezhet egymas ellen!");
            cbxWin.requestFocus();
            return;
        } 
        
        String nap = ("" + dpKDatum.getValue());
        
        
                    String v = ab.kuzdo_hozzaad(nev, nap, win, lose);
                if (v.isEmpty()) {
            ab.kuzdoBe(tblKuzdo.getItems());
            txtEnev.clear();

        } else {
            ps.playSound("error");          
            Panel.hiba("Hiba", v);
        }
                
 
        tblKuzdo.requestFocus();  
        ab.versenyzok_pont_frissit();

        ab.versenyzokBe(tblVersenyzok.getItems());
        ab.orszag_PV_frissit(); 
        ab.orszagBe(tblOrszagok.getItems(), cbxOrszag.getItems());

        
    }

    @FXML
    void btnKmodosit(ActionEvent event) {
       String nev = txtKnev.getText();
        int index = tblKuzdo.getSelectionModel().getSelectedIndex();
        if (nev.length() < 1 || nev.length() > 30) {
            ps.playSound("error");  
            Panel.hiba("Hiba!", "A név 1-30 karakter hosszú lehet!");
            txtEnev.requestFocus();
            return;
        }
       int id = tblKuzdo.getItems().get(index).getId();
        String win = cbxWin.getValue();
        String lose = cbxLose.getValue();
        
        /**
        * Beolvassa a két versenyző nemzetiségét, amennyiben
        * ugyanolyan nemzetiégüek, hibaüzenetet ír ki
        */  
        String winner_nationality = find("orszag.name", "orszag join versenyzok on versenyzok.orszagid = orszag.id ", ("versenyzok.nev='"+win+"';"));
        String loser_nationality = find("orszag.name", "orszag join versenyzok on versenyzok.orszagid = orszag.id ", ("versenyzok.nev='"+lose+"';"));

        if (winner_nationality.equals(loser_nationality)) {
            ps.playSound("error");  
            Panel.hiba("Hiba!", "Ket ugyanolyan nemzetisegu versenyzo nem versenyezhet egymas ellen!");
            cbxWin.requestFocus();
            return;
        } 
        
        
        String nap = ("" + dpKDatum.getValue());

        
                String sor = ab.kuzdo_modosit(nev, nap, win, lose, id);
        if (sor.isEmpty()) {
            ab.kuzdoBe(tblKuzdo.getItems());
            for (int i = 0; i < tblKuzdo.getItems().size(); i++) {
                if (tblKuzdo.getItems().get(i).getId().equals(id)) {
                    tblKuzdo.getSelectionModel().select(i);
                    break;
                }
            }
        }
        tblKuzdo.requestFocus();
        
        ab.versenyzok_pont_frissit();

        ab.versenyzokBe(tblVersenyzok.getItems());
        ab.orszag_PV_frissit(); 
        ab.orszagBe(tblOrszagok.getItems(), cbxOrszag.getItems());
  
 
    }

    @FXML
    void btnKtorol(ActionEvent event) {
      int i = tblKuzdo.getSelectionModel().getSelectedIndex();
        if (i > -1) {
            ps.playSound("yesno");  
            if (!Panel.igennem("Törlés", "Törölni szeretnéd ezt a sort?")) {
                return;
            }
            int id = tblKuzdo.getItems().get(i).getId();
            ab.kuzdo_torol(id);
            ab.kuzdoBe(tblKuzdo.getItems());
            int utolso = tblKuzdo.getItems().size() - 1;
            if (i < utolso) {
                tblKuzdo.getSelectionModel().select(i);
            } else {
                tblKuzdo.getSelectionModel().select(i - 1);
            }
        }
        
        
        tblKuzdo.requestFocus();
        
        ab.versenyzok_pont_frissit();

        ab.versenyzokBe(tblVersenyzok.getItems());
        ab.orszag_PV_frissit(); 
        ab.orszagBe(tblOrszagok.getItems(), cbxOrszag.getItems());
    }
    /**
     * A küzdősportok módosítható mezőit tiszttja le
     */ 
    @FXML
    void btnKuj() {
        txtKnev.requestFocus();
        txtKnev.clear();
        cbxWin.setValue(null);
        cbxLose.setValue(null);
        dpKDatum.setValue(LocalDate.now());
    }

    @FXML
    void btnOTorol() {
                int i = tblOrszagok.getSelectionModel().getSelectedIndex();
  
        

        String name = txtOnev.getText();                    
        int counted = count("versenyzok.orszagid", "orszag " +
"join versenyzok on (versenyzok.orszagid = orszag.id)","orszag.name", name);
        
        try{
        if (counted > 0){
            ps.playSound("error");  
             Panel.hiba("Hiba!", "Orszagok akik mar regisztraltak legalabb egy "
                     + "versenyzok nem lehet torolni");
                tblOrszagok.requestFocus();
                return;
            }
        } catch (NumberFormatException ex) { 
            ps.playSound("error");  
            Panel.hiba("Orszag torles: ", ex.getMessage());
            return;
        } 
                
        
        
       
        if (i > -1) {
            ps.playSound("yesno");  
            if (!Panel.igennem("Törlés", "Törölni szeretnéd ezt a sort?")) {
                return;
            }
            int id = tblOrszagok.getItems().get(i).getId();
            ab.orszag_torol(id);
            ab.orszagBe(tblOrszagok.getItems(), cbxOrszag.getItems());
            int utolso = tblVersenyzok.getItems().size() - 1;
            if (i < utolso) {
                tblOrszagok.getSelectionModel().select(i);
            } else {
                tblOrszagok.getSelectionModel().select(i - 1);
            }
        }
        
        
        tblOrszagok.requestFocus();
    }

    @FXML
    void btnOment() {
        String nev = txtOnev.getText();
        
        if (nev.length() < 1 || nev.length() > 30) {
            ps.playSound("error");  
            Panel.hiba("Hiba!", "A név 1-30 karakter hosszú lehet!");
            txtOnev.requestFocus();
            return;
        }


        int counted = count("name", "orszag", "name",  nev);

        if (counted > 0){
            ps.playSound("error");  
            Panel.hiba("Hiba!", "Ez az orszag mar benne van az adatbazisban!");
            txtOnev.requestFocus();
            return;
        }    
                
            String v = ab.orszag_hozzaad(nev);
                if (v.isEmpty()) {
            ab.orszagBe(tblOrszagok.getItems(), cbxOrszag.getItems());
            txtOnev.clear();

        } else {
            ps.playSound("error");  
            Panel.hiba("Hiba", v);
        }
    }

    @FXML
    void btnOmodosit() {
        String name = txtOnev.getText();
        int index = tblOrszagok.getSelectionModel().getSelectedIndex();
        if (name.length() < 1 || name.length() > 30) {
            ps.playSound("error");  
            Panel.hiba("Hiba!", "A név 1-30 karakter hosszú lehet!");
            txtOnev.requestFocus();
            return;
        }
        
        int counted = count("name", "orszag","name", name);

        if (counted > 1){
            ps.playSound("error");  
            Panel.hiba("Hiba!", "Ez az orszag mar benne van az adatbazisban!");
            txtOnev.requestFocus();
            return;
        }    
        
         int id = tblOrszagok.getItems().get(index).getId();
        
                int sor = ab.orszag_modosit(name, id);
        if (sor > 0) {
            ab.orszagBe(tblOrszagok.getItems(), cbxOrszag.getItems());
            for (int i = 0; i < tblOrszagok.getItems().size(); i++) {
                if (tblOrszagok.getItems().get(i).getId().equals(id)) {
                    tblOrszagok.getSelectionModel().select(i);
                    break;
                }
            }
        }
        tblOrszagok.requestFocus();

    }
    /**
     * Az országok módosítható mezőhét tiszttja le
     */ 
      @FXML
    void btnOuj() {
        
        
        txtOnev.requestFocus();
        txtOnev.clear();

    }

    @FXML
    void btnVTorol(ActionEvent event) {
       int i = tblVersenyzok.getSelectionModel().getSelectedIndex();
  
                
                
        /**
        * Ellenőrzés, hogy a versenyző rögzítve lett-e valamilyen versenyben.
        * Mivel lehetnek versenyzők, akiknek nincs pontja, de elert
        * valamilen helyezest (pl. diszkvalifikáltak), nem elég csak a pontszám
        * alapján ellenőrizni.        
        */

        String nev = txtVnev.getText();
        
        
        int player_k_history = count("kuzdo.win + kuzdo.lose","kuzdo " +
"join versenyzok on (versenyzok.id = kuzdo.win || versenyzok.id = kuzdo.lose)",
                "versenyzok.nev ", nev );
        
        int player_e_history = count("egyeni.1st + egyeni.2nd +"
                + " egyeni.3rd"," egyeni " +
"join versenyzok on (versenyzok.id = egyeni.1st || versenyzok.id = egyeni.2nd"
                + " || versenyzok.id = egyeni.3rd )",
                "versenyzok.nev ",  nev );
        
                try {;    
            if (player_k_history + player_e_history > 0) {
                ps.playSound("error");  
                Panel.hiba("Hiba!", "Versenyzok akik mar rogzitve vannak  "
                        + "valamilyen versenyben nem lehet torolni!");
                tblVersenyzok.requestFocus();
                return;
            }
        } catch (NumberFormatException ex) { 
            ps.playSound("error");  
            Panel.hiba("Versenyzo torles: ", ex.getMessage());
            return;
        }        
        
        if (i > -1) {
            ps.playSound("yesno");  
            if (!Panel.igennem("Törlés", "Törölni szeretnéd ezt a sort?")) {
                return;
            }
            int id = tblVersenyzok.getItems().get(i).getId();
            ab.versenyzo_torol(id);
            ab.versenyzokBe(tblVersenyzok.getItems());
            int utolso = tblVersenyzok.getItems().size() - 1;
            if (i < utolso) {
                tblVersenyzok.getSelectionModel().select(i);
            } else {
                tblVersenyzok.getSelectionModel().select(i - 1);
            }
        }
        
        
        tblVersenyzok.requestFocus();     
        ab.orszag_PV_frissit(); 
        ab.orszagBe(tblOrszagok.getItems(), cbxOrszag.getItems());
               ab.cbxBe(cbxH1.getItems(), cbxH2.getItems(), cbxH3.getItems(), 
                cbxWin.getItems(), cbxLose.getItems());
   
    }

    @FXML
    void btnVment(ActionEvent event) throws SQLException, IOException {
        String nev = txtVnev.getText();
        String orszag = cbxOrszag.getValue();
        String edzo = txtEdzo.getText();
        String status = cbxStatus.getValue();
        String comment = txtComment.getText();
        if (nev.length() < 1 || nev.length() > 30) {
            ps.playSound("error");  
            Panel.hiba("Hiba!", "A név 1-30 karakter hosszú lehet!");
            txtVnev.requestFocus();
            return;
        }
        int counted = count("nev", "versenyzok","nev", nev);

        if (counted > 0){
            ps.playSound("error");  
            Panel.hiba("Hiba!", "Ilyen nevu versenyzo mar van az adatbazisban!");
            txtVnev.requestFocus();
            return;
        }   
        
        int counted2 = count_letszam(orszag);
        if (counted2 > 2){
            ps.playSound("error");  
            Panel.hiba("Hiba!", "Egy oszagnak legfeljebb 3 aktiv vagy beteg versenyzoje lehet");
            cbxOrszag.requestFocus();
            return;
        }  
        
        int kor;
        try {
            kor = Integer.parseInt(txtKor.getText());
            if (kor < 16) {
                ps.playSound("error");  
                Panel.hiba("Hiba!", "A versenzoknbek legalab 16 evesnek kell lenniuk");
                txtKor.requestFocus();
                return;
            }
            else if (kor > 99) {
                ps.playSound("error");  
                Panel.hiba("Hiba!", "Kicsit idos a jeleolt a profi sportolashoz");
                txtKor.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            ps.playSound("error");  
            Panel.hiba("Hiba!", "A kor nem szám");
            return;
        }

            String v = ab.versenyzok_hozzaad(nev, orszag, kor, edzo, status, comment);

                if (v.isEmpty()) {
            ab.versenyzok_pont_frissit();
            ab.versenyzokBe(tblVersenyzok.getItems());
            txtVnev.clear();

        } else {
            ps.playSound("error");  
            Panel.hiba("Hiba", v);
        }
         int index = 0;        
        for (int i = 0; i < tblVersenyzok.getItems().size(); i++) {
        if (tblVersenyzok.getItems().get(i).getNev().equals(nev)) {
            index = i;
        }
        }      
        int id = tblVersenyzok.getItems().get(index).getId();
        if (file != null)ab.setImageById(file, id);   
        file=null;        
        tblVersenyzok.requestFocus();     
        ab.orszag_PV_frissit(); 
        ab.orszagBe(tblOrszagok.getItems(), cbxOrszag.getItems());
               ab.cbxBe(cbxH1.getItems(), cbxH2.getItems(), cbxH3.getItems(), 
                cbxWin.getItems(), cbxLose.getItems());
     
    }

    @FXML
    void btnVmodosit() throws SQLException, IOException {
        
        String nev = txtVnev.getText();
        String edzo = txtEdzo.getText();
        String comment = txtComment.getText();
        int index = tblVersenyzok.getSelectionModel().getSelectedIndex();
        String orszag = cbxOrszag.getValue();
        int counted = count("nev", "versenyzok", "nev", nev);

        if (counted > 1){
            ps.playSound("error");  
            Panel.hiba("Hiba!", "Ilyen nevu versenyzo mar van az adatbazisban!");
            txtVnev.requestFocus();
            return;
        }  

        int counted2 = count_letszam(orszag);

        if (counted2 > 3
                ){
            ps.playSound("error");  
            Panel.hiba("Hiba!", "Egy oszagnak legfeljebb 3 aktiv vagy beteg versenyzoje lehet");
            cbxOrszag.requestFocus();
            return;
        }           
        
                int kor;

        String status = cbxStatus.getValue();
        try {
            kor = Integer.parseInt(txtKor.getText());
            if (kor < 16) {
                ps.playSound("error");  
                Panel.hiba("Hiba!", "A versenyzoknbek legalab 16 evesnek kell lenniuk");
                txtKor.requestFocus();
                return;
            }
            else if (kor > 99) {
                ps.playSound("error");  
                Panel.hiba("Hiba!", "Kicsit idos a jeleolt a profi sportolashoz");
                txtKor.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            ps.playSound("error");  
            Panel.hiba("Hiba!", "A kor nem szám");
            return;
        }
        
        

        if (nev.length() < 1 || nev.length() > 30 || edzo.length() > 30) {
            ps.playSound("error");  
            Panel.hiba("Hiba!", "A név 1-30 karakter hosszú lehet!");
            txtVnev.requestFocus();
            return;
        }


                               
        
        
        int id = tblVersenyzok.getItems().get(index).getId();
         
        
        if (file !=null ) ab.setImageById(file, id);
        
                String sor = ab.versenyzok_modosit(nev, orszag, kor, edzo, 
                        status, comment, id);
        if (sor.isEmpty()) {
            ab.versenyzok_pont_frissit();
            ab.versenyzokBe(tblVersenyzok.getItems());
            for (int i = 0; i < tblVersenyzok.getItems().size(); i++) {
                if (tblVersenyzok.getItems().get(i).getId().equals(id)) {
                    tblVersenyzok.getSelectionModel().select(i);
                    break;
                }
            }
        }
        file=null; 
       tblVersenyzok.requestFocus();    
       ab.kuzdoBe(tblKuzdo.getItems());
       ab.egyeniBe(tblEgyeni.getItems());
       ab.cbxBe(cbxH1.getItems(), cbxH2.getItems(), cbxH3.getItems(), 
                cbxWin.getItems(), cbxLose.getItems());
        ab.orszag_PV_frissit(); 
        ab.orszagBe(tblOrszagok.getItems(), cbxOrszag.getItems());
        


        

    }
    /**
     * A versenyzők módosítható mezőit tiszttja le
     */ 
    @FXML
    void btnVuj() {
        txtVnev.clear();
        txtKor.clear();
        cbxOrszag.setValue(null);   
        txtEdzo.clear();
        cbxStatus.setValue("Aktív");
        txtComment.clear();
        image = new Image("/pfps/unknown.png");
        pfp.setImage(image);
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       oONev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        oOVersenyzo.setCellValueFactory(new PropertyValueFactory<>("versenyzok"));
        oOPontszam.setCellValueFactory(new PropertyValueFactory<>("pont"));
         
        ab.orszagBe(tblOrszagok.getItems(), cbxOrszag.getItems());
        
       tblOrszagok.getSelectionModel().selectedIndexProperty().addListener(
                (o, regi, uj) -> orszagTablabol(uj.intValue()));
       
       cbxStatus.setItems(FXCollections.observableArrayList(
    "Aktív", "Beteg", "Visszavonult", "Diszkvalifikált")
);
       
       oVNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        oVOrszag.setCellValueFactory(new PropertyValueFactory<>("orszag"));
        oVPontszam.setCellValueFactory(new PropertyValueFactory<>("pont"));
         
        ab.versenyzokBe(tblVersenyzok.getItems());
        
        ab.cbxBe(cbxH1.getItems(), cbxH2.getItems(), cbxH3.getItems(), 
                cbxWin.getItems(), cbxLose.getItems());
                 
       tblVersenyzok.getSelectionModel().selectedIndexProperty().addListener(
                (o, regi, uj) -> {
           try {
               versenyzoTablabol(uj.intValue());
           } catch (SQLException ex) {
               Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
           } catch (IOException ex) {
               Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
           }
       });
      oENev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        oEDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        o1H.setCellValueFactory(new PropertyValueFactory<>("I"));
       o2H.setCellValueFactory(new PropertyValueFactory<>("II"));
       o3H.setCellValueFactory(new PropertyValueFactory<>("III"));       
        ab.egyeniBe(tblEgyeni.getItems());
               
       tblEgyeni.getSelectionModel().selectedIndexProperty().addListener(
                (o, regi, uj) -> egyeniTablabol(uj.intValue()));
       oKNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        oKDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        oWin.setCellValueFactory(new PropertyValueFactory<>("win"));
       oLose.setCellValueFactory(new PropertyValueFactory<>("lose"));       
        ab.kuzdoBe(tblKuzdo.getItems());               
       tblKuzdo.getSelectionModel().selectedIndexProperty().addListener(
                (o, regi, uj) -> kuzdoTablabol(uj.intValue()));       
       
       
    }  
/**
 * Betölti a kijelölt ország nevét a módosítható felületre
 *
 */     

     private void orszagTablabol(int i) {
        if (i == -1) {
            return;
        }
        orszagok o= tblOrszagok.getItems().get(i);
        txtOnev.setText(o.getNev());
    }
/**
 * Betölti a kijelölt versenyző adatait a módosítható felületre
 *
 */     

       private void versenyzoTablabol(int i) throws SQLException, IOException{
        if (i == -1) {
            return;
        }
        versenyzok v= tblVersenyzok.getItems().get(i);
        txtVnev.setText(v.getNev());
        txtEdzo.setText(v.getEdzo());
        txtKor.setText("" + v.getKor());
        cbxOrszag.setValue(v.getOrszag());
        cbxStatus.setValue(v.getStatus());
        txtComment.setText(v.getComment());
        int j = tblVersenyzok.getSelectionModel().getSelectedIndex();
        
        int id = tblVersenyzok.getItems().get(j).getId();

       try{
        image = ab.getImageById(id);
        }catch (NullPointerException e){
            image = new Image("/pfps/unknown.png");
        }finally{
        pfp.setImage(image);
        }
    }
/**
 * Betölti a kijelölt egyéni sport adatait a módosítható felületre
 *
 */     
   
       private void egyeniTablabol(int i) {
        if (i == -1) {
            return;
        }
        egyeni e= tblEgyeni.getItems().get(i);
        txtEnev.setText(e.getNev());
        cbxH1.setValue(e.getI());
        cbxH2.setValue(e.getII());
        cbxH3.setValue(e.getIII());
        dpEDatum.setValue(LocalDate.parse(e.getDatum()));
    }
       
/**
 * Betölti a kijelölt küzdősport adatait a módosítható felületre
 *
 */
        
       private void kuzdoTablabol(int i) {
        if (i == -1) {
            return;
        }
        kuzdo k= tblKuzdo.getItems().get(i);
        txtKnev.setText(k.getNev());
        cbxWin.setValue(k.getWin());
        cbxLose.setValue(k.getLose());
        dpKDatum.setValue(LocalDate.parse(k.getDatum()));
    }
       
       
       
/**Megnézi, hogy egy bizonyos adat szerepel-e már az adatbázisban, és ha igen
 * akkor hányszor
 * 
 */  
    
    private int count(String type1, String table, String type2, String name){
        int counted = 0;
        String s = "SELECT count("+ type1 +") as count from " + table + " where "+type2+"='" + name + "';";
        counted = ab.count(s, counted);
        return counted;
        

} 
    
/**
 * Megnézi hogy egy bizonyos országnak mennyi a versenyzői létszáma
 *
 * 
 */    
  
    private int count_letszam(String orszag){
        int counted = 0;
        String s = "SELECT letszam as count from orszag where name='"+ orszag +"'";
        counted = ab.count(s, counted);
        return counted;
        

} 
/**
 * Megkeres egy szükséges String-et
 *
 * 
 */     

    private String find(String name, String table, String condition){
        String result = "";
        String s = ("select " + name + " as result from " + table + " where " + condition) ;
        result = ab.findString(s, result);
        return result;
        

} 

     



    
       
}

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.fxml.Initializable;

public class ControllerDesktop  implements Initializable{
  @FXML
private ChoiceBox<String> choiceBox;

  @FXML
private VBox yPane;

  @FXML
private AnchorPane info;

    String opcions[] = { "Personatges", "Jocs", "Consoles" };

    @Override

    public void initialize(URL url, ResourceBundle rb) {

    // Afegeix les opcions al ChoiceBox
    choiceBox.getItems().addAll(opcions);
    // Selecciona la primera opció
    choiceBox.setValue(opcions[0]);
    // Callback que s’executa quan l’usuari escull una opció
    choiceBox.setOnAction((event) -> { loadList(); });
    // Carregar automàticament les dades de ‘Personatges’
    loadList();
    }
    

    public void loadList() {
    // Obtenir l'opció seleccionada
    String opcio = choiceBox.getValue();
    // Obtenir una referència a AppData que gestiona les dades
    AppData appData = AppData.getInstance();
    // Mostrar el missatge de càrrega
    showLoading();
    // Demanar les dades
    appData.load(opcio, (result) -> {
        if (result == null) {
          System.out.println("ControllerDesktop: Error loading data.");
        } else {
          // Cal afegir el try/catch a la crida de ‘showList’
          try {
            showList();
          } catch (Exception e) {
            System.out.println("ControllerDesktop: Error showing list.");
          }
        }
      });

    }

    public void showList() throws Exception{
      String opcioSeleccionada = choiceBox.getValue();
      AppData appData = AppData.getInstance();
      JSONArray dades = appData.getData(opcioSeleccionada);
      URL resource = this.getClass().getResource("assets/template_list_item.fxml");
      
      // Esborrar la llista actual
      yPane.getChildren().clear();

      for (int i = 0; i < dades.length(); i++) {
        JSONObject consoleObject = dades.getJSONObject(i);
        if (consoleObject.has("nom")) {
          String nom = consoleObject.getString("nom");
          String imatge = "assets/images/" + consoleObject.getString("imatge");
          FXMLLoader loader = new FXMLLoader(resource);
          Parent itemTemplate = loader.load();
          ControllerListItem itemController = loader.getController();
          itemController.setText(nom);
          itemController.setImage(imatge);

        final String type = opcioSeleccionada;
        final int index = i;
        
        itemTemplate.setOnMouseClicked(event -> {
          
          switch (type) {
            case "Consoles": showInfocons(type, index);break;
            case "Jocs":  showInfojocs(type, index); break;
            case "Personatges":  showInfo(type, index);break;
            }
                    
          
          
        });
    
      yPane.getChildren().add(itemTemplate);
      }
      }
}
public void showLoading() {

// Esborrar la llista actual
    yPane.getChildren().clear();

// Afegeix un indicador de progrés com a primer element de la llista
ProgressIndicator progressIndicator = new ProgressIndicator();
yPane.getChildren().add(progressIndicator);
}

void showInfo(String type, int index) {
  AppData appData = AppData.getInstance();
  JSONObject dades = appData.getItemData(type, index);

  URL resource = this.getClass().getResource("assets/layout_info_item.fxml");
  info.getChildren().clear();
  try {
    FXMLLoader loader = new FXMLLoader(resource);
    Parent itemTemplate = loader.load();
    ControllerInfoItem itemController = loader.getController();
    itemController.setImage("assets/images/" + dades.getString("imatge"));
    itemController.setTitle(dades.getString("nom"));
    itemController.setText(dades.getString("color"));
    itemController.setText1(dades.getString("nom_del_videojoc"));
    
    
    // Afegeix la informació a la vista
    info.getChildren().add(itemTemplate);


    AnchorPane.setTopAnchor(itemTemplate, 0.0);
    AnchorPane.setRightAnchor(itemTemplate, 0.0);
    AnchorPane.setBottomAnchor(itemTemplate, 0.0);
    AnchorPane.setLeftAnchor(itemTemplate, 0.0);

    } catch (Exception e) {
      System.out.println("ControllerDesktop: Error showing info.");
      System.out.println(e);
    }
  }

void showInfojocs(String type, int index) {
  AppData appData = AppData.getInstance();
  JSONObject dades = appData.getItemData(type, index);


  URL resource = this.getClass().getResource("assets/layout_info_jocs.fxml");
  info.getChildren().clear();
  try {
    FXMLLoader loader = new FXMLLoader(resource);
    Parent itemTemplate = loader.load();
    ControllerInfojoc itemController = loader.getController();
    itemController.setImage("assets/images/" + dades.getString("imatge"));
    itemController.setTitle(dades.getString("nom"));
    itemController.setText(String.valueOf(dades.getInt("any")));
    itemController.setText1(dades.getString("tipus"));
    itemController.setText11(dades.getString("descripcio")); 
      
    

    // Afegeix la informació a la vista
    info.getChildren().add(itemTemplate);


    AnchorPane.setTopAnchor(itemTemplate, 0.0);
    AnchorPane.setRightAnchor(itemTemplate, 0.0);
    AnchorPane.setBottomAnchor(itemTemplate, 0.0);
    AnchorPane.setLeftAnchor(itemTemplate, 0.0);

    } catch (Exception e) {
      System.out.println("ControllerDesktop: Error showing info.");
      System.out.println(e);
    }
  }
void showInfocons(String type, int index) {
  AppData appData = AppData.getInstance();
  JSONObject dades = appData.getItemData(type, index);
  

  URL resource = this.getClass().getResource("assets/layout_info_cons.fxml");
  info.getChildren().clear();
  try {
    FXMLLoader loader = new FXMLLoader(resource);
    Parent itemTemplate = loader.load();
    ControllerInfocons itemController = loader.getController();
    itemController.setImage("assets/images/" + dades.getString("imatge"));
    itemController.setTitle(dades.getString("nom"));
    itemController.setText(dades.getString("data"));
    itemController.setText1(dades.getString("color"));
    itemController.setText11(dades.getString("procesador")); 
    itemController.setText111(String.valueOf(dades.getInt("venudes"))); 
    

    // Afegeix la informació a la vista
    info.getChildren().add(itemTemplate);


    AnchorPane.setTopAnchor(itemTemplate, 0.0);
    AnchorPane.setRightAnchor(itemTemplate, 0.0);
    AnchorPane.setBottomAnchor(itemTemplate, 0.0);
    AnchorPane.setLeftAnchor(itemTemplate, 0.0);

    } catch (Exception e) {
      System.out.println("ControllerDesktop: Error showing info.");
      System.out.println(e);
    }
  }



}


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class ControllerInfoItem {

  @FXML
  private ImageView img;

  @FXML
  private Label title = new Label();

  @FXML
  private Label text = new Label();
  @FXML
  private Label text1 = new Label();

  public void setImage(String resourceName) {

    // Obté una referència al recurs dins del .jar
    ClassLoader classLoader = getClass().getClassLoader();
    Image image = new Image(classLoader.getResourceAsStream(resourceName));

    // Estableix la imatge a l'ImageView
    img.setImage(image);
  }
  public void setTitle(String text) {

    // Estableix el contingut del Label
    this.title.setText(text);
  }

  public void setText(String text) {

    // Estableix el contingut del Label
    this.text.setTextFill(Color.web(text));
    this.text.setText(text);
  }
  public void setText1(String text) {

    // Estableix el contingut del Label
    this.text1.setText(text);
  }
}



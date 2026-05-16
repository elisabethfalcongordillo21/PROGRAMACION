package panels;

import java.util.List;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;



public class BotPanel extends GridPane {
    
//cargar las variables del fichero . env en el objeto dotenv

private Dotenv dotenv = Dotenv.load();

private ScrollPane scrollChat;
private Text chat;
private TextField prompt;
private Button btnEnviar;

//datos de la llamada a la api
// sacamos del .env la clave api de gemini

private final String API_KEY=dotenv.get("GEMINI_API_KEY");
private final String API_MODEL = "gemini-2.5-flash";

public BotPanel(){

    //espaciamos los elementos 
    this.setVgap(10);
    this.setHgap(10);
    this.setAlignment(Pos.CENTER);
    this.setPadding(new Insets(25,25,25,25));

    //contruir nodos de javafx
    scrollChat = new ScrollPane();
    chat = new Text("Bienvenid@ al chat de la Tienda Kairo, ¿que necesitas?");
    btnEnviar = new Button("Enviar");
    prompt = new TextField();

    // añadir el texto del chat en el ScrollPane

    this.scrollChat.setContent(chat);
    scrollChat.setPrefSize(500, 400);

    this.add(scrollChat, 0, 0, 2, 1);
    this.add(prompt, 0, 1);
    this.add(btnEnviar, 1, 1);


    btnEnviar.setOnAction(e -> { llamarIA();});

}

    private void llamarIA()
    {
        // creamos el objeto que llama a la api de google
        Client client = Client.builder().apiKey(API_KEY).build();

        GenerateContentConfig config = GenerateContentConfig.builder().systemInstruction(
                        Content.builder()
                                .role("system")
                                .parts(List.of(
                                        Part.builder()
                                                .text("Actua como un experto en videojuegos,productos de informática y periféricos")
                                                .build()))
                                .build())// Para dar un rol y instrucciones hay que crear un objeto content que metemos
                                         // a systemInstructions
                .temperature(2.0f) //  locura o creatividad de la ia
                .topP(0.95f) // variedad de respuestas que tiene en cuenta
                .maxOutputTokens(50000) //  limita la cantidad de tokens gastados en la generacion
                .build();

        //llamamos a gemini con una pregunta 
        GenerateContentResponse resp = client.models.generateContent("gemini-2.5-flash",prompt.getText(),config);

        this.chat.setText(this.chat.getText() + "\n" + prompt.getText() + "\n" + resp.text());

        prompt.clear();

    }




















}

package Frontend;

import Backend.RedBlackTree;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DictionaryApp extends Application {

    private final RedBlackTree dictionary = new RedBlackTree();
    private static final String DICTIONARY_FILE = "Dictionary.txt";

    @Override
    public void start(Stage primaryStage) {
        // Load dictionary file into Red-Black Tree
        try {
            dictionary.loadFromFile(DICTIONARY_FILE);
        } catch (Exception e) {
            System.out.println("Error: Failed to load dictionary: " + e.getMessage() + ". The dictionary will be empty.");
        }

        // UI Controls
        Label titleLabel = new Label("Simple Dictionary (Red-Black Tree)");
        titleLabel.getStyleClass().add("label-title");

        TextField wordField = new TextField();
        wordField.setPromptText("Enter a word");
        wordField.getStyleClass().add("text-field");

        Button insertBtn = new Button("Insert Word");
        Button searchBtn = new Button("Look-up Word");
        Button heightBtn = new Button("Tree Height");
        Button blackHeightBtn = new Button("Black Height");
        Button sizeBtn = new Button("Tree Size");
        Button infoBtn = new Button("Show Tree Info");
        insertBtn.getStyleClass().add("button");
        searchBtn.getStyleClass().add("button");
        heightBtn.getStyleClass().add("button");
        blackHeightBtn.getStyleClass().add("button");
        sizeBtn.getStyleClass().add("button");
        infoBtn.getStyleClass().add("button");

        Label statusLabel = new Label();
        statusLabel.getStyleClass().add("label-status");

        // Shape the status label area
        Region statusContainer = new Region();
        statusContainer.getStyleClass().add("status-container");
        statusContainer.setMinHeight(50);
        StackPane statusPane = new StackPane(statusLabel);
        statusPane.setAlignment(Pos.CENTER_LEFT);
        statusContainer.setShape(new Rectangle(580, 50)); // Match container size
        StackPane.setMargin(statusLabel, new Insets(0, 0, 0, 10));

        // Event Handlers
        insertBtn.setOnAction(e -> {
            String word = wordField.getText().trim();
            if (word.isEmpty()) {
                statusLabel.setText("Please enter a word.");
                return;
            }
            if (dictionary.search(word)) {
                statusLabel.setText("ERROR: Word already in the dictionary!");
            } else {
                dictionary.insert(word);
                dictionary.appendToFile(DICTIONARY_FILE, word.toLowerCase());
                statusLabel.setText("Word inserted successfully!");
            }
            wordField.clear();
        });

        searchBtn.setOnAction(e -> {
            String word = wordField.getText().trim();
            if (word.isEmpty()) {
                statusLabel.setText("Please enter a word.");
                return;
            }
            statusLabel.setText(dictionary.search(word)
                    ? "YES: Word found in dictionary."
                    : "NO: Word not found.");
            wordField.clear();
        });

        heightBtn.setOnAction(e ->
                statusLabel.setText("Tree Height: " + dictionary.getTreeHeight()));

        blackHeightBtn.setOnAction(e ->
                statusLabel.setText("Black Height: " + dictionary.getBlackHeight()));

        sizeBtn.setOnAction(e ->
                statusLabel.setText("Tree Size: " + dictionary.getSize()));

        infoBtn.setOnAction(e -> {
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setTitle("Tree Information");

            VBox dialogContent = new VBox(10);
            dialogContent.setPadding(new Insets(20));
            dialogContent.getStyleClass().add("dialog-content");

            Label heightLabel = new Label("Tree Height: " + dictionary.getTreeHeight());
            Label blackHeightLabel = new Label("Black Height: " + dictionary.getBlackHeight());
            Label sizeLabel = new Label("Tree Size: " + dictionary.getSize());
            heightLabel.getStyleClass().add("dialog-label");
            blackHeightLabel.getStyleClass().add("dialog-label");
            sizeLabel.getStyleClass().add("dialog-label");

            Button closeBtn = new Button("Close");
            closeBtn.getStyleClass().add("button");
            closeBtn.setOnAction(event -> dialog.close());

            dialogContent.getChildren().addAll(heightLabel, blackHeightLabel, sizeLabel, closeBtn);
            Scene dialogScene = new Scene(dialogContent, 300, 200);
            dialogScene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
            dialog.setScene(dialogScene);
            dialog.showAndWait();
        });

        // Layout containers
        HBox opsRow1 = new HBox(10, insertBtn, searchBtn, infoBtn);
        HBox opsRow2 = new HBox(10, heightBtn, blackHeightBtn, sizeBtn);
        opsRow1.getStyleClass().add("hbox");
        opsRow2.getStyleClass().add("hbox");

        VBox root = new VBox(15,
                titleLabel,
                wordField,
                opsRow1,
                opsRow2,
                statusPane
        );
        root.setPadding(new Insets(20));
        root.getStyleClass().add("root");

        // Scene setup
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        primaryStage.setTitle("Dictionary App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
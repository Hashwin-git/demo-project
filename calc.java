import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SimpleCalculator extends Application {

    private final DoubleProperty result = new SimpleDoubleProperty();
    private final StringProperty expression = new SimpleStringProperty("");

    private final TextField display = new TextField();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculator");

        // Display setup
        display.setEditable(false);
        display.setStyle("-fx-font-size: 2em;");
        display.textProperty().bind(expression);

        // Calculator buttons
        Button[] numberButtons = new Button[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new Button(String.valueOf(i));
            numberButtons[i].setOnAction(event -> appendToExpression(String.valueOf(i)));
        }

        Button addButton = new Button("+");
        Button subtractButton = new Button("-");
        Button multiplyButton = new Button("*");
        Button divideButton = new Button("/");
        Button equalsButton = new Button("=");
        Button clearButton = new Button("C");

        // Action Handlers
        addButton.setOnAction(event -> appendToExpression("+"));
        subtractButton.setOnAction(event -> appendToExpression("-"));
        multiplyButton.setOnAction(event -> appendToExpression("*"));
        divideButton.setOnAction(event -> appendToExpression("/"));
        equalsButton.setOnAction(event -> calculateResult());
        clearButton.setOnAction(event -> clearExpression());

        // Layout using GridPane
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        // Adding buttons to grid
        grid.add(numberButtons[7], 0, 1);
        grid.add(numberButtons[8], 1, 1);
        grid.add(numberButtons[9], 2, 1);
        grid.add(divideButton, 3, 1);

        grid.add(numberButtons[4], 0, 2);
        grid.add(numberButtons[5], 1, 2);
        grid.add(numberButtons[6], 2, 2);
        grid.add(multiplyButton, 3, 2);

        grid.add(numberButtons[1], 0, 3);
        grid.add(numberButtons[2], 1, 3);
        grid.add(numberButtons[3], 2, 3);
        grid.add(subtractButton, 3, 3);

        grid.add(numberButtons[0], 0, 4);
        grid.add(clearButton, 1, 4);
        grid.add(equalsButton, 2, 4);
        grid.add(addButton, 3, 4);

        // Main layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(display, grid);

        // Scene setup
        Scene scene = new Scene(root, 300, 400);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case DIGIT0: appendToExpression("0"); break;
                case DIGIT1: appendToExpression("1"); break;
                case DIGIT2: appendToExpression("2"); break;
                case DIGIT3: appendToExpression("3"); break;
                case DIGIT4: appendToExpression("4"); break;
                case DIGIT5: appendToExpression("5"); break;
                case DIGIT6: appendToExpression("6"); break;
                case DIGIT7: appendToExpression("7"); break;
                case DIGIT8: appendToExpression("8"); break;
                case DIGIT9: appendToExpression("9"); break;
                case ADD: appendToExpression("+"); break;
                case SUBTRACT: appendToExpression("-"); break;
                case MULTIPLY: appendToExpression("*"); break;
                case DIVIDE: appendToExpression("/"); break;
                case ENTER: calculateResult(); break;
                case ESCAPE: clearExpression(); break;
                default: break;
            }
        });

        // Set stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void appendToExpression(String value) {
        expression.set(expression.get() + value);
    }

    private void clearExpression() {
        expression.set("");
    }

    private void calculateResult() {
        try {
            String expr = expression.get();
            if (!expr.isEmpty()) {
                // Use JavaScript engine to evaluate the expression (simple approach)
                javax.script.ScriptEngine engine = new javax.script.ScriptEngineManager().getEngineByName("JavaScript");
                Object resultObj = engine.eval(expr);
                result.set(Double.parseDouble(resultObj.toString()));
                expression.set(String.valueOf(result.get()));
            }
        } catch (Exception e) {
            expression.set("Error");
        }
    }
}
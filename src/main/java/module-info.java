module org.example.rb_tree {
    requires javafx.controls;
    requires javafx.fxml;


    exports Frontend;
    opens Frontend to javafx.fxml;
}
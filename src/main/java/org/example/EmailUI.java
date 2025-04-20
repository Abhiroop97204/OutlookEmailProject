package org.example;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jakarta.mail.Message;

import java.util.List;
import java.util.Map;

public class EmailUI extends Application {

    public static class EmailGroup {
        private final SimpleStringProperty subject;
        private final SimpleIntegerProperty count;

        public EmailGroup(String subject, int count) {
            this.subject = new SimpleStringProperty(subject);
            this.count = new SimpleIntegerProperty(count);
        }

        public String getSubject() {
            return subject.get();
        }

        public int getCount() {
            return count.get();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Gmail Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField fetchCountField = new TextField();
        fetchCountField.setPromptText("Number of emails to fetch (e.g., 100)");

        Button fetchButton = new Button("Fetch and Group Emails");

        TableView<EmailGroup> tableView = new TableView<>();

        TableColumn<EmailGroup, String> subjectCol = new TableColumn<>("Email Subject");
        subjectCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubject()));
        subjectCol.setMinWidth(250);

        TableColumn<EmailGroup, Number> countCol = new TableColumn<>("Count");
        countCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()));
        countCol.setMinWidth(100);

        tableView.getColumns().addAll(subjectCol, countCol);

        fetchButton.setOnAction(e -> {
            try {
                int fetchCount = Integer.parseInt(fetchCountField.getText().trim());
                if (fetchCount <= 0) {
                    throw new NumberFormatException("Fetch count must be positive");
                }

                EmailReader reader = new EmailReader(usernameField.getText(), passwordField.getText(), fetchCount);
                List<Message> emails = reader.fetchEmails();
                EmailGrouper grouper = new EmailGrouper();
                Map<String, List<Message>> grouped = grouper.groupEmails(emails);

                ObservableList<EmailGroup> data = FXCollections.observableArrayList();
                for (String key : grouped.keySet()) {
                    data.add(new EmailGroup(key, grouped.get(key).size()));
                }
                tableView.setItems(data);

            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid number for emails to fetch!");
                alert.showAndWait();
            }
        });

        VBox layout = new VBox(10, usernameField, passwordField, fetchCountField, fetchButton, tableView);
        layout.setPrefSize(450, 500);
        Scene scene = new Scene(layout);

        primaryStage.setTitle("Gmail Email Grouper 📬");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

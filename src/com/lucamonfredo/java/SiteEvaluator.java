package com.lucamonfredo.java;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.concurrent.Worker.State;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn.*;
import javafx.util.*;




public class SiteEvaluator extends Application {


  private double maxSpeed = 0;
  private final int ALEXA_SITES = 30000000;

  // the table
  private TableView<Website> table = new TableView<Website>();

  // the table's data
  private final ObservableList<Website> tableData = FXCollections.observableArrayList();

  // input's container
  final HBox hb = new HBox();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    Scene scene = new Scene(new Group());
    stage.setTitle("Site Evaluator");
    stage.setWidth(1040);
    stage.setHeight(610);

    final Label siteEvaluatorLabel = new Label("Evaluation results");
    siteEvaluatorLabel.setFont(new Font("Arial", 16));

    final Label addSiteLabel = new Label("Add website");
    addSiteLabel.setFont(new Font("Arial", 16));

    table.setEditable(false);

    // site URL column
    TableColumn siteURLCol = new TableColumn("Site URL");
    siteURLCol.setMinWidth(325);
    siteURLCol.setCellValueFactory(
      new PropertyValueFactory<Website, String>("siteURL")
    );
    siteURLCol.setCellFactory(TextFieldTableCell.forTableColumn());

    // Alexa Rank column
    TableColumn alexaRankCol = new TableColumn("Alexa Rank");
    alexaRankCol.setMinWidth(85);
    alexaRankCol.setCellValueFactory(
      new PropertyValueFactory<Website, Number>("alexaRank")
    );
    alexaRankCol.setCellFactory(column -> {
      return new TableCell<Website, Number>() {
        @Override
        protected void updateItem(Number item, boolean empty) {
          super.updateItem(item, empty);
          if (item == null || empty) {
            setText("");
          } else {
            if ((int) item == ALEXA_SITES) {
              setText("- no rank -");
            } else {
              setText(String.format("%d", item));
            }
          }
        }
      };
    });

    // visibility column
    TableColumn visibilityCol = new TableColumn("Visibility");
    visibilityCol.setMinWidth(85);
    visibilityCol.setCellValueFactory(
      new PropertyValueFactory<Website, Number>("visibility")
    );
    visibilityCol.setCellFactory(column -> {
      return new TableCell<Website, Number>() {
        @Override
        protected void updateItem(Number item, boolean empty) {
          super.updateItem(item, empty);
          if (item == null || empty) {
            setText("");
          } else {
            setText(String.format("%.3f", item));
          }
        }
      };
    });

    // transferRateKbs column
    TableColumn transferRateKbsCol = new TableColumn("XFER Kb/s");
    transferRateKbsCol.setMinWidth(85);
    transferRateKbsCol.setCellValueFactory(
      new PropertyValueFactory<Website, Number>("transferRateKbs")
    );
    transferRateKbsCol.setCellFactory(column -> {
      return new TableCell<Website, Number>() {
        @Override
        protected void updateItem(Number item, boolean empty) {
          super.updateItem(item, empty);
          if (item == null || empty) {
            setText("");
          } else {
            setText(String.format("%.3f", item));
          }
        }
      };
    });

    // speed column
    TableColumn speedCol = new TableColumn("Speed");
    speedCol.setMinWidth(85);
    speedCol.setCellValueFactory(
      new PropertyValueFactory<Website, Number>("speed")
    );
    speedCol.setCellFactory(column -> {
      return new TableCell<Website, Number>() {
        @Override
        protected void updateItem(Number item, boolean empty) {
          super.updateItem(item, empty);
          if (item == null || empty) {
            setText("");
          } else {
            setText(String.format("%.3f", item));
          }
        }
      };
    });

    // navigability column
    TableColumn navigabilityCol = new TableColumn("Navigability");
    navigabilityCol.setMinWidth(85);
    navigabilityCol.setCellValueFactory(
      new PropertyValueFactory<Website, Number>("navigability")
    );
    navigabilityCol.setCellFactory(column -> {
      return new TableCell<Website, Number>() {
        @Override
        protected void updateItem(Number item, boolean empty) {
          super.updateItem(item, empty);
          if (item == null || empty) {
            setText("");
          } else {
            setText(String.format("%.3f", item));
          }
        }
      };
    });

    // content column
    TableColumn contentCol = new TableColumn("Content");
    contentCol.setMinWidth(85);
    contentCol.setCellValueFactory(
      new PropertyValueFactory<Website, Number>("content")
    );
    contentCol.setCellFactory(column -> {
      return new TableCell<Website, Number>() {
        @Override
        protected void updateItem(Number item, boolean empty) {
          super.updateItem(item, empty);
          if (item == null || empty) {
            setText("");
          } else {
            setText(String.format("%.3f", item));
          }
        }
      };
    });

    // score column
    TableColumn scoreCol = new TableColumn("WAI");
    scoreCol.setMinWidth(85);
    scoreCol.setCellValueFactory(
      new PropertyValueFactory<Website, Number>("score")
    );
    scoreCol.setCellFactory(column -> {
      return new TableCell<Website, Number>() {
        @Override
        protected void updateItem(Number item, boolean empty) {
          super.updateItem(item, empty);
          if (item == null || empty) {
            setText("");
            // setStyle("");
          } else {
            setText(String.format("%.3f", item));
          }
        }
      };
    });

    // "add site" functionality
    final TextField addSiteURL = new TextField();
    addSiteURL.setPromptText("Site URL");
    addSiteURL.setMinWidth(486);
    addSiteURL.setMaxWidth(siteURLCol.getPrefWidth());
    final TextField addNavigability = new TextField();
    addNavigability.setMinWidth(135);
    addNavigability.setMaxWidth(navigabilityCol.getPrefWidth());
    addNavigability.setPromptText("Navigability [0.0 - 1.0]");
    addNavigability.focusedProperty().addListener((arg0, oldValue, newValue) -> {
      if (!newValue) { // when focus lost
        if (!addNavigability.getText().matches("[0](\\.[0-9]{1,2}){0,1}|1(\\.0{1,2}){0,1}")) {
          // when it not matches the pattern (0.00 - 1.00)
          // set the textField to 0
          addNavigability.setText("0");
        }
      }
    });
    final TextField addContent = new TextField();
    addContent.setMinWidth(135);
    addContent.setMaxWidth(contentCol.getPrefWidth());
    addContent.setPromptText("Content [0.0 - 1.0]");
    addContent.focusedProperty().addListener((arg0, oldValue, newValue) -> {
      if (!newValue) { // when focus lost
        if (!addContent.getText().matches("[0](\\.[0-9]{1,2}){0,1}|1(\\.0{1,2}){0,1}")) {
          // when it not matches the pattern (0.00 - 1.00)
          // set the textField to 0
          addContent.setText("0");
        }
      }
    });
    final Button addButton = new Button("Add Website");
    addButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        // protocol check
        String url = addSiteURL.getText();
        if (!url.toLowerCase().matches("^\\w+://.*")) {
          url = "http://" + url;
        }
        tableData.add(
          new Website(
            url,
            0,
            0,
            0,
            0,
            Double.parseDouble(addNavigability.getText()),
            Double.parseDouble(addContent.getText()),
            0
          )
        );
        addSiteURL.clear();
        addNavigability.clear();
        addContent.clear();

        addButton.setDisable(true);
        maxSpeed = 0;
        evaluateWebsites(tableData);
        addButton.setDisable(false);
      }
    });
    final Button clearButton = new Button("Clear list");
    clearButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        tableData.clear();
        addSiteURL.clear();
        addNavigability.clear();
        addContent.clear();
        maxSpeed = 0;
      }
    });

    // delete site
    TableColumn deleteCol = new TableColumn("");
    deleteCol.setMinWidth(85);
    deleteCol.setStyle( "-fx-alignment: CENTER;");
    deleteCol.setCellValueFactory(
      new PropertyValueFactory<>("DUMMY")
    );
    Callback<TableColumn<Website, String>, TableCell<Website, String>> cellFactory = //
      new Callback<TableColumn<Website, String>, TableCell<Website, String>>() {
        @Override
        public TableCell call(final TableColumn<Website, String> param) {
          final TableCell<Website, String> cell = new TableCell<Website, String>() {
            final Button btn = new Button("Remove");
            @Override
            public void updateItem(String item, boolean empty) {
              super.updateItem(item, empty);
              if (empty) {
                setGraphic(null);
                setText(null);
              } else {
                btn.setStyle("-fx-font: 10 arial;");
                btn.setOnAction((ActionEvent event) -> {
                  btn.setDisable(true);
                  Website website = getTableView().getItems().get(getIndex());
                  addButton.setDisable(true);
                  tableData.remove(getIndex());
                  maxSpeed = 0;
                  evaluateWebsites(tableData);
                  addButton.setDisable(false);
                  btn.setDisable(false);
                });
                setGraphic(btn);
                setText(null);
              }
            }
          };
        return cell;
        }
      };
    deleteCol.setCellFactory(cellFactory);

    // bind data to the table
    table.setItems(tableData);
    table.getColumns().addAll(
      siteURLCol,
      alexaRankCol,
      visibilityCol,
      transferRateKbsCol,
      speedCol,
      navigabilityCol,
      contentCol,
      scoreCol,
      deleteCol
    );

    // Layout
    hb.getChildren().addAll(addSiteURL, addNavigability, addContent, addButton, clearButton);
    hb.setSpacing(10);
    hb.setPadding(new Insets(20, 20, 20, 20));
    hb.setStyle("-fx-background-color: #f2f2f2;");

    final VBox vbox = new VBox();
    vbox.setSpacing(10);
    vbox.setPadding(new Insets(20, 0, 0, 20));
    vbox.getChildren().addAll(siteEvaluatorLabel, table, addSiteLabel, hb);

    ((Group) scene.getRoot()).getChildren().addAll(vbox);

    stage.setScene(scene);
    stage.show();
  }


  private double evaluateVisibility(Website website) {
    int alexaRank = Alexa.getAlexaRanking(website.getSiteURL());
    if (alexaRank == 0) {
      alexaRank = ALEXA_SITES;
    }
    website.setAlexaRank(alexaRank);
    double visibility = (ALEXA_SITES - ((double) alexaRank) + 1) / ALEXA_SITES;
    return visibility;
  }


  private double evaluateTransferRateKbs(Website website) {
    try {
        String il;
        URL url = new URL(website.getSiteURL());
        long tStart = System.nanoTime();
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        // HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        int bytes = 0;
        while ((il = br.readLine()) != null) {
          bytes += il.getBytes().length;
        }
        br.close();
        long tEnd = System.nanoTime();
        double tDelta = (double)(tEnd - tStart) / 1.0E9;
        double speed = ((double) bytes / 1024) / tDelta;

        if (maxSpeed < speed) {
          maxSpeed = speed;
        }

        return speed;
    }
    catch (Exception ex) {
        return 0;
    }
  }


  private double evaluateSpeed(Website website) {
    return website.getTransferRateKbs() / maxSpeed;
  }


  private void evaluateWebsites(ObservableList<Website> websites) {

    websites.forEach((website) -> {
      website.setVisibility(evaluateVisibility(website));
      website.setTransferRateKbs(evaluateTransferRateKbs(website));
    });

    websites.forEach((website) -> {
      website.setSpeed(evaluateSpeed(website));
    });

    websites.forEach((website) -> {
      double score = 0.3 * website.getVisibility() + 0.1 * website.getSpeed() + 0.1 * website.getNavigability() + 0.5 * website.getContent();
      website.setScore(Math.floor(score * 1000) / 1000);
    });

  }


}

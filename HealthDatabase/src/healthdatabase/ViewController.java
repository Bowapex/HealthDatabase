
package healthdatabase;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;


public class ViewController implements Initializable {
    
    //<editor-fold defaultstate="collapsed" desc="@FXML annotációk">
    @FXML
            SplitPane mainSplit;
    @FXML
            StackPane menuPane;
    @FXML
            Pane dataInputPane;
    @FXML
            Pane exportPane;
    @FXML
            Pane tablePane;
    @FXML
            Pane statPane;
    @FXML
            Pane alertPane;
    @FXML
            TableView table;
    @FXML
            DatePicker inputDate;
    @FXML
            TextField inputTime;
    @FXML
            TextField inputGlucose;
    @FXML
            ChoiceBox mealChoice;
    @FXML
            TextField inputBloodPress;
    @FXML
            TextField inputPulse;
    @FXML
            TextField inputWeight;
    @FXML
            TextField inputPdf;
    @FXML
            Label alertLabel;
    @FXML
            TextArea inputNote;
    @FXML
            Button addNewRecord;
    @FXML
            Button exportButton;
    @FXML
            Button alertButton;
    @FXML
            LineChart glucChart;
    @FXML
            LineChart weightChart;
    
    
//</editor-fold>
 
   DB db = new DB();
   
   private final String MENU_TABLAZAT = "Táblázat";
   private final String MENU_EXIT = "Kilépés";
   private final String MENU_ADATINPUT = "Adatbevitel";
   private final String MENU_EXPORT = "PDF-export";
   private final String MENU_ADATOK = "Adatok";
   private final String MENU_STAT = "Statisztika";
   
   
   
   private final ObservableList<Records> data = FXCollections.observableArrayList();
   
   
    public void adRecord(ActionEvent event) {

        try {
            Double glucose = Double.parseDouble(inputGlucose.getText());
            Double pulse = Double.parseDouble(inputPulse.getText());
            Double weight = Double.parseDouble(inputWeight.getText());

            if (inputBloodPress.getText().contains("/") || inputBloodPress.getText().equals("")) {
                Records record = new Records(inputDate.getValue().toString(), inputTime.getText(), glucose.toString(),
                        mealChoice.getValue().toString(), inputBloodPress.getText(), pulse.toString(), weight.toString());

                data.add(record);
                db.addRecord(record);

                inputTime.clear();
                inputBloodPress.clear();
                inputPulse.clear();
                inputWeight.clear();
                inputGlucose.clear();
            } else {
                alert("A vérnyomás értéknél használj / jelet!");
            }
        } catch (Exception e) {
            alert("Számot kell megadni!");
        }

    }
   
    public void pdfCreate(ActionEvent event) {
        String fileName = inputPdf.getText();
        fileName = fileName.replaceAll("\\s+", "");
        if (fileName != null && !fileName.equals("")) {
            PDFGeneretion pdfCreate = new PDFGeneretion();
            pdfCreate.pdfGeneration(fileName, data);
        } else {
            alert("Adj meg egy fájlnevet");
        }
    }
   
    private void getTableData() {

        TableColumn dateColumn = new TableColumn("Dátum");
        dateColumn.setMinWidth(50);
        dateColumn.setStyle("-fx-alignment: CENTER;");
        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateColumn.setCellValueFactory(new PropertyValueFactory<Records, String>("date"));

        dateColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Records, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Records, String> t) {
                        Records actualRecord = (Records) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualRecord.setDate(t.getNewValue());
                        db.updateRecord(actualRecord);
                    }
                }
        );

        TableColumn timeColumn = new TableColumn("Idöpont");
        timeColumn.setMinWidth(50);
        timeColumn.setStyle("-fx-alignment: CENTER;");
        timeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        timeColumn.setCellValueFactory(new PropertyValueFactory<Records, String>("time"));

        timeColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Records, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Records, String> t) {
                        Records actualRecord = (Records) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualRecord.setTime(t.getNewValue());
                        db.updateRecord(actualRecord);
                    }
                }
        );

        TableColumn glucoseColumn = new TableColumn("Vércukor");
        glucoseColumn.setMinWidth(20);
        glucoseColumn.setStyle("-fx-alignment: CENTER;");
        glucoseColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        glucoseColumn.setCellValueFactory(new PropertyValueFactory<Records, String>("glucose"));

        glucoseColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Records, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Records, String> t) {
                        Records actualRecord = (Records) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualRecord.setGlucose(t.getNewValue());
                        db.updateRecord(actualRecord);
                    }
                }
        );

        TableColumn mealTypeColumn = new TableColumn("Étkezés");
        mealTypeColumn.setMinWidth(100);
        mealTypeColumn.setStyle("-fx-alignment: CENTER;");
        mealTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        mealTypeColumn.setCellValueFactory(new PropertyValueFactory<Records, String>("mealType"));

        mealTypeColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Records, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Records, String> t) {
                        Records actualRecord = (Records) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualRecord.setMealType(t.getNewValue());
                        db.updateRecord(actualRecord);
                    }
                }
        );

        TableColumn bloodPressColumn = new TableColumn("Vérnyomás");
        bloodPressColumn.setMinWidth(50);
        bloodPressColumn.setStyle("-fx-alignment: CENTER;");
        bloodPressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        bloodPressColumn.setCellValueFactory(new PropertyValueFactory<Records, String>("bloodPress"));

        bloodPressColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Records, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Records, String> t) {
                        Records actualRecord = (Records) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualRecord.setBloodPress(t.getNewValue());
                        db.updateRecord(actualRecord);
                    }
                }
        );

        TableColumn pulseColumn = new TableColumn("Pulzus");
        pulseColumn.setMinWidth(20);
        pulseColumn.setStyle("-fx-alignment: CENTER;");
        pulseColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        pulseColumn.setCellValueFactory(new PropertyValueFactory<Records, String>("pulse"));

        pulseColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Records, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Records, String> t) {
                        Records actualRecord = (Records) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualRecord.setPulse(t.getNewValue());
                        db.updateRecord(actualRecord);
                    }
                }
        );

        TableColumn weightColumn = new TableColumn("Testsúly");
        weightColumn.setMinWidth(20);
        weightColumn.setStyle("-fx-alignment: CENTER;");
        weightColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        weightColumn.setCellValueFactory(new PropertyValueFactory<Records, String>("weight"));

        weightColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Records, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Records, String> t) {
                        Records actualRecord = (Records) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualRecord.setWeight(t.getNewValue());
                        db.updateRecord(actualRecord);
                    }
                }
        );

        TableColumn removeCol = new TableColumn("Törlés");

        Callback<TableColumn<Records, String>, TableCell<Records, String>> cellFactory
                = new Callback<TableColumn<Records, String>, TableCell<Records, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Records, String> param) {
                        final TableCell<Records, String> cell = new TableCell<Records, String>() {
                            final Button btn = new Button("Törlés");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction((ActionEvent event) -> {
                                        Records record = getTableView().getItems().get(getIndex());
                                        data.remove(record);
                                        db.removeRecords(record);
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        removeCol.setCellFactory(cellFactory);

        table.getColumns().addAll(dateColumn, timeColumn, glucoseColumn, mealTypeColumn, bloodPressColumn,
                pulseColumn, weightColumn, removeCol);
        data.addAll(db.getAllRecords());
        table.setItems(data);

    }
    
    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem("Menü");
        TreeView<String> treeView = new TreeView(treeItemRoot1);
        treeView.setShowRoot(false);

        TreeItem nodeItemA = new TreeItem(MENU_TABLAZAT);
        TreeItem nodeItemB = new TreeItem(MENU_EXIT);

        TreeItem nodeItemA1 = new TreeItem(MENU_ADATOK);
        TreeItem nodeItemA2 = new TreeItem(MENU_ADATINPUT);
        TreeItem nodeItemA3 = new TreeItem(MENU_EXPORT);
        TreeItem nodeItemA4 = new TreeItem(MENU_STAT);

        treeItemRoot1.setExpanded(true);
        nodeItemA.setExpanded(true);

        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2, nodeItemA3, nodeItemA4);
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemB);

        menuPane.getChildren().add(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();

                if (null != selectedMenu) {
                    switch (selectedMenu) {
                        case MENU_TABLAZAT:
                            selectedItem.setExpanded(true);
                            break;
                        case MENU_ADATINPUT:
                            dataInputPane.setVisible(true);
                            exportPane.setVisible(false);
                            tablePane.setVisible(false);
                            statPane.setVisible(false);
                            mealChoice.setItems(FXCollections.observableArrayList(
                                    "", new Separator(), "Reggeli elött", "Reggeli után", "Ebéd elött", "Ebéd után",
                                    "Vacsora elött", "Vacsora után"));
                            mealChoice.setValue("");
                            initClock();

                            break;
                        case MENU_ADATOK:
                            dataInputPane.setVisible(false);
                            exportPane.setVisible(false);
                            tablePane.setVisible(true);
                            statPane.setVisible(false);

                            break;
                        case MENU_EXPORT:
                            tablePane.setVisible(false);
                            dataInputPane.setVisible(false);
                            exportPane.setVisible(true);
                            statPane.setVisible(false);
                            break;
                        case MENU_STAT:
                            tablePane.setVisible(false);
                            dataInputPane.setVisible(false);
                            exportPane.setVisible(false);
                            statPane.setVisible(true);
                            glucChartSet();
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }
                }

            }
        });

    }
    
    
    private void setMenuData2() {
        Menu rootMenu1 = new Menu("Menü");

        rootMenu1.getItems().add(new MenuItem("Adatok"));
        rootMenu1.getItems().add(new MenuItem("Adatbevitel"));
        rootMenu1.getItems().add(new MenuItem("PDF-export"));
        rootMenu1.getItems().add(new MenuItem("Kilépés"));

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(rootMenu1);
        tablePane.getChildren().add(menuBar);
    }
    
    private void alert(String text) {
        alertPane.setVisible(true);
        mainSplit.setOpacity(0.4);
        mainSplit.setDisable(true);
        alertLabel.setText(text);
        VBox vbox = new VBox(alertLabel, alertButton);
        vbox.setAlignment(Pos.CENTER);

        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                alertPane.setVisible(false);
            }
        });
        alertPane.getChildren().addAll(alertLabel, alertButton);

    }
    
    private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
            inputTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    
    private void glucChartSet() {

        glucChart.getData().clear();
        weightChart.getData().clear();

        XYChart.Series<String, Number> glucSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> weightSeries = new XYChart.Series<>();

        ResultSet rs = db.chartResult();

        try {
            while (rs.next()) {
                glucSeries.getData().add(new XYChart.Data<>(rs.getString(2), Double.parseDouble(rs.getString(4))));
                weightSeries.getData().add(new XYChart.Data<>(rs.getString(2), Double.parseDouble(rs.getString(8))));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        glucChart.getData().add(glucSeries);
        weightChart.getData().add(weightSeries);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        getTableData();
        setMenuData();
//        setMenuData2();
        
    }    

    

    

    

    

    

    
    
}

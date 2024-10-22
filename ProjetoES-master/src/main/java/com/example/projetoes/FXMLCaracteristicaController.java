package com.example.projetoes;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.opencsv.CSVReader;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.JOptionPane;
public class FXMLCaracteristicaController implements Initializable{
    @FXML
    public TableColumn edificio;
    @FXML
    public TableColumn nomeSala;
    @FXML
    public TableColumn capacidadeNormal;
    @FXML
    public TableColumn capacidadeExame;
    @FXML
    public Button addFile_salas;
    @FXML
    public TextField texto_pesquisa_salas;
    @FXML
    public Button btn_pesquisa_salas;
    @FXML
    public TableView <Caracteristica> caracteristicas;
    @FXML
    private TableView<Horario> horario;
    @FXML
    private TableColumn<Horario, String> caracteristicasSala;
    @FXML
    private TableColumn<Horario, String> curso;
    @FXML
    private TableColumn<Horario, String> dataAula;
    @FXML
    private TableColumn<Horario, String> diaSemana;
    @FXML
    private TableColumn<Horario, String> horaFimAula;
    @FXML
    private TableColumn<Horario, String> horaInicioAula;
    @FXML
    private TableColumn<Horario, Integer> inscritosTurma;
    @FXML
    private TableColumn<Horario, String> salaAtribuida;
    @FXML
    private TableColumn<Horario, String> turma;
    @FXML
    private TableColumn<Horario, String> turno;
    @FXML
    private TableColumn<Horario, String> uc;
    @FXML
    public TextField texto_pesquisa;
    @FXML
    private Label focusG;
    @FXML
    private Label anfiteatro;
    @FXML
    private Label apoiarTec;
    @FXML
    private Label arq1;
    @FXML
    private Label arq2;
    @FXML
    private Label arq3;
    @FXML
    private Label arq4;
    @FXML
    private Label arq5;
    @FXML
    private Label arq6;
    @FXML
    private Label arq9;
    @FXML
    private Label byod;
    @FXML
    private Label horVisPub;
    @FXML
    private Label labArqComp1;
    @FXML
    private Label labArqComp2;
    @FXML
    private Label labBasesEng;
    @FXML
    private Label labEletro;
    @FXML
    private Label labInfo;
    @FXML
    private Label labJornal;
    @FXML
    private Label labRedesComp1;
    @FXML
    private Label labRedesComp2;
    @FXML
    private Label labTelecom;
    @FXML
    private Label salaMestra;
    @FXML
    private Label salaMestraPlus;
    @FXML
    private Label salasNEE;
    @FXML
    private Label salaProvas;
    @FXML
    private Label salaReuniao;
    @FXML
    private Label salaArq;
    @FXML
    private Label salaNormal;
    @FXML
    private Label videoconf;
    @FXML
    private Label atrio;
    @FXML
    private Label infoSala;

    ObservableList<Caracteristica> listM;
    ObservableList<Caracteristica> listC;
    ObservableList<Horario> dataList;

    private int index = -1;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void getSelected(MouseEvent mouseEvent) throws IOException {
        index=caracteristicas.getSelectionModel().getSelectedIndex();
        String focus=caracteristicas.getSelectionModel().getSelectedItem().getFocusGroup();
        Caracteristica item = caracteristicas.getSelectionModel().getSelectedItem();
        //caracteristicas.getSelectionModel().getFocusedIndex();
        if (index >= 0){
            System.out.println(nomeSala.getCellData(index));
            System.out.println(nomeSala.getId());
            System.out.println(focus);
            UpdateTableInfo(item);
        }else{
            return;
        }
    }

    public void UpdateTableInfo(Caracteristica sala) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("informacoesSalas.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setScene(scene);

        String nome=sala.getNomeSala();
        Label infoSala = (Label) scene.lookup("#infoSala");
        infoSala.setText("Informações da sala " + nome);

        Label focusG = (Label) scene.lookup("#focusG");
        Label anfiteatro = (Label) scene.lookup("#anfiteatro");
        Label apoiarTec = (Label) scene.lookup("#apoioTec");
        Label arq1 = (Label) scene.lookup("#arq1");
        Label arq2 = (Label) scene.lookup("#arq2");
        Label arq3 = (Label) scene.lookup("#arq3");
        Label arq4 = (Label) scene.lookup("#arq4");
        Label arq5 = (Label) scene.lookup("#arq5");
        Label arq6 = (Label) scene.lookup("#arq6");
        Label arq9 = (Label) scene.lookup("#arq9");
        Label byod = (Label) scene.lookup("#byod");
        Label horVisPub = (Label) scene.lookup("#horVisPub");
        Label labArqComp1 = (Label) scene.lookup("#labArqComp1");
        Label labArqComp2 = (Label) scene.lookup("#labArqComp2");
        Label labBasesEng = (Label) scene.lookup("#labBasesEng");
        Label labEletro = (Label) scene.lookup("#labEletro");
        Label labInfo = (Label) scene.lookup("#labInfo");
        Label labJornal = (Label) scene.lookup("#labJornal");
        Label labRedesComp1 = (Label) scene.lookup("#labRedesComp1");
        Label labRedesComp2 = (Label) scene.lookup("#labRedesComp2");
        Label labTelecom = (Label) scene.lookup("#labTelecom");
        Label salaMestra = (Label) scene.lookup("#salaMestra");
        Label salaMestraPlus = (Label) scene.lookup("#salaMestraPlus");
        Label salasNEE = (Label) scene.lookup("#salasNEE");
        Label salaProvas = (Label) scene.lookup("#salaProvas");
        Label salaReuniao = (Label) scene.lookup("#salaReuniao");
        Label salaArq = (Label) scene.lookup("#salaArq");
        Label salaNormal = (Label) scene.lookup("#salaNormal");
        Label videoconf = (Label) scene.lookup("#videoconf");
        Label atrio = (Label) scene.lookup("#atrio");

        if (sala.getFocusGroup() != null && !sala.getFocusGroup().isBlank()) {
            System.out.println("FocusGroup: Tem");
            focusG.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            focusG.setDisable(true);
        }

        if (sala.getAnfiteatroAulas() != null && !sala.getAnfiteatroAulas().isBlank()) {
            System.out.println("Anfiteatro: Tem");
            anfiteatro.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            anfiteatro.setDisable(true);
        }

        if (sala.getApoioTecnicoEventos() != null && !sala.getApoioTecnicoEventos().isBlank()) {
            System.out.println("Apoio técnico eventos: Tem");
            apoiarTec.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            apoiarTec.setDisable(true);
        }

        if (sala.getArq1() != null && !sala.getArq1().isBlank()) {
            System.out.println("Arq 1: Tem");
            arq1.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            arq1.setDisable(true);
        }

        if (sala.getArq2() != null && !sala.getArq2().isBlank()) {
            System.out.println("Arq 2: Tem");
            arq2.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            arq2.setDisable(true);
        }

        if (sala.getArq3() != null && !sala.getArq3().isBlank()) {
            System.out.println("Arq 3: Tem");
            arq3.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            arq3.setDisable(true);
        }

        if (sala.getArq4() != null && !sala.getArq4().isBlank()) {
            System.out.println("Arq 4: Tem");
            arq4.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            arq4.setDisable(true);
        }

        if (sala.getArq5() != null && !sala.getArq5().isBlank()) {
            System.out.println("Arq 5: Tem");
            arq5.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            arq5.setDisable(true);
        }

        if (sala.getArq6() != null && !sala.getArq6().isBlank()) {
            System.out.println("Arq 6: Tem");
            arq6.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            arq6.setDisable(true);
        }

        if (sala.getArq9() != null && !sala.getArq9().isBlank()) {
            System.out.println("Arq 9: Tem");
            arq9.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            arq9.setDisable(true);
        }

        if (sala.getBYOD() != null && !sala.getBYOD().isBlank()) {
            System.out.println("BYOD: Tem");
            byod.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            byod.setDisable(true);
        }

        if (sala.getHorarioVisivelPublico() != null && !sala.getHorarioVisivelPublico().isBlank()) {
            System.out.println("Horário visitação pública: Tem");
            horVisPub.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            horVisPub.setDisable(true);
        }


        if (sala.getLabArqCompI() != null && !sala.getLabArqCompI().isBlank()) {
            System.out.println("Lab. Arq. Comp. 1: Tem");
            labArqComp1.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            labArqComp1.setDisable(true);
        }

        if (sala.getLabArqCompII() != null && !sala.getLabArqCompII().isBlank()) {
            System.out.println("Lab. Arq. Comp. 2: Tem");
            labArqComp2.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            labArqComp2.setDisable(true);
        }

        if (sala.getLabBasesEng() != null && !sala.getLabBasesEng().isBlank()) {
            System.out.println("Lab. Bases Eng.: Tem");
            labBasesEng.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            labBasesEng.setDisable(true);
        }


        if (sala.getLabEletronica() != null &&!sala.getLabEletronica().isBlank()) {
            System.out.println("Lab. Eletro: Tem");
            labEletro.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        }else{
            labEletro.setDisable(true);
        }

        if (sala.getLabInformatica() != null &&!sala.getLabInformatica().isBlank()) {
            System.out.println("Lab. Info: Tem");
            labInfo.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        }else{
            labInfo.setDisable(true);
        }

        if (sala.getLabJornalismo() != null &&!sala.getLabJornalismo().isBlank()) {
            System.out.println("Lab. Jornal: Tem");
            labJornal.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        }else{
            labJornal.setDisable(true);
        }

        if (sala.getLabRedesCompl() != null &&!sala.getLabRedesCompl().isBlank()) {
            System.out.println("Lab. Redes Comp. 1: Tem");
            labRedesComp1.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        }else{
            labRedesComp1.setDisable(true);
        }

        if (sala.getLabRedesCompII() != null &&!sala.getLabRedesCompII().isBlank()) {
            System.out.println("Lab. Redes Comp. 2: Tem");
            labRedesComp2.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        }else{
            labRedesComp2.setDisable(true);
        }

        if (sala.getLabTelecom() != null &&!sala.getLabTelecom().isBlank()) {
            System.out.println("Lab. Telecom: Tem");
            labTelecom.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        }else{
            labTelecom.setDisable(true);
        }

        if (sala.getSalaAulasMest() != null &&!sala.getSalaAulasMest().isBlank()) {
            System.out.println("Sala Mestra: Tem");
            salaMestra.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        }else{
            salaMestra.setDisable(true);
        }

        if (sala.getSalaAulasMestPlus() != null &&!sala.getSalaAulasMestPlus().isBlank()) {
            System.out.println("Sala Mestra Plus: Tem");
            salaMestraPlus.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        }else{
            salaMestraPlus.setDisable(true);
        }

        if (sala.getSalaNEE() != null &&!sala.getSalaNEE().isBlank()) {
            System.out.println("Salas NEE: Tem");
            salasNEE.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        }else{
            salasNEE.setDisable(true);
        }

        if (sala.getSalaProvas() != null &&!sala.getSalaProvas().isBlank()) {
            System.out.println("Sala de Provas: Tem");
            salaProvas.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        }else{
            salaProvas.setDisable(true);
        }

        if (sala.getSalaReuniao() != null &&!sala.getSalaReuniao().isBlank()) {
            System.out.println("Sala de Reunião: Tem");
            salaReuniao.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        }else{
            salaReuniao.setDisable(true);
        }

        if (sala.getSalaArquitetura() != null &&!sala.getSalaArquitetura().isBlank()) {
            System.out.println("Sala de Arquitetura: Tem");
            salaArq.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        }else{
            salaArq.setDisable(true);
        }

        if (sala.getSalaAulasNormal() != null &&!sala.getSalaAulasNormal().isBlank()) {
            System.out.println("Sala Normal: Tem");
            salaNormal.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        }else{
            salaNormal.setDisable(true);
        }

        if (sala.getVideoconferencia() != null && !sala.getVideoconferencia().isBlank()) {
            System.out.println("Videoconferencia: tem2");
            videoconf.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            videoconf.setDisable(true);
        }

        if (sala.getAtrio() != null && !sala.getAtrio().isBlank()) {
            System.out.println("Atrio: tem2");
            atrio.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #4682B4;");
        } else {
            atrio.setDisable(true);
        }
        stage.show();

    }

    public void UpdateTableCaracteristica(){

        edificio.setCellValueFactory(new PropertyValueFactory<Caracteristica,String>("edificio"));
        nomeSala.setCellValueFactory(new PropertyValueFactory<Caracteristica,String>("nomeSala"));
        capacidadeNormal.setCellValueFactory(new PropertyValueFactory<Caracteristica,Integer>("capacidadeNormal"));
        capacidadeExame.setCellValueFactory(new PropertyValueFactory<Caracteristica,Integer>("capacidadeExame"));

        listC =mysqlconnect.getDatausersC();
        caracteristicas.setItems(listC);

    }
    @FXML
    void search(){
        //AA2.25
        System.out.println(texto_pesquisa_salas.getText());
        if (texto_pesquisa_salas.getText().isEmpty()){
            listM = mysqlconnect.getDatausersC();
            caracteristicas.setItems(listM);
            return;
        }
        FilteredList<Caracteristica> filteredList = new FilteredList<>(caracteristicas.getItems());

        if (texto_pesquisa_salas.getText().split(" OU ").length > 1){
            String [] filtros = texto_pesquisa_salas.getText().split(" OU ");
            filteredList.setPredicate(item -> {
                String columnValue = item.Edificio;
                return (item.Edificio.equals(filtros[0])
                        || item.NomeSala.equals(filtros[0])
                        || ("" + item.CapacidadeNormal).equals(filtros[0])
                        || ("" + item.CapacidadeExame).equals(filtros[0])
                        || ("" + item.NoCaracteristicas).equals(filtros[0])

                )
                        || (item.Edificio.equals(filtros[1])
                        || item.NomeSala.equals(filtros[1])
                        || ("" + item.CapacidadeNormal).equals(filtros[1])
                        || ("" + item.CapacidadeExame).equals(filtros[1])
                        || ("" + item.NoCaracteristicas).equals(filtros[1])

                );
            });
            caracteristicas.setItems(filteredList);
            return;
        }

        if (texto_pesquisa_salas.getText().split(" E ").length > 1){
            String [] filtros = texto_pesquisa_salas.getText().split(" E ");
            filteredList.setPredicate(item -> {
                String columnValue = item.Edificio;
                return (item.Edificio.equals(filtros[0])
                        || item.NomeSala.equals(filtros[0])
                        || ("" + item.CapacidadeNormal).equals(filtros[0])
                        || ("" + item.CapacidadeExame).equals(filtros[0])
                        || ("" + item.NoCaracteristicas).equals(filtros[0])

                )
                        && (item.Edificio.equals(filtros[1])
                        || item.NomeSala.equals(filtros[1])
                        || ("" + item.CapacidadeNormal).equals(filtros[1])
                        || ("" + item.CapacidadeExame).equals(filtros[1])
                        || ("" + item.NoCaracteristicas).equals(filtros[1])

                );
            });
            caracteristicas.setItems(filteredList);
            return;
        }

        filteredList.setPredicate(item -> {
            String columnValue = item.Edificio;
            return (item.Edificio.equals(texto_pesquisa_salas.getText())
                    || item.NomeSala.equals(texto_pesquisa_salas.getText())
                    || ("" + item.CapacidadeNormal).equals(texto_pesquisa_salas.getText())
                    || ("" + item.CapacidadeExame).equals(texto_pesquisa_salas.getText())
                    || ("" + item.NoCaracteristicas).equals(texto_pesquisa_salas.getText())

            );
        });
        caracteristicas.setItems(filteredList);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UpdateTableCaracteristica();
    }
}

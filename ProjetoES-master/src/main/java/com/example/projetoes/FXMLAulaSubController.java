package com.example.projetoes;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.DayOfWeek;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import javafx.scene.*;

import javax.swing.*;

import static com.example.projetoes.mysqlconnect.ConnectDb;

public class FXMLAulaSubController {


    @FXML
    private DatePicker dataInicial;
    @FXML
    private DatePicker dataFinal;
    @FXML
    private Label labelDataI;

    @FXML
    private Label labelDataF;
    @FXML
    private Label labelAvisoData;
    @FXML
    private Menu ucList;
    @FXML
    private Menu cursoList;
    @FXML
    private Menu salaList;
    @FXML
    private Menu salaListE;
    @FXML
    private Menu horarioE;
    @FXML
    private Menu horarioEF;
    @FXML
    private MenuBar columnEditorUC;
    @FXML
    private TableColumn<Horario, String> caracteristicasSala;
    @FXML
    private TableColumn<Horario, String> curso;
    @FXML
    private TableColumn<Horario, String> dataAula;
    @FXML
    private TableColumn<Horario, String> horaFimAula;
    @FXML
    private TableColumn<Horario, String> horaInicioAula;
    @FXML
    private TableColumn<Horario, String> salaAtribuida;
    @FXML
    private TableColumn<Horario, String> curso1;
    @FXML
    private TableColumn<Horario, String> dataAula1;
    @FXML
    private TableColumn<Horario, String> horaFimAula1;
    @FXML
    private TableColumn<Horario, String> horaInicioAula1;
    @FXML
    private TableColumn<Horario, String> salaAtribuida1;

    @FXML
    private TableColumn<HorarioSujestao, String> dataSuj;
    @FXML
    private TableColumn<HorarioSujestao, String> horaFimSuj;
    @FXML
    private TableColumn<HorarioSujestao, String> horaSuj;
    @FXML
    private TableColumn<HorarioSujestao, String> salaSuj;
    @FXML
    private TableColumn<HorarioSujestao, String> diaSemanaSuj;

    @FXML
    private TableColumn<Horario, String> uc;
    @FXML
    private TableColumn<Horario, String> uc1;
    @FXML
    private Button butnInit;
    @FXML
    private Label labelUc;
    @FXML
    private Label labelCurso;
    @FXML
    private Label labelTurma;
    @FXML
    private Label labelTurno;
    @FXML
    private Label labelInscritos;
    @FXML
    private Button btnAdd;
    @FXML
    private Label labelPrincipal;
    @FXML
    private MenuButton pref1;
    @FXML
    private MenuButton pref2;
    @FXML
    private MenuButton pref3;

    @FXML
    private TableView<Horario> horario;

    @FXML
    private TableView<Horario> horarioControlo;

    @FXML
    private TableView<HorarioSujestao> horarioSuj;

    private Horario horarioNovo;

    private  TableView<Horario> horarioaux ;

    @FXML
    private ListView <ArrayList<String>> horarioResultado;
    Connection conn = null;

    ObservableList<Horario> dataList;
    ObservableList<Horario> listM;
    private String dataI;
    private String dataF;
    private String ucEscolhida;
    private String cursoEscolhido;
    private String salaPref1;
    private String salaPref2;
    private String salaPref3;
    private ArrayList<String> ucs=new ArrayList<>();
    private ArrayList<String> horaInicioLista=new ArrayList<>();
    private ArrayList<String> horaFimLista=new ArrayList<>();
    private ArrayList<String> cursos=new ArrayList<>();
    private ArrayList<String> salas=new ArrayList<>();
    private ArrayList<String> horarioExclusao=new ArrayList<>();
    private ArrayList<String> horarioExclusaoF=new ArrayList<>();
    private ArrayList<String> salasPref=new ArrayList<>();
    private ArrayList<String> salasExclusao=new ArrayList<>();
    private int inscritosPreen;
    private String turmaPreen;
    private String turnoPreen;
    private boolean controlo=true;




    public void init() {

        if(!controlo){
            return;
        }
         controlo=false;
         butnInit.setVisible(false);
         horarioExclusao=new ArrayList<>();
         horarioExclusaoF=new ArrayList<>();
         salasPref=new ArrayList<>();
         salasExclusao=new ArrayList<>();
         dataList = FXCollections.observableArrayList();
         listM = mysqlconnect.getDatausers();
         conn = ConnectDb();

        addColumnOptionsUC();
        addColumnOptionsSALA();
        addColumnOptionsCurso();
        populateMenuButton(pref1, salasPref);
        populateMenuButton(pref2, salasPref);
        populateMenuButton(pref3, salasPref);
        populateHoraioExcList();
        populateHoraioExFcList();
        initUCListener();
        addHoraInicio();
        resetPrefListeners();
        initControlo();





        if(!labelUc.getText().isEmpty()){
            ucEscolhida=labelUc.getText();
            cursoEscolhido=labelCurso.getText();
            ucList.setText(ucEscolhida);
            cursoList.setText(cursoEscolhido);
        }

    }

    private void resetPrefListeners(){
        for (MenuButton menuButton : new MenuButton[]{pref1, pref2, pref3,}) {
            for (MenuItem item : menuButton.getItems()) {
                item.setOnAction(event -> {
                    String selectedItem = item.getText();
                    if(menuButton.equals(pref1)){
                        salaPref1=selectedItem;
                    }
                    if(menuButton.equals(pref2)){
                        salaPref2=selectedItem;
                    }
                    if(menuButton.equals(pref3)){
                        salaPref3=selectedItem;
                    }
                    menuButton.setText(selectedItem);
                });
            }
        }
    }
    private void initUCListener() {
        for (Menu menu : new Menu[]{ucList, cursoList}) {
            for (MenuItem item : menu.getItems()) {
                item.setOnAction(event -> {
                    String selectedItem = item.getText();
                    if(menu.equals(ucList)){
                        ucEscolhida=selectedItem;
                        menu.setText(selectedItem);
                    }
                    if(menu.equals(cursoList)){
                        cursoEscolhido=selectedItem;
                        menu.setText(selectedItem);
                    }
                    //if(menu.equals(horarioE) && !horarioExclusao.contains(selectedItem)){
                       // horarioExclusao.add(selectedItem);
                   // }
                   // if(menu.equals(salaList) && !salasPref.contains(selectedItem)){
                  //      salasPref.add(selectedItem);
                   // }
                  //  if(menu.equals(salaListE) && !salasExclusao.contains(selectedItem)){
                   //     salasExclusao.add(selectedItem);
                   // }

                });
            }
        }


    }
    private void addColumnOptionsUC() {
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select UnidadeCurricular from horario");

                List<String> tempUcs = new ArrayList<>();
                while (rs.next()) {
                    tempUcs.add(rs.getString("UnidadeCurricular"));
                }
                ucs = (ArrayList<String>) tempUcs;
                ucs=removerDuplicados(ucs);
                populateUcList(ucs);
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
    private void addColumnOptionsCurso() {
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select curso from horario");

                List<String> tempUcs = new ArrayList<>();
                while (rs.next()) {
                    tempUcs.add(rs.getString("curso"));
                }
                cursos = (ArrayList<String>) tempUcs;
                cursos=removerDuplicados(cursos);
                populateCursoList(cursos);
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
    HashMap<String, Integer> ocupacao = new HashMap<String, Integer>();

    public void ocupacaoPreen(){
        List<String> auxSalas = new ArrayList<>();
        List<Integer> auxInscritos = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : ocupacao.entrySet()) {
            String chave = entry.getKey();
            Integer valor = entry.getValue();

            HashMap<String, Integer> ocupacaoAux = new HashMap<String, Integer>();

            if(ocupacaoAux.containsKey(chave)){
               int valorNovo= ocupacaoAux.get(chave) + valor;
               ocupacaoAux.remove(chave);
               ocupacaoAux.put(chave,valorNovo );
            }else{

            }



        }
    }
    private void addColumnOptionsSALA() {
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select nomeSala from caracterizacaosalas");

                List<String> tempUcs = new ArrayList<>();
                while (rs.next()) {
                    tempUcs.add(rs.getString("nomeSala"));
                }

                salas = (ArrayList<String>) tempUcs;
                salas=removerDuplicados(salas);
                populateSalaList(salas);
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {

            }
        }
    }


private void populateUcList(ArrayList<String> ucs) {
    ucList.getItems().clear();
    for (String uc : ucs) {
        MenuItem menuItem = new MenuItem(uc);
        ucList.getItems().add(menuItem);
    }
}

 private void populateCursoList(ArrayList<String> ucs) {
        cursoList.getItems().clear();
        for (String uc : ucs) {
            MenuItem menuItem = new MenuItem(uc);
            cursoList.getItems().add(menuItem);
        }
    }

    EventHandler horarioExList = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() instanceof CheckBox) {
                CheckBox cb = (CheckBox) event.getSource();
                ObservableList<MenuItem> MenuItem;
                MenuItem=horarioE.getItems();
                for(MenuItem c : horarioE.getItems() ){
                    if(c.getText().equals(cb.getText()) && !horarioExclusao.contains(c.getText())){
                        horarioExclusao.add(c.getText());
                    } else if (c.getText().equals(cb.getText()) && horarioExclusao.contains(c.getText())) {
                        horarioExclusao.remove(c.getText());
                    }

                }
            }
        }
    };

    EventHandler horarioExFList = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() instanceof CheckBox) {
                CheckBox cb = (CheckBox) event.getSource();
                ObservableList<MenuItem> MenuItem;
                MenuItem=horarioEF.getItems();
                for(MenuItem c : horarioEF.getItems() ){
                    if(c.getText().equals(cb.getText()) && !horarioExclusaoF.contains(c.getText())){
                        horarioExclusaoF.add(c.getText());
                    }else if (c.getText().equals(cb.getText()) && horarioExclusaoF.contains(c.getText())) {
                        horarioExclusaoF.remove(c.getText());
                    }
                }
            }
        }
    };
    private void populateHoraioExcList() {
        horarioE.getItems().clear();
        String[] horarios = new String[9];
        horarios [0]="08:30:00";
        horarios [1]="10:00:00";
        horarios [2]="11:30:00";
        horarios [3]="13:00:00";
        horarios [4]="14:30:00";
        horarios [5]="16:00:00";
        horarios [6]="18:00:00";
        horarios [7]="19:30:00";
        horarios [8]="21:00:00";

        for (String uc : horarios) {
            CheckBox cb = new CheckBox();
            cb.setText(uc);
            cb.setStyle("-fx-text-fill: black");
            cb.setSelected(false);
            cb.setOnAction(horarioExList);

            CustomMenuItem item = new CustomMenuItem(cb);
            item.setHideOnClick(false);
            item.setText(uc);
            horarioE.getItems().add(item);

            //MenuItem menuItem = new MenuItem(uc);
            //salaList.getItems().add(menuItem);
        }

    }
    private void populateHoraioExFcList() {
        horarioEF.getItems().clear();
        String[] horarios = new String[9];
        horarios [0]="10:00:00";
        horarios [1]="11:30:00";
        horarios [2]="13:00:00";
        horarios [3]="14:30:00";
        horarios [4]="16:00:00";
        horarios [5]="18:00:00";
        horarios [6]="19:30:00";
        horarios [7]="21:00:00";
        horarios [8]="22:30:00";


        for (String uc : horarios) {
            CheckBox cb = new CheckBox();
            cb.setText(uc);
            cb.setStyle("-fx-text-fill: black");
            cb.setSelected(false);
            cb.setOnAction(horarioExFList);

            CustomMenuItem item = new CustomMenuItem(cb);
            item.setHideOnClick(false);
            item.setText(uc);
            horarioEF.getItems().add(item);

            //MenuItem menuItem = new MenuItem(uc);
            //salaList.getItems().add(menuItem);
        }

    }


    EventHandler salaListHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() instanceof CheckBox) {
                CheckBox cb = (CheckBox) event.getSource();
                ObservableList<MenuItem> MenuItem;
                MenuItem=salaList.getItems();

                for(MenuItem c : salaList.getItems() ){
                    if(c.getText().equals(cb.getText()) && !salasPref.contains(c.getText())){
                        salasPref.add(c.getText());
                    }else if (c.getText().equals(cb.getText()) && salasPref.contains(c.getText())) {
                        salasPref.remove(c.getText());
                }
                }
                populateMenuButton(pref1, salasPref);
                populateMenuButton(pref2, salasPref);
                populateMenuButton(pref3, salasPref);
                resetPrefListeners();
            }
        }
    };
    EventHandler salaListExHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() instanceof CheckBox) {
                CheckBox cb = (CheckBox) event.getSource();
                ObservableList<MenuItem> MenuItem;
                MenuItem=salaListE.getItems();
                for(MenuItem c : salaListE.getItems() ){
                    if(c.getText().equals(cb.getText()) && !salasExclusao.contains(c.getText())){
                        salasExclusao.add(c.getText());
                    }else if (c.getText().equals(cb.getText()) && salasExclusao.contains(c.getText())) {
                        salasExclusao.remove(c.getText());
                    }
                }
            }
        }
    };
    private void populateSalaList(ArrayList<String> ucs) {
        salaList.getItems().clear();
        for (String uc : ucs) {
            CheckBox cb = new CheckBox();
            cb.setText(uc);
            cb.setStyle("-fx-text-fill: black");
            cb.setSelected(false);
            cb.setOnAction(salaListHandler);

            CustomMenuItem item = new CustomMenuItem(cb);
            item.setHideOnClick(false);
            item.setText(uc);
            salaList.getItems().add(item);

            //MenuItem menuItem = new MenuItem(uc);
            //salaList.getItems().add(menuItem);
        }

        salaListE.getItems().clear();
        for (String uc : ucs) {
            CheckBox cb = new CheckBox();
            cb.setText(uc);
            cb.setStyle("-fx-text-fill: black");
            cb.setSelected(false);
            cb.setOnAction(salaListExHandler);

            CustomMenuItem item = new CustomMenuItem(cb);
            item.setHideOnClick(false);
            item.setText(uc);
            salaListE.getItems().add(item);

            //MenuItem menuItem = new MenuItem(uc);
            //salaListE.getItems().add(menuItem);
        }
    }
    private void populateMenuButton(MenuButton button, ArrayList<String> options) {
        button.getItems().clear();
        for (String option : options) {
            MenuItem menuItem = new MenuItem(option);
            button.getItems().add(menuItem);
        }
    }
    //2020-12-02
    public void getDateI(ActionEvent event) {
        LocalDate myDate = dataInicial.getValue();
        String myFormattedDate = myDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        labelDataI.setText("Data inicio "+myFormattedDate);
        dataI=myFormattedDate;
    }

    public void getDateF(ActionEvent event) {
        LocalDate myDate = dataFinal.getValue();
        String myFormattedDate = myDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        labelDataF.setText("Data final "+myFormattedDate);
        dataF=myFormattedDate;
    }


    private void addHoraInicio() {
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select horaInicio from horario");

                List<String> tempUcs = new ArrayList<>();
                while (rs.next()) {
                    tempUcs.add(rs.getString("horaInicio"));
                }
                horaInicioLista = (ArrayList<String>) tempUcs;
                //populateUcList(horaInicioLista);
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
            }
        }
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select horaFim from horario");

                List<String> tempUcs = new ArrayList<>();
                while (rs.next()) {
                    tempUcs.add(rs.getString("horaFim"));
                }
                horaFimLista = (ArrayList<String>) tempUcs;
                //populateUcList(horaInicioLista);
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
    @FXML
    void updatePref(){
        System.out.println("Atualizado");
        System.out.println(salasPref);
        populateMenuButton(pref1, salasPref);
        populateMenuButton(pref2, salasPref);
        populateMenuButton(pref3, salasPref);
    }

    public void initControlo(){
        curso1.setCellValueFactory(new PropertyValueFactory<Horario,String>("curso"));
        uc1.setCellValueFactory(new PropertyValueFactory<Horario,String>("unidadeCurricular"));
        horaInicioAula1.setCellValueFactory(new PropertyValueFactory<Horario,String>("horaInicio"));
        horaFimAula1.setCellValueFactory(new PropertyValueFactory<Horario,String>("horaFim"));
        dataAula1.setCellValueFactory(new PropertyValueFactory<Horario,String>("dataAula"));
        salaAtribuida1.setCellValueFactory(new PropertyValueFactory<Horario,String>("salaAula"));

        listM = mysqlconnect.getDatausers();
        horarioControlo.setItems(listM);
        System.out.println("controlo size "+horarioControlo.getItems().size());
    }


    @FXML
    void search(){
        //AA2.25
        //UpdateTable();

        if (dataInicial.getValue()==null|| dataFinal.getValue()==null) {
            labelAvisoData.setText("Selecione as datas");
            return;
        }

        if (cursoEscolhido==null) {
            labelAvisoData.setText("Selecione o curso");
            return;
        }

        if (ucEscolhida==null) {
            labelAvisoData.setText("Selecione a uc");
            return;
        }

        labelAvisoData.setText("");
        curso.setCellValueFactory(new PropertyValueFactory<Horario,String>("curso"));
        uc.setCellValueFactory(new PropertyValueFactory<Horario,String>("unidadeCurricular"));
        horaInicioAula.setCellValueFactory(new PropertyValueFactory<Horario,String>("horaInicio"));
        horaFimAula.setCellValueFactory(new PropertyValueFactory<Horario,String>("horaFim"));
        dataAula.setCellValueFactory(new PropertyValueFactory<Horario,String>("dataAula"));
        salaAtribuida.setCellValueFactory(new PropertyValueFactory<Horario,String>("salaAula"));

       //2020-12-02
        listM = mysqlconnect.getDatausers();
        horario.setItems(listM);

        FilteredList<Horario> filteredList;
        filteredList = new FilteredList<>(horario.getItems());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        filteredList.setPredicate(item -> {
            String columnValue = item.salaAula;

            return columnValue != null && (
                    !horarioExclusao.contains(item.horaInicio)
                            //ver naquela hora naquele dia as salas que estão livres
                            //criar outra tabela horarioAux pra por no javaFx
                            //aqui ta em comentario mas é o onde se confere se n tem nenhuma aula associada ao horario
                            //&& item.unidadeCurricular.isBlank()
                            && !horarioExclusaoF.contains(item.horaFim)
                            && !salasExclusao.contains(item.salaAula)
                            && (LocalDate.parse(item.dataAula, formatter).isAfter(dataInicial.getValue())||LocalDate.parse(item.dataAula, formatter).isEqual(dataInicial.getValue()))
                            && (LocalDate.parse(item.dataAula, formatter).isBefore(dataFinal.getValue())||LocalDate.parse(item.dataAula, formatter).isEqual(dataFinal.getValue()))
            );
        });


        //faz o sort baseado em pref1, pref2 e pref3
        List<Horario> sortedList = new ArrayList<>(filteredList);
        Collections.sort(sortedList, (horario1, horario2) -> {
            if (horario1.getSalaAula().equals(salaPref1) && !horario2.getSalaAula().equals(salaPref1)) {
                return -1;
            } else if (!horario1.getSalaAula().equals(salaPref1) && horario2.getSalaAula().equals(salaPref1)) {
                return 1;
            }

            if (horario1.getSalaAula().equals(salaPref2) && !horario2.getSalaAula().equals(salaPref2)) {
                return -1;
            } else if (!horario1.getSalaAula().equals(salaPref2) && horario2.getSalaAula().equals(salaPref2)) {
                return 1;
            }

            if (horario1.getSalaAula().equals(salaPref3) && !horario2.getSalaAula().equals(salaPref3)) {
                return -1;
            } else if (!horario1.getSalaAula().equals(salaPref3) && horario2.getSalaAula().equals(salaPref3)) {
                return 1;
            }

            return horario1.getDataAula().compareTo(horario2.getDataAula());
        });


        //horario.setItems(filteredList);
        horario.setItems(FXCollections.observableArrayList(sortedList));



        HashMap<String, ArrayList<String>> result= new HashMap<String, ArrayList<String>>();
        String resultados = "";
        ArrayList <LocalDate> datas = new ArrayList<>();
        datas.add(dataInicial.getValue());
        ArrayList<String> horasInicio = new ArrayList<>();
        horasInicio.add("08:30:00");
        horasInicio.add("10:00:00");
        horasInicio.add("11:30:00");
        horasInicio.add("13:00:00");
        horasInicio.add("14:30:00");
        horasInicio.add("16:00:00");
        horasInicio.add("18:00:00");
        horasInicio.add("19:30:00");
        horasInicio.add("21:00:00");

        ArrayList<String> horasFim = new ArrayList<>();
        horasFim.add("10:00:00");
        horasFim.add("11:30:00");
        horasFim.add("13:00:00");
        horasFim.add("14:30:00");
        horasFim.add("16:00:00");
        horasFim.add("18:00:00");
        horasFim.add("19:30:00");
        horasFim.add("21:00:00");
        horasFim.add("22:30:00");

        LocalDate dtI=dataInicial.getValue();
        LocalDate dtF=dataFinal.getValue();


            while (dtI.isBefore(dtF.plusDays(1))) {
                datas.add(dtI);
                dtI = dtI.plusDays(1);
            }

            datas.remove(0);


        HashMap<String, ArrayList<String>> horaSalas = new HashMap<String, ArrayList<String>>();
        ArrayList<String> horasDisponiveis = new ArrayList<>(horasInicio);  // Copy horasInicio to avoid modification
        for (String hora : horasInicio) {
            if (horarioExclusao.contains(hora)) {
                horasDisponiveis.remove(hora);
            }

        }

        ArrayList<String> salasDisponiveis = new ArrayList<>(salas);  // Copy salas to avoid modification
        for (String sala : salas) {
            if (salasExclusao.contains(sala)) {
                salasDisponiveis.remove(sala);
            }
        }

        HashMap<String, HashMap<String, ArrayList<String>>> diaHoras = new HashMap<>();
        for (LocalDate data : datas) {
            HashMap<String, ArrayList<String>> diaSalas = new HashMap<>();  // Create nested HashMap for each day
            for (String hora : horasDisponiveis) {
                diaSalas.put(hora, new ArrayList<>(salasDisponiveis));  // Add available hours with all available rooms
            }
            diaHoras.put(data.toString(), diaSalas);
            System.out.println("data : " + data.toString()+" diaSalas: "+diaSalas);// Add day-sala mapping to diaHoras
        }

        ObservableList<Horario> itens = horario.getItems();


        for (int i = 0; i < itens.size(); i++) {
            Horario item = itens.get(i);

            String diaItem = item.getDataAula();
            String horaItem = item.getHoraInicio();
            String salaItem = item.getSalaAula();

            // Retrieve the nested HashMap for the specified date
            HashMap<String, ArrayList<String>> diaSalas = diaHoras.get(diaItem);

            // Check if the nested HashMap exists for the given date
            if (diaSalas != null) {
                // Retrieve the list of available rooms for the specified hour
                ArrayList<String> salasHora = diaSalas.get(horaItem);

                // Check if the list of rooms exists for the given hour
                if (salasHora != null) {
                    // Remove the room with the same name as salaItem from the list
                    salasHora.remove(salaItem);

                    // If the list is empty after removal, remove the hour-room mapping from diaSalas
                    if (salasHora.isEmpty()) {
                        diaSalas.remove(horaItem);
                    }

                    // Check if the nested HashMap is empty after removing the hour-room mapping
                    if (diaSalas.isEmpty()) {
                        diaHoras.remove(diaItem);
                    }
                }
            }

        }

        StringBuilder resultadosBuilder = new StringBuilder();
        ObservableList<HorarioSujestao> horarioSujLista = FXCollections.observableArrayList();

        dataSuj.setCellValueFactory(new PropertyValueFactory<HorarioSujestao,String>("data"));
        horaSuj.setCellValueFactory(new PropertyValueFactory<HorarioSujestao,String>("horaInicio"));
        horaFimSuj.setCellValueFactory(new PropertyValueFactory<HorarioSujestao,String>("horaFim"));
        salaSuj.setCellValueFactory(new PropertyValueFactory<HorarioSujestao,String>("salaAula"));
        diaSemanaSuj.setCellValueFactory(new PropertyValueFactory<HorarioSujestao,String>("diaSemana"));


        System.out.println("diaHoras:");
        for (Map.Entry<String, HashMap<String, ArrayList<String>>> diaHoraEntry : diaHoras.entrySet()) {
            String dia = diaHoraEntry.getKey();
            HashMap<String, ArrayList<String>> diaSalasAux = diaHoraEntry.getValue();

            resultadosBuilder.append("  Dia: ").append(dia).append('\n');
            System.out.println("  Dia: " + dia);

            for (Map.Entry<String, ArrayList<String>> horaSalaEntry : diaSalasAux.entrySet()) {
                String hora = horaSalaEntry.getKey();
                ArrayList<String> salas = horaSalaEntry.getValue();

                for(String s:salas){
                    String hrFim="00";
                    switch (hora) {
                        case "08:30:00":
                            hrFim = "10:00:00";
                            break; // Add break to prevent fall-through

                        case "10:00:00":
                            hrFim = "11:30:00";
                            break; // Add break

                        case "11:30:00":
                            hrFim= "13:00:00";
                            break; // Add break

                        case "13:00:00":
                            hrFim = "14:30:00";
                            break; // Add break

                        case"14:30:00":
                            hrFim = "16:00:00";
                            break; // Add break
                        case"16:00:00":
                            hrFim= "17:30:00";
                            break; // Add break
                        case"17:30:00":
                            hrFim = "18:00:00";
                            break; // Add break
                        case "18:00:00":
                            hrFim= "19:30:00";
                            break; // Add break
                        case "19:30:00":
                            hrFim =  "21:00:00";
                            break; // Add break
                        case "21:00:00":
                            hrFim = "22:30:00";
                            break; // Add break for the last case
                    }
                    horarioSujLista.add(new HorarioSujestao(dia,hora,s,hrFim, getDayOfWeek(dia)));
                }


                resultadosBuilder.append("    Hora: ").append(hora).append(", Salas: ").append(salas).append('\n');
                System.out.println("    Hora: " + hora + ", Salas: " + salas);
            }
        }


        //fazer aqui:
       // atualizar os campos da tableview aqui para mostrar os valores adicionados a horarioSujLista
        horarioSuj.setItems(horarioSujLista);

        FilteredList<HorarioSujestao> filteredListSuj;
        filteredListSuj = new FilteredList<>(horarioSujLista);

        filteredListSuj.setPredicate(itemS -> {
            String columnValue = itemS.getSalaAula();

            return columnValue != null && (
                    !horarioExclusaoF.contains(itemS.getHoraFim())
            );
        });

        horarioSuj.setItems(filteredListSuj);

        //faz o sort baseado em pref1, pref2 e pref3
        List<HorarioSujestao> sortedListSuj = new ArrayList<>(horarioSujLista);
        Collections.sort(sortedListSuj, (horario1, horario2) -> {
            if (horario1.getSalaAula().equals(salaPref1) && !horario2.getSalaAula().equals(salaPref1)) {
                return -1;
            } else if (!horario1.getSalaAula().equals(salaPref1) && horario2.getSalaAula().equals(salaPref1)) {
                return 1;
            }

            if (horario1.getSalaAula().equals(salaPref2) && !horario2.getSalaAula().equals(salaPref2)) {
                return -1;
            } else if (!horario1.getSalaAula().equals(salaPref2) && horario2.getSalaAula().equals(salaPref2)) {
                return 1;
            }

            if (horario1.getSalaAula().equals(salaPref3) && !horario2.getSalaAula().equals(salaPref3)) {
                return -1;
            } else if (!horario1.getSalaAula().equals(salaPref3) && horario2.getSalaAula().equals(salaPref3)) {
                return 1;
            }

            return horario1.getData().compareTo(horario2.getData());
        });


        horarioSuj.setItems(FXCollections.observableArrayList(sortedListSuj));


        buscarOpt();
        System.out.println("controlo size "+horarioControlo.getItems().size());




    }

    public void getSelected(MouseEvent mouseEvent) throws IOException, SQLException {
        btnAdd.setVisible(true);
        int hjIndex=horarioSuj.getSelectionModel().getSelectedIndex();
        HorarioSujestao hj=horarioSuj.getItems().get(hjIndex);
        if(hj.getDiaSemana()=="Dom"||hj.getDiaSemana()=="Sab"){
            btnAdd.setVisible(false);
            labelAvisoData.setText("Fim de semana selecionado");
            return;
        }

        String caracteristicaSala="";
        String diaSemanaS=hj.getDiaSemana();
        String turnoSugest="";
        String turmaSugest="";
        int inscritosSust=0;

        horarioaux=horario;


        FilteredList<Horario> filteredListPreen;
        filteredListPreen = new FilteredList<>(horarioControlo.getItems());

        filteredListPreen.setPredicate(item -> {
            String columnValue = item.salaAula;

            return columnValue != null && (
                    item.salaAula.equals(hj.getSalaAula())

            );
        });


        horarioaux.setItems(filteredListPreen);

        System.out.println(horarioaux.getItems().size());
        System.out.println(horarioControlo.getItems().size());
        Horario itemAux = horarioaux.getItems().get(0);
        caracteristicaSala=itemAux.caractSala;

        if(labelUc.getText().isEmpty()){

            turnoSugest=itemAux.turno;
            turmaSugest=itemAux.turma;
            inscritosSust=itemAux.inscrtosTurno;
        }else{
             turnoSugest=labelTurno.getText();
             turmaSugest=labelTurma.getText();
             inscritosSust=Integer.parseInt(labelInscritos.getText());
        }


        Horario novoHorario=new Horario(cursoEscolhido,ucEscolhida,turnoSugest,turmaSugest,inscritosSust,diaSemanaS,hj.getHoraInicio(),hj.getHoraFim(),hj.getData(),caracteristicaSala,hj.getSalaAula());
        horarioNovo=novoHorario;

        System.out.println("cursoEscolhido : "+novoHorario.curso);
        System.out.println("ucEscolhida : "+novoHorario.unidadeCurricular);
        System.out.println("Turno : "+novoHorario.turno);
        System.out.println("Turma : "+novoHorario.turma);
        System.out.println("Inscritos : "+novoHorario.inscrtosTurno);
        System.out.println("diaSemana : "+novoHorario.diaSemana);
        System.out.println("HoraInicio : "+hj.getHoraInicio());
        System.out.println("HoraFim : "+hj.getHoraFim());
        System.out.println("Data : "+hj.getData());
        System.out.println("caracteristicaSala : "+caracteristicaSala);
        System.out.println("Sala : "+hj.getSalaAula());
        System.out.println("diaSemana : "+hj.getDiaSemana());





    }


    public void adicionarAula(MouseEvent mouseEvent) throws SQLException, IOException {
        //TODO:
        // FUNCÃO QUE ADICIONA horarioNovo AO HORARIO
        if(horarioNovo.diaSemana=="Dom"||horarioNovo.diaSemana=="Sab"){
            btnAdd.setVisible(false);
            labelAvisoData.setText("Fim de semana selecionado");
            return;
        }
        InsertHorarioDB(horarioNovo.curso,horarioNovo.unidadeCurricular,horarioNovo.turno,horarioNovo.turma,horarioNovo.inscrtosTurno,horarioNovo.diaSemana,horarioNovo.horaInicio,
                        horarioNovo.horaFim, LocalDate.parse(horarioNovo.dataAula),horarioNovo.caractSala,horarioNovo.salaAula);
        System.out.println("HORARIO ADICIONADO ");
        System.out.println("cursoEscolhido : "+horarioNovo.curso);
        System.out.println("ucEscolhida : "+horarioNovo.unidadeCurricular);
        System.out.println("Turno : "+horarioNovo.turno);
        System.out.println("Turma : "+horarioNovo.turma);
        System.out.println("Inscritos : "+horarioNovo.inscrtosTurno);
        System.out.println("diaSemana : "+horarioNovo.diaSemana);
        System.out.println("HoraInicio : "+horarioNovo.horaInicio);
        System.out.println("HoraFim : "+horarioNovo.horaFim);
        System.out.println("Data : "+horarioNovo.dataAula);
        System.out.println("caracteristicaSala : "+horarioNovo.caractSala);
        System.out.println("Sala : "+horarioNovo.salaAula);
        JOptionPane.showMessageDialog(null, "Updated successfully");

        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();


    }



    public void InsertHorarioDB(String curso, String unidadeCurricular, String turno, String turma,
    int inscritosTurno, String diaSemana, String horaInicio, String horaFim,
    LocalDate dataAula, String caracteristicasSala, String salaAula) throws SQLException {

        PreparedStatement preparedStatement = null;
        try {


            String sql = "INSERT INTO horario (Curso, UnidadeCurricular, Turno, Turma, InscritosTurno, DiaSemana, " +
                    "HoraInicio, HoraFim, DataAula, CaracteristicasSala, SalaAula) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, curso);
            preparedStatement.setString(2, unidadeCurricular);
            preparedStatement.setString(3, turno);
            preparedStatement.setString(4, turma);
            preparedStatement.setInt(5, inscritosTurno);
            preparedStatement.setString(6, diaSemana);
            preparedStatement.setTime(7, java.sql.Time.valueOf(horaInicio)); // Convert to Time object
            preparedStatement.setTime(8, java.sql.Time.valueOf(horaFim)); // Convert to Time object
            preparedStatement.setDate(9, Date.valueOf(dataAula)); // Convert to Date object
            preparedStatement.setString(10, caracteristicasSala);
            preparedStatement.setString(11, salaAula);

            preparedStatement.executeUpdate();
            System.out.println("Horário inserido com sucesso!");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void buscarOpt(){

        System.out.println("Salas : "+salas);
        System.out.println("Horas Inicio : "+horaInicioLista);
        System.out.println("Horas Fim : "+horaFimLista);
        System.out.println("Curso : "+cursoEscolhido);
        System.out.println("UC : "+ucEscolhida);
        System.out.println("Horario de exclusão Inicio: "+horarioExclusao);
        System.out.println("Horario de exclusão Fim: "+horarioExclusaoF);
        System.out.println("Data Inicial : "+dataI);
        System.out.println("Data Final : "+dataF);
        System.out.println("Salas de preferência : "+salasPref);
        System.out.println("Salas excluídas : "+salasExclusao);
        System.out.println("Preferência 1 : "+salaPref1);
        System.out.println("Preferência 2 : "+salaPref2);
        System.out.println("Preferência 3 : "+salaPref3);
        //fazer o update da tabela com o filtro das opções escolhidas
    }



        public static String getDayOfWeek(String dateString) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Formatter for parsing the date string

            LocalDate date = LocalDate.parse(dateString.formatted(formatter));

            DayOfWeek dayOfWeek = date.getDayOfWeek(); // Get the day of the week enum

            // Convert day of week enum to a string representation
            String dayOfWeekString = "null";
            switch (dayOfWeek) {
                case SUNDAY:
                    dayOfWeekString = "Dom";
                    break;
                case MONDAY:
                    dayOfWeekString = "Seg";
                    break;
                case TUESDAY:
                    dayOfWeekString = "Ter";
                    break;
                case WEDNESDAY:
                    dayOfWeekString = "Qua";
                    break;
                case THURSDAY:
                    dayOfWeekString = "Qui";
                    break;
                case FRIDAY:
                    dayOfWeekString = "Sex";
                    break;
                case SATURDAY:
                    dayOfWeekString = "Sab";
                    break;
            }

            return dayOfWeekString;
        }
    public ArrayList<String> removerDuplicados(ArrayList<String> listaComDuplicatas) {
        ArrayList<String> listaSemDuplicatas = new ArrayList<>();
        for (String elemento : listaComDuplicatas) {
            if (!listaSemDuplicatas.contains(elemento)) {
                listaSemDuplicatas.add(elemento);
            }
        }
        return listaSemDuplicatas;
    }

    public void setData(Connection conn) {
        this.conn = conn;
    }
}

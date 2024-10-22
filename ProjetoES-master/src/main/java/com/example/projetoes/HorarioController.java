package com.example.projetoes;

import com.opencsv.CSVReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLType.*;
import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.example.projetoes.mysqlconnect.ConnectDb;
import javafx.scene.control.TableRow;
import javafx.scene.paint.Color;
public class HorarioController implements Initializable {

    //final ListView<String> selectedItems = new ListView<>();
    private ObservableList<TableColumn<Horario, ?>> shownColumns;
    @FXML
    public TextField texto_pesquisa_horario;



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
    private TableView<Horario> horario;

    @FXML
    private TableColumn<Horario, Integer> inscritosTurma;

    @FXML
    private TableColumn<Horario, String> salaAtribuida;

    @FXML
    private TableColumn<Horario, String> turma;

    @FXML
    private TableColumn<Horario, String> turno;
    @FXML
    private TableColumn<Horario, Integer> idHorario;

    @FXML
    private TableColumn<Horario, String> uc;

    private ArrayList <Integer> resultadosIds=new ArrayList<>();
    private HashMap<Horario,Integer> horarioIds=new HashMap<>();
    private HashMap<Horario,Integer> horarioIdsControlo=new HashMap<>();
    @FXML
    public TextField texto_pesquisa;

    @FXML
    private Menu columnEditor;

    @FXML
    private MenuButton btn_semana_sem_horario;

    @FXML
    private MenuBar columnEditor2;
    @FXML
    private Button aulaSub;

    @FXML
    private Button heatMapButton;

    @FXML
    private Button recarregarBtn;
    @FXML
    private Label labelSelecionada;
    @FXML
    private Label labelConfirmar;
    private int confirm=1;


    ObservableList<Horario> listM;
    ObservableList<Horario> dataList;
    private ObservableList<TableColumn<Horario, ?>> columnsVar;

    private int index = -1;
    private int indexSug=-1;

    public Horario getHorarioSelecionado() {
        return horarioSelecionado;
    }

    public Horario horarioSelecionado;

    private ArrayList<String> ucs = new ArrayList<>();
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void switchStageheatMap(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource("heatmap.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void switchStageAulaSub(MouseEvent mouseEvent) throws IOException, SQLException {

        if(confirm==0){

            labelConfirmar.setText("A AÇÃO IRA APAGAR A AULA SELECIONADA");
            aulaSub.setText("CONFIRMAR");
            confirm++;
        }else {
            if (horarioSelecionado != null) {

                System.out.println("Curso: " + horarioSelecionado.curso);
                System.out.println("Unidade Curricular: " + horarioSelecionado.unidadeCurricular);
                System.out.println("Turno: " + horarioSelecionado.turno);
                System.out.println("Turma: " + horarioSelecionado.turma);
                System.out.println("Dia da Semana: " + horarioSelecionado.diaSemana);
                System.out.println("Hora de Início: " + horarioSelecionado.horaInicio);
                System.out.println("Hora de Fim: " + horarioSelecionado.horaFim);
                System.out.println("Data da Aula: " + horarioSelecionado.dataAula);
                System.out.println("Características da Sala: " + horarioSelecionado.caractSala);
                System.out.println("Sala: " + horarioSelecionado.salaAula);
                System.out.println("Inscritos: " + horarioSelecionado.inscrtosTurno);


                deleteHorarioByAttributes(horarioSelecionado.curso,horarioSelecionado.unidadeCurricular,horarioSelecionado.turno,
                        horarioSelecionado.turma,horarioSelecionado.diaSemana,horarioSelecionado.horaInicio,
                        horarioSelecionado.horaFim,horarioSelecionado.dataAula,horarioSelecionado.caractSala,
                        horarioSelecionado.salaAula,horarioSelecionado.inscrtosTurno );

            }


            confirm=1;
            Stage stage = new Stage();

            Parent root = FXMLLoader.load(getClass().getResource("aulaSub.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            if (horarioSelecionado != null) {
                Label labelS = (Label) scene.lookup("#labelPrincipal");
                Label labelUc = (Label) scene.lookup("#labelUc");
                Label labelTurma = (Label) scene.lookup("#labelTurma");
                Label labelTurno = (Label) scene.lookup("#labelTurno");
                Label labelInscritos = (Label) scene.lookup("#labelInscritos");
                Label labelCurso = (Label) scene.lookup("#labelCurso");
                Button btnS = (Button) scene.lookup("#btnAdd");
                btnS.setText("Substituir por slot selecionado");
                labelS.setText("Substituir aula");
                labelUc.setText(horarioSelecionado.unidadeCurricular);
                labelCurso.setText(horarioSelecionado.curso);
                labelTurma.setText(horarioSelecionado.turma);
                labelTurno.setText(horarioSelecionado.turno);
                labelInscritos.setText(String.valueOf(horarioSelecionado.inscrtosTurno));
                //turma
                //turno
                //inscritos




            }
        }
        recarregarBtn.setVisible(true);

    }

    public void deleteHorarioByAttributes(String curso, String unidadeCurricular, String turno,
                                          String turma, String diaSemana, String horaInicio,
                                          String horaFim, String dataAula, String caracteristicasSala,
                                          String salaAula, int InscritosTurno) throws SQLException {


        Integer index=0;

        for(Horario item:horario.getItems()){
            if( item.curso.equals(curso)
                    && item.unidadeCurricular.equals(unidadeCurricular)
                    && item.turno.equals(turno)
                    && item.turma.equals(turma)
                    &&item.diaSemana.equals(diaSemana)
                    &&item.horaInicio.equals(horaInicio)
                    &&item.horaFim.equals(horaFim)
                    &&item.dataAula.equals(dataAula)
                    &&item.salaAula.equals(salaAula)){
                index=horarioIds.get(item);
            }
        }

        System.out.println("index --> "+index);

        if(index>0){

            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            conn=ConnectDb();

            try {
                int idToDelete=index;
                System.out.println("Horário encontrado com ID: " + idToDelete);


                String deleteSql = "DELETE FROM horario WHERE id = ?";
                PreparedStatement deleteStatement = conn.prepareStatement(deleteSql);

                deleteStatement.setInt(1, idToDelete);

                int deletedRows = deleteStatement.executeUpdate();

                if (deletedRows > 0) {
                    System.out.println("Horário com ID " + idToDelete + " deletado com sucesso!");
                } else {
                    System.out.println("Falha ao deletar o horário.");
                }

                deleteStatement.close();

            } finally {

            }
        }
        //UpdateTable();

    }

    public void getIds() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        conn=ConnectDb();
        resultadosIds.clear();
        try {

            String sql = "SELECT id FROM horario";

            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while( resultSet.next()) {
                int idToDelet=resultSet.getInt("id");
                resultadosIds.add(idToDelet);
            }
            System.out.println("Ids : "+resultadosIds);
        } finally {}

        int i=0;

        horarioIds.clear();
        for(Horario h :horario.getItems()){
            horarioIds.put(h,resultadosIds.get(i));
            //System.out.println("h.horaInicio + h.horaFim e ID");
            // System.out.println(h.horaInicio +" "+ h.horaFim);
            // System.out.println(resultadosIds.get(i));
            // System.out.println("");
            i++;
        }

        System.out.println(horarioIds);

    }


    EventHandler viewColumn = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() instanceof CheckBox) {
                CheckBox cb = (CheckBox) event.getSource();
                for(TableColumn c : columnsVar ){
                    if(c.getText().equals(cb.getText())){
                        c.setVisible(cb.isSelected());
                    }
                }
            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataList = FXCollections.observableArrayList();
        try {
            UpdateTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        addColumnOptions();

        try {
            getIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        int i=0;

        for(Horario h :horario.getItems()){
            horarioIdsControlo.put(h,resultadosIds.get(i));
            i++;
        }

        System.out.println("Init horarioIds "+horarioIds.size());
        System.out.println("Init horarioControlo "+horarioIdsControlo.size());


    }



    @FXML
    public void Add_horario (){
        conn = ConnectDb();
        String sql = "insert into horario (Curso, UnidadeCurricular, Turno, Turma, InscritosTurno, DiaSemana, HoraInicio, HoraFim, DataAula, CaracteristicasSala, SalaAula) values (?,?,?,?,?,?,?,?,?,?,?)";
        //INSERT INTO `horario` VALUES ('ME','Teoria dos Jogos e dos Contratos','01789TP01','MEA1',30,'Sex','13:00:00','14:30:00','2022-12-02','Sala Aulas Mestrado','AA2.25');*/

        FileChooser fil_chooser = new FileChooser();
        fil_chooser.setTitle("Escolher Ficheiro");
        fil_chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json e CSV", "*.json", "*.csv"));
        File file = fil_chooser.showOpenDialog(null);
        System.out.println(file.getPath());

        try {

            FileReader filereader = new FileReader(file.getPath());

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;
            int count = 0;

            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {

                    String [] line  = cell.split(";");
                    if (count > 0) {
                        String[] dateParts = line[8].split("/");
                        String date = dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];

                        pst = conn.prepareStatement(sql);
                        pst.setString(1, line[0]);
                        pst.setString(2, line[1]);
                        pst.setString(3, line[2]);
                        pst.setString(4, line[3]);
                        pst.setInt(5, Integer.parseInt(line[4]));
                        pst.setString(6, line[5]);
                        pst.setTime(7, Time.valueOf(line[6]));
                        pst.setTime(8, Time.valueOf(line[7]));
                        pst.setDate(9, Date.valueOf(date));
                        pst.setString(10, line[9]);
                        pst.setString(11, line[10]);
                        pst.execute();
                        System.out.println("Users Add succes");
                    }
                    count ++;
                }
            }

            JOptionPane.showMessageDialog(null, "Users Add success");
            UpdateTable();
            //search_user();
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }

    }

    public void UpdateTable() throws SQLException {

        if(!recarregarBtn.isDisable()){
            recarregarBtn.setVisible(false);
        }

        horarioSelecionado=null;
        labelSelecionada.setText("");
        aulaSub.setText("Adicionar aula");

        curso.setCellValueFactory(new PropertyValueFactory<Horario,String>("curso"));
        uc.setCellValueFactory(new PropertyValueFactory<Horario,String>("unidadeCurricular"));
        turno.setCellValueFactory(new PropertyValueFactory<Horario,String>("turno"));
        turma.setCellValueFactory(new PropertyValueFactory<Horario,String>("turma"));
        inscritosTurma.setCellValueFactory(new PropertyValueFactory<Horario,Integer>("inscrtosTurno"));
        diaSemana.setCellValueFactory(new PropertyValueFactory<Horario,String>("diaSemana"));
        horaInicioAula.setCellValueFactory(new PropertyValueFactory<Horario,String>("horaInicio"));
        horaFimAula.setCellValueFactory(new PropertyValueFactory<Horario,String>("horaFim"));
        dataAula.setCellValueFactory(new PropertyValueFactory<Horario,String>("dataAula"));
        caracteristicasSala.setCellValueFactory(new PropertyValueFactory<Horario,String>("caractSala"));
        salaAtribuida.setCellValueFactory(new PropertyValueFactory<Horario,String>("salaAula"));



        listM = mysqlconnect.getDatausers();
        conn = ConnectDb();

        horario.setItems(listM);
        confirm=1;
        labelConfirmar.setText("");

        getIds();
        System.out.println(" horarioIds "+horarioIds.size());
        System.out.println(" horarioControlo "+horarioIdsControlo.size());

        ArrayList<Integer> indexs=new ArrayList<>();

        for (Horario h : horarioIds.keySet()) {
            if(!horarioIdsControlo.containsValue(horarioIds.get(h))&&horarioIdsControlo.size()>1){
                indexs.add(horarioIds.get(h));
                for(Horario h2:horario.getItems()){
                    if(h.equals(h2)){
                        h2.horaInicio= "* "+h2.horaInicio+" *";
                        h2.setCurso("* " + h2.getCurso() + " *");
                        h2.setUnidadeCurricular("* " + h2.getUnidadeCurricular() + " *");
                        h2.setTurno("* " + h2.getTurno() + " *");
                        h2.setTurma("* " + h2.getTurma() + " *");
                        h2.setDiaSemana("* " + h2.getDiaSemana() + " *");
                        h2.setHoraInicio("* " + h2.getHoraInicio() + " *");
                        h2.setHoraFim("* " + h2.getHoraFim() + " *");
                        //h2.setDataAula("* " + h2.getDataAula() + " *");
                        h2.setCaractSala("* " + h2.getCaractSala() + " *");
                        h2.setSalaAula("* " + h2.getSalaAula() + " *");
                    }
                }

                labelSelecionada.setText("*horario adicionado*");

            }
        }

        

        System.out.println(" indexs "+indexs.size());


    }

    @FXML
    void search(){
        confirm=1;
        labelSelecionada.setText("");
        //AA2.25
        System.out.println(texto_pesquisa_horario.getText());
        if (texto_pesquisa_horario.getText().isEmpty()){
            listM = mysqlconnect.getDatausers();
            horario.setItems(listM);
            return;
        }

        FilteredList<Horario> filteredList = new FilteredList<>(horario.getItems());

        if (texto_pesquisa_horario.getText().split(" OU ").length > 1){
            String [] filtros = texto_pesquisa_horario.getText().split(" OU ");
            filteredList.setPredicate(item -> {
                String columnValue = item.unidadeCurricular;
                return (item.curso.equals(filtros[0])
                        || item.unidadeCurricular.equals(filtros[0])
                        || item.turno.equals(filtros[0])
                        || item.turma.equals(filtros[0])
                        || ("" + item.inscrtosTurno).equals(filtros[0])
                        || item.diaSemana.equals(filtros[0])
                        || item.horaInicio.equals(filtros[0])
                        || item.horaFim.equals(filtros[0])
                        || item.caractSala.equals(filtros[0])
                        || item.salaAula.equals(filtros[0])
                )
                        || (item.curso.equals(filtros[1])
                        || item.unidadeCurricular.equals(filtros[1])
                        || item.turno.equals(filtros[1])
                        || item.turma.equals(filtros[1])
                        || ("" + item.inscrtosTurno).equals(filtros[1])
                        || item.diaSemana.equals(filtros[1])
                        || item.horaInicio.equals(filtros[1])
                        || item.horaFim.equals(filtros[1])
                        || item.caractSala.equals(filtros[1])
                        || item.salaAula.equals(filtros[1])
                );
            });
            horario.setItems(filteredList);
            return;
        }

        if (texto_pesquisa_horario.getText().split(" E ").length > 1){
            String [] filtros = texto_pesquisa_horario.getText().split(" E ");
            System.out.println(filtros[0] + " " + filtros[1]);
            filteredList.setPredicate(item -> {
                String columnValue = item.unidadeCurricular;
                return (item.curso.equals(filtros[0])
                        || item.unidadeCurricular.equals(filtros[0])
                        || item.turno.equals(filtros[0])
                        || item.turma.equals(filtros[0])
                        || ("" + item.inscrtosTurno).equals(filtros[0])
                        || item.diaSemana.equals(filtros[0])
                        || item.horaInicio.equals(filtros[0])
                        || item.horaFim.equals(filtros[0])
                        || item.caractSala.equals(filtros[0])
                        || item.salaAula.equals(filtros[0])
                )
                        && (item.curso.equals(filtros[1])
                        || item.unidadeCurricular.equals(filtros[1])
                        || item.turno.equals(filtros[1])
                        || item.turma.equals(filtros[1])
                        || ("" + item.inscrtosTurno).equals(filtros[1])
                        || item.diaSemana.equals(filtros[1])
                        || item.horaInicio.equals(filtros[1])
                        || item.horaFim.equals(filtros[1])
                        || item.caractSala.equals(filtros[1])
                        || item.salaAula.equals(filtros[1])
                );
            });
            horario.setItems(filteredList);
            return;
        }

        filteredList.setPredicate(item -> {
            String columnValue = item.unidadeCurricular;
            return columnValue != null && (
                    item.curso.equals(texto_pesquisa_horario.getText())
                            || item.unidadeCurricular.equals(texto_pesquisa_horario.getText())
                            || item.turno.equals(texto_pesquisa_horario.getText())
                            || item.turma.equals(texto_pesquisa_horario.getText())
                            || ("" + item.inscrtosTurno).equals(texto_pesquisa_horario.getText())
                            || item.diaSemana.equals(texto_pesquisa_horario.getText())
                            || item.horaInicio.equals(texto_pesquisa_horario.getText())
                            || item.horaFim.equals(texto_pesquisa_horario.getText())
                            || item.caractSala.equals(texto_pesquisa_horario.getText())
                            || item.salaAula.equals(texto_pesquisa_horario.getText())
            );
        });
        horario.setItems(filteredList);
        //horario.setItems(mysqlconnect.getSearch(texto_pesquisa.getText()));
    }


    public void getSelected(MouseEvent mouseEvent) throws IOException {
        indexSug=horario.getSelectionModel().getSelectedIndex();
        Horario item = horario.getSelectionModel().getSelectedItem();
        horarioSelecionado=item;
        if (indexSug >= 0){

            labelSelecionada.setText(item.curso+ " "+item.salaAula+ " "+item.dataAula);
            aulaSub.setText("Substituir aula");
            confirm=0;

        }else{
            return;
        }
    }

    public void addColumnOptions(){
        columnsVar = horario.getColumns();

        for( TableColumn c : columnsVar) {
            CheckBox cb = new CheckBox();
            cb.setText(c.getText());
            cb.setStyle("-fx-text-fill: black");
            cb.setSelected(true);
            cb.setOnAction(viewColumn);

            CustomMenuItem item = new CustomMenuItem(cb);
            item.setHideOnClick(false);
            item.setText(c.getText());

            columnEditor.getItems().add(item);

        }
        System.out.println("Aqui -> " + columnEditor.getItems().size());
    }


    public void gravar_horario(MouseEvent mouseEvent) {

        for (Horario h : horario.getItems()) {
            if (h.equals(h))
                h.removerAsteriscos();
        }


        ArrayList<Horario> lista = new ArrayList<>();
        lista.addAll(horario.getItems().sorted());

        FileChooser salvarFicheiro = new FileChooser();
        salvarFicheiro.setTitle("Guardar Ficheiro");
        FileChooser.ExtensionFilter extencoes = new FileChooser.ExtensionFilter("Json", "*.json");
        FileChooser.ExtensionFilter extencoes1 = new FileChooser.ExtensionFilter("CSV",  "*.csv");
        salvarFicheiro.getExtensionFilters().add(extencoes);
        salvarFicheiro.getExtensionFilters().add(extencoes1);
        File resultado = salvarFicheiro.showSaveDialog(null);

        try {

            FileWriter writer = new FileWriter(resultado);
            for(Horario h : lista) {
                String linha = h.curso + "; " + h.unidadeCurricular + "; " + h.turno + "; " + h.turma + "; " + h.inscrtosTurno + "; " + h.diaSemana + "; " + h.horaInicio + "; " + h.horaFim + "; " + h.dataAula + "; " + h.caractSala + "; " + h.salaAula;
                writer.write(linha);
                writer.write("\n");
            }
            writer.close();
            JOptionPane.showMessageDialog(null, "Updated successfully");
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    }


    @FXML
    public void ss1h(ActionEvent event){

    }

    @FXML
    public void onclicked_semanahorario(ActionEvent event) throws SQLException {

        UpdateTable();
        MenuItem clickedButton = (MenuItem) event.getSource();
        String id = clickedButton.getId();
        String [] idSplited1 = id.split("ss");
        String [] idSplited2 = idSplited1[1].split("h");
        int val = Integer.parseInt(idSplited2[0]);
        splitSemestre(val);

    }

    public void splitSemestre(int semana) throws SQLException {
        String dia1Semestre = "select DataAula, DiaSemana from horario order by DataAula limit 1";
        conn = ConnectDb();
        Statement st = conn.prepareStatement(dia1Semestre);
        ResultSet rs = st.executeQuery(dia1Semestre);

        Date dia1 = null;
        String dia1Semana = "";
        while(rs.next()){
            dia1 = rs.getDate("DataAula");
            dia1Semana = rs.getString("DiaSemana");
        }
        LocalDate dia1Local = dia1.toLocalDate();

        int day = 0;
        switch (dia1Semana){
            case "Seg":
                break;

            case "Ter":
                day = 1;
                break;

            case "Qua":
                day = 2;
                break;

            case "Qui":
                day = 3;
                break;

            case "Sex":
                day = 4;
                break;

            case "Sab":
                day = 5;
                break;

            case "Dom":
                day = 6;
                break;

        }

        LocalDate inicioSemana = dia1Local.with(DayOfWeek.of(day)).plusWeeks(semana - 1);
        LocalDate finalSemana = inicioSemana.plusDays(6);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String inicioSemanaStr = inicioSemana.format(formatter);
        String finalSemanaStr = finalSemana.format(formatter);

        FilteredList<Horario> filteredList = new FilteredList<>(horario.getItems());
        filteredList.setPredicate(item -> {
            String str = "";
            return (LocalDate.parse(item.dataAula,formatter).isEqual(LocalDate.parse(inicioSemanaStr,formatter))
                    || LocalDate.parse(item.dataAula,formatter).isAfter(LocalDate.parse(inicioSemanaStr,formatter)))
                    && (LocalDate.parse(item.dataAula,formatter).isEqual(LocalDate.parse(finalSemanaStr,formatter))
                    || (LocalDate.parse(item.dataAula,formatter).isBefore(LocalDate.parse(finalSemanaStr,formatter))));
        });

        horario.setItems(filteredList);
    }


    //semanas
    @FXML
    public void onclicked_semanaAnoHorario(ActionEvent event) throws SQLException {
        UpdateTable();
        MenuItem clickedButton = (MenuItem) event.getSource();
        String id = clickedButton.getId();
        String [] idSplited1 = id.split("sa");
        String [] idSplited2 = idSplited1[1].split("h");
        int val = Integer.parseInt(idSplited2[0]);
        splitSemestre(val);

    }

    @FXML
    public void onclicked_teste(ActionEvent event) throws IOException {
        Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource("aulasConflito.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void switchStageConflito(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource("aulasConflito.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
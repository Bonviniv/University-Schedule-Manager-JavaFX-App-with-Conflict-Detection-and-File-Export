package com.example.projetoes;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static com.example.projetoes.mysqlconnect.ConnectDb;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.*;


public class HeatMapController {


    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private MenuButton btn_semana_sem_horario;

    private ArrayList<String> salas=new ArrayList<>();
    private ArrayList<String> numSalas=new ArrayList<>();
    ObservableList<Horario> listM;
    Connection conn = null;
    HashMap<String, Integer> ocupacao = new HashMap<String, Integer>();

    HashMap<String, Integer> ocupacaoFinal = new HashMap<String, Integer>();

    private Map<String, Integer> sortedHashMap = new LinkedHashMap<>();

    private int inscritosPreen;

    @FXML
    private Button teste_heatmap;

    @FXML
    private Label collabel;
    @FXML
    private Menu salaList;
    @FXML
    private Label rowlabel;
    private ArrayList<String> salasPref=new ArrayList<>();
    //TODO criar funcao para ir buscar os inscritos

    @FXML
    private TableView<Horario> horario;

    private static final int NUM_ROWS = 7; // Dias da semana
    private int NUM_COLS = 0; // Salas
    private int VAL_MAX = 0;
    private int VAL_MIN = 0;


    private int semana_selecionada=0;

    private boolean controlo=true;



    HashMap<String, Integer> ocupacaoAux = new HashMap<String, Integer>();

    public void ocupacaoPreen(MouseEvent mouseEvent){
        List<String> auxSalas = new ArrayList<>();
        List<Integer> auxInscritos = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : ocupacao.entrySet()) {
            String chave = entry.getKey();
            String chaveAux = chave.split("/")[0];

            Integer valor = entry.getValue();

            auxSalas.add(chave);
            auxInscritos.add(valor);

            if(ocupacaoAux.containsKey(chaveAux)){
                int valorNovo= ocupacaoAux.get(chaveAux) + valor;
                ocupacaoAux.remove(chaveAux);
                ocupacaoAux.put(chaveAux,valorNovo);

            }else{
                ocupacaoAux.put(chaveAux,valor);
            }


        }
        Node node = (Node) mouseEvent.getSource();

        // Obtém a cena associada ao nó
        javafx.scene.Scene scene = node.getScene();

        // Obtém o stage associado à cena

        Stage stage = (Stage) scene.getWindow();
        startHeatMap(stage);
        /*for (Map.Entry<String, Integer> entry : ocupacaoAux.entrySet()) {
            String chave = entry.getKey();
            Integer valor = entry.getValue();
            System.out.println("Chave: " + chave + ", Valor: " + valor);
        }*/
    }

    @FXML
    public void onclicked_semanahorario(ActionEvent event) throws SQLException {


        // UpdateTable();
        MenuItem clickedButton = (MenuItem) event.getSource();
        String id = clickedButton.getId();
        String [] idSplited1 = id.split("ss");
        String [] idSplited2 = idSplited1[1].split("h");
        int val = Integer.parseInt(idSplited2[0]);
        semana_selecionada=val;
        System.out.println(val);
        //splitSemestre(val);
    }

    public void inscritosPreenAux(){
        inscritosPreen();
        inscritosPreen();
    }

    public void inscritosPreen(){


        // Adiciona os dias da semana e seus números correspondentes ao HashMap
        HashMap<String, Integer> diasSemana = new HashMap<>();
            diasSemana.put("Seg", 1);
            diasSemana.put("Ter", 2);
            diasSemana.put("Qua", 3);
            diasSemana.put("Qui", 4);
            diasSemana.put("Sex", 5);
            diasSemana.put("Sab", 6);
            diasSemana.put("Dom", 7);


        addColumnOptionsSALA();
        conn = ConnectDb();
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select SalaAula, InscritosTurno, DiaSemana from horario order by DataAula");

                int aux=0;
                int auxAnt=0;
                int i = 0;
                int j = 1;
                boolean dom=false;
                boolean cont=true;
                while (rs.next()) {

                    if(i<1){
                        String salaAula = rs.getString("DiaSemana");
                        aux=diasSemana.get(salaAula);
                        auxAnt=diasSemana.get(salaAula);
                    }

                    String salaAula = rs.getString("SalaAula");
                    int inscritosTurno = rs.getInt("InscritosTurno");
                    String diaSemana = rs.getString("DiaSemana");

                        aux=diasSemana.get(diaSemana);

                        if(aux<auxAnt && cont){
                            j++;
                            cont=false;
                        }
                     if(aux>=auxAnt ){
                        cont=true;
                    }

                        ocupacao.put(salaAula + "-" + diaSemana + "-"+ j+"/" + i+ "-" ,inscritosTurno);
                        numSalas.add(salaAula);



                    i++;
                }


                numSalas = removerDuplicados(numSalas);


                /*for (Map.Entry<String, Integer> entry : ocupacao.entrySet()) {
                    String chave = entry.getKey();
                    Integer valor = entry.getValue();
                    System.out.println("Chave: " + chave + ", Valor: " + valor);
                }*/

                rs.close();
                stmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {

            }
        }

    }

    public List<String> removerDuplicados(List<String> listaComDuplicatas) {
        List<String> listaSemDuplicatas = new ArrayList<>();
        Set<String> elementos = new LinkedHashSet<>(listaComDuplicatas); // Usamos um conjunto para garantir a remoção de duplicatas

        for (String elemento : elementos) {
            listaSemDuplicatas.add(elemento);
        }
        return listaSemDuplicatas;
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



    public void startHeatMap(Stage primaryStage) {

        NUM_COLS = numSalas.size();

        List<String> keyList = new ArrayList<>(ocupacaoAux.keySet());

        List<String> salas = new ArrayList<>();


        Collections.sort(keyList, new Comparator<String>() {
            @Override
            public int compare(String key1, String key2) {
                // Extrair a sala de cada chave
                String sala1 = key1.split("-")[0];
                String sala2 = key2.split("-")[0];

                // Comparar as salas primeiro
                int salaComparison = sala1.compareTo(sala2);
                if (salaComparison != 0) {
                    return salaComparison;
                }

                // Se as salas forem iguais, compare os dias da semana
                String diaSemana1 = key1.split("-")[1];
                String diaSemana2 = key2.split("-")[1];
                return diaSemana1.compareTo(diaSemana2);
            }
        });


        for (String key : keyList) {
            sortedHashMap.put(key, ocupacaoAux.get(key));
        }

        for (Map.Entry<String, Integer> entry : sortedHashMap.entrySet()) {
            if(entry.getValue() > VAL_MAX)
                VAL_MAX = entry.getValue();

            String sala = entry.getKey().split("-")[0];
            salas.add(sala);
        }

        // Exibir o HashMap ordenado
        System.out.println("sortedHashMap");
        for (Map.Entry<String, Integer> entry : sortedHashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }


        Map<String, Integer> ocupacaoSalas; // Seu HashMap
        ocupacaoSalas = sortedHashMap;// Obtenha seu HashMap

        // Crie uma lista ordenada de dias da semana
        List<String> diasSemana = Arrays.asList("Dom", "Sab", "Sex", "Qui", "Qua", "Ter", "Seg");



        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(2);
        gridPane.setVgap(2);


        List<Integer> ocup = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : ocupacaoSalas.entrySet()) {
            ocup.add(entry.getValue());
        }
        System.out.println("ocupacaoSalas");
        System.out.println("ocupacaoSalas");
        System.out.println("ocupacaoSalas");
        System.out.println("ocupacaoSalas");
        for (Map.Entry<String, Integer> entry : ocupacaoSalas.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        salas = removerDuplicados(salas);

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                String sala = salas.get(col);
                if(salasPref.contains(sala)){
                    String semana= String.valueOf(semana_selecionada);
                    String diaSemana = diasSemana.get(row);

                    String chave = sala + "-" + diaSemana+ "-" +semana;
                    //System.out.println(chave);
                    int ocupacao = ocupacaoSalas.getOrDefault(chave, 0) * (3);
                    System.out.println(ocupacao);
                    Rectangle cell = new Rectangle(200, 100); // Tamanho da célula
                    cell.setFill(getColorFromValue(ocupacao)); // Cor com base na ocupação

                    Text text = new Text(String.valueOf(ocupacao / 3));
                    text.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    text.setFill(Color.BLACK);

                    // Posiciona o texto centralizado na célula
                    StackPane stackPane = new StackPane();
                    stackPane.getChildren().addAll(cell, text);
                    StackPane.setAlignment(text, Pos.CENTER);
                    gridPane.add(stackPane, col, row);
                }

            }


        }
        // Crie o heatmap com base nos dados de ocupação


        for (Map.Entry<String, Integer> entry : ocupacaoSalas.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        int larg=0;
        for (int col = 0; col < NUM_COLS; col++) {
            if(salasPref.contains(salas.get(col))) {
                Label salaLabel = new Label(salas.get(col)); // Obtenha o nome da sala
                salaLabel.setFont(Font.font(10));
                gridPane.add(salaLabel, col, NUM_ROWS);
                larg++;
            }// Adicione o rótulo abaixo do heatmap
        }

        for (int i = 0; i < NUM_ROWS; i++) {
            Label label = new Label(diasSemana.get(i)); // Crie uma label com o nome do dia da semana
            label.setPrefWidth(50); // Defina a largura da label
            label.setAlignment(Pos.CENTER_RIGHT); // Alinhe o texto à direita
            gridPane.add(label, NUM_COLS, i); // Adicione a label à cena
        }


        int largura = (larg*220)+100; // Largura desejad
        int altura = 800; // Altura desejada
        Scene scene = new Scene(gridPane, largura, altura); // Crie a cena com as dimensões desejadas
        primaryStage.setScene(scene);
        primaryStage.setTitle("Heatmap From HashMap Example");
        primaryStage.show();


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

    }

    private Color getColorFromValue(int ocupacao) {
        // Normalizar a ocupação dentro do intervalo 0-1

        double percentagem = (double) (ocupacao - VAL_MIN) / (VAL_MAX- VAL_MIN);

        // Calcular os componentes R (vermelho), G (verde) e B (azul) com base na porcentagem

        int red = (int)(255 * percentagem) ;
        if( red > 255 )
            red = 255;
        if( red < 0 )
            red = 0;

        int green = (int)(255 * (1 - percentagem));
        if(green < 0)
            green = 0;

        if(green > 255)
            green = 255;

        int blue = 100; // Valor fixo para o canal azul

        // Retornar a cor com base nos componentes calculados
        return Color.rgb(red, green, blue);
    }




}



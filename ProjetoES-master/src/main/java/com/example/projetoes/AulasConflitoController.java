package com.example.projetoes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.javafx.FxGraphRenderer;
import org.graphstream.ui.view.Viewer;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.projetoes.mysqlconnect.ConnectDb;


public class AulasConflitoController implements Initializable {
    private Connection conn;
    private ArrayList<String> ucs = new ArrayList<>();
    private HashMap<String, List<String>> aulasConflito;
    private List<String> cursos;
    private List<String> ucs1;
    private List<String> ucs2;
    Graph graph;

    @FXML
    private Menu cursoList;
    @FXML
    private Menu cursoList1;
    @FXML
    private Menu ucList;
    @FXML
    private Menu ucList1;
    @FXML
    Label labelCurso1;
    @FXML
    Label labelCurso2;
    @FXML
    private ScrollPane graphScrollPane;
    @FXML
    private MenuBar columnEditorUC1;
    @FXML
    private MenuBar columnEditorUC2;
    @FXML
    private MenuBar columnEditorCurso2;
    @FXML
    private MenuBar columnEditorCurso;
    @FXML
    private Button mostrarConflitos;
    @FXML
    private Button iniciar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        graph = new SingleGraph("Conflitos");
        System.out.println("Entrou no FXML");
        conn = ConnectDb();
        ucs1 = new ArrayList<String>();
        ucs2 = new ArrayList<String>();
        aulasConflito = new HashMap<String, List<String>>();


        addColumnOptionsCursos();
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void teste(){
        Graph graph = new SingleGraph("Graph");
        graph.addNode("1");
        graph.addNode("2");
        graph.addNode("3");

        graph.addEdge("1-->2","1","2", true);
        graph.addEdge("1-->3","1","3", true);


        FxViewer fxViewer = new FxViewer(graph, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        FxViewPanel viewPanel = (FxViewPanel) fxViewer.addDefaultView(false);
        viewPanel.getCamera().setViewPercent(5);

        graphScrollPane.setContent(viewPanel);

        graphScrollPane.setFitToWidth(true); // Allow the scroll pane to resize to the width
        graphScrollPane.setFitToHeight(true);

        fxViewer.enableAutoLayout();
    }

    private void addColumnOptionsCursos() {
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select distinct Curso from horario");

                List<String> tempCursos = new ArrayList<>();
                while (rs.next()) {
                    tempCursos.add(rs.getString("Curso"));
                }
                cursos = (ArrayList<String>) tempCursos;
                populateCursos();
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }




    private void populateCursos() {
        cursoList.getItems().clear();
        for (String curso : cursos) {
            MenuItem menuItem = new MenuItem(curso);
            cursoList.getItems().add(menuItem);
            menuItem.setOnAction(new EventHandler<>() {

                @Override
                public void handle(ActionEvent event) {
                    cursoList.setText(curso);
                    labelCurso1.setText("UC de " + curso);
                    labelCurso1.setVisible(true);
                    columnEditorCurso2.setVisible(true);

                    populateCursos2(curso);
                    columnEditorUC1.setVisible(true);
                    cursoList1.setText("Curso");

                    columnEditorUC2.setVisible(false);
                    labelCurso2.setVisible(false);
                    addColumnOptionsUC(curso, ucList);
                }
            });
        }
    }

    private void populateCursos2(String curso) {
        cursoList1.getItems().clear();
        List<String> cursos2 = new ArrayList<String>();
        cursos.forEach(x -> cursos2.add(x));
        cursos2.remove(curso);
        for (String curso2 : cursos2) {
            MenuItem menuItem = new MenuItem(curso2);
            cursoList1.getItems().add(menuItem);
            menuItem.setOnAction(new EventHandler<>() {

                @Override
                public void handle(ActionEvent event) {
                    cursoList1.setText(curso2);
                    labelCurso2.setText("UC de " + curso2);
                    labelCurso2.setVisible(true);
                    columnEditorUC2.setVisible(true);
                    addColumnOptionsUC(curso2, ucList1);
                }
            });
        }
    }


    private void addColumnOptionsUC(String curso, Menu menu) {
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select distinct UnidadeCurricular from horario WHERE Curso = '" + curso + "'");

                List<String> tempUCs = new ArrayList<>();
                while (rs.next()) {
                    tempUCs.add(rs.getString("UnidadeCurricular"));
                }

                populateUC(tempUCs, menu);
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

    private void populateUC(List<String> ucs, Menu menu) {
        menu.getItems().clear();
        for (String uc : ucs) {

            CheckBox cb = new CheckBox();
            cb.setText(uc);
            cb.setStyle("-fx-text-fill: black");
            cb.setSelected(false);
            cb.setOnAction(new EventHandler<>() {

                @Override
                public void handle(ActionEvent event) {
                    if(cb.isSelected()) {
                        if (menu.equals(ucList))
                            ucs1.add(uc);
                        else
                            ucs2.add(uc);
                    }
                    else{
                        if (menu.equals(ucList))
                            ucs1.remove(uc);
                        else
                            ucs2.remove(uc);
                    }
                }
            });

            CustomMenuItem item = new CustomMenuItem(cb);
            item.setHideOnClick(false);
            item.setText(uc);
            menu.getItems().add(item);
        }
    }

    @FXML
    private void calculateConflitos(){
        System.out.println("teste1");
        Graph aux = graph;
        graph.clear();

        if(aux.equals(graph))
            System.out.println("É igual");

        graph = new SingleGraph("Conflitos");

        for(String uc1: ucs1){
            for(String uc2: ucs2){
//                String query =
//                        "select h2.Curso, h2.UnidadeCurricular, h2.HoraInicio, h2.HoraFim, h2.DataAula" +
//                                "from horario h1 " +
//                                "JOIN horario h2 ON h1.DataAula = h2.DataAula AND h1.id < h2.id" +
//                                "WHERE h1.Curso = '" + cursoList.getText() + "' AND h2.Curso = '" + cursoList1.getText() +
//                                "' AND h1.UnidadeCurricular = '"+ uc1 + "' AND h2.UnidadeCurricular = '" + uc2 +
//                                "' AND ((h1.HoraInicio BETWEEN h2.HoraInicio AND h2.HoraFim) OR ( h1.HoraFIM BETWEEN h2.HoraInicio AND h2.HoraFim))";
                String query = "SELECT h1.Curso as Curso1, h1.UnidadeCurricular as UC1, h1.HoraInicio as HI1, h1.HoraFim as HF1, h1.DataAula as DataAula1, " +
                        "h2.Curso as Curso2, h2.UnidadeCurricular as UC2, h2.HoraInicio as HI2, h2.HoraFim as HF2, h2.DataAula as DataAula2 " +
                        "FROM horario h1 " +
                        "JOIN horario h2 ON h1.DataAula = h2.DataAula AND h1.id < h2.id " +
                        "WHERE h1.Curso = ? " +
                        "AND h2.Curso = ? " +
                        "AND h1.UnidadeCurricular = ? " +
                        "AND h2.UnidadeCurricular = ? " +
                        "AND ((h1.HoraInicio BETWEEN h2.HoraInicio AND h2.HoraFim) " +
                        "OR (h1.HoraFim BETWEEN h2.HoraInicio AND h2.HoraFim))";
                PreparedStatement ps = null;
                try {
                    ps = conn.prepareStatement(query);
                    ps.setString(1, cursoList.getText());
                    ps.setString(2, cursoList1.getText());
                    ps.setString(3, uc1);
                    ps.setString(4, uc2);
                    System.out.println("Prepared Query: " + ps.toString());
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        String aula1 = rs.getString("Curso1") + "," +
                                rs.getString("UC1") + "," +
                                rs.getString("HI1") + "," +
                                rs.getString("HF1") + "," +
                                rs.getString("DataAula1");
                        String aula2 = rs.getString("Curso2") + "," +
                                rs.getString("UC2") + "," +
                                rs.getString("HI2") + "," +
                                rs.getString("HF2") + "," +
                                rs.getString("DataAula2");
                        List<String> lst;
                        if(aulasConflito.containsKey(aula1)) {
                            lst = aulasConflito.get(aula1);
                        }
                        else {
                            lst = new ArrayList<String>();
                            graph.addNode(aula1);
                        }
                        lst.add(aula2);
                        aulasConflito.put(aula1, lst);
                    }


                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                finally {
                    if(ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
                }
            }
        }

        aulasConflito.forEach((key, list) -> {
                    list.forEach( x -> {
                        if(graph.getNode(x) != null){
                            graph.addEdge(key + "," + x, key, x);
                        }
                        else{
                            graph.addNode(x);
                            graph.addEdge(key + "," + x, key, x);
                        }
                    }
                    );
                }
                );



        FxViewer fxViewer = new FxViewer(graph, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        FxViewPanel viewPanel = (FxViewPanel) fxViewer.addDefaultView(false);
        viewPanel.getCamera().setViewPercent(5);

        graphScrollPane.setContent(viewPanel);

        graphScrollPane.setFitToWidth(true); // Allow the scroll pane to resize to the width
        graphScrollPane.setFitToHeight(true);

        fxViewer.enableAutoLayout();

        columnEditorCurso.setDisable(true);
        columnEditorCurso2.setDisable(true);
        columnEditorUC1.setDisable(true);
        columnEditorUC2.setDisable(true);
        mostrarConflitos.setDisable(true);
        iniciar.setDisable(true);
    }

}

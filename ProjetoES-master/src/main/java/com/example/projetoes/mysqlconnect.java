package com.example.projetoes;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class mysqlconnect {

    Connection conn = null;
    public static Connection ConnectDb(){
        try {

            System.out.println("acontece!!!!!!!!!!");
            String dataBaseName = "gestaohorario";
            String dataBaseURL = "jdbc:mysql://localhost:3307/";
            String dataUserName = "root";
            String dataUserPass = "sql123";
            String dataBaseConnParam = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false&AllowPublicKeyRetrieval=true";
            String dbUrl = dataBaseURL + dataBaseName + dataBaseConnParam;
            System.out.println(dbUrl);
            //Class.forName("com.mysql.jdbc.Driver");
            //Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/market","root","Lovania1");
            Connection conn = DriverManager.getConnection(dbUrl, dataUserName, dataUserPass);
            // JOptionPane.showMessageDialog(null, "Connection Established");
            return conn;
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
            return null;
        }

    }

    public static ObservableList<Caracteristica> getDatausersC(){
        Connection conn = ConnectDb();
        ObservableList<Caracteristica> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from caracterizacaosalas");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new Caracteristica(
                        rs.getInt("id"),
                        rs.getString("Edificio"),
                        rs.getString("NomeSala"),
                        rs.getInt("CapacidadeNormal"),
                        rs.getInt("CapacidadeExame"),
                        rs.getInt("noCaracteristicas"),
                        rs.getString("AnfiteatroAulas"),
                        rs.getString("ApoioTecnicoEventos"),
                        rs.getString("Arq1"),
                        rs.getString("Arq2"),
                        rs.getString("Arq3"),
                        rs.getString("Arq4"),
                        rs.getString("Arq5"),
                        rs.getString("Arq6"),
                        rs.getString("Arq9"),
                        rs.getString("BYOD"),
                        rs.getString("FocusGroup"),
                        rs.getString("HorarioVisivelPublico"),
                        rs.getString("LabArqCompI"),
                        rs.getString("LabArqCompII"),
                        rs.getString("LabBasesEng"),
                        rs.getString("LabEletronica"),
                        rs.getString("LabInformatica"),
                        rs.getString("LabJornalismo"),
                        rs.getString("LabRedesCompI"),
                        rs.getString("LabRedesCompII"),
                        rs.getString("LabTelecom"),
                        rs.getString("SalaAulasMest"),
                        rs.getString("SalaAulasMestPlus"),
                        rs.getString("SalaNEE"),
                        rs.getString("SalaProvas"),
                        rs.getString("SalaReuniao"),
                        rs.getString("SalaArquitetura"),
                        rs.getString("SalaAulasNormal"),
                        rs.getString("Videoconferencia"),
                        rs.getString("Atrio")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ObservableList<Horario> getDatausers(){
        Connection conn = ConnectDb();
        ObservableList<Horario> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from horario");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new Horario(
                        rs.getString("Curso"),
                        rs.getString("UnidadeCurricular"),
                        rs.getString("Turno"),
                        rs.getString("Turma"),
                        rs.getInt("InscritosTurno"),
                        rs.getString("DiaSemana"),
                        rs.getString("HoraInicio"),
                        rs.getString("HoraFim"),
                        rs.getString("DataAula"),
                        rs.getString("CaracteristicasSala"),
                        rs.getString("SalaAula")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }



    public static ObservableList<Horario> getSearch(String filter){
        Connection conn = ConnectDb();
        ObservableList<Horario> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from horario where SalaAula = " + "\'" + filter + "\'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new Horario(
                        rs.getString("Curso"),
                        rs.getString("UnidadeCurricular"),
                        rs.getString("Turno"),
                        rs.getString("Turma"),
                        rs.getInt("InscritosTurno"),
                        rs.getString("DiaSemana"),
                        rs.getString("HoraInicio"),
                        rs.getString("HoraFim"),
                        rs.getString("DataAula"),
                        rs.getString("CaracteristicasSala"),
                        rs.getString("SalaAula")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
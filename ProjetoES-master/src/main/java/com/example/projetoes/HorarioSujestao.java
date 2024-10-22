package com.example.projetoes;

public class HorarioSujestao {

    private String data;
    private String horaInicio;

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    private String horaFim;
    private String salaAula;

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    private String diaSemana;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getSalaAula() {
        return salaAula;
    }

    public void setSalaAula(String salaAula) {
        this.salaAula = salaAula;
    }

    public HorarioSujestao(String data, String horaInicio, String salaAula, String horaFim, String diasSemana) {
        this.diaSemana=diasSemana;
        this.data = data;
        this.horaInicio = horaInicio;
        //this.horaFim = horaFim;
        this.salaAula = salaAula;
        this.horaFim=horaFim;


    }
}

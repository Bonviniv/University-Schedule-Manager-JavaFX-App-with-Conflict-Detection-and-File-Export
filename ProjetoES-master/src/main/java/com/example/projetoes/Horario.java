package com.example.projetoes;

public class Horario {
    String curso;
    String unidadeCurricular;
    String turno;
    String turma;
    int inscrtosTurno;
    String diaSemana;
    String horaInicio;
    String horaFim;
    String dataAula;
    String caractSala;
    String salaAula;

    public Horario(String curso, String unidadeCurricular, String turno, String turma, int inscrtosTurno, String diaSemana, String horaInicio, String horaFim, String dataAula, String caractSala, String salaAula) {
        this.curso = curso;
        this.unidadeCurricular = unidadeCurricular;
        this.turno = turno;
        this.turma = turma;
        this.inscrtosTurno = inscrtosTurno;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.dataAula = dataAula;
        this.caractSala = caractSala;
        this.salaAula = salaAula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getUnidadeCurricular() {
        return unidadeCurricular;
    }

    public void setUnidadeCurricular(String unidadeCurricular) {
        this.unidadeCurricular = unidadeCurricular;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public int getInscrtosTurno() {
        return inscrtosTurno;
    }

    public void setInscrtosTurno(int inscrtosTurno) {
        this.inscrtosTurno = inscrtosTurno;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public String getDataAula() {
        return dataAula;
    }

    public void setDataAula(String dataAula) {
        this.dataAula = dataAula;
    }

    public String getCaractSala() {
        return caractSala;
    }

    public void setCaractSala(String caractSala) {
        this.caractSala = caractSala;
    }

    public String getSalaAula() {
        return salaAula;
    }

    public void setSalaAula(String salaAula) {
        this.salaAula = salaAula;
    }

    public void removerAsteriscos() {
        horaInicio = horaInicio.replace("*", "").trim();
        curso = curso.replace("*", "").trim();
        unidadeCurricular = unidadeCurricular.replace("*", "").trim();
        turno = turno.replace("*", "").trim();
        turma = turma.replace("*", "").trim();
        diaSemana = diaSemana.replace("*", "").trim();
        horaFim = horaFim.replace("*", "").trim();
        caractSala = caractSala.replace("*", "").trim();
        salaAula = salaAula.replace("*", "").trim();

    }

}

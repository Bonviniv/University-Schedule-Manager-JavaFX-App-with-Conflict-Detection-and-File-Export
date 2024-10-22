package com.example.projetoes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HorarioTest {

    public Horario horario = new Horario("ME", "Teoria dos Jogos e dos Contratos",
            "01789TP01", "MEA1", 10, "Sex", "13:00:00",
            "14:30:00", "02/12/2022", "Sala Aulas Mestrado", "AA2.25");


    @Test
    void getCurso() {
        assertEquals("ME", horario.getCurso());
    }

    @Test
    void getUnidadeCurricular() {
        assertEquals("Teoria dos Jogos e dos Contratos", horario.unidadeCurricular);
    }

    @Test
    void getTurno() {
        assertEquals("01789TP01", horario.getTurno());
    }

    @Test
    void getTurma() {
        assertEquals("MEA1", horario.getTurma());
    }


    @Test
    void getInscrtosTurno() {
        assertEquals(10, horario.getInscrtosTurno());
    }

    @Test
    void getDiaSemana() {
        assertEquals("Sex", horario.getDiaSemana());
    }

    @Test
    void getHoraInicio() {
        assertEquals("13:00:00", horario.getHoraInicio());
    }


    @Test
    void getHoraFim() {
        assertEquals("14:30:00", horario.getHoraFim());
    }

    @Test
    void getDataAula() {
        assertEquals("02/12/2022", horario.getDataAula());
    }


    @Test
    void getCaractSala() {
        assertEquals("Sala Aulas Mestrado", horario.getCaractSala());
    }

    @Test
    void getSalaAula() {
        assertEquals("AA2.25", horario.getSalaAula());
    }

}
package br.com.dio.model;

public enum GameStatus {

    NOT_STARTED ("Jogo não iniciado"),
    INCOMPLETE("Jogo incompleto"),
    FINISHED ("Jogo completo");


    private String label;

    GameStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

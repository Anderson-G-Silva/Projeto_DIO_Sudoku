package br.com.dio.UI.service;


import br.com.dio.model.Frame;
import br.com.dio.model.GameStatus;
import br.com.dio.model.Space;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static javax.swing.JOptionPane.QUESTION_MESSAGE;


//1ª etapa

public class FrameService {
    private static Random random = new Random();
    private final static int limit = 9;

    private Frame frame; // criação do frame para inicialização

    public FrameService(final Map<String, String> gameConfig) {
        this.frame = new Frame(initBoard(gameConfig)); // construtor que recebe um mapa com nome game config e cria o novo frame chamando o novo jogo (init)
    }
// Getter spaces
    public List<List<Space>> getSpaces(){
        return frame.getSpaces();
    }
// chama metodo reset
    public void reset(){
        frame.reset();
    }
// chama metodo has erros
    public boolean hasErrors(){
        return frame.hasErros();
    }
// chama metodo status
    public GameStatus status(){
        return frame.status();
    }
// chama metodo gameFinished
    public boolean gameFinished(){
        return frame.gameFinished();
    }
    // metodo de inicialização. Praticamente o mesmo da versão original alterando o position para gameConfig
    public List<List<Space>> initBoard(final Map<String, String> gameConfig) {

        String [] options = {"Fácil", "Médio", "Difícil"};
        var dialogResult1 = JOptionPane.showOptionDialog(
                null,
                "Qual o nível de dificuldade do jogo?",
                "Nível dificuldade",
                JOptionPane.YES_NO_CANCEL_OPTION,
                QUESTION_MESSAGE,
                null,
                options,
                options [0]
        );
        double answer = -1;
        if (dialogResult1 == 0) {
            answer = 0.8;
        } else if ( dialogResult1 == 1) {
            answer = 0.5;
        } else { answer = 0.35;}

        int var =  random.nextInt( 9 - 0);
        int varCol = random.nextInt(2 - 0);
        int varRow = random.nextInt(2 - 0);
        int changeI = 0;
        int changeJ = 0;
        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            if (i<3 && i+varCol>2){
                changeI = i+varCol-3;
            }
            else if (i<6 && i+varCol>5){
                changeI = i+varCol-3;
            }
            else if (i<9 && i+varCol>8){
                changeI = i+varCol-3;
            }
            else {
                changeI = i+varCol;
            }
            spaces.add(new ArrayList<>());
            for (int j = 0; j < limit; j++) {
                if (j<3 && j+varRow>2){
                    changeJ = j+varRow-3;
                }
                else if (j<6 && j+varRow>5){
                    changeJ = j+varRow-3;
                }
                else if (j<9 && j+varRow>8){
                    changeJ = j+varRow-3;
                }
                else {
                    changeJ = j+varRow;
                }
                var positionConfig = gameConfig.get("%s,%s".formatted(changeI, changeJ));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                if (expected+var>9){
                    expected = expected+var-9;
                }
                else {
                    expected = expected+var;
                }
                double probTrue = answer;
                double aleatory = Math.random();
                fixed = aleatory <=probTrue;
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }
        frame = new Frame(spaces);
        return spaces;
    }

}


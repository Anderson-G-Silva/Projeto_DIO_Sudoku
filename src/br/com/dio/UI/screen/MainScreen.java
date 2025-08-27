package br.com.dio.UI.screen;

import br.com.dio.model.Space;
import br.com.dio.UI.button.NewGameButton;
import br.com.dio.UI.button.CheckGameStatusButton;
import br.com.dio.UI.button.FinishGameButton;
import br.com.dio.UI.button.ResetButton;
import br.com.dio.UI.frame.MainFrame;
import br.com.dio.UI.input.NumberText;
import br.com.dio.UI.panel.MainPanel;
import br.com.dio.UI.panel.SudokuSector;
import br.com.dio.UI.service.FrameService;
import br.com.dio.UI.service.NotifierService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;


import static br.com.dio.UI.service.EventEnum.CLEAR_SPACE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600); // define a dimensão do frame

    private final FrameService frameService; // instancia o frameService
    private final NotifierService notifierService;

    private JButton checkGameStatusButton;
    private JButton finishGameButton;
    private JButton resetButton;
    private JButton newGameButton;



    public MainScreen(final Map<String, String> gameConfig) {
        this.frameService = new FrameService(gameConfig);// inicializa o frameService
        this.notifierService = new NotifierService();
    }
    // Metodo para criar o painel
    public void buildMainScreen(Map<String, String> gameConfig){
        JPanel mainPanel = new MainPanel(dimension); // cria novo frame
        JFrame mainFrame = new MainFrame(dimension, mainPanel); // cria novo panel
        for (int r = 0; r < 9; r+=3) {
            var endRow = r + 2;
            for (int c = 0; c < 9; c+=3) {
                var endCol = c + 2;
                var spaces = getSpacesFromSector(frameService.getSpaces(), c, endCol, r, endRow);
                JPanel sector = generateSection(spaces);
                mainPanel.add(sector);
            }
        }
        addNewGameButton(mainPanel,gameConfig );
        addResetButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        mainFrame.revalidate(); // atualiza a tela (refresh)
        mainFrame.repaint(); // atualiza a tela (refresh)
    }

    private List<Space> getSpacesFromSector(final List<List<Space>> spaces,
                                            final int initCol, final int endCol,
                                            final int initRow, final int endRow){
        List<Space> spaceSector = new ArrayList<>();
        for (int r = initRow; r <= endRow; r++) {
            for (int c = initCol; c <= endCol; c++) {
                spaceSector.add(spaces.get(c).get(r));
            }
        }
        return spaceSector;

    }

    private JPanel generateSection(final List<Space> spaces){
        List<NumberText> fields = new ArrayList<>(spaces.stream().map(NumberText::new).toList());
        fields.forEach(t -> notifierService.subscribe(CLEAR_SPACE, t));
        return new SudokuSector(fields);

    }

    private void addFinishGameButton(final JPanel mainPanel) {
        finishGameButton = new FinishGameButton(e -> {
            if (frameService.gameFinished()){
                showMessageDialog(null, "Parabéns você concluiu o jogo");
                resetButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            } else {
                var message = "Seu jogo tem alguma inconsistência, ajuste e tente novamente";
                showMessageDialog(null, message);
            }
        });
        mainPanel.add(finishGameButton);
    }

    private void addCheckGameStatusButton(final JPanel mainPanel) {
        checkGameStatusButton = new CheckGameStatusButton(e -> {
            var hasErrors = frameService.hasErrors();
            var gameStatus = frameService.status();
            var message = switch (gameStatus){
                case NOT_STARTED -> "O jogo não foi iniciado";
                case INCOMPLETE -> "O jogo está imcompleto";
                case FINISHED -> "O jogo está completo";
            };
            message += hasErrors ? " e contém erros" : " e não contém erros";
            showMessageDialog(null, message);
        });
        mainPanel.add(MainScreen.this.checkGameStatusButton);
    }

    private void addResetButton(final JPanel mainPanel) {
        resetButton = new ResetButton(e ->{
            // cria caixa de diálogo para confirmação
            var dialogResult = showConfirmDialog(
                    null, // centraliza o componente na tela
                    "Deseja realmente reiniciar o jogo?", // mensagem a ser exibida
                    "Limpar o jogo", // título a caixa de diálogo
                    YES_NO_OPTION,
                    QUESTION_MESSAGE
            );
            if (dialogResult == 0){ // 0 sim, 1 não e -1 botão de x que fecha a tela
                frameService.reset();
                notifierService.notify(CLEAR_SPACE);
            }
        });
        mainPanel.add(resetButton);
    }

    private void addNewGameButton (JPanel mainPanel, Map<String, String> gameConfig){

        newGameButton = new NewGameButton(e ->{
            // cria caixa de diálogo para confirmação
            var dialogResult = showConfirmDialog(
                    null, // centraliza o componente na tela
                    "Deseja iniciar um NOVO jogo?", // mensagem a ser exibida
                    "Novo jogo", // título a caixa de diálogo
                    YES_NO_OPTION,
                    QUESTION_MESSAGE
            );
            if (dialogResult == 0){ // 0 sim, 1 não e -1 botão de x que fecha a tela
                frameService.initBoard(gameConfig);
                buildMainScreen(gameConfig);


            }
        });
        mainPanel.add(newGameButton);
    }

}


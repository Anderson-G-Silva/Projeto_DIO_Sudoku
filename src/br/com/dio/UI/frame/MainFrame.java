package br.com.dio.UI.frame;

import javax.swing.*;
import java.awt.*;
// tela principal e dentro ficam os paineis
public class MainFrame extends JFrame {

    public MainFrame(final Dimension dimension, final JPanel mainPanel) {
        super("Sudoku");
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // parametriza o botão X
        this.setVisible(true);
        this.setLocationRelativeTo(null); // centraliza o painel na tela
        this.setResizable(false);
        this.add(mainPanel); // adiciona o mainPainel no frame
    }


}


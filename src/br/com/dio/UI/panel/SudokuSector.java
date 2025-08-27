package br.com.dio.UI.panel;

import br.com.dio.UI.input.NumberText;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.Dimension;
import java.awt.*;
import java.util.List;

import static java.awt.Color.black;
// são os 9 setores que tem na tela do jogo
public class SudokuSector extends JPanel {

    public SudokuSector(final List<NumberText> textFields){ // recebe a lista dos campos pelo numberText
        var dimension = new Dimension(170, 170); // dimensões dos componentes
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setBorder(new LineBorder(black, 2, true)); // define cor, espessura e arredondamento das bordas
        this.setVisible(true); // define que está visível
        textFields.forEach(this::add); // adiciona os campos do text fild no painel
    }

}


package br.com.dio.UI.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class NewGameButton extends JButton {

    public NewGameButton(final ActionListener actionListener){
        this.setText("Novo jogo");
        this.addActionListener(actionListener);
    }
}


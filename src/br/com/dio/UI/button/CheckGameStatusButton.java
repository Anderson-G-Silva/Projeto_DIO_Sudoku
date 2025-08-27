package br.com.dio.UI.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CheckGameStatusButton extends JButton {

    public CheckGameStatusButton(final ActionListener actionListener){
        this.setText("Verificar jogo"); // descrição que aparece no botão
        this.addActionListener(actionListener); // action listener é a ação que irá executar ao acionar o botão
    }

}

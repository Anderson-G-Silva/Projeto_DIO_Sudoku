package br.com.dio.UI.input;

import br.com.dio.model.Space;
import br.com.dio.UI.service.EventEnum;
import br.com.dio.UI.service.EventListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

import static br.com.dio.UI.service.EventEnum.CLEAR_SPACE;
import static java.awt.Font.PLAIN;

public class NumberText extends JTextField implements EventListener {

    private final Space space; // inicializa Space

    public NumberText(final Space space) {
        this.space = space;
        var dimension = new Dimension(50, 50); // dimensão de cada espaço
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setFont(new Font("Arial", PLAIN, 20));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new NumberTextLimit()); // inicializa a classe text limit
        this.setEnabled(!space.isFixed()); // habilita a edição somente dos campos não fixos
        if (space.isFixed()){
            this.setText(space.getReal().toString()); // busca os dados reais dos campos fixos
        }
        //propaga os valores pelo painel e cria os métodps para alterar
        this.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(final DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                changeSpace();
            }

            private void changeSpace(){
                if (getText().isEmpty()){
                    space.clearSpace(); // se limpa os dados na tela propaga para o space
                    return;
                }
                space.setReal(Integer.parseInt(getText()));
            }

        });
    }

    @Override
    public void update(final EventEnum eventType) {
        if (eventType.equals(CLEAR_SPACE) && (this.isEnabled())){
            this.setText("");
        }
    }
}


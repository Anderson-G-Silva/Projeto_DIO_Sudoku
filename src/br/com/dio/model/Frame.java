package br.com.dio.model;

import java.util.Collection;
import java.util.List;

import static br.com.dio.model.GameStatus.FINISHED;
import static br.com.dio.model.GameStatus.INCOMPLETE;
import static br.com.dio.model.GameStatus.NOT_STARTED;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Frame {

    private final List<List<Space>> spaces; // List externo colunas e list interno linhas

    public Frame(List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatus status(){
        if (spaces.stream().flatMap(Collection::stream).noneMatch(s-> nonNull(s.getReal()) && !s.isFixed())){
            return NOT_STARTED;
        } return spaces.stream().flatMap(Collection::stream).anyMatch(s-> isNull(s.getReal())) ? INCOMPLETE : FINISHED;
    }

    public boolean hasErros(){
        if (status() == NOT_STARTED){
            return false;
        }
        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(s-> nonNull(s.getReal()) && !s.getReal().equals(s.getExpected()));
    }



    public boolean changeValue(final int col, final int row, final int value){
        var space = spaces.get(col).get(row);
        if (space.isFixed() == true){return false;} else {
            space.setReal(value);
            return true;
        }
    }

    public boolean clearValue(final int col, final int row){
        var space = spaces.get(col).get(row);
        if (space.getReal() == null) {
            System.out.println("Campo vazio");return false;}
        else if (space.isFixed() == true){return false;}
        else {
            space.clearSpace();
            return true;}
    }

    public void reset(){
        spaces.stream().forEach(s-> s.forEach(Space::clearSpace));
    }

    public boolean gameFinished(){
        return !hasErros() && status().equals(FINISHED);
    }

}

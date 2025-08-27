package br.com.dio;

import br.com.dio.model.Frame;
import br.com.dio.model.Space;
import br.com.dio.usefull.FrameTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;


import static br.com.dio.model.GameStatus.NOT_STARTED;
import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    private static Frame frame;
    private static Random random = new Random();
    private final static int limit = 9;

    public static void main(String[] args) {
        final var positions = Stream.of(args)
                .collect(toMap(
                k-> k.split(";")[0], //define posíção zero do split que conterá os campos de posição (2  esquerda)
                v-> v.split(";")[1]  //define posíção um do split que conterá os campos de valores (2 a direita)
        ));

        int option = 0;
        while (option!=8){
            System.out.println("1 - Iniciar o jogo");
            System.out.println("2 - Inserir um número");
            System.out.println("3 - Apagar um número");
            System.out.println("4 - Visualizar o jogo");
            System.out.println("5 - Status do jogo");
            System.out.println("6 - Limpar o jogo");
            System.out.println("7 - Finalizar o jogo");
            System.out.println("8 - Sair do aplicativo");
            option = scanner.nextInt();
            switch (option) {
                case 1 -> startGamer(positions);
                case 2 -> insertNumber();
                case 3 -> deleteNumber();
                case 4 -> showGame();
                case 5 -> getStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida");
            }
        }
    }

    private static void startGamer(final Map <String, String> positions) {
        if (nonNull(frame)){
            System.out.println("Jogo iniciado");
        }
        System.out.println("Qual o nível de dificuldade do jogo (Facil, Médio ou Dificil)?");
        String result = scanner.next();
        while (!(result.equalsIgnoreCase("Facil") || result.equalsIgnoreCase( "Medio") || result.equalsIgnoreCase("dificil"))){
            System.out.println("opção incorreta");
            result = scanner.next();
        }

        int var =  random.nextInt( 9 - 0);
        int varRow = random.nextInt(2-0);
        int varCol = random.nextInt(2-0);
        int changeI = 0;
        int changeJ = 0;
        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < limit; i++){
            if (i<3 && i+varCol>2){changeI = i+varCol-3;
            }
            else if (i<6 && i+varCol>5){changeI = i+varCol-3;
            }
            else if (i<9 && i+varCol>8){changeI = i+varCol-3;
            }
            else {changeI = i+varCol;
            }
            spaces.add(new ArrayList<>());
            for (int j = 0; j< limit; j++){
                if (j<3 && j+varRow>2){changeJ = j+varRow-3;
                }
                else if (j<6 && j+varRow>5){changeJ = j+varRow-3;
                }
                else if (j<9 && j+varRow>8){changeJ = j+varRow-3;
                }
                else {changeJ = j+varRow;
                }
                var positionConfig = positions.get("%s,%s".formatted(changeI,changeJ));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                if (expected+var>9){
                    expected = expected+var-9;
                }
                else {
                    expected = expected+var;
                }
                double probTrue = 0;
                if(result.equalsIgnoreCase("Facil")){probTrue = 0.80;}
                else if(result.equalsIgnoreCase("Medio")){probTrue = 0.50;}
                else if(result.equalsIgnoreCase("Dificil")){probTrue = 0.35;}
                double aleatory = Math.random();
                fixed = aleatory <=probTrue;
                var currentSpace = new Space(expected,fixed);
                spaces.get(i).add(currentSpace);
            }
        }
        frame = new Frame(spaces);
        System.out.println("O Jogo está pronto para começar");
    }

    public static void insertNumber(){
        if (isNull(frame)){
            System.out.println("O Jogo ainda não foi iniciado");
            return;
        }
        System.out.println("Informe a linha para inserir o número");
        int row = runNumbers();
        System.out.println("Informe a coluna para inserir o número");
        int col = runNumbers();
        System.out.println("Informe o valor a ser inserido");
        int value = runNumbers();
        frame.changeValue(col, row,value);

    }

    public static void deleteNumber(){
        if (isNull(frame)){
            System.out.println("O Jogo ainda não foi iniciado");
            return;
        }
        System.out.println("Informe a linha para inserir o número");
        int row = runNumbers();
        System.out.println("Informe a coluna para inserir o número");
        int col = runNumbers();
        frame.clearValue(col, row);

    }
    public static void showGame(){
        if (isNull(frame)){
            System.out.println("O Jogo ainda não foi iniciado");
            return;
        }
        var args = new Object[81]; // cria objeto com 81 posições (9x9)
        var argPos = 0;
        for (int i = 0; i < limit; i++) { // pula linha a cada 9
            for (var col: frame.getSpaces()){ // passa pelo stream de espaços
                args[argPos ++] = " " + ((isNull(col.get(i).getReal())) ? " " : col.get(i).getReal()); //  se não tem dado preencho real insere espaço e caso contrário o real
            }
        }
        System.out.println("Seu jogo se encontra da seguinte forma");
        System.out.printf((FrameTemplate.frameTemplate) + "\n", args);

    }

    public static void getStatus(){
        if (isNull(frame)){
            System.out.println("O Jogo ainda não foi iniciado");
            return;
        }
        String message = "";
        if(frame.hasErros()){
            message = " - jogo contém erros";} else {message=" - jogo não contém erros";}
        System.out.printf("Status -> %s %s\n", frame.status().getLabel(),message);
    }

    public static void clearGame(){
        if (isNull(frame)){
            System.out.println("O Jogo ainda não foi iniciado");
            return;
        }
        if (frame.status() == NOT_STARTED){
            System.out.println(frame.status().getLabel());
            return;}
        String clear = "";
        System.out.println("Tem certeza que deseja limpar jogo?");

        while (!clear.equalsIgnoreCase("Sim") && !clear.equalsIgnoreCase("Não")){
            clear = scanner.next();
            System.out.println("digitar sim ou não");
        }
        if (clear.equalsIgnoreCase("não")){
            return;
        } else {frame.reset();}
    }
    public static void finishGame(){
        if (isNull(frame)){
            System.out.println("O Jogo ainda não foi iniciado");
            return;
        }
        if (frame.gameFinished() != true){
            getStatus();
        } else{
            System.out.println("Parabéns !! Jogo finalizado com sucesso!!");
            frame = null;
        }

    }

    public static int runNumbers(){
        int validNumber = scanner.nextInt();
        int min = 0;
        int max = limit-1;
        while (validNumber < min || validNumber > max){
            System.out.printf("Digite um número válido entre %d e %d", min, max);
            validNumber = scanner.nextInt();

        } return validNumber;

    }

}

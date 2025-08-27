package br.com.dio;

import java.util.stream.Stream;

import br.com.dio.UI.screen.MainScreen;

import static java.util.stream.Collectors.toMap;

public class UIMain {

    public static void main(String[] args) {
        var gameConfig = Stream.of(args)
                .collect(toMap(k -> k.split(";")[0], v -> v.split(";")[1]));
        var mainsScreen = new MainScreen(gameConfig);
        mainsScreen.buildMainScreen(gameConfig);
    }

}

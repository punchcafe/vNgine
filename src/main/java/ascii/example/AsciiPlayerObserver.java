package ascii.example;

import dev.punchcafe.vngine.player.PlayerObserver;

import java.util.List;
import java.util.Scanner;

public class AsciiPlayerObserver implements PlayerObserver {
    @Override
    public String getFromChoice(List<String> choices) {
        for(int i = 0; i < choices.size(); i++){
            System.out.println(String.format("%d : %s", i+1, choices.get(i)));
        }
        final int min = 1;
        final int max = choices.size();
        final int index = getInput(min, max) -1; //account for offset of list index
        return choices.get(index);
    }

    private int getInput(int min, int max){
        int choice = -1;
        try {
            Scanner scanner = new Scanner(System.in);
            final String input = scanner.nextLine();
            choice = Integer.parseInt(input);
            if(choice < min ||  choice > max){
                throw new RuntimeException();
            }
        } catch (RuntimeException ex){
            System.out.println("Please enter a valid number:");
            getInput(min, max);
        }
        return choice;
    }
}

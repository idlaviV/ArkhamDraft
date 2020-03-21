package arkhamDraft;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Gson gson = new Gson();
        File jsonCards = new File("data/cards.json");
        FileReader fileReader = new FileReader(jsonCards);
        CardBox masterCardBox = new CardBox(gson.fromJson(fileReader, Card[].class));
        Face face = new Face(masterCardBox);
        face.watch();
        masterCardBox.filter(Card.generateCardFilter("xp", Relator.equalRelator(),0));
        System.out.println("finished");
    }
}

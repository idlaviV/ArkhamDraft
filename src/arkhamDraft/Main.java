package arkhamDraft;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Gson gson = new Gson();
        File jsonCards = new File("data/cards.json");
        FileReader fileReader = new FileReader(jsonCards);
        CardBox cardBox = new CardBox(gson.fromJson(fileReader, Card[].class));
    }
}

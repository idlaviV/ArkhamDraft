package arkhamDraft;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Gson gson = new Gson();
        File jsonCards = new File("data/cards.json");
        FileReader fileReader = new FileReader(jsonCards);
        CardBox masterCardBox = new CardBox(gson.fromJson(fileReader, Card[].class));
        Face face = new Face(masterCardBox);
        face.watch();
        System.out.println("finished");
    }
}

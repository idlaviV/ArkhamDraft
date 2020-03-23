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
        File jsonPacks = new File("data/packs.json");
        FileReader fileReaderPacks = new FileReader(jsonPacks);
        PackManager packManager = new PackManager(gson.fromJson(fileReaderPacks, Pack[].class));
        Face face = new Face(masterCardBox, packManager);
        face.watch();
        System.out.println("finished");
    }
}

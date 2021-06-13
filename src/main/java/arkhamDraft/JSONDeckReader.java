package arkhamDraft;

import com.google.gson.Gson;
import jdk.management.resource.ResourceRequestDeniedException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JSONDeckReader {
    private final String id;
    private final MasterCardBox masterCardBox;
    private URL remote;

    public JSONDeckReader(String id, MasterCardBox masterCardBox) {
        this.id = id;
        this.masterCardBox = masterCardBox;
        constructURL();
    }

    public List<String> getCodes() {
        Gson gson = new Gson();
        Map<String, Double> idMap;
        BufferedReader reader = downloadJSON();
        Map<?, ?> map = gson.fromJson(reader, Map.class);
        idMap = (Map<String, Double>) map.get("slots");
        List<String> codeList = new ArrayList<>();
        for (String key : idMap.keySet()) {
            int n = idMap.get(key).intValue();
                for (int i = 0; i<n; i++) {
                    codeList.add(key);
                }
        }
        return codeList;
    }

    public List<Card> buildCardListFromJsonList() {
        List<Card> cardList = new ArrayList<>();
        List<String> codeList = getCodes();
        for (String cardCode : codeList) {
            Card card = masterCardBox.findCardByCode(cardCode);
            if (card != null) {
                cardList.add(card);
            }
        }
        return cardList;
    }

    private BufferedReader downloadJSON() {
        try {
            BufferedInputStream biStream = new BufferedInputStream(remote.openStream());
            biStream.mark(0);
            byte[] b = new byte[6];
            biStream.read(b, 0, 6);
            if (new String(b, StandardCharsets.UTF_8).equals("{\"id\":")) {
                biStream.reset();
                return new BufferedReader(new InputStreamReader(biStream));
            } else {
                throw wrongInputException(null);
            }
        } catch (IOException e) {
           throw wrongInputException(e);
        }
    }

    private RuntimeException wrongInputException(Exception e) {
        return new ResourceRequestDeniedException(String.format("ID %s could not be read.", id), e);
    }

    private void constructURL() {
        try {
            remote = new URL("https://arkhamdb.com/api/public/deck/" + id);
        } catch (MalformedURLException e) {
            throw new RuntimeException("URL to remote deck was illegal. Wrong id format?",e);
        }
    }
}

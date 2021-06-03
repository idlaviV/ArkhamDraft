package arkhamDraft;

import com.google.gson.Gson;
import jdk.management.resource.ResourceRequestDeniedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JSONDeckReader {
    private final String id;
    private final CardBox masterCardBox;
    private URL remote;

    public JSONDeckReader(String id, CardBox masterCardBox) {
        this.id = id;
        this.masterCardBox = masterCardBox;
        constructURL();
    }

    public List<String> getIDs() {
        Gson gson = new Gson();
        Map<?, ?> map = gson.fromJson(downloadJSON(), Map.class);
        Map<String, Double> idMap = (Map<String, Double>) map.get("slots");
        List<String> idList = new ArrayList<>();
        for (String key : idMap.keySet()) {
            int n = idMap.get(key).intValue();
            for (int i = 0; i<n; i++) {
                idList.add(key);
            }
        }
        return idList;
    }

    //TODO: implement buildDeckFromJsonList

    private BufferedReader downloadJSON() {
        try {
            return new BufferedReader(new InputStreamReader(remote.openStream()));
        } catch (IOException e) {
            throw new ResourceRequestDeniedException(String.format("ID %s could not be read.", id), e);
        }
    }

    private void constructURL() {
        try {
            remote = new URL("https://arkhamdb.com/api/public/deck/" + id);
        } catch (MalformedURLException e) {
            throw new RuntimeException("URL to remote deck was illegal. Wrong id format?",e);
        }
    }
}

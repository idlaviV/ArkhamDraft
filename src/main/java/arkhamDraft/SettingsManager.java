package arkhamDraft;


import com.google.gson.Gson;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class SettingsManager {

    private List<Pack> packs;
    private final ArrayList<Cycle> cycles = new ArrayList<>();
    private List<Pack> ownedPacks;
    private boolean secondCore;
    private boolean useOnlyRegularCards;
    private CardBox blackList;
    private static final File file = new File("./data/packs.txt");
    public static final File BLACK_LIST_FILE = new File("./data/blacklist.txt");

    public SettingsManager() {
    }

    public void updateDatabaseFromAPI() throws MalformedURLException {
        URL website = new URL("https://arkhamdb.com/api/public/cards/");
        try (InputStream in = website.openStream()) {
            Files.copy(in, Paths.get("data/cards.json"), StandardCopyOption.REPLACE_EXISTING);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return;
        }
        website = new URL("https://arkhamdb.com/api/public/packs/");
        try (InputStream in = website.openStream()) {
            Files.copy(in, Paths.get("data/packs.json"), StandardCopyOption.REPLACE_EXISTING);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public CardBox updateDatabaseFromJSON() throws IOException {
        Gson gson = new Gson();
        File jsonCards = new File("./data/cards.json");
        FileReader fileReader = new FileReader(jsonCards);
        CardBox masterCardBox = new CardBox(gson.fromJson(fileReader, Card[].class));
        fileReader.close();
        File jsonPacks = new File("./data/packs.json");
        FileReader fileReaderPacks = new FileReader(jsonPacks);
        this.packs = Arrays.asList(gson.fromJson(fileReaderPacks, Pack[].class));
        Collections.sort(packs);
        fileReaderPacks.close();
        buildCycles();
        return masterCardBox;
    }

    private CardBox getBlackList(CardBox masterCardBox) {
        if (blackList == null) {
            if (BLACK_LIST_FILE.exists()) {
                try {
                    blackList = new CardBox(BLACK_LIST_FILE, masterCardBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                blackList = new CardBox(Collections.emptySet());
            }
        }
        return blackList;
    }

    public boolean toggleRegular(boolean regular) {
        if (file.exists() && file.isFile()) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                file.delete();
                file.createNewFile();
                String contentNew = content.replaceAll("regular (false|true)\n",String.format("regular %b\n", regular));
                FileWriter fw = new FileWriter(file);
                fw.write(contentNew);
                fw.close();
                useOnlyRegularCards = regular;
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public CardBox getOwnedCards(CardBox masterCardBox) {
        Set<Card> cards = masterCardBox.getCards();
        Set<Card> ownedCards = new HashSet<>();
        CardBox realBlackList = getBlackList(masterCardBox);
        for (Card card : cards) {
            if (isThisPackOwned(card.getPack())) {
                if (!useOnlyRegularCards || card.isRegular()) {
                    if (!realBlackList.containsCard(card)) {
                        ownedCards.add(card);
                    }
                }
            }
        }
        return new CardBox(ownedCards);

    }

    private boolean isThisPackOwned(String packCode) {
        for (Pack pack : ownedPacks) {
            if (pack.getCode().equals(packCode)) return true;
        }
        return false;
    }

    public boolean setOwnedPacks(Scanner scanner) {
        List<Pack> newOwnedPacks = new ArrayList<>();
        System.out.println("Do you own all cycles? (y/n)");
        String input = scanner.nextLine();
        switch (input) {
            case "y":
                    newOwnedPacks.addAll(packs);
                    break;
                    default:
                    for (Cycle cycle : cycles) {
                        newOwnedPacks.addAll(isThisCycleOwned(scanner, cycle));
                    }
        }

            System.out.println("Do you own a second core? (y/n)");
            boolean answered = false;
            while (!answered) {
                input = scanner.nextLine();
                switch (input) {
                    case "y":
                        secondCore = true;
                        answered = true;
                        break;
                    case "n":
                        secondCore = false;
                        answered = true;
                    default:
                }
            }
            saveSettingsToFile();
            return true;
    }

    public void saveSettingsToFile()  {
        try {
            file.delete();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            for (Pack pack : ownedPacks) {
                fileWriter.write(pack.getCode() + "\n");
            }
            if (secondCore) {
                fileWriter.write("core2\n");
            }
            fileWriter.write(String.format("regular %b\n", useOnlyRegularCards));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Pack> isThisCycleOwned(Scanner scanner, Cycle cycle) {
        System.out.println(String.format("Do you own the cycle %s? 'y' for 'yes', 'n' for 'no', 'p' for 'partially'.", cycle.getName()));
        boolean answered = false;
        while (!answered) {
            String input = scanner.nextLine();
            switch (input) {
                case "n":
                    return new ArrayList<>();
                case "y":
                    return cycle.getPacks();
                case "p":
                    answered = true;
                default:
            }
        }
        /*p-case*/
        List<Pack> ownedPacks = new ArrayList<>();
        List<Pack> cyclePacks = cycle.getPacks();
        for (Pack pack : cyclePacks) {
            System.out.println(String.format("Do you own %s? (y/n)", pack.getName()));
            String input = scanner.nextLine();
            answered = false;
            while(!answered) {
                switch (input) {
                    case "y":
                        ownedPacks.add(pack);
                        answered = true;
                        break;
                    case "n":
                        answered = true;
                    default:
                }
            }
        }
        return ownedPacks;
    }

    public void updateSettings() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            ownedPacks = new ArrayList<>();
            secondCore = false;
            String line;
            while ((line = br.readLine()) != null) {
                for (Pack pack : packs) {
                    if (pack.getCode().equals(line)) {
                        ownedPacks.add(pack);
                    }
                }
                if (line.equals("core2")) {
                    secondCore = true;
                }
                if (line.equals("regular true")) useOnlyRegularCards = true;
                if (line.equals("regular false")) useOnlyRegularCards = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildCycles() {
        Map<Integer, List<Pack>> cycleMap = new LinkedHashMap<>();
        for (Pack pack: packs) {
            int i = pack.getCycle();
            if (cycleMap.containsKey(i)) {
                cycleMap.get(i).add(pack);
            } else {
                cycleMap.put(i, new ArrayList<>(Collections.singletonList(pack)));
            }
        }

        for (Integer i : cycleMap.keySet()) {
            Cycle cycle = new Cycle(i, getCycleName(cycleMap.get(i)), cycleMap.get(i));
            cycles.add(cycle);
        }
    }

    public String getCycleName(List<Pack> packs) {
        Pack pack = packs.get(0);
        int cycle = pack.getCycle();
        switch (cycle) {
            case 50:
                return "Return to...";
            case 60:
                return "Investigators";
            case 70:
                return "Standalone";
            case 80:
                return "Promo/Books";
            case 90:
                return "Parallel";
            default:
                return pack.getName();
        }
    }

    public boolean getSecondCore() {
        return secondCore;
    }

    public void generateDefaultSettings() throws IOException {
        file.delete();
        file.createNewFile();
        //String input = "core\ndwl\ntmm\ntece\nbota\nuau\nwda\nlitas\nptc\neotp\ntuo\napot\ntpm\nbsr\ndca\ntfa\ntof\ntbb\nhote\ntcoa\ntdoy\nsha\ntcu\ntsn\nwos\nfgg\nuad\nicc\nbbt\ntde\nsfk\ntsh\ndsm\npnr\nwgd\nwoc\nrtnotz\nrtdwl\nrtptc\ncotr\ncoh\nlol\nguardians\nhotel\nbooks\npromo\ncore2\nregular true\n";
        useOnlyRegularCards = true;
        secondCore = true;
        ownAllPacks();
    }

    private void ownAllPacks() {
        ownedPacks = new ArrayList<>(packs);
        saveSettingsToFile();
    }


    public boolean getRegularCardsFlag() {
        return useOnlyRegularCards;
    }

    public List<Cycle> getCycles() {
        return cycles;
    }

    public List<Pack> getOwnedPacks(){
        return ownedPacks;
    }

    public void setOwnedPacks(List<Pack> ownedPacks) {
        this.ownedPacks = ownedPacks;
    }

    public List<Pack> getPacks() {
        return packs;
    }

    public void toggleSecondCore(boolean secondCore) {
        this.secondCore = secondCore;
    }

    public void generateDefaultBlacklist() {
        try {
            Path copied = BLACK_LIST_FILE.toPath();

            Path originalPath = new File("src/main/resources/defaultBlacklist.txt").toPath();
            Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

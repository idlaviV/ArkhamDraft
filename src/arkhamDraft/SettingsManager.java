package arkhamDraft;

import com.google.gson.Gson;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class SettingsManager {

    private List<Pack> packs;
    private List<Pack> ownedPacks;
    private boolean secondCore;
    private boolean useOnlyRegularCards;

    public SettingsManager() {
    }

    public boolean updateDatabaseFromAPI() throws MalformedURLException {
        URL website = new URL("https://arkhamdb.com/api/public/cards/");
        try (InputStream in = website.openStream()) {
            Files.copy(in, Paths.get("data/cards.json"), StandardCopyOption.REPLACE_EXISTING);
        } catch (java.io.IOException e) {
            System.out.println(e);
            return false;
        }
        website = new URL("https://arkhamdb.com/api/public/packs/");
        try (InputStream in = website.openStream()) {
            Files.copy(in, Paths.get("data/packs.json"), StandardCopyOption.REPLACE_EXISTING);
        } catch (java.io.IOException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public CardBox updateDatabaseFromJSON() throws IOException {
        Gson gson = new Gson();
        File jsonCards = new File("data/cards.json");
        FileReader fileReader = new FileReader(jsonCards);
        CardBox masterCardBox = new CardBox(gson.fromJson(fileReader, Card[].class));
        fileReader.close();
        File jsonPacks = new File("data/packs.json");
        FileReader fileReaderPacks = new FileReader(jsonPacks);
        this.packs = Arrays.asList(gson.fromJson(fileReaderPacks, Pack[].class));
        Collections.sort(packs);
        fileReaderPacks.close();
        return masterCardBox;
    }

    public boolean toggleRegular(File file, boolean regular) {
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
                System.out.println(e);
            }
        }
        return false;
    }

    public CardBox getOwnedCards(CardBox masterCardBox) {
        Set<Card> cards = masterCardBox.getCards();
        Set<Card> ownedCards = new HashSet<>();
        for (Card card : cards) {
            if (isThisPackOwned(card.getPack())) {
                if (!useOnlyRegularCards || card.isRegular()) {
                    ownedCards.add(card);
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

    public boolean setOwnedPacks(Scanner scanner, File file) {
        if (file.exists() && file.isFile()) {
            List<Pack> newOwnedPacks = new ArrayList<>();
            try {
                file.delete();
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                System.out.println("Do you own all cycles? (y/n)");
                String input = scanner.next();
                switch (input) {
                    case "y":
                        newOwnedPacks.addAll(packs);
                        break;
                    default:
                        List<Integer> cycles = getCycles();
                        for (Integer cycle : cycles) {
                            newOwnedPacks.addAll(isThisCycleOwned(scanner, cycle));
                        }
                }
                for (Pack pack : newOwnedPacks) {
                    fileWriter.write(pack.getCode() + "\n");
                }
                System.out.println("Do you own a second core? (y/n)");
                boolean answered = false;
                while (!answered) {
                    input = scanner.next();
                    switch (input) {
                        case "y":
                            secondCore = true;
                            answered = true;
                            fileWriter.write("core2\n");
                            break;
                        case "n":
                            secondCore = false;
                            answered = true;
                        default:
                    }
                }
                fileWriter.write(String.format("regular %b\n", useOnlyRegularCards));
                fileWriter.close();
            } catch (IOException e) {
                return false;
            }
            updateSettings(file);
            return true;
        }
        return false;
    }

    private List<Pack> isThisCycleOwned(Scanner scanner, Integer cycle) {
        System.out.println(String.format("Do you own the cycle %s? 'y' for 'yes', 'n' for 'no', 'p' for 'partially'.",getCycleName(cycle)));
        boolean answered = false;
        while (!answered) {
            String input = scanner.next();
            switch (input) {
                case "n":
                    return new ArrayList<>();
                case "y":
                    return listCycle(cycle);
                case "p":
                    answered = true;
                default:
            }
        }
        /*p-case*/
        List<Pack> ownedPacks = new ArrayList<>();
        List<Pack> cyclePacks = listCycle(cycle);
        for (Pack pack : cyclePacks) {
            System.out.println(String.format("Do you own %s? (y/n)",pack.getName()));
            String input = scanner.next();
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

    public boolean updateSettings(File file) {
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
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }

    public List<Pack> listCycle(int cycle) {
        List<Pack> cyclePacks = new ArrayList<>();
        for (Pack pack : packs) {
            if (pack.getCycle() == cycle) {
                cyclePacks.add(pack);
            }
        }
        return cyclePacks;
    }

    public List<Integer> getCycles() {
        List<Integer> cycles = new ArrayList<>();
        for (Pack pack: packs) {
            if (!cycles.contains(pack.getCycle())) {
                cycles.add(pack.getCycle());
            }
        }
        return cycles;
    }

    public String getCycleName(int cycle) {
        switch (cycle) {
            case 50:
                return "Return to...";
            case 70:
                return "Standalone";
            case 80:
                return "Promo/Books";
            default:
                for (Pack pack : packs) {
                    if (pack.getCycle() == cycle && pack.getPosition() == 1) {
                        return pack.getName();
                    }
                }
        }
        return "";
    }

    public boolean getSecondCore() {
        return secondCore;
    }

    public static void generateDefaultSettings() throws IOException {
        File settings = new File("data/packs.txt");
        settings.delete();
        settings.createNewFile();
        String input = "core\ndwl\ntmm\ntece\nbota\nuau\nwda\nlitas\nptc\neotp\ntuo\napot\ntpm\nbsr\ndca\ntfa\ntof\ntbb\nhote\ntcoa\ntdoy\nsha\ntcu\ntsn\nwos\nfgg\nuad\nicc\nbbt\ntde\nsfk\ntsh\ndsm\npnr\nwgd\nwoc\nrtnotz\nrtdwl\nrtptc\ncotr\ncoh\nlol\nguardians\nhotel\nbooks\npromo\ncore2\nregular true\n";
        Files.copy(new ByteArrayInputStream(input.getBytes()), settings.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}

package arkhamDraft;

import java.io.*;
import java.util.*;

public class PackManager {

    private List<Pack> packs;
    private List<Pack> ownedPacks;
    private boolean secondCore;

    public PackManager(Pack[] packArray) {
        this.packs = Arrays.asList(packArray);
        Collections.sort(packs);
    }

    public CardBox getOwnedCards(CardBox masterCardBox) {
        Set<Card> cards = masterCardBox.getCards();
        Set<Card> ownedCards = new HashSet<>();
        for (Card card : cards) {
            if (isThisPackOwned(card.getPack())) {
                ownedCards.add(card);
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
                            fileWriter.write("core2");
                            break;
                        case "n":
                            secondCore = false;
                            answered = false;
                        default:
                    }
                }
                fileWriter.close();
            } catch (IOException e) {
                return false;
            }
            updateOwnedCards(file);
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

    public boolean updateOwnedCards(File file) {
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
            }
            return true;
        } catch (IOException e) {
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
}

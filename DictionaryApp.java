import java.util.Scanner;

public class DictionaryApp {

    private static final Dictionary dictionary = new Dictionary();

    public static void main(String[] args) {
        dictionary.loadWords();
        printMenu();
    }

    public static void printMenu() {
        System.out.println("\nPlease select:");
        System.out.println("1) Search word");
        System.out.println("2) Print history");
        System.out.println("3) Translate word");
        System.out.println("0) Exit");
        monitorRequest();
    }

    public static void monitorRequest() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String request = scanner.nextLine();
            if (!request.isEmpty()) {
                switch (request) {
                    case "1": searchWord();
                    case "2": printHistory();
                    case "3": translateWord();
                    case "0": System.exit(0);
                    default:
                        System.out.println("\nInvalid input. Please enter '1', '2', '3' or '0'.\n");
                        printMenu();
                }
            }
        }
    }

    public static void searchWord() {
        System.out.println("\nEnter the word you want to search:");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            if (!word.isEmpty()) {
                dictionary.search(word, "search");
                printMenu();
                break;
            }
        }
    }

    public static void translateWord() {
        System.out.println("\nEnter the word you want to translate:");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            if (!word.isEmpty()) {
                dictionary.translate(word);
                printMenu();
                break;
            }
        }
    }

    public static void printHistory() {
        dictionary.printHistory();
        printMenu();
    }
}

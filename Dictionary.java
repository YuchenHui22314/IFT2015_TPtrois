import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import ca.umontreal.maps.ChainHashMap;
import ca.umontreal.maps.Entry;

public class Dictionary {

    private StandardTries datas = new StandardTries();
    private ArrayList<String> history = new ArrayList<>();

    public void loadWords() {
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dictionary.csv"));
            while ((line = bufferedReader.readLine()) != null) {
                String[] infoTable = line.split(",");
                String word = infoTable[0];
                String firstChar = word.substring(0,1).toUpperCase();
                word = firstChar + word.substring(1);
                String otherInfo = "";
                for (int i = 1; i < infoTable.length; i++) {
                    otherInfo += infoTable[i];
                    if (i != infoTable.length - 1) otherInfo += ",";
                }
                datas.insert(word, otherInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param word word the user want to consult.
     * @param type translate/search
     */
    public void search(String word,String type) {
        ChainHashMap<String,DictionaryEntry> infos = datas.get(word);
        DictionaryEntry wordInfos = infos.get(word);
        // if the word is found
        if (wordInfos != null){
            System.out.println("The Word is found !\n");
            String result = generatePrint(wordInfos, type, word);
            System.out.println(result);
            result = (history.size()+1) + ". " + result;
            history.add(result);
        }else{
            // if the word is not found
            System.out.println("\nDidn't find the exact word.");
            int similarWordsNumber = infos.size();
            if (infos.size() == 0){ 
                System.out.println("There do not exists any similar word.");
                return;
            }
            System.out.println("There exists " + similarWordsNumber + 
            " similar words : \n");
            for (Entry<String, DictionaryEntry> element : infos.entrySet()) {
                String similarWord = element.getKey();
                DictionaryEntry similarWordInfos = element.getValue();
                String result = generatePrint(
                    similarWordInfos, type, similarWord);
                System.out.println(result);
            }
            System.out.println("There exists " + similarWordsNumber + 
            " similar words : \n");
        }
        // String wordInfo = datas.similarWords(word);
        // if (wordInfo != null) {
        //     history.add(word + "," + wordInfo);
        //     System.out.println(wordInfo);
        // } else {
        //     List<String> keySuggestions = datas.getKeySuggestions(word);
        //     List<String> valueSuggestions = datas.getValueSuggestions(word);
        //     System.out.println("\nDidn't find the exact word. But there are " + datas.getKeySuggestions(word).size() +
        //             " similar words:");
        //     for (int i = 0; i < keySuggestions.size(); i++) {
        //         System.out.println(keySuggestions.get(i));
        //         System.out.println(valueSuggestions.get(i));
        //     }
        // }
    }

    public void translate(String word) {
        search(word, "translate");
    }

    public void printHistory() {
        System.out.println("\n*** Searching history ***\n");
        if (history.size() == 0) System.out.println("(You haven't searched any word.)");
        for (int i = history.size() - 1; i >=0; i--) {
            System.out.println(history.get(i));
        }
        System.out.println("\n*** End of history ***");
    }

    private String generatePrint(
        DictionaryEntry wordInfos,
        String type,
        String word){

        String result = word + " : \n";
        if (type.equals("search")){
            result += "Type : " + wordInfos.getType() + "\n"; 
            result += "Meaning : " + wordInfos.getMeaning() + "\n";
        }else{
            result += "Translation : " + wordInfos.getTranslation() + "\n";
        }
        return result;
    }

}

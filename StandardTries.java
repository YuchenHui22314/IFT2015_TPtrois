import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Element;

import ca.umontreal.maps.ChainHashMap;
import ca.umontreal.maps.Entry;

/**
 * StandardTries
 * @author Yuchen Hui    and     Yuyang Xiong
 */
public class StandardTries {

    /**----------------------------------------------------------------
     * --                      inner class Node                      --
     * ----------------------------------------------------------------
     */
    protected static class Node {
        private DictionaryEntry dicEntry;
        private Node parent;
        private ChainHashMap<String,Node> hashChildrens;

        //getters
        public DictionaryEntry getEntry() {
            return this.dicEntry;
        }
        public ChainHashMap<String, Node> getChildrens() {
            return hashChildrens;
        }
        public Node getParent() {
            return parent;
        }
        

        //setters
        public void setDicEntry(DictionaryEntry dEntry) {
            this.dicEntry = dEntry;
        }
        //?
        public void setHashChildrens(ChainHashMap<String, Node> hashChildrens) {
            this.hashChildrens = hashChildrens;
        }
        public void setParent(Node parent) {
            this.parent = parent;
        }


        //constructor
        public Node (DictionaryEntry dEntry, Node parent){
            this.dicEntry = dEntry;
            this.parent = parent;
            this.hashChildrens = new ChainHashMap<>();
        }
    }
    /**----------------------------------------------------------------
     * --                  end of inner class Node                   --
     * ----------------------------------------------------------------
     */

    private Node root;

    //constructor : we do not store words and entries in 
    public StandardTries(){
        this.root = new Node(null, null);
    }

    public void insert (String word, String EntryInString){
        //
        String[] entryArray = EntryInString.split(","); 
        DictionaryEntry dicEntry = new DictionaryEntry(
            entryArray[0], 
            entryArray[1], 
            entryArray[2]); 
        
        // add a postfix not belong to alphbat to the end of each word
        word = word.concat("#"); 
        System.out.println(word);
        // search the place to insert and insert.
        insertPrivate(this.root, word, dicEntry);
        
        

    }

    private void insertPrivate(
        Node node, 
        String postfixWord, 
        DictionaryEntry dicEntry){

        //begin with fisrt character and move character by character 
        //forward to check if the character is already in the treis.
        //if it has already been in the tries, we move to the corresponding
        //node, else we add an pair <character,node> 
        //reserved to the character and then move to the node just added
        //when we get to the last character, we register the dictionaryEntry
        //which contains meanings, types and translations.
        
        while(postfixWord.length() > 0){
            // list for all possible succeeding characters 
            // belongs to words aleady exist.
            ChainHashMap<String,Node> childrens = node.getChildrens();
            // next character of the word
            String nextCharacter = postfixWord.substring(0,1);
            // entry in the list corresponding to the next character
            Node nextNode =childrens.get(nextCharacter);

            if (nextNode != null){ // prefix still matched
                node = nextNode;
            }else{                 // new branch
                childrens.put(nextCharacter, new Node(null,node));
                node = childrens.get(nextCharacter); 
            }
            postfixWord = postfixWord.substring(1);
        }
        //< "#", dicEntry>
        node.setDicEntry(dicEntry);
            


    }

    public ChainHashMap<String,DictionaryEntry> get(String word){
        return similarWords(this.root, word.concat("#"));
    }
    /**
     * this methode search find all similar words and their 
     * (meaning+type)/translation, or the word himself if the 
     * word exists in the dictionary.
     * @param node starting node of the search
     * @param postfixWord postfix part of the word (we delete the first
     * character each time we got to next node)
     * @return HashMap which contains all similar words and their 
     * (meaning+type)/translation, or the word himself if the 
     * word exists in the dictionary.
     */
    private ChainHashMap<String,DictionaryEntry> similarWords(
        Node node, 
        String postfixWord){
        
        ArrayList<String> longestPrefix = new ArrayList<>();
        ChainHashMap<String,DictionaryEntry> similarWords = 
            new ChainHashMap<>();
        Node finalNode = searchPlace(
            longestPrefix,
            node, 
            postfixWord);
        DictionaryEntry lastEntry = finalNode.getEntry();
        // 404 not found
        if (lastEntry == null){
            WordsBeginWithPrefix(
                similarWords, 
                finalNode, 
                StringListToString(longestPrefix));
            return similarWords;
           
        }
        // otherwise word is found, we just return the hashMap 
        // contains the word its self and information about it.
        String wordFound = StringListToString(longestPrefix); 
        similarWords.put(wordFound,lastEntry);
        return similarWords;
    }
    private void WordsBeginWithPrefix(
        ChainHashMap<String,DictionaryEntry> similarWords,
        Node beginNode,
        String longestPrefix){
        
        ChainHashMap<String,Node> childrens = 
            beginNode.getChildrens();
        // if begin node is the root, it means that we have
        // 0 word in dictionnary begin with the prefix.
        if (childrens.size() == 0){
            return;
        }
        //otherwise we look for all the words begin with longest prefix  
        //and add them into similar words.
        for (Entry<String,Node> element : childrens.entrySet()) {
            String character = element.getKey();
            Node nextNode = element.getValue();
            if (character.equals("#")){
                similarWords.put(
                    longestPrefix,nextNode.getEntry());
            }else{
                WordsBeginWithPrefix(
                    similarWords, 
                    nextNode, 
                    longestPrefix.concat(character));
            }

            
        }

        }
    /**
     * exemple transformation from ["a","p","p","l","e","#"] to "apple"
     * @param list ["a","p","p","l","e","#"]
     * @return  "apple"
     */
    private String StringListToString(ArrayList<String> list){
        String result = "";
        for (String character : list) {
            if (!character.equals("#"))
                result += character;
        }
        return result;
    }

    private Node searchPlace(
        ArrayList<String> longestPrefix, 
        Node node,
        String postfixWord){

        // found
        if (postfixWord.length() == 0){
            return node;
        }
        ChainHashMap<String,Node> childrens = node.getChildrens();
        String nextCharacter = postfixWord.substring(0,1);
        Node nextNode =childrens.get(nextCharacter);
        // not found
        if (nextNode == null){
            return node;
        } 
        // continue to search by recursive call
        longestPrefix.add(nextCharacter);
        return searchPlace(
            longestPrefix, 
            nextNode, 
            postfixWord.substring(1));
    } 
}
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import ca.umontreal.maps.ChainHashMap;
import ca.umontreal.maps.Entry;
import ca.umontreal.trees.AbstractTree;

/**
 * StandardTries
 */
public class StandardTries {

    /**----------------------------------------------------------------
     * --                      inner class node                      --
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
     * --                  end of inner class node                   --
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
        insert(this.root, word, dicEntry);
        
        

    }

    private void insert(
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
            while(postfixWord.length() >= 0){
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
        Node finalNode ;


    }

    private Map<Node,String> searchPlace(Node node, String postfixWord){
        return new HashMap<>();

    }
}
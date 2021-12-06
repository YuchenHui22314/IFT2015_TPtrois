import java.lang.reflect.Constructor;
import java.util.HashMap;

import ca.umontreal.maps.ChainHashMap;
import ca.umontreal.maps.Entry;
import ca.umontreal.trees.AbstractTree;

/**
 * StandardTries
 */
public class StandardTries {
    protected static class Node {
        private DictionaryEntry de;
        private Node parent;
        private ChainHashMap<String,Node> hashChildrens;

        //getters
        public DictionaryEntry getDe() {
            return de;
        }
        //cunyi!需要测试返回的是否为副本。忘了忘了。
        public ChainHashMap<String, Node> getHashChildrens() {
            return hashChildrens;
        }
        public Node getParent() {
            return parent;
        }
        

        //setters
        public void setDe(DictionaryEntry de) {
            this.de = de;
        }
        //?
        public void setHashChildrens(ChainHashMap<String, Node> hashChildrens) {
            this.hashChildrens = hashChildrens;
        }
        public void setParent(Node parent) {
            this.parent = parent;
        }


        //constructor
        public Node (DictionaryEntry de, Node parent){
            this.de = de;
            this.parent = parent;
            this.hashChildrens = new ChainHashMap<>();
        }
    }

}
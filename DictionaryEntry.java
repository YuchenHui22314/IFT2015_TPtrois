
/**
 * DictionaryEntry : to store the meaning/type/translation related to a word.
 */
public class DictionaryEntry {
    
    private String translation;
    private String type;
    private String meaning;

    public DictionaryEntry(String translation, String type, String meaning){
        this.translation = translation;
        this.type = type;
        this.meaning = meaning;
    }
    
    public String getTranslation() {
        return translation;
    }
    public String getType() {
        return type;
    }
    public String getMeaning() {
        return meaning;
    }
    public void setTranslation(String translation) {
        this.translation = translation;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString(){
        return ("[trans: " + this.translation + ", type: " 
        + this.type + ", meaning: " + this.meaning.substring(0,5) + "]");
    }
}
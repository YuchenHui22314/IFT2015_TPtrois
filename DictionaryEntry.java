
/**
 * DictionaryEntry
 */
public class DictionaryEntry {
    
    private String translation;
    private String meaning;
    private String type;
    
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
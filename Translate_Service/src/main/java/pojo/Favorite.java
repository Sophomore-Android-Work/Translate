package pojo;

public class Favorite {

    private String word;
    private String explain;
    private int id;

    public Favorite() {
    }

    public Favorite(String word, String explain, int id) {
        this.word = word;
        this.explain = explain;
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "word='" + word + '\'' +
                ", explain='" + explain + '\'' +
                ", id=" + id +
                '}';
    }
}

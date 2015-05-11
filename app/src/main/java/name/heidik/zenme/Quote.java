package name.heidik.zenme;

/**
 * Created by Haidy on 5/11/15.
 */
public class Quote {
    public String quote;
    public String author;

    @Override
    public String toString() {
        return quote + "\n" + "--" + author + "\n";
    }

}

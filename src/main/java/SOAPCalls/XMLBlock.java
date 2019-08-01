package SOAPCalls;

import java.util.ArrayList;

public class XMLBlock {

    private String title;
    private String xmlns;
    private String text;
    private ArrayList<XMLBlock> children;

    public XMLBlock(String title, String text, String xmlns) {
        this.title = title;
        this.text = text;
        this.xmlns = xmlns;
        this.children = new ArrayList<>();
    }
    public XMLBlock(String title, String text) { this(title, text, null); }
    public XMLBlock(String title) { this(title, null, null); }

    @Override
    public String toString() {
        String result = "<" + title;
        if (xmlns != null) {
            result += " xmlns=\"" + xmlns + "\">";
        } else {
            result += ">";
        }
        if (text != null) {
            result += text;
            result += "</" + title+ ">\n";
        } else {
            result += "\n";
            for (XMLBlock child : children) {
                result += child.toString();
            }
            result += "</" + title + ">\n";
        }
        return result;
    }

    public void addChild(XMLBlock child) {
        children.add(child);
    }
}

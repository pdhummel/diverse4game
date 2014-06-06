package pdh.diverse4game;

public class GamePieceAttribute {
    private String attributeName;
    private String attributeValue0;
    private String attributeValue1;

    public static final boolean VALUE0 = true;
    public static final boolean VALUE1 = false;  

    public GamePieceAttribute(String name, String value0, String value1) {
        this.attributeName = name;
        this.attributeValue0 = value0;
        this.attributeValue1 = value1;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getAttributeValue(boolean value) {
        String attribute = null;
        if (GamePieceAttribute.VALUE0 == value)
            attribute = attributeValue0;
        else
            attribute = attributeValue1;
        return attribute;    
    }
 
    public String getAttribute(boolean value) {
        StringBuffer sb = new StringBuffer();
        sb.append(getAttributeName());
        sb.append("=");
        sb.append(getAttributeValue(value));
        return sb.toString();
    }
    
    public String toString() {
        return this.getAttribute(true) + " " + this.getAttribute(false) + " ";
    }
    
}

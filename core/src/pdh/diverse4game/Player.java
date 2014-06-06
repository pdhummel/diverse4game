package pdh.diverse4game;

public class Player {
    private String name;
    private boolean computer;
    
    public Player() {
    }
    
    public Player(String name, boolean computer) {
        this.name = name;
        this.computer = computer;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean getComputer() {
        return computer;
    }
    
    public void setComputer(boolean computer) {
        this.computer = computer;
    }
    
    public boolean isComputer() {
        return computer;
    }
    
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Player:[");
        sb.append("name=" + name);
        sb.append(", computer=");
        if (computer)
            sb.append("yes");
        else
            sb.append("no");
        sb.append("]");
        return sb.toString();
    }
    
    
}

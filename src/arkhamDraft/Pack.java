package arkhamDraft;

public class Pack implements Comparable<Pack> {
    private String name;
    private String code;
    private Integer position;
    private Integer cycle_position;
    private Integer id;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Integer getPosition() {
        return position;
    }

    public Integer getCycle() {
        return cycle_position;
    }

    public Integer getOrder() {
        return cycle_position*10 + position;
    }

    @Override
    public int compareTo(Pack otherPack) {
        return this.getOrder() - otherPack.getOrder();
    }
}
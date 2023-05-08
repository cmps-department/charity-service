package cmps.charityservice.model;

public enum Category {
    ARMY(1),
    REFUGEES(2),
    ANIMALS(3),
    ELDERY(4),
    DISABLITY(5),
    ONKOLOGY(6),
    ORPHANS(7),
    HOMELESS(8),
    EKOORG(9),     //EKO ORGANIZATIONS
    COMMORG(10),    //COMMUNITY ORGANIZATIONS
    INTORG(11);     //INTERNATIONAL ORGANIZATIONS

    private final int id;

    Category(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

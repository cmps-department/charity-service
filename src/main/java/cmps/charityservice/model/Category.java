package cmps.charityservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    ARMY(1),
    REFUGEES(2),
    ANIMALS(3),
    ELDERLY(4),
    DISABILITY(5),
    ONCOLOGY(6),
    ORPHANS(7),
    HOMELESS(8),
    EKOORG(9),     //EKO ORGANIZATIONS
    COMMORG(10),    //COMMUNITY ORGANIZATIONS
    INTORG(11);     //INTERNATIONAL ORGANIZATIONS

    private final int id;
}

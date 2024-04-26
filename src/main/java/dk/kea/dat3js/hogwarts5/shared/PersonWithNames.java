package dk.kea.dat3js.hogwarts5.shared;

public interface PersonWithNames {
    String getFirstName();
    String getMiddleName();
    String getLastName();
    void setFirstName(String firstName);
    void setMiddleName(String middleName);
    void setLastName(String lastName);
//    String getFullName();
//    void setFullName(String fullName);

    default String getFullName() {
        return getFirstName() + " " + (getMiddleName() != null ? getMiddleName() + " " : "") + getLastName();
    }

    default void setFullName(String fullName) {
        if( fullName == null || fullName.isBlank() || fullName.isEmpty() ) {
            System.out.println("Invalid name");
            return;
        }
        String[] names = fullName.split(" ");
        if (names.length == 0 || names[0].isBlank() || names[names.length - 1].isBlank()) {
            System.out.println("Invalid name");
            return;
        }
        setFirstName(names[0]) ;
        if (names.length == 3) {
            setMiddleName(names[1]);
            setLastName(names[2]);
        } else if (names.length == 2) {
setMiddleName(null);
            setLastName(names[1]);
        } else if (names.length >= 4) {
            String middleName = names[1];
            for (int i = 2; i < names.length - 1; i++) {
                middleName += " " + names[i];
            }
            setMiddleName(middleName);
            setLastName(names[names.length - 1]);
        } else {
            setMiddleName(null);
            setLastName(null);
        }
    }
}

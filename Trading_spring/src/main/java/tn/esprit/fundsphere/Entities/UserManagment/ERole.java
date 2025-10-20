package tn.esprit.fundsphere.Entities.UserManagment;

public enum ERole {
    ADMIN,
    CLIENT;

    public static boolean equalsIgnoreCase(ERole role, String input) {
        return role.name().equalsIgnoreCase(input);
    }
}

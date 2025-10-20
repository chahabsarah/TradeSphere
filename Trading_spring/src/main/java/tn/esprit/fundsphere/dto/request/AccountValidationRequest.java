package tn.esprit.fundsphere.dto.request;


import tn.esprit.fundsphere.Entities.UserManagment.UserStatus;

public class AccountValidationRequest {
    private Long userId;
    private UserStatus status;

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}

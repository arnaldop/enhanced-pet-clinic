package sample.ui.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.data.annotation.Transient;

/**
 * A system user.
 */
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(unique = true)
    @NotEmpty
    private String userName;

    @Column
    @NotEmpty
    @Size(min=5)
    private String password;

    @Transient
    private String verifyPassword;

    @Column(unique = true)
    @NotEmpty
    @Email
    private String email;

    @Column
    @NotEmpty
    private String name;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile userProfile;

    public User() {
    }

    public User(String userName, String password, String name) {
        this.userName = userName;
        this.password = password;
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public String toString() {
        return "User(" + userName + ", " + email + ")";
    }

    public void validateCreateUser(ValidationContext context) {
        MessageContext messages = context.getMessageContext();
        if (!StringUtils.equals(password, verifyPassword)) {
            messages.addMessage(new MessageBuilder().error().source("password")
                    .source("verifyPassword").defaultText("Passwords must be the same.").build());
        }
    }
}

package apps.authentication.event;

import java.time.LocalDate;
import java.util.Date;

public class UserInfoFormEvent extends LoginFormEvent {
    private final String name;
    private final String lastName;
    private final String pass2;
    private final String email;
    private final boolean visibleEmail;
    private final String phone;
    private final boolean visiblePhone;
    private final String bio;
    private Date dateOfBirth;
    private final boolean visibleDate;
    private final String picPath;

    public UserInfoFormEvent(String username, String name, String lastName, String pass, String pass2, String email, boolean visibleEmail,
                             String phone, boolean visiblePhone, String bio, LocalDate dateOfBirth, boolean visibleDate, String picPath)
    {
        super(username , pass);
        this.name = name;
        this.lastName = lastName;
        this.pass2 = pass2;
        this.email = email;
        this.visibleEmail = visibleEmail;
        this.phone = phone;
        this.visiblePhone = visiblePhone;
        this.bio = bio;

        this.dateOfBirth = null;
        if(dateOfBirth != null)
            this.dateOfBirth = java.sql.Date.valueOf(dateOfBirth);

        this.visibleDate = visibleDate;
        this.picPath = picPath;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPass2() {
        return pass2;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBio() {
        return bio;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean isVisibleEmail() {
        return visibleEmail;
    }

    public boolean isVisiblePhone() {
        return visiblePhone;
    }

    public boolean isVisibleDate() {
        return visibleDate;
    }

    public String getPicPath() {
        return picPath;
    }
}

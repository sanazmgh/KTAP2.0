package apps.authentication.model;

import apps.authentication.event.UserInfoFormEvent;
import apps.page.messenger.models.Messenger;
import db.Context;
import models.Model;
import utilities.Config;

import java.io.File;
import java.util.Date;

public class User extends Model {

    private String name;
    private String lastName;
    private String username;
    private String password;
    private String bio;

    private String email;
    private boolean visibleEmail;

    private String phone;
    private boolean visiblePhone;

    private Date dateOfBirth;
    private boolean visibleDateOfBirth;

    private Date lastSeen;
    private int lastSeenMode;
    /***
     * 0 : nobody
     * 1 : everybody
     * 2 : followers
     */

    private boolean isActive;
    private boolean Private;

    private String imagePath;
    private static final String DEF_PIC = Config.getConfig("UploadedFilesDirectories").getProperty(String.class, "defaultImage");
    private final Profile profile;
    private final Messenger messenger;


    public User(String name, String lastName, String username, String password, String email )
    {
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isActive = true;
        this.imagePath = "";
        this.profile = new Profile();
        this.id = Context.getContext().users.lastID()+1;
        this.messenger = new Messenger(this);
    }

    public User(UserInfoFormEvent userInfoFormEvent) {
        this.name = userInfoFormEvent.getName();
        this.lastName = userInfoFormEvent.getLastName();
        this.username = userInfoFormEvent.getUsername();
        this.password = userInfoFormEvent.getPass();
        this.bio = userInfoFormEvent.getBio();
        this.email = userInfoFormEvent.getEmail();
        this.visibleEmail = userInfoFormEvent.isVisibleEmail();
        this.phone = userInfoFormEvent.getPhone();
        this.visiblePhone = userInfoFormEvent.isVisiblePhone();
        this.dateOfBirth = userInfoFormEvent.getDateOfBirth();
        this.visibleDateOfBirth = userInfoFormEvent.isVisibleDate();
        this.isActive = true;
        this.imagePath = "";
        this.profile = new Profile();
        this.id = Context.getContext().users.lastID()+1;//TODO add
        this.messenger = new Messenger(this);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVisibleEmail() {
        return visibleEmail;
    }

    public void setVisibleEmail(boolean visibleEmail) {
        this.visibleEmail = visibleEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isVisiblePhone() {
        return visiblePhone;
    }

    public void setVisiblePhone(boolean visiblePhone) {
        this.visiblePhone = visiblePhone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isVisibleDateOfBirth() {
        return visibleDateOfBirth;
    }

    public void setVisibleDateOfBirth(boolean visibleDateOfBirth) {
        this.visibleDateOfBirth = visibleDateOfBirth;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    public int getLastSeenMode() {
        return lastSeenMode;
    }

    public void setLastSeenMode(int lastSeenMode) {
        this.lastSeenMode = lastSeenMode;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isPrivate() {
        return Private;
    }

    public void setPrivate(boolean aPrivate) {
        Private = aPrivate;
    }

    public String getImagePath() {
        if(imagePath.equals(""))
            return DEF_PIC;

        File file = new File(imagePath);
        if(!file.exists())
            return DEF_PIC;

        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Profile getProfile() {
        return profile;
    }

    public Messenger getMessenger() {
        return messenger;
    }
}

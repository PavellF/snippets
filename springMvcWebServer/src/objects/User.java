package objects;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.google.gson.annotations.Expose;

import enums.Roles;
import internationalization.Locale;
/**
users
CREATE TABLE users(
id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(36) UNIQUE,
password VARCHAR(256) NOT NULL,
registration_date DATETIME NOT NULL DEFAULT NOW(),
email VARCHAR(64) NOT NULL UNIQUE,
role VARCHAR(36) NOT NULL DEFAULT 'user',
blocked BIT(1) DEFAULT 0,
about MEDIUMTEXT DEFAULT NULL,
UserPicURL VARCHAR(256),
popularity BIGINT UNSIGNED NOT NULL DEFAULT 0,
rating BIGINT UNSIGNED NOT NULL DEFAULT 0,
locale VARCHAR(32) NOT NULL DEFAULT 'EN'
);
*/

@SuppressWarnings("serial")
@Entity
@Table(name="users")
public class User implements Serializable{

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	private long id;
	
	@Expose
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Expose
	@Column(name = "REGISTRATION_DATE")
	private Date registrationDate;
	
	@Column(name = "email")
	private String email;
	
	@Expose
	@Column(name = "role")
	@Enumerated(value = EnumType.STRING)
	private Roles role;
	
	@Expose
	@Column(name = "blocked")
	private boolean blocked;
	
	@Expose
	@Column(name = "about", columnDefinition="clob")
	private String about;
	
	@Expose
	@Column(name =  "userPicURL")
	private String userPicURL;
	
	@Expose
	@Column(name = "popularity")
	private long popularity;
	
	@Expose
	@Column(name = "rating")
	private long rating;
	
	@Expose
	@Column(name = "locale")
	@Enumerated(value = EnumType.STRING)
	private Locale locale;
	
	public User(){}
	
	public User(long id){
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getUserPicURL() {
		return userPicURL;
	}

	public void setUserPicURL(String userPicURL) {
		this.userPicURL = userPicURL;
	}

	public long getPopularity() {
		return popularity;
	}

	public void setPopularity(long popularity) {
		this.popularity = popularity;
	}

	public long getRating() {
		return rating;
	}

	public void setRating(long rating) {
		this.rating = rating;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((about == null) ? 0 : about.hashCode());
		result = prime * result + (blocked ? 1231 : 1237);
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + (int) (popularity ^ (popularity >>> 32));
		result = prime * result + (int) (rating ^ (rating >>> 32));
		result = prime * result + ((registrationDate == null) ? 0 : registrationDate.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((userPicURL == null) ? 0 : userPicURL.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (about == null) {
			if (other.about != null)
				return false;
		} else if (!about.equals(other.about))
			return false;
		if (blocked != other.blocked)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (locale != other.locale)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (popularity != other.popularity)
			return false;
		if (rating != other.rating)
			return false;
		if (registrationDate == null) {
			if (other.registrationDate != null)
				return false;
		} else if (!registrationDate.equals(other.registrationDate))
			return false;
		if (role != other.role)
			return false;
		if (userPicURL == null) {
			if (other.userPicURL != null)
				return false;
		} else if (!userPicURL.equals(other.userPicURL))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", registrationDate=");
		builder.append(registrationDate);
		builder.append(", email=");
		builder.append(email);
		builder.append(", role=");
		builder.append(role);
		builder.append(", blocked=");
		builder.append(blocked);
		builder.append(", about=");
		builder.append(about);
		builder.append(", userPicURL=");
		builder.append(userPicURL);
		builder.append(", popularity=");
		builder.append(popularity);
		builder.append(", rating=");
		builder.append(rating);
		builder.append(", locale=");
		builder.append(locale);
		builder.append("]");
		return builder.toString();
	}
	
	
}

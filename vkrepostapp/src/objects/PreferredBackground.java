package objects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PreferredBackground implements Serializable{

	private String preferredBackground;

	public String getPreferredBackground() {
		return preferredBackground;
	}

	public void setPreferredBackground(String preferredBackground) {
		this.preferredBackground = preferredBackground;
	}
}

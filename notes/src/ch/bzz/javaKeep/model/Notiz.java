package ch.bzz.javaKeep.model;

import javax.ws.rs.FormParam;
import java.util.Date;
import javax.validation.constraints.*;

/**
 * model for Notizen
 * <p>
 * M133: Restful Webservice
 *
 * @author Aladin Boudouda
 */

public class Notiz {
    @FormParam("title")
    @NotEmpty
    @Size(min = 2, max = 40)
    private String titel;

    @FormParam("inhalt")
    @NotEmpty
    @Size(min = 2, max = 100)
    private String inhalt;

    @FormParam("date")
    @NotEmpty
    @Pattern(regexp = "(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})")
    private String datum;

    @FormParam("color")
    @NotEmpty
    private String color;

    @FormParam("notizUUID")
    @NotEmpty
    @Pattern(regexp = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$")
    private String notizUUID;
    private Author author;

    /**
     * Gets the title
     *
     * @return value of title
     */
    public String getTitel() {
        return titel;
    }

    /**
     * Sets the title
     *
     * @param titel the value to set
     */

    public void setTitel(String titel) {
        this.titel = titel;
    }

    /**
     * Gets the inhalt
     *
     * @return value of inhalt
     */
    public String getInhalt() {
        return inhalt;
    }

    /**
     * Sets the inhalt
     *
     * @param inhalt the value to set
     */

    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }

    /**
     * Gets the datum
     *
     * @return value of datum
     */
    public String getDatum() {
        return datum;
    }

    /**
     * Sets the datum
     *
     * @param datum the value to set
     */

    public void setDatum(String datum) {
        this.datum = datum;
    }

    /**
     * Gets the color
     *
     * @return value of color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color
     *
     * @param color the value to set
     */

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets the notizID
     *
     * @return value of notizID
     */
    public String getNotizUUID() {
        return notizUUID;
    }

    /**
     * Sets the notizID
     *
     * @param notizUUID the value to set
     */

    public void setNotizUUID(String notizUUID) {
        this.notizUUID = notizUUID;
    }

    /**
     * Gets the author
     *
     * @return value of author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets the author
     *
     * @param author the value to set
     */

    public void setAuthor(Author author) {
        this.author = author;
    }
}

package ch.bzz.javaKeep.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

/**
 * model for authors
 * <p>
 * M133: Restful Webservice
 *
 * @author Aladin Boudouda
 */
public class Author {
    @FormParam("authorUUID")
    @Pattern(regexp = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$")
    @NotEmpty
    private String authorUUID;

    @FormParam("author")
    @NotEmpty
    @Size(min = 2, max = 40)
    private String author;

    /**
     * Gets the authorUUID
     *
     * @return value of authorUUID
     */
    public String getAuthorUUID() {
        return authorUUID;
    }

    /**
     * Sets the authorUUID
     *
     * @param authorUUID the value to set
     */

    public void setAuthorUUID(String authorUUID) {
        this.authorUUID = authorUUID;
    }

    /**
     * Gets the author
     *
     * @return value of author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author
     *
     * @param author the value to set
     */

    public void setAuthor(String author) {
        this.author = author;
    }
}

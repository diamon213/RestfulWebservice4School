package ch.bzz.javaKeep.data;

import ch.bzz.javaKeep.model.Author;
import ch.bzz.javaKeep.model.Notiz;
import ch.bzz.javaKeep.model.User;
import ch.bzz.javaKeep.service.Config;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * datahandler for managing csv files
 * <p>
 * M133: Restful Webservice
 *
 * @author Aladin Boudouda
 */

public class DataHandler {
    private static final DataHandler instance = new DataHandler();
    private static Map<String, Notiz> notizMap = new HashMap<>();
    private static Map<String, Author> authorMap = new HashMap<>();

    /**
     * default constructor: defeat instantiation
     */
    private DataHandler() {
    }

    /**
     * @return the instance of this class
     */
    public static DataHandler getInstance() {
        return DataHandler.instance;
    }

    /**
     * reads data from csv files to the notizMap
     */
    private static void readNotizen() {

        BufferedReader bufferedReader;
        FileReader fileReader;
        try {
            String notizPath = Config.getProperty("notizFile");
            fileReader = new FileReader(notizPath);
            bufferedReader = new BufferedReader(fileReader);

        } catch (FileNotFoundException fileEx) {
            fileEx.printStackTrace();
            throw new RuntimeException();
        }

        try {
            String line;
            Notiz notiz;
            while ((line = bufferedReader.readLine()) != null) {
                notiz = new Notiz();
                String[] values = line.split(";");
                notiz.setNotizUUID(values[0]);
                notiz.setTitel(values[1]);
                notiz.setInhalt((values[2]));
                notiz.setColor(values[3]);
                notiz.setDatum(values[4]);
                Author author = getAuthorMap().get(values[5]);
                notiz.setAuthor(author);

                notizMap.put(values[0], notiz);
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
                throw new RuntimeException();
            }
        }
    }



    /**
     * csv file gets updated with the notizMap
     *
     * @param notizMap map of all Notizen
     */
    public static void writeNotizen(Map<String, Notiz> notizMap) {
        Writer writer = null;
        FileOutputStream fileOutputStream = null;

        try {
            String path = Config.getProperty("notizFile");
            fileOutputStream = new FileOutputStream(path);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "utf-8"));

            for (Map.Entry<String, Notiz> notizEntry : notizMap.entrySet()) {
                Notiz notiz = notizEntry.getValue();
                String contents = String.join(";",
                        notiz.getNotizUUID(),
                        notiz.getTitel(),
                        notiz.getInhalt(),
                        notiz.getAuthor().getAuthorUUID(),
                        notiz.getDatum(),
                        notiz.getColor()
                );
                writer.write(contents + '\n');
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException();

        } finally {

            try {
                if (writer != null) {
                    writer.close();
                }

                if (fileOutputStream != null) {
                    fileOutputStream.close();

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * csv file gets put into authorMap
     */
    private static void readAuthors() {
        BufferedReader bufferedReader;
        FileReader fileReader;
        try {
            String authorPath = Config.getProperty("authorFile");
            fileReader = new FileReader(authorPath);
            bufferedReader = new BufferedReader(fileReader);

        } catch (FileNotFoundException fileEx) {
            fileEx.printStackTrace();
            throw new RuntimeException();
        }

        try {
            String line;
            Author author;
            while ((line = bufferedReader.readLine()) != null) {
                author = new Author();
                String[] values = line.split(";");
                author.setAuthorUUID(values[0]);
                author.setAuthor(values[1]);

                authorMap.put(values[0], author);
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
                throw new RuntimeException();
            }
        }
    }
    /**
     * reads a user from username and password
     *
     * @param username the username
     * @param password the password
     * @return a user-object
     */
    public static User readUser(String username, String password) {
        BufferedReader bufferedReader;
        FileReader fileReader;
        try {
            String path = Config.getProperty("userFile");
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);

        } catch (FileNotFoundException fileEx) {
            fileEx.printStackTrace();
            throw new RuntimeException();
        }

        User user = new User();
        try {
            String line;

            while ((line = bufferedReader.readLine()) != null &&
                    user.getUserRole().equals("guest")) {

                String[] values = line.split(";");
                if (username.equals(values[0]) &&
                        password.equals(values[3])) {
                    user.setUsername(values[0]);
                    user.setUserRole(values[2]);
                }
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
                throw new RuntimeException();
            }
        }
        return user;
    }

    /**
     * csv file gets updated  with authorMap
     *
     * @param authorMap map with all the authors
     */
    public static void writeAuthors(Map<String, Author> authorMap) {
        Writer writer = null;
        FileOutputStream fileOutputStream = null;

        try {
            String path = Config.getProperty("authorFile");
            fileOutputStream = new FileOutputStream(path);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "utf-8"));

            for (Map.Entry<String, Author> authorEntry : authorMap.entrySet()) {
                Author author = authorEntry.getValue();
                String contents = String.join(";",
                        author.getAuthorUUID(),
                        author.getAuthor()
                );
                writer.write(contents + '\n');
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException();

        } finally {

            try {
                if (writer != null) {
                    writer.close();
                }

                if (fileOutputStream != null) {
                    fileOutputStream.close();

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * Gets the authorMap
     *
     * @return value of authorMap
     */
    public static Map<String, Notiz> getNotizMap() {
        if (notizMap.isEmpty()) {
            readNotizen();
        }
        return notizMap;
    }

    /**
     * Sets the notizMap
     *
     * @param notizMap the value to set
     */

    public static void setNotizMap(Map<String, Notiz> notizMap) {
        DataHandler.notizMap = notizMap;
    }

    /**
     * Gets the authorMap
     *
     * @return value of authorMap
     */
    public static Map<String, Author> getAuthorMap() {
        if (authorMap.isEmpty()) {
            readAuthors();
        }
        return authorMap;
    }

    /**
     * Sets the authorMap
     *
     * @param authorMap the value to set
     */

    public static void setAuthorMap(Map<String, Author> authorMap) {
        DataHandler.authorMap = authorMap;
    }


}
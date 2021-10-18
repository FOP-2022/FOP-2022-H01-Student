package h01;

import fopbot.*;
import h01.misc.PropertyConverter;
import h01.misc.PropertyException;

import java.io.*;

/**
 * Task 1 of Hausuebung01 of "Funktionale und objektorientierte
 * Programmierkonzepte" WS 21/22. In this task students have to add
 * functionality to certain {@code Robot}s according to the sheets presented in
 * the lecture. This class initializes the world which has to be used and
 * presents which parts of code have to be changed. Depending on whether this is
 * the solution or the template the doc and included code might differ.
 * <p>
 * If this is the template do ONLY modify parts of the code which are commented
 * to be modified.
 *
 * @author Thomas Rothenbaecher
 */

public class Task1 {

  private static final PropertyConverter<Integer> TO_INTEGER = s -> {
    try {
      return Integer.parseInt(s);
    } catch (NumberFormatException e) {
      throw new PropertyException("Die Property kann nicht als Ganzzahl interpretiert werden.", e);
    }
  };

  private static final String FOPBOT_PROPERTIES = "fopbot.properties";

  public static class RookAndBishop {
    private final int NUMBER_OF_ROWS;
    private final int NUMBER_OF_COLUMNS;
    private final int nextFrameDelay;
    private final boolean uiVisible;

    public RookAndBishop(int rows, int columns, int nextFrameDelay, boolean uiVisible) {
      this.nextFrameDelay = nextFrameDelay;
      this.uiVisible = uiVisible;
      this.NUMBER_OF_ROWS = rows > 0 ? rows : readProperty("NUMBER_OF_ROWS", TO_INTEGER);
      this.NUMBER_OF_COLUMNS = columns > 0 ? columns : readProperty("NUMBER_OF_COLUMNS", TO_INTEGER);
    }

    public RookAndBishop(int nextFrameDelay, boolean uiVisible) {
      this(-1, -1, nextFrameDelay, uiVisible);
    }

    public RookAndBishop() {
      this(20, true);
    }

    public void execute() {
      initializeTask(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS, nextFrameDelay, uiVisible);
      // Hier programmieren
    }

  }
  /**
   * Method which makes Java code runnable as a program. For this task the main
   * method handles initialization and preparation of the world and anything
   * needed necessary to finish this task.
   *
   * @param args generic arguments needed by definition to run a program
   */
  public static void main(String[] args) {
    var environment = new RookAndBishop();
    environment.execute();
  }

  private static void initializeTask(int numberOfRows, int numberOfColumns, int delay, boolean uiVisible) {
    World.setSize(numberOfColumns, numberOfRows);
    World.setVisible(uiVisible);
    if (delay < 0) {
      delay = 100;
    }
    World.setDelay(delay);
  }

  private static <T> T readProperty(String key, PropertyConverter<T> converter) {
    String value = null;
    var loader = Task1.class.getClassLoader();
    try (InputStream inputStream = loader.getResourceAsStream(FOPBOT_PROPERTIES)) {
      if (inputStream == null) {
        throw new PropertyException(String.format("Die Property-Datei mit Namen %s " +
          "konnte nicht gefunden werden.", FOPBOT_PROPERTIES));
      }
      try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
           BufferedReader br = new BufferedReader(inputStreamReader)) {
        String line = null;
        while ((line = br.readLine()) != null) {
          if (line.startsWith(key)) {
            var split = line.split("=");
            try {
              value = split[1];
            } catch (ArrayIndexOutOfBoundsException e) {
              throw new PropertyException(String.format("Die Zeile für Schlüssel %s enthält kein '='", key), e);
            }
            break;
          }
        }
      }
    } catch (IOException e) {
      throw new PropertyException("Die Property-Datei konnte nicht gelesen werden.", e);
    }
    if (value == null) {
      throw new PropertyException(String.format("Der gesuchte Schlüssel %s konnte in der Datei nicht gefunden werden.",
        key));
    }
    return converter.convert(value);
  }
}

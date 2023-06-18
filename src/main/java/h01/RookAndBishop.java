package h01;

import fopbot.Direction;
import fopbot.Robot;

import java.util.concurrent.ThreadLocalRandom;

public class RookAndBishop {
  private final int NUMBER_OF_ROWS;
  private final int NUMBER_OF_COLUMNS;
  private final int nextFrameDelay;
  private final boolean uiVisible;

  public RookAndBishop(int rows, int columns, int nextFrameDelay, boolean uiVisible) {
    this.nextFrameDelay = nextFrameDelay;
    this.uiVisible = uiVisible;
    this.NUMBER_OF_ROWS = rows > 0 ? rows : Task1.readProperty("NUMBER_OF_ROWS", Task1.TO_INTEGER);
    this.NUMBER_OF_COLUMNS = columns > 0 ? columns : Task1.readProperty("NUMBER_OF_COLUMNS", Task1.TO_INTEGER);
  }

  public RookAndBishop(int nextFrameDelay, boolean uiVisible) {
    this(-1, -1, nextFrameDelay, uiVisible);
  }

  public RookAndBishop() {
    this(20, true);
  }

  /**
   * creates a value from 12 to 20
   * @return coins between 12 to 20
   */
  public int randomCoins() {
    int coins = ThreadLocalRandom.current().nextInt(12, 20);
    return coins;
  }

  /**
   * To produce a random number
   * @return a number within the World
   */
  public int row() {
    return ThreadLocalRandom.current().nextInt(NUMBER_OF_ROWS);
  }

  /**
   * To produce a random number
   * @return a number within the World
   */
  public int column() {
    return ThreadLocalRandom.current().nextInt(NUMBER_OF_COLUMNS);
  }

  /**
   * creates a radom value from 0 to 3
   * @return on the base of the value the random Direction
   */
  public Direction randomDirection() {
    int randomValue = ThreadLocalRandom.current().nextInt(0, 3);
    Direction randomDirection = Direction.UP;

    switch (randomValue) {
      case 0:
        randomDirection = Direction.UP;
        break;
      case 1:
        randomDirection = Direction.RIGHT;
        break;
      case 2:
        randomDirection = Direction.DOWN;
        break;
      case 3:
        randomDirection = Direction.LEFT;
        break;
      default:
        System.out.println("Fehler");
    }

    return randomDirection;
  }

  public void execute() {
    Task1.initializeTask(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS, nextFrameDelay, uiVisible);

    Robot rook = new Robot(row(), column(), randomDirection(), randomCoins());
    Robot bishop = new Robot(row(), column(), randomDirection(), 0);
    rookMovement(rook);
  }

  /**
   * Exercise 3.1 of H01
   */
  private void rookMovement(Robot rook) {
    rook.putCoin();
    if (rook.isFrontClear()) {
      rook.move();
    }
  }

  /**
   * Exercise 3.2 of H01
   */
  private void bishopMovement(Robot rook, Robot bishop) {
    // Hier programmieren
  }
}

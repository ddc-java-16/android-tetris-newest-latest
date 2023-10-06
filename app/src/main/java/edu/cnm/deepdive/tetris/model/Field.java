package edu.cnm.deepdive.tetris.model;

import edu.cnm.deepdive.tetris.model.Block.ShapeType;

public class Field {


  private final int bufferHeight;
  private final int height;
  private final int width;
  private final Dealer dealer;

  private Block currentBlock;
  private Block ghostBlock;

  private int rowsRemoved;
  private boolean gameOver;
  private final ShapeType[][] contents;

  public Field(int height, int width, int bufferHeight, Dealer dealer) {
    this.height = height;
    this.width = width;
    this.bufferHeight = bufferHeight;
    this.dealer = dealer;
    contents = new ShapeType[height][width];
  }

public void start() throws GameOverException {
    if (gameOver) {
      throw new GameOverException();
      };

    addBlock();
}

public boolean rotateLeft() throws GameOverException {
  if (gameOver) {
    throw new GameOverException();
  };
  boolean rotated = currentBlock.rotate((false));
  if(rotated) {
    createGhost();;
  }
  return rotated;
}
public boolean moveLeft() throws GameOverException {
  if (gameOver) {
    throw new GameOverException();
  };
  boolean moved = currentBlock.move(0, -1);
  if(moved) {
    createGhost();
  }
  return moved;


}
public boolean moveRight() throws GameOverException {
  if (gameOver) {
    throw new GameOverException();
  };
  boolean moved = currentBlock.move(0, 1);
  if(moved) {
    createGhost();
  }
  return moved;

  }
public boolean rotateRight() throws GameOverException {
  if (gameOver) {
    throw new GameOverException();
  };
  boolean rotated = currentBlock.rotate((true));
  if(rotated) {
    createGhost();
  }
  return rotated;

}
  public boolean moveDown() {
    if (gameOver) {
      throw new GameOverException();
    };
    boolean moved = currentBlock.move(1, 0);
    if (!moved) {
      currentBlock.freeze();
      update(currentBlock.getTopRow() + currentBlock.getLastOccupiedRow());
      if (currentBlock.getTopRow() + currentBlock.getLastOccupiedRow() < bufferHeight) {
        gameOver = true;
      } else {
        addBlock();
      }
    }
    return moved;
  }

  private void addBlock() {
    ShapeType newType = dealer.next();
    int width = newType.width();
    int leftCol = (this.width - width) / 2;
    currentBlock = new Block(newType, this, 0, leftCol);
    currentBlock.move(bufferHeight - currentBlock.getLastOccupiedRow() - 1, 0);
    if (currentBlock.move(1, 0)) {
      currentBlock.move(1, 0);
    }
    createGhost();
  }

  public ShapeType get(int rowIndex, int colIndex) {
    return contents[rowIndex][colIndex];

  }

  void set(int rowIndex, int colIndex, ShapeType type) {
    contents[rowIndex][colIndex] = type;
  }

  public boolean isOccupied(int rowIndex, int colIndex) {
    return (contents[rowIndex][colIndex] != null);

  }

  public boolean isOutOfBounds(int rowIndex, int colIndex) {
    return rowIndex < 0 || rowIndex >= contents.length ||
        colIndex < 0 || colIndex >= contents[rowIndex].length;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public int getBufferHeight() {
    return bufferHeight;
  }

  public Block getCurrentBlock(){
    return currentBlock;
  }

  public Block getGhostBlock() {
    return ghostBlock;
  }

  public int getRowsRemoved() {
    return rowsRemoved;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  private void update(int rowIndex) {
    for (; rowIndex >= 0 && !isEmpty(rowIndex); rowIndex--) {
      while (isFull(rowIndex)) {
        removeRow(rowIndex);
        rowsRemoved++;
      }

    }

  }


  private boolean isFull(int rowIndex) {
    boolean full = true;
    ShapeType[] row = contents[rowIndex];
    for (ShapeType type : row) {
      if (type == null) {
        full = false;
      }
    }
    return full;

  }

  private boolean isEmpty(int rowIndex) {
    boolean empty = true;
    ShapeType[] row = contents[rowIndex];
    for (ShapeType type : row) {
      if (type != null) {
        ;
        empty = false;
        break;
      }
    }
    return empty;
  }

  private void removeRow(int rowIndex) {

    System.arraycopy(contents, 0, contents, 1, rowIndex);
    contents[0] = new ShapeType[width];


  }

  private void createGhost() {
    Block ghost = currentBlock.clone();
    while(ghost.move(1,0)) {
      //no body code require; movement is done entirely in the condition...
    }
    ghostBlock = ghost;
  }

  public static class GameOverException extends IllegalStateException {

    public GameOverException() {
      super();
    }

    public GameOverException(String message) {
    //  super(message);
    }

    public GameOverException(String message, Throwable cause) {
      super(message, cause);
    }

    public GameOverException(Throwable cause) {
      super(cause);
    }
  }
}

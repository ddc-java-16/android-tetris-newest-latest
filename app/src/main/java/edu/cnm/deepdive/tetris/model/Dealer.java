package edu.cnm.deepdive.tetris.model;

import edu.cnm.deepdive.tetris.model.Block.ShapeType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;


public class Dealer {
  private final Queue<ShapeType> queue;
  private final List<ShapeType> bag;
  private final int size;

  private final Random rng;

public Dealer(int size, Random rng){

    this.size = size;
    this.rng = rng;
    queue = new LinkedList<>();
    bag = new ArrayList<>();
    while (queue.size() < size) {
transfer();
    }
  }

  public ShapeType next() {
  ShapeType shape = queue.remove();
  transfer();
  return shape;
    //TODO Retrieve first item in qeue and add an item from bag to end fo qeue If bag i emtpy re fill it with all the shape ypes
  }
  public int getSize(int size){
  return size;

  }
  public List<ShapeType> getQueue () {
  return Collections.unmodifiableList((LinkedList<ShapeType>) queue);
  }
  private void transfer() {
  if(bag.isEmpty()) {
    Collections.addAll(bag, ShapeType.values());
    Collections.shuffle(bag, rng);
  }
  ShapeType shape = bag.remove(0);
  queue.add(shape);

  }
}

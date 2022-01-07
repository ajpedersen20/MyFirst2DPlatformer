class Clock{
  
  private float displayTime;
  private float buffer = 0;
  private boolean display = false;
  
  public void clockUpdater(){
    if(display){
      stroke(255);
      fill(255);
      textSize(25);
      if(display)
        displayTime = millis() / 1000. - buffer;
      text(displayTime, 40, 40);
    }
  }

  public void stopClock(){
    display = false;
    lb.checkLeaderboard(displayTime);
  }
  
  public void startClock(){
    buffer = millis() / 1000.;
    display = true;
  }

  public boolean getDisplay(){
    return display;
  }

  public void setDisplay(boolean val){
    display = val;
  }

}

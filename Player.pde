class Player{
  
  int size = 25, move = 5, jump = 16; // movement characteristics
  int gravity = 1;
  boolean alive = true;
  int x = 750, y = 125; // position
  int xv, yv; // velocity
  int resetX, resetY;
  
  int dash = 12;
  int dashLength = 12;
  boolean dashAvailable = false;
  int dashTimer;
  
  public Player(){
    resetX = x;
    resetY = y;
  }
  
  public void playerUpdater(){
    xv = 0;
    yv += gravity;
    if(keys['a'])
      xv -= move;
    if(keys['d'])
      xv += move;
    if(keys['r']){
      x = resetX;
      y = resetY;
      yv = 0;
      xv = 0;
      dashTimer = 0;
      dashAvailable = false;
    }
    
    // dashing
    boolean bottomContact = currentLevel.bottomContact();
    if(keys['j'] && dashAvailable && !bottomContact){
      dashTimer = -dashLength;
      dashAvailable = false;
    }
    if(keys['l'] && dashAvailable && !bottomContact){
      dashTimer = dashLength;
      dashAvailable = false;
    }
    if(dashTimer != 0){
      yv = 0;
      if(dashTimer > 0){
        --dashTimer;
        xv += dash;
      }else{
        ++dashTimer;
        xv -= dash;
      }
    }
    
    int[][] result = currentLevel.contact();
    // 0 - bottom, 1 - right, 2 - top, 3 - left
    if(result[0][0] > 0){
      yv = result[0][1];
      dashAvailable = true;
      if(keys['w'] && result[0][0] == 2)
        yv -= jump;
    }
    if(result[1][0] == 1){
      xv = result[1][1];
      dashTimer = 0;
    }
    if(result[2][0] == 1){
      yv = result[2][1];
    }
    if(result[3][0] == 1){
      xv = result[3][1];
      dashTimer = 0;
    }
      
    x += xv;
    y += yv;
    
    stroke(255, 0, 0);
    fill(255, 0, 0);
    rect(x, y, size, size);
    
  }
  
  public void setResetValues(int _x, int _y){
    x = _x;
    y = _y;
    resetX = x;
    resetY = y;
    xv = 0;
    yv = 0;
    dashTimer = 0;
    dashAvailable = false;
  }
  
  public void reset(int _x, int _y){
    x = _x;
    y = _y;
    dashTimer = 0;
    dashAvailable = false;
    xv = 0;
    yv = 0;
  }
  
  public void setX(int _x){
    x = _x;
  }
  
  public void setY(int _y){
    y = _y;
  }
  
  public int getX(){
    return x;
  }
  
  public int getY(){
    return y;
  }
  
  public int getXV(){
    return xv;
  }
  
  public int getYV(){
    return yv;
  }
  
  public int getSize(){
    return size;
  }
  
}

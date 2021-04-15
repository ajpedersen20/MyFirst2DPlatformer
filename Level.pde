class Level{
  
  int[][] level;
  int startingX;
  int startingY;
  
  public Level(int[][] lvl, int startX, int startY){
    level = lvl;
    startingX = startX;
    startingY = startY;
  }
  
  public void levelUpdater(){
    for(int i = 0; i < level.length; ++i){
      int type = level[i][4];
      if(type == 0){
        stroke(255);
        fill(255);
      }
      if(type == 1){
        stroke(0, 0, 255);
        fill(0, 0, 255);
      }
      if(type == 2){
        stroke(0, 255, 0);
        fill(0, 255, 0);
      }
      rect(level[i][0], level[i][1], level[i][2], level[i][3]);
    }
  }
  
  public boolean bottomContact(){
    int py = player.getY();
    int px = player.getX();
    int size = player.getSize();
    for(int i = 0; i < level.length; ++i){
      int lx = level[i][0];
      int ly = level[i][1];
      int w = level[i][2];
      if(py + size == ly - 1 && px <= lx + w && px + size >= lx)
        return true;
    }
    return false;
  }
  
  public boolean trueContact(int[] platform){
    int px = player.getX();
    int py = player.getY();
    int size = player.getSize();
    int lx = platform[0];
    int ly = platform[1];
    int w = platform[2];
    int h = platform[3];
    if(py + size == ly - 1 && px <= lx + w && px + size >= lx)
      return true;
    if(px + size == lx - 1 && py + size >= ly && py <= ly + h)
      return true;
    if(py == ly + h + 1 && px + size >= lx && px <= lx + w)
      return true;
    if(px == lx + w + 1 && py + size >= ly && py <= ly + h)
      return true;
    return false;
  }
  
  public int[][] contact(){
    int[][] result = new int[4][2];
    
    // first column is true or false for contact, 0 or 1
    // column two will give the distance between platform and player if there
    //   is contact
    
    // the error last time was coming from when contact could be established 
    //   on more than one side
    // choosing the side to make contact with needs to be done
    // otherwise, I think this will be very similar to the other version
    // I'm excited
        
    // level[startingX, startingY, width, height]
    int size = player.getSize();
    int px = player.getX();
    int xv = player.getXV();
    int py = player.getY();
    int yv = player.getYV();
    for(int i = 0; i < level.length; ++i){
      if(trueContact(level[i])){
        if(level[i][4] == 1){
          updateLevel(++levelIndex);
          return result;
        }else if(level[i][4] == 2){
          player.reset(startingX, startingY);
          return result;
        }
      }
      int lx = level[i][0];
      int ly = level[i][1];
      int w = level[i][2];
      int h = level[i][3];
      
      // contact in general
      if(py + yv + size >= ly && py + yv <= ly + h &&
           px + xv + size >= lx && px + xv <= lx + w){
      
        // contact on bottom of player
        if(py + yv + size >= ly && py <= ly + h &&
           px + size >= lx && px <= lx + w){
          
          // for jumping: checking if actually touching the platform   
          if(py + size == ly - 1){
            result[0][0] = 2;
            result[0][1] = 0;
          }
          else{
            result[0][0] = 1;
            result[0][1] = ly - py - size - 1;
          }
        }
        
        // contact on top of player
        else if(py + size >= ly && py + yv <= ly + h &&
           px + size >= lx && px <= lx + w){
          result[2][0] = 1;
          result[2][1] = ly + h - py + 1;
        }
        
        // contact on right of player
        else if(py + size >= ly && py <= ly + h &&
           px + xv + size >= lx && px <= lx + w){
          result[1][0] = 1;
          result[1][1] = lx - px - size - 1;
        }
        
        // contact on left of player
        else if(py + size >= ly && py <= ly + h &&
           px + size >= lx && px + xv <= lx + w){
          result[3][0] = 1;
          result[3][1] = lx + w - px + 1;
        }
        
        // contact with corner //////////////////////////////////////////
        // if(result[0][0] == 0 && result[1][0] == 0 && result[2][0] == 0 && result[3][0] == 0){
        else{
          
          // contact with bottom left corner of player
          if(py + yv + size >= ly && py <= ly + h &&
             px + size >= lx && px + xv <= lx + w){
            //result[3][0] = 1;
            //result[3][1] = lx + w - px + 1;
            result[0][0] = 1;
            result[0][1] = ly - py - size - 1;
          }
          
          // contact with bottom right corner of player
          else if(py + yv + size >= ly && py <= ly + h &&
             px + xv + size >= lx && px <= lx + w){
            //result[1][0] = 1;
            //result[1][1] = lx - px - size - 1;
            result[0][0] = 1;
            result[0][1] = ly - py - size - 1;
          }
          
          // contact with top left corner of player
          else if(py + size >= ly && py + yv <= ly + h &&
             px + size >= lx && px + xv <= lx + w){
            //result[3][0] = 1;
            //result[3][1] = lx + w - px + 1;
            result[2][0] = 1;
            result[2][1] = ly + h - py + 1;
          }
          
          // contact with top right corner of player
          else if(py + size >= ly && py + yv <= ly + h &&
             px + xv + size >= lx && px <= lx + w){
            //result[1][0] = 1;
            //result[1][1] = lx - px - size - 1;
            result[2][0] = 1;
            result[2][1] = ly + h - py + 1;
          }
          
        }
        
      }
    }    
    
    return result;
  }
  
  public int getType(int index){
    return level[index][4];
  }
  
  public int getStartingX(){
    return startingX;
  }
  public int getStartingY(){
    return startingY;
  }
  
  public int[][] getPlatforms(){
    return level;
  }
  
}

class Menu{
  
  // x, y, width, height, go to menu id for text boxes
  // -1 menu id means not clickable
  Level level;
  String[] text;
  int[] targetIDs;
  
  public Menu(Level l, String[] t, int[] tIDs){
    level = l;
    text = t;
    targetIDs = tIDs;
  }
  
  public void menuUpdater(){
    textSize(20);
    int[][] platforms = level.getPlatforms();
    for(int i = 0; i < platforms.length; ++i){
      stroke(255);
      fill(255);
      rect(platforms[i][0], platforms[i][1], platforms[i][2], platforms[i][3]);
      stroke(0);
      fill(0);
      text(text[i], platforms[i][0] + 10, platforms[i][1] + 20);
    }
  }
  
  public void clicked(int x, int y){
    int value = 0;
    int[][] platforms = level.getPlatforms();
    for(int i = 0; i < platforms.length; ++i){
      int bx = platforms[i][0];
      int by = platforms[i][1];
      int w = platforms[i][2];
      int h = platforms[i][3];
      if(x < bx + w && x > bx && y < by + h && y > by)
        value = targetIDs[i];
    }
    if(value > 0){
      updateMenu(value - 1);
    }
    if(value < 0){
      int levelNum = abs(value) - 1;
      updateLevel(levelNum);
      screenType = 1;
      levelIndex = levelNum;
    }
  }
  
  public void updateLeaderboardMenu(double t1, double t2, double t3){
    if(lb.getCheck(1))
      text[0] = String.valueOf(t1).substring(0, 5);
    if(lb.getCheck(2))
      text[1] = String.valueOf(t2).substring(0, 5);
    if(lb.getCheck(3))
      text[2] = String.valueOf(t3).substring(0, 5);
  }
  
  public Level getLevel(){
    return level;
  }
  
}

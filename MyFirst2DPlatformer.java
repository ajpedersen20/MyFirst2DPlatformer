import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class MyFirst2DPlatformer extends PApplet {

Player player = new Player();
Clock clock = new Clock();
Leaderboard lb = new Leaderboard();

int screenType = 0;

// startingX, startingY, length, width, type
int[][] lvl1 = {{0,0,1000,10,0},{990,0,10,700,0},{0,0,10,700,0},{0,690,1000,10,0},{490,0,20,410,0},{0,190,410,20,0},
                {90,390,710,20,0},{90,390,20,220,0},{190,590,120,120,0},{390,390,120,120,0},{590,650,120,40,2},{890,590,120,20,0},
                {790,490,120,20,0},{540,340,20,20,1}};
int[][] lvl2 = {
                // left side
                {0,590,210,20,0},{90,90,20,420,0},{90,490,310,20,0},{190,90,20,320,0},{200,90,400,20,0},{290,190,20,320,0},
                {290,590,20,110,0},{290,290,320,20,0},{300,590,210,20,0},{390,90,20,120,0},{390,490,20,120,0},{390,390,110,20,0},
                {490,190,20,120,0},{490,390,20,120,0},{490,490,110,20,0},{590,90,20,120,0},{590,290,20,120,0},{590,490,20,120,0},
                {590,190,100,20,0},{590,590,100,20,0},
                // left side footholds
                {75,190,50,20,0},{75,390,50,20,0},{-25,290,50,20,0},{-25,490,50,20,0},{175,290,50,20,0},{-25,90,50,20,0},
                {275,390,50,20,0},
                // right side
                {690,90,220,20,0},{790,190,110,20,0},{900,190,100,20,2},{710,290,90,20,0},{800,290,110,20,2},{790,390,140,20,2},
                {710,490,200,20,0},{890,511,20,99,2},{690,90,20,520,2},{740,640,20,20,1},{931,390,59,20,0},
                // border
                {0,0,1000,10,0},{0,0,10,700,0},{0,690,800,20,0},{990,11,10,690,2},{800,690,200,10,2}
                };
int[][] lvl3 = {
                {690,590,220,20,0},{190,590,220,20,0},{-10,490,120,20,0},{290,390,220,20,0},{690,340,100,20,0},{780,240,120,20,0},
                {-10,190,420,20,0},{190,90,20,99,2},{0,0,10,190,2},{0,0,110,10,2},{90,0,20,110,2},{40,40,20,20,1}
                };
int[][] lvl4 = {{90,690,120,20,0},{290,690,120,20,0},{490,690,120,20,2},{690,690,120,20,0},{890,690,120,20,0},
                {90,590,120,20,0},{290,590,120,20,2},{490,590,120,20,0},{690,590,120,20,2},{890,590,120,20,0},
                {90,490,120,20,2},{290,490,120,20,0},{490,490,120,20,0},{690,490,120,20,2},{890,490,120,20,0},
                {90,390,120,20,0},{290,390,120,20,2},{490,390,120,20,0},{690,390,120,20,0},{890,390,120,20,2},
                {90,290,120,20,0},{290,290,120,20,2},{490,290,120,20,2},{690,290,120,20,2},{890,290,120,20,0},
                {90,190,120,20,0},{290,190,120,20,0},{490,190,120,20,0},{690,190,120,20,2},{890,190,120,20,2},
                {90,90,120,20,0},{290,90,120,20,2},{490,90,120,20,2},{690,90,120,20,0},{890,90,120,20,0},{940,40,20,20,1}};
int[][] lvl5 = {
                // border
                {0,0,1000,10,0},{0,0,10,700,0},{990,0,10,700,0},{0,690,1000,10,0},{940,640,20,20,1},
                // walls
                {290,90,20,620,0},{590,0,20,610,0},{890,110,20,600,0},
                // platforms
                {-10,90,120,20,0},{-10,290,120,20,0},{-10,490,120,20,0},{60,190,180,20,0},{60,390,180,20,0},{60,590,180,20,0},
                {190,90,220,20,0},{190,290,220,20,0},{190,490,220,20,0},{360,190,180,20,2},{360,390,180,20,2},{360,590,180,20,2},
                {490,90,110,20,0},{490,290,110,20,0},{490,490,170,20,0},{690,90,220,20,2},{611,190,29,20,2},{641,190,100,20,0},
                {742,190,90,20,2},{690,290,110,20,2},{801,290,100,20,0},{600,390,25,20,0},{661,490,65,20,2},{660,590,60,20,2},
                {721,590,100,20,0},{822,590,67,20,2}
                };
int[][] lvl6 = {                                
                // obstacles
                {490,590,20,110,2},{790,0,120,110,2},
                {590,0,120,110,2},{90,90,100,20,2},{110,190,100,20,2},{110,490,100,20,2},{190,490,20,120,2},
                {290,110,20,180,2},{310,90,200,20,2},{390,210,20,280,2},{490,90,20,320,2},{590,190,120,120,2},
                {590,390,220,20,2},{590,390,20,220,2},{790,190,120,120,2},{790,410,20,80,2},{890,510,20,100,2},
                // platforms
                {90,590,20,110,0},{0,190,110,20,0},{0,490,110,20,0},{890,290,110,120,0},{90,290,20,120,0},{90,290,220,20,0},
                {190,390,120,120,0},{190,90,120,20,0},{290,490,320,20,0},{490,290,420,20,0},{690,590,120,20,0},{790,490,120,20,0},
                // border
                {0,0,1000,10,0},{990,0,10,700,0},{0,0,10,700,0},{0,690,1000,10,0},{290,690,120,10,2},{690,690,220,10,2},
                // objective and footholds
                {940,40,20,20,1},{975,590,50,20,0},{-25,390,50,20,0},{375,390,50,20,0},{375,190,50,20,0},{475,290,50,20,0},
                {975,190,50,20,0}};

Level level1 = new Level(lvl1, 50, 50);
Level level2 = new Level(lvl2, 500, 30);
Level level3 = new Level(lvl3, 787, 525);
Level level4 = new Level(lvl4, 140, 640);
Level level5 = new Level(lvl5, 140, 640);
Level level6 = new Level(lvl6, 25, 625);

Level[] levels = {level1, level2, level3, level4, level5, level6};
Level currentLevel;

int levelIndex = 0;

public void updateLevel(int index){
  if(index >= levels.length){
    updateMenu(0);
    screenType = 0;
    if(clock.getDisplay())
      clock.stopClock();
  }else{
    currentLevel = levels[index];
    player.setResetValues(currentLevel.getStartingX(), currentLevel.getStartingY());
    if(index == 0)
      clock.startClock();
  }
}

// Menus
int[][] mMenu = {{400, 100, 200, 50, 0}, {400, 300, 200, 50, 0}, {400, 500, 200, 50, 0}};
String[] mmText = {"Level Selector", "Leaderboard", "Controls"};
Level mainMenuLevel = new Level(mMenu, 565, 50);
int[] menuIDs = {2, 3, 4};

int[][] lSelector = {{200, 100, 200, 50, 0}, {600, 100, 200, 50, 0}, {200, 300, 200, 50, 0},
                     {600, 300, 200, 50, 0}, {200, 500, 200, 50, 0}, {600, 500, 200, 50, 0},
                     {25, 25, 150, 30, 0}};
String[] lsText = {"Level 1", "Level 2", "Level 3" , "Level 4" , "Level 5" , "Level 6", "Back"};
Level lsLevel = new Level(lSelector, 690, 50);
int[] lsIDs = {-1, -2, -3, -4, -5, -6, 1};

int[][] lBoard = {{400, 100, 200, 50, 0}, {400, 300, 200, 50, 0}, {400, 500, 200, 50, 0},
                  {35, 35, 150, 30, 0}};
String[] lbText = {"--", "--", "--", "Back"}; // this could be hard
Level lbLevel = new Level(lBoard, 125, -15);
int[] lbIDs = {0, 0, 0, 1};

int[][] drctions = {{200, 100, 200, 50, 0}, {600, 100, 200, 50, 0}, {200, 300, 200, 50, 0},
                    {600, 300, 200, 50, 0}, {200, 500, 200, 50, 0}, {550, 450, 400, 50, 0},
                    {550, 500, 400, 50, 0}, {400, 575, 200, 50, 0}, {25, 25, 150, 30, 0}};
String[] dText = {"'A' - left", "'D' - right", "'J' - dash left", "'L' - dash right", "'W' - jump",
                  "Green Block - Obstacle", "Blue Block - Objective", "'R' - reset position", "Back"};
Level dLevel = new Level(drctions, 350, 50);
int[] dIDs = {0, 0, 0, 0, 0, 0, 0, 0, 1};

Menu mainMenu = new Menu(mainMenuLevel, mmText, menuIDs);
Menu levelSelector = new Menu(lsLevel, lsText, lsIDs);
Menu leaderboard = new Menu(lbLevel, lbText, lbIDs);
Menu directions = new Menu(dLevel, dText, dIDs);

Menu[] menus = {mainMenu, levelSelector, leaderboard, directions};
Menu currentMenu;

public void updateMenu(int id){
  currentMenu = menus[id];
  updateLevel(menus[id].getLevel());
}

public void updateLevel(Level l){
  currentLevel = l;
  player.setResetValues(l.getStartingX(), l.getStartingY());
}

boolean[] keys = new boolean[127];

public void keyPressed(){
  if(key >= 0 && key <= 127)
    keys[key] = true;
}

public void keyReleased(){
  if(key >= 0 && key <= 127)
    keys[key] = false;
}

public void mousePressed(){
  if(screenType == 0)
    currentMenu.clicked(mouseX, mouseY);
}

public void setup(){
  
  frameRate(60);
  updateMenu(0);
}

public void draw(){
  background(0);
  if(screenType == 0)
    currentMenu.menuUpdater();
  else
    currentLevel.levelUpdater();
  player.playerUpdater();
  clock.clockUpdater();
}
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
        displayTime = millis() / 1000.f - buffer;
      text(displayTime, 40, 40);
    }
  }

  public void stopClock(){
    display = false;
    lb.checkLeaderboard(displayTime);
  }
  
  public void startClock(){
    buffer = millis() / 1000.f;
    display = true;
  }

  public boolean getDisplay(){
    return display;
  }

  public void setDisplay(boolean val){
    display = val;
  }

}
class Leaderboard{
  
  double b1, b2, b3;
  boolean c1 = false, c2 = false, c3 = false;
  boolean display = false;
  
  public void checkLeaderboard(float time){
    if(!c1 && !c2 && ! c3){
      c1 = true;
      b1 = time;
    }else if(!c2 && !c3){
      c2 = true;
      if(time < b1){
        b2 = b1;
        b1 = time;
      }else
        b2 = time;
    }else if(!c3){
      c3 = true;
      if(time < b1 && time < b2){
        b3 = b2;
        b2 = b1;
        b1 = time;
      }else if(time < b2){
        b3 = b2;
        b2 = time;
      }else
        b3 = time;
    }else{
      if(time < b1 && time < b2 && time < b3){
        b3 = b2;
        b2 = b1;
        b1 = time;
      }else if(time < b2 && time < b3){
        b3 = b2;
        b2 = time;
      }else if(time < b3)
        b3 = time;
    }
    leaderboard.updateLeaderboardMenu(b1, b2, b3);
  }
  
  public boolean getCheck(int num){
    if(num == 1)
      return c1;
    if(num == 2)
      return c2;
    if(num == 3)
      return c3;
    return false;
  }
  
}
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
  public void settings() {  size(1000,700); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "MyFirst2DPlatformer" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

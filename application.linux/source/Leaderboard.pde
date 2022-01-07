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

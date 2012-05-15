public class Joke {
    public int jokeNum;
    public int userNum;
    public float value;

    public Joke(int jokeNum, int userNum, float value) {
       this.jokeNum = jokeNum;
       this.userNum = userNum;
       this.value = value;
    }

    public boolean equals(Object x) {
       if(this.jokeNum == ((Joke) x).jokeNum && this.userNum == ((Joke) x).userNum) {
          return true;
       }
       return false;
    }
}

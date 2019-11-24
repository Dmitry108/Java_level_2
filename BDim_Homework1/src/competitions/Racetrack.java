package competitions;

public class Racetrack extends Obstacle {
    private int length;

    public Racetrack (int length){
        this.length = length;
    }

    //Конструктор по умолчанию, пусть дляна дорожки будет 100 м
    public Racetrack (){
        this.length = 100;
    }

    @Override
    public void doIt(Competitor competitor) {
        competitor.run(length);
    }
}

package competitions;

public class Wall extends Obstacle {
    private double height;

    public Wall (double height){
        this.height = height;
    }
    //Конструктор по умолчанию, пусть высота равна 0,5 м
    public Wall () {
        this.height = 0.5;
    }

    @Override
    public void doIt(Competitor competitor) {
        competitor.jump(height);
    }
}

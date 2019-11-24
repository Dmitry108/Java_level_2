//Dmitrii Babanov 20.11.2019
//Homework lesson 1
//Требуется реализовать enum DayOfWeek,
//  который будет представлять дни недели.
//  С его помощью необходимо решить задачу определения кол-ва рабочих часов до конца недели
//  по заднному текущему дню.
//  Возвращает кол-во оставшихся рабочих часов до конца
//  недели по заданному текущему дню.
//  Считается, что текущий день ещё не начался,
//  и рабочие часы за него должны учитываться.
//  Если заданный день выходной то вывести "Сегодня выходной"

package enums;

public class DayOfWeekMain {
    enum DayOfWeek{MONDAY(true), TUESDAY (true), WEDNESDAY (true), THURSDAY (true), FRIDAY (true), SATURDAY (false), SUNDAY (false);
        boolean workingDay;
        DayOfWeek(boolean workingDay){
        this.workingDay=workingDay;
        }
        public boolean isWorkingDay(){return workingDay;}
    }

    public static void main(String[] args) {
        DayOfWeek day = DayOfWeek.TUESDAY;
        if (!day.isWorkingDay()) System.out.println("Сегодня выходной");
        System.out.println(getWorkingHours(day));
    }
    public static int getWorkingHours(DayOfWeek day){
        int hours=0;
        //пусть будет стандартный рабочий день 8 часов
        final int workingHours = 8;
        for (int i = day.ordinal(); i<7;
             hours+=day.values()[i].isWorkingDay()?workingHours:0, i++);
        return hours;
    }
}


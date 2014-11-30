import java.util.Random;

/**
 * Created by oky on 2013/12/31.
 */
public class MN_movement {

/*方向は右回りに8つか、ランダムの9通り*/
    int direction_num;
    double[] direction_array;
    Random ran;
    int staytime;



    public MN_movement(int x) {
        direction_num = x;
        direction_array = new double[8];

        ran = new Random();
       // set_direction_num();

        System.out.println("move_direction:"+direction_num);

    }

    public void decide_movement_model(int staytime){
        int x = ran.nextInt(10);
        this.staytime = staytime;

        if(x <= 2){
            set_direction_random();
        }else{
            set_direction_num();
        }
    }

    public void set_direction_num(){

        switch (direction_num){
            case 0:
            case 7:
                set_direction(direction_num);
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                set_direction();
                break;
            case 8:
                set_direction_random();
                break;
            default:
                break;
        }

    }

//進行方向をランダムにする
    public void set_direction_random(){

        //電車出ない場合はすべての方向にランダム
        if(staytime != Main.mn_mag_stay_time[8]){

            direction_num = ran.nextInt(8);

            for(int i = 0;i<direction_array.length;i++){
                if(direction_num == i)direction_array[i] = 1;
                else direction_array[i] = 0;
            }

            //電車の場合
        }else{

            if(direction_num >=1 && direction_num <= 6){

                for(int i = 0;i<direction_array.length;i++){

                   if(i == direction_num-1 || i == direction_num+1 || i == direction_num){
                        direction_array[i] = 0.2;
                    }else if(i == direction_num-2 || i == direction_num+2 ||
                            ((direction_num == 1)&&(i == 7)) || ((direction_num == 6)&&(i == 0))){
                        direction_array[i] = 0.175;
                    }else if(Math.abs(direction_num-i) == 4){
                        direction_array[i] = 0;
                    }else{
                       direction_array[i] = 0.025;
                   }

                }

            }else{

                for(int i = 1;i<direction_array.length-1;i++){
                    direction_array[i] = 0;
                }
                if(direction_num == 0){
                    direction_array[0] = 0.2;
                    direction_array[1] = 0.2;
                    direction_array[7] = 0.2;
                    direction_array[6] = 0.175;
                    direction_array[2] = 0.175;
                    direction_array[5] = 0.025;
                    direction_array[3] = 0.025;

                }
                else if( direction_num == 7){
                    direction_array[7] = 0.2;
                    direction_array[0] = 0.2;
                    direction_array[6] = 0.2;
                    direction_array[1] = 0.175;
                    direction_array[5] = 0.175;
                    direction_array[4] = 0.025;
                    direction_array[2] = 0.025;
                }
            }

            int z = ran.nextInt(100);
            double total = 0;
            int i=0;
            do{total += (direction_array[i++]*100);} while (total<=z);

            direction_num = i-1;


        }

        System.out.println("set_direction_random");
    }

    public void set_direction(){

        //自動車の場合
        if(staytime <= Main.mn_mag_stay_time[4] && staytime >= Main.mn_mag_stay_time[7]){
            for(int i = 0;i<direction_array.length;i++){

                if(i == direction_num-1 || i == direction_num+1){
                    direction_array[i] = 0.05;
                }else if ( i == direction_num){
                    direction_array[i] = 0.8;
                }else {
                    direction_array[i] = 0.02;
                }
            }
            //電車の場合
        }else if(staytime == Main.mn_mag_stay_time[8]){

            for(int i = 0;i<direction_array.length;i++){
                if ( i == direction_num){
                    direction_array[i] = 0.85;
                }else if(i == direction_num-1 || i == direction_num+1){
                    direction_array[i] = 0.045;
                }else if(i == direction_num-2 || i == direction_num+2 ||
                        ((direction_num == 1)&&(i == 7)) || ((direction_num == 6)&&(i == 0))){
                    direction_array[i] = 0.02;
                }else if(Math.abs(direction_num-i) == 4){
                    direction_array[i] = 0;
                }else{
                    direction_array[i] = 0.01;
                }
            }
             //徒歩、自転車の場合
        }else{

             for(int i = 0;i<direction_array.length;i++){

                 if ( i == direction_num){
                     direction_array[i] = 0.4;
                 }else if(i == direction_num-1 || i == direction_num+1){
                     direction_array[i] = 0.1;
                 }else if(i == direction_num-2 || i == direction_num+2 ||
                         ((direction_num == 1)&&(i == 7)) || ((direction_num == 6)&&(i == 0))){
                     direction_array[i] = 0.1;
                 }else if(Math.abs(direction_num-i) == 4){
                     direction_array[i] = 0.05;
                 }else{
                     direction_array[i] = 0.075;
                 }
             }


         }
    }


    public void set_direction(int x){


            if(staytime <= Main.mn_mag_stay_time[4] && staytime >= Main.mn_mag_stay_time[7]){
            for(int i = 1;i<direction_array.length-1;i++){
                direction_array[i] = 0.02;
            }

            if(x == 0){
                direction_array[0] = 0.8;
                direction_array[1] = 0.05;
                direction_array[7] = 0.05;

            }
            else if( x == 7){
                direction_array[7] = 0.8;
                direction_array[0] = 0.05;
                direction_array[6] = 0.05;

            }
        }else if(staytime == Main.mn_mag_stay_time[8]){
            for(int i = 1;i<direction_array.length-1;i++){
                direction_array[i] = 0;
            }

            if(x == 0){
                direction_array[0] = 0.85;
                direction_array[1] = 0.045;
                direction_array[7] = 0.045;
                direction_array[6] = 0.02;
                direction_array[2] = 0.02;
                direction_array[5] = 0.01;
                direction_array[3] = 0.01;

            }
            else if( x == 7){
                direction_array[7] = 0.85;
                direction_array[0] = 0.045;
                direction_array[6] = 0.045;
                direction_array[1] = 0.02;
                direction_array[5] = 0.02;
                direction_array[4] = 0.01;
                direction_array[2] = 0.01;
            }
        }else {

                for(int i = 0;i<direction_array.length;i++){
                    direction_array[i] = 0.1;
                }

                if(x == 0){
                    direction_array[0] = 0.4;
                    direction_array[3] = 0.075;
                    direction_array[4] = 0.05;
                    direction_array[5] = 0.075;

                }
                else if( x == 7){
                    direction_array[7] = 0.4;
                    direction_array[2] = 0.075;
                    direction_array[3] = 0.05;
                    direction_array[4] = 0.075;

                }

            }
    }

    public int move(){

        int z = ran.nextInt(100);
        double total = 0;
        int i=0;

        System.out.println("z:"+z);


        do{
            total += (direction_array[i++]*100);

        }while (total<=z);

        System.out.println("move:"+(i-1)+" array:"+direction_array[i-1]);

        direction_num = i-1;

        return i-1;

    }

    //フィールドの端にいる場合に向きを変えるかどうかの判定
    public void check_direction(int x,int y){

        if (x == 0) {
            switch (y) {
                case 0://左下
                    if (direction_num == 6 || direction_num == 5 || direction_num == 4) {
                        change_direction(6, 5, 4);
                        System.out.println("change01");
                    }
                    break;
                case Main.fieldy - 1://左上
                    if (direction_num == 0 || direction_num == 7 || direction_num == 6) {
                        change_direction(0, 7, 6);
                        System.out.println("change02");
                    }
                    break;
                default://左
                    if (direction_num == 7 || direction_num == 6 || direction_num == 5) {
                        System.out.println("change03");
                        change_direction(5, 7, 6);
                    }

                    break;
            }

        } else if (x == Main.fieldx - 1) {
            switch (y) {
                case 0://右下
                    if (direction_num == 2 || direction_num == 3 || direction_num == 4) {
                        System.out.println("change04");
                        change_direction(2, 3, 4);
                    }

                    break;
                case Main.fieldy - 1://右上
                    if (direction_num == 0 || direction_num == 1 || direction_num == 2) {
                        System.out.println("change05");
                        change_direction(0, 1, 2);
                    }

                    break;
                default://右
                    if (direction_num == 1 || direction_num == 2 || direction_num == 3) {
                        change_direction(1, 2, 3);
                        System.out.println("change06");
                    }

                    break;
            }
        } else {
            switch (y) {
                case 0://下
                    if (direction_num == 3 || direction_num == 4 || direction_num == 5) {
                        System.out.println("change07");
                        change_direction(3, 4, 5);
                    }

                    break;
                case Main.fieldy - 1://上
                    if (direction_num == 0 || direction_num == 7 || direction_num == 1) {
                        change_direction(0, 1, 7);
                        System.out.println("change08");
                    }

                    break;
            }

        }


    }

    public void change_direction(int x,int y,int z){

        do{
            direction_num = ran.nextInt(8);

        }while (direction_num == x || direction_num == y || direction_num == z);

        System.out.println("change_direction:"+direction_num);
        set_direction_num();

    }



}

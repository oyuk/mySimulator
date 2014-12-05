import java.util.Random;

/**
 * Created by oky on 14/11/27.
 */
public class MN_movement {

    /*方向は右回りに8つか、ランダムの9通り*/
    int direction_num;
    double[] direction_array;
    Random ran;

/*
* 移動モデルの番号
* 1:ランダム
* 2:二地点最短
* 3:二地点迂回
*/
    int movement_model_num;

    /*目的地方向
    * 移動モデルが2,3の場合に使用
    * */
    int dest_direction;

/*移動モデルが2,3の場合の移動しない方向が指定されている配列*/
    int[] not_move_direction_array;


    public MN_movement(int movement_model_num,int dest_direction) {

        this.movement_model_num = movement_model_num;

        if (movement_model_num >= 2) {
            direction_num = this.dest_direction = dest_direction;
            set_not_move_direction();
        }

        direction_array = new double[8];
        ran = new Random();

        System.out.println("move_direction:"+direction_num);

    }


    public void set_not_move_direction(){

        not_move_direction_array = new int[3];

        switch (dest_direction){
            case 0:
                not_move_direction_array[0] = 3;
                not_move_direction_array[1] = 4;
                not_move_direction_array[2] = 5;
                break;
            case 1:
                not_move_direction_array[0] = 4;
                not_move_direction_array[1] = 5;
                not_move_direction_array[2] = 6;
                break;
            case 2:
                not_move_direction_array[0] = 5;
                not_move_direction_array[1] = 6;
                not_move_direction_array[2] = 7;
                break;
            case 3:
                not_move_direction_array[0] = 6;
                not_move_direction_array[1] = 7;
                not_move_direction_array[2] = 0;
                break;
            case 4:
                not_move_direction_array[0] = 7;
                not_move_direction_array[1] = 0;
                not_move_direction_array[2] = 1;
                break;
            case 5:
                not_move_direction_array[0] = 0;
                not_move_direction_array[1] = 1;
                not_move_direction_array[2] = 2;
                break;
            case 6:
                not_move_direction_array[0] = 1;
                not_move_direction_array[1] = 2;
                not_move_direction_array[2] = 3;
                break;
            case 7:
                not_move_direction_array[0] = 2;
                not_move_direction_array[1] = 3;
                not_move_direction_array[2] = 4;
                break;
            default:
                break;
        }

    }


    public void decide_movement_model(){

        switch (movement_model_num) {
            case 1:
                set_direction_random();
                break;
            case 2:
                set_direction();
                break;
            case 3:
                set_fixed_direction();
                break;
            default:
                break;
        }
    }


    //進行方向をランダムにする
    public void set_direction_random(){

            direction_num = ran.nextInt(8);

            for(int i = 0;i<direction_array.length;i++){
                if(direction_num == i)direction_array[i] = 1;
                else direction_array[i] = 0;
            }

        System.out.println("set_direction_random");
    }

    public void set_direction() {

        //いったん全方向の移動確率いれる
        for (int i = 0;i<direction_array.length;i++){
            direction_array[i] = 0.2;
        }

        //destinationと反対方向だけ0にする。
        for(int i = 0;i < not_move_direction_array.length;i++){
            direction_array[not_move_direction_array[i]] = 0;
        }

    }

    public void set_fixed_direction(){

        //目的地方向1で、それ以外は0
        for (int i = 0;i<direction_array.length;i++){

            if (i == dest_direction){
                direction_array[i] = 1;
            }else{
                direction_array[i] = 0;
            }

        }
    }

    public int move(){

        int z = ran.nextInt(100);
        double total = 0;
        int i=0;

//        System.out.println("z:"+z);

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
        set_direction();
    }

}

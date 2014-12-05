import java.util.ArrayList;
import java.util.Random;

/**
 * Created by oky on 14/11/27.
 */
public class MN {

    int x; //x座標
    int y; //y座標
    boolean isActive;//mnの状態
    int stay_time_count;
    int bs_move_count;//bsを移動した回数
    int i;//count_active_timerで使用

    int move_direction;//ノードの移動した方向
    int move_dire_count[];//どの方向に移動したかカウント

    int active_timer;

    MN_movement mn_movement;

    Random random;


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

    //mnについて、activeになっている時間をつくる
    public ArrayList<Integer> intervalList;


    MN(int x,int y,int movement_model_num,int dest_direction) {

        this.x = x;
        this.y = y;
        isActive = false;

        bs_move_count = 0;

        this.movement_model_num = movement_model_num;
        this.dest_direction = dest_direction;

        mn_movement = new MN_movement(movement_model_num,dest_direction);

        intervalList = calc_interval_time();

        random = new Random();
    }

    void update(int time) {

        if (isActive) count_active_timer();

        active_state(time);

        if (++stay_time_count == Main.mn_bs_stay_time[0]) {

            move();

            stay_time_count = 0;
        }

    }

    void active_state(int time) {

        if (intervalList.get(0) == time) {

            if (!isActive) {
                isActive = true;
                //アクティブタイマーの長さを決定
                active_timer += Main.active_timer[random.nextInt(Main.active_timer.length)];
                //配列の先頭を削除
                intervalList.remove(0);
            }
        }
    }

    //active状態の時間を測る
    void count_active_timer() {

        i++;
        if (i >= active_timer+1) {
            isActive = false;
            i = 0;
            active_timer = 0;
        }
    }


    void move() {

        int pre_x = x;
        int pre_y = y;

        mn_movement.decide_movement_model();

        //フィールドの端にいる場合は進む方向を変える。
        if(pre_y == Main.fieldy-1 || pre_x == Main.fieldx-1 || pre_y == 0 || pre_x == 0){
            mn_movement.check_direction(pre_x, pre_y);
        }

//        移動していない間はループする
        while(pre_x == x && pre_y == y){

            switch (move_direction = mn_movement.move()) {
                case 0:
                    if (y < Main.fieldy - 1) y++;//上
                    break;
                case 1:
                    if (x < Main.fieldx - 1 && y < Main.fieldy - 1) {x++;y++;}//右上
                    break;
                case 2:
                    if (x < Main.fieldx - 1) x++;//右
                    break;
                case 3:
                    if (x < Main.fieldx - 1 && y > 0) {x++;y--;}//右下
                    break;
                case 4:
                    if (y > 0) y--;//下
                    break;
                case 5:
                    if (x > 0 && y > 0) {x--;y--;}//左下
                    break;
                case 6:
                    if (x > 0) x--;//左
                    break;
                case 7:
                    if (x > 0 && y < Main.fieldy - 1) {x--;y++;}//左上
                    break;
                default:
                    break;
            }

        }

        move_dire_count[move_direction]++;
        bs_move_count++;
    }



    //指数分布に従ったパケット送受信レート作成
    ArrayList<Integer> calc_interval_time() {

       ArrayList<Integer> expList = new ArrayList<Integer>();
       double time = 0;

        double t;
        double t2;

        while (time < Main.SimuTime) {

            t = -Math.log(1 - Math.random()) / Main.lambda;

            t2 = Main.per_unit_time * t;

            time += t2;

            expList.add((int) Math.round(time));
            System.out.print((int)Math.round(time) + ",");

        }

        System.out.println();

        return expList;
    }


    //移動履歴を表示
    void print_move_history(){

        for(int i = 0;i< move_dire_count.length;i++){
            System.out.println(i+":"+ move_dire_count[i]);
        }
    }

}

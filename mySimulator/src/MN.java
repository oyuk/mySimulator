import java.util.Random;

/**
 * Created by oky on 14/11/27.
 */
public class MN {

    int x; //x座標
    int y; //y座標
    boolean isActive;//mnの状態
    int stay_time;
    int stay_time_count;
    int id;//mnのid
    int bs_move_count;//bsを移動した回数
    int session_count;//通信した回数
    int i;//count_active_timerで使用
    int j;//move_historyで使用
    int pa_center_x;//ページングエリアの中心
    int pa_center_y;//ページングエリアの中心
    int move_direction;//ノードの移動した方向
    int move_dire_count[];//どの方向に移動したかカウント
    int last_pa_move_time;
    int active_timer;
    int pa_staytime;//paが設定される時のstaytime

    Field field;
    MN_movement mn_movement;
    PagingArea pa;
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

    /*目的地座標*/
    int dest_point;



    MN(int x,int y,int id,int movement_model_num,int dest_direction) {

        this.x = pa_center_x = x;
        this.y = pa_center_y = y;
//        this.id = id;
//        this.stay_time = pa_staytime = stay_time;
        isActive = false;

        bs_move_count = 0;

        this.movement_model_num = movement_model_num;
        this.dest_direction = dest_direction;


//        field = Field.getInstance();
        mn_movement = new MN_movement(movement_model_num,dest_direction);
//        move_dire_count = new int[8];

//        pa = new PagingArea();
//        pa.set_PA(move_dir,x,y,stay_time);

//        ran = new Random();
    }

    void update(int time) {

        if (isActive) count_active_timer();

        if (++stay_time_count == Main.mn_bs_stay_time[0]) {

//            detach_bs();
            move();
//            attach_bs(time);
//            state();
            stay_time_count = 0;

        }

    }

    //ノードのモードを変更
    void change_mode(boolean x) {
        isActive = x;
    }

    //active状態の時間を測る
    void count_active_timer() {

        i++;
        if (i >= active_timer+1) {
            change_mode(false);
            i = 0;
            active_timer = 0;
        }
    }


    //セッションが来た際の座標をページングエリアの中心座標に変える
    void pa_center_change(int time){
        pa.pa_x.clear();
        pa.pa_y.clear();
        pa.set_PA(move_direction,x,y,stay_time);
        pa_center_x = x;
        pa_center_y = y;
        last_pa_move_time = time;
        pa_staytime = stay_time;
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



    //MAGにattachした際の処理
    void attach_bs(int time) {

        //PAを移動しているかの判定
        if(!pa.check(x, y)){//PAを移動した場合
            pa.pa_x.clear();
            pa.pa_y.clear();
            pa.set_PA(move_direction,x,y,stay_time);
            pa.pa_move_count++;
            pa_center_x = x;
            pa_center_y = y;
            field.reg_bs(x, y,id ,true, isActive);
            last_pa_move_time = time;
            pa_staytime = stay_time;

        }else{
            field.reg_bs(x, y,id ,false, isActive);
        }


    }

    //MAGから離れる際の処理
    void detach_bs() {
        field.del_bs(x, y, id);
    }

    //移動履歴を表示
    void print_move_history(){

        for(int i = 0;i< move_dire_count.length;i++){
            System.out.println(i+":"+ move_dire_count[i]);
        }
    }

}

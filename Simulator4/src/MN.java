import java.util.Random;


public class MN {

    int x; //x座標
    int y; //y座標
    boolean active;//mnの状態
    int stay_time;
    int stay_time_count;
    int id;//mnのid
    int mag_move_count;//magを移動した回数
    int session_count;//通信した回数
    int i;//count_active_timerで使用
    int j;//move_historyで使用
    int pre_x;
    int pre_y;
    int pa_center_x;//ページングエリアの中心
    int pa_center_y;//ページングエリアの中心
    int move_direction;//ノードの移動した方向
    int move_dire_count[];//どの方向に移動したかカウント
    int last_pa_move_time;
    int active_timer;
    int pa_staytime;//paが設定される時のstaytime
    int[] signal = {0,30,60};




    Field field;
    MN_movement mn_move;
    PagingArea pa;
    Random ran;


    MN(int x, int y, int id, int stay_time,int move_dir) {

        this.x = pa_center_x = x;
        this.y = pa_center_y = y;
        this.id = id;
        this.stay_time = pa_staytime = stay_time;
        active = false;
        field = Field.getInstance();
        mag_move_count = 0;



        mn_move = new MN_movement(move_dir);
        move_dire_count = new int[8];
        pa = new PagingArea();
        pa.set_PA(move_dir,x,y,stay_time);

        ran = new Random();

    }


    void update(int time) {

        if (active) count_active_timer();

        if (++stay_time_count == stay_time) {

            detach_mag();
            move();
            change_staytime();
            attach_mag(time);
            state();


            stay_time_count = -wait_signal();
        }

    }

    void change_staytime(){

        if(stay_time <= Main.mn_mag_stay_time[1] && stay_time >= Main.mn_mag_stay_time[3]){

            stay_time = Main.mn_mag_stay_time[ran.nextInt(2)+1];

        }else if(stay_time <= Main.mn_mag_stay_time[4] && stay_time >= Main.mn_mag_stay_time[7]){

            stay_time = Main.mn_mag_stay_time[ran.nextInt(3)+4];
        }

    }

    int wait_signal(){

        if(stay_time != Main.mn_mag_stay_time[8]){

            int x = signal[ran.nextInt(3)];


            System.out.println("wait_time:"+x);
            return x;

        }else{

            if(ran.nextInt(4) == 0){
                System.out.println("wait_time:120");
                return 120;
            }else
                return 0;
        }

    }

    //ノードのモードを変更
    void change_mode(boolean x) {

        active = x;
    }

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

        pre_x = x;
        pre_y = y;

        mn_move.decide_movement_model(stay_time);

        //フィールドの端にいる場合は進む方向を変える。
        if(pre_y == Main.fieldy-1 || pre_x == Main.fieldx-1 || pre_y == 0 || pre_x == 0){
            mn_move.check_direction(pre_x,pre_y);
        }

//        移動していない間はループする
        while(pre_x == x && pre_y == y){


            switch (move_direction = mn_move.move()) {
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
        mag_move_count++;



    }

    //mnの状態を表示
    void state() {


        if (active) {
            System.out.printf("mn%d:(%d,%d) dire_num:%d actual_move:%d pa_center:(%d,%d) pa_m_c:%d  mag_m_c:%d stay_time:%d session:%d active%n"
                    , id, x, y,mn_move.direction_num,move_direction,pa_center_x,pa_center_y, pa.pa_move_count, mag_move_count, stay_time, session_count);
        } else {
            System.out.printf("mn%d:(%d,%d) dire_num:%d actual_move:%d pa_center:(%d,%d) pa_m_c:%d  mag_m_c:%d stay_time:%d session:%d%n"
                    , id, x,y,mn_move.direction_num,move_direction,pa_center_x,pa_center_y, pa.pa_move_count, mag_move_count, stay_time, session_count);
        }
    }

    //MAGにattachした際の処理
    void attach_mag(int time) {

        //PAを移動しているかの判定
        if(!pa.check(x, y)){//PAを移動した場合
            pa.pa_x.clear();
            pa.pa_y.clear();
            pa.set_PA(move_direction,x,y,stay_time);
            pa.pa_move_count++;
            pa_center_x = x;
            pa_center_y = y;
            field.reg_mag(x, y,id ,true, active);
            last_pa_move_time = time;
            pa_staytime = stay_time;

        }else{
            field.reg_mag(x, y,id ,false, active);
        }



    }

    //MAGから離れる際の処理
    void detach_mag() {
        field.del_mag(x, y, id);
    }

    //移動履歴を表示
    void print_move_history(){

        for(int i = 0;i< move_dire_count.length;i++){
            System.out.println(i+":"+ move_dire_count[i]);
        }
    }


}

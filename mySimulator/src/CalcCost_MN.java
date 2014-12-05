import sun.misc.resources.Messages_it;

import java.util.Random;

/**
 * Created by oky on 14/11/30.
 */
public class CalcCost_MN {

    private int x;
    private int y;
    boolean isActive;
    private PagingArea pagingArea;
    private Field field;

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


    Random random;

    public CalcCost_MN(int x, int y,int movement_model_num,int dest_direction){

        this.x = x;
        this.y = y;

        this.dest_direction = dest_direction;
        this.movement_model_num = movement_model_num;

        pagingArea = new PagingArea(movement_model_num,dest_direction);
        pagingArea.set_PA(this.x, this.y);
        field = Field.getInstance();
        field.initial_mn_reg(x,y);

        random = new Random();
    }

    public void update(int x,int y,boolean isActive){

        /*activeに変わった時にPAを作り直す*/
        if (isActive && !this.isActive){
            pa_center_change();

             /* セッションがmn宛か、mnが通信を始めるのかの判定
                1/2で分岐する
                mnからの通信の場合は何もしない*/
            if (random.nextInt(2) == 0){

                pagingArea.paging(this.x,this.y);
            }

            pa_center_change();
        }

        this.isActive = isActive;

        if (this.x != x || this.y != y) {
            detach_bs();
            this.x = x;
            this.y = y;
            attach_bs();

            Main.move_count++;
        }
    }

    //セッションが来た際の座標をページングエリアの中心座標に変える
    void pa_center_change(){
        pagingArea.pa_x.clear();
        pagingArea.pa_y.clear();
        pagingArea.set_PA(x, y);
    }

    //MAGにattachした際の処理
    void attach_bs() {

        //PAを移動しているかの判定
        if(!pagingArea.check(x, y)){//PAを移動した場合
            pagingArea.pa_x.clear();
            pagingArea.pa_y.clear();
            pagingArea.set_PA(x,y);
            Main.pagingarea_move_num++;
            field.reg_bs(x, y,true,isActive);
        }else{
            field.reg_bs(x, y,false,isActive);
        }
    }

    //MAGから離れる際の処理
    void detach_bs() {
        field.del_bs(x, y);
    }

}

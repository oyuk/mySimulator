/**
 * Created by oky on 14/11/30.
 */
public class CalcCost_MN {

    private int x;
    private int y;
    private boolean isActive;
    private PagingArea pa;
    private Field field;

    public CalcCost_MN() {

    }

    public CalcCost_MN(int x, int y, boolean isActive) {
        this.x = x;
        this.y = y;
        this.isActive = isActive;

        pa = new PagingArea();
        field = Field.getInstance();
    }

    public void update(int x,int y,boolean isActive){

        if (this.x != x || this.y != y) {

            detach_bs();

            this.x = x;
            this.y = y;

            attach_bs();
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


}

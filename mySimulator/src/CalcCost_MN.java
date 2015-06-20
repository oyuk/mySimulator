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


    /*
 * ページングモデルの番号
 * 1:3*3
 * 2:提案手法
 */
    int location_legistration_model_num;


    /*目的地方向
    * 移動モデルが2,3の場合に使用
    * */
    int dest_direction;


    Random random;

    public CalcCost_MN(int x, int y,int movement_model_num,int location_legistration_model_num,int dest_direction){

        this.x = x;
        this.y = y;

        this.dest_direction = dest_direction;
//        this.movement_model_num = movement_model_num;
        this.location_legistration_model_num = location_legistration_model_num;

        pagingArea = new PagingArea(location_legistration_model_num,dest_direction);
        pagingArea.set_PA(this.x, this.y);
        field = Field.getInstance();

        System.out.println("ccmn dest_directoin : "+ this.dest_direction);

        random = new Random();
    }

    public void update(int x,int y,boolean isActive){

        /*activeに変わった時にPAを作り直す*/
        if (isActive && !this.isActive){

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
            attach_bs(x,y);
        }
    }

    void checkMoveDirectionIsCorrect(int new_x, int new_y){

        int diff_x = new_x - this.x;
        int diff_y = new_y - this.y;

        boolean is_movement_model_change = false;

        switch (this.dest_direction){
            case 0:
                if (diff_y == -1){
                    is_movement_model_change = true;
                    System.out.println("0");
                }
                break;
            case 1:
                if((diff_x == -1 && diff_y == 0)||
                        (diff_x == -1 && diff_y == -1 )||
                                (diff_x == 0 && diff_y == -1))
                {is_movement_model_change = true;
                    System.out.println("1");}
                break;
            case 2:
                if (diff_x == -1){
                    is_movement_model_change = true;
                    System.out.println("2");
                }
                break;
            case 3:
                if((diff_x == -1 && diff_y == 0)||
                        (diff_x == -1 && diff_y == 1 )||
                        (diff_x == 0 && diff_y == 1))
                {
                    is_movement_model_change = true;
                    System.out.println("3");}
                break;
            case 4:
                if (diff_y == 1){
                    is_movement_model_change = true;
                    System.out.println("4");}
                break;
            case 5:
                if((diff_x == 0 && diff_y == 1)||
                        (diff_x == 1 && diff_y == 1 )||
                        (diff_x == 1 && diff_y == 0))
                {
                    is_movement_model_change = true;
                    System.out.println("5");
                }
                break;
            case 6:
                if (diff_x == 1){
                    is_movement_model_change = true;
                    System.out.println("6");}
                break;
            case 7:
                if((diff_x == 1 && diff_y == 0)||
                        (diff_x == 1 && diff_y == -1 )||
                        (diff_x == 0 && diff_y == -1))
                {
                    is_movement_model_change = true;
                    System.out.println("7");}
                break;
            default:
                break;
        }


        if(is_movement_model_change) {
            this.location_legistration_model_num = pagingArea.location_legistration_model_num = location_registration_model.exist3_3.getNum();
            Main.detour_count++;

            System.out.println("-------------------------------");
            System.out.println("|     movement_model_change   |");
            System.out.println("-------------------------------");
            System.out.println("("+this.x+","+this.y+")->("+new_x+","+new_y+")");
            System.out.println("diff_x = " + diff_x);
            System.out.println("diff_y = " + diff_y);
        }
    }


    //セッションが来た際の座標をページングエリアの中心座標に変える
    void pa_center_change(){
        pagingArea.pa_x.clear();
        pagingArea.pa_y.clear();
        pagingArea.set_PA(x, y);
    }

    //MAGにattachした際の処理
    void attach_bs(int new_x,int new_y) {       
        //PAを移動しているかの判定
        if(!pagingArea.check(new_x,new_y)){//PAを移動した場合
            if(location_legistration_model_num == location_registration_model.proposal.getNum()) {
                checkMoveDirectionIsCorrect(new_x,new_y);
            }
            pagingArea.pa_x.clear();
            pagingArea.pa_y.clear();
            this.x = new_x;
            this.y = new_y;
            pagingArea.set_PA(x,y);
            Main.pagingarea_move_num++;
            field.reg_bs(x, y,true,isActive);
        }else{
            this.x = new_x;
            this.y = new_y;
            field.reg_bs(x, y,false,isActive);
        }
    }

    //MAGから離れる際の処理
    void detach_bs() {
        field.del_bs(x, y);
    }

}

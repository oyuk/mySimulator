import java.util.Random;

/**
 * Created by oky on 14/11/26.
 */
public class Simulator {

    private Field field;

    private Random ran;
    private MN mn;


    private MN_state[] mn_state_array;

    private final int initial_x = 100;
    private final int initial_y = 100;

    public Simulator() {

        field = Field.getInstance();
        field.initField();
        ran = new Random();

    }

    /*
    *
    * ここでMNの移動履歴を記録する
    *
    * */
    public void recordMoveHistory(){

        int time = 0;

        mn_state_array = new MN_state[Main.SimuTime];

        while (true) {

            mn.update(time);

            mn_state_array[time] = new MN_state(mn.x,mn.y,mn.isActive);

            time += Main.refresh_rate;

            if (time >= Main.SimuTime) break;
        }

    }

    /*
    *
    *
    * ここで履歴からコストを計算する
    *
    * */
    public void calcCost(){

        //目的地方向を設定
        int dest_direction = makeMNDirection();

        MN_state mn_state;

        CalcCost_MN ccmn = new CalcCost_MN(initial_x,initial_y,mn.movement_model_num,Main.location_registration_model_num,dest_direction);

        field.initial_mn_reg(initial_x,initial_y);

        for (int i = 0;i<mn_state_array.length;i++){

            mn_state = mn_state_array[i];

            ccmn.update(mn_state.getX(),mn_state.getY(),mn_state.isActive());
        }

    }


    /*
    *
    *
    * MNの初期位置と最終地から移動方向をつくる
    *
    * */

    public int makeMNDirection() {

        int abs_diff_x = Math.abs(initial_x - mn.x);
        int abs_diff_y = Math.abs(initial_y - mn.y);

        int diff_x = mn.x - initial_x;
        int diff_y = mn.y - initial_y;

        if (abs_diff_x <= 1) {

            if (diff_y >= 1) {
                return 0;
            } else if (diff_y <= -1) {
                return 4;
            }
        }


        if (abs_diff_y <= 1) {

            if (diff_x >= 2) {
                return 2;

            } else if (diff_x <= -2) {
                return 6;
            }
        }


        if (diff_x >= 2 && diff_y >= 2) {
            return 1;
        } else if (diff_x >= 2 && diff_y <= -2) {
            return 3;
        } else if (diff_x <= -2 && diff_y <= -2) {
            return 5;
        } else if (diff_x <= -2 && diff_y >= 2) {
            return 7;
        }

        return 0;

    }

    public void startSimulation() {

        for(int i =0;i<Main.SimuCount;i++){

            mn = new MN(initial_x,initial_y,Main.movement_model_num,ran.nextInt(8));
            recordMoveHistory();
            calcCost();

        }

        showResult();

    }

    private void showResult(){

        System.out.println("\nPARAM\n");

        System.out.println("SimuTime                          = " + Main.SimuTime);
        System.out.println("mn_bs_stay_time                   = " + Main.mn_bs_stay_time);
        System.out.println("location_registration_area_extent = " + Main.location_registration_area_extent);
        System.out.println("movement_model_num                = " + Main.movement_model_num);
        System.out.println("lambda                            = " + Main.lambda);


        System.out.println("\nTOTAL\n");

        System.out.println("location_registration_num = " + Main.location_registration_num);
        System.out.println("pagingarea_move_num       = " + Main.pagingarea_move_num);
        System.out.println("paging_num                = "+Main.paging_num);
        System.out.println("paging_cell_num           = "+Main.paging_cell_num);
        System.out.println("paging_delay              = "+Main.paging_delay);
        System.out.println("move_count                = " + Main.move_count);
        System.out.println("detour_count                = " + Main.detour_count);

        System.out.println("\nAVERAGE\n");


        System.out.println("location_registration_num = " + (double)Main.location_registration_num /Main.SimuCount);
        System.out.println("pagingarea_move_num       = " + (double)Main.pagingarea_move_num/Main.SimuCount);
        System.out.println("paging_num                = " + (double)Main.paging_num/Main.SimuCount);

        if(Main.paging_num >= 1) {
            System.out.println("paging_delay              = " + (double)Main.paging_delay / Main.paging_num);
            System.out.println("paging_cell_num           = " + (double)Main.paging_cell_num / Main.paging_num);
        }else{
            System.out.println("paging_delay              = 0");
            System.out.println("paging_cell_num           = 0");
        }

        System.out.println("move_count                = " + Main.move_count/Main.SimuCount);
        System.out.println("detour_count_rate         = " + (double)Main.detour_count/Main.SimuCount);

    }
}

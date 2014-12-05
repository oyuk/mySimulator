import java.io.*;
import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by oky on 14/11/26.
 */
public class Simulator {

    private Field field;
    private int time = 0;
    private Random ran;
    private MN mn;
    private MNlist mNlist;
    private BigDecimal bi;

    private MN_state[] mn_state_array;

    private final int initial_x = 100;
    private final int initial_y = 100;

    public Simulator() {

        field = Field.getInstance();
        field.initField();
        ran = new Random();

        for (int i = 0; i < Main.nodeNum; i++) {
            mn = new MN(initial_x, initial_y,1,2);
        }

    }


    /*
    *
    * ここでMNの移動履歴を記録する
    *
    * */
    public void recordMoveHistory(){

        time = 0;

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

        CalcCost_MN ccmn = new CalcCost_MN(initial_x,initial_y,mn.movement_model_num,dest_direction);

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

        for(int i =0;i<1000;i++){



        }


    }

}

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
    private PagingManager pagingManager;
    private MNlist mNlist;
    private BigDecimal bi;

    private MN_state[] mn_state_array;

    public Simulator() {

        field = Field.getInstance();
        field.initField();
        ran = new Random();
        mNlist = new MNlist();

        for (int i = 0; i < Main.nodeNum; i++) {

            int x = 100;
            int y = 100;

//			mn = new MN(x,y,i,Main.mn_mag_stay_time[ran.nextInt(Main.mn_mag_stay_time.length)]);
//          mn = new MN(x,y,i,Main.mn_bs_stay_time[0],2);

//          MN(int x,int y,int id,int movement_model_num,int dest_direction)

            mn= new MN(x,y,i,1,2);

            //mnの初期BSへの登録
//            field.initial_mn_reg(x, y, i);
//            mNlist.add(mn);
        }

        pagingManager = new PagingManager(mNlist);
        pagingManager.allocate_interval_time();
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

        CalcCost_MN ccmn = new CalcCost_MN();

        MN_state mn_state;

        for (int i = 0;i<mn_state_array.length;i++){

            mn_state = mn_state_array[i];

            //activeなら
            if (mn_state_array[i].isActive()){

            }
        }

    }


    public void startSimulation(){

        time = 0;
        System.out.println("Simulation time:" + Main.SimuTime + "\nField:" + Main.fieldx + "*" + Main.fieldy +
                "\nNode" + ":" + Main.nodeNum);

        System.out.println("\nsimulation start");

//        for (int i = 0; i < Main.nodeNum; i++) mNlist.get(i).state();

        System.out.println();


        while (true) {
            time += Main.refresh_rate;

            // System.out.println(time);

            for (int i = 0; i < Main.nodeNum; i++) mNlist.get(i).update(time);

            pagingManager.update(time);

            if (time >= Main.SimuTime) break;

        }

        System.out.println("simulation finish\n");

        System.out.println();
        field.print();
        System.out.println();
        for (int i = 0; i < Main.nodeNum; i++) {
            mNlist.get(i).state();
            mNlist.get(i).print_move_history();
        }
        System.out.println();
        pagingManager.state();

        System.out.println("PagingManager:location registration cost:"+Main.location_registration_cost);
        //System.out.println("straight:"+Main.straight);
        System.out.println("total_cost:" + (Main.total_cost+pagingManager.total_paging_cost));
        System.out.println("total_paging_delay:" + Main.paging_delay);
        System.out.println("average_paging_delay:" + ((double)Main.paging_delay/(double)pagingManager.session_come_count));
        System.out.println("pbu_count:" + Main.PBU_count);
        System.out.println("pbu2_count:" + Main.PBU2_count);
    }


}

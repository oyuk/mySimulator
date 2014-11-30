import java.util.ArrayList;
import java.util.Random;

/**
 * Created by oky on 14/11/27.
 */
public class PagingManager {


    private ArrayList<MN> mnList;//mnのリスト
    private double time;
    private ArrayList<Integer> expList;//指数分布のリスト
    private ArrayList<ArrayList<Integer>> intervalList;//各mnについて、aListを割り当てるリスト
    int paging_cost;
    double total_paging_cost;

    int session_come_count;
    int session_send_count;

    Random ra;


    public PagingManager(ArrayList<MN> mnList) {
        this.mnList = mnList;
        ra = new Random();
        total_paging_cost = 0;
        paging_cost = 0;
    }

    void allocate_interval_time() {

        intervalList = new ArrayList<ArrayList<Integer>>();

        for (int i = 0; i < Main.nodeNum; i++) {
            intervalList.add(calc_interval_time());
        }
    }


    void update(int time) {

        for (int i = 0; i < Main.nodeNum; i++) {

            if (intervalList.get(i).get(0) == time) {

                /* セッションがmn宛か、mnが通信を始めるのかの判定
                1/2で分岐する
                mnからの通信の場合は何もしない*/
                if(!mnList.get(i).isActive){

                    if(ra.nextInt(2) == 0){
                        //ページングする場合
                        System.out.println(" mn"+i+" session come");
//                        prepare_paging(time, mnList.get(i));
                        session_come_count++;

                    }else{
                        System.out.println(" mn"+i+" session send");
                        session_send_count++;
                    }
                }

                //mnの状態をactive
                mnList.get(i).change_mode(true);
                //セッション回数を追加
                mnList.get(i).session_count++;
                //アクティブタイマーの長さを決定

                mnList.get(i).active_timer += Main.active_timer[ra.nextInt(Main.active_timer.length)];
                System.out.println(time+" active_timer:"+mnList.get(i).active_timer);


                //配列の先頭を削除
                intervalList.get(i).remove(0);

                //ページングエリアの中心を変更
                mnList.get(i).pa_center_change(time);

            }
        }

    }

    //指数分布に従ったパケット送受信レート作成
    ArrayList<Integer> calc_interval_time() {

        expList = new ArrayList<Integer>();
        time = 0;

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

    void state() {
        System.out.println("PagingManager:session_come_count:"+session_come_count);
        System.out.println("PagingManager:session_send_count:"+session_send_count);
                /*
        ペ-ージングリクエストコストはページング回数/2*ページングコストとする。
        セッション数のうち、半分が通信要求、半分がMNがパケットを送る場合とする。
        */
        System.out.println("PagingManager:paging_cost:"+total_paging_cost);
        System.out.println("PagingManager:paging_cost_avarage:"+(total_paging_cost /session_come_count));
    }
}

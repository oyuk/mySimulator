import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;


public class LMA {


    ArrayList<MN> mnList;//mnのリスト
    double time;
    double t;
    BigDecimal bi;
    double t2;
    int digits = 1;
    ArrayList<Integer> expList;//指数分布のリスト
    ArrayList<ArrayList<Integer>> intervalList;//各mnについて、aListを割り当てるリスト
    int paging_cost;
    double total_paging_cost;

    int session_come_count;
    int session_send_count;

    Random ra;


    public LMA(ArrayList<MN> mnList) {
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
                if(!mnList.get(i).active){

                    if(ra.nextInt(2) == 0){
                        //ページングする場合
                        System.out.println(" mn"+i+" session come");
                        prepare_paging(time, mnList.get(i));
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

    void prepare_paging(int time, MN mn){

        int prospect_mn_move_cell;//mnが移動したであろうセル数
        double z = total_paging_cost;
        boolean mn_move_straight = false;
        int mn_exist_num = mn.pa.mn_exist_num(mn.x,mn.y);
        int mn_exist_row = mn.pa.mn_exist_row(mn.x,mn.y);
        int mn_straight_move_cell_num = 0;



        /*mnが直線で進んでいる場合*/
        if(mn_exist_num % 3 == 1){
            mn_move_straight = true;
            mn_straight_move_cell_num = mn_exist_num/3;
            System.out.println("    straight");
            System.out.println("mn_straight_move_cell_num:"+mn_straight_move_cell_num);

            Main.straight++;
        }


        if(mn.pa_staytime == Main.mn_mag_stay_time[0]){
        //    if(mn.pa_staytime <= Main.mn_mag_stay_time[1] && mn.pa_staytime >= Main.mn_mag_stay_time[3]){

           //*mnの進んだであろうセルは5km/hの場合*//*
            prospect_mn_move_cell = (time - mn.last_pa_move_time)/Main.mn_mag_stay_time[0];
            System.out.println(" prospect_mn_move_cell "+prospect_mn_move_cell);

            //*歩行者*//*
            //*ページングエリアの中心にいるかどうかを判定*//*
            if(prospect_mn_move_cell == 5){

                if (mn.pa_center_x == mn.x && mn.pa_center_y == mn.y){
                    paging_cost++;
                    Main.paging_delay++;
                }else{
                    paging_cost +=9;
                    Main.paging_delay += 2;
                }

                System.out.println("   pagingnum:1");

            }else{

                if(mn_move_straight && mn_straight_move_cell_num >= 1 ){
                    paging_cost += 2;
                    Main.paging_delay++;

                }else{
                    paging_cost += Main.paging_cost_array1[2 - mn_exist_row];
                    Main.paging_delay += Main.paging_delay_array1[2-mn_exist_row];
                }

                System.out.println("   pagingnum:2");

            }

        }else if(mn.pa_staytime <= Main.mn_mag_stay_time[4] && mn.pa_staytime >= Main.mn_mag_stay_time[7]){
                //if(mn.pa_staytime == Main.mn_mag_stay_time[0]){
            /*mnの進んだであろうセルは15km/hの場合*/
            prospect_mn_move_cell = (time - mn.last_pa_move_time)/Main.mn_mag_stay_time[4];
            System.out.println(" prospect_mn_move_cell "+prospect_mn_move_cell);

            //自転車
            /*セルを1つ以上移動する時間が経っている場合は提案手法
             そうでない場合は通常のページング*/

            switch (prospect_mn_move_cell){
                case 0:
                    if(mn_move_straight && mn_straight_move_cell_num >= 1 && mn_straight_move_cell_num <=2 ){
                        paging_cost += 2;
                        Main.paging_delay++;

                    }else{

                 //  paging_cost += Main.paging_cost_array2[3 - mn_exist_row_column];
                   // Main.paging_delay += Main.paging_delay_array2[3-mn_exist_row_column];

                   paging_cost += Main.paging_cost_array14[4 - mn_exist_row];
                   Main.paging_delay += Main.paging_delay_array14[4-mn_exist_row];
                    }

                    System.out.println("   pagingnum:3");

                    break;
                case 1:

                    if(mn_move_straight && mn_straight_move_cell_num >= 2 && mn_straight_move_cell_num <=3 ){
                        paging_cost += 2;
                        Main.paging_delay++;

                    }else{

                      // paging_cost += Main.paging_cost_array3[3 - mn_exist_row_column];
                      // Main.paging_delay += Main.paging_delay_array3[3-mn_exist_row_column];

                     paging_cost += Main.paging_cost_array15[4 - mn_exist_row];
                       Main.paging_delay += Main.paging_delay_array15[4-mn_exist_row];

                    }
                    System.out.println("   pagingnum:4");
                    break;
                default:
                    if(mn_move_straight && mn_straight_move_cell_num >= 2 ){
                        paging_cost += 2;
                        Main.paging_delay++;

                    }else{

                       // paging_cost += Main.paging_cost_array3[3 - mn_exist_row_column];
                       // Main.paging_delay += Main.paging_delay_array3[3-mn_exist_row_column];

                        paging_cost += Main.paging_cost_array16[4 - mn_exist_row];
                        Main.paging_delay += Main.paging_delay_array16[4-mn_exist_row];

                    }
                    break;
            }


    }else //if(mn.pa_staytime <= Main.mn_mag_stay_time[8] && mn.pa_staytime >= Main.mn_mag_stay_time[3]){
                if(mn.pa_staytime == Main.mn_mag_stay_time[8]){

        /*mnの進んだであろうセルは40km/hの場合*/
            prospect_mn_move_cell = (time - mn.last_pa_move_time)/Main.mn_mag_stay_time[5];
            System.out.println(" prospect_mn_move_cell "+prospect_mn_move_cell);


            switch (prospect_mn_move_cell){
                case 0:

                    if(mn_move_straight && mn_straight_move_cell_num >= 1 && mn_straight_move_cell_num <=2 ){
                       paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:5");
                    }else{
                     //   paging_cost += Main.paging_cost_array4[5 - mn_exist_row_column];
                     //Main.paging_delay += Main.paging_delay_array4[5-mn_exist_row_column];


                     paging_cost += Main.paging_cost_array17[6 - mn_exist_row];
                       Main.paging_delay += Main.paging_delay_array17[6-mn_exist_row];

                        System.out.println("   pagingnum:6");
                    }
                    break;
                case 1:
                    if(mn_move_straight && mn_straight_move_cell_num >= 2 && mn_straight_move_cell_num <=3 ){
                //        if(mn_move_straight && mn_straight_move_cell_num >= 2 && mn_straight_move_cell_num <=3 ){
                            paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:7");
                    }else{
                      //  paging_cost += Main.paging_cost_array5[5 - mn_exist_row_column];
                      //  Main.paging_delay += Main.paging_delay_array5[5-mn_exist_row_column];

                        paging_cost += Main.paging_cost_array18[6 - mn_exist_row];
                        Main.paging_delay += Main.paging_delay_array18[6-mn_exist_row];

                        System.out.println("   pagingnum:8");
                    }

                    break;
                case 2:
                   if(mn_move_straight && mn_straight_move_cell_num >= 3 && mn_straight_move_cell_num <=4 ){
                       // if(mn_move_straight && mn_straight_move_cell_num >= 3 && mn_straight_move_cell_num <=4 ){
                           // paging_cost += 3;
                            paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:9");
                    }else{
                       //paging_cost += Main.paging_cost_array6[5 - mn_exist_row_column];
                        //Main.paging_delay += Main.paging_delay_array6[5-mn_exist_row_column];

                         paging_cost += Main.paging_cost_array19[6 - mn_exist_row];
                           Main.paging_delay += Main.paging_delay_array19[6-mn_exist_row];

                        System.out.println("   pagingnum:10");
                    }
                    break;

                case 3:
                    //+3の場合はcase3は消す
                    if(mn_move_straight && mn_straight_move_cell_num >= 4 && mn_straight_move_cell_num <=5 ){
                        paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:11");
                    }else{
                        paging_cost += Main.paging_cost_array20[6 - mn_exist_row];
                        Main.paging_delay += Main.paging_delay_array20[6-mn_exist_row];
                        System.out.println("   pagingnum:12");
                    }

                    break;

                default:
                  // if(mn_move_straight && mn_straight_move_cell_num >= 4 && mn_straight_move_cell_num <=5 ){
                       if(mn_move_straight && mn_straight_move_cell_num >= 4 && mn_straight_move_cell_num <=6 ){
                        paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:11");
                    }else{
                      //  paging_cost += Main.paging_cost_array7[5 - mn_exist_row_column];
                    //   Main.paging_delay += Main.paging_delay_array7[5-mn_exist_row_column];

                     paging_cost += Main.paging_cost_array21[6 - mn_exist_row];
                      Main.paging_delay += Main.paging_delay_array21[6-mn_exist_row];

                            System.out.println("   pagingnum:12");
                    }

                    break;
            }

        }else if(mn.pa_staytime <= Main.mn_mag_stay_time[8] && mn.pa_staytime >= Main.mn_mag_stay_time[7]){
                //if(mn.pa_staytime == Main.mn_mag_stay_time[8]){
            /*mnの進んだであろうセルは100km/hの場合*/
            prospect_mn_move_cell = (time - mn.last_pa_move_time)/Main.mn_mag_stay_time[5];
            System.out.println(" prospect_mn_move_cell "+prospect_mn_move_cell);

            switch (prospect_mn_move_cell){
                case 0:

                    if(mn_move_straight && mn_straight_move_cell_num >= 1 && mn_straight_move_cell_num <=2 ){
                        paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:13");
                    }else{
                        //paging_cost += Main.paging_cost_array8[7 - mn_exist_row_column];
                       // Main.paging_delay += Main.paging_delay_array8[7-mn_exist_row_column];

                        paging_cost += Main.paging_cost_array22[8 - mn_exist_row];
                        Main.paging_delay += Main.paging_delay_array22[8-mn_exist_row];

                        System.out.println("   pagingnum:14");
                    }

                    break;
                case 1:

                   if(mn_move_straight && mn_straight_move_cell_num >= 2 && mn_straight_move_cell_num <=3 ){
                        paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:15");
                    }else{
                      //  paging_cost += Main.paging_cost_array9[7 - mn_exist_row_column];
                      // Main.paging_delay += Main.paging_delay_array9[7-mn_exist_row_column];

                      paging_cost += Main.paging_cost_array23[8 - mn_exist_row];
                       Main.paging_delay += Main.paging_delay_array23[8-mn_exist_row];

                        System.out.println("   pagingnum:16");
                    }

                    break;
                case 2:

                    if(mn_move_straight && mn_straight_move_cell_num >= 3 && mn_straight_move_cell_num <=4 ){

                        paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:17");
                    }else{
                      //  paging_cost += Main.paging_cost_array10[7 - mn_exist_row_column];
                     //Main.paging_delay += Main.paging_delay_array10[7-mn_exist_row_column];

                        paging_cost += Main.paging_cost_array24[8 - mn_exist_row];
                        Main.paging_delay += Main.paging_delay_array24[8-mn_exist_row];

                        System.out.println("   pagingnum:18");
                    }

                    break;
                case 3:

                    if(mn_move_straight && mn_straight_move_cell_num >= 4 && mn_straight_move_cell_num <=5 ){

                        paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:19");
                    }else{
                     // paging_cost += Main.paging_cost_array11[7 - mn_exist_row_column];
                     //  Main.paging_delay += Main.paging_delay_array11[7-mn_exist_row_column];

                      paging_cost += Main.paging_cost_array25[8 - mn_exist_row];
                      Main.paging_delay += Main.paging_delay_array25[8-mn_exist_row];

                        System.out.println("   pagingnum:20");
                    }

                    break;
                case 4:

                    if(mn_move_straight && mn_straight_move_cell_num >= 5 && mn_straight_move_cell_num <=6 ){
                        paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:21");
                    }else{
                      //  paging_cost += Main.paging_cost_array12[7 - mn_exist_row_column];
                      //  Main.paging_delay += Main.paging_delay_array12[7-mn_exist_row_column];

                      paging_cost += Main.paging_cost_array26[8 - mn_exist_row];
                       Main.paging_delay += Main.paging_delay_array26[8-mn_exist_row];

                        System.out.println("   pagingnum:22");
                    }

                    break;



                case 5:

                    if(mn_move_straight && mn_straight_move_cell_num >= 6 && mn_straight_move_cell_num <=7 ){
                        paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:21");
                    }else{
                        paging_cost += Main.paging_cost_array27[8 - mn_exist_row];
                        Main.paging_delay += Main.paging_delay_array27[8-mn_exist_row];

                        System.out.println("   pagingnum:22");
                    }
                    break;


                default:

                  // if(mn_move_straight && mn_straight_move_cell_num >= 5){
                       if(mn_move_straight && mn_straight_move_cell_num >= 6){
                        paging_cost += 3;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:23");
                    }else{
                    //    paging_cost += Main.paging_cost_array13[7 - mn_exist_row_column];
                      //  Main.paging_delay += Main.paging_delay_array13[7-mn_exist_row_column];

                       paging_cost += Main.paging_cost_array28[8 - mn_exist_row];
                        Main.paging_delay += Main.paging_delay_array28[8-mn_exist_row];

                            System.out.println("   pagingnum:24");
                    }

                    break;
            }


        }else //if(mn.pa_staytime == Main.mn_mag_stay_time[0]){
                if(mn.pa_staytime <= Main.mn_mag_stay_time[1] && mn.pa_staytime >= Main.mn_mag_stay_time[3]){
                        /*mnの進んだであろうセルは100km/hの場合*/
            prospect_mn_move_cell = (time - mn.last_pa_move_time)/Main.mn_mag_stay_time[1];
            System.out.println(" prospect_mn_move_cell "+prospect_mn_move_cell);

            switch (prospect_mn_move_cell){
                case 0:

                    if(mn_move_straight && mn_straight_move_cell_num >= 1 && mn_straight_move_cell_num <=2 ){
                        paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:13");
                    }else{

                       // paging_cost += Main.paging_cost_array29[9 - mn_exist_row_column];
                       // Main.paging_delay += Main.paging_delay_array29[9-mn_exist_row_column];

                        paging_cost += Main.paging_cost_array37[10 - mn_exist_row];
                        Main.paging_delay += Main.paging_delay_array37[10-mn_exist_row];

                        System.out.println("   pagingnum:14");
                    }

                    break;
                case 1:

                    if(mn_move_straight && mn_straight_move_cell_num >= 2 && mn_straight_move_cell_num <=3 ){
                        paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:15");
                    }else{

                      //  paging_cost += Main.paging_cost_array30[9 - mn_exist_row_column];
                       //Main.paging_delay += Main.paging_delay_array30[9-mn_exist_row_column];

                        paging_cost += Main.paging_cost_array38[10 - mn_exist_row];
                        Main.paging_delay += Main.paging_delay_array38[10-mn_exist_row];


                        System.out.println("   pagingnum:16");
                    }

                    break;
                case 2:

                    if(mn_move_straight && mn_straight_move_cell_num >= 3 && mn_straight_move_cell_num <=4 ){

                        paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:17");
                    }else{

                      //  paging_cost += Main.paging_cost_array31[9 - mn_exist_row_column];
                      // Main.paging_delay += Main.paging_delay_array31[9-mn_exist_row_column];

                        paging_cost += Main.paging_cost_array39[10 - mn_exist_row];
                        Main.paging_delay += Main.paging_delay_array39[10-mn_exist_row];


                        System.out.println("   pagingnum:18");
                    }

                    break;
                case 3:

                    if(mn_move_straight && mn_straight_move_cell_num >= 4 && mn_straight_move_cell_num <=5 ){

                        paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:19");
                    }else{

                      //  paging_cost += Main.paging_cost_array32[9 - mn_exist_row_column];
                      // Main.paging_delay += Main.paging_delay_array32[9-mn_exist_row_column];

                       paging_cost += Main.paging_cost_array40[10 - mn_exist_row];
                        Main.paging_delay += Main.paging_delay_array40[10-mn_exist_row];


                        System.out.println("   pagingnum:20");
                    }

                    break;
                case 4:

                    if(mn_move_straight && mn_straight_move_cell_num >= 5 && mn_straight_move_cell_num <=6 ){
                        paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:21");
                    }else{

                       // paging_cost += Main.paging_cost_array33[9 - mn_exist_row_column];
                       // Main.paging_delay += Main.paging_delay_array33[9-mn_exist_row_column];


                        paging_cost += Main.paging_cost_array41[10 - mn_exist_row];
                        Main.paging_delay += Main.paging_delay_array41[10-mn_exist_row];

                        System.out.println("   pagingnum:22");
                    }

                    break;

                case 5:

                    if(mn_move_straight && mn_straight_move_cell_num >= 6 && mn_straight_move_cell_num <=7 ){
                        paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:21");
                    }else{

                        //paging_cost += Main.paging_cost_array34[9 - mn_exist_row_column];
                       // Main.paging_delay += Main.paging_delay_array34[9-mn_exist_row_column];

                       paging_cost += Main.paging_cost_array42[10 - mn_exist_row];
                       Main.paging_delay += Main.paging_delay_array42[10-mn_exist_row];

                        System.out.println("   pagingnum:22");
                    }
                case 6:
                    if(mn_move_straight && mn_straight_move_cell_num >= 7 && mn_straight_move_cell_num <=8 ){
                        paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:21");
                    }else{

                       // paging_cost += Main.paging_cost_array35[9 - mn_exist_row_column];
                       // Main.paging_delay += Main.paging_delay_array35[9-mn_exist_row_column];

                        paging_cost += Main.paging_cost_array43[10 - mn_exist_row];
                       Main.paging_delay += Main.paging_delay_array43[10-mn_exist_row];


                        System.out.println("   pagingnum:22");
                    }

                    break;
                case 7:
                    if(mn_move_straight && mn_straight_move_cell_num >= 8 && mn_straight_move_cell_num <=9 ){
                        paging_cost += 2;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:21");
                    }else{

                       // paging_cost += Main.paging_cost_array35[9 - mn_exist_row_column];
                       // Main.paging_delay += Main.paging_delay_array35[9-mn_exist_row_column];

                        paging_cost += Main.paging_cost_array44[10 - mn_exist_row];
                        Main.paging_delay += Main.paging_delay_array44[10-mn_exist_row];


                        System.out.println("   pagingnum:22");
                    }

                    break;
                default:

                    if(mn_move_straight && mn_straight_move_cell_num >= 8){
                        paging_cost += 3;
                        Main.paging_delay++;
                        System.out.println("   pagingnum:23");
                    }else{


                        paging_cost += Main.paging_cost_array45[9 - mn_exist_row];
                        Main.paging_delay += Main.paging_delay_array45[9-mn_exist_row];


                        System.out.println("   pagingnum:24");
                    }

                    break;
            }

        }

        total_paging_cost += Main.PR*paging_cost*Main.d_LMA_MAG;
        //total_paging_cost += paging_cost;
        paging_cost = 0;
        System.out.println(" cost:"+(total_paging_cost -z));
    }



    void state() {
        System.out.println("LMA:session_come_count:"+session_come_count);
        System.out.println("LMA:session_send_count:"+session_send_count);
                /*
        ペ-ージングリクエストコストはページング回数/2*ページングコストとする。
        セッション数のうち、半分が通信要求、半分がMNがパケットを送る場合とする。
        */
        System.out.println("LMA:paging_cost:"+total_paging_cost);
        System.out.println("LMA:paging_cost_avarage:"+(total_paging_cost /session_come_count));
    }

    //指数分布に従ったパケット送受信レート作成
    ArrayList<Integer> calc_interval_time() {

        expList = new ArrayList<Integer>();
        time = 0;

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


}

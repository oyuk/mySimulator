import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

        String result = "";

        result +="\nPARAM\n\n";

        result +="SimuTime                          = " + Main.SimuTime + "\n";
        result +="mn_bs_stay_time                   = " + Main.mn_bs_stay_time + "\n";
        result +="location_registration_area_extent = " + Main.location_registration_area_extent + "\n";
        result +="movement_model_num                = " + Main.movement_model_num + "\n";
        result +="lambda                            = " + Main.lambda + "\n";


//        result +="\nTOTAL\n\n";
//
//        result +="location_registration_num = " + Main.location_registration_num + "\n";
//        result +="pagingarea_move_num       = " + Main.pagingarea_move_num + "\n";
//        result +="paging_num                = "+Main.paging_num + "\n";
//        result +="paging_cell_num           = "+Main.paging_cell_num + "\n";
//        result +="paging_delay              = "+Main.paging_delay + "\n";
//        result +="move_count                = " + Main.move_count + "\n";
//        result +="detour_count              = " + Main.detour_count + "\n";

        result +="\nAVERAGE\n\n";

        result +="location_registration_num = " + (double)Main.location_registration_num /Main.SimuCount + "\n";
        result +="pagingarea_move_num       = " + (double)Main.pagingarea_move_num/Main.SimuCount + "\n";
        result +="paging_num                = " + (double)Main.paging_num/Main.SimuCount + "\n";

        if(Main.paging_num >= 1) {
            result +="paging_delay              = " + String.format("%.3f",(double)Main.paging_delay / Main.paging_num) + "\n";
            result +="paging_cell_num           = " + String.format("%.3f",(double)Main.paging_cell_num / Main.paging_num) + "\n";
        }else{
            result +="paging_delay              = 0" + "\n";
            result +="paging_cell_num           = 0" + "\n";
        }

        result +="move_count                = " + Main.move_count/Main.SimuCount + "\n";
        result +="detour_count_rate         = " + (double)Main.detour_count/Main.SimuCount + "\n";

        result +="filepath                  = " + Main.filePath + "\n";
        System.out.println(result);
        writeResultToFile(result);
    }

    private void writeResultToFile(String result){
        int index = Main.filePath.indexOf(".txt");
        String extractString = Main.filePath.substring(0,index);
        String writeFilePath = extractString + "_result.txt";

        // 出力したいファイル名を指定してFileオブジェクトを生成します。
        File file = new File(writeFilePath);

        FileWriter filewriter = null;
        try{
            filewriter = new FileWriter(file);

            // ここでファイルに文字を書き込みます。
            filewriter.write(result);
            System.out.println("write file ->" + writeFilePath);

        }catch(IOException e){
            System.out.println(e);
        } finally {
            // クローズ処理（成功・失敗に関わらず必ずクローズします。）
            // クローズ漏れはバグのもとになります。必ずfinally句でクローズしましょう。
            if (filewriter != null) {
                try {
                    filewriter.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }

    }
}

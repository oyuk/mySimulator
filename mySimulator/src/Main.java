import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oky on 14/11/26.
 */
public class Main {

    static final int SimuCount = 1000;

    //フィールドの範囲
    static final int fieldx = 200;
    static final int fieldy = 200;

    //シミュレーション時間
    static int SimuTime = 0;
    static final int refresh_rate = 1;
    //MNの数
    static final int nodeNum = 1;

    //MNがMAGにいる時間
    static int mn_bs_stay_time = 0;


    static int movement_model_num = 0;

//    MNがactive状態になっている時間
    static final int active_timer[] = {15,30,60};


    /*PagingManager
    *
    * lambda:ポアソン過程のλ
    * per_unit_time:単位時間
    *
    */
    static int lambda = 3;
    //1時間を単位時間とする
    final static int per_unit_time = 60*60;


    final static int[] C_paging_33 = {1,9};

    final static int[] C_proposed_paging_33 = {3,9};
    final static int[] C_proposed_paging_34 = {6,12};
    final static int[] C_proposed_paging_35 = {3,9,15};

    //位置登録エリアの大きさ
    static int location_registration_area_extent;


    /*位置登録をした回数*/
    /*BS locationupadte*/
    static int location_registration_num = 0;

    /*ページングエリアを移動した回数*/
    /*CalcCost_MN attachbs*/
    static int pagingarea_move_num = 0;

    /*ページングディレイ*/
    /*PagingArea paging*/
    static int paging_delay = 0;

    /*ページングしたセル数*/
    /*pagingArea paging*/
    static int paging_cell_num = 0;

    /*ページングした回数*/
    /*PagingArea paging*/
    static int paging_num = 0;

    /*ページングコスト * ページング回数*/
    static int total_paging_cost = 0;

    /*移動した回数*/
    /*MN move*/
    static int move_count = 0;

/*迂回しているため手法が使えなかったmnの数*/
    static int detour_count = 0;

    /*テキストファイルから読み込むパラメータ
    *
    * ノード数(ランダム、二地点最短、二地点迂回)
    * 基地局通信半径
    * 位置登録エリアの大きさ
    *
    *
    *
    *
    *
    * */


    /*
    *
    * 入力するテキストファイルの書式
    *
    * シミュレーション時間 0.5,1,1.5,2
    *
    *           30分、１時間、１時間３０分、二時間
    *
    * 基地局通信半径 1,1.5
    *
    *            1km,1.5km
    *
    * 位置登録エリアの大きさ 0,1,2
    *
    *             3*3,3*4,3*5
    *
    * 移動端末の移動パターン 1,2,3
    *
    *             移動モデルの番号
    *             1:ランダム
    *             2:二地点最短
    *             3:二地点迂回
    *
    * ページングエリア作成方法
    *
    *            1: 3 * 3
    *            2: 提案手法
    *
    * 一時間あたりの平均通信回数のλ
    *              1,2,3
    *
    *
    * */

    static String filePath;

    public static void main(String[] args) {

        if (args != null) {

            filePath = args[0];
            readFile();
            new Simulator().startSimulation();

        }
    }

    private static void readFile(){

        List<String> paramList = new ArrayList<String>();

        try {

            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String str;
            while ((str = br.readLine()) != null) {
                paramList.add(str);
            }

            br.close();

            if (paramList.size() == 12){
                setParam(paramList);
            }

        }catch(FileNotFoundException e){
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }
    }

    private static void setParam(List<String> list) {

        SimuTime = (int) (Float.parseFloat(list.get(1)) * 3600);

        float bs_radius = Float.parseFloat(list.get(3));


        mn_bs_stay_time = (int)((bs_radius/35) * 3600);

        location_registration_area_extent = Integer.parseInt(list.get(5));

        movement_model_num = Integer.parseInt(list.get(7));

        location_registration_num = Integer.parseInt(list.get(9));

        lambda = Integer.parseInt(list.get(11));

    }


}
